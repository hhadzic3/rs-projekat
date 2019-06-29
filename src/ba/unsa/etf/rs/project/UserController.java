package ba.unsa.etf.rs.project;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserController {
    public TextField fldName;
    public TextField fldSurname;
    public TextField fldPostalNumber;
    private User user = null;


    public UserController(User user) {
        this.user = user;
    }

    @FXML
    public void initialize() {

        isEmptyByListener(fldName);
        isEmptyByListener(fldSurname);
        isEmptyByListener(fldPostalNumber);

    }
    private void isEmptyByListener(TextField field) {
        field.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                field.getStyleClass().removeAll("poljeNijeIspravno");
                field.getStyleClass().add("poljeIspravno");
            } else {
                field.getStyleClass().removeAll("poljeIspravno");
                field.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }

    public void OkAction(ActionEvent actionEvent) {
        boolean sveOk = true;
        sveOk = isEmptyy(fldName);
        sveOk &= isEmptyy(fldSurname);
        sveOk &= isEmptyy(fldPostalNumber);


        try {
            URL lokacija = new URL("http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj=" + fldPostalNumber.getText());
            fldPostalNumber.getStyleClass().removeAll("poljeIspravno");
            fldPostalNumber.getStyleClass().removeAll("poljeNijeIspravno");
            fldPostalNumber.getStyleClass().add("poljeProvjeraUToku");
            new Thread(() -> {
                String json = "", line = null;
                BufferedReader ulaz = null;
                try {
                    ulaz = new BufferedReader(new InputStreamReader(lokacija.openStream(),
                            StandardCharsets.UTF_8));
                    while ((line = ulaz.readLine()) != null)
                        json = json + line;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (!json.equals("OK")) {
                    Platform.runLater( () -> {
                        fldPostalNumber.getStyleClass().removeAll("poljeProvjeraUToku");
                        fldPostalNumber.getStyleClass().add("poljeNijeIspravno");
                    });

                } else {

                    Platform.runLater( () -> {
                        fldPostalNumber.getStyleClass().removeAll("poljeProvjeraUToku");
                        fldPostalNumber.getStyleClass().add("poljeIspravno");
                        Stage stage = (Stage) fldName.getScene().getWindow();
                        stage.close();
                    });
                }
            }).start();
        } catch (Exception e) {
            // Do nothing
        }

        if (!sveOk ){
            user = null;
            return;
        }

        if (user == null) user = new User();
        user.setName(fldName.getText());
        user.setSurname(fldSurname.getText());
        user.setPostalNumber(Integer.parseInt(fldPostalNumber.getText()));
        Stage stage = (Stage) fldSurname.getScene().getWindow();
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

    public void CancleAction(ActionEvent actionEvent) {
        user = null;
        Stage stage = (Stage) fldName.getScene().getWindow();
        stage.close();
    }

    public User getUsers() {
        return user;
    }
}
