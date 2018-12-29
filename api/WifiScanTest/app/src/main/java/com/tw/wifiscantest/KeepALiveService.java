package com.tw.wifiscantest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class KeepALiveService extends Service {
    private static final String TAG = "KeepALiveService";
    private static final String CHANNEL_ID = "channelForKeepALive";


    public KeepALiveService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "channelForKeepALive", importance);
                channel.setDescription("just for keep alive.");
                notificationManager.createNotificationChannel(channel);
            }

            // 不知道为什么在android O上使用NotificationCompat.Builder构建的notification会弹不出
            // 目前先改成直接使用Notification.Builder了
            notification = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText("a test for power usage when scan wifi")
                    .setAutoCancel(false).build();
        } else {
            notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText("a test for power usage when scan wifi")
                    .setAutoCancel(false)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT).build();
        }

        startForeground(1, notification);
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        Log.i(TAG, "onDestroy");
    }
}