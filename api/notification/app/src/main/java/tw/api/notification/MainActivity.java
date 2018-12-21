package tw.api.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * 参考的文档：
 * https://developer.android.com/training/notify-user/build-notification#java
 *
 * 翻墙版：
 * https://developer.android.google.cn/training/notify-user/build-notification
 */
public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "channelId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.text_hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            createChannel();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String from = intent.getStringExtra("from");
        Toast.makeText(this, from, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "name", importance);
        channel.setDescription("description");
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void showNotification() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, AlertDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent actionIntent = new Intent(this, MainActivity.class);
        actionIntent.putExtra("from", "notification");
        PendingIntent pendingActionIntent = PendingIntent.getActivity(this, 0, actionIntent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("title")
                .setContentText("contentText")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_launcher_foreground, "tongwei", pendingActionIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //除了 action button 以外，还可以添加输入框，设置在锁屏显示，以及悬浮通知

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
    }
}