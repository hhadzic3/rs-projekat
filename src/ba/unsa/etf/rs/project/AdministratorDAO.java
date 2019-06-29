package ba.unsa.etf.rs.project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministratorDAO {
    private static AdministratorDAO inst;
    private Connection connection;
    public static AdministratorDAO getInst() {
        if (inst == null) {
            initialize();
        }
        return inst;
    }

    private static void initialize() {
        inst = new AdministratorDAO();
    }

    public static void deleteInstance() {
        if (inst != null) {
            try {
                inst.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        inst = null;
    }


    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    PreparedStatement getAdministrator;

    public AdministratorDAO(){
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:videoLibrary.db");
            getAdministrator = connection.prepareStatement("SELECT name,password FROM administrator");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(Administrator user){

        String sql = "INSERT INTO administrator (name, password) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            preparedStatement.execute();
            preparedStatement.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    public boolean removeUser(String username){
        String sql = "DELETE FROM administrator WHERE name=" + username;

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);

            preparedStatement.executeUpdate();

            preparedStatement.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String findUserName(String username){

        for(Administrator user: this.listUsers()){
            if (user.getUsername().toLowerCase().equals(username.toLowerCase())){
                return user.getUsername();
            }
        }

        return " ";
    }

    public String findUserPass(String pass){

        for(Administrator user: this.listUsers()){
            if (user.getUsername().toLowerCase().equals(pass.toLowerCase())){
                return user.getPassword();
            }
        }
        return " ";    }

    public List<Administrator> listUsers(){
        String sql = "SELECT name, password FROM administrator";
        List<Administrator> userList = new ArrayList<>();
        try {
            ResultSet rs = this.connection.prepareStatement(sql).executeQuery();
            while (rs.next()){
                Administrator user = new Administrator();
                user.setUsername(rs.getString(1));
                user.setPassword(rs.getString(2));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

}
