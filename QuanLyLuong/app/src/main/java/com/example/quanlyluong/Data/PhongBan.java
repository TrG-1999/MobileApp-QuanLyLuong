package com.example.quanlyluong.Data;

public class PhongBan {
    private int maPB;
    private String tenPB;

    public PhongBan() {
    }

    public PhongBan(int maPB, String tenPB) {
        this.maPB = maPB;
        this.tenPB = tenPB;
    }

    public void setMaPB(int maPB) {
        this.maPB = maPB;
    }

    public void setTenPB(String tenPB) {
        this.tenPB = tenPB;
    }

    public int getMaPB() {
        return maPB;
    }

    public String getTenPB() {
        return tenPB;
    }
}
