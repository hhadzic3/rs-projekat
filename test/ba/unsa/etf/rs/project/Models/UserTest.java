package ba.unsa.etf.rs.project.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getName() {
        User k = new User("a","b");
        assertEquals("a", k.getName());
    }

    @Test
    void getPassword() {
        User k = new User("a","b");
        assertEquals("b", k.getSurname());
    }

    @Test
    void setName() {
        User k = new User("a","b");
        assertEquals("a", k.getName());
    }
    @Test
    void setPassword() {
        User k = new User("a","b");
        assertEquals("b", k.getSurname());
    }


    @Test
    void toStringTest() {
        User k = new User("a","b");
        String result = "" + k;
        String expected = "a b";
        assertEquals(expected, result);
    }

}