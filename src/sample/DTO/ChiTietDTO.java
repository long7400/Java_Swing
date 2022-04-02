package sample.DTO;

import java.math.BigDecimal;

public class ChiTietDTO {

    public ChiTietDTO(String IDGIO, String IDHH, int SOLUONGC, BigDecimal DONGGIA) {
        this.IDGIO = IDGIO;
        this.IDHH = IDHH;
        this.SOLUONGC = SOLUONGC;
        this.DONGGIA = DONGGIA;
    }

    public ChiTietDTO(String IDGIO, HangHoaDTO hangHoaDTO, int soluongc, BigDecimal donggia) {
        this.IDGIO = IDGIO;
        this.hangHoaDTO = hangHoaDTO;
        SOLUONGC = soluongc;
        DONGGIA = donggia;
    }


    public ChiTietDTO() {

    }
    private HangHoaDTO hangHoaDTO;

    public HangHoaDTO getHangHoaDTO() {
        return hangHoaDTO;
    }

    public String getTenHH() {
        return TENHH= hangHoaDTO.getTENHH();
    }

    private  String TENHH;;
    //==============================

    public String getIDGIOC() {
        return IDGIO;
    }

    public void setIDGIO(String IDGIOCAY) {
        this.IDGIO = IDGIOCAY;
    }

    public String getIDHH() {
        return IDHH;
    }

    public void setIDHH(String IDCAY) {
        this.IDHH = IDCAY;
    }

    public int getSOLUONGC() {
        return SOLUONGC;
    }

    public void setSOLUONGC(int SOLUONGC) {
        this.SOLUONGC = SOLUONGC;
    }

    public BigDecimal getDONGGIA() {
        return DONGGIA;
    }

    public void setDONGGIA(BigDecimal DONGGIA) {
        this.DONGGIA = DONGGIA;
    }

    String IDGIO;
    String IDHH;
    int SOLUONGC;
    BigDecimal DONGGIA;
}
