package com.movies.icuba.mymovieworld.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by icuba on 2/12/2018.
 */

public class People_Model implements Serializable {
    public ArrayList<People> results;

    public ArrayList<People> getResults() {
        return results;
    }

    public void setResults(ArrayList<People> results) {
        this.results = results;
    }
}
