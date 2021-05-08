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

import com.example.quanlyluong.DAO.ChamCong_Repository;
import com.example.quanlyluong.DAO.NV_Repository;
import com.example.quanlyluong.DAO.Phongban_Repository;
import com.example.quanlyluong.Data.NV;
import com.example.quanlyluong.Data.PhongBan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.vavr.control.Either;


public class ChamCong extends AppCompatActivity {
    EditText etNgayGhi, etSoNgayCong;
    Button btnXoa, btnSua, btnThem;
    TableLayout dataTable;
    Spinner spinnerMaNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cham_cong);
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
                Intent intent_cc = new Intent(ChamCong.this, ChamCong.class);
                startActivity(intent_cc);
                finish();
                break;
            case R.id.menuNhanvien:
                Intent intent_nv = new Intent(ChamCong.this, QuanLyNhanVien.class);
                startActivity(intent_nv);
                finish();
                break;
            case R.id.menuPhongban:
                Intent intent_pb = new Intent(ChamCong.this, QuanLyPhongBan.class);
                startActivity(intent_pb);
                finish();
                break;
            case R.id.menuTamung:
                Intent intent_tu = new Intent(ChamCong.this, TamUng.class);
                startActivity(intent_tu);
                finish();
                break;
            case R.id.menuOptions:
                onBackPressed();
                break;
            case R.id.menuLogout:
                Intent intent_lo = new Intent(ChamCong.this, login.class);
                startActivity(intent_lo);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private static Either<String, String> notNull(String str) {
        if (str == null || str.isEmpty()) {
            return Either.left("String is null");
        } else {
            return Either.right(str);
        }
    }

    private static Either<String, String> validNumber(String number) {
        if (number == null) {
            return Either.left("Number is null");
        }
        try {
            double d = Double.parseDouble(number);
        } catch (NumberFormatException nfe) {
            return Either.left("Number is not valid");
        }
        return Either.right(number);
    }

    private static Either<String, Date> validDate(Date date) {
        if (date == null) {
            return Either.left("Ngay ung tien khong hop le");
        } else {
            return Either.right(date);
        }
    }

    private void getID() {
        spinnerMaNV = ChamCong.this.findViewById(R.id.spinnerMaNV);
        etNgayGhi = ChamCong.this.findViewById(R.id.etNgayGhi);
        etSoNgayCong = ChamCong.this.findViewById(R.id.etSoNgayCong);
        dataTable = ChamCong.this.findViewById(R.id.tableCC);

        btnSua = ChamCong.this.findViewById(R.id.btnSuaCC);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maNV = spinnerMaNV.getSelectedItem().toString();
                AlertDialog.Builder buidler = new AlertDialog.Builder(ChamCong.this);
                buidler.setTitle("XAC NHAN");
                buidler.setMessage("XAC NHAN SUA CHAM CONG CUA NHAN VIEN CO MA NHAN VIEN " + maNV + "?");
                buidler.setIcon(android.R.drawable.ic_dialog_alert);
                buidler.setPositiveButton("CO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            ChamCong_Repository repo = new ChamCong_Repository(ChamCong.this);
                            //UPDATE Nhan Vien
                            String maNV = spinnerMaNV.getSelectedItem().toString();
                            if (validNumber(maNV).isLeft())
                                throw new Exception("Lỗi: Không nhân viên nào được chọn");
                            //UPDATE Date
                            Date date = new Date(System.currentTimeMillis());
                            String ngayGhi = etNgayGhi.getText().toString();
                            if (notNull(ngayGhi).isLeft())
                                throw new Exception("Lỗi: 'Ngày ghi sổ' trống");
                            date = new SimpleDateFormat("dd/MM/yyyy").parse(ngayGhi);
                            //UPDATE so ngay cong
                            String soNgayCong = etSoNgayCong.getText().toString();
                            if (validNumber(soNgayCong).isLeft())
                                throw new Exception("Lỗi: 'Số ngày công' trống");
                            com.example.quanlyluong.Data.ChamCong cc = repo.getByIdAndDate(Integer.parseInt(maNV), date);
                            cc.setSoNgayCong(Integer.parseInt(soNgayCong));
                            repo.update(cc);
                            getData();
                        } catch (ParseException e) {
                            Toast.makeText(ChamCong.this, "Lỗi: 'Ngày ghi sổ' không hợp lệ", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (Exception e) {
                            Toast.makeText(ChamCong.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
                buidler.setNegativeButton("KHONG", null);
                buidler.show();
            }
        });

        btnThem = ChamCong.this.findViewById(R.id.btnThemCC);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maNV = spinnerMaNV.getSelectedItem().toString();
                String date = etNgayGhi.getText().toString();
                AlertDialog.Builder buidler = new AlertDialog.Builder(ChamCong.this);
                buidler.setTitle("XAC NHAN");
                buidler.setMessage("XAC NHAN CHAM CONG NHAN VIEN CO MA NHAN VIEN " + maNV + " VAO NGAY " + date + " ?");
                buidler.setIcon(android.R.drawable.ic_dialog_alert);
                buidler.setPositiveButton("CO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            ChamCong_Repository repo = new ChamCong_Repository(ChamCong.this);
                            //add NV
                            String maNV = spinnerMaNV.getSelectedItem().toString();
                            if (notNull(maNV).isLeft())
                                throw new Exception("Lỗi: Không nhân viên nào được chọn");
                            //ADD DATE
//                            Date date = new Date(System.currentTimeMillis());
//                            String ngayGhi = etNgayGhi.getText().toString();
//                            if (notNull(ngayGhi).isLeft())
//                                throw new Exception("Lỗi: 'Ngày ghi sổ' trống");
//                            date = new SimpleDateFormat("dd/MM/yyyy").parse(ngayGhi);
                            Date date = new SimpleDateFormat("dd/MM/yyyy").parse((new SimpleDateFormat("dd/MM/yyyy")).format(new Date()));

                            //ADD so ngay cong
                            String soNgayCong = etSoNgayCong.getText().toString();
                            if (notNull(soNgayCong).isLeft())
                                throw new Exception("Lỗi: 'Số ngày công' trống");
                            com.example.quanlyluong.Data.ChamCong cc = new com.example.quanlyluong.Data.ChamCong(Integer.parseInt(maNV), date, Integer.parseInt(soNgayCong));
                            repo.create(cc);
                            getData();
                        } catch (ParseException e) {
                            Toast.makeText(ChamCong.this, "Lỗi: 'Ngày ghi sổ' không hợp lệ", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (Exception e) {
                            Toast.makeText(ChamCong.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
                buidler.setNegativeButton("KHONG", null);
                buidler.show();
            }
        });
        btnXoa = ChamCong.this.findViewById(R.id.btnXoaCC);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maNV = spinnerMaNV.getSelectedItem().toString();
                String date = etNgayGhi.getText().toString();
                AlertDialog.Builder buidler = new AlertDialog.Builder(ChamCong.this);
                buidler.setTitle("XAC NHAN");
                buidler.setMessage("BAN CO MUON XOA CHAM CONG CUA NHAN VIEN " + maNV + " VAO NGAY " + date + " ?");
                buidler.setIcon(android.R.drawable.ic_dialog_alert);
                buidler.setPositiveButton("CO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            int maNV = Integer.parseInt(String.valueOf(spinnerMaNV.getSelectedItem().toString()));
                            ChamCong_Repository repo = new ChamCong_Repository(ChamCong.this);
                            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(etNgayGhi.getText().toString());
                            com.example.quanlyluong.Data.ChamCong cc = repo.getByIdAndDate(maNV, date);
                            repo.deleteByObject(cc);
                            getData();
                        } catch (Exception e) {
                            Toast.makeText(ChamCong.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
                buidler.setNegativeButton("KHONG", null);
                buidler.show();
            }
        });
    }

    private void getData() {
        try {
            dataTable.removeAllViews();
            ChamCong_Repository repo = new ChamCong_Repository(this);
            List<com.example.quanlyluong.Data.ChamCong> data = repo.getAll();
            for (com.example.quanlyluong.Data.ChamCong i : data) {
                TableRow row = (TableRow) LayoutInflater.from(ChamCong.this).inflate(R.layout.table_row_cc, null);
                ((TextView) row.findViewById(R.id.maNV)).setText(String.valueOf(i.getMaNV()));
                String tempDate = new SimpleDateFormat("dd/MM/yyyy").format(i.getNgayGhiSo());
                ((TextView) row.findViewById(R.id.ngayGhi)).setText(tempDate);
                ((TextView) row.findViewById(R.id.soNgayCong)).setText((String.valueOf(i.getSoNgayCong())));
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String maNV = (String) ((TextView) row.findViewById(R.id.maNV)).getText();
                        String ngayGhi = (String) ((TextView) row.findViewById(R.id.ngayGhi)).getText();
                        String soNgayCong = (String) ((TextView) row.findViewById(R.id.soNgayCong)).getText();
                        for (int i = 0; i < spinnerMaNV.getCount(); i++) {
                            if (spinnerMaNV.getItemAtPosition(i).toString().equalsIgnoreCase(maNV)) {
                                spinnerMaNV.setSelection(i);
                                break;
                            }
                        }
                        etSoNgayCong.setText(soNgayCong);
                        etNgayGhi.setText(ngayGhi);
                    }
                });
                dataTable.addView(row);
            }
            dataTable.requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void getSpinnerData() {
        try {
            NV_Repository repo = new NV_Repository(ChamCong.this);
            List<NV> data = repo.getAll();
            List<String> dataList = new ArrayList<>();
            for (NV i : data) {
                dataList.add(String.valueOf(i.getMaNV()));
            }
            ArrayAdapter<String> tempData = new ArrayAdapter<String>(ChamCong.this, android.R.layout.simple_spinner_dropdown_item, dataList);
            tempData.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerMaNV.setAdapter(tempData);
        } catch (Exception e) {
            Toast.makeText(ChamCong.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}