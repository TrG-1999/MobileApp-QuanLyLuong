package com.example.quanlyluong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    Animation slide_enter, slide_txtheader;
    ImageView imgmain;
    TextView txt_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        slide_enter = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_logomain);
        slide_txtheader = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_txt_header_login);
        imgmain = MainActivity.this.findViewById(R.id.imgmain);
        txt_header = MainActivity.this.findViewById(R.id.txt_header);

        Field field = null;
        try {
            field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
        } catch (NoSuchFieldException noSuchFieldException) {
            noSuchFieldException.printStackTrace();
        }
        field.setAccessible(true);
        try {
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        txt_header.setAnimation(slide_txtheader);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_intent_enter,R.anim.anim_intent_exit);
                finish();
            }
        }, 2000);
    }
}