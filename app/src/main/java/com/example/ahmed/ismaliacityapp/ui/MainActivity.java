package com.example.ahmed.ismaliacityapp.ui;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.example.ahmed.ismaliacityapp.R;
import com.example.ahmed.ismaliacityapp.fragment.CategoryFragment;
import com.example.ahmed.ismaliacityapp.fragment.HelpFragment;
import com.example.ahmed.ismaliacityapp.fragment.HomeFragment;
import com.example.ahmed.ismaliacityapp.fragment.MapsFragment;
import com.example.ahmed.ismaliacityapp.fragment.OptionsFragment;
import com.roughike.bottombar.BottomBar;

import org.aviran.cookiebar2.CookieBar;

public class MainActivity extends AppCompatActivity {
    private BottomBar bottomNavigationView;
    private CookieBar backCookieBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnTabSelectListener(this::handleBottomNavClicked);

    }

    private void handleBottomNavClicked(int item) {
        switch (item) {
            case R.id.action_home:
                setTheme(R.style.AppTheme_Home);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.homePrimaryDark));
                    getWindow().setNavigationBarColor(getResources().getColor(R.color.homePrimaryDark));
                }

                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.main_container, new HomeFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.action_categorys:
                setTheme(R.style.AppTheme_Home);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.categoryPrimaryDark));
                    getWindow().setNavigationBarColor(getResources().getColor(R.color.categoryPrimaryDark));
                }
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.main_container, new CategoryFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.action_maps:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.mapsPrimaryDark));
                    getWindow().setNavigationBarColor(getResources().getColor(R.color.mapsPrimaryDark));
                }
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.main_container, new MapsFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.action_options:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.optionsPrimaryDark));
                    getWindow().setNavigationBarColor(getResources().getColor(R.color.optionsPrimaryDark));
                }
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.main_container, new OptionsFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.action_help:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.helpPrimaryDark));
                    getWindow().setNavigationBarColor(getResources().getColor(R.color.helpPrimaryDark));
                }
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.main_container, new HelpFragment())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        if (backCookieBar != null) {
            finish();
        } else {
            backCookieBar = CookieBar.build(MainActivity.this)
                    .setLayoutGravity(Gravity.BOTTOM)
                    .setTitle("Hit the back button again to quit.")
                    .setDuration(7000)
                    .show();
        }
    }
}
