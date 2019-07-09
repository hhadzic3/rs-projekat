package ba.unsa.etf.rs.project;

import java.beans.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class XMLFormat implements Serializable{


    public static void write ( ArrayList<Movie> movies) {
        XMLEncoder encoder = null;
        try {
            encoder = new XMLEncoder(new FileOutputStream("movies.xml"));
            encoder.setPersistenceDelegate(LocalDate.class,
                    new PersistenceDelegate() {
                        @Override
                        protected Expression instantiate(Object localDate, Encoder encdr) {
                            return new Expression(localDate,
                                    LocalDate.class,
                                    "parse",
                                    new Object[]{localDate.toString()});
                        }
                    });
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
