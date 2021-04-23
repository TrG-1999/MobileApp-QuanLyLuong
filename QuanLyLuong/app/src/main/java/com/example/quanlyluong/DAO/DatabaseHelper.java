package com.example.quanlyluong.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    protected static final String PHONGBAN_TABLE = "PHONGBAN";
    protected static final String MAPB_COLUMN = "MAPB";
    protected static final String TENPB_COLUMN = "TENPB";
    protected static final String NHANVIEN_TABLE = "NHANVIEN";
    protected static final String MANV_COLMN = "MANV";
    protected static final String HOTEN_COLUMN = "HOTEN";
    protected static final String MUCLUONG_COLUMN = "MUCLUONG";
    protected static final String NGAY_COLUMN = "NGAY";
    protected static final String NGAYSINH_COLUMN = NGAY_COLUMN + "SINH";
    protected static final String TAMUNG_TABLE = "TAMUNG";
    protected static final String SOPHIEU_COLUMN = "SOPHIEU";
    protected static final String SOTIEN_COLUMN = "SOTIEN";
    protected static final String CHAMCONG_TABLE = "CHAMCONG";
    protected static final String NGAYGHISO_COLUMN = "NGAYGHISO";
    protected static final String SONGAYCONG_COLUMN = "SONGAYCONG";

    public DatabaseHelper(Context context) {
        super(context, "QUAN_LI_LUONG", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createST = "CREATE TABLE " + PHONGBAN_TABLE + "(\n" +
                "  " + MAPB_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  " + TENPB_COLUMN + " TEXT NOT NULL" +
                ");\n" +
                "CREATE TABLE " + NHANVIEN_TABLE + "(\n" +
                "  \t" + MANV_COLMN + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  \t" + HOTEN_COLUMN + " TEXT NOT NULL,\n" +
                "  \t" + NGAYSINH_COLUMN + " DATE NOT NULL,\n" +
                "  \t" + MAPB_COLUMN + " INT NOT NULL,\n" +
                "  \t" + MUCLUONG_COLUMN + " INT NOT NULL,\n" +
                " \tCONSTRAINT fk_" + PHONGBAN_TABLE + "_NV\n" +
                "    \tFOREIGN KEY (" + MAPB_COLUMN + ")\n" +
                "    \tREFERENCES " + PHONGBAN_TABLE + "M(" + MAPB_COLUMN + ") ON DELETE CASCADE\n" +
                " );\n" +
                "CREATE TABLE " + TAMUNG_TABLE + "(\n" +
                "   " + SOPHIEU_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "   " + NGAY_COLUMN + " DATE NOT NULL,\n" +
                "   " + MANV_COLMN + " INTEGER NOT NULL,\n" +
                "   " + SOTIEN_COLUMN + " INTEGER NOT NULL,\n" +
                "   CONSTRAINT fk_" + TAMUNG_TABLE + "_NV\n" +
                "    \tFOREIGN KEY (" + MANV_COLMN + ")\n" +
                "    \tREFERENCES " + NHANVIEN_TABLE + "(" + MANV_COLMN + ") ON DELETE CASCADE\n" +
                " );\n" +
                "CREATE TABLE " + CHAMCONG_TABLE + "(\n" +
                "   " + MANV_COLMN + " INTEGER NOT NULL,\n" +
                "   " + NGAYGHISO_COLUMN + " DATE NOT NULL,\n" +
                "   SO" + SONGAYCONG_COLUMN + " INTEGER NOT NULL,\n" +
                "   PRIMARY KEY(" + MANV_COLMN + ", " + NGAY_COLUMN + "GHISO)\n" +
                "   CONSTRAINT fk_CHAMCONG_NV\n" +
                "    \tFOREIGN KEY (" + MANV_COLMN + ")\n" +
                "    \tREFERENCES " + NHANVIEN_TABLE + "(" + MANV_COLMN + ") ON DELETE CASCADE" +
                ");";
//                "CREATE TABLE NHANVIEN(" +
//                "MANV INTEGER PRIMARY KEY," +
//                "HOTEN TEXT NOT NULL," +
//                "NGAYSINH DATETIME NOT NULL," +
//                "MAPB INT NOT NULL," +
//                "MUCLUONG INT NOT NULL);" +
//                "  CREATE TABLE CHAMCONG(" +
//                "MANV INT NOT NULL," +
//                "NGAYGHISO DATTIME NOT NULL," +
//                "SONGAYCONG INT NOT NULL);" +
//                "CREATE TABLE PHONGBAN(" +
//                "MAPB INT PRIMARY KEY," +
//                "TENPB TEXT NOT NULL);" +
//                "CREATE TABLE TAMUNG(" +
//                "SOPHIEU INT PRIMARY KEY," +
//                "NGAY INT NOT NULL," +
//                "MANV INT NOT NULL," +
//                "SOTIEN INT NOT NULL);" +
//                "";
        db.execSQL(createST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
