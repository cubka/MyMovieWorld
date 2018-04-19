package com.movies.icuba.mymovieworld.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by icuba on 2/10/2018.
 */

public class Movie implements Serializable {

    public int id;
    public String original_title;
    public String poster_path;
    public String overview;
    public String title;
    public String vote_average;
    public String release_date;
    public String status;
    public ArrayList<Cast> cast;

    public boolean isFavorite() {
        return favorite;
    }

    public boolean favorite;



    public Movie(){

    }

    public Movie(int id, String original_title, String poster_path, String overview, String title, String vote_average) {
        this.id = id;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.title = title;
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }
}
