package ba.unsa.etf.rs.project.Controllers;

import ba.unsa.etf.rs.project.DAO.AdministratorDAO;
import ba.unsa.etf.rs.project.DAO.MovieLibraryDAO;
import ba.unsa.etf.rs.project.Models.Administrator;
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

        String username = this.nameField.getText();
        String password = this.passwordField.getText();

        if(username.trim().equals("") || password.trim().equals("")){
            this.nameField.setPromptText("Invalid credentials");
            this.passwordField.setPromptText("Invalid credentials");
            return;
        }

        AdministratorDAO userDAO = new AdministratorDAO();

        String provjera1 = userDAO.findUserName(username);
        String provjera2 = userDAO.findUserPass(username);


          if(provjera2.equals(password) && provjera1.equals(username)){
              Stage stage = new Stage();
              try {
                  MovieLibraryDAO model = MovieLibraryDAO.getInstance();
                  FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainform.fxml"));
                  MainController mainController = new MainController(model);
                  loader.setController(mainController);
                  Parent root = loader.load();
                  stage.setTitle("Movie Library");
                  stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                  stage.show(); } catch (IOException e) {
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
            alert.setHeaderText("User name and password does not exist!");
            alert.setContentText("User name and password does not exist!" );
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
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            RegisterController editController = new RegisterController(null);
            loader.setController(editController);
            root = loader.load();
            stage.setTitle("Add/Edit administrator");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Administrator administrator = editController.getAdministrator();
                if (administrator != null) {
                    userDAO.addUser(administrator);
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
