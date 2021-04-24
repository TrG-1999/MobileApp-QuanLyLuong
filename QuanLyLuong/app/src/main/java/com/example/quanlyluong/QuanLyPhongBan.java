package com.example.quanlyluong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

public class QuanLyPhongBan extends AppCompatActivity {
    Button btnXoa, btnSua, btnThem;
    EditText etMaPB, etTenPB;
    TableLayout tablePB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_phong_ban);
//        setObject();

    }
//    private void setObject(){
//        btnXoa = findViewById(R.id.btnXoaPB);
//        btnSua = findViewById(R.id.btnSuaPB);
//        btnThem = findViewById(R.id.btnThemPB);
//        etMaPB = findViewById(R.id.etMaPB);
//        etTenPB = findViewById(R.id.etTenPB);
//        tablePB = findViewById(R.id.tablePB);
//    }


}