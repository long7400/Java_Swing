package sample.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.DTO.KhachHangDTO;
import sample.DTO.NhanVienDTO;
import sample.U.AppContain;
import sample.U.Cre;
import sample.connSQL.DBconnect;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.scene.control.ButtonType.OK;

public class EmployeeManagementController implements Initializable {
    /**
     *KHÁCH HÀNG
     */
    @FXML
    private TextField tennvTextField,sdtnvTextField,dcnvTextField,luongnvTextField,txtSreachNV;

    @FXML
    private TableView<NhanVienDTO> nhanvienTable;

    @FXML
    private TableColumn<NhanVienDTO, String> idNVCol;

    @FXML
    private TableColumn<NhanVienDTO, String> tenNVCol;

    @FXML
    private TableColumn<NhanVienDTO, String> sdtNVCol;

    @FXML
    private TableColumn<NhanVienDTO, String> dcNVCol;

    @FXML
    private TableColumn<NhanVienDTO, String> luongNVCol;
    @FXML
    private Button addNV,editNV,deleNV;
    @FXML
    private ToggleButton btnTog;
    public static ObservableList<NhanVienDTO> nhanvienObservableList= FXCollections.observableArrayList();

    Connection conn = null ;
    ResultSet rs = null ;
    PreparedStatement prst ;
    String mnv="";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDataNV();
        setDisabled(true);
        sdtnvTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    sdtnvTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    /**
     *====================================================== KHU VỰC NHAN VIÊN ========================================================
     */
    private void refreshNVTable() {
        try {
            nhanvienObservableList.clear();
            String query = AppContain.CBSHOWNV_OP;
            PreparedStatement pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()){
                nvien = rs.getString("IDNV");
                nhanvienObservableList.add(new NhanVienDTO(
                        rs.getString(AppContain.IDNV),
                        rs.getString(AppContain.TENNV),
                        rs.getString(AppContain.SDTNV),
                        rs.getString(AppContain.DCNV),
                        rs.getInt(AppContain.LUONGNV)));
                nhanvienTable.setItems(nhanvienObservableList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductionManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static String nvien = "";

    /**
     * LOAD DỮ LIỆU NHÂN VIÊN
     */
    private void loadDataNV() {
        conn = DBconnect.getConnect();
        nhanvienTable.refresh();
        refreshNVTable();
        idNVCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.IDNV));
        tenNVCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.TENNV));
        sdtNVCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.SDTNV));
        dcNVCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.DIACHI));
        luongNVCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.LUONGNV));

    }

    /**
     * CHECK DATA
     */
    public boolean CheckDataNV(boolean Edit) {
        String tennv = tennvTextField.getText();
        String diachi = dcnvTextField.getText();
        String sdtnv = sdtnvTextField.getText();
        String luongnv = String.valueOf(luongnvTextField.getText());
        boolean check=true;
        if (tennv.isEmpty() || luongnv.isEmpty() ||diachi.isEmpty() ||sdtnv.isEmpty() ) {
            Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.HEADER_TITLE, Alert.AlertType.ERROR, OK);
            alert.show();
            check=false;
            return check;
        }else{
            if(!Edit) {
                for (NhanVienDTO x : nhanvienObservableList) {
                    if (String.valueOf(x.getIDNV()).equals(tennvTextField.getText())) {
                        Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.IDTEST_TITLE, Alert.AlertType.ERROR, OK);
                        alert.show();
                        check = false;
                        break;
                    }
                }
            }
            return check;
        }
    }

    /**
     * BIND GRID
     */
    public void BindTableNV(javafx.scene.input.MouseEvent mouseEvent) {
        NhanVienDTO selected = nhanvienTable.getSelectionModel().getSelectedItem();
        if(selected!=null) {
            mnv = selected.getIDNV();
            tennvTextField.setText(selected.getTENNV());
            sdtnvTextField.setText(selected.getSODT());
            dcnvTextField.setText(selected.getDIACHI());
            luongnvTextField.setText(String.valueOf(selected.getLUONG()));
        }
    }
    /**
     * THÊM NHÂN VIÊN
     */
    public void addNVOnAction(ActionEvent event) {
        String tennv = tennvTextField.getText();
        String diachi = dcnvTextField.getText();
        String sdtnv = sdtnvTextField.getText();
        String luongnv = String.valueOf(luongnvTextField.getText());

        if (tennv.isEmpty() || luongnv.isEmpty() ||diachi.isEmpty() ||sdtnv.isEmpty() ) {
            Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.HEADER_TITLE, Alert.AlertType.ERROR, OK);
            alert.show();
        } else if(CheckDataNV(false)) {
            try {
                String query = AppContain.ADDNV_OP;
                prst = conn.prepareStatement(query);
                prst.setString(1, tennv);
                prst.setString(2, sdtnv);
                prst.setString(3, diachi);
                prst.setString(4, luongnv);
                prst.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            loadDataNV();
            ClearFieldsNV();
        }
    }/**
     * CLEAR FIELD
     */
    private void ClearFieldsNV(){
        tennvTextField.clear();
        dcnvTextField.clear();
        sdtnvTextField.clear();
        luongnvTextField.clear();
        txtSreachNV.clear();
        loadDataNV();
    }

    /**
     * SỬA NHÂN VIÊN
     */
    public void editNVOnAction(ActionEvent event){
        NhanVienDTO selected= nhanvienTable.getSelectionModel().getSelectedItem();
        String tennv = tennvTextField.getText();
        String sdtnv = sdtnvTextField.getText();
        String diachi = dcnvTextField.getText();
        String luongnv = String.valueOf(luongnvTextField.getText());

        if(selected==null){
            Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.ERRORDELETE_TITLE, Alert.AlertType.ERROR, OK);
            alert.show();
            return;
        } if(CheckDataNV(true)) {
            try {
                String query = "Update NHANVIEN set TENNV= ?, SODT= ?, DIACHI= ?, LUONG= ?  where IDNV='"+mnv+"'";
                prst = conn.prepareStatement(query);
                prst.setString(1, tennv);
                prst.setString(2, sdtnv);
                prst.setString(3, diachi);
                prst.setString(4, luongnv);
                prst.executeUpdate();
                int rows = prst.executeUpdate();
                if (rows > 0){
                    Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.SUCC_MESSAGE, Alert.AlertType.CONFIRMATION, OK);
                    alert.show();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            loadDataNV();
            ClearFieldsNV();
        }
    }
    /**
     * XÓA NHÂN VIÊN
     */
    public void deleteNV(ActionEvent event){
        NhanVienDTO selected= nhanvienTable.getSelectionModel().getSelectedItem();
        if(selected!=null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(AppContain.TITLE2);
            alert.setHeaderText("Bạn có chắc muốn xoá " + selected.getTENNV());
            alert.setContentText("Lựa chọn");

            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeYes) {
                try {
                    String query = "DELETE FROM NHANVIEN WHERE IDNV='"+mnv+"'";
                    prst = conn.prepareStatement(query);
                    int rows = prst.executeUpdate();
                    if (rows > 0) {
                        Alert alert1 = Cre.createAlert(AppContain.TITLE, AppContain.SUCC_MESSAGE, Alert.AlertType.CONFIRMATION, OK);
                        alert1.show();
                    }
                    loadDataNV();
                    ClearFieldsNV();

                } catch (SQLException ex) {
                    Alert alert1 = Cre.createAlert(AppContain.TITLE, AppContain.FAILS_MESSAGE, Alert.AlertType.CONFIRMATION, OK);
                    alert1.show();
                    System.out.println(ex.getMessage());
                }
            }
        }else{
            Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.ERRORDELETE_TITLE, Alert.AlertType.ERROR, OK);
            alert.show();
        }
    }
    /**
     * ACTION TÌM KIẾM
     */
    public void SeachNVTextChange(ActionEvent event){
        SearchNV(txtSreachNV.getText());
    }

    /**
     * CLASS TÌM KIẾM
     */
    public void SearchNV(String key){

        ObservableList<NhanVienDTO> observableList= FXCollections.observableArrayList();
        for (NhanVienDTO s: nhanvienObservableList) {
            if(s.getTENNV().toLowerCase().contains(key.toLowerCase())
                    || String.valueOf(s.getIDNV()).contains(key))
                observableList.add(s);
        }
        nhanvienTable.refresh();
        nhanvienTable.setItems(observableList);
    }
    public void clearNVDataButton(ActionEvent event){
        ClearFieldsNV();
    }

    void setDisabled(boolean a){
        btnTog.setStyle("");
        tennvTextField.setDisable(a);
        sdtnvTextField.setDisable(a);
        dcnvTextField.setDisable(a);
        luongnvTextField.setDisable(a);
        addNV.setDisable(a);
        editNV.setDisable(a);
        deleNV.setDisable(a);
    }

    public void Toggle(ActionEvent event){
        if(btnTog.isSelected()){
            tennvTextField.setDisable(false);
            sdtnvTextField.setDisable(false);
            dcnvTextField.setDisable(false);
            luongnvTextField.setDisable(false);
            addNV.setDisable(false);
            editNV.setDisable(false);
            deleNV.setDisable(false);
        } else {
            setDisabled(true);
        }
    }
}
