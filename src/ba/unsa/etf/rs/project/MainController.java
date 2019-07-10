package ba.unsa.etf.rs.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainController {
    public Label statusMsg;
    public TableView<Movie> tblMovies;
    public TableColumn colDirector;
    public TableColumn colTitle;
    public TableColumn colBorrowed;
    public TextField searchFld;
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
        colBorrowed.setCellValueFactory(new PropertyValueFactory("borrowed"));

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Movie> filteredData = new FilteredList<>(listMovies, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchFld.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(movie -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every movie with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (movie.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }
                /*else if (movie.getCategory().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }*/
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Movie> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tblMovies.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tblMovies.setItems(sortedData);

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
                Movie newMovie = editController.getMovie();
                if (newMovie != null) {
                    dao.updateMovie(newMovie);
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
            stage.setScene(new Scene(root,1100,550));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void borrowAction(ActionEvent actionEvent) {
        Movie movie = tblMovies.getSelectionModel().getSelectedItem();
        if (movie == null) return;
        if(movie.getBorrowed() != true) {
            movie.setBorrowed(true);
            dao.updateMovieBorrowed(movie);
            listMovies.setAll(dao.getArrayOfMovies());
        }
    }

    public void returnAction(ActionEvent actionEvent) {
        Movie movie = tblMovies.getSelectionModel().getSelectedItem();
        if (movie == null) return;
        if(movie.getBorrowed() != false) {
            movie.setBorrowed(false);
            dao.updateMovieBorrowed(movie);
            listMovies.setAll(dao.getArrayOfMovies());
        }
    }

    public void listAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/list.fxml"));
            ListController editController = new ListController();
            loader.setController(editController);
            root = loader.load();
            stage.setTitle("List of users");
            stage.setScene(new Scene(root,500,300));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
