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
import java.util.ArrayList;
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
//                    Phongban_Repository pvRepo = new Phongban_Repository(TestDB.this);
//                    PhongBan tempPB = new PhongBan(1, "ABC");
//                    pvRepo.create(tempPB);

//                    NV_Repository nvRepo = new NV_Repository(TestDB.this);
//                    NV tempNV = new NV();
//                    tempNV.setMaNV(0);
//                    tempNV.setMucLuong(100);
//                    tempNV.setMaPB(2);
//                    tempNV.setNgaySinh(new Date(System.currentTimeMillis()));
//                    tempNV.setHoTen("CBE");
//                    nvRepo.create(tempNV);

//                    ChamCong_Repository tempCC = new ChamCong_Repository(TestDB.this);
//                    com.example.quanlyluong.Data.ChamCong cc = new com.example.quanlyluong.Data.ChamCong();
//                    cc.setMaNV(1);
//                    cc.setNgayGhiSo(new Date(System.currentTimeMillis()));
//                    cc.setSoNgayCong(3);
//                    tempCC.create(cc);


                    String tempStr = "";

//                    List<com.example.quanlyluong.Data.PhongBan> listPB = pvRepo.getAll();
//                    for(com.example.quanlyluong.Data.PhongBan i : listPB) tempStr += i.toString() + "\n";
//
//                    List<com.example.quanlyluong.Data.NV> listNV = nvRepo.getAll();
//                    for(com.example.quanlyluong.Data.NV i : listNV) tempStr += i.toString() + "\n";
//                    Date date = new SimpleDateFormat("MMMM dd yyyy HH:mm:ss").parse("Apr 25 2021 " +
//                            "02:18:41");
//                    com.example.quanlyluong.Data.ChamCong cc = tempCC.getByIdAndDate(1, date);
//                    List<com.example.quanlyluong.Data.ChamCong> listCC = tempCC.getAll();
//                    for(com.example.quanlyluong.Data.ChamCong i : listCC) tempStr += i.toString() + "\n";
//
//                    tempCC.deleteByObject(cc);
//                    tempStr += "\n\n";
//                    listCC = tempCC.getAll();
//                    for(com.example.quanlyluong.Data.ChamCong i : listCC) tempStr += i.toString() + "\n";

                    TamUng_Reposiroty tuRepo = new TamUng_Reposiroty(TestDB.this);
//                    TamUng tu = new TamUng();
//                    tu.setSoPhieu(-1);
//                    tu.setMaNV(1);
//                    tu.setSoTien(190);
//                    tu.setNgay(new Date(System.currentTimeMillis()));
//                    tuRepo.create(tu);
                    List<TamUng> listTU = tuRepo.getAll();
                    for(TamUng i : listTU) tempStr += i.toString() + "\n";
                    tempStr += "\n\n";
                    tuRepo.deleteById(-1);
                    TamUng tu = tuRepo.getById(1);
                    tu.setSoTien(111111);
                    tuRepo.update(tu);
                    listTU = tuRepo.getAll();
                    for(TamUng i : listTU) tempStr += i.toString() + "\n";
                    txtTest.setText(tempStr);

                }
                catch (Exception e){
                    txtTest.setText("ERROR: " + e.getMessage());
                }
            }
        });
    }
}