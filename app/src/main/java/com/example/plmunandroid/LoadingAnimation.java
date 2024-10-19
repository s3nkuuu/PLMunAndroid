package com.example.plmunandroid;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class LoadingAnimation {

    private final Context context;

    public LoadingAnimation(Context context) {
        this.context = context;
    }

    public void startLoadingAnimation(TextView textView, int animationResId) {
        Animation animation = AnimationUtils.loadAnimation(context, animationResId);
        animation.setFillAfter(true);
        textView.startAnimation(animation);
    }

    public void showLetter(TextView textView) {
        textView.setVisibility(View.VISIBLE);
    }

    public void hideLetter(TextView textView) {
        textView.setVisibility(View.GONE);
    }
}
