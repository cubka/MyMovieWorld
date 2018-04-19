package com.movies.icuba.mymovieworld.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by icuba on 3/1/2018.
 */

public class Favorites_Model implements Serializable {

    @SerializedName("media_type")

    public String media_type;

    @SerializedName("media_id")

    public int media_id;

    @SerializedName("favorite")

    public boolean favorite = false;



    public Favorites_Model(){}



    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
