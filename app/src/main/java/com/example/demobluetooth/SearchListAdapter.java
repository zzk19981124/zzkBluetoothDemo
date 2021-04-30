package com.example.demobluetooth;


import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vise.baseble.model.BluetoothLeDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hello word
 * @desc 作用描述
 * @date 2021/4/30
 */
public class SearchListAdapter extends BaseAdapter {

    private List<BluetoothLeDevice> mData;
    private Context mContext;

    public SearchListAdapter(List<BluetoothLeDevice> data, Context context) {
        mData = data;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null){
            itemView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_2,parent,false);
        }
        TextView line1 = itemView.findViewById(android.R.id.text1);
        TextView line2 = itemView.findViewById(android.R.id.text2);

        BluetoothLeDevice device = (BluetoothLeDevice) getItem(position);
        line1.setText(device.getName());
        line2.setText(device.getAddress());

        return itemView;
    }
}
