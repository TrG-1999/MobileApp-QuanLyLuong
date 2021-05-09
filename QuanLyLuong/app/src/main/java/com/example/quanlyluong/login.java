package com.example.quanlyluong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class login extends AppCompatActivity {
    Button btn_login;
    TextView txt_alert;
    TextInputEditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        //Reflection
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        txt_alert = findViewById(R.id.txt_alert);
        btn_login = findViewById(R.id.btn_login);
        //Action
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                Log.d("login", user);
                Log.d("login", pass);
                if ( user.equals("admin") && pass.equals("admin")){
                    Intent intent = new Intent(getApplicationContext(), Menu.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_intent_enter,R.anim.anim_intent_exit);
                    txt_alert.setText("");
                    finish();
                }else{
                    txt_alert.setText("Xin kiểm tra lại tài khoản và mật khẩu!");
                }

            }
        });
    }
}