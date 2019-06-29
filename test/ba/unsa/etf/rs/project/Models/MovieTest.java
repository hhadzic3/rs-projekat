package ba.unsa.etf.rs.project.Models;

import ba.unsa.etf.rs.project.Movie;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {
    @Test
    void getIsbn() {
        Movie k = new Movie("a","b","c",1,"ima svega","action", LocalDate.now(),false);
        assertEquals("c", k.getCategory());
    }

    @Test
    void getPublishDate() {
        Movie k = new Movie("a","b","c",1,"ima svega","action", LocalDate.now(),false);
        assertEquals(LocalDate.now(), k.getPublishDate());
    }

    @Test
    void setPublishDate() {
        Movie k = new Movie("a","b","c",1,"ima svega","action", LocalDate.now(),false);
        assertNotEquals(LocalDate.of(2018, 11, 17), k.getPublishDate());
    }

    @Test
    void toStringTest() {
        Movie k = new Movie("a","b","c",1,"ima svega","action", LocalDate.now(),false);
        String result = "" + k;
        String expected = "a b false";
        assertEquals(expected, result);
    }

    @Test
    void idTest() {
        Movie k = new Movie("a","b","c",1,"ima svega","action", LocalDate.now(),false);
        assertEquals(0, k.getId());
    }

    @Test
    void idCtorTest() {
        Movie k = new Movie(1234,"a","b","c",1,"ima svega","action", LocalDate.now(),false);
        assertEquals(1234, k.getId());
    }
}