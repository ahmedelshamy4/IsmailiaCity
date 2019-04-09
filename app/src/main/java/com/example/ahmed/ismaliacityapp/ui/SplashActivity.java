package com.example.ahmed.ismaliacityapp.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.ahmed.ismaliacityapp.Onborad.BounceLoadingView;
import com.example.ahmed.ismaliacityapp.Onborad.OnBoardActivity;
import com.example.ahmed.ismaliacityapp.R;

public class SplashActivity extends AppCompatActivity {
    protected boolean _active = true;
    protected int _splashTime = 5000;
    private BounceLoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenDesign();
        setContentView(R.layout.activity_splash);

        loadingView = findViewById(R.id.loadingView);
        loadingView.addBitmap(R.drawable.bus);
        loadingView.addBitmap(R.drawable.car);
        loadingView.addBitmap(R.drawable.car1);
        loadingView.addBitmap(R.drawable.school);
        loadingView.setShadowColor(Color.LTGRAY);
        loadingView.setDuration(700);
        loadingView.start();
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                            if (waited > _splashTime - 200) {

                            }
                        }
                    }
                } catch (Exception e) {

                } finally {
                    {
                        startActivity(new Intent(SplashActivity.this, OnBoardActivity.class));

                    }

                    finish();
                }
            }

            ;
        };
        splashTread.start();
    }

    private void ScreenDesign() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            boolean shouldChangeStatusBarTintToDark = true;

            if (shouldChangeStatusBarTintToDark) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                // We want to change tint color to white again.
                // You can also record the flags in advance so that you can turn UI back completely if
                // you have set other flags before, such as translucent or full screen.
                decor.setSystemUiVisibility(0);

            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkL));
            }
        }
    }
}

