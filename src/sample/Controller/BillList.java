package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.text.Text;
import sample.DTO.HoaDonDTO;


import sample.U.AppContain;
import sample.U.Cre;
import sample.connSQL.DBconnect;
import java.net.URL;
import java.sql.*;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BillList implements Initializable {
    @FXML
    private Label todaynow,tonghd,tongthunhap;
    @FXML
    private Button buttonsreach,linecharbt;
    @FXML
    private TableView<HoaDonDTO> hdtable;
    @FXML
    private TableColumn<HoaDonDTO, String> idhdCol;
    @FXML
    private TableColumn<HoaDonDTO, String> dateCol;
    @FXML
    private TableColumn<HoaDonDTO, String> tongCol;
    @FXML
    private TableColumn<HoaDonDTO, String> nhanCol;
    @FXML
    private TableColumn<HoaDonDTO, String> reCol;
    @FXML
    private TableColumn<HoaDonDTO, String> nameCol;
    @FXML
    private LineChart<String, Integer> lineChart;
    @FXML
    private Text tnText,hdText;
    @FXML
    private Button idCT;
    @FXML
    private CategoryAxis dateAxis;
    @FXML
    private NumberAxis amountAxis;
    @FXML
    private DatePicker datePikcer;
    public static ObservableList<HoaDonDTO> hoadonObservableList= FXCollections.observableArrayList();
    Connection conn = DBconnect.getConnect();;
    ResultSet rs = null ;
    PreparedStatement prst ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
        tnText.setVisible(false);
    }
    Double total = 0.0;
    Integer totalhd = 0;
    public static String mhd = "";
    public static String id = "";
    public static String total1 = "";
    private void refreshTable() {
        try {
            hoadonObservableList.clear();
            String query = AppContain.GIOHANG_OP;
            PreparedStatement pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()){
                hoadonObservableList.add(new HoaDonDTO(
                        rs.getString(AppContain.IDGIO),
                        rs.getDate(AppContain.NGAYGIAODICH),
                        rs.getBigDecimal(AppContain.THANHTIEN),
                        rs.getBigDecimal(AppContain.TIENNHAN),
                        rs.getBigDecimal(AppContain.TIENTHOI),
                        rs.getString(AppContain.USERNAME)));
                hdtable.setItems(hoadonObservableList);
                total += rs.getDouble(AppContain.THANHTIEN);
                totalhd++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductionManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tongthunhap.setText(total.toString() + "VND");
        tonghd.setText(totalhd.toString());
    }
    private void loadData() {
        hdtable.refresh();
        refreshTable();
        idhdCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.IDGIO));
        dateCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.NGAYGIAODICH));
        tongCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.THANHTIEN));
        nhanCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.TIENNHAN));
        reCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.TIENTHOI));
        nameCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.USERNAME));
    }
//    Double total1 = 0.0;
//    Integer totalhd1 = 0;
//    private void refreshWithDate() {
//
//        try {
//            String txt = String.valueOf(datePikcer.getValue());
//            hoadonObservableList.clear();
//            PreparedStatement pst = conn.prepareStatement("SELECT * FROM GIOHANG WHERE NGAYGIAODICH ='"+txt+"'");
//            rs = pst.executeQuery();
//            while (rs.next()){
//                hoadonObservableList.add(new HoaDonDTO(
//                        rs.getString(AppContain.IDGIO),
//                        rs.getDate(AppContain.NGAYGIAODICH),
//                        rs.getBigDecimal(AppContain.THANHTIEN),
//                        rs.getBigDecimal(AppContain.TIENNHAN),
//                        rs.getBigDecimal(AppContain.TIENTHOI),
//                        rs.getString(AppContain.USERNAME)));
//                hdtable.setItems(hoadonObservableList);
//                total1 += rs.getDouble(AppContain.THANHTIEN);
//                totalhd1++;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ProductionManagementController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        tongthunhap.setText(total1.toString() + "VND");
//        tonghd.setText(totalhd1.toString());
//    }
//    private void loadWithDate() {
//        hdtable.refresh();
//        refreshWithDate();
//        idhdCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.IDGIO));
//        dateCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.NGAYGIAODICH));
//        tongCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.THANHTIEN));
//        nhanCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.TIENNHAN));
//        reCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.TIENTHOI));
//        nameCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.USERNAME));
//    }
//    public void loadDateNow(javafx.event.ActionEvent event) {
//        loadWithDate();
//    }
//
    private void setLineChart() {
        lineChart.getData().clear();
        try {
                prst = conn.prepareStatement("SELECT NGAYGIAODICH, sum(THANHTIEN) FROM GIOHANG  GROUP BY NGAYGIAODICH ORDER BY NGAYGIAODICH ASC ");
            rs = prst.executeQuery();
            XYChart.Series chartData = new XYChart.Series<>();
            while(rs.next()) {
                chartData.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
            }
            lineChart.getData().addAll(chartData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void showchar(javafx.event.ActionEvent event) {
        setLineChart();
        hdtable.setVisible(false);
        lineChart.setVisible(true);
        tnText.setVisible(true);
        hdText.setVisible(false);
        idCT.setVisible(false);
    }
    public void showtable(javafx.event.ActionEvent event) {
        hdtable.setVisible(true);
        lineChart.setVisible(false);
        hdText.setVisible(true);
        tnText.setVisible(false);
        idCT.setVisible(true);
    }
    public void BindGrid(javafx.scene.input.MouseEvent mouseEvent) {
        HoaDonDTO selected = hdtable.getSelectionModel().getSelectedItem();
        if(selected!=null) {
            mhd = selected.getIDGIO();
            id = selected.getUSERNAME();
            total1 = String.valueOf(selected.getTHANHTIEN());
        }
    }
    public void showDetails(javafx.event.ActionEvent event) {
        Cre.loadWindow("Chi tiết hóa đơn","/sample/View/DetailBill.fxml");
    }
}
