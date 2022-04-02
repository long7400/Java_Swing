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

public class ProductionManagementController implements Initializable {
    @FXML
    private ToggleButton btnTogg,btnToggN,btnToggL;
    @FXML
    private Button addhhButton,edithhButtedon,deletehhButton,addnnkButton,editnnkButon,delennkButon,addLButton,deleLButton,edtLButton;
    /**
     *========================================== CÂY CẢNH ====================================================
     */
    @FXML
    public TableView<HangHoaDTO> tableHH;
    @FXML
    private TableColumn<HangHoaDTO, String> idHCol;
    @FXML
    private TableColumn<HangHoaDTO, String> nameCol;
    @FXML
    private TableColumn<HangHoaDTO, String> trinhtrangCol;
    @FXML
    private TableColumn<HangHoaDTO, String> giaCol;
    @FXML
    private TableColumn<HangHoaDTO, String> loaiCol;
    @FXML
    private TableColumn<HangHoaDTO, String> nhapkhauCol;
    @FXML
    private TextField txtSreach,txtSreachN,txtSreachL;
    //ComboBOX
    @FXML
    private ComboBox<LoaiDTO> loaiCombobox = new ComboBox<>();
    @FXML
    private ComboBox<NhapKhauDTO> nkCombobox =  new ComboBox<>();
    @FXML
    private ComboBox<NhapKhauDTO> nk1Combobox =  new ComboBox<>();
    @FXML
    private TextField tenHHText,giaTextField,slTextField;
    /**
     *========================================== NHẬP KHẨU ====================================================
     */
    @FXML
    private TableView<NhapKhauDTO> NNKTable;
    @FXML
    private TableColumn<NhapKhauDTO, String> idNNKCol;
    @FXML
    private TableColumn<NhapKhauDTO, String> NNKCol;
    @FXML
    private TableColumn<NhapKhauDTO, String> DiachiCol;
    @FXML
    private TableColumn<NhapKhauDTO, String> SDTCol;
    @FXML
    private TextField nnkTextFleid,dcTextFleid,sdtTextFeild;
    /**
     *========================================== KHU VỰC LOẠI SẢN PHẨM  ====================================================
     */
    @FXML
    private TextField loaispTextFeild;

    @FXML
    private TableView<LoaiDTO> loaiTable;
    @FXML
    private TableColumn<LoaiDTO, String> idLCol;
    @FXML
    private TableColumn<LoaiDTO, String> tenLCol;

    public static ObservableList<HangHoaDTO> cayObservableList= FXCollections.observableArrayList();
    public static ObservableList<NhapKhauDTO> nhapkhauObservableList= FXCollections.observableArrayList();
    public static ObservableList<LoaiDTO> loaiObservableList= FXCollections.observableArrayList();
    public static ObservableList<NhapKhauDTO> nhapkhau1ObservableList= FXCollections.observableArrayList();
    public static ObservableList<LoaiDTO> loai1ObservableList= FXCollections.observableArrayList(); // Tránh bị trung 1 hàng trong table
    public static ObservableList<NhapKhauDTO> nkObservableList = FXCollections.observableArrayList();// Tránh bị dư 1 hàng dữ liệu trong table

    Connection conn = null ;
    ResultSet rs = null ;
    PreparedStatement prst ;
    String ml="";
    String mlpk = "";
    String mlnk = "";
    String mll="";
    @Override
    public void initialize(URL url, ResourceBundle rb){
        setDisabled();
        setDisabledN(); setDisabledL();
        loaDataNNK();
        loadDataCay();loadDataLoai();
        fillLoaiCay();fillNNK();
        FxU.autoCompleteComboBoxPlus(loaiCombobox, (typedText, itemToCompare) -> itemToCompare.getTENLOAI().toLowerCase().contains(typedText.toLowerCase()));
        loaiCombobox.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null) {
                System.out.println("Selected: " + newval.getTENLOAI()
                        + ". ID: " + newval.getIDLOAI());
                loaiCombobox.setItems(loaiObservableList);
            }
        });
        FxU.autoCompleteComboBoxPlus(nkCombobox, (typedText, itemToCompare) -> itemToCompare.getTENNNK().toLowerCase().contains(typedText.toLowerCase()));
        nkCombobox.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null) {
                System.out.println("Selected: " + newval.getTENNNK()
                        + ". ID: " + newval.getIDNNK());
                nkCombobox.setItems(nkObservableList);
            }
        });
        FxU.autoCompleteComboBoxPlus(nk1Combobox, (typedText, itemToCompare) -> itemToCompare.getTENNNK().toLowerCase().contains(typedText.toLowerCase()));
        nk1Combobox.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null) {
                System.out.println("Selected: " + newval.getTENNNK()
                        + ". ID: " + newval.getIDNNK());
                nk1Combobox.setItems(nhapkhau1ObservableList);
            }
        });

        giaTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    giaTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        sdtTextFeild.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    sdtTextFeild.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        slTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    slTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });


    }
    /**
     *========================================== KHU VỰC SÉT NÚT ====================================================
     */
    public void setDisabled(){
        btnTogg.setStyle("");
        tenHHText.setDisable(true);
        giaTextField.setDisable(true);
        slTextField.setDisable(true);
        nkCombobox.setDisable(true);
        loaiCombobox.setDisable(true);
        addhhButton.setDisable(true);
        deletehhButton.setDisable(true);
        edithhButtedon.setDisable(true);
    }
    public void setDisabledN(){
        btnToggN.setStyle("");
        nnkTextFleid.setDisable(true);
        dcTextFleid.setDisable(true);
        sdtTextFeild.setDisable(true);
        addnnkButton.setDisable(true);
        editnnkButon.setDisable(true);
        delennkButon.setDisable(true);
    }
    public void setDisabledL(){
        btnToggL.setStyle("");
        loaispTextFeild.setDisable(true);
        addLButton.setDisable(true);
        deleLButton.setDisable(true);
        edtLButton.setDisable(true);
    }
    @FXML
    void ButtonToG(ActionEvent event){
        if(btnTogg.isSelected()){
            tenHHText.setDisable(false);
            giaTextField.setDisable(false);
            slTextField.setDisable(false);
            nkCombobox.setDisable(false);
            loaiCombobox.setDisable(false);
            addhhButton.setDisable(false);
            deletehhButton.setDisable(false);
            edithhButtedon.setDisable(false);
        } else {
            setDisabled();
        }
    }// Cây Cảnh
    @FXML
    void ButtonToGN(ActionEvent event){
        if(btnToggN.isSelected()) {
            nnkTextFleid.setDisable(false);
            dcTextFleid.setDisable(false);
            sdtTextFeild.setDisable(false);
            addnnkButton.setDisable(false);
            editnnkButon.setDisable(false);
            delennkButon.setDisable(false);
        } else {
            setDisabledN();
        }
    } // Nhập Khẩu
    @FXML
    void ButtonToGL(ActionEvent event){
        if(btnToggL.isSelected()){
            loaispTextFeild.setDisable(false);
            addLButton.setDisable(false);
            deleLButton.setDisable(false);
            edtLButton.setDisable(false);
        } else {
            setDisabledL();
        }
    } // Loại
    /**
     *========================================== KHU VỰC CÂY CẢNH ====================================================
     * LÀM MỚI CÂY
     */
    private void refreshCayTable() {
        try {
            cayObservableList.clear();
           String query = AppContain.GETLISTOFHH_OP;
            PreparedStatement pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()){
                cayObservableList.add(new HangHoaDTO(
                        rs.getString(AppContain.IDHH),
                        rs.getString(AppContain.TENHH),
                        rs.getInt(AppContain.SOLUONGHANG),
                        rs.getBigDecimal(AppContain.GIA),
                        new LoaiDTO(rs.getString(AppContain.IDLOAI), rs.getString(AppContain.TENLOAI)),
                        new NhapKhauDTO(rs.getString(AppContain.IDNNK), rs.getString(AppContain.TENNNK))));
                tableHH.setItems(cayObservableList);
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
        tableHH.refresh();
        refreshCayTable();
        idHCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.IDHH));
        nameCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.TENHH));
        trinhtrangCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.SOLUONGHANG));
        giaCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.GIA));
        loaiCol.setCellValueFactory(new PropertyValueFactory<>("TenLoai")); // Lấy dữ liệu từ tên loại mới
        nhapkhauCol.setCellValueFactory(new PropertyValueFactory<>("TenNNK"));
    }
    /**
     * LOAD DỮ LIỆU COMBOBOX
     */
    public void fillLoaiCay() {
        loaiCombobox.getItems().clear();
        loaiObservableList.clear();

        loaiObservableList.add(new LoaiDTO("0","Lựa chọn loại"));
        loaiCombobox.getSelectionModel().selectFirst();

        try {
            rs=conn.createStatement().executeQuery(AppContain.CBSHOWLOAI_OP);
            while(rs.next()){
                loaiObservableList.add(new LoaiDTO(rs.getString(AppContain.IDLOAI),rs.getString(AppContain.TENLOAI)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        loaiCombobox.setItems(loaiObservableList);
        loaiCombobox.setConverter(new StringConverter<LoaiDTO>() {

            @Override
            public String toString(LoaiDTO object) {
                return object != null ? object.getTENLOAI() : "";
            }

            @Override
            public LoaiDTO fromString(String string) {
                return loaiCombobox.getItems().stream().filter(object ->
                        object.getTENLOAI().equals(string)).findFirst().orElse(null);
            }
        });
    }
    public void fillNNK() {
        nkCombobox.getItems().clear();
        nkObservableList.clear();

        nkObservableList.add(new NhapKhauDTO("0","Chọn Nhập Khẩu","DIACHI","SDT"));
        nkCombobox.getSelectionModel().selectFirst();

        try {
            rs=conn.createStatement().executeQuery(AppContain.CBSHOWNNK_OP);
            while(rs.next()){
                nkObservableList.add(new NhapKhauDTO(rs.getString(AppContain.IDNNK),rs.getString(AppContain.TENNNK),rs.getString(AppContain.DIACHI),rs.getString(AppContain.SDT)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        nkCombobox.setItems(nkObservableList);
        nkCombobox.setConverter(new StringConverter<NhapKhauDTO>() {

            @Override
            public String toString(NhapKhauDTO object) {
                return object != null ? object.getTENNNK() : "";
            }

            @Override
            public NhapKhauDTO fromString(String string) {
                return nkCombobox.getItems().stream().filter(object ->
                        object.getTENNNK().equals(string)).findFirst().orElse(null);
            }
        });
    }
    /**
     * CHECK DATA
     */
    public boolean CheckDataC(boolean Edit) {
        String tencay = tenHHText.getText();
        String soluong = slTextField.getText();
        String gia = String.valueOf(giaTextField.getText());
        String loai = loaiCombobox.valueProperty().getValue().getIDLOAI();
        String nnk = nkCombobox.valueProperty().getValue().getIDNNK();
        boolean check=true;
        if (tencay.isEmpty() || soluong.isEmpty() || gia.isEmpty() || loai==0+"" || nnk==0+"" ) {
            Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.HEADER_TITLE, Alert.AlertType.ERROR, OK);
            alert.show();
            check=false;
            return check;
        }else{
            if(!Edit) {
                for (HangHoaDTO x : cayObservableList) {
                    if (String.valueOf(x.getTENHH()).equals(tenHHText.getText())) {
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
    public void BindTable(javafx.scene.input.MouseEvent mouseEvent) {
            HangHoaDTO selected = tableHH.getSelectionModel().getSelectedItem();
            if(selected!=null) {
                ml = selected.getIDHH();
                tenHHText.setText(selected.getTENHH());
                slTextField.setText(String.valueOf(selected.getSOLUONG()));
                giaTextField.setText(String.valueOf(selected.getGIA()));
                loaiCombobox.setValue(new LoaiDTO(selected.getLoaiDTO().getIDLOAI(), selected.getTenLoai()));
                nkCombobox.setValue(new NhapKhauDTO(selected.getNhapKhauDTO().getIDNNK(), selected.getTenNNK()));
            }
        }
    /**
     * THÊM CÂY
     */
    public void addCayOnAction(ActionEvent event) {
            String tencay = tenHHText.getText();
            String soluong = slTextField.getText();
            String gia = String.valueOf(giaTextField.getText());
            String loai = loaiCombobox.valueProperty().getValue().getIDLOAI();
            String nnk = nkCombobox.valueProperty().getValue().getIDNNK();

            if (tencay.isEmpty() || soluong.isEmpty() || gia.isEmpty() || loai==0+"" || nnk==0+"" ) {
                Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.HEADER_TITLE, Alert.AlertType.ERROR, OK);
                alert.show();
            } else if(CheckDataC(false)) {
                try {
                    String query = AppContain.ADDHH_OP;
                    prst = conn.prepareStatement(query);
                    prst.setString(1, tencay);
                    prst.setString(2, soluong);
                    prst.setString(3, gia);
                    prst.setString(4, loai);
                    prst.setString(5, nnk);
                    prst.executeUpdate();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                loadDataCay();
                ClearFields();
            }
    }
    /**
     * SỬA CÂY
     */
    public void editCayOnAction(ActionEvent event){
        HangHoaDTO selected= tableHH.getSelectionModel().getSelectedItem();
        String tencay = tenHHText.getText();
        String soluong = slTextField.getText();
        String gia = String.valueOf(giaTextField.getText());
        String loai = loaiCombobox.valueProperty().getValue().getIDLOAI();
        String nnk = nkCombobox.valueProperty().getValue().getIDNNK();
        if(selected == null){
            Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.ERROR_TITLE, Alert.AlertType.ERROR, OK);
            alert.show();
            return;
        } if(CheckDataC(true)) {
            try {
                String query = "Update HANGHOA set TENHH= ?, SOLUONG= ?, GIA= ?, IDLOAI= ?, IDNNK= ? where IDHH='"+ml+"'";
                prst = conn.prepareStatement(query);
                prst.setString(1, tencay);
                prst.setString(2, soluong);
                prst.setString(3, gia);
                prst.setString(4, loai);
                prst.setString(5, nnk);
                prst.executeUpdate();
                int rows = prst.executeUpdate();
                if (rows > 0){
                    Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.SUCC_MESSAGE, Alert.AlertType.CONFIRMATION, OK);
                    alert.show();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            refreshCayTable();
            ClearFields();
        }
    } // EDIT CÂY
    /**
     * XÓA CÂY
     */
    public void DeleteC(ActionEvent event){
        HangHoaDTO selected= tableHH.getSelectionModel().getSelectedItem();
        if(selected!=null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(AppContain.TITLE2);
            alert.setHeaderText("Bạn có chắc muốn xoá " + selected.getTENHH());
            alert.setContentText("Lựa chọn");
            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeYes) {
                try {
                    String query = "DELETE FROM HANGHOA WHERE IDHH='"+ml+"'";
                    prst = conn.prepareStatement(query);
                    int rows = prst.executeUpdate();
                    if (rows > 0) {
                        Alert alert1 = Cre.createAlert(AppContain.TITLE, AppContain.SUCC_MESSAGE, Alert.AlertType.CONFIRMATION, OK);
                        alert1.show();
                    }
                    refreshCayTable();
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
        SearchC(txtSreach.getText());
    }
    /**
     * CLASS TÌM KIẾM
     */
    public void SearchC(String key){

        ObservableList<HangHoaDTO> observableList= FXCollections.observableArrayList();
        for (HangHoaDTO s: cayObservableList) {
            if(s.getTENHH().toLowerCase().contains(key.toLowerCase())
                    || String.valueOf(s.getIDHH()).contains(key))
                observableList.add(s);
        }
        tableHH.refresh();
        tableHH.setItems(observableList);

    }
    /**
     *========================================== CLASS CLEAR TEXT FIELDS====================================================
     */
    private void ClearFields(){
        tenHHText.clear();
        giaTextField.clear();
        slTextField.clear();
        fillLoaiCay();
        loaiCombobox.getSelectionModel().selectFirst();
        fillNNK();
        nkCombobox.getSelectionModel().selectFirst();
        txtSreach.clear();
        refreshCayTable();
    }
    private void ClearFieldsN(){
        nnkTextFleid.clear();
        dcTextFleid.clear();
        sdtTextFeild.clear();
        txtSreachN.clear();
        refreshNNKTable();
    }
    private void ClearFieldsL(){
        txtSreachL.clear();
        loaispTextFeild.clear();
        refreshLoaiTable();
    }
    /**
     *========================================== KHU VỰC NHẬP KHẨU====================================================
     */
    /**
     * LOAD DATA LÊN TABLE NHẬP KHẨU
     */
    private void loaDataNNK() {
        conn = DBconnect.getConnect();
        refreshNNKTable();
        idNNKCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.IDNNK));
        NNKCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.TENNNK));
        DiachiCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.DIACHI));
        SDTCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.SDT));
    } // Load dữ liệu lên table NNK
    /**
     *LÀM MỚI DỮ LIỆU
     */
    private void refreshNNKTable() {
        try {
            nhapkhauObservableList.clear();
            String query = AppContain.CBSHOWNNK_OP;
            PreparedStatement pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()){
                nhapkhauObservableList.add(new NhapKhauDTO(
                        rs.getString(AppContain.IDNNK),
                        rs.getString(AppContain.TENNNK),
                        rs.getString(AppContain.DIACHI),
                        rs.getString(AppContain.SDT)));
                NNKTable.setItems(nhapkhauObservableList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductionManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * CHECK DỮ LIỆU ĐÃ CÓ CHƯA
     */
    public boolean CheckDataN(boolean Edit) {
        String tennk = nnkTextFleid.getText();
        String dc = dcTextFleid.getText();
        String sdt = sdtTextFeild.getText();
        boolean check=true;
        if  (tennk.isEmpty() || dc.isEmpty() || sdt.isEmpty() )  {
            Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.HEADER_TITLE, Alert.AlertType.ERROR, OK);
            alert.show();
            check=false;
            return check;
        }else{
            if(!Edit) {
                for (NhapKhauDTO x : nhapkhauObservableList) {
                    if (String.valueOf(x.getTENNNK()).equals(nnkTextFleid.getText())) {
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
     * THÊM NHẬP KHẨU
     */
    public void addNNKOnAction(ActionEvent event) {
        String tennk = nnkTextFleid.getText();
        String dc = dcTextFleid.getText();
        String sdt = sdtTextFeild.getText();

        if (tennk.isEmpty() || dc.isEmpty() || sdt.isEmpty() ) {
            Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.HEADER_TITLE, Alert.AlertType.ERROR, OK);
            alert.show();
        } else if(CheckDataN(false)) {
            try {
                String query = AppContain.ADDNK_OP;
                prst = conn.prepareStatement(query);
                prst.setString(1, tennk);
                prst.setString(2, dc);
                prst.setString(3, sdt);
                prst.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            loaDataNNK();
            ClearFieldsN();
        }
    }
    /**
     * BIND GRID
     */
    public void BindTable2(javafx.scene.input.MouseEvent mouseEvent) {
        NhapKhauDTO selected = NNKTable.getSelectionModel().getSelectedItem();
        if(selected!=null) {
            mlnk = selected.getIDNNK();
            nnkTextFleid.setText(selected.getTENNNK());
            dcTextFleid.setText(selected.getDIACHI());
            sdtTextFeild.setText(selected.getSDT());
        }
    }
    /**
     * SỬA NHẬP KHẨU
     */
    public void editNKOnAction(ActionEvent event){
        NhapKhauDTO selected= NNKTable.getSelectionModel().getSelectedItem();
        String tennk = nnkTextFleid.getText();
        String dc = dcTextFleid.getText();
        String sdt = sdtTextFeild.getText();
        if(selected==null){
            Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.ERROR_TITLE, Alert.AlertType.ERROR, OK);
            alert.show();
            return;
        } if(CheckDataN(true)) {
            try {
                String query = "Update NOINHAPKHAU set TENNNK= ?, DIACHI= ?, SDT= ? where IDNNK='"+mlnk+"'";
                prst = conn.prepareStatement(query);
                prst.setString(1, tennk);
                prst.setString(2, dc);
                prst.setString(3, sdt);
                prst.executeUpdate();
                int rows = prst.executeUpdate();
                if (rows > 0){
                    Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.SUCC_MESSAGE, Alert.AlertType.CONFIRMATION, OK);
                    alert.show();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            loaDataNNK();
            ClearFieldsN();
        }
    }
    /**
     * XÓA NHẬP KHẨU
     */

    public void DeleteN(ActionEvent event){
        NhapKhauDTO selected= NNKTable.getSelectionModel().getSelectedItem();
        if(selected!=null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(AppContain.TITLE2);
            alert.setHeaderText("Bạn có chắc muốn xoá " + selected.getTENNNK());
            alert.setContentText("Lựa chọn");

            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeYes) {
                try {
                    String query = "DELETE FROM NOINHAPKHAU WHERE IDNNK='"+mlnk+"'";
                    prst = conn.prepareStatement(query);
                    int rows = prst.executeUpdate();
                    if (rows > 0) {
                        Alert alert1 = Cre.createAlert(AppContain.TITLE, AppContain.SUCC_MESSAGE, Alert.AlertType.CONFIRMATION, OK);
                        alert1.show();
                    }
                    loaDataNNK();
                    ClearFieldsN();

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
    public void txtSeachNKTextChange(ActionEvent event){
        SearchN(txtSreachN.getText());
    }
    /**
     * CLASS TÌM KIẾM
     */
    public void SearchN(String key){

        ObservableList<NhapKhauDTO> observableList= FXCollections.observableArrayList();
        for (NhapKhauDTO s: nhapkhauObservableList) {
            if(s.getTENNNK().toLowerCase().contains(key.toLowerCase())
                    || String.valueOf(s.getIDNNK()).contains(key))
                observableList.add(s);
        }
        NNKTable.refresh();
        NNKTable.setItems(observableList);
    }
    /**
     *========================================== KHU VỰC LOẠI CÂY ====================================================
     */
    /**
     * LÀM MỚI LOẠI
     */
    private void refreshLoaiTable() {
        try {
            loai1ObservableList.clear();
            String query = AppContain.CBSHOWLOAI_OP;
            PreparedStatement pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()){
                loai1ObservableList.add(new LoaiDTO(
                        rs.getString(AppContain.IDLOAI),
                        rs.getString(AppContain.TENLOAI)));
                loaiTable.setItems(loai1ObservableList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductionManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * LOAD DỮ LIỆU LOẠI
     */
    private void loadDataLoai() {
        conn = DBconnect.getConnect();
        loaiTable.refresh();
        refreshLoaiTable();
        idLCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.IDLOAI));
        tenLCol.setCellValueFactory(new PropertyValueFactory<>(AppContain.TENLOAI));
    }
    /**
     * CHECK DATA
     */
    public boolean CheckDataL(boolean Edit) {
        String tenloai = loaispTextFeild.getText();
        boolean check=true;
        if (tenloai.isEmpty() ) {
            Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.HEADER_TITLE, Alert.AlertType.ERROR, OK);
            alert.show();
            check=false;
            return check;
        }else{
            if(!Edit) {
                for (LoaiDTO x : loai1ObservableList) {
                    if (String.valueOf(x.getTENLOAI()   ).equals(loaispTextFeild.getText())) {
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
    public void BindTableL(javafx.scene.input.MouseEvent mouseEvent) {
        LoaiDTO selected = loaiTable.getSelectionModel().getSelectedItem();
        if(selected!=null) {
            mll = selected.getIDLOAI();
            loaispTextFeild.setText(selected.getTENLOAI());
        }
    }
    /**
     * THÊM LOẠI
     */
    public void addLoaiOnAction(ActionEvent event) {
        String tenloai = loaispTextFeild.getText();

        if (tenloai.isEmpty()) {
            Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.HEADER_TITLE, Alert.AlertType.ERROR, OK);
            alert.show();
        } else if(CheckDataL(false)) {
            try {
                String query = AppContain.ADDLOAI_OP;
                prst = conn.prepareStatement(query);
                prst.setString(1, tenloai);
                prst.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            loadDataLoai();
            ClearFieldsL();
        }
    }
    /**
     * SỬA LOẠI
     */
    public void editLoaiOnAction(ActionEvent event){
        LoaiDTO selected= loaiTable.getSelectionModel().getSelectedItem();
        String tenloai = loaispTextFeild.getText();

        if(selected==null){
            Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.ERROR_TITLE, Alert.AlertType.ERROR, OK);
            alert.show();
            return;
        } if(CheckDataL(true)) {
            try {
                String query = "Update LOAI set TENLOAI= ? where IDLOAI='"+mll+"'";
                prst = conn.prepareStatement(query);
                prst.setString(1, tenloai);
                prst.executeUpdate();
                int rows = prst.executeUpdate();
                if (rows > 0){
                    Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.SUCC_MESSAGE, Alert.AlertType.CONFIRMATION, OK);
                    alert.show();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            loadDataLoai();
            ClearFieldsL();
        }
    }
    /**
     * XÓA LOẠI
     */
    public void deleteL(ActionEvent event){

        LoaiDTO selected= loaiTable.getSelectionModel().getSelectedItem();
        if(selected!=null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(AppContain.TITLE2);
            alert.setHeaderText("Bạn có chắc muốn xoá " + selected.getTENLOAI());
            alert.setContentText("Lựa chọn");

            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeYes) {
                try {
                    String query = "DELETE FROM LOAI WHERE IDLOAI='"+mll+"'";
                    prst = conn.prepareStatement(query);
                    int rows = prst.executeUpdate();
                    if (rows > 0) {
                        Alert alert1 = Cre.createAlert(AppContain.TITLE, AppContain.SUCC_MESSAGE, Alert.AlertType.CONFIRMATION, OK);
                        alert1.show();
                    }
                    loadDataLoai();
                    ClearFieldsL();

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
    public void txtSeachLoaiTextChange(ActionEvent event){
        SearchL(txtSreachL.getText());
    }
    /**
     * CLASS TÌM KIẾM
     */
    public void SearchL(String key){

        ObservableList<LoaiDTO> observableList= FXCollections.observableArrayList();
        for (LoaiDTO s: loai1ObservableList) {
            if(s.getTENLOAI().toLowerCase().contains(key.toLowerCase())
                    || String.valueOf(s.getIDLOAI()).contains(key))
                observableList.add(s);
        }
        loaiTable.refresh();
        loaiTable.setItems(observableList);

    }
    /**
     *========================================== KHU VỰC BUTTON CLEAR DATA ====================================================
     */
    public void clearCDataButton(ActionEvent event){
        ClearFields();
    }
    public void clearNDataButton(ActionEvent event){
        ClearFieldsN();
    }
    public void clearLDataButton(ActionEvent event){
        ClearFieldsL();
    }

}
