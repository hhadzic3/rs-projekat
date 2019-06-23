package ba.unsa.etf.rs.project.Controllers;

import ba.unsa.etf.rs.project.Models.User;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ReturnController {
    public TextField titleFld;
    public TextField nameFld;
    public TextField surnameFld;
    private User user;

    public void OKAction(ActionEvent actionEvent) {

    }

    public void CancleAction(ActionEvent actionEvent) {
        user = null;
        Stage stage = (Stage) titleFld.getScene().getWindow();
        stage.close();
    }
}
