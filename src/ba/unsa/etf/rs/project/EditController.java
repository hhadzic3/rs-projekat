package ba.unsa.etf.rs.project;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
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

public class EditController {
    public TextField fldDirector;
    public TextField fldTitle;
    public TextField fldActors;
    public Spinner<Integer> spinLength;
    public DatePicker dpPublishDate;
    public TextField fldCategory;
    public TextField fldAbout;
    private TextField lastFocused;

    private Movie movie = null;

    public EditController(Movie movie) {
        this.movie = movie;
    }

    @FXML
    public void initialize() {
        if (movie != null) {
            fldDirector.setText(movie.getDirector());
            fldTitle.setText(movie.getTitle());
            fldCategory.setText(movie.getCategory());
            fldAbout.setText(movie.getAbout());
            fldActors.setText(movie.getActors());
            spinLength.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 10000, movie.getLength() , 5));
            dpPublishDate.setValue(movie.getPublishDate());
        }
        dpPublishDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate o, LocalDate n) {
                if (validanDatum(n))
                {
                    dpPublishDate.getStyleClass().removeAll("poljeNijeIspravno");
                    dpPublishDate.getStyleClass().add("poljeIspravno");
                }
                else {
                    dpPublishDate.getStyleClass().removeAll("poljeIspravno");
                    dpPublishDate.getStyleClass().add("poljeNijeIspravno");
                }
            }
        });
    }

    public Movie getMovie() {
        return movie;
    }

    public void clickCancel(ActionEvent actionEvent) {
        movie = null;
        Stage stage = (Stage) fldAbout.getScene().getWindow();
        stage.close();
    }

    public void clickOk(ActionEvent actionEvent) {
        boolean sveOk = true;
        sveOk = isEmptyy(fldDirector);
        sveOk &= isEmptyy(fldCategory);
        sveOk &= isEmptyy(fldTitle);
        sveOk &= isEmptyy(fldAbout);
        sveOk &= isEmptyy(fldActors);

        boolean sveOkdate = true;
        LocalDate datum = dpPublishDate.getValue();
        if (datum != null && datum.isAfter(LocalDate.now())) {
            dpPublishDate.getStyleClass().removeAll("poljeIspravno");
            dpPublishDate.getStyleClass().add("poljeNijeIspravno");
            sveOkdate = false;
        } else {
            dpPublishDate.getStyleClass().removeAll("poljeNijeIspravno");
            dpPublishDate.getStyleClass().add("poljeIspravno");
        }
        if (datum == null){
            dpPublishDate.setValue(LocalDate.now());
        }

        if (!sveOk || !sveOkdate){
            movie = null;
            lastFocused.requestFocus();
            return;
        }


        if (movie == null) movie = new Movie();
        movie.setDirector(fldDirector.getText());
        movie.setTitle(fldTitle.getText());
        movie.setCategory(fldCategory.getText());
        movie.setAbout(fldAbout.getText());
        movie.setActors(fldActors.getText());
        movie.setLength((Integer) spinLength.getValueFactory().getValue());
        movie.setPublishDate(dpPublishDate.getValue());
        Stage stage = (Stage) fldCategory.getScene().getWindow();
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
    private boolean validanDatum(LocalDate n) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd. MM. yyyy");

        String trenutno = LocalDate.now().format(f);

        String [] dmg_tren = trenutno.split(". ");
        int d_tren = Integer.parseInt(dmg_tren[0]);
        int m_tren = Integer.parseInt(dmg_tren[1]);
        int g_tren = Integer.parseInt(dmg_tren[2]) ;

        String uneseno = n.format(f);
        String [] dmg_une = uneseno.split(". ");
        int d_une = Integer.parseInt(dmg_une[0]);
        int m_une = Integer.parseInt(dmg_une[1]);
        int g_une = Integer.parseInt(dmg_une[2]);

        if(g_tren == g_une)
        {
            if(m_tren == m_une)
            {
                if(d_tren == d_une)
                    return true;
                else if(d_tren >d_une)
                    return true;
            }
            else if (m_tren > m_une)
                return true;

        }
        else if(g_tren > g_une)
            return true;

        return false;
    }

}
