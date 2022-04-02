package sample.Controller;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.DTO.*;
import sample.U.AppContain;
import sample.U.Cre;
import sample.Unity.OnBill;
import sample.connSQL.DBconnect;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.scene.control.ButtonType.OK;

public class CashierController implements Initializable {

    @FXML
    private TextField txtTongTien,txtTienThua,txtTienNhan;
    @FXML
    private Label txtTamtinh,iduser,txtNgayBan;
    @FXML
    private TableView<HangHoaDTO> tableCay;
    @FXML
    private TableColumn<HangHoaDTO, String> cayCol;
    @FXML
    private TableColumn<HangHoaDTO, String> tcayCol;
    @FXML
    private TableColumn<HangHoaDTO, String> tonCol;
    @FXML
    private TableColumn<HangHoaDTO, String> giaCCol;
    @FXML
    private Button btnInHD;

    @FXML
    private TableView<OnBill> hdTable;
    @FXML
    private TableColumn<OnBill, String> idCol;
    @FXML
    private TableColumn<OnBill, String> tenCol;
    @FXML
    private TableColumn<OnBill, Integer> slCol;
    @FXML
    private TableColumn<OnBill, String> giaCol;
    @FXML
    private TableColumn<OnBill, Void > edit;
    public static ObservableList<HangHoaDTO> caylist= FXCollections.observableArrayList();
    public static ObservableList<OnBill> ctHDlist= FXCollections.observableArrayList();
    private static String username = "";
    Connection conn ;
    ResultSet rs;
    PreparedStatement prst;
    public CashierController() {
        ctHDlist = FXCollections.observableList(new ArrayList<>());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ProductionManagementController.cayObservableList = caylist;
        username = LoginController.loggerUsername;
        iduser.setText(username);
        loadDataCay();
        loadDataHD();
        setMoney();
        btnInHD.setDisable(true);
        txtNgayBan.setText(LocalDate.now(ZoneId.systemDefault()).toString());

        txtTongTien.setDisable(true);
        txtTienThua.setDisable(true);

        txtTienNhan.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                    if (!newValue.matches("\\d*")) {
                        txtTienNhan.setText(newValue.replaceAll("[^\\d]", ""));
                    }
            }
        });
    }

    private void refreshCayTable() {
        try {
            caylist.clear();
            String query = AppContain.HH_OP;
            PreparedStatement pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()){
                caylist.add(new HangHoaDTO(
                        rs.getString(AppContain.IDHH),
                        rs.getString(AppContain.TENHH),
                        rs.getInt(AppContain.SOLUONGHANG),
                        rs.getBigDecimal(AppContain.GIA)));
                tableCay.setItems(caylist);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductionManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * LOAD DỮ LIỆU CÂY
     */
    private void loadDataCay() {
        conn = DBconnect.getConnect();
        tableCay.refresh();
        refreshCayTable();
        cayCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.IDHH));
        tcayCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.TENHH));
        tonCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.SOLUONGHANG));
        giaCCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.GIA));
    }

    private void loadDataHD() {
        conn = DBconnect.getConnect();
        hdTable.refresh();

        tenCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.TENHH));
        slCol.setCellValueFactory(new PropertyValueFactory<>("SOLUONG"));
        giaCol.setCellValueFactory(new PropertyValueFactory<>("GIA"));
        edit.impl_setReorderable(false);
        edit.setCellFactory(t -> {
            TableCell<OnBill, Void> cell = new TableCell<OnBill, Void>() {
                final Button editButton = new Button("Xóa");
                {
                    editButton.setOnAction(t -> {
                        OnBill cayedit = hdTable.getItems().get(getIndex());
                        hdTable.getItems().remove(cayedit);
                        ctHDlist.remove(cayedit);
                    });
                }
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : editButton);
                    setAlignment(Pos.CENTER);
                }
            };
            return cell;
        });
    }
    //
    private BigDecimal tientamtinh = new BigDecimal(0);
    private BigDecimal tongtien = new BigDecimal(0);
    public void setMoney(){
        //Xem tiền tạm tính đơn giá * số lượng của toàn bộ cây
        ctHDlist.addListener((ListChangeListener<? super OnBill>) change ->{
            tientamtinh = tientamtinh.subtract(tientamtinh);
            for (OnBill chiTietCDTO : ctHDlist) {
                tientamtinh = tientamtinh.add((chiTietCDTO.getGIA()).multiply(new BigDecimal(chiTietCDTO.getSOLUONG())));
            }
            txtTamtinh.setText(tientamtinh.toString());

            //Tính tiền thừa = tiền nhận - tổng tiền
            if (!txtTienThua.getText().trim().isEmpty()) {
                BigDecimal tienthoi = new BigDecimal(txtTienNhan.getText());
                txtTienThua.setText(tienthoi.subtract(tongtien).toString());
            }
        } );
        // Tiền tạm tính = tổng tiền
        txtTamtinh.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            tongtien = tientamtinh.divide(new BigDecimal(1));
            txtTongTien.setText(tongtien.toString());
        }));

        txtTienNhan.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                BigDecimal change = new BigDecimal(newValue).subtract(tongtien);
                txtTienThua.setText(change.toString());
                btnInHD.setDisable(BigDecimal.valueOf(Float.parseFloat(newValue.trim())).compareTo(tongtien) < 0);// Tiền bé hơn 0 nút in hđ null
            } else {
                txtTienThua.clear();
            }
        }));
    }
    void refresh(){
        hdTable.getItems().clear();
        txtTongTien.clear();
        txtTienNhan.clear();
        txtTienThua.clear();
    }
    public void refresh(ActionEvent event) {
        refresh();
    }
    // Xem cây có chưa
    public boolean checkIfContains(ObservableList<OnBill> cayonBill, String IDHH) {
        for (OnBill cay : cayonBill) {
            if (cay.getIDHH().equals(IDHH)) {
                return true;
            }
        }
        return false;
    }
    //xem có trong danh sách chưa chưa có thì dô
    public OnBill getCOnBillByCay(ObservableList<OnBill> observableList, String IDHH) {
        for (OnBill cay : observableList) {
            if (cay.getIDHH().equals(IDHH)) {
                return cay;
            }
        }
        return null;
    }
    @FXML

    private void setBill(MouseEvent event) {
        ObservableList<HangHoaDTO> observableList = caylist;
        HangHoaDTO caydto = tableCay.getSelectionModel().getSelectedItem();
        OnBill cayDTO = new OnBill(caydto.getIDHH(), caydto.getTENHH(), 1, caydto.getGIA());
        //chưa có thì +1
        if (!checkIfContains(ctHDlist, cayDTO.getIDHH())) {
            ctHDlist.add(cayDTO);
            hdTable.setItems(ctHDlist);
        } else {
            OnBill tangSL = getCOnBillByCay(ctHDlist, cayDTO.getIDHH());
            // có rùi thì tăng lên 1
            if (tangSL != null) {
                tangSL.setSOLUONG(tangSL.getSOLUONG() + 1);
                ctHDlist.set(ctHDlist.indexOf(tangSL), tangSL);
            }
        }
    }

    public void addBill(ActionEvent event) {
        List<OnBill> list = hdTable.getItems();
        // Sử dụng for each ( Cú pháp for( Kiểu_dữ_liệu Biến : List )
        for (OnBill caybill : list){
            try {
                // Add chi tiết
                String query = AppContain.ADDCT_OP;
                prst = conn.prepareStatement(query);
                prst.setString(1, caybill.getIDHH());
                prst.setInt(2, caybill.getSOLUONG());
                prst.setBigDecimal(3, caybill.getGIA());
                prst.executeUpdate();
            }
            catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        String date = String.valueOf(txtNgayBan.getText());
        String thanhtien =  String.valueOf(txtTongTien.getText());
        String tiennhan = String.valueOf(txtTienNhan.getText());
        String tienthoi =  String.valueOf(txtTienNhan.getText());
        String user = iduser.getText();
        try {
            String query = AppContain.ADDHD_OP;
            prst = conn.prepareStatement(query);
            prst.setString(1, date);
            prst.setString(2, thanhtien);
            prst.setString(3, tiennhan);
            prst.setString(4, tienthoi);
            prst.setString(5, user);
            prst.executeUpdate();
            Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.HD_SUCCESS, Alert.AlertType.NONE, OK);
            btnInHD.setDisable(true);
            alert.show();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        refresh();
    }
}
