package com.example.quanlyluong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlyluong.DAO.ChamCong_Repository;
import com.example.quanlyluong.DAO.NV_Repository;
import com.example.quanlyluong.DAO.Phongban_Repository;
import com.example.quanlyluong.Data.NV;
import com.example.quanlyluong.Data.PhongBan;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TheoDoiPhongBan extends AppCompatActivity {
    EditText txtTongNV, txtSoThang, txtTongLuongAll;
    Spinner spinnerTenPB, spinnerMaNV;
    TableLayout tableLuongNV, tableNV;
    Button btnIn;
    RadioButton radioNV, radioTH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theo_doi_phong_ban);
        getId();
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
                Intent intent_cc = new Intent(TheoDoiPhongBan.this, ChamCong.class);
                startActivity(intent_cc);
                finish();
                break;
            case R.id.menuNhanvien:
                Intent intent_nv = new Intent(TheoDoiPhongBan.this, QuanLyNhanVien.class);
                startActivity(intent_nv);
                finish();
                break;
            case R.id.menuPhongban:
                Intent intent_pb = new Intent(TheoDoiPhongBan.this, QuanLyPhongBan.class);
                startActivity(intent_pb);
                finish();
                break;
            case R.id.menuTamung:
                Intent intent_tu = new Intent(TheoDoiPhongBan.this, TamUng.class);
                startActivity(intent_tu);
                finish();
                break;
            case R.id.menuOptions:
                onBackPressed();
                break;
            case R.id.menuLogout:
                Intent intent_lo = new Intent(TheoDoiPhongBan.this, login.class);
                startActivity(intent_lo);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getId(){
        txtTongNV = TheoDoiPhongBan.this.findViewById(R.id.txtTongNV);
        txtSoThang = TheoDoiPhongBan.this.findViewById(R.id.txtSoThang);
        txtTongLuongAll = TheoDoiPhongBan.this.findViewById(R.id.txtTongLuongAll);
        spinnerTenPB = TheoDoiPhongBan.this.findViewById(R.id.spinnerTenPB);
        spinnerMaNV = TheoDoiPhongBan.this.findViewById(R.id.spinnerMaNV);
        tableLuongNV = TheoDoiPhongBan.this.findViewById(R.id.tableLuongNV);
        tableNV = TheoDoiPhongBan.this.findViewById(R.id.tableNV);
        btnIn = TheoDoiPhongBan.this.findViewById(R.id.btnIn);
        radioNV = TheoDoiPhongBan.this.findViewById(R.id.radioNV);
        radioTH = TheoDoiPhongBan.this.findViewById(R.id.radioTH);
        spinnerTenPB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    NV_Repository repo = new NV_Repository(TheoDoiPhongBan.this);
                    String[] tempStr = spinnerTenPB.getSelectedItem().toString().split("-");
                    String maPB = tempStr[0];
                    List<NV> listNV = repo.getByMaPB(maPB);
                    List<String> resultNV = new ArrayList<>();
                    List<byte[]> imageList = new ArrayList<>();
                    for(NV i : listNV){
                        resultNV.add(i.getMaNV() + " - " + i.getHoTen());
                        imageList.add(i.getPhoto());
                    }
                    CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(), imageList, resultNV);
                    spinnerMaNV.setAdapter(customAdapter);
                    txtTongNV.setText(String.valueOf(listNV.size()));
                    // do du lieu len table
                    tableNV.removeAllViews();
                    for(NV i : listNV){
                        TableRow row = (TableRow) LayoutInflater.from(TheoDoiPhongBan.this).inflate(R.layout.table_row_nv_theodoi, null );
                        ((TextView) row.findViewById(R.id.maNV)).setText(String.valueOf(i.getMaNV()));
                        ((TextView) row.findViewById(R.id.hotenNV)).setText(i.getHoTen());
                        ((TextView) row.findViewById(R.id.luong)).setText(String.valueOf(i.getMucLuong()));
                        tableNV.addView(row);
                    }
                    tableNV.requestLayout();

                }
                catch (Exception e){
                    System.out.println("Failed " +  e);
                    Toast.makeText(TheoDoiPhongBan.this, e.getMessage(), Toast.LENGTH_SHORT);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
        spinnerMaNV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    NV_Repository repoNV = new NV_Repository(TheoDoiPhongBan.this);
                    ChamCong_Repository repo = new ChamCong_Repository(TheoDoiPhongBan.this);
                    String maNV = Long.toString(spinnerMaNV.getSelectedItemId());
                    maNV = maNV.trim();
                    List<com.example.quanlyluong.Data.ChamCong> listCC = repo.getByMaNV(maNV);
                    NV tempNV = repoNV.getById(Integer.parseInt(maNV));
                    int tongLuong = 0;
                    List<String> thang = new ArrayList<>();
                    tableLuongNV.removeAllViews();
                    for(com.example.quanlyluong.Data.ChamCong i : listCC){
                        TableRow row = (TableRow) LayoutInflater.from(TheoDoiPhongBan.this).inflate(R.layout.table_row_luongnv_theodoi, null);
                        ((TextView) row.findViewById(R.id.maNV)).setText(String.valueOf(i.getMaNV()));
                        String tempDate = new SimpleDateFormat("dd/MM/yyyy").format(i.getNgayGhiSo());
                        String tempMonth = new SimpleDateFormat("MM/yyyy").format(i.getNgayGhiSo());
                        if(!thang.contains(tempMonth)){
                            thang.add(tempMonth);
                        }
                        ((TextView) row.findViewById(R.id.thoigian)).setText(tempDate);
                        ((TextView) row.findViewById(R.id.ngay)).setText(String.valueOf(i.getSoNgayCong()));
                        int tempLuongTable = (tempNV.getMucLuong() * i.getSoNgayCong())/26;
                        ((TextView) row.findViewById(R.id.luong)).setText(String.valueOf(tempLuongTable));
                        tongLuong += tempLuongTable;
                        tableLuongNV.addView(row);
                    }
                    tableLuongNV.requestLayout();

                    txtTongLuongAll.setText(String.valueOf(tongLuong));
                    txtSoThang.setText(String.valueOf(thang.size()));
                }
                catch (Exception e){
                    Toast.makeText(TheoDoiPhongBan.this, e.getMessage(), Toast.LENGTH_SHORT);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
        btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioNV.isChecked()){
                    String maNV = Long.toString(spinnerMaNV.getSelectedItemId());
                    //activity in NV
                    Intent intent_nv = new Intent(TheoDoiPhongBan.this, InNhanVien.class);
                    intent_nv.putExtra("maNV", maNV);
                    startActivity(intent_nv);
                    finish();
                }
                else if(radioTH.isChecked()){
                    String[] tempStr = spinnerTenPB.getSelectedItem().toString().split("-");
                    String maPB = tempStr[0];
                    //activity in TH
                    Intent intent_th = new Intent(TheoDoiPhongBan.this, InTongHop.class);
                    intent_th.putExtra("maPB", maPB);
                    startActivity(intent_th);
                    finish();
                }
            }
        });
    }
    private void getSpinnerData(){
        try {
            Phongban_Repository repoPB = new Phongban_Repository(TheoDoiPhongBan.this);
            List<PhongBan> listPB = repoPB.getAll();
            List<String> resultPB = new ArrayList<>();
            for(PhongBan i : listPB){
                resultPB.add(String.valueOf(i.getMaPB()) + " - " + i.getTenPB());
            }
            ArrayAdapter<String> tempData = new ArrayAdapter<String>(TheoDoiPhongBan.this, android.R.layout.simple_spinner_dropdown_item, resultPB);
            tempData.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTenPB.setAdapter(tempData);
        }
        catch (Exception e){
            Toast.makeText(TheoDoiPhongBan.this, e.getMessage(), Toast.LENGTH_SHORT);
        }
    }

}