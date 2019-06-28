package ba.unsa.etf.rs.project.Models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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
        String expected = "a";
        assertEquals(expected, result);
    }
    @Test
    void idTest() {
        User k = new User("a","b");
        assertEquals(0, k.getId());
    }

    @Test
    void idCtorTest() {
        User k = new User(12,"a","b");
        assertEquals(12, k.getId());
    }

}