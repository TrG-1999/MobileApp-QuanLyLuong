package com.example.quanlyluong.Data;

public class NV_ThongKe {
    private int maNV;
    private String tenNV;
    private int maPB;
    private int tongLuong;

    public NV_ThongKe(int maNV, String tenNV, int maPB, int tongLuong) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.maPB = maPB;
        this.tongLuong = tongLuong;
    }

    public NV_ThongKe() {
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public void setMaPB(int maPB) {
        this.maPB = maPB;
    }

    public void setTongLuong(int tongLuong) {
        this.tongLuong = tongLuong;
    }

    public int getMaNV() {
        return maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public int getMaPB() {
        return maPB;
    }

    public int getTongLuong() {
        return tongLuong;
    }
}
