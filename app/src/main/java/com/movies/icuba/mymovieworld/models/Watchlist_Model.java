package com.movies.icuba.mymovieworld.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by icuba on 3/2/2018.
 */

public class Watchlist_Model implements Serializable {
    @SerializedName("media_type")

    public String media_type;

    @SerializedName("media_id")

    public int media_id;

    @SerializedName("watchlist")

    public boolean watchlist;
}
