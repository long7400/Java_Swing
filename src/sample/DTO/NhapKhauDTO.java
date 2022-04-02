package sample.DTO;

public class NhapKhauDTO {

    public NhapKhauDTO(String id, String ten) {
        IDNNK = id;
        TENNNK = ten;
    }

    public String getIDNNK() {
        return IDNNK;
    }

    public void setIDNNK(String IDNNK) {
        this.IDNNK = IDNNK;
    }

    public String getTENNNK() {
        return TENNNK;
    }

    public void setTENNNK(String TENNNK) {
        this.TENNNK = TENNNK;
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

    public NhapKhauDTO(String IDNNK, String TENNNK, String DIACHI, String SDT) {
        this.IDNNK = IDNNK;
        this.TENNNK = TENNNK;
        this.DIACHI = DIACHI;
        this.SDT = SDT;
    }

    String IDNNK;
    String TENNNK;
    String DIACHI;
    String SDT;
}
