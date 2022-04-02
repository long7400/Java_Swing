package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.U.AppContain;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(AppContain.FORM_LOGIN_UI));
        primaryStage.setTitle("Cây cảnh NHL");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.DECORATED.UNDECORATED);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
