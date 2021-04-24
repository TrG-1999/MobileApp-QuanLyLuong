package com.example.quanlyluong.DAO;

import android.content.Context;
import android.database.Cursor;
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
    SQLiteDatabase temp;
    public DatabaseHelper(Context context) {
        super(context, "QUAN_LI_LUONG", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createST = "CREATE TABLE NHANVIEN(\n" +
                "  \tMANV INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  \tHOTEN TEXT NOT NULL,\n" +
                "  \tNGAYSINH DATETIME NOT NULL,\n" +
                "  \tMAPB INT NOT NULL,\n" +
                "  \tMUCLUONG INT NOT NULL,\n" +
                " \tCONSTRAINT fk_PHONGBAN_NV\n" +
                "    \tFOREIGN KEY (MAPB)\n" +
                "    \tREFERENCES PHONGBANM(MAPB)\n" +
                "\tON DELETE CASCADE\n" +
                " )";
        db.execSQL(createST);
        String createST1 = "CREATE TABLE TAMUNG(\n" +
                "   SOPHIEU INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "   NGAY DATETIME NOT NULL,\n" +
                "   MANV INTEGER NOT NULL,\n" +
                "   SOTIEN INTEGER NOT NULL,\n" +
                "   CONSTRAINT fk_TAMUNG_NV\n" +
                "    \tFOREIGN KEY (MANV)\n" +
                "    \tREFERENCES NHANVIEN(MANV)\n" +
                " );";
        db.execSQL(createST1);
        String createST2 = "CREATE TABLE PHONGBAN(\n" +
                "  MAPB INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  TENPB TEXT NOT NULL\n" +
                ");";
        db.execSQL(createST2);
        String createST3 = "CREATE TABLE CHAMCONG(\n" +
                "   MANV INTEGER NOT NULL,\n" +
                "   NGAYGHISO DATETIME NOT NULL,\n" +
                "   SONGAYCONG INTEGER NOT NULL,\n" +
                "   PRIMARY KEY(MANV, NGAYGHISO)\n" +
                ");";
        db.execSQL(createST3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}