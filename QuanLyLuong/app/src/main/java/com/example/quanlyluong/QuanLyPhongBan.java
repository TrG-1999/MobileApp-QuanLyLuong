package com.example.quanlyluong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlyluong.DAO.Phongban_Repository;
import com.example.quanlyluong.Data.PhongBan;

import java.util.ArrayList;
import java.util.List;

public class QuanLyPhongBan extends AppCompatActivity {
    TableLayout dataTable;
    EditText etMaPB, etTenPB;
    Button btnXoa, btnSua, btnThem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_phong_ban);
        getId();
        getData();
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
                Intent intent_cc = new Intent(QuanLyPhongBan.this, ChamCong.class);
                startActivity(intent_cc);
                finish();
                break;
            case R.id.menuNhanvien:
                Intent intent_nv = new Intent(QuanLyPhongBan.this, QuanLyNhanVien.class);
                startActivity(intent_nv);
                finish();
                break;
            case R.id.menuPhongban:
                Intent intent_pb = new Intent(QuanLyPhongBan.this, QuanLyPhongBan.class);
                startActivity(intent_pb);
                finish();
                break;
            case R.id.menuTamung:
                Intent intent_tu = new Intent(QuanLyPhongBan.this, TamUng.class);
                startActivity(intent_tu);
                finish();
                break;
            case R.id.menuOptions:
                onBackPressed();
                break;
            case R.id.menuLogout:
                Intent intent_lo = new Intent(QuanLyPhongBan.this, login.class);
                startActivity(intent_lo);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getId(){
        dataTable =(TableLayout) QuanLyPhongBan.this.findViewById(R.id.tablePB);

        etTenPB = (EditText) QuanLyPhongBan.this.findViewById(R.id.etTenPB);
        etMaPB = (EditText) QuanLyPhongBan.this.findViewById(R.id.etMaPB);

        btnThem = (Button) QuanLyPhongBan.this.findViewById(R.id.btnThemPB);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenPB = etTenPB.getText().toString().toUpperCase();
                AlertDialog.Builder buidler = new AlertDialog.Builder(QuanLyPhongBan.this);
                buidler.setTitle("XAC NHAN");
                buidler.setMessage("BAN CO MUON TAO PHONG BAN " + tenPB + "?");
                buidler.setIcon(android.R.drawable.ic_dialog_alert);
                buidler.setPositiveButton("CO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String tenPB = etTenPB.getText().toString().toUpperCase();
                            Phongban_Repository repo = new Phongban_Repository(QuanLyPhongBan.this);
                            PhongBan phongBan = new PhongBan();
                            phongBan.setMaPB(-1);
                            phongBan.setTenPB(tenPB);
                            //-------------------------------------------------------

                            //validate input data

                            //-------------------------------------------------------
                            repo.create(phongBan);
                            getData();
                        }
                        catch (Exception e){
                            Toast.makeText(QuanLyPhongBan.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
                buidler.setNegativeButton("KHONG", null);
                buidler.show();
            }
        });
        btnSua = (Button) QuanLyPhongBan.this.findViewById(R.id.btnSuaPB);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenPB = etTenPB.getText().toString().toUpperCase();
                AlertDialog.Builder buidler = new AlertDialog.Builder(QuanLyPhongBan.this);
                buidler.setTitle("XAC NHAN");
                buidler.setMessage("BAN CO MUON SUA THANH " + tenPB + "?");
                buidler.setIcon(android.R.drawable.ic_dialog_alert);
                buidler.setPositiveButton("CO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String tenPB = etTenPB.getText().toString().toUpperCase();
                            Phongban_Repository repo = new Phongban_Repository(QuanLyPhongBan.this);
                            int id = Integer.parseInt(String.valueOf(etMaPB.getText()));
                            PhongBan phongBan = repo.getById(id);
                            phongBan.setTenPB(tenPB);
                            //-------------------------------------------------------

                            //validate input data

                            //-------------------------------------------------------
                            repo.update(phongBan);
                            getData();
                        }
                        catch (Exception e){
                            Toast.makeText(QuanLyPhongBan.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
                buidler.setNegativeButton("KHONG", null);
                buidler.show();
            }
        });
        btnXoa = (Button) QuanLyPhongBan.this.findViewById(R.id.btnXoaPB);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenPB = etTenPB.getText().toString().toUpperCase();
                AlertDialog.Builder buidler = new AlertDialog.Builder(QuanLyPhongBan.this);
                buidler.setTitle("XAC NHAN");
                buidler.setMessage("BAN CO MUON XOA PHONG BAN " + tenPB + "?");
                buidler.setIcon(android.R.drawable.ic_dialog_alert);
                buidler.setPositiveButton("CO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            int id = Integer.parseInt(String.valueOf(etMaPB.getText()));
                            Phongban_Repository repo = new Phongban_Repository(QuanLyPhongBan.this);
                            repo.deleteById(id);
                            getData();
                        }
                        catch (Exception e){
                            Toast.makeText(QuanLyPhongBan.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
                buidler.setNegativeButton("KHONG", null);
                buidler.show();
            }
        });
    }

    private void getData(){
        try {
            dataTable.removeAllViews();
            Phongban_Repository repo = new Phongban_Repository(this);
            List<PhongBan> data = repo.getAll();
            for( PhongBan i : data){
                TableRow row = (TableRow) LayoutInflater.from(QuanLyPhongBan.this).inflate(R.layout.table_row_pb, null);
                ((TextView)row.findViewById(R.id.tenPB)).setText(i.getTenPB());
                ((TextView)row.findViewById(R.id.maPB)).setText((String.valueOf(i.getMaPB())));
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String maPB = (String) ((TextView)row.findViewById(R.id.maPB)).getText();
                        String tenPB = (String) ((TextView)row.findViewById(R.id.tenPB)).getText();
                        etMaPB.setText(maPB);
                        etTenPB.setText(tenPB);
                    }
                });
                dataTable.addView(row);
            }
            dataTable.requestLayout();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}