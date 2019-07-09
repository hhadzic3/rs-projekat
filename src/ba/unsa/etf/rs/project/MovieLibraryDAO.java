package ba.unsa.etf.rs.project;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MovieLibraryDAO {
    private static MovieLibraryDAO instance;
    private Connection conn;
    private PreparedStatement getMovies, deleteMovies, addMovie, truncMovies, changeMovie, getIdMovies,changeMovieBorrowed;


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

            getMovies = conn.prepareStatement("SELECT id, director, title, category, length,  about, actors,publishdate,borrowed FROM movies ORDER BY id ASC ;");
            addMovie = conn.prepareStatement("INSERT INTO movies values (?, ?, ?, ?, ?, ?, ? , ?,?)");
            deleteMovies = conn.prepareStatement("DELETE FROM movies where id = ?");
            truncMovies = conn.prepareStatement("DELETE FROM movies where 1=1");
            changeMovie = conn.prepareStatement("UPDATE movies SET  director = ?, title = ?, category = ?, length = ? , " +
                    "about = ? , actors = ?, publishdate = ? ,borrowed=? where id = ?");
            getIdMovies = conn.prepareStatement("SELECT MAX(id)+1 FROM movies");
            changeMovieBorrowed = conn.prepareStatement("UPDATE movies SET borrowed=? where id = ?");

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }



    public void addMovie(Movie movie){
        try {
            ResultSet rs = getIdMovies.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            addMovie.setInt(1, id);
            addMovie.setString(2, movie.getDirector());
            addMovie.setString(3, movie.getTitle());
            addMovie.setString(4 , movie.getCategory());
            addMovie.setInt(5, movie.getLength());
            addMovie.setDate(6 , Date.valueOf(movie.getPublishDate()));
            addMovie.setString(7 , movie.getAbout());
            addMovie.setString(8 , movie.getActors());
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
                boolean b = result.getBoolean("borrowed");
                LocalDate publishDate = result.getDate("publishDate").toLocalDate();

                movie = new Movie( author , title ,category ,duz ,about , actors, publishDate,b);
                movie.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movie;
    }

    public void deleteMovie(Movie movie) {
        try {
            if (movie != null) {
                deleteMovies.setInt(1, movie.getId());
                deleteMovies.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateMovie(Movie movie) {
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
    public void updateMovieBorrowed(Movie movie){
        try {
            changeMovieBorrowed.setBoolean(1, movie.getBorrowed());
            changeMovieBorrowed.setInt(2, movie.getId());
            changeMovieBorrowed.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllMovies() {
        try {
            truncMovies.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
