package ba.unsa.etf.rs.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LoginController {
    public TextField nameField;
    public PasswordField passwordField;


    public void loginAction(ActionEvent actionEvent) {
        boolean sveOk = true;
        sveOk = isEmptyy(nameField);
        sveOk &= isEmptyy(passwordField);

        if (!sveOk) return;

        if(Exists(nameField.getText(),passwordField.getText())){
            Stage stage = new Stage();
            try {
                MovieLibraryDAO model = MovieLibraryDAO.getInstance();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainform.fxml"));
                MainController mainController = new MainController(model);
                loader.setController(mainController);
                Parent root = loader.load();
                stage.setTitle("Movie Library");
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.show();
            } catch (IOException e) {
            e.printStackTrace();
            }
            finally {
                Stage s = (Stage) nameField.getScene().getWindow();
                s.close();
            }
        }
        else return;
    }
    private boolean isEmptyy(TextField field) {
        if (field.getText().trim().isEmpty()) {
            field.getStyleClass().removeAll("poljeIspravno");
            field.getStyleClass().add("poljeNijeIspravno");
            return false;
        } else {
            field.getStyleClass().removeAll("poljeNijeIspravno");
            field.getStyleClass().add("poljeIspravno");
        }
        return true;
    }
    private boolean Exists(String text , String pas) {
        if(text.equals("admin") &&  pas.equals("pass")) return true;
        return false;
    }

    public void cancleAction(ActionEvent actionEvent) {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
