package ba.unsa.etf.rs.project.Controllers;

import ba.unsa.etf.rs.project.Models.Movie;
import ba.unsa.etf.rs.project.Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BorrowController {
    public TextField titleFld;
    public TextField nameFld;
    public TextField surnameFld;
    private User user;


    public BorrowController() {
    }

    @FXML
    public void initialize() {

    }


    public void OKAction(ActionEvent actionEvent) {




    }

    public void CancleAction(ActionEvent actionEvent) {
        user = null;
        Stage stage = (Stage) titleFld.getScene().getWindow();
        stage.close();
    }
}
