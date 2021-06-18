package com.example.demobluetooth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BleInformationActivity extends AppCompatActivity {
    private TextView mTextView;
    private Button get_infor;
    private ArrayList<String> stringList;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_information);
        mTextView = findViewById(R.id.getAddress);
        get_infor = findViewById(R.id.get_infor);
        
        stringList = (ArrayList<String>) getIntent().getStringArrayListExtra("ListString");
        get_infor.setOnClickListener(mOnClickListener);
    }
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String getInfo = printStringList(stringList);
            if (getInfo.equals("")){
                Toast.makeText(BleInformationActivity.this,"no no no",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            mTextView.setText(getInfo);
        }
    };
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
}