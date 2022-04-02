package sample.DTO;

public class NhanVienDTO {

    public NhanVienDTO( String idnv, String tennv) {
        IDNV = idnv;
        TENNV = tennv;
    }

    public NhanVienDTO(String IDNV, String TENNV, String SODT, String DIACHI, int LUONG) {
        this.IDNV = IDNV;
        this.TENNV = TENNV;
        this.SODT = SODT;
        this.DIACHI = DIACHI;
        this.LUONG = LUONG;
    }




    public String getIDNV() {
        return IDNV;
    }

    public void setIDNV(String IDNV) {
        this.IDNV = IDNV;
    }

    public String getTENNV() {
        return TENNV;
    }

    public void setTENNV(String TENNV) {
        this.TENNV = TENNV;
    }

    public String getSODT() {
        return SODT;
    }

    public void setSODT(String SODT) {
        this.SODT = SODT;
    }

    public String getDIACHI() {
        return DIACHI;
    }

    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;
    }

    public int getLUONG() {
        return LUONG;
    }

    public void setLUONG(int LUONG) {
        this.LUONG = LUONG;
    }

    String IDNV;
    String TENNV;
    String SODT;
    String DIACHI;
    int LUONG;
}
