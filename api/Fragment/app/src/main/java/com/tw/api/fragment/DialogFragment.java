package com.tw.api.fragment;


import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogFragment extends Fragment {


    public DialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_layout, container, false);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        TranslateAnimation animation = null;
        if (transit == FragmentTransaction.TRANSIT_FRAGMENT_OPEN) {
            if (enter) {
                animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
            } else {
                animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1,
                        Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
            }
        } else if (FragmentTransaction.TRANSIT_FRAGMENT_CLOSE == transit) {
            if (enter) {
                animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
            } else {
                animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1,
                        Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
            }
        }
        if (animation == null) {
            animation = new TranslateAnimation(0, 0, 0, 0);
        }
        animation.setDuration(300);
        return animation;
    }

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        android.animation.ObjectAnimator animator = android.animation.ObjectAnimator.ofFloat(
                getView(), View.TRANSLATION_Y, 1000, 0);
        animator.setDuration(300L);
        animator.setInterpolator(new DecelerateInterpolator());
        return animator;
    }

}
