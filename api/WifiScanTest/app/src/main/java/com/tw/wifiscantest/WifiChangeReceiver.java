package com.tw.wifiscantest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

public class WifiChangeReceiver extends BroadcastReceiver {
    private static final String TAG = "twTestWifi";

    @Override
    public void onReceive(Context context, Intent intent) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        boolean resultUpdate = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
        List<ScanResult> resultList = wifiManager.getScanResults();
        Log.d(TAG, "WifiChangeReceiver-->resultUpdate：" + resultUpdate + ", resultList.size():" + resultList.size());
    }
}
