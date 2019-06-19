package ba.unsa.etf.rs.project;

import java.time.LocalDate;

public class Movie {
    private String title, actors, category, about,  director;
    private Integer length , id;
    private LocalDate publishDate;


    public Movie() {
    }

    public Movie(Integer id, String title, String actors, String category, String about, String director, Integer length, LocalDate publishDate) {
        this.id = id;
        this.title = title;
        this.actors = actors;
        this.category = category;
        this.about = about;
        this.director = director;
        this.length = length;
        this.publishDate = publishDate;
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
        return title;
    }
}
