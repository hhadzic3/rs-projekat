package ba.unsa.etf.rs.project;

public class User {
    private int id;
    private String name , surname;
    private int postalNumber;

    public User() {}

    public User(String name, String surname,int num) {
        this.name = name;
        this.surname = surname;
        postalNumber = num;
    }

    public User(int id, String name, String surname,int num) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        postalNumber = num;
    }

    public int getPostalNumber() {
        return postalNumber;
    }

    public void setPostalNumber(int postalNumber) {
        this.postalNumber = postalNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return name;
    }
}
