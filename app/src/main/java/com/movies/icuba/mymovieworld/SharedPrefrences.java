package com.movies.icuba.mymovieworld;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.movies.icuba.mymovieworld.models.Movie;
import com.movies.icuba.mymovieworld.models.MovieDetails;
import com.movies.icuba.mymovieworld.models.People;
import com.movies.icuba.mymovieworld.models.request_token.User;

/**
 * Created by icuba on 2/15/2018.
 */

public class SharedPrefrences {
    private static SharedPreferences getPreferences(Context context) {
        return context.getApplicationContext().getSharedPreferences("MySharedPreferencesFile", Activity.MODE_PRIVATE);
    }
    public static void addMovie(Movie people, Context c){
        Gson gson = new Gson();
        String mapStrnig = gson.toJson(people);
        getPreferences(c).edit().putString("people", mapStrnig).apply();
    }
    public static Movie getMovie(Context context){
        return  new Gson().fromJson(getPreferences(context).getString("people", ""), Movie.class);
    }

    public static void addPeople(People people, Context c){
        Gson gson = new Gson();
        String mapStrnig = gson.toJson(people);
        getPreferences(c).edit().putString("people", mapStrnig).apply();
    }
    public static People getPeople(Context context){
        return  new Gson().fromJson(getPreferences(context).getString("people", ""), People.class);
    }




    public static void addUser(User user, Context c) {

        Gson gson = new Gson();
        String mapString = gson.toJson(user);
        getPreferences(c).edit().putString("User", mapString).apply();


    }

    public static User getUser(Context c) {

        return new Gson().fromJson(getPreferences(c).getString("User", ""), User.class);
    }
    public static void addMovieId(MovieDetails moviId, Context c) {

        Gson gson = new Gson();
        String mapString = gson.toJson(moviId);
        getPreferences(c).edit().putString("mId", mapString).apply();


    }

    public static MovieDetails getmoviId(Context c) {

        return new Gson().fromJson(getPreferences(c).getString("mId", ""), MovieDetails.class);
    }

    public static void addFav(Movie favorites_model, Context c) {

        Gson gson = new Gson();
        String mapString = gson.toJson(favorites_model);
        getPreferences(c).edit().putString("Fav", mapString).apply();


    }

    public static Movie getFav(Context c) {

        return new Gson().fromJson(getPreferences(c).getString("Fav", ""), Movie.class);
    }

//    public static void removeUserID(Context c) {
//        getPreferences(c).edit().remove(USERID).apply();
//    }

    public static void removeFav(Context c) {

        getPreferences(c).edit().remove("Fav").apply();
    }
}
