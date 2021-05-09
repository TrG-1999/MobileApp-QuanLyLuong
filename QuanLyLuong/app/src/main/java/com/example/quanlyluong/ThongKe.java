package com.example.quanlyluong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlyluong.DAO.NV_Repository;
import com.example.quanlyluong.DAO.Phongban_Repository;
import com.example.quanlyluong.Data.NV;
import com.example.quanlyluong.Data.NV_ThongKe;
import com.example.quanlyluong.Data.PhongBan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThongKe extends AppCompatActivity {
    Spinner spinnerNam, spinnerMaPB, spinnerThang;
    TableLayout dataTable;
    TextView txtSoNV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        getID();
        getSpinnerData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuChamcong:
                Intent intent_cc = new Intent(ThongKe.this, ChamCong.class);
                startActivity(intent_cc);
                finish();
                break;
            case R.id.menuNhanvien:
                Intent intent_nv = new Intent(ThongKe.this, QuanLyNhanVien.class);
                startActivity(intent_nv);
                finish();
                break;
            case R.id.menuPhongban:
                Intent intent_pb = new Intent(ThongKe.this, QuanLyPhongBan.class);
                startActivity(intent_pb);
                finish();
                break;
            case R.id.menuTamung:
                Intent intent_tu = new Intent(ThongKe.this, TamUng.class);
                startActivity(intent_tu);
                finish();
                break;
            case R.id.menuOptions:
                onBackPressed();
                break;
            case R.id.menuLogout:
                Intent intent_lo = new Intent(ThongKe.this, login.class);
                startActivity(intent_lo);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getID(){
        dataTable = ThongKe.this.findViewById(R.id.tableThongKe);
        txtSoNV = ThongKe.this.findViewById(R.id.txtViewTongNV);


        spinnerMaPB = ThongKe.this.findViewById(R.id.spinnerMaPB);
        spinnerMaPB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerNam = ThongKe.this.findViewById(R.id.spinnerNam);
        spinnerNam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerThang = ThongKe.this.findViewById(R.id.spinnerThang);
        spinnerThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void getData(){
        try {
            String[] tempStr = spinnerMaPB.getSelectedItem().toString().split(" ");
            String maPB = tempStr[0];
            String nam = spinnerNam.getSelectedItem().toString();
            String thang = spinnerThang.getSelectedItem().toString();
            if(maPB.isEmpty() || nam.isEmpty() || thang.isEmpty()) return;
            dataTable.removeAllViews();
            NV_Repository repo = new NV_Repository(this);
            List<NV_ThongKe> data = repo.thongKe(maPB, thang, nam);
            for( NV_ThongKe i : data){
                TableRow row = (TableRow) LayoutInflater.from(ThongKe.this).inflate(R.layout.table_row_thongke, null);
                ((TextView)row.findViewById(R.id.maNV)).setText(String.valueOf(i.getMaNV()));
                ((TextView)row.findViewById(R.id.tenNV)).setText(i.getTenNV());
                ((TextView)row.findViewById(R.id.maPB)).setText(String.valueOf(i.getMaPB()));
                ((TextView)row.findViewById(R.id.luong)).setText(String.valueOf(i.getTongLuong()));
                dataTable.addView(row);
            }
            txtSoNV.setText("Tổng NV: " + data.size());
            dataTable.requestLayout();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void getSpinnerData(){
        try {
            Phongban_Repository repo = new Phongban_Repository(ThongKe.this);
            List<PhongBan> data = repo.getAll();
            List<String> dataList = new ArrayList<>();
            for(PhongBan i : data){
                dataList.add(String.valueOf(i.getMaPB()) + " - " + i.getTenPB());
            }
            ArrayAdapter<String> tempData = new ArrayAdapter<String>(ThongKe.this, android.R.layout.simple_spinner_dropdown_item, dataList);
            tempData.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerMaPB.setAdapter(tempData);

            List<String> dataThang = new ArrayList<>();
            for(int i = 1; i <= 12; i++ ){
                dataThang.add(String.valueOf(i));
            }
            ArrayAdapter<String> tempDataThang = new ArrayAdapter<String>(ThongKe.this, android.R.layout.simple_spinner_dropdown_item, dataThang);
            tempDataThang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerThang.setAdapter(tempDataThang);

            List<String> dataNam = new ArrayList<>();
            for(int i = 2015; i <= Calendar.getInstance().get(Calendar.YEAR); i++){
                dataNam.add(String.valueOf(i));
            }
            ArrayAdapter<String> tempDataNam = new ArrayAdapter<String>(ThongKe.this, android.R.layout.simple_spinner_dropdown_item, dataNam);
            tempDataNam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerNam.setAdapter(tempDataNam);
        }
        catch (Exception e){
            Toast.makeText(ThongKe.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}