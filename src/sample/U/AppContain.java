package sample.U;


// Tóm gọn lại cả đống này là cái nào sử dụng nhiều lần thì sài cái này cho lẹ
public class AppContain {
    //GIAO DIỆN 2
    public static final String FORM_ADMIN_UI = "View/Admin.fxml";
    public static final String FORM_LOGIN_UI = "View/Login.fxml";
    public static final String FORM_CONSTACTUS_UI = "View/CONSTACTUS.fxml";
    public static final String FORM_ADMIN2_UI ="View/NavBar.fxml";

    //GIAO DIỆN 1
    public static final String FORM_PRODUCTMANAGEMENT_UI ="ProductionManagement";
    public static final String FORM_RESS_UI = "Registration";
    public static final String FORM_EMPLOY_UI = "EmployeeManagement";
    public static final String FORM_CASHIER_UI = "Cashier";

    //THÔNG BÁO
    public static final String TITLE = "Chú ý";
    public static final String TITLE2 = "Thông báo";
    public static final String HEADER_TITLE = "Nhập đầy đủ thông tin";
    public static final String IDTEST_TITLE = "Đã tồn tại";
    public static final String ERROR_TITLE = "Lỗi chọn đối tượng";
    public static final String ERRORDELETE_TITLE = "Không tìm thấy đối tượng";
    public static final String CHECK_ACCOUT = "Kiểm tra lại thông tin đăng nhập";
    public static final String SUCC_MESSAGE = "Thành công!!!";
    public static final String FAILS_MESSAGE = "Thất bại!!!";
    public static final String HD_SUCCESS = "Thanh toán thành công";

     //TRUY VẤN ĐĂNG NHẬP
    public  static final String RES2_OP = "INSERT INTO DANGNHAP(USERNAME,PASSWORD,GMAIL,PHANQUYEN) VALUES (?, ?, ? , ?)";
    public  static final String LOG_OP = "SELECT * FROM DANGNHAP where USERNAME = ? and PASSWORD = ? ";
    public  static final String CHECK_OP = "SELECT * FROM DANGNHAP ";
    public  static final String GETLISTOFACC_OP = "Select * FROM  DANGNHAP D, NHANVIEN N WHERE D.IDNV=N.IDNV";
    public  static final String ADDACCOUNT_OP = "INSERT INTO DANGNHAP(USERNAME,PASSWORD,GMAIL,PHANQUYEN,IDNV) VALUES (?,?,?,?,?)";

    //TRUY VẤN HÀNG HÓA
    public  static final String GETLISTOFHH_OP = "Select * FROM  HANGHOA H, LOAI L, NOINHAPKHAU N where H.IDLOAI=L.IDLOAI and H.IDNNK=N.IDNNK";
    public  static final String ADDHH_OP = "INSERT INTO HANGHOA(TENHH,SOLUONG,GIA,IDLOAI,IDNNK) VALUES (?,?,?,?,?)";
    public  static final String HH_OP = "SELECT IDHH, TENHH,SOLUONG,GIA FROM HANGHOA";

    //CHI TIẾT HD
    public static final String ADDHD_OP = "INSERT INTO GIOHANG(NGAYGIAODICH,THANHTIEN,TIENNHAN,TIENTHOI,USERNAME) VALUES (?,?,?,?,?)";
    public  static final String CTHD_OP = "Select * FROM  HANGHOA H, CTGIOHANG CT where H.IDHH= CT.IDHH";
    public  static final String ADDCT_OP = "INSERT INTO CTGIOHANG(IDHH,SOLUONGC,DONGGIA) VALUES (?,?,?)";
    public  static final String GIOHANG_OP = "SELECT * FROM GIOHANG";
    public  static final String TOTALGIOHANG_OP = "select count(IDGIO) FROM GIOHANG";
    //TRUY VẤN LOẠI HÀNG
    public  static final String CBSHOWLOAI_OP = "SELECT * FROM LOAI";
    public  static final String ADDLOAI_OP = "INSERT INTO LOAI(TENLOAI) VALUES (?)";

    //TRUY VẤN NHẬP KHẨU
    public  static final String CBSHOWNNK_OP = "SELECT * FROM NOINHAPKHAU";
    public  static final String ADDNK_OP = "INSERT INTO NOINHAPKHAU(TENNNK,DIACHI,SDT) VALUES (?,?,?)";

    //TRUY VẤN NHÂN VIÊN
    public  static final String CBSHOWNV_OP = "SELECT * FROM NHANVIEN";
    public  static final String ADDNV_OP = "INSERT INTO NHANVIEN(TENNV,SODT,DIACHI,LUONG) VALUES (?,?,?,?)";

    // TRUY VẤN KHÁCH HÀNG
    public  static final String CBSHOWKH_OP = "SELECT * FROM KHACHHANG";
    public  static final String ADDKH_OP = "INSERT INTO KHACHHANG(TENKH,DIACHI,SDT) VALUES (?,?,?)";

    // HÀNG HÓA
    public  static final String IDHH = "IDHH";
    public  static final String TENHH = "TENHH";
    public  static final String SOLUONGHANG = "SOLUONG";
    public  static final String GIA = "GIA";
    public  static final String IDLOAI = "IDLOAI";
    public  static final String IDNNK = "IDNNK";

    //ĐĂNG NHẬP
    public  static final String USERNAME = "USERNAME";
    public  static final String PASSWORD = "PASSWORD";
    public  static final String GMAIL = "GMAIL";
    public  static final String PHANQUYEN = "PHANQUYEN";


    //NNK
    public  static final String TENNNK = "TENNNK";
    public  static final String DIACHI = "DIACHI";
    public  static final String SDT = "SDT";

    //LOẠI
    public  static final String TENLOAI = "TENLOAI";

    //KHÁCH HÀNG
    public  static final String IDKH = "IDKH";
    public  static final String TENKH = "TENKH";
    public  static final String DIACHIKH = "DIACHI";
    public  static final String SDTKH = "SDT";

    //NHÂN VIÊN
    public  static final String IDNV = "IDNV";
    public  static final String TENNV = "TENNV";
    public  static final String SDTNV = "SODT";
    public  static final String LUONGNV = "LUONG";
    public  static final String DCNV = "DIACHI";

    //HÓA ĐƠN
    public  static final String IDGIO = "IDGIO";
    public  static final String NGAYGIAODICH = "NGAYGIAODICH";
    public  static final String THANHTIEN = "THANHTIEN";
    public  static final String TIENNHAN = "TIENNHAN";
    public  static final String TIENTHOI = "TIENTHOI";

    //CHI TIẾT
    public  static final String SOLUONGC = "SOLUONGC";
    public  static final String DONGGIA = "DONGGIA";


}
