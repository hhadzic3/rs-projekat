package ba.unsa.etf.rs.project;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
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
