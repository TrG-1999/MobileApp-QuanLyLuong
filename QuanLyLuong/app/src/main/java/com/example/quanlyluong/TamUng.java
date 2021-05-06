package com.example.quanlyluong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlyluong.DAO.NV_Repository;
import com.example.quanlyluong.DAO.Phongban_Repository;
import com.example.quanlyluong.DAO.TamUng_Reposiroty;
import com.example.quanlyluong.Data.NV;
import com.example.quanlyluong.Data.PhongBan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.vavr.Function2;
import io.vavr.control.Either;

import static io.vavr.API.run;

public class TamUng extends AppCompatActivity {
    EditText etSoPhieu, etNgayUng, etSoTien;
    Spinner spinnerMaNV;
    Button btnXoa, btnSua, btnThem;
    TableLayout dataTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tam_ung);
        getID();
        getSpinnerData();
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
                Intent intent_cc = new Intent(TamUng.this, ChamCong.class);
                startActivity(intent_cc);
                finish();
                break;
            case R.id.menuNhanvien:
                Intent intent_nv = new Intent(TamUng.this, QuanLyNhanVien.class);
                startActivity(intent_nv);
                finish();
                break;
            case R.id.menuPhongban:
                Intent intent_pb = new Intent(TamUng.this, QuanLyPhongBan.class);
                startActivity(intent_pb);
                finish();
                break;
            case R.id.menuTamung:
                Intent intent_tu = new Intent(TamUng.this, TamUng.class);
                startActivity(intent_tu);
                finish();
                break;
            case R.id.menuOptions:
                onBackPressed();
                break;
            case R.id.menuLogout:
                Intent intent_lo = new Intent(TamUng.this, login.class);
                startActivity(intent_lo);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private static Either<String, Date> validNgayUng(Date date) {
        if (date == null) {
            return Either.left("Ngay ung tien khong hop le");
        } else {
            return Either.right(date);
        }
    }

    private static Either<String, String> notNull(String str) {
        if (str == null && str.isEmpty()) {
            return Either.left("String is null");
        } else {
            return Either.right(str);
        }
    }

    private static Either<String, Object> notNull(Object obj) {
        if (obj == null) {
            return Either.left("Field is null");
        } else {
            return Either.right(obj);
        }
    }

    private static Either<String, String> validID(String id) {
            if (id == null) {
                return Either.left("ID is null");
            }
            try {
                double d = Double.parseDouble(id);
            } catch (NumberFormatException nfe) {
                return Either.left("ID is not valid");
            }
            return Either.right(id);
    }

    private static void showToast(android.content.Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    private void getID(){
        etSoPhieu = TamUng.this.findViewById(R.id.etSoPhieu);
        etNgayUng = TamUng.this.findViewById(R.id.etNgayUng);
        etSoTien = TamUng.this.findViewById(R.id.etSoTien);
        spinnerMaNV = TamUng.this.findViewById(R.id.spinnerMaNV);
        dataTable = TamUng.this.findViewById(R.id.tableTU);

        btnThem = TamUng.this.findViewById(R.id.btnThemTU);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notNull(spinnerMaNV.getSelectedItem()).isRight()) {
                    if (validID(spinnerMaNV.getSelectedItem().toString()).isRight()) {
                        int maNV = Integer.parseInt(spinnerMaNV.getSelectedItem().toString());
                        AlertDialog.Builder builder = new AlertDialog.Builder(TamUng.this);
                        builder.setTitle("XAC NHAN");
                        builder.setMessage("XAC NHAN TAM UNG MOI CUA NHAN VIEN " + maNV + "?");
                        builder.setIcon(android.R.drawable.ic_dialog_alert);
                        builder.setPositiveButton("CO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    String ngayUng = etNgayUng.getText().toString();
                                    int maNV = Integer.parseInt(spinnerMaNV.getSelectedItem().toString());
                                    int soTien = Integer.parseInt(etSoTien.getText().toString());

                                    if (notNull(ngayUng).isRight()) {
                                        Date tempDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(notNull(ngayUng).get());
                                        if (validNgayUng(tempDate).isRight()) {
                                            TamUng_Reposiroty repo = new TamUng_Reposiroty(TamUng.this);
                                            com.example.quanlyluong.Data.TamUng temp = new com.example.quanlyluong.Data.TamUng(-1, tempDate, maNV, soTien);
                                            repo.create(temp);
                                            getData();
                                        } else {
                                            showToast(TamUng.this, validNgayUng(tempDate).getLeft());
                                        }
                                    } else {
                                        showToast(TamUng.this, notNull(ngayUng).getLeft());
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(TamUng.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }
                        });
                        builder.setNegativeButton("KHONG", null);
                        builder.show();
                    } else {
                        showToast(TamUng.this, validID(spinnerMaNV.getSelectedItem().toString()).getLeft());
                    }
                } else {
                    showToast(TamUng.this, notNull(spinnerMaNV.getSelectedItem()).getLeft());
                }
            }
        });

        btnSua = TamUng.this.findViewById(R.id.btnSuaTU);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etSoPhieu.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(TamUng.this,"KHONG TAM UNG NAO DUOC CHON", Toast.LENGTH_SHORT).show();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(TamUng.this);
                builder.setTitle("XAC NHAN");
                builder.setMessage("BAN CO MUON CHINH SUA THONG TIN TAM UNG CO MA TAM UNG LA "  + etSoPhieu.getText().toString());
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("CO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String id = etSoPhieu.getText().toString();
                            TamUng_Reposiroty repo = new TamUng_Reposiroty(TamUng.this);
                            com.example.quanlyluong.Data.TamUng temp = repo.getById(Integer.parseInt(id));
                            temp.setSoTien(Integer.parseInt(etSoTien.getText().toString()));
                            String ngayUng = etNgayUng.getText().toString();
                            temp.setMaNV(Integer.parseInt(spinnerMaNV.getSelectedItem().toString()));

                            temp.setSoTien(Integer.parseInt(etSoTien.getText().toString()));
                            Date tempDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(ngayUng);
                            temp.setNgay(tempDate);

                            //-------------------------------------------------------

                            //validate input data

                            //-------------------------------------------------------
                            repo.update(temp);
                            getData();
                        }
                        catch (Exception e){
                            Toast.makeText(TamUng.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("KHONG", null);
                builder.show();
            }
        });

        btnXoa = TamUng.this.findViewById(R.id.btnXoaTU);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etSoPhieu.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(TamUng.this,"KHONG NHAN VIEN NAO DUOC CHON", Toast.LENGTH_SHORT).show();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(TamUng.this);
                builder.setTitle("XAC NHAN");
                builder.setMessage("BAN CO MUON XOA TAM UNG CO MA TAM UNG LA "  + etSoPhieu.getText().toString());
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("CO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            TamUng_Reposiroty repo = new TamUng_Reposiroty(TamUng.this);
                            int id = Integer.parseInt(etSoPhieu.getText().toString());
                            repo.deleteById(id);
                            getData();
                        }
                        catch (Exception e){
                            Toast.makeText(TamUng.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("KHONG", null);
                builder.show();
            }
        });
    }

    private void getData(){
        try {
            dataTable.removeAllViews();
            TamUng_Reposiroty repo = new TamUng_Reposiroty(this);
            List<com.example.quanlyluong.Data.TamUng> data = repo.getAll();
            for( com.example.quanlyluong.Data.TamUng i : data){
                TableRow row = (TableRow) LayoutInflater.from(TamUng.this).inflate(R.layout.table_row_tu, null);
                ((TextView)row.findViewById(R.id.soPhieu)).setText(String.valueOf(i.getSoPhieu()));
                ((TextView)row.findViewById(R.id.soTien)).setText(String.valueOf(i.getSoTien()));
                ((TextView)row.findViewById(R.id.maNV)).setText(String.valueOf(i.getMaNV()));
                String tempDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(i.getNgay());
                ((TextView)row.findViewById(R.id.ngayUng)).setText(tempDate);
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String soPhieu = (String) ((TextView)row.findViewById(R.id.soPhieu)).getText();
                        String soTien = (String) ((TextView)row.findViewById(R.id.soTien)).getText();
                        String ngayUng = (String) ((TextView)row.findViewById(R.id.ngayUng)).getText();
                        String maNV = (String) ((TextView)row.findViewById(R.id.maNV)).getText();
                        for(int i = 0; i < spinnerMaNV.getCount(); i++){
                            if(spinnerMaNV.getItemAtPosition(i).toString().equalsIgnoreCase(maNV)){
                                spinnerMaNV.setSelection(i);
                                break;
                            }
                        }
                        etSoPhieu.setText(soPhieu);
                        etNgayUng.setText(ngayUng);
                        etSoTien.setText(soTien);
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
    private void getSpinnerData(){
        try {
            NV_Repository repo = new NV_Repository(TamUng.this);
            List<NV> data = repo.getAll();
            List<String> dataList = new ArrayList<>();
            for(NV i : data){
                dataList.add(String.valueOf(i.getMaNV()));
            }
            ArrayAdapter<String> tempData = new ArrayAdapter<String>(TamUng.this, android.R.layout.simple_spinner_dropdown_item, dataList);
            tempData.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerMaNV.setAdapter(tempData);
        }
        catch (Exception e){
            Toast.makeText(TamUng.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}