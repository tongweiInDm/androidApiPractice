package com.tw.customeviewandgroup.customview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.tw.customeviewandgroup.R;

public class ReadCustomAttrView extends View {
    private static final String TAG = "ReadCustomAttr";

    public ReadCustomAttrView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AttrView);
        int int_value_test1= typedArray.getInt(R.styleable.AttrView_test1,0);
        int int_value_test2= typedArray.getInt(R.styleable.AttrView_test2,0);
        boolean boolean_value_test1= typedArray.getBoolean(R.styleable.AttrView_test1,false);

        Log.d(TAG, "read custom int value of test1 " + int_value_test1 + " attr count:" + attrs.getAttributeCount());
        Log.d(TAG, "read custom int value of test2 " + int_value_test2);
        Log.d(TAG, "read custom boolean value of test1 " + boolean_value_test1);
        typedArray.recycle();


        //get test3 from merged theme,
        // 这里想验证的结论是：通过theme.obtainStyledAttributes(attrs 拿到的是主题和activity_main.xml中的重写合并后的结果，而不是主题中定义的原始值
        //test3在主题和 activity_main.xml中都有定义，而test4却只在主题中有定义
        //如果拿到的merge后端结果，test3的预期值是50dp，如果是主题中的原始值，test3的预期值是78dp
        //test4是对照组，没有被重写过，无论是那种情况，都拿到78dp
        //实验的结果，test3拿到的是50dp，test4是78dp，证明确实是覆写后的属性集合
        final Resources.Theme theme = context.getTheme();
        TypedArray a = theme.obtainStyledAttributes(attrs, R.styleable.AttrView_measure, 0, 0);
        float test3FromMergedTheme = a.getDimension(R.styleable.AttrView_measure_test3, -1);
        float test4FromMergedTheme = a.getDimension(R.styleable.AttrView_measure_test4, -1);

        //get test3 from context
        TypedArray typedArrayMea = context.obtainStyledAttributes(attrs, R.styleable.AttrView_measure);
        float float_value_test3 = typedArrayMea.getDimension(R.styleable.AttrView_measure_test3,0);
        float float_value_test4 = typedArrayMea.getDimension(R.styleable.AttrView_measure_test4,0);

        Log.d(TAG, "read custom float value of test3 " + float_value_test3 + ", test3FromMergedTheme:" + test3FromMergedTheme);
        Log.d(TAG, "read custom float value of test4 " + float_value_test4 + ", test4FromMergedTheme:" + test4FromMergedTheme);

        typedArrayMea.recycle();

    }

    public ReadCustomAttrView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
