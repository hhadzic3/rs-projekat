package ba.unsa.etf.rs.project.Models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Movie {
    private String title, actors, category, about,  director;
    private Integer length , id;
    private LocalDate publishDate ;
    public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd. MM. yyyy");
    private Boolean borrowed;



    public Movie() { }

    public Movie(int id, String author, String title, String category, int length, String about, String actors, LocalDate publishDate,Boolean borrowed) {
        this.id = id;
        this.title = title;
        this.actors = actors;
        this.category = category;
        this.about = about;
        this.director = author;
        this.length = length;
        this.publishDate = publishDate;
        this.borrowed = borrowed;
    }
    public Movie( String author, String title, String category, int length, String about, String actors, LocalDate publishDate,Boolean borrowed) {
        this.id = 0;
        this.title = title;
        this.actors = actors;
        this.category = category;
        this.about = about;
        this.director = author;
        this.length = length;
        this.publishDate = publishDate;
        this.borrowed = borrowed;
    }

    public Boolean getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Boolean borrowed) {
        this.borrowed = borrowed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return director +" "+ title + " " +borrowed.booleanValue();
    }
}
