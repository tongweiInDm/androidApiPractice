package com.tw.customeviewandgroup.customview;

import android.content.Context;
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

        Log.d(TAG, "read custom int value of test1 " + int_value_test1);
        Log.d(TAG, "read custom int value of test2 " + int_value_test2);
        Log.d(TAG, "read custom boolean value of test1 " + boolean_value_test1);
        typedArray.recycle();

        TypedArray typedArrayMea = context.obtainStyledAttributes(attrs, R.styleable.AttrView_measure);
        float float_value_test3 = typedArrayMea.getDimension(R.styleable.AttrView_measure_test3,0);

        Log.d(TAG, "read custom float value of test3 " + float_value_test3);

        typedArrayMea.recycle();

    }

    public ReadCustomAttrView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
