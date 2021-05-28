package com.example.quanlyluong.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.quanlyluong.Data.NV;
import com.example.quanlyluong.Data.NV_ThongKe;
import com.example.quanlyluong.ThongKe;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class NV_Repository extends DatabaseHelper implements DAO<NV> {
    public static String months[] = {"January", "February", "March", "April",
            "May", "June", "July", "August", "September",
            "October", "November", "December"};
    public NV_Repository(Context context) {
        super(context);
    }

    @Override
    public boolean create(NV nv) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(this.HOTEN_COLUMN, nv.getHoTen());
        cv.put(this.NGAYSINH_COLUMN, nv.getNgaySinh().toString());
        cv.put(MAPB_COLUMN, nv.getMaPB());
        cv.put(MUCLUONG_COLUMN, nv.getMucLuong());
        cv.put(PHOTO_COLUMN, nv.getPhoto());
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
        try {
            if(cursor.moveToFirst()){
                Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
                field.setAccessible(true);
                field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
                do{
                    NV temp = new NV();
                    temp.setMaNV(cursor.getInt(0));
                    temp.setHoTen(cursor.getString(1));
                    String[] tempStr = cursor.getString(2).split(" ");

                    Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(tempStr[1]);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int month = cal.get(Calendar.MONTH) + 1;

                    String dateStr = String.valueOf(month) + " " + tempStr[2] + " " + tempStr[tempStr.length-1];
                    Date tempDate = new SimpleDateFormat("MM dd yyyy").parse(dateStr);
                    temp.setNgaySinh(tempDate);
                    temp.setMaPB(cursor.getInt(3));
                    temp.setMucLuong(cursor.getInt(4));
                    temp.setPhoto(cursor.getBlob(5));
                    resultList.add(temp);
                }while(cursor.moveToNext());
            }
        }
        catch (Exception e){
            throw e;
        }
        finally {
            cursor.close();
            db.close();
            return resultList;
        }
    }

    @Override
    public NV getById(int MANV) throws Exception{
        NV result = null;
        String queryST = "SELECT * FROM " + this.NHANVIEN_TABLE + " WHERE " + MANV_COLMN + " = " + MANV ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryST, null);
        try {
            if(cursor.moveToFirst()){
                Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
                field.setAccessible(true);
                field.set(null, 100 * 1024 * 1024); //the 100MB is the new size

                result = new NV();
                result.setMaNV(cursor.getInt(0));
                result.setHoTen(cursor.getString(1));
                String[] tempStr = cursor.getString(2).split(" ");

                Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(tempStr[1]);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int month = cal.get(Calendar.MONTH) + 1;

                String dateStr = String.valueOf(month) + " " + tempStr[2] + " " + tempStr[tempStr.length-1];
                Date tempDate = new SimpleDateFormat("MM dd yyyy").parse(dateStr);
                result.setNgaySinh(tempDate);
                result.setMaPB(cursor.getInt(3));
                result.setMucLuong(cursor.getInt(4));
                result.setPhoto(cursor.getBlob(5));
            }
        }catch (Exception e){
            throw e;
        }
        finally {
            db.close();
            cursor.close();
            return result;
        }
    }
    public List<NV> getByMaPB(String maPB) throws Exception{
        String queryST = "SELECT * FROM " + this.NHANVIEN_TABLE + " WHERE " + MAPB_COLUMN + " = " + maPB ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryST, null);
        List<NV> result = new ArrayList<>();
        try {
            if(cursor.moveToFirst()){
                Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
                field.setAccessible(true);
                field.set(null, 100 * 1024 * 1024); //the 100MB is the new size

                do{
                    NV tempNV = new NV();
                    tempNV.setMaNV(cursor.getInt(0));
                    tempNV.setHoTen(cursor.getString(1));
                    String[] tempStr = cursor.getString(2).split(" ");

                    Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(tempStr[1]);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int month = cal.get(Calendar.MONTH) + 1;

                    String dateStr = String.valueOf(month) + " " + tempStr[2] + " " + tempStr[tempStr.length-1];
                    Date tempDate = new SimpleDateFormat("MM dd yyyy").parse(dateStr);
                    tempNV.setNgaySinh(tempDate);
                    tempNV.setMaPB(cursor.getInt(3));
                    tempNV.setMucLuong(cursor.getInt(4));
                    tempNV.setPhoto(cursor.getBlob(5));
                    result.add(tempNV);
                }
                while(cursor.moveToNext());
            }
        }catch (Exception e){
            throw e;
        }
        finally {
            db.close();
            cursor.close();
            return result;
        }
    }

    @Override
    public boolean deleteAll() throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryST = "DELETE FROM " + NHANVIEN_TABLE;
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()) return true;
        return false;
    }

    @Override
    public boolean deleteById(int MANV ) throws Exception{
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
                this.HOTEN_COLUMN + " = '" + nv.getHoTen() + "', " +
                this.NGAYSINH_COLUMN + " = '" + nv.getNgaySinh().toString() + "', " +
                this.MAPB_COLUMN + " = '" + nv.getMaPB() + "', " +
                this.MUCLUONG_COLUMN + " = '" + nv.getMucLuong() + "' "  +
                this.PHOTO_COLUMN + " = '" + nv.getPhoto() + "' "  +
                " WHERE " + MANV_COLMN + " = '" +  nv.getMaNV() + "'";
        Cursor cursor = db.rawQuery(queryST, null);
        if(cursor.moveToFirst()) return true;
        return false;
    }
    public List<NV_ThongKe> thongKe(String maPB, String thang, String nam) throws Exception{
        SQLiteDatabase db = this.getReadableDatabase();
        String currentMonth = months[Integer.parseInt(thang)-1];
        String queryST ="SELECT NHANVIEN.MANV, NHANVIEN.HOTEN, NHANVIEN.MAPB,\n" +
                " CHAMCONG.DIEMTHUONG\n" +
                " FROM NHANVIEN\n" +
                " INNER JOIN CHAMCONG ON NHANVIEN.MANV = CHAMCONG.MANV WHERE " + "\n" +
                " NGAYGHISO like '%" + currentMonth + "%' \n" +
                " AND NGAYGHISO like '%" + nam + "%'\n" +
                " AND NHANVIEN.MAPB = " + maPB +
                " ORDER BY CHAMCONG.DIEMTHUONG\n" +
                " LIMIT 10";
        Cursor cursor = db.rawQuery(queryST,null);
        List<NV_ThongKe> result = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                NV_ThongKe temp = new NV_ThongKe(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
                result.add(temp);
            }while(cursor.moveToNext());
        }
        db.close();
        return result;
    }
}
