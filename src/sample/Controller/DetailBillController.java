package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.DTO.ChiTietDTO;
import sample.DTO.HangHoaDTO;
import sample.DTO.HoaDonDTO;
import sample.DTO.LoaiDTO;
import sample.U.AppContain;
import sample.connSQL.DBconnect;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DetailBillController implements Initializable {
    @FXML
    private Label idHD,nguoitaoHD,tongtienHD;
    @FXML
    private TableView<ChiTietDTO>  tbctHD;
    @FXML
    private TableColumn<ChiTietDTO, String> hhCol;
    @FXML
    private TableColumn<ChiTietDTO, String> slhhCol;
    @FXML
    private TableColumn<ChiTietDTO, String> dongiaCol;
    public static String idGio = "";
    public static String idUs = "";
    public static String totalT = "";
    public static ObservableList<ChiTietDTO> cthoadonObservableList= FXCollections.observableArrayList();
    Connection conn = DBconnect.getConnect();
    ResultSet rs;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idGio = BillList.mhd;
        idUs = BillList.id;
        totalT = BillList.total1;
        idHD.setText(idGio);
        nguoitaoHD.setText(idUs);
        tongtienHD.setText(totalT);
        loadData();
    }
    private void refreshTable() { 
        try {
            cthoadonObservableList.clear();
            String query = "Select * FROM  HANGHOA H, CTGIOHANG CT where H.IDHH= CT.IDHH and IDGIO='"+idGio+"'";
            PreparedStatement pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()){
                cthoadonObservableList.add(new ChiTietDTO(
                        rs.getString(AppContain.IDGIO),
                        new HangHoaDTO(rs.getString(AppContain.IDHH), rs.getString(AppContain.TENHH)),
                        rs.getInt(AppContain.SOLUONGC),
                        rs.getBigDecimal(AppContain.DONGGIA)));
                tbctHD.setItems(cthoadonObservableList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductionManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void loadData() {
        tbctHD.refresh();
        refreshTable();
        hhCol.setCellValueFactory(new PropertyValueFactory<>("TenHH"));
        slhhCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.SOLUONGC));
        dongiaCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.DONGGIA));
    }
}
