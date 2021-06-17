package com.example.demobluetooth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class BleInformationActivity extends AppCompatActivity {
    private TextView mTextView;
    private Button get_infor;
    private ArrayList<String> stringList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_information);
        mTextView = findViewById(R.id.getAddress);
        get_infor = findViewById(R.id.get_infor);
        get_infor.setOnClickListener(mOnClickListener);
        stringList = (ArrayList<String>) getIntent().getStringArrayListExtra("ListString");

    }
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mTextView.setText(stringList.toString());
            //var s = 1;
            //val
        }
    };
    //public void someList()
}