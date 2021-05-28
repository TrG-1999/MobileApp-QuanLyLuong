package com.example.quanlyluong.Data;

import java.util.Date;

public class NV {
    private int maNV;
    private String hoTen;
    private Date ngaySinh;
    private int maPB;
    private int mucLuong;
    private byte[] photo;
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }


    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void setMaPB(int maPB) {
        this.maPB = maPB;
    }

    public void setMucLuong(int mucLuong) {
        this.mucLuong = mucLuong;
    }

    public int getMaNV() {
        return maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public int getMaPB() {
        return maPB;
    }

    public int getMucLuong() {
        return mucLuong;
    }

    public NV() {
    }

    public NV(int maNV, String hoTen, Date ngaySinh, int maPB, int mucLuong, byte[] photo) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.maPB = maPB;
        this.mucLuong = mucLuong;
        this.photo = photo;
    }
}
