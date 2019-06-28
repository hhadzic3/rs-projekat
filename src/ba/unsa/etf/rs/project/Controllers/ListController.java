package ba.unsa.etf.rs.project.Controllers;

import ba.unsa.etf.rs.project.Models.User;
import ba.unsa.etf.rs.project.DAO.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
}
