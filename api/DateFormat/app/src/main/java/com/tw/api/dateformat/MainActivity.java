package com.tw.api.dateformat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import java.util.Date;

/**
 * 关于按照格式输出日期的方法
 * 参考来自：https://www.cnblogs.com/tendoasan/p/5257509.html
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.output_tv);

        textView.setText(DateFormat.format("mm:ss", new Date(30 * 1000)));
    }
}
