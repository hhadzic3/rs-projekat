package ba.unsa.etf.rs.project;

import ba.unsa.etf.rs.project.Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static UserDAO inst;
    private Connection connection;

    public static UserDAO getInst() {
        if (inst == null) {
            initialize();
        }
        return inst;
    }

    private static void initialize() {
        inst = new UserDAO();
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
    PreparedStatement getIdUsers;
    public UserDAO(){
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:videoLibrary.db");
            getIdUsers =connection.prepareStatement("SELECT MAX(id)+1 FROM users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(User user){
        String sql = "INSERT INTO users (id ,name, password) VALUES (?,?, ?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);

            ResultSet rs = getIdUsers.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSurname());

            preparedStatement.execute();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    public boolean removeUser(String username){
        String sql = "DELETE FROM users WHERE name=" + username;
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
        for(User user: this.listUsers()){
            if (user.getName().toLowerCase().equals(username.toLowerCase())){
                return user.getName();
            }
        }
        return " ";
    }

    public String findUserSur(String surname){
        for(User user: this.listUsers()){
            if (user.getName().toLowerCase().equals(surname.toLowerCase())){
                return user.getSurname();
            }
        }
        return " ";
    }

    public List<User> listUsers(){
        String sql = "SELECT id,name, surname FROM users";
        List<User> userList = new ArrayList<>();
        try {
            ResultSet rs = this.connection.prepareStatement(sql).executeQuery();
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setSurname(rs.getString(3));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }



}
