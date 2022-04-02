package sample.Controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import sample.DTO.LoginDTO;
import sample.U.AppContain;

import sample.connSQL.DBconnect;


import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import sample.U.Cre;


import static javafx.scene.control.ButtonType.*;

public class LoginController implements Initializable {

    @FXML
    private Button loginButton;

    @FXML
    private Button dangkiButton;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Hyperlink linkTo;

    Connection conn = DBconnect.getConnect();
    PreparedStatement prst;
    ResultSet rs;
    public static String loggerUsername = "";
    public static String loggerPhanQuyen = "";
    public static ObservableList<LoginDTO> loginObservableList= FXCollections.observableArrayList();


    public void login(ActionEvent event) {
            String sql =  AppContain.LOG_OP;
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            if(username.isEmpty() || password.isEmpty()) {
                Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.HEADER_TITLE, Alert.AlertType.ERROR, OK);
                alert.show();
                txtUsername.requestFocus();
            }
            else
            {
                try{
                    prst = conn.prepareStatement(sql);
                    prst.setString(1, username);
                    prst.setString(2,password);
                    rs = prst.executeQuery();
                    if(rs.next()) {
                        loggerPhanQuyen= rs.getString("PHANQUYEN");
                        loggerUsername = rs.getString("USERNAME");
//                        if(loggerPhanQuyen.equals("Admin"))
//                        {
                            Stage stage = (Stage) loginButton.getScene().getWindow();
                            stage.close();
                            Cre.OP_UI(AppContain.FORM_ADMIN2_UI);
//                        }
//                        else
//                        {
//                            Stage stage = (Stage) loginButton.getScene().getWindow();
//                            stage.close();
//                            Cre.OP_UI(AppContain.FORM_ADMIN2_UI);
//                        }
                    }
                    else
                    {
                        Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.CHECK_ACCOUT, Alert.AlertType.ERROR, OK);
                        alert.show();

                        txtUsername.setText("");
                        txtPassword.setText("");
                        txtUsername.requestFocus();
                    }
                } catch (Exception e)
                {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
                }
            }
    }

    public void cancel(ActionEvent event)
    {
        System.exit(0);
    } // Thoát app

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private void linkHP(ActionEvent event){
        Cre.loadWindow("Thông tin liên hệ","/sample/View/CONSTACTUS.fxml");
    }
}
