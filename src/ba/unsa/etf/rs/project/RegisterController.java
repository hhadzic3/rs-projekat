package ba.unsa.etf.rs.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {
    public TextField txtUsername;
    public PasswordField txtPassword;
    public PasswordField txtConfirmPassword;
    public Button btnRegister;

    private Administrator administrator = null;

    public RegisterController(Administrator administrator) {
        this.administrator = administrator;
    }

    public Administrator getAdministrator() {
        return administrator;
    }
    @FXML
    public void initialize(){
    if (administrator != null) {
        txtUsername.setText(administrator.getUsername());
        txtPassword.setText(administrator.getPassword());
        txtConfirmPassword.setText(administrator.getPassword());
    }
    }



    public void register(ActionEvent actionEvent) {
        boolean sveOk = true;
        sveOk &= isEmptyy(txtUsername);
        sveOk &= isEmptyy(txtPassword);
        sveOk &= isEmptyy(txtConfirmPassword);

        if (!txtConfirmPassword.getText().equals(txtPassword.getText())){
            txtPassword.getStyleClass().removeAll("poljeIspravno");
            txtPassword.getStyleClass().add("poljeNijeIspravno");
            txtConfirmPassword.getStyleClass().removeAll("poljeIspravno");
            txtConfirmPassword.getStyleClass().add("poljeNijeIspravno");
            administrator = null;
            return;
        }

        if (!sveOk ){
            administrator = null;
            return;
        }


        if (administrator == null) administrator = new Administrator();
        administrator.setUsername(txtUsername.getText());
        administrator.setPassword(txtPassword.getText());
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close();
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
}
