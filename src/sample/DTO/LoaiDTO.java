package sample.DTO;

public class LoaiDTO {
    public LoaiDTO() {
    }

    public String getIDLOAI() {
        return IDLOAI;
    }

    public void setIDLOAI(String IDLOAI) {
        this.IDLOAI = IDLOAI;
    }

    public String getTENLOAI() {
        return TENLOAI;
    }

    public void setTENLOAI(String TENLOAI) {
        this.TENLOAI = TENLOAI;
    }


    public LoaiDTO(String IDLOAICAY, String TENLOAI) {
        this.IDLOAI = IDLOAICAY;
        this.TENLOAI = TENLOAI;
    }
    String IDLOAI;
    String TENLOAI;
}
