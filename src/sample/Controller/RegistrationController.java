package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import sample.DTO.*;
import sample.U.AppContain;
import sample.U.Cre;
import sample.U.FxU;
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

public class RegistrationController implements Initializable {

    @FXML
    private TextField usernametxt,passwordtxt,gmailtxt,txtSreach;
    @FXML
    private ComboBox<NhanVienDTO> nvCombox =  new ComboBox<>();

    @FXML
    private ComboBox<String> pqCombox;

    @FXML
    private TableView<LoginDTO> accTable;

    @FXML
    private TableColumn<LoginDTO, String> usernameCol;

    @FXML
    private TableColumn<LoginDTO, String> passCol;

    @FXML
    private TableColumn<LoginDTO, String> gmailCol;

    @FXML
    private TableColumn<LoginDTO, String> pqCol;

    @FXML
    private TableColumn<LoginDTO, String> nvCol;


    ObservableList<String> list = FXCollections.observableArrayList("Admin","User");
    public static final ObservableList<NhanVienDTO> nhanvienObservableList= FXCollections.observableArrayList();
    public static final ObservableList<LoginDTO> loginObservableList= FXCollections.observableArrayList();
    Connection conn = null ;
    ResultSet rs = null ;
    PreparedStatement prst;

    String acc = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pqCombox.setItems(list);
        loadData();
        fillNV();

        FxU.autoCompleteComboBoxPlus(nvCombox, (typedText, itemToCompare) -> itemToCompare.getTENNV().toLowerCase().contains(typedText.toLowerCase()));

        nvCombox.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null) {
                System.out.println("Selected: " + newval.getTENNV()
                        + ". ID: " + newval.getIDNV());
                nvCombox.setItems(nhanvienObservableList);
            }
        });
    }

    private void refreshTable() {
        try {
            loginObservableList.clear();
            String query = AppContain.GETLISTOFACC_OP;
            PreparedStatement pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()){
                loginObservableList.add(new LoginDTO(
                        rs.getString(AppContain.USERNAME),
                        rs.getString(AppContain.PASSWORD),
                        rs.getString(AppContain.GMAIL),
                        rs.getString(AppContain.PHANQUYEN),
                        new NhanVienDTO(rs.getString("IDNV"), rs.getString("TENNV"))));
                accTable.setItems(loginObservableList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductionManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * LOAD DỮ LIỆU ACCOUNT
     */
    private void loadData() {
        conn = DBconnect.getConnect();
        accTable.refresh();
        refreshTable();
        usernameCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.USERNAME));
        passCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.PASSWORD));
        gmailCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.GMAIL));
        pqCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.PHANQUYEN));
        nvCol.setCellValueFactory(new PropertyValueFactory<>("TenNV"));
    }
    public void BindTable(javafx.scene.input.MouseEvent mouseEvent) {
        LoginDTO selected = accTable.getSelectionModel().getSelectedItem();
        usernametxt.setDisable(true);
        if(selected!=null) {
            acc = selected.getUSERNAME();
            usernametxt.setText(selected.getUSERNAME());
            passwordtxt.setText(String.valueOf(selected.getPASSWORD()));
            gmailtxt.setText(String.valueOf(selected.getGMAIL()));
            pqCombox.setValue(String.valueOf(selected.getPHANQUYEN()));
            nvCombox.setValue(new NhanVienDTO(selected.getNhanVienDTO().getIDNV(), selected.getTenNV()));
        }
    }
    public void fillNV() {
        nvCombox.getItems().clear();
        nhanvienObservableList.clear();

        nhanvienObservableList.add(new NhanVienDTO("0","Chọn Nhân Viên"));
        nvCombox.getSelectionModel().selectFirst();

        try {
            rs=conn.createStatement().executeQuery(AppContain.CBSHOWNV_OP);
            while(rs.next()){
                nhanvienObservableList.add(new NhanVienDTO(rs.getString(AppContain.IDNV),rs.getString(AppContain.TENNV)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        nvCombox.setItems(nhanvienObservableList);
        nvCombox.setConverter(new StringConverter<NhanVienDTO>() {

            @Override
            public String toString(NhanVienDTO object) {
                return object != null ? object.getTENNV() : "";
            }

            @Override
            public NhanVienDTO fromString(String string) {
                return nvCombox.getItems().stream().filter(object ->
                        object.getTENNV().equals(string)).findFirst().orElse(null);
            }
        });
    }
    public boolean CheckDataLogin(boolean Edit) {
        String user = usernametxt.getText();
        String pass = passwordtxt.getText();
        String gmail = gmailtxt.getText();
        String quyen = String.valueOf(pqCombox.getValue());
        String nv = nvCombox.valueProperty().getValue().getIDNV();
        boolean check=true;
        if (user.isEmpty() || pass.isEmpty() || gmail.isEmpty() || quyen==0+"" || nv==0+"" ) {
            Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.HEADER_TITLE, Alert.AlertType.ERROR, OK);
            alert.show();
            check=false;
            return check;
        }else{
            if(!Edit) {
                for (LoginDTO x : loginObservableList) {
                    if (String.valueOf(x.getUSERNAME()).equals(usernametxt.getText())) {
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
    public void ClearFields(){
            usernametxt.clear();
            passwordtxt.clear();
            gmailtxt.clear();
            fillNV();
            nvCombox.getSelectionModel().selectFirst();
            txtSreach.clear();
            refreshTable();

    }
    public void editOnAction(ActionEvent event){
        LoginDTO selected= accTable.getSelectionModel().getSelectedItem();
        String user = usernametxt.getText();
        String pass = passwordtxt.getText();
        String gmail = gmailtxt.getText();
        String quyen = String.valueOf(pqCombox.getValue());
        String nv = nvCombox.valueProperty().getValue().getIDNV();
        if(selected == null){
            Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.ERROR_TITLE, Alert.AlertType.ERROR, OK);
            alert.show();
            return;
        } if(CheckDataLogin(true)) {

            try {
                String query = "Update DANGNHAP set PASSWORD= ?, GMAIL= ?, PHANQUYEN= ?, IDNV= ? where USERNAME='"+acc+"'";
                prst = conn.prepareStatement(query);

                prst.setString(1, pass);
                prst.setString(2, gmail);
                prst.setString(3, quyen);
                prst.setString(4, nv);
                prst.executeUpdate();
                prst.executeUpdate();
                int rows = prst.executeUpdate();
                if (rows > 0){
                    Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.SUCC_MESSAGE, Alert.AlertType.CONFIRMATION, OK);
                    alert.show();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            refreshTable();
            ClearFields();
        }
    } //
    /**
     * DELETE
     */
    public void Delete(ActionEvent event){
        LoginDTO selected= accTable.getSelectionModel().getSelectedItem();
        if(selected!=null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(AppContain.TITLE2);
            alert.setHeaderText("Bạn có chắc muốn xoá " + selected.getUSERNAME());
            alert.setContentText("Lựa chọn");

            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeYes) {
                try {
                    String query = "DELETE FROM DANGNHAP WHERE USERNAME='"+acc+"'";
                    prst = conn.prepareStatement(query);
                    int rows = prst.executeUpdate();
                    if (rows > 0) {
                        Alert alert1 = Cre.createAlert(AppContain.TITLE, AppContain.SUCC_MESSAGE, Alert.AlertType.CONFIRMATION, OK);
                        alert1.show();
                    }
                    refreshTable();
                    ClearFields();

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
    public void txtSeachTextChange(ActionEvent event){
        SearchLogin(txtSreach.getText());
    }
    /**
     * CLASS TÌM KIẾM
     */
    public void SearchLogin(String key){

        ObservableList<LoginDTO> observableList= FXCollections.observableArrayList();
        for (LoginDTO s: loginObservableList) {
            if(s.getUSERNAME().toLowerCase().contains(key.toLowerCase())
                    || String.valueOf(s.getTenNV()).contains(key))
                observableList.add(s);
        }
        accTable.refresh();
        accTable.setItems(observableList);
    }
    public void clearDataButton(ActionEvent event){
        ClearFields();
    }
}
