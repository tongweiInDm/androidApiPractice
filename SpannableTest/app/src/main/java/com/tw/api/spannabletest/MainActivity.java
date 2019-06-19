package com.tw.api.spannabletest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();
    }

    private void test() {

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder("");
        for (int i = 0; i < 10; i++) {
            String text = String.valueOf(i);
            stringBuilder.append(text);

            int length = stringBuilder.length();
            int textLength = text.length();

            ForegroundColorSpan span = new ForegroundColorSpan(i % 2 == 0 ? Color.YELLOW : Color.RED);
            stringBuilder.setSpan(span, length - textLength, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        stringBuilder.append("tongwei");
        ForegroundColorSpan span = new ForegroundColorSpan(Color.GREEN);
        stringBuilder.setSpan(span, stringBuilder.length() - "tongwei".length(), stringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        this.<TextView>findViewById(R.id.tw_text).setText(stringBuilder);

    }
}