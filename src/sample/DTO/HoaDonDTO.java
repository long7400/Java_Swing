package sample.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class HoaDonDTO {



    public HoaDonDTO(String IDGIO, Date NGAYGIAODICH, BigDecimal THANHTIEN, BigDecimal TIENNHAN, BigDecimal TIENTHOI, String USERNAME) {
        this.IDGIO = IDGIO;
        this.NGAYGIAODICH = NGAYGIAODICH;
        this.THANHTIEN = THANHTIEN;
        this.TIENNHAN = TIENNHAN;
        this.TIENTHOI = TIENTHOI;
        this.USERNAME = USERNAME;
    }

    public HoaDonDTO() {

    }

    public String getIDGIO() {
        return IDGIO;
    }

    public void setIDGIO(String IDGIOCAY) {
        this.IDGIO = IDGIOCAY;
    }

    public Date getNGAYGIAODICH() {
        return NGAYGIAODICH;
    }

    public void setNGAYGIAODICH(Date NGAYGIAODICH) {
        this.NGAYGIAODICH = NGAYGIAODICH;
    }

    public BigDecimal getTHANHTIEN() {
        return THANHTIEN;
    }

    public void setTHANHTIEN(BigDecimal THANHTIEN) {
        this.THANHTIEN = THANHTIEN;
    }

    public BigDecimal getTIENNHAN() {
        return TIENNHAN;
    }

    public void setTIENNHAN(BigDecimal TIENNHAN) {
        this.TIENNHAN = TIENNHAN;
    }

    public BigDecimal getTIENTHOI() {
        return TIENTHOI;
    }

    public void setTIENTHOI(BigDecimal TIENTHOI) {
        this.TIENTHOI = TIENTHOI;
    }


    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    String IDGIO;
    Date NGAYGIAODICH;
    BigDecimal THANHTIEN;
    BigDecimal TIENNHAN;
    BigDecimal TIENTHOI;
    String USERNAME;

}
