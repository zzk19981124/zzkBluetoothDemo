package com.example.demobluetooth.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.example.demobluetooth.MainActivity;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

public class DynamicPermissions {
    //private Context mContext;
    private Activity mActivity;
    public DynamicPermissions(Activity mActivity) {
        this.mActivity = mActivity;
        getLocationPermissions();
    }
    //获取定位权限
    public void getLocationPermissions(){
        XXPermissions.with(mActivity)
                .permission(Permission.ACCESS_COARSE_LOCATION,Permission.ACCESS_FINE_LOCATION)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll)
                            toast("获取地址权限成功");
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {

                    }
                });
    }

    public void toast(CharSequence text){
        Toast.makeText(mActivity,text,Toast.LENGTH_SHORT).show();
    }
}
