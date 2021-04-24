package com.example.quanlyluong.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlyluong.Data.ChamCong;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChamCong_Repository extends DatabaseHelper implements DAO<ChamCong>  {
    public ChamCong_Repository(Context context) {
        super(context);
    }

    @Override
    public boolean create(ChamCong chamCong) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(this.MANV_COLMN, chamCong.getMaNV());
        cv.put(this.NGAYGHISO_COLUMN, chamCong.getNgayGhiSo().toString());
        cv.put(this.SONGAYCONG_COLUMN, chamCong.getSoNgayCong());
        long create = db.insert(this.CHAMCONG_TABLE, null, cv);
        if(create > 0) return true;
        return false;
    }

    @Override
    public List<ChamCong> getAll() throws Exception {
        List<ChamCong> resultList = new ArrayList<>();
        String queryST = "SELECT * FROM CHAMCONG";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()){
            do{
                ChamCong temp = new ChamCong();
                temp.setMaNV(cursor.getInt(0));
                String[] tempStr = cursor.getString(1).split(" ");
                String dateStr = tempStr[1] + " " + tempStr[2] + " " + tempStr[tempStr.length-1]  + " " + tempStr[3];
                Date tempDate = new SimpleDateFormat("MMMM dd yyyy HH:mm:ss").parse(dateStr);
                temp.setNgayGhiSo(tempDate);
                temp.setSoNgayCong(cursor.getInt(2));
                resultList.add(temp);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return resultList;
    }

    @Override
    @Deprecated
    public ChamCong getById(int id) throws Exception {
        return null;
    }

    public ChamCong getByIdAndDate(int id, Date date) throws Exception{
        ChamCong result = null;
        String queryST = "SELECT * FROM CHAMCONG WHERE " + MANV_COLMN + " = '" + id + "' AND "
                + NGAYGHISO_COLUMN + " = '" + date.toString() + "'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()){
            result = new ChamCong();
            result.setMaNV(cursor.getInt(0));
            String[] tempStr = cursor.getString(1).split(" ");
            String dateStr = tempStr[1] + " " + tempStr[2] + " " + tempStr[tempStr.length-1]  + " " + tempStr[3];
            Date tempDate = new SimpleDateFormat("MMMM dd yyyy HH:mm:ss").parse(dateStr);
            result.setNgayGhiSo(tempDate);
            result.setSoNgayCong(cursor.getInt(2));
        }
        db.close();
        cursor.close();
        return result;
    }

    @Override
    public boolean deleteAll() throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryST = "DELETE FROM " + CHAMCONG_TABLE;
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()) return true;
        return false;
    }

    @Override
    @Deprecated
    public boolean deleteById(int id) throws Exception {
        return false;
    }
    public boolean deleteByObject(ChamCong chamCong){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryST = "DELETE FROM " + CHAMCONG_TABLE + " WHERE " + MANV_COLMN +" = '"
                + chamCong.getMaNV() + "' " + "AND "
                + NGAYGHISO_COLUMN + " ='" + chamCong.getNgayGhiSo().toString() + "'";
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()) return true;
        return false;
    }
    @Override
    public boolean update(ChamCong chamCong) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryST = "UPDATE "+  this.CHAMCONG_TABLE + " SET " +
                this.SONGAYCONG_COLUMN + " = " + chamCong.getSoNgayCong() + " " +
                " WHERE " + MANV_COLMN +" = '"
                + chamCong.getMaNV() + "' " + "AND "
                + NGAYGHISO_COLUMN + " ='" + chamCong.getNgayGhiSo().toString() + "'";
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()) return true;
        return false;
    }
}
