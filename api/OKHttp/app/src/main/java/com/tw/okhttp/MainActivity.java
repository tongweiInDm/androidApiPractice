package com.tw.okhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "OKHttpTest";

    OkHttpClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.text_hello).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mClient == null) {
                    mClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            return chain.proceed(chain.request());
                        }
                    }).build();
                    Log.d(TAG, "Create ok http ok");
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.d(TAG, fetch("https://www.mi.com"));
                        } catch (IOException e) {
                            Log.e(TAG, "Fail to fetch data", e);
                        }
                    }
                }).start();
            }
        });

    }

    String fetch(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }
}
