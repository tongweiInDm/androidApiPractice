package tw.test.w.f.pendingintenttest;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

/**
 * 关于PendingIntent的flags
 * https://www.jianshu.com/p/20ad37d1418b
 */
public class MainActivity extends AppCompatActivity {
    private static String TAG = "TestPending";

    public static final String EXTRA_WHICH = "which";
    public static final String EXTRA_FLAGS = "flags";
    public static final String EXTRA_TS = "ts";

    private PendingIntent[] pendingIntents = new PendingIntent[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CheckBox checkBox = findViewById(R.id.cb_which);
        findViewById(R.id.tv_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首次调用的时候创建，二次调用的时候，只是取回，Intent参数被忽略
                record(checkBox.isChecked() ? 0 : 1, 1, 0);
            }
        });
        findViewById(R.id.tv_oneshot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首次调用的时候创建，二次调用的时候，只是取回，Intent参数被忽略，PendingIntent的持有只能send一次，之后会失败
                record(checkBox.isChecked() ? 0 : 1, 1, PendingIntent.FLAG_ONE_SHOT);
            }
        });
        findViewById(R.id.tv_update_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新不取消之前的
                record(checkBox.isChecked() ? 0 : 1, 1, PendingIntent.FLAG_UPDATE_CURRENT);
            }
        });
        findViewById(R.id.tv_cancel_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新同时取消之前的PendingIntent(之前的PendingIntent在执行send时会失败)
                record(checkBox.isChecked() ? 0 : 1, 1, PendingIntent.FLAG_CANCEL_CURRENT);
            }
        });

        findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PendingIntent pendingIntent = pendingIntents[checkBox.isChecked() ? 0 : 1];
                    if (pendingIntent != null) {
                        pendingIntent.send();
                        Log.d(TAG, "Send success.");
                    } else {
                        Log.d(TAG, "Pending intent is null.");
                    }
                } catch (PendingIntent.CanceledException e) {
                    Log.e(TAG, "Fail to send.", e);
                }
            }
        });
    }

    private void record(int which, int reqCode, int flags) {
        Intent intent = new Intent(this, MessageReceiver.class);
        intent.putExtra(EXTRA_WHICH, which);
        intent.putExtra(EXTRA_FLAGS, flags);
        long ts = System.currentTimeMillis();
        intent.putExtra(EXTRA_TS, ts);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, reqCode, intent, flags);
        Log.d(TAG, "record which:" + which + ", reqCode:" + reqCode + ", flags:" + flags + ", ts:" + ts + ", pendingIntent:" + pendingIntent);
        pendingIntents[which] = pendingIntent;
    }
}
