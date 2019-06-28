package ba.unsa.etf.rs.project.Models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AdministratorTest {
    @Test
    void getName() {
        Administrator k = new Administrator("a","b");
        assertEquals("a", k.getUsername());
    }

    @Test
    void getPassword() {
        Administrator k = new Administrator("a","b");
        assertEquals("b", k.getPassword());
    }

    @Test
    void setName() {
        Administrator k = new Administrator("a","b");
        assertEquals("a", k.getUsername());
    }
    @Test
    void setPassword() {
        Administrator k = new Administrator("a","b");
        assertEquals("b", k.getPassword());
    }


    @Test
    void toStringTest() {
        Administrator k = new Administrator("a","b");
        String result = "" + k;
        String expected = "a b";
        assertEquals(expected, result);
    }

}