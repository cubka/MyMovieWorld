package com.movies.icuba.mymovieworld.api;

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

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by icuba on 2/10/2018.
 */

public interface ApiService {

  @GET("movie/now_playing")
  Call<Movie_Model> getNowPlaying ();

  @GET("movie/popular")
  Call<Movie_Model> getPopular ();

  @GET("movie/top_rated")
  Call<Movie_Model> getTopRated ();

  @GET("movie/upcoming")
  Call<Movie_Model> getUpcoming ();

  @GET ("search/movie")
  Call<Movie_Model> getSearchedMovie (@Query("query") String term);

  @GET ("person/popular")
  Call<People_Model> getPeople ();

  @GET ("search/person")
  Call<People_Model> getSearchedPeople (@Query("query") String query);

  @GET("movie/{id}/credits")
  Call<MovieDetails> getMovieDetails (@Path("id") int id);

  @GET("authentication/token/new?" + ApiConstants.Api_key)
  Call<User> getFirstTocken ();

  @GET("authentication/session/new?"+ApiConstants.Api_key)
  Call<User> getSession (@Query("request_token") String token);

  @GET("authentication/token/validate_with_login?" + ApiConstants.Api_key)
  Call<User> getLogin (@Query("username") String username,
                       @Query("password") String password,
                       @Query("request_token") String token );

  @GET("authentication/guest_session/new?" + ApiConstants.Api_key)
  Call<User> getGuest ();

  @GET("account?" + ApiConstants.Api_key)
  Call<User> getAccDet (@Query("session_id")  String session_id );

  @GET("movie/{id}/credits")
  Call<MovieDetails> getCredits (@Path("id") int id);

  @GET("account/{account_id}/favorite/movies?" + ApiConstants.Api_key)
  Call<Movie_Model> getFavorite (@Query("session_id")String session_id);

  @POST ("account/{account_id}/favorite?" + ApiConstants.Api_key)
  Call<Movie> addFavorite (@Header("json/application") String con_type, @Query("session_id") String session_id,
                                     @Body Favorites_Model modelf );

  @GET("account/{account_id}/rated/movies?"+ ApiConstants.Api_key)
    Call<Movie_Model> getRated (@Query("session_id")String session_id);

  @POST ("movie/{id}/rating")
    Call<Movie> addRating (@Path("id") int id, @Header("json/application") String con_type, @Query("session_id")
          String session_id, @Body Rated_Model modelr);

  @GET("account/{account_id}/watchlist/movies")
  Call<Movie_Model> getWatchlist (@Query("session_id")String session_id);

  @POST("account/{account_id}/watchlist")
  Call<Movie> addWatchlist (@Header("json/application") String con_type, @Query("session_id") String session_id,
                                     @Body Watchlist_Model modelw);

  @GET("person/{id}")
  Call<People> getPeopleDetails (@Path("id") String id);

  @GET("movie/{id}/videos")
  Call<Video> getVideo (@Path("id") int Id);













}
