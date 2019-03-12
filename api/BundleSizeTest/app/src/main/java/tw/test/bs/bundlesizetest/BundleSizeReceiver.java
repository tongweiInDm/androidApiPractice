package tw.test.bs.bundlesizetest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class BundleSizeReceiver extends BroadcastReceiver {
    private static final String TAG = "BundleSizeTest";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getBundleExtra("data");
        int code = bundle.getInt("code");
        String data = bundle.getString("data");
        List<ParcelFileDescriptor> fileDescriptors = bundle.getParcelableArrayList("files");
        Log.d(TAG, "code:" + code);
        Log.d(TAG, "data.length:" + data.length() + ", first 10 chars:" + data.substring(0, 10));
        Log.d(TAG, "fileDescriptors.size():" + fileDescriptors.size());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileDescriptors.get(0).getFileDescriptor()));
        try {
            Log.d(TAG, "file[0]'s content:" + bufferedReader.readLine());
        } catch (IOException e) {
            Log.e(TAG, "Fail to read file[0]", e);
        }
    }
}
