package sample.DTO;

public class LoginDTO {



    public LoginDTO(String USERNAME, String password, String gmail, String phanquyen, NhanVienDTO nhanVienDTO) {
        this.USERNAME = USERNAME;
        PASSWORD = password;
        GMAIL = gmail;
        PHANQUYEN = phanquyen;
        this.nhanVienDTO = nhanVienDTO;

    }
    private NhanVienDTO nhanVienDTO;


    public NhanVienDTO getNhanVienDTO() {
        return nhanVienDTO;
    }

    public String getTenNV() {
        return TENNV=nhanVienDTO.getTENNV();
    }

    private  String  TENNV;

//
//    public LoginDTO(String USERNAME, String PASSWORD, String PHANQUYEN, String GMAIL, String IDNV) {
//        this.USERNAME = USERNAME;
//        this.PASSWORD = PASSWORD;
//        this.PHANQUYEN = PHANQUYEN;
//        this.GMAIL = GMAIL;
//        this.IDNV = IDNV;
//    }
//

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
    public String getGMAIL() {
        return GMAIL;
    }

    public void setGMAIL(String GMAIL) {
        this.GMAIL = GMAIL;
    }

    public String getPHANQUYEN() {
        return PHANQUYEN;
    }

    public void setPHANQUYEN(String PHANQUYEN) {
        this.PHANQUYEN = PHANQUYEN;
    }

    public String getIDNV() {
        return IDNV;
    }

    public void setIDNV(String IDNV) {
        this.IDNV = IDNV;
    }

    String USERNAME;
    String PASSWORD;
    String GMAIL;
    String PHANQUYEN;
    String IDNV;
}
