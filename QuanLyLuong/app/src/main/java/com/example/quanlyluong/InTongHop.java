package com.example.quanlyluong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlyluong.DAO.ChamCong_Repository;
import com.example.quanlyluong.DAO.NV_Repository;
import com.example.quanlyluong.DAO.Phongban_Repository;
import com.example.quanlyluong.Data.ChamCong;
import com.example.quanlyluong.Data.NV;
import com.example.quanlyluong.Data.PhongBan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class InTongHop extends AppCompatActivity {

    LinearLayout layoutAddOn;
    String maPB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_tong_hop);
        layoutAddOn = this.findViewById(R.id.layoutAddOn);
        Intent intent = getIntent();
        maPB = intent.getStringExtra("maPB").trim();
        getData();
    }
    private void getData(){
        try {
            NV_Repository repoNV = new NV_Repository(this);
            ChamCong_Repository repoCC = new ChamCong_Repository(this);
            Phongban_Repository repoPB = new Phongban_Repository(this);

            List<NV> listNV = repoNV.getByMaPB(maPB);
            for(NV nv : listNV){
                LinearLayout tempLayout = (LinearLayout) LayoutInflater.from(InTongHop.this).inflate(R.layout.layout_in_tonghop, null);
                ((TextView) tempLayout.findViewById(R.id.etMaNV)).setText(String.valueOf(nv.getMaNV()));
                ((TextView) tempLayout.findViewById(R.id.etTenNV)).setText(nv.getHoTen());

                PhongBan pb = repoPB.getById(nv.getMaPB());
                ((TextView) tempLayout.findViewById(R.id.etPB)).setText(pb.getMaPB() + "-" + pb.getTenPB());
                int tongLuong = 0;
                List<com.example.quanlyluong.Data.ChamCong> listCC = repoCC.getByMaNV(String.valueOf(nv.getMaNV()));
                List<String> thang = new ArrayList<>();
                for(ChamCong cc : listCC){
                    int tempLuong = (nv.getMucLuong() * cc.getSoNgayCong())/26;
                    tongLuong += tempLuong;
                    TableRow row = (TableRow) LayoutInflater.from(InTongHop.this).inflate(R.layout.table_in_nv, null);
                    ((TextView) row.findViewById(R.id.ngay)).setText(String.valueOf(cc.getSoNgayCong()));
                    ((TextView) row.findViewById(R.id.luong)).setText(String.valueOf(tempLuong));
                    String tempDate = new SimpleDateFormat("MM/yyyy").format(cc.getNgayGhiSo());
                    if(!thang.contains(tempDate)){
                        thang.add(tempDate);
                    }
                    ((TextView) row.findViewById(R.id.thoigian)).setText(tempDate);
                    ((TableLayout) tempLayout.findViewById(R.id.tableLuongNV)).addView(row);
                }
                ((TextView) tempLayout.findViewById(R.id.etTongLuongAll)).setText(String.valueOf(tongLuong));
                ((TextView) tempLayout.findViewById(R.id.txtTongThang)).setText("Tổng lương của: " + thang.size());

                layoutAddOn.addView(tempLayout);
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}