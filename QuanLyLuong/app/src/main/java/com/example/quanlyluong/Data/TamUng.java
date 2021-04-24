package com.example.quanlyluong.Data;

import java.util.Date;

public class TamUng {
    private int soPhieu;
    private Date ngay;
    private int maNV;
    private int soTien;

    public TamUng() {
    }

    public TamUng(int soPhieu, Date ngay, int maNV, int soTien) {
        this.soPhieu = soPhieu;
        this.ngay = ngay;
        this.maNV = maNV;
        this.soTien = soTien;
    }

    public int getSoPhieu() {
        return soPhieu;
    }

    public void setSoPhieu(int soPhieu) {
        this.soPhieu = soPhieu;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }

    @Override
    public String toString() {
        return "TamUng{" +
                "soPhieu=" + soPhieu +
                ", ngay=" + ngay +
                ", maNV=" + maNV +
                ", soTien=" + soTien +
                '}';
    }
}
