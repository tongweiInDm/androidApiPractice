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
        boolean boolean_value_test1= typedArray.getBoolean(R.styleable.AttrView_test1,false);

        Log.d(TAG, "read custom int value of test1 " + int_value_test1);
        Log.d(TAG, "read custom boolean value of test1 " + boolean_value_test1);

        typedArray.recycle();
    }

    public ReadCustomAttrView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
