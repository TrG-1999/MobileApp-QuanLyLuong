package com.example.quanlyluong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlyluong.DAO.NV_Repository;
import com.example.quanlyluong.DAO.Phongban_Repository;
import com.example.quanlyluong.Data.NV;
import com.example.quanlyluong.Data.PhongBan;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.vavr.control.Either;


public class QuanLyNhanVien extends AppCompatActivity {
    private  static int RESULT_LOAD_IMAGE=1;
    EditText etMaNV, etTenNV, etNgaySinh, etMucLuong;
    Spinner spinnerMaPB;
    TableLayout dataTable;
    Button btnXoa, btnThem, btnSua;
    ImageView imgViewNV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_nhan_vien);
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
                Intent intent_cc = new Intent(QuanLyNhanVien.this, ChamCong.class);
                startActivity(intent_cc);
                finish();
                break;
            case R.id.menuNhanvien:
                Intent intent_nv = new Intent(QuanLyNhanVien.this, QuanLyNhanVien.class);
                startActivity(intent_nv);
                finish();
                break;
            case R.id.menuPhongban:
                Intent intent_pb = new Intent(QuanLyNhanVien.this, QuanLyPhongBan.class);
                startActivity(intent_pb);
                finish();
                break;
            case R.id.menuTamung:
                Intent intent_tu = new Intent(QuanLyNhanVien.this, TamUng.class);
                startActivity(intent_tu);
                finish();
                break;
            case R.id.menuOptions:
                onBackPressed();
                break;
            case R.id.menuLogout:
                Intent intent_lo = new Intent(QuanLyNhanVien.this, login.class);
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

    private static Either<String, Date> validDate(Date date) {
        if (date == null) {
            return Either.left("Ngay ung tien khong hop le");
        } else {
            return Either.right(date);
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

    private void getID() {
        etMaNV = QuanLyNhanVien.this.findViewById(R.id.etMaNV);
        etMucLuong = QuanLyNhanVien.this.findViewById(R.id.etMucLuongNV);
        etNgaySinh = QuanLyNhanVien.this.findViewById(R.id.etNSNV);
        etTenNV = QuanLyNhanVien.this.findViewById(R.id.etTenNV);
        spinnerMaPB = QuanLyNhanVien.this.findViewById(R.id.spinnerMaPB);
        dataTable = QuanLyNhanVien.this.findViewById(R.id.tableNV);
        btnThem = QuanLyNhanVien.this.findViewById(R.id.btnThemNV);
        imgViewNV = QuanLyNhanVien.this.findViewById(R.id.imgViewNV);
        imgViewNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int REQUEST_EXTERNAL_STORAGE = 1;
                String[] PERMISSIONS_STORAGE = {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE };
                if (ContextCompat.checkSelfPermission(QuanLyNhanVien.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(QuanLyNhanVien.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
                } else {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);

                }
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyNhanVien.this);
                builder.setTitle("XAC NHAN");
                builder.setMessage("BAN CO MUON THEM NHAN VIEN MOI VAO");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("CO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String tenNV = etTenNV.getText().toString().toUpperCase();
                            String ngaySinh = etNgaySinh.getText().toString();
                            String[] tempStr = spinnerMaPB.getSelectedItem().toString().split(" ");
                            String maPB = tempStr[0];
                            String mucLuong = etMucLuong.getText().toString();

                            if (notNull(tenNV).isLeft())
                                throw new Exception("Lỗi: 'Tên nhân viên' trống");
                            if (notNull(ngaySinh).isLeft())
                                throw new Exception("Lỗi: 'Ngày sinh' trống");
                            if (validNumber(mucLuong).isLeft())
                                throw new Exception("Lỗi: 'Lương' trống");
                            Date tempDate = new SimpleDateFormat("dd/MM/yyyy").parse(ngaySinh);
                            if (validDate(tempDate).isLeft())
                                throw new Exception("Lỗi: 'Ngày sinh' không hợp lệ");
                            NV_Repository repo = new NV_Repository(QuanLyNhanVien.this);
                            byte[] tempImgNV = imageViewToByte(imgViewNV);
                            NV temp = new NV(-1, tenNV, tempDate, Integer.parseInt(maPB), Integer.parseInt(mucLuong), tempImgNV);
                            repo.create(temp);
                            getData();
                        } catch (ParseException e) {
                            Toast.makeText(QuanLyNhanVien.this, "Lỗi: 'Ngày sinh' không hợp lệ", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (Exception e) {
                            Toast.makeText(QuanLyNhanVien.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("KHONG", null);
                builder.show();
            }
        });
        btnSua = QuanLyNhanVien.this.findViewById(R.id.btnSuaNV);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMaNV.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(QuanLyNhanVien.this, "KHONG NHAN VIEN NAO DUOC CHON", Toast.LENGTH_SHORT).show();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyNhanVien.this);
                builder.setTitle("XAC NHAN");
                builder.setMessage("BAN CO MUON CHINH SUA THONG TIN NHAN VIEN CO MA NHAN VIEN LA " + etMaNV.getText().toString());
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("CO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            NV_Repository repo = new NV_Repository(QuanLyNhanVien.this);

                            String id = etMaNV.getText().toString();
                            if (notNull(id).isLeft())
                                throw new Exception("Lỗi: Không nhân viên nào được chọn");
                            NV temp = repo.getById(Integer.parseInt(id));
                            //Update name
                            String tenNV = etTenNV.getText().toString().toUpperCase();
                            if (notNull(tenNV).isLeft())
                                throw new Exception("Lỗi: 'Tên nhân viên' trống");
                            temp.setHoTen(tenNV);
                            //Update PhongBan
                            String[] tempStr = spinnerMaPB.getSelectedItem().toString().split(" ");
                            String maPB = tempStr[0];
                            if (validNumber(maPB).isLeft())
                                throw new Exception("Lỗi: 'Mã phòng ban' trống");
                            temp.setMaPB(Integer.parseInt(maPB));
                            //Update muc luong
                            String mucLuong = etMucLuong.getText().toString();
                            if (validNumber(mucLuong).isLeft())
                                throw new Exception("Lỗi: 'Lương' không hợp lệ");
                            temp.setMucLuong(Integer.parseInt(mucLuong));
                            //Update ngay sinh
                            String ngaySinh = etNgaySinh.getText().toString();
                            if (notNull(ngaySinh).isLeft())
                                throw new Exception("Lỗi: 'Ngày sinh' trống");
                            Date tempDate = new SimpleDateFormat("dd/MM/yyyy").parse(ngaySinh);
                            temp.setNgaySinh(tempDate);
                            byte[] tempImgNV = imageViewToByte(imgViewNV);
                            temp.setPhoto(tempImgNV);

                            repo.update(temp);
                            dataTable.setClickable(false);
                            etMaNV.setText("");
                            etMucLuong.setText("");
                            etNgaySinh.setText("");
                            etTenNV.setText("");
                            getData();
                        } catch (NumberFormatException e) {
                            Toast.makeText(QuanLyNhanVien.this, "Lỗi: 'Lương' không hợp lệ", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (ParseException e) {
                            Toast.makeText(QuanLyNhanVien.this, "Lỗi: 'Ngày sinh' không hợp lệ", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (Exception e) {
                            Toast.makeText(QuanLyNhanVien.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("KHONG", null);
                builder.show();
            }
        });
        btnXoa = QuanLyNhanVien.this.findViewById(R.id.btnXoaNV);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMaNV.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(QuanLyNhanVien.this, "KHONG NHAN VIEN NAO DUOC CHON", Toast.LENGTH_SHORT).show();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyNhanVien.this);
                builder.setTitle("XAC NHAN");
                builder.setMessage("BAN CO MUON XOA NHAN VIEN CO MA LA " + etMaNV.getText().toString());
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("CO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            NV_Repository repo = new NV_Repository(QuanLyNhanVien.this);
                            int id = Integer.parseInt(etMaNV.getText().toString());
                            repo.deleteById(id);
                            getData();
                        } catch (Exception e) {
                            Toast.makeText(QuanLyNhanVien.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("KHONG", null);
                builder.show();
            }
        });
    }

    private void getSpinnerData() {
        try {
            Phongban_Repository repo = new Phongban_Repository(QuanLyNhanVien.this);
            List<PhongBan> data = repo.getAll();
            List<String> dataList = new ArrayList<>();
            for (PhongBan i : data) {
                dataList.add(String.valueOf(i.getMaPB()) + " - " + i.getTenPB());
            }
            ArrayAdapter<String> tempData = new ArrayAdapter<String>(QuanLyNhanVien.this, android.R.layout.simple_spinner_dropdown_item, dataList);
            tempData.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerMaPB.setAdapter(tempData);
        } catch (Exception e) {
            Toast.makeText(QuanLyNhanVien.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void getData() {
        try {
            dataTable.removeAllViews();
            NV_Repository repo = new NV_Repository(this);
            List<NV> data = repo.getAll();
            for (NV i : data) {
                TableRow row = (TableRow) LayoutInflater.from(QuanLyNhanVien.this).inflate(R.layout.table_row_nv, null);
                ((TextView) row.findViewById(R.id.maNV)).setText(String.valueOf(i.getMaNV()));
                ((TextView) row.findViewById(R.id.tenNV)).setText(i.getHoTen());
                ((TextView) row.findViewById(R.id.maPB)).setText(String.valueOf(i.getMaPB()));
                ((TextView) row.findViewById(R.id.luongNV)).setText(String.valueOf(i.getMucLuong()));
                String tempDate = new SimpleDateFormat("dd/MM/yyyy").format(i.getNgaySinh());
                ((TextView) row.findViewById(R.id.NSNV)).setText(tempDate);
                ((ImageView) row.findViewById(R.id.imgViewNV)).setImageBitmap(BitmapFactory.decodeByteArray(i.getPhoto(),0,i.getPhoto().length));
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String maNV = (String) ((TextView) row.findViewById(R.id.maNV)).getText();
                        String tenNV = (String) ((TextView) row.findViewById(R.id.tenNV)).getText();
                        String maPB = (String) ((TextView) row.findViewById(R.id.maPB)).getText();
                        String luongNV = (String) ((TextView) row.findViewById(R.id.luongNV)).getText();
                        String NSNV = (String) ((TextView) row.findViewById(R.id.NSNV)).getText();
                        etMaNV.setText(maNV);
                        etMucLuong.setText(luongNV);
                        etTenNV.setText(tenNV);
                        etNgaySinh.setText(NSNV);
                        for (int i = 0; i < spinnerMaPB.getCount(); i++) {
                            String[] tempStr = spinnerMaPB.getItemAtPosition(i).toString().split(" ");
                            String tempPB = tempStr[0].trim();
                            if (tempPB.equalsIgnoreCase(maPB)) {
                                spinnerMaPB.setSelection(i);
                                break;
                            }
                        }

                        imgViewNV.setImageBitmap(BitmapFactory.decodeByteArray(i.getPhoto(),0 , i.getPhoto().length));
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
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.imgViewNV);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
}