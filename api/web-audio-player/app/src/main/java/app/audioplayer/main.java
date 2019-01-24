package app.audioplayer;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.math.BigInteger;
import java.security.SecureRandom;

public class main extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView page = new WebView(this);
        page.getSettings().setJavaScriptEnabled(true);
        page.getSettings().setDomStorageEnabled(true);
        page.getSettings().setUseWideViewPort(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            page.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        page.loadUrl("http://10.232.74.56:8080/xm/gitlab/quick-game/test/canvas.html\n");

        page.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        setContentView(page);

        BigInteger bigInteger = new BigInteger("100000000000000000000001", 20);
        String data = bigInteger.toString(32);
        Log.d("bigIntTest", "data:" + data);
        Log.d("bigIntTest", "data.substring(0, 16):" + data.substring(0, 16));
        Log.d("bigIntTest", "data.substring(0, 16):" + data.substring(0, 16).getBytes().length);
    }
}
