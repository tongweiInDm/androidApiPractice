package tw.test.w.f.pendingintenttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MessageReceiver extends BroadcastReceiver {
    private static String TAG = "TestPending";

    @Override
    public void onReceive(Context context, Intent intent) {
        int which = intent.getIntExtra(MainActivity.EXTRA_WHICH, -1);
        int flags = intent.getIntExtra(MainActivity.EXTRA_FLAGS, -1);
        long ts = intent.getLongExtra(MainActivity.EXTRA_TS, -1);
        String log = "which:" + which + ", flags:" + flags + ", ts:" + ts;
        Log.e(TAG, log);
    }
}
