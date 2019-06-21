package ba.unsa.etf.rs.project;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MovieLibraryDAO {
    private static MovieLibraryDAO instance;
    private Connection conn;
    private PreparedStatement getMovies, deleteMovies, addMovie, truncMovies, changeMovie, getIdMovies;


    public static MovieLibraryDAO getInstance() {
        if (instance == null) {
            initialize();
        }
        return instance;
    }

    private static void initialize() {
        instance = new MovieLibraryDAO();
    }

    public static void deleteInstance() {
        if (instance != null) {
            try {
                instance.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        instance = null;
    }

    public MovieLibraryDAO() {
        prepareStatements();
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void prepareStatements() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:videoLibrary.db");

            getMovies = conn.prepareStatement("SELECT id, director, title, category, length,  about, actors,publishdate FROM movies ORDER BY id ASC ;");
            addMovie = conn.prepareStatement("INSERT INTO movies values (?, ?, ?, ?, ?, ?, ? , ?)");
            deleteMovies = conn.prepareStatement("DELETE FROM movies where id = ?");
            truncMovies = conn.prepareStatement("DELETE FROM movies where 1=1");
            changeMovie = conn.prepareStatement("UPDATE movies SET  director = ?, title = ?, category = ?, length = ? , " +
                    "about = ? , actors = ?, publishdate = ? where id = ?");
            getIdMovies = conn.prepareStatement("SELECT MAX(id)+1 FROM movies");

        } catch (SQLException e) {
            e.printStackTrace();
            //defaultData();
        }
    }

/*
    public void defaultData() {
        addMovie(new Movie("Meša Selimović" , "Tvrđava" , "abcd" , 500 , LocalDate.now()));
        addMovie(new Movie("Ivo Andrić" , "Travnička hronika" , "abcd" , 500 , LocalDate.now()));
        addMovie(new Movie("J. K. Rowling" , "Harry Potter" , "abcd" , 500 , LocalDate.now()));
    }*/

    public void addMovie(Movie book){
        try {
            ResultSet rs = getIdMovies.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            addMovie.setInt(1, id);
            addMovie.setString(2, book.getDirector());
            addMovie.setString(3, book.getTitle());
            addMovie.setString(4 , book.getCategory());
            addMovie.setInt(5, book.getLength());
            addMovie.setString(6 , book.getAbout());
            addMovie.setString(7 , book.getActors());
            addMovie.setDate(8 , Date.valueOf(book.getPublishDate()));
            addMovie.executeUpdate();
        } catch (SQLException e) {
            e.getErrorCode();
        }
    }

    public ArrayList<Movie> getArrayOfMovies() {
        ArrayList<Movie> books = new ArrayList<>();
        ResultSet result = null;
        try {
            result = getMovies.executeQuery();
            Movie k;
            while (  ( k = getMoviesQuery(result) ) != null )
                books.add(k);
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    private Movie getMoviesQuery(ResultSet result) {
        Movie movie = null;
        try {
            if (result.next() ){
                int id = result.getInt("id");
                String author = result.getString("director");
                String title = result.getString("title");
                String category = result.getString("category");
                String about = result.getString("about");
                String actors = result.getString("actors");
                int duz = result.getInt("length");
                LocalDate publishDate = result.getDate("publishDate").toLocalDate();

                movie = new Movie(id , author , title ,category ,duz ,about , actors, publishDate );
                movie.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movie;
    }

    public void deleteBook(Movie book) {
        try {
            if (book != null) {
                deleteMovies.setInt(1, book.getId());
                deleteMovies.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateBook (Movie movie) {
        try {
                changeMovie.setString(1,movie.getDirector());
                changeMovie.setString(2, movie.getTitle());
                changeMovie.setString(3, movie.getCategory());
                changeMovie.setInt(4, movie.getLength());
                changeMovie.setString(5, movie.getAbout());
                changeMovie.setString(6, movie.getActors());
                changeMovie.setDate(7, Date.valueOf(movie.getPublishDate()));
                changeMovie.setInt(8, movie.getId());
                changeMovie.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearAll() {
        try {
            truncMovies.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
