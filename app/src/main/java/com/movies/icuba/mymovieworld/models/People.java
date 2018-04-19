package com.movies.icuba.mymovieworld.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by icuba on 2/10/2018.
 */

public class People implements Serializable {

    public  String popularity;
            public String id;
            public String profile_path;
           public String name;
    ArrayList<KnownFor> known_for;
    ArrayList<MovieDetails> movieDetails;

    String birthday;
    String biography;
    String place_of_birth;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public People() {}

    public ArrayList<MovieDetails> getMovieDetails() {
        return movieDetails;
    }

    public void setMovieDetails(ArrayList<MovieDetails> movieDetails) {
        this.movieDetails = movieDetails;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<KnownFor> getKnown_for() {
        return known_for;
    }

    public void setKnown_for(ArrayList<KnownFor> known_for) {
        this.known_for = known_for;
    }
}
