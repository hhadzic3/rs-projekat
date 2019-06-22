package ba.unsa.etf.rs.project;

import javafx.scene.control.Alert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class XMLFormat {


    public static void write ( ArrayList<Movie> movies) {
        XMLEncoder encoder = null;
        try {
            encoder = new XMLEncoder(new FileOutputStream("movies.xml"));
            encoder.writeObject(movies);
            encoder.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Movie> read() throws Exception {
        ArrayList<Movie> movies = new ArrayList<>();
        XMLDecoder decoder = new XMLDecoder(new FileInputStream("movies.xml"));
        movies = (ArrayList<Movie>)decoder.readObject();
        decoder.close();
        return movies;
    }


}
