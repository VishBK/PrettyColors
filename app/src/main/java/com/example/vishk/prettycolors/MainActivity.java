package com.example.vishk.prettycolors;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private FrameLayout mMainFrame;
    private BottomNavigationView mMainNav;

    private WheelFragment wheelFragment;
    private ColorsFragment colorsFragment;
    private CameraFragment cameraFragment;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mTextMessage = findViewById(R.id.message);
        mMainFrame = findViewById(R.id.main_frame);
        mMainNav = findViewById(R.id.main_nav);

        wheelFragment = new WheelFragment();
        colorsFragment = new ColorsFragment();
        cameraFragment = new CameraFragment();

        setFragment(colorsFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        //mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(wheelFragment);
                        //mTextMessage.setText(R.string.title_home);
                        return true;
                    case R.id.navigation_dashboard:
                        //mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(colorsFragment);
                        //mTextMessage.setText(R.string.title_dashboard);
                        return true;
                    case R.id.navigation_camera:
                        //mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(cameraFragment);
                        //mTextMessage.setText(R.string.title_camera);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

}
