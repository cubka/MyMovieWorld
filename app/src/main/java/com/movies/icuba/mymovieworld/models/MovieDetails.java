package com.movies.icuba.mymovieworld.models;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by icuba on 2/26/2018.
 */

public class MovieDetails  {
    protected int id;
   public ArrayList<Cast> cast = new ArrayList<>();
    public ArrayList<Crew> crew = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Cast> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Cast> cast) {
        this.cast = cast;
    }

    public ArrayList<Crew> getCrew() {
        return crew;
    }

    public void setCrew(ArrayList<Crew> crew) {
        this.crew = crew;
    }
}
