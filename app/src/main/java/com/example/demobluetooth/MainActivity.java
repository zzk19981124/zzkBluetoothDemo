package com.example.demobluetooth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.demobluetooth.myInterface.BLEBluetooth;
import com.example.demobluetooth.myPermission.BasePermission;
import com.example.demobluetooth.myPermission.PermissionHelper;
import com.example.demobluetooth.utils.DynamicPermissions;
import com.vise.baseble.model.BluetoothLeDevice;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private List<BluetoothLeDevice> mDeviceList = new ArrayList<>();
    private SearchListAdapter mAdapter;
    private Button scanBtn,getPerBtn;
    private static final String TAG = "MainActivity";
    private PermissionHelper permissionHelper;
    private final int MY_PERMISSION_REQUEST_CODE = 404;
    private ArrayList<String> stringList = new ArrayList<String>();  //放连接成功后设备的信息
    private BLEBluetooth bleBluetooth;
    private DynamicPermissions dynamicPermissions;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            mAdapter.notifyDataSetChanged();
            String text = String.valueOf(msg.obj);
            if (text == null || text.equals("")){
                scanBtn.setText(msg.what + "");
            }else
                scanBtn.setText(text);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        //确定BLE的可用性
        ble_is_support();
        //使用蓝牙库之前，初始化
        initBlueTooth();
        //初始化控件
        initView();
        bindListener();

    }
    //检查动态权限
    private void init(){
        permissionHelper = new PermissionHelper(this, MY_PERMISSION_REQUEST_CODE);
    }


    private void ble_is_support() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
        }
    }

    private void initBlueTooth() {
        //蓝牙相关配置修改
        bleBluetooth = new BLEBluetooth(this);
    }

    private void initView() {
        mListView = findViewById(R.id.device_list);
        scanBtn = findViewById(R.id.scan_device);
        getPerBtn = findViewById(R.id.get_permissions);
    }

    private void bindListener(){
        /*
        * 检查需要满足的动态权限，如果没有打开权限，会弹窗提示需要用户打开哪些权限
        * */
        getPerBtn.setText("申请权限");
        getPerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionHelper.startRequestPermissions();
            }
        });
        /*
        * 扫描所有低功耗蓝牙设备
        * */
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击按钮——扫描");
                scanningAllEquipment();
            }
        });

        /*
        * 点击长按监听listView的item，用来连接设备
        * */
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this,"长按了第"+position+"个item",Toast.LENGTH_SHORT).show();
                switch (parent.getId()){
                    case R.id.device_list:
                        connectDevice(position);    //连接设备
                        expressItemClick(position);  //代表你点击了哪个item
                        break;
                }

                return false;
            }
            public void expressItemClick(int position){
                stringList.add(bleBluetooth.getAdress());
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,BleInformationActivity.class);
                intent.putStringArrayListExtra("ListString",stringList);
                startActivity(intent);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //bleBluetooth.ConnectDevice(mDeviceList.get(position).getAddress());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void scanningAllEquipment() {
        mDeviceList = bleBluetooth.ScanDevice(5000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(i);
                }
                //scanBtn.setText("刷新设备");
                //System.out.println("所有设备：" + mDeviceList);
                Log.d(TAG, "run: ");
                //handler.sendEmptyMessage(666);
                Message msg = new Message();
                msg.obj = "扫描结束";
                handler.sendMessage(msg);
            }
        }).start();

        mAdapter = new SearchListAdapter(mDeviceList, this);
        mListView.setAdapter(mAdapter);
    }
    private void connectDevice(int position){
        BluetoothLeDevice bluetoothLeDevice = mDeviceList.get(position);
        //连接设备
        bleBluetooth.ConnectDevice(bluetoothLeDevice);
        Log.d(TAG, "连接设备中");

    }

}