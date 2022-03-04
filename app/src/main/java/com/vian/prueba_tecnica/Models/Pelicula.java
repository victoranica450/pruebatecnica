package com.vian.prueba_tecnica.Models;

public class Pelicula {


    private String id;
    private String original_title;
    private String overview;
    private String popularity;
    private String release_data;
    private String vote_average;
    private String vote_count;
    private String poster_path;
    private String backdrop_path;
    private String original_lenguaje;

    public Pelicula(){

    }

    public Pelicula(String id, String original_title, String overview, String popularity,
                    String release_data, String vote_average, String vote_count,  String poster_path, String backdrop_path, String original_lenguaje
    ) {
        this.id = id;
        this.original_title = original_title;
        this.overview = overview;
        this.popularity = popularity;
        this.release_data = release_data;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.original_lenguaje = original_lenguaje;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getRelease_data() {
        return release_data;
    }

    public void setRelease_data(String release_data) {
        this.release_data = release_data;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOriginal_lenguaje() {
        return original_lenguaje;
    }

    public void setOriginal_lenguaje(String original_lenguaje) {
        this.original_lenguaje = original_lenguaje;
    }
}
