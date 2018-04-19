package com.movies.icuba.mymovieworld.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by icuba on 2/10/2018.
 */

public class VideoData implements Serializable {

    public String id;
    public String key;
    public String poster_path;
    public String overview;
    public String title;
    public String vote_average;
    public String release_date;
    public String status;
    public ArrayList<Cast> cast;




}
