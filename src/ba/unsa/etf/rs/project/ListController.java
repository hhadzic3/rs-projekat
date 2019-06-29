package ba.unsa.etf.rs.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ListController {
    public TableView<User> TableList;
    public TableColumn nameCol;
    public TableColumn surnameCol;

    private UserDAO daoUser;
    private ObservableList<User> list2;

    public ListController() {
        daoUser = UserDAO.getInst();
        list2 = FXCollections.observableArrayList(daoUser.listUsers());
    }
    @FXML
    public void initialize() {
        TableList.setItems(list2);
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory("surname"));
    }

    public void CancleAction(ActionEvent actionEvent) {
        Stage stage = (Stage) TableList.getScene().getWindow();
        stage.close();
    }

    public void AddAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/edituser.fxml"));
            UserController editController = new UserController(null);
            loader.setController(editController);
            root = loader.load();
            stage.setTitle("Add user");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                User user = editController.getUsers();
                if (user != null) {
                    daoUser.addUser(user);
                    list2.setAll(daoUser.listUsers());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void DeleteAction(ActionEvent actionEvent) {
        User user = TableList.getSelectionModel().getSelectedItem();
        if (user == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje korisnika "+user.getName());
        alert.setContentText("Da li ste sigurni da želite obrisati korisnika " +user.getName()+"?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            daoUser.removeUser(user);
            list2.setAll(daoUser.listUsers());
        }
    }
}
