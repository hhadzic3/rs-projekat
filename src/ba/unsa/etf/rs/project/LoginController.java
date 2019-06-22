package ba.unsa.etf.rs.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    private Administrator user;
    private AdministratorDAO userDAO;

    @FXML
    private void initialize(){
        this.userDAO = new AdministratorDAO();
    }

    public void loginAction(ActionEvent actionEvent) {
        boolean sveOk = true;
        sveOk = isEmptyy(nameField);
        sveOk &= isEmptyy(passwordField);

        if (!sveOk) return;

        String username = this.nameField.getText();
        String password = this.nameField.getText();

        if(username.trim().equals("") || password.trim().equals("")){
            this.nameField.setPromptText("Invalid credentials");
            this.passwordField.setPromptText("Invalid credentials");
            return;
        }

        AdministratorDAO userDAO = new AdministratorDAO();

        this.user = userDAO.findUser(username);

        if (this.user == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("User does not exist!");
            alert.setContentText("User does not exist!" );

        } else {
            if(this.user.getPassword().equals(password)){
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

            else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("User password does not exist!");
            alert.setContentText("User password does not exist!" );
            }

        }

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


    public void cancleAction(ActionEvent actionEvent) {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void register() throws IOException {
        Stage stage = null;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
        stage.setTitle("Register");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }


}
