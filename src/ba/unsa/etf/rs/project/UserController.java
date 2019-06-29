package ba.unsa.etf.rs.project;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class UserController {
    public TextField fldName;
    public TextField fldSurname;
    public TextField fldPostalNumber;
    User user;


    public UserController(Object o) {
    }

    public void OkAction(ActionEvent actionEvent) {


        // Tek ako je sve ostalo ok validiramo poštanski broj jer je to najsporije
        /*try {
            URL lokacija = new URL("http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj=" + postalNumberField.getText());
            postalNumberField.getStyleClass().removeAll("poljeIspravno");
            postalNumberField.getStyleClass().removeAll("poljeNijeIspravno");
            postalNumberField.getStyleClass().add("poljeProvjeraUToku");
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
                        postalNumberField.getStyleClass().removeAll("poljeProvjeraUToku");
                        postalNumberField.getStyleClass().add("poljeNijeIspravno");
                    });

                } else {
                    dodajIzmijeniVlasnika(mjestoRodjenjaObj, mjestoPrebivalista);

                    Platform.runLater( () -> {
                        postalNumberField.getStyleClass().removeAll("poljeProvjeraUToku");
                        postalNumberField.getStyleClass().add("poljeIspravno");
                        Stage stage = (Stage) placeOfBirth.getScene().getWindow();
                        stage.close();
                    });
                }
            }).start();
        } catch (Exception e) {
            // Do nothing
        }*/

    }

    public void CancleAction(ActionEvent actionEvent) {
    }

    public User getUsers() {
        return user;
    }
}
