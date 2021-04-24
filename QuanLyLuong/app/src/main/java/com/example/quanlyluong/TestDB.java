package com.example.quanlyluong;

import android.os.Bundle;

import com.example.quanlyluong.DAO.ChamCong_Repository;
import com.example.quanlyluong.DAO.DatabaseHelper;
import com.example.quanlyluong.DAO.NV_Repository;
import com.example.quanlyluong.DAO.Phongban_Repository;
import com.example.quanlyluong.DAO.TamUng_Reposiroty;
import com.example.quanlyluong.Data.NV;
import com.example.quanlyluong.Data.PhongBan;
import com.example.quanlyluong.Data.TamUng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestDB extends AppCompatActivity {
    TextView txtTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_d_b);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtTest = findViewById(R.id.txtTest);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ChamCong_Repository tempCC = new ChamCong_Repository(TestDB.this);
                    com.example.quanlyluong.Data.ChamCong cc = new com.example.quanlyluong.Data.ChamCong();
                    cc.setMaNV(1);
                    cc.setNgayGhiSo(new Date(System.currentTimeMillis()));
                    cc.setSoNgayCong    (3);
                    tempCC.create(cc);
                    String tempStr = "";
                    List<com.example.quanlyluong.Data.ChamCong> listPB = tempCC.getAll();
                    for(com.example.quanlyluong.Data.ChamCong i : listPB) tempStr += i.getSoNgayCong() + " ";
                    txtTest.setText(tempStr);

                }
                catch (Exception e){
                    txtTest.setText("ERROR: " + e.getMessage());
                }
            }
        });
    }
}