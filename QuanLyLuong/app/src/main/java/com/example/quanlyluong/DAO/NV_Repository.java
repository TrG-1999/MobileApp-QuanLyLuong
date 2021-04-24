package com.example.quanlyluong.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlyluong.Data.NV;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NV_Repository extends DatabaseHelper implements DAO<NV> {
    public NV_Repository(Context context) {
        super(context);
    }

    @Override
    public boolean create(NV nv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(this.HOTEN_COLUMN, nv.getHoTen());
        cv.put(this.NGAY_COLUMN, nv.getNgaySinh().toString());
        cv.put(MAPB_COLUMN, nv.getMaPB());
        cv.put(MUCLUONG_COLUMN, nv.getMucLuong());
        long create = db.insert(this.NHANVIEN_TABLE, null, cv);
        db.close();
        if(create > 0) return true;
        return false;
    }

    @Override
    public List<NV> getAll() throws Exception{
        List<NV> resultList = new ArrayList<>();
        String queryST = "SELECT * FROM " + this.NHANVIEN_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()){
            do{
                NV temp = new NV();
                temp.setMaNV(cursor.getInt(0));
                temp.setHoTen(cursor.getString(1));
                Date tempDate = new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(2));
                temp.setNgaySinh(tempDate);
                temp.setMaPB(cursor.getInt(3));
                temp.setMucLuong(cursor.getInt(4));
                resultList.add(temp);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return resultList;
    }

    @Override
    public NV getById(int MANV) throws Exception{
        NV result = null;
        String queryST = "SELECT * FROM " + this.NHANVIEN_TABLE + " WHERE ID = " + MANV ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()){
            result = new NV();
            result.setMaNV(cursor.getInt(0));
            result.setHoTen(cursor.getString(1));
            Date tempDate = new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(2));
            result.setNgaySinh(tempDate);
            result.setMaPB(cursor.getInt(3));
            result.setMucLuong(cursor.getInt(4));
        }
        db.close();
        cursor.close();
        return result;
    }

    @Override
    public boolean deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryST = "DELETE FROM " + NHANVIEN_TABLE;
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()) return true;
        return false;
    }

    @Override
    public boolean deleteById(int MANV ) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryST = "DELETE FROM " + NHANVIEN_TABLE + " WHERE " + MANV_COLMN +" = " + MANV;
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()) return true;
        return false;
    }

    @Override
    public boolean update(NV nv) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryST = "UPDATE "+  this.NHANVIEN_TABLE + " SET " +
                this.HOTEN_COLUMN + " = " + nv.getHoTen() + " " +
                this.NGAY_COLUMN + " = " + nv.getNgaySinh().toString() + " " +
                this.MAPB_COLUMN + " = " + nv.getMaPB() + " " +
                this.MUCLUONG_COLUMN + " = " + nv.getMucLuong()  +
                " WHERE " + MANV_COLMN + " = " +  nv.getMaNV();
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()) return true;
        return false;
    }
}
