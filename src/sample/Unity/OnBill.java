package sample.Unity;

import java.math.BigDecimal;

public class OnBill {
    public OnBill(String IDHH, String TENHH, int SOLUONG, BigDecimal GIA) {
        this.IDHH = IDHH;
        this.TENHH = TENHH;
        this.SOLUONG = SOLUONG;
        this.GIA = GIA;
    }


    public OnBill() {
    }

    public String getIDHH() {
        return IDHH;
    }

    public void setIDHH(String IDHH) {
        this.IDHH = IDHH;
    }

    public String getTENHH() {
        return TENHH;
    }

    public void setTENHH(String TENHH) {
        this.TENHH = TENHH;
    }

    public int getSOLUONG() {
        return SOLUONG;
    }

    public void setSOLUONG(int SOLUONG) {
        this.SOLUONG = SOLUONG;
    }

    public BigDecimal getGIA() {
        return GIA;
    }

    public void setGIA(BigDecimal GIA) {
        this.GIA = GIA;
    }

    String IDHH;
    String TENHH;
    int SOLUONG;
    BigDecimal GIA;
}
