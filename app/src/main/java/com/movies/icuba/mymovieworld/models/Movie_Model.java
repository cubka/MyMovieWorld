package com.movies.icuba.mymovieworld.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by icuba on 2/10/2018.
 */

public class Movie_Model implements Serializable{

  public ArrayList<Movie> results;

    public Movie_Model() {

    }

    public Movie_Model(ArrayList<Movie> results) {
        this.results = results;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }
}
