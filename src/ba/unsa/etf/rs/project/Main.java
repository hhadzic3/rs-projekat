package ba.unsa.etf.rs.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../../../../../../resources/fxml/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
    //--module-path "C:\Users\HARUN\Documents\javafx-sdk-11.0.2\lib" --add-modules=javafx.controls,javafx.fxml

    public static void main(String[] args) {
        launch(args);
    }
}
