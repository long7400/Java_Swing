package sample.U;


import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Main;


import java.io.IOException;


public class Cre { // Phần này để làm class nào sài nhiều lần

    public static Alert createAlert(String title, String context, Alert.AlertType alertType, ButtonType... buttons) {
        Alert alert = new Alert(alertType, context, buttons);
        alert.setTitle(title);
        alert.setHeaderText(null);
        return alert;
    } // Tạo bảng thông báo

    public static void OP_UI(String ui){
        Stage scene2 = new Stage();
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(ui));
            Scene scene = new Scene(root);
            scene2.setScene(scene);
            scene2.initStyle(StageStyle.DECORATED.UNDECORATED);
            scene2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // Không có tile
    public static void loadWindow(String title, String URL) {
        try {
            Parent acc = FXMLLoader.load(Main.class.getResource(URL));
            Scene s = new Scene(acc);
            Stage stg = new Stage();
            stg.setTitle(title);
            stg.setScene(s);
            stg.setResizable(false);
            stg.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//mở 1 màn hình mới có tilê
}
