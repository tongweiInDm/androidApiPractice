package com.tw.api.customeanimator;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Property;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView view = findViewById(R.id.content_tv);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final BezierLocator locator = new BezierLocator(
                        new PointF(v.getTranslationX(), v.getTranslationY()),
                        new PointF(v.getTranslationX() - 800, v.getTranslationY() - 800),
                        new PointF(v.getTranslationX(), v.getTranslationY() - 2000));

                Property<TextView, Float> property = new Property<TextView, Float>(Float.class, "BezierLocator") {
                    Float mValue;
                    public void set(TextView view, Float value) {
                        mValue = value;
                        view.setText(String.valueOf(value * 1000));
                        PointF pointF = locator.get((float) Math.pow(value, 0.4f));
                        view.setTranslationX(pointF.x);
                        view.setTranslationY(pointF.y);
                    }

                    @Override
                    public Float get(TextView view) {
                        return mValue;
                    }
                };
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, property, 0, 1);
                objectAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setTranslationX(0);
                        view.setTranslationY(0);
                    }
                });
                objectAnimator.setDuration(1700);
                objectAnimator.start();
            }
        });
    }

    //起点，终点，t，算出一个中间值，t从0到1能勾勒出一条弧线
    //


}
