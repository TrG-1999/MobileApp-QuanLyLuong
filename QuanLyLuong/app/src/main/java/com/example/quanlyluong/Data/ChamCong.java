package com.example.quanlyluong.Data;

import java.util.Date;

public class ChamCong {
    private int maNV;
    private Date ngayGhiSo;
    private int soNgayCong;

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public Date getNgayGhiSo() {
        return ngayGhiSo;
    }

    public void setNgayGhiSo(Date ngayGhiSo) {
        this.ngayGhiSo = ngayGhiSo;
    }

    public int getSoNgayCong() {
        return soNgayCong;
    }

    public void setSoNgayCong(int soNgayCong) {
        this.soNgayCong = soNgayCong;
    }

    public ChamCong() {
    }

    public ChamCong(int maNV, Date ngayGhiSo, int soNgayCong) {
        this.maNV = maNV;
        this.ngayGhiSo = ngayGhiSo;
        this.soNgayCong = soNgayCong;
    }

    @Override
    public String toString() {
        return "ChamCong{" +
                "maNV=" + maNV +
                ", ngayGhiSo=" + ngayGhiSo +
                ", soNgayCong=" + soNgayCong +
                '}';
    }
}
