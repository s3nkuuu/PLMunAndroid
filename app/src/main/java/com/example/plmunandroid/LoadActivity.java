package com.example.plmunandroid;

import android.content.Intent;
import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_load);
        Log.d("MainActivity", "onCreate");

        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);

        //loadingAnimation = new LoadingAnimation(this);

        //showLettersWithAnimation(0);
    }

/*
    private final String[] letters = {"P", "L", "M", "U", "N"};
    private final int[] animationResIds = {
            R.anim.scale_p,
            R.anim.scale_l,
            R.anim.scale_m,
            R.anim.scale_u,
            R.anim.scale_n
    };

    private LoadingAnimation loadingAnimation;

    private void showLettersWithAnimation(int index) {
        if (index < letters.length) {
            @SuppressLint("DiscouragedApi") TextView textView = findViewById(getResources().getIdentifier("textAnimation" + letters[index], "id", getPackageName()));
            loadingAnimation.showLetter(textView);

            textView.setTranslationX(0);
            textView.setTranslationY(0);

            loadingAnimation.startLoadingAnimation(textView, animationResIds[index]);

            int delay = 300;
            textView.postDelayed(() -> {
                showLettersWithAnimation(index + 1);
            }, delay);
        } else {
            int delayAfterLastLetter = 1000;
            new android.os.Handler().postDelayed(this::startMenu, delayAfterLastLetter);
        }
    }

    private void startMenu() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
    */
}