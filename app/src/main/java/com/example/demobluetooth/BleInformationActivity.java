package com.example.demobluetooth;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vise.baseble.core.DeviceMirror;
import com.vise.baseble.model.BluetoothLeDevice;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BleInformationActivity extends AppCompatActivity {
    private TextView mTextView;
    private Button get_infor;
    //private ArrayList<String> stringList;
    private HashMap<String,BluetoothLeDevice> getDevice = new HashMap<String,BluetoothLeDevice>();
    private List<DeviceMirror> getDeviceMirror = new ArrayList<>();
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_information);
        initView();  //初始化控件
        Intent intent = getIntent();
        getDeviceMirror = (List<DeviceMirror>) getIntent().getSerializableExtra("message");
        get_infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mTextView.setText(getAllInfo());
            }
        });
    }

    private void initView() {
        mTextView = findViewById(R.id.getAddress);
        get_infor = findViewById(R.id.get_infor);
    }

    //从设备镜像中获取到需要的数据
    public String getAllInfo(){
        String result = "";
        //getDevice.getClass();
        BluetoothLeDevice theDevice = getDevice.get(0);
        String deviceName = theDevice.getName();
        String deviceAddress = theDevice.getAddress();
        result = deviceName+"\n"+deviceAddress;
        return result;
    }
    //将stringList转换成string
    private String printStringList(ArrayList<String> stringList){
        String result = "";
        if (stringList!=null&&stringList.size()>0){
            for (String item : stringList){
                //result += item + ",";
                result += item;
            }
        }
        return result;
    }
    private void getIntentResult(){

    }
    private String getDeviceMirror(){
        String result = "";
        //String
        return result;
    }
}