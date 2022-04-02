package sample.Controller;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.DTO.LoaiDTO;

import sample.U.AppContain;
import sample.U.Cre;


import java.net.URL;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;



public class UiController implements Initializable {

    @FXML
    private BorderPane BroderMain;

    @FXML
    private Button donhangButton,logout,exitBtn,qlyButton,tkhoanButton,nsuButton;

    @FXML
    private Label txtUser,txtQuyen,todayLabel;
    private static String username = "";
    private static String phanquyen = "";
    @Override
    public void initialize(URL url, ResourceBundle rb){
        username = LoginController.loggerUsername;
        phanquyen = LoginController.loggerPhanQuyen;
        txtUser.setText(username);
        txtQuyen.setText(phanquyen);
        if (phanquyen.equals("User")){
            qlyButton.setVisible(false);
            tkhoanButton.setVisible(false);
            nsuButton.setVisible(false);
        }
        todayLabel.setText(LocalDate.now(ZoneId.systemDefault()).toString());
    }

        public void qlButton(ActionEvent event){
        UiLoader object = new UiLoader();
        Pane view = object.getView(AppContain.FORM_PRODUCTMANAGEMENT_UI);
        BroderMain.setCenter(view);
    }

    public void tkButton(ActionEvent event){
        UiLoader object = new UiLoader();
//        Pane view = object.getView(AppContain.FORM_RESS_UI);
        Pane view = object.getView("Administrative");
        BroderMain.setCenter(view);
    }

    public void oderButton(ActionEvent event){
        UiLoader object = new UiLoader();
        Pane view = object.getView(AppContain.FORM_CASHIER_UI);
        BroderMain.setCenter(view);
    }

    public void nsButton(ActionEvent event){
        UiLoader object = new UiLoader();
        Pane view = object.getView(AppContain.FORM_EMPLOY_UI);
        BroderMain.setCenter(view);
    }

    public void logout(ActionEvent event) {
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
        Cre.OP_UI(AppContain.FORM_LOGIN_UI);
    }
    public void exitBtnClick(ActionEvent event)
    {
        System.exit(0);
    } // Thoát app
}


