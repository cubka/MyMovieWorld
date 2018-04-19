package com.movies.icuba.mymovieworld.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.movies.icuba.mymovieworld.BuildConfig;
import com.movies.icuba.mymovieworld.models.Favorites_Model;
import com.movies.icuba.mymovieworld.models.Movie;
import com.movies.icuba.mymovieworld.models.MovieDetails;
import com.movies.icuba.mymovieworld.models.Movie_Model;
import com.movies.icuba.mymovieworld.models.People;
import com.movies.icuba.mymovieworld.models.People_Model;
import com.movies.icuba.mymovieworld.models.Rated_Model;
import com.movies.icuba.mymovieworld.models.Watchlist_Model;
import com.movies.icuba.mymovieworld.models.request_token.User;
import com.movies.icuba.mymovieworld.models.Video;
import com.movies.icuba.mymovieworld.other.CheckInternet;
import com.movies.icuba.mymovieworld.other.LoggingInterceptor;
import com.movies.icuba.mymovieworld.other.RequestInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by icuba on 2/10/2018.
 */

public class RestApi {
    public static final int request_max_time_in_seconds = 20;
    private Context activity;

    public RestApi(Context activity) {
        this.activity = activity;
    }

    public Retrofit getRetrofitInstance(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(new RequestInterceptor())
                .readTimeout(request_max_time_in_seconds, TimeUnit.SECONDS)
                .connectTimeout(request_max_time_in_seconds, TimeUnit.SECONDS)
                .build();
        return new  Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
    public ApiService request(){
        return getRetrofitInstance().create(ApiService.class);
    }
    public Call<Movie_Model> getNowPlaying (){
        return request().getNowPlaying();
    }
    public  Call<Movie_Model> getPopular(){
        return request().getPopular();
    }
    public Call<Movie_Model> getTopRated (){
        return  request().getTopRated();
    }
    public Call<Movie_Model> getUpcoming (){
        return request().getUpcoming();
    }
    public Call<MovieDetails> getMovieDetails (int id){
        return request().getMovieDetails(id);
    }
    public Call<Movie_Model> getSearchedMovie (String term){
        return request().getSearchedMovie(term);

    }
    public Call<MovieDetails> getCredits (int id){
        return request().getMovieDetails(id);
    }
    public Call<People_Model> getPeople (){
        return request().getPeople();
    }
    public Call<People_Model> getSearchedPeople (String query){
        return request().getSearchedPeople(query);
    }

    public Call <User> getFirstTocken (){
        return request().getFirstTocken();
    }
    public Call <User> getSession (String token){
        return request().getSession(token);
    }
    public Call <User> getLogin(String username, String password, String token) {
        return  request().getLogin(username, password,token);
    }
    public Call <User> getGuest () {
        return request().getGuest();
    }
    public Call <User> getAccDet (String id) {
        return request().getAccDet(id);
    }


   public Call<Movie_Model> getFavorite (String session_id) {return request().getFavorite(session_id);}

    public Call<Movie> addFavorite (String con_type, String session_id, Favorites_Model modelf )
    {return request().addFavorite(con_type,session_id,modelf);}

    public Call<Movie_Model> getRated (String session_id){return request().getRated(session_id );}

    public Call<Movie> addRating (int id, String con_type, String session_id, Rated_Model modelr) {
        return request().addRating(id,con_type,session_id,modelr);}

    public Call<Movie_Model> getWatchlist (String session_id){return request().getWatchlist(session_id);}

   public Call<Movie> addWatchlist (String con_type, String session_id, Watchlist_Model modelw)
   {return request().addWatchlist(con_type,session_id,modelw);}


   public Call<People> getPeopleDetails (String id)
    {return  request().getPeopleDetails(id);}


    public Call<Video> getVideo (int id)
    {return request().getVideo(id);}





    public void checkInternet(Runnable callback){
        if (CheckInternet.CheckInternetConnectivity(activity, true, callback )){
            callback.run();
        }
    }
    public void checkInternet (Runnable callback, boolean showConnectionDialog){
        if (CheckInternet.CheckInternetConnectivity(activity,showConnectionDialog,callback))
            callback.run();
        else {
            Toast.makeText(activity, "Connection failed, please check your connection in settings", Toast.LENGTH_LONG).show();
        }
    }

    public  void  checkInternet (Runnable callback, boolean showConnetionDialog, String message){
        if (CheckInternet.CheckInternetConnectivity(activity,showConnetionDialog,callback))
            callback.run();
        else {
            if (showConnetionDialog)
                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            else
                Log.d("Connection failed", "" + message);
        }
    }
}