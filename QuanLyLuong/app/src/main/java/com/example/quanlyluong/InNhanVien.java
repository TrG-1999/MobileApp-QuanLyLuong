package com.example.quanlyluong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class InNhanVien extends AppCompatActivity {
    String maNV;
    EditText etMaNV, etTenNV, etPB, etTongLuongAll;
    TableLayout tableLuongNV;
    TextView txtTongThang, txtNgayThang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_nhan_vien);
        getId();
         Intent intent = getIntent();
        maNV = intent.getStringExtra("maNV").trim();
        getData();

    }

    private void getId(){
        etMaNV = this.findViewById(R.id.etMaNV);
        etTenNV = this.findViewById(R.id.etTenNV);
        etPB = this.findViewById(R.id.etPB);
        etTongLuongAll = this.findViewById(R.id.etTongLuongAll);
        tableLuongNV = this.findViewById(R.id.tableLuongNV);
        txtTongThang = this.findViewById(R.id.txtTongThang);
        txtNgayThang = this.findViewById(R.id.txtNgayThang);
    }
    private void getData(){
        NV_Repository repoNV = new NV_Repository(this);
        ChamCong_Repository repoCC = new ChamCong_Repository(this);
        Phongban_Repository repoPB = new Phongban_Repository(this);

        try {
            NV nv = repoNV.getById(Integer.parseInt(maNV));
            etMaNV.setText(String.valueOf(nv.getMaNV()));
            etTenNV.setText(nv.getHoTen());

            PhongBan pb = repoPB.getById(nv.getMaNV());
            etPB.setText(pb.getMaPB() + "-" + pb.getTenPB());
            int tongLuong = 0;
            List<ChamCong> listCC = repoCC.getByMaNV(maNV);
            List<String> thang = new ArrayList<>();
            for(ChamCong i : listCC){
                int tempLuong = (nv.getMucLuong() * i.getSoNgayCong())/26;
                tongLuong += tempLuong;
                TableRow row = (TableRow) LayoutInflater.from(InNhanVien.this).inflate(R.layout.table_in_nv, null);
                ((TextView) row.findViewById(R.id.ngay)).setText(String.valueOf(i.getSoNgayCong()));
                ((TextView) row.findViewById(R.id.luong)).setText(String.valueOf(tempLuong));
                String tempDate = new SimpleDateFormat("MM/yyyy").format(i.getNgayGhiSo());
                if(!thang.contains(tempDate)){
                    thang.add(tempDate);
                }
                ((TextView) row.findViewById(R.id.thoigian)).setText(tempDate);
                tableLuongNV.addView(row);
            }
            etTongLuongAll.setText(String.valueOf(tongLuong));
            txtTongThang.setText("Tổng lương của: " + thang.size());
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            //getTime() returns the current date in default time zone
            Date date = calendar.getTime();
            int day = calendar.get(Calendar.DATE);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            txtNgayThang.setText("TP.HCM, Ngày " + day + " Tháng " + month + " Năm " + year);

        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
