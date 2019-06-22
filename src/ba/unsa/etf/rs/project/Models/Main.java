package ba.unsa.etf.rs.project.Models;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    //--module-path "C:\Users\HARUN\Documents\javafx-sdk-11.0.2\lib" --add-modules=javafx.controls,javafx.fxml
    // org.xerial:sqlite-jdbc:3.21.0
    public static void main(String[] args) {
        launch(args);
    }
}
