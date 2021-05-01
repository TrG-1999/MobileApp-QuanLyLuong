package com.example.quanlyluong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ThongKe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
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
}