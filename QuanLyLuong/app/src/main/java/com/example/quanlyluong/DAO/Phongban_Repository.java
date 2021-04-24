package com.example.quanlyluong.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlyluong.Data.PhongBan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Phongban_Repository extends DatabaseHelper implements DAO<PhongBan> {

    public Phongban_Repository(Context context) {
        super(context);
    }

    @Override
    public boolean create(PhongBan phongBan) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
//        cv.put(this.MAPB_COLUMN, phongBan.getMaPB());
        cv.put(this.TENPB_COLUMN, phongBan.getTenPB());
        long create = db.insert(this.PHONGBAN_TABLE, null, cv);
        db.close();
        if(create > 0) return true;
        return false;
    }

    @Override
    public List<PhongBan> getAll() throws Exception {
        List<PhongBan> resultList = new ArrayList<>();
        String queryST = "SELECT * FROM " + this.PHONGBAN_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()){
            do{
                PhongBan temp = new PhongBan();
                temp.setMaPB(cursor.getInt(0));
                temp.setTenPB(cursor.getString(1));
                resultList.add(temp);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return resultList;
    }

    @Override
    public PhongBan getById(int MAPB) throws Exception {
        PhongBan result = null;
        String queryST = "SELECT * FROM " + this.PHONGBAN_TABLE + " WHERE " + MAPB_COLUMN + " = " + MAPB ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()){
            result = new PhongBan();
            result.setMaPB(cursor.getInt(0));
            result.setTenPB(cursor.getString(1));
        }
        db.close();
        cursor.close();
        return result;
    }

    @Override
    public boolean deleteAll() throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryST = "DELETE FROM " + PHONGBAN_TABLE;
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()) return true;
        return false;
    }

    @Override
    public boolean deleteById(int MAPB) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryST = "DELETE FROM " + PHONGBAN_TABLE + " WHERE " + MAPB_COLUMN + " = " + MAPB;
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()) return true;
        return false;
    }

    @Override
    public boolean update(PhongBan phongBan) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryST = "UPDATE "+  this.PHONGBAN_TABLE + " SET " +
                this.TENPB_COLUMN + " = '" + phongBan.getTenPB() + "' " +
                " WHERE " + MAPB_COLUMN + " = " +  phongBan.getMaPB();
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()) return true;
        return false;
    }

}
