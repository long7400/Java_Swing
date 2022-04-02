package sample.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.U.AppContain;
import sample.U.Cre;

import java.net.URL;

import static javafx.scene.control.ButtonType.OK;

public class UiLoader {
    private Pane view;

    public Pane getView(String filename) {
        try {
            URL fileURL = Main.class.getResource("View/" + filename + ".fxml");
            if (fileURL == null) {
                Alert alert = Cre.createAlert(AppContain.TITLE, AppContain.CHECK_ACCOUT, Alert.AlertType.ERROR, OK);
                alert.show();
            }
            view = new FXMLLoader().load(fileURL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return view;
    }

}
