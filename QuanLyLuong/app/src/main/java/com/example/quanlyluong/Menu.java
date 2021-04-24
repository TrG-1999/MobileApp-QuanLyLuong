package com.example.quanlyluong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    Button btn_phongban, btn_nhanvien, btn_chamcong, btn_tamung, btn_thoat, btn_theodoiphongban, btn_thongke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setControl();
        setAction();
    }

    public void setControl() {
        btn_phongban = (Button) findViewById(R.id.btn_phongban);
        btn_nhanvien = (Button) findViewById(R.id.btn_nhanvien);
        btn_chamcong = (Button) findViewById(R.id.btn_chamcong);
        btn_tamung = (Button) findViewById(R.id.btn_tamung);
        btn_thoat = (Button) findViewById(R.id.btn_thoat);
        btn_theodoiphongban = (Button) findViewById(R.id.btn_theodoiphongban);
        btn_thongke = (Button) findViewById(R.id.btn_thongke);
    }

    public void setAction() {
        btn_phongban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, QuanLyPhongBan.class);
                startActivity(intent);
            }
        });
        btn_nhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, QuanLyNhanVien.class);
                startActivity(intent);
            }
        });
        btn_chamcong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, ChamCong.class);
                startActivity(intent);
            }
        });
        btn_tamung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, TamUng.class);
                startActivity(intent);
            }
        });
        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, login.class);
                startActivity(intent);
                finish();//stop back
            }
        });
        btn_theodoiphongban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, TheoDoiPhongBan.class);
                startActivity(intent);
            }
        });
        btn_thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, ThongKe.class);
                startActivity(intent);
            }
        });
    }
}