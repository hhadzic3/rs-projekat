package ba.unsa.etf.rs.project.Controllers;

import ba.unsa.etf.rs.project.Models.Movie;
import ba.unsa.etf.rs.project.MovieLibraryDAO;
import ba.unsa.etf.rs.project.XMLFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainController {
    public Label statusMsg;
    public TableView<Movie> tblMovies;
    public TableColumn colDirector;
    public TableColumn colTitle;
    public TableColumn colPublishDate;
    private MovieLibraryDAO dao;

    private ObservableList<Movie> listMovies ;

    public MainController(MovieLibraryDAO model) {
        dao = model.getInstance();
        listMovies = FXCollections.observableArrayList(dao.getArrayOfMovies());
    }

    @FXML
    public void initialize() {
        statusMsg.setText("Program started.");
        tblMovies.setItems(listMovies);
        colDirector.setCellValueFactory(new PropertyValueFactory("director"));
        colTitle.setCellValueFactory(new PropertyValueFactory("title"));
        colPublishDate.setCellValueFactory(new PropertyValueFactory("publishDate"));
        colPublishDate.setCellFactory(column -> {
            TableCell<MainController, LocalDate> cell = new TableCell<MainController, LocalDate>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd. MM. yyyy");
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) setGraphic(null);
                    else setGraphic(new Label(item.format(DateTimeFormatter.ofPattern("dd. MM. yyyy"))));
                }
            };
            return cell;
        });
    }

    // Akcije za menuBar
    public void menuOpen(ActionEvent actionEvent) {
        try {
            ArrayList<Movie> movies = XMLFormat.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuSave(ActionEvent actionEvent) {
        ArrayList<Movie> arrayOfMovies = dao.getArrayOfMovies();
        XMLFormat.write(arrayOfMovies);
    }

    public void menuPrint(ActionEvent actionEvent) {
        statusMsg.setText("Printing movies to standard output.");
        System.out.println(listMovies);
    }

    public void menuExit(ActionEvent actionEvent) {
        Stage stage = (Stage) tblMovies.getScene().getWindow();
        stage.close();
    }

    public void menuAdd(ActionEvent actionEvent) {
        actionAdd(actionEvent);
    }

    public void menuChange(ActionEvent actionEvent) {
        actionChange(actionEvent);
    }
    public void menuDelete(ActionEvent actionEvent) {
        actionDelete(actionEvent);
    }
    public void menuAbout(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"));
            root = loader.load();
            stage.setTitle("About");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Akcije za dugmad
    public void actionAdd(ActionEvent actionEvent) {
        statusMsg.setText("Adding new movie.");
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editmovie.fxml"));
            EditController editController = new EditController(null);
            loader.setController(editController);
            root = loader.load();
            stage.setTitle("Add/Edit movie");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Movie movie = editController.getMovie();
                if (movie != null) {
                    dao.addMovie(movie);
                    listMovies.setAll(dao.getArrayOfMovies());
                    statusMsg.setText("Movie added.");
                }
                else statusMsg.setText("Movie not added.");
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void actionChange(ActionEvent actionEvent) {
        statusMsg.setText("Editing movie.");
        Movie movie = tblMovies.getSelectionModel().getSelectedItem();
        if (movie == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editmovie.fxml"));
            EditController editController = new EditController(movie);
            loader.setController(editController);
            root = loader.load();
            stage.setTitle("Add/Edit movie");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Movie newBook = editController.getMovie();
                if (newBook != null) {
                    dao.updateMovie(newBook);
                    listMovies.setAll(dao.getArrayOfMovies());
                    statusMsg.setText("Movie edited.");
                }
                else statusMsg.setText("Movie not edited.");
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionDelete(ActionEvent actionEvent) {
        statusMsg.setText("Deleting movie.");
        Movie movie = tblMovies.getSelectionModel().getSelectedItem();
        if (movie == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje filma "+movie.getTitle());
        alert.setContentText("Da li ste sigurni da želite obrisati film " +movie.getTitle()+"?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.deleteMovie(movie);
            listMovies.setAll(dao.getArrayOfMovies());
            statusMsg.setText("Movie deleted.");
        }
        else statusMsg.setText("Movie not deleted.");
    }


    public void priceAction (ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/price.fxml"));
            PriceController editController = new PriceController();
            loader.setController(editController);
            root = loader.load();
            stage.setTitle("Price list of movies");
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
