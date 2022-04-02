package sample.DTO;

public class KhachHangDTO {


    public KhachHangDTO() {
    }


    public String getIDKH() {
        return IDKH;
    }

    public void setIDKH(String IDKH) {
        this.IDKH = IDKH;
    }

    public String getTENKH() {
        return TENKH;
    }

    public void setTENKH(String TENKH) {
        this.TENKH = TENKH;
    }

    public String getDIACHI() {
        return DIACHI;
    }

    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }


    public KhachHangDTO(String IDKH, String TENKH, String DIACHI, String SDT) {
        this.IDKH = IDKH;
        this.TENKH = TENKH;
        this.DIACHI = DIACHI;
        this.SDT = SDT;
    }
    String IDKH;
    String TENKH;
    String DIACHI;
    String SDT;
}
