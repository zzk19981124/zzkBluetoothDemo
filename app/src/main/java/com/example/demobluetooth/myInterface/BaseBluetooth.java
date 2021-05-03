package com.example.demobluetooth.myInterface;

import java.util.ArrayList;
import java.util.List;

public interface BaseBluetooth<T> {
    // 扫描所有设备
    public List<T> ScanDevice(int timeout);
    public Boolean StopScan();
    // 通过地址连接设备
    public Boolean ConnectDevice(String address);
    // 断开当前设备
    public Boolean Disconnect();
    // 获取所有设备
    public List<T> getAllDevice();
}
