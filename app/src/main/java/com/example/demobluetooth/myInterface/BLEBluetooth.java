package com.example.demobluetooth.myInterface;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import com.vise.baseble.ViseBle;
import com.vise.baseble.callback.IConnectCallback;
import com.vise.baseble.callback.scan.IScanCallback;
import com.vise.baseble.callback.scan.ScanCallback;
import com.vise.baseble.core.DeviceMirror;
import com.vise.baseble.exception.BleException;
import com.vise.baseble.model.BluetoothLeDevice;
import com.vise.baseble.model.BluetoothLeDeviceStore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class BLEBluetooth implements BaseBluetooth<BluetoothLeDevice> {
    private Context context;
    private List<BluetoothLeDevice> allDevice = new ArrayList<>();
    private Set<String> addressSet = new HashSet<>();
    private ScanCallback scanCallback;

    public BLEBluetooth(Context context) {
        this.context = context;
        init();
    }

    private void init(){
        //蓝牙相关配置修改
        ViseBle.config()
                .setScanTimeout(-1)//扫描超时时间，这里设置为永久扫描
                .setConnectTimeout(10 * 1000)//连接超时时间
                .setOperateTimeout(5 * 1000)//设置数据操作超时时间
                .setConnectRetryCount(3)//设置连接失败重试次数
                .setConnectRetryInterval(1000)//设置连接失败重试间隔时间
                .setOperateRetryCount(3)//设置数据操作失败重试次数
                .setOperateRetryInterval(1000)//设置数据操作失败重试间隔时间
                .setMaxConnectCount(3);//设置最大连接设备数量
        //蓝牙信息初始化，全局唯一，必须在应用初始化时调用
        ViseBle.getInstance().init(context);
    }

    @Override
    public List<BluetoothLeDevice> ScanDevice(int timeout) {
        if (timeout == 0) timeout = 5000;

        System.out.println("开始扫描");
        allDevice.clear();
        addressSet.clear();
        scanCallback = new ScanCallback(new IScanCallback() {
            @Override
            public void onDeviceFound(BluetoothLeDevice bluetoothLeDevice) {
                if (bluetoothLeDevice.getName() == null || bluetoothLeDevice.getName().isEmpty()){
                    System.out.println("检测到无名称");
                    return;
                }
                if (addressSet.contains(bluetoothLeDevice.getAddress())){
                    System.out.println("检测到重复设备");
                    return;
                }

                addressSet.add(bluetoothLeDevice.getAddress());
                allDevice.add(bluetoothLeDevice);

                System.out.println("设备的长度："+allDevice.size());
            }

            @Override
            public void onScanFinish(BluetoothLeDeviceStore bluetoothLeDeviceStore) {
                System.out.println("扫描结束"+allDevice.size());
            }

            @Override
            public void onScanTimeout() {
                System.out.println("扫描超时"+allDevice.size());
            }
        });
        ViseBle.getInstance().startScan(scanCallback);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                StopScan();
            }
        }, timeout);
        return allDevice;
    }

    @Override
    public Boolean StopScan() {
        System.out.println("停止扫描设备: "+ allDevice.size());
        ViseBle.getInstance().stopScan(scanCallback);
        return true;
    }
    //根据设备mac地址直接扫描并连接
    @Override
    public Boolean ConnectDevice(String address) {
        IConnectCallback iConnectCallback = new IConnectCallback(){

            @Override
            public void onConnectSuccess(DeviceMirror deviceMirror) {
                System.out.println("连接成功");
            }

            @Override
            public void onConnectFailure(BleException exception) {
                System.out.println("连接设备失败");
            }

            @Override
            public void onDisconnect(boolean isActive) {
                System.out.println("断开连接");
            }
        };
        ViseBle.getInstance().connectByMac(address,iConnectCallback);
        return true;
    }

    @Override
    public Boolean Disconnect() {
        return true;
    }

    @Override
    public List<BluetoothLeDevice> getAllDevice() {
        return allDevice;
    }
}
