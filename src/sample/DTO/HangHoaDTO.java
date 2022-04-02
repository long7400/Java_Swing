package sample.DTO;

import java.math.BigDecimal;

public class HangHoaDTO {

    public HangHoaDTO(String IDHH, String tenhh, int asoluong, BigDecimal agia, LoaiDTO loaiDTO, NhapKhauDTO nhapKhauDTO) {
        this.IDHH = IDHH;
        TENHH = tenhh;
        SOLUONG = asoluong;
        GIA = agia;
        this.loaiDTO = loaiDTO;
        this.nhapKhauDTO = nhapKhauDTO;
    }
//    public CayCanhDTO(String IDCAY, String TENCAY, String TINHTRANG, float GIA, String IDLOAICAY, String IDNNK) {
//        this.IDCAY = IDCAY;
//        this.TENCAY = TENCAY;
//        this.TINHTRANG = TINHTRANG;
//        this.GIA = GIA;
//        this.IDLOAICAY = IDLOAICAY;
//        this.IDNNK = IDNNK;
//    }
    private NhapKhauDTO nhapKhauDTO;


    public HangHoaDTO(String IDHH, String atencay, int sl, BigDecimal agia) {
        this.IDHH = IDHH;
        TENHH = atencay;
        SOLUONG = sl;
        GIA = agia;
    }

    public HangHoaDTO(String IDHH, String tenhh) {
        this.IDHH = IDHH;
        TENHH = tenhh;
    }


    public NhapKhauDTO getNhapKhauDTO() {
        return nhapKhauDTO;
    }

    public String getTenNNK() {
        return TENNNK=nhapKhauDTO.getTENNNK();
    }

    private  String  TENNNK;
    //===========================
    private LoaiDTO loaiDTO;

    public LoaiDTO getLoaiDTO() {
        return loaiDTO;
    }

    public String getTenLoai() {
        return TENLOAI= loaiDTO.getTENLOAI();
    }

    private  String TENLOAI;

    //=======================

    public String getIDHH() {
        return IDHH;
    }

    public void setIDHH(String IDCAY) {
        this.IDHH = IDHH;
    }

    public String getTENHH() {
        return TENHH;
    }

    public void setTENHH(String TENCAY) {
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

    public String getIDLOAICAY() {
        return IDLOAICAY;
    }

    public void setIDLOAICAY(String IDLOAICAY) {
        this.IDLOAICAY = IDLOAICAY;
    }

    public String getIDNNK() {
        return IDNNK;
    }

    public void setIDNNK(String IDNNK) {
        this.IDNNK = IDNNK;
    }



//    public CayCanhDTO(String IDCAY, String TENCAY, int SOLUONG, int GIA, String IDLOAICAY, String IDNNK) {
//        this.IDCAY = IDCAY;
//        this.TENCAY = TENCAY;
//        this.SOLUONG = SOLUONG;
//        this.GIA = GIA;
//        this.IDLOAICAY = IDLOAICAY;
//        this.IDNNK = IDNNK;
//    }
    String IDHH;
    String TENHH;
    int SOLUONG;
    BigDecimal GIA;
    String IDLOAICAY;
    String IDNNK;


}
