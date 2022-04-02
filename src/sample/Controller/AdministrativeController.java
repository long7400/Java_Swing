package sample.Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.StringConverter;
import sample.DTO.NhanVienDTO;
import sample.Main;
import sample.U.AppContain;
import sample.U.Cre;
import sample.U.FxU;
import sample.connSQL.DBconnect;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.control.ButtonType.OK;

public class AdministrativeController implements Initializable {
    // Đăng Ký
    @FXML
    private FontAwesomeIcon dk1,dk2,dk3,dk4,dk5,dk6,id1;
    @FXML
    private TextField usernametxt,gmailtxt,txtPass;
    @FXML
    private Text txt1,txt2,dangkyTxt;
    @FXML
    private Line line1,line2;
    @FXML
    private Button dangkyButton1,dangkyButton,adminButton,but1,but2,but3;
    @FXML
    private Label hello1,username1;
    @FXML
    private ComboBox<NhanVienDTO> nvCombox1=  new ComboBox<>();
    @FXML
    private ComboBox<String> pqCombox;

    @FXML
    private AnchorPane layer1,layer2,layerfirts;
    ObservableList<String> list = FXCollections.observableArrayList("Admin","User");
    public static final ObservableList<NhanVienDTO> nhanvien1ObservableList= FXCollections.observableArrayList();
    Connection conn = DBconnect.getConnect();
    ResultSet rs = null ;
    PreparedStatement prst;
    private static String phanquyen = "";
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        phanquyen = LoginController.loggerPhanQuyen;
        username1.setText(phanquyen);
        adminButton.setVisible(false);
        setVisible(false);
        pqCombox.setItems(list);
        fillNV1();
        FxU.autoCompleteComboBoxPlus(nvCombox1, (typedText, itemToCompare) -> itemToCompare.getTENNV().toLowerCase().contains(typedText.toLowerCase()));

        nvCombox1.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null) {
                System.out.println("Selected: " + newval.getTENNV()
                        + ". ID: " + newval.getIDNV());
                nvCombox1.setItems(nhanvien1ObservableList);
            }
        });
    }
    public void setVisible(Boolean a){
        dk1.setVisible(a);
        dk2.setVisible(a);
        dk3.setVisible(a);
        dk4.setVisible(a);
        dk5.setVisible(a);
        dk6.setVisible(a);
        dangkyTxt.setVisible(a);
        dangkyButton1.setVisible(a);
        usernametxt.setVisible(a);
        txtPass.setVisible(a);
        gmailtxt.setVisible(a);
        pqCombox.setVisible(a);
        nvCombox1.setVisible(a);
    }
    public void setVisible1(boolean b){
        txt1.setVisible(b);
        txt2.setVisible(b);
        line1.setVisible(b);
        line2.setVisible(b);
        but1.setVisible(b);
        but2.setVisible(b);
        but3.setVisible(b);
    }
    public void btn(javafx.scene.input.MouseEvent mouseEvent) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.6));
        slide.setNode(layer2);

        slide.setToX(940);
        slide.play();

        layer1.setTranslateX(-258);
        setVisible(true);
        setVisible1(false);
        adminButton.setVisible(true);
        slide.setOnFinished((e->{


        }));
    }

    public void btn2(javafx.scene.input.MouseEvent mouseEvent) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.6));
        slide.setNode(layer2);

        slide.setToX(0);
        slide.play();

        layer1.setTranslateX(0);
        setVisible(false);
        setVisible1(true);
        adminButton.setVisible(false);
        dangkyButton.setVisible(true);
        layer1.setTranslateX(0);
        slide.setOnFinished((e->{
        }));
    }

    public void fillNV1() {
        nvCombox1.getItems().clear();
        nhanvien1ObservableList.clear();

        nhanvien1ObservableList.add(new NhanVienDTO("0","Chọn Nhân Viên"));
        nvCombox1.getSelectionModel().selectFirst();

        try {
            rs=conn.createStatement().executeQuery(AppContain.CBSHOWNV_OP);
            while(rs.next()){
                nhanvien1ObservableList.add(new NhanVienDTO(rs.getString(AppContain.IDNV),rs.getString(AppContain.TENNV)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        nvCombox1.setItems(nhanvien1ObservableList);
        nvCombox1.setConverter(new StringConverter<NhanVienDTO>() {

            @Override
            public String toString(NhanVienDTO object) {
                return object != null ? object.getTENNV() : "";
            }

            @Override
            public NhanVienDTO fromString(String string) {
                return nvCombox1.getItems().stream().filter(object ->
                        object.getTENNV().equals(string)).findFirst().orElse(null);
            }
        });
    }

    public void dangkyOnAction(ActionEvent event) {
        String user = usernametxt.getText();
        String pass = txtPass.getText();
        String gmail = gmailtxt.getText();
        String quyen = String.valueOf(pqCombox.getValue());
        String nv = nvCombox1.valueProperty().getValue().getIDNV();

        if (user.isEmpty() || pass.isEmpty() || gmail.isEmpty() || quyen==0+"" || nv==0+"" ) {
            Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.HEADER_TITLE, Alert.AlertType.ERROR, OK);
            alert.show();
        } else
            try {
                String query = AppContain.ADDACCOUNT_OP;
                prst = conn.prepareStatement(query);
                prst.setString(1, user);
                prst.setString(2, pass);
                prst.setString(3, gmail);
                prst.setString(4, quyen);
                prst.setString(5, nv);
                prst.executeUpdate();
                Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.SUCC_MESSAGE, Alert.AlertType.NONE, OK);
                alert.show();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            ClearFields();
    }

    public void ClearFields(){
        usernametxt.clear();
        txtPass.clear();
        gmailtxt.clear();
        fillNV1();
        nvCombox1.getSelectionModel().selectFirst();
    }

    public void hdOnAction(ActionEvent event){
        Cre.loadWindow("Thu Nhập", "/sample/View/BillList.fxml");
    }
    public void qlAccOnAction(ActionEvent event){
        Cre.loadWindow("Quản lý tài khoản", "/sample/View/Registration.fxml");
    }

}
