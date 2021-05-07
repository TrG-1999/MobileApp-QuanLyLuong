package com.example.quanlyluong.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlyluong.Data.TamUng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TamUng_Reposiroty extends DatabaseHelper  implements DAO<TamUng> {
    public TamUng_Reposiroty(Context context) {
        super(context);
    }

    @Override
    public boolean create(TamUng tamUng) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(this.NGAY_COLUMN, tamUng.getNgay().toString());
        cv.put(this.MANV_COLMN, tamUng.getMaNV());
        cv.put(this.SOTIEN_COLUMN, tamUng.getSoTien());
        long create = db.insert(this.TAMUNG_TABLE, null, cv);
        if(create > 0) return true;
        return false;
    }

    @Override
    public List<TamUng> getAll() throws Exception {
        List<TamUng> resultList = new ArrayList<>();
        String queryST = "SELECT * FROM " + this.TAMUNG_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()){
            do{
                TamUng temp = new TamUng();
                temp.setSoPhieu(cursor.getInt(0));
                String[] tempStr = cursor.getString(1).split(" ");

                Date date1 = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(tempStr[1]);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date1);
                int month = cal.get(Calendar.MONTH) + 1;
                String dateStr = String.valueOf(month) + " " + tempStr[2] + " " + tempStr[tempStr.length-1];

                Date tempDate = new SimpleDateFormat("MM dd yyyy").parse(dateStr);
                temp.setNgay(tempDate);
                temp.setMaNV(cursor.getInt(2));
                temp.setSoTien(cursor.getInt(3));
                resultList.add(temp);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return resultList;
    }

    @Override
    public TamUng getById(int id) throws Exception {
        TamUng result = null;
        String queryST = "SELECT * FROM " + this.TAMUNG_TABLE + " WHERE " + SOPHIEU_COLUMN + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()){
            result = new TamUng();
            result.setSoPhieu(cursor.getInt(0));
            String[] tempStr = cursor.getString(1).split(" ");

            Date date1 = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(tempStr[1]);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            int month = cal.get(Calendar.MONTH) + 1;
            String dateStr = String.valueOf(month) + " " + tempStr[2] + " " + tempStr[tempStr.length-1];
            Date tempDate = new SimpleDateFormat("MM dd yyyy").parse(dateStr);
            result.setNgay(tempDate);
            result.setMaNV(cursor.getInt(2));
            result.setSoTien(cursor.getInt(3));
        }
        db.close();
        cursor.close();
        return result;
    }
    public TamUng getByIdAndDate(int id, Date date) throws Exception{
        TamUng result = null;
        String queryST = "SELECT * FROM " + this.TAMUNG_TABLE + " WHERE " + SOPHIEU_COLUMN + " = " + id + " " +
                "AND " + NGAY_COLUMN + " = '" + date.toString() + "' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()){
            result = new TamUng();
            result.setSoPhieu(cursor.getInt(0));
            String[] tempStr = cursor.getString(1).split(" ");

            Date date1 = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(tempStr[1]);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            int month = cal.get(Calendar.MONTH) + 1;
            String dateStr = String.valueOf(month) + " " + tempStr[2] + " " + tempStr[tempStr.length-1];

            Date tempDate = new SimpleDateFormat("MM dd yyyy").parse(dateStr);
            result.setNgay(tempDate);
            result.setMaNV(cursor.getInt(2));
            result.setSoTien(cursor.getInt(3));
        }
        db.close();
        cursor.close();
        return result;
    }

    @Override
    public boolean deleteAll() throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryST = "DELETE FROM " + TAMUNG_TABLE;
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()) return true;
        return false;
    }

    @Override
    public boolean deleteById(int id) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryST = "DELETE FROM " + TAMUNG_TABLE + " WHERE " + SOPHIEU_COLUMN + " = " + id;
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()) return true;
        return false;
    }
    public boolean deleteByIdAndDate(int id, Date date) throws Exception{
        SQLiteDatabase db = this.getWritableDatabase();
        String queryST = "DELETE FROM " + TAMUNG_TABLE + " WHERE " + SOPHIEU_COLUMN + " = " + id + "' " +
                "AND " + NGAY_COLUMN + " = '" + date.toString() + "'";
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()) return true;
        return false;
    }

    @Override
    public boolean update(TamUng tamUng) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryST = "UPDATE "+  this.TAMUNG_TABLE + " SET " +
                this.MANV_COLMN + " = " + tamUng.getMaNV() + ", " +
                this.NGAY_COLUMN + " = '" + tamUng.getNgay() + "', " +
                this.SOTIEN_COLUMN + " = " + tamUng.getSoTien() + " " +
                " WHERE " + SOPHIEU_COLUMN + " = " +  tamUng.getSoPhieu();
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()) return true;
        return false;
    }
}
