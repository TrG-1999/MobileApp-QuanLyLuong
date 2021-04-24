package com.example.quanlyluong;

import android.os.Bundle;

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
                    Phongban_Repository temp = new Phongban_Repository(TestDB.this);
                    temp.create(new PhongBan(0, "Hello"));
                    temp.update(new PhongBan(6,"Hello con cac"));
//                    temp.update(new Phong)
                    List<PhongBan> list = temp.getAll();
                    String tempStr = "";
                    for(PhongBan i : list) tempStr += i.getTenPB() + " ";
                    txtTest.setText(tempStr);


//                    Snackbar.make(view, tempStr, Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
                }
                catch (Exception e){
                    txtTest.setText(e.getMessage());
                }
            }
        });
    }
}