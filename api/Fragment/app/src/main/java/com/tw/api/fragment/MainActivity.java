package com.tw.api.fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private DialogFragment mDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogFragment = new DialogFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        // 一定要在replace() / add() 之前调用，否则无效
//                        .setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out)
                        .replace(R.id.dialog_container, mDialogFragment)
                        .commitAllowingStateLoss();
            }
        });

        findViewById(R.id.remove_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
//                        .setCustomAnimations(R.anim.slide_left_out, R.anim.slide_right_out)
                        .remove(mDialogFragment)
                        .commitAllowingStateLoss();
            }
        });
    }
}