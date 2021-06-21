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
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_information);
        mTextView = findViewById(R.id.getAddress);
        get_infor = findViewById(R.id.get_infor);
        //getDevice = getIntent().getSE

        Intent intent = getIntent();
        //Bundle bundle = intent.getExtras();
        //getDevice = (List<BluetoothLeDevice>) bundle.getSerializable("message");
        getDevice = (HashMap<String,BluetoothLeDevice>) getIntent().getSerializableExtra("message");
       // stringList = (ArrayList<String>) getIntent().getStringArrayListExtra("ListString");
        get_infor.setOnClickListener(mOnClickListener);
    }
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*String getInfo = printStringList(stringList);
            if (getInfo.equals("")){
                Toast.makeText(BleInformationActivity.this,"no no no",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            mTextView.setText(getInfo);*/

            //getAllInfo();
            mTextView.setText(getAllInfo());//从设备镜像中获取到需要的数据,并传递给文本框
        }
    };
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
}