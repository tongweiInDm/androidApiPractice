package com.tw.wifiscantest;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "twTestWifi";
    private static final int PERMISSION_REQ_CODE = 123;
    private static final long SCAN_CYCLE = 30000;
    private static final int MSG_SCAN = 0;

    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss.SSS");
    private Handler mHandler;
    private TextView mLogView;
    private StringBuilder mLogBuilder;
    private WifiManager mWifiManager;
    private BroadcastReceiver mScanResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQ_CODE);
                } else {
                    mWifiManager.startScan();
                    log("start scan");
                }
                mHandler.sendEmptyMessageDelayed(MSG_SCAN, SCAN_CYCLE);
            }
        };

        mScanResultReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean resultUpdate = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
                List<ScanResult> resultList = mWifiManager.getScanResults();
                log("resultUpdate:" + resultUpdate + ", result.size:" + resultList.size());
//                for (int i = 0;i < resultList.size();i++) {
//                    ScanResult scanResult = resultList.get(i);
//                    Log.d(TAG, "---------------------" + i);
//                    Log.d(TAG, "scanResult[" + i + "].BSSID:" + scanResult.BSSID);
//                    Log.d(TAG, "scanResult[" + i + "].SSID:" + scanResult.SSID);
//                    Log.d(TAG, "scanResult[" + i + "].level:" + scanResult.level);
//                    Log.d(TAG, "scanResult[" + i + "].capabilities:" + scanResult.capabilities);
//                }
            }
        };

        mLogView = findViewById(R.id.tv_log);
        mLogBuilder = new StringBuilder();

        findViewById(R.id.btn_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(MSG_SCAN);
            }
        });

        registerReceiver(mScanResultReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    private void log(String log) {
        mLogBuilder.append(mDateFormat.format(new Date(System.currentTimeMillis())) + ":");
        mLogBuilder.append(log);
        mLogBuilder.append("\n");
        mLogView.setText(mLogBuilder.toString());
        Log.d(TAG, log);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_CODE) {
            mWifiManager.startScan();
        }
    }
}
