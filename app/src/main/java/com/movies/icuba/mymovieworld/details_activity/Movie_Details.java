package com.movies.icuba.mymovieworld.details_activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.movies.icuba.mymovieworld.Explore;
import com.movies.icuba.mymovieworld.R;
import com.movies.icuba.mymovieworld.SharedPrefrences;
import com.movies.icuba.mymovieworld.adapteri.MovieVievAdapter;
import com.movies.icuba.mymovieworld.api.ApiConstants;
import com.movies.icuba.mymovieworld.api.RestApi;
import com.movies.icuba.mymovieworld.drawer.FavouritesActivity;
import com.movies.icuba.mymovieworld.drawer.LoginActivity;
import com.movies.icuba.mymovieworld.drawer.PeopleActivity;
import com.movies.icuba.mymovieworld.drawer.RatedActivity;
import com.movies.icuba.mymovieworld.drawer.WatchlistActivity;
import com.movies.icuba.mymovieworld.models.Favorites_Model;
import com.movies.icuba.mymovieworld.models.Movie;
import com.movies.icuba.mymovieworld.models.MovieDetails;
import com.movies.icuba.mymovieworld.models.Movie_Model;
import com.movies.icuba.mymovieworld.models.Rated_Model;
import com.movies.icuba.mymovieworld.models.Watchlist_Model;
import com.movies.icuba.mymovieworld.models.request_token.User;
import com.movies.icuba.mymovieworld.models.Video;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Movie_Details extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.movie_img_details)
    ImageView movieImg;
    @BindView(R.id.movie_name_details)
    TextView movieName;
    @BindView(R.id.movie_director)
    TextView movieDirector;
    @BindView(R.id.movie_writers)
    TextView movieWriter;
    @BindView(R.id.movie_stars)
    TextView movieStars;
    @BindView(R.id.movie_trailer)
    LinearLayout movieTrailer;
    DrawerLayout drawer;
    @BindView(R.id.movie_description)
    TextView movieDescription;
    @BindView(R.id.writers)
    TextView writerText;
    @BindView(R.id.director)
    TextView directorText;
    @BindView(R.id.stars)
    TextView starsText;
    Movie movie;
    RestApi api;
    MovieDetails credits;
   User user;
   Favorites_Model favorites_model;
   Watchlist_Model watchlist_model;
   Rated_Model rated_model;
   Movie_Model movie_model;


   @BindView(R.id.heart_img_details)
   ImageView heart_view;

   @BindView(R.id.play_btn)
   ImageView play_trailer;
    String session_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_det_drawer_lay);

        user = new User();
        user=SharedPrefrences.getUser(this);

        movie_model = new Movie_Model();



        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        api = new RestApi(this);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                Movie_Details.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        movie = SharedPrefrences.getMovie(this);
        Picasso.with(this).load(ApiConstants.imgLink + movie.getPoster_path()).centerCrop().fit().into(movieImg);
        movieName.setText(movie.getOriginal_title());
        movieDescription.setText(movie.getOverview());
        api.checkInternet(new Runnable() {
            @Override
            public void run() {
                Call<MovieDetails> call = api.getMovieDetails(movie.getId());
                call.enqueue(new Callback<MovieDetails>() {
                    @Override
                    public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                        credits = response.body();
                        SharedPrefrences.addMovieId(credits, Movie_Details.this);
                        for (int i = 0; i < credits.getCrew().size() && i < credits.getCast().size(); i++) {
                            if (credits.getCrew().get(i).getJob().equals("Director")) {
                                if (movieDirector.getText().equals("")) {
                                    movieDirector.setText(movieDirector.getText() + credits.getCrew().get(i).getName());
                                } else
                                    movieDirector.setText(movieDirector.getText() + ", " + credits.getCrew().get(i).getName());
                            }
                            if (credits.getCrew().get(i).getJob().equals("Writer")) {

                                if (movieWriter.getText().equals("")) {
                                    movieWriter.setText(movieWriter.getText() + credits.getCrew().get(i).getName());
                                } else
                                    movieWriter.setText(movieWriter.getText() + ", " + (credits.getCrew().get(i).getName()));
                            }
                            if (movieStars.getText().equals("")) {
                                movieStars.setText(movieStars.getText() + credits.getCast().get(i).getName());
                            } else
                                movieStars.setText(movieStars.getText() + ", " + credits.getCast().get(i).getName());
                        }
                        hideTxt();
                    }

                    @Override
                    public void onFailure(Call<MovieDetails> call, Throwable t) {

                    }
                });
            }



        });

    }

    @OnClick(R.id.play_btn)
    public void setPlay_trailer (View view){

        api.checkInternet(new Runnable() {
            @Override
            public void run() {
                Call<Video> call = api.getVideo(movie.getId());
                call.enqueue(new Callback<Video>() {
                    @Override
                    public void onResponse(Call<Video> call, Response<Video> response) {

                        if (response.isSuccessful()) {



                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" +
                            response.body().results.get(0).key)));

                        } else {
                            Toast.makeText(Movie_Details.this, "error", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(Call<Video> call, Throwable t) {
t.printStackTrace();
                    }
                });
            }
        });



    }

    public void hideTxt (){
        if (movieWriter.getText().equals("")) {
            movieWriter.setVisibility(View.GONE);
            writerText.setVisibility(View.GONE);
        }
        if (movieDirector.getText().equals("")) {
            movieDirector.setVisibility(View.GONE);
            directorText.setVisibility(View.GONE);
        }
        if (movieStars.getText().equals("")) {
            movieStars.setVisibility(View.GONE);
            starsText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.explore, menu);
        return true;
    }
    @OnClick(R.id.cast_n_crew_btn)
    public void onCnCclick (View view){

        startActivity(new Intent(Movie_Details.this, Cast_and_Crew.class));
    }

    @OnClick(R.id.heart_img_details)
    public  void onHearClick (View view) {


            session_id = user.session_id;

            favorites_model = new Favorites_Model();

            favorites_model.media_type = "movie";
            favorites_model.media_id = movie.id;
            favorites_model.favorite = true;

            RestApi api2 = new RestApi(this);

            Call<Movie> call = api2.addFavorite("json/application", session_id, favorites_model);

            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if (response.code() == 200) {

                        movie = response.body();

                        SharedPrefrences.addFav(movie, Movie_Details.this);




                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {

                    Toast.makeText(Movie_Details.this, "Error, cannot apply to Favorites", Toast.LENGTH_SHORT).show();

                }
            });




    }


    @OnClick(R.id.rate_img_details)
    public  void onRateClick (View view){

        String session_id = "";
        session_id = user.session_id;

        int movie_id = 0;
        movie_id = movie.id;

        rated_model = new Rated_Model();

       rated_model.value = 3;

        RestApi apiR = new RestApi(this);

        Call<Movie> call = apiR.addRating(movie_id ,"json/application",session_id,rated_model);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.code() == 200) {

                    movie = response.body();


                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

                Toast.makeText(Movie_Details.this, "Error, cannot rate", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @OnClick(R.id.watch_img_details)
    public void onWclick (View view){

        String session_id = "";
        session_id = user.session_id;

        watchlist_model = new Watchlist_Model();

       watchlist_model.media_type = "movie";
        watchlist_model.media_id = movie.id;
        watchlist_model.watchlist = true;

        RestApi apiW = new RestApi(this);

        Call<Movie> call = apiW.addWatchlist("json/application",session_id, watchlist_model);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.code() == 200) {

                    movie = response.body();



                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

                Toast.makeText(Movie_Details.this, "Error, cannot apply to Watchlist", Toast.LENGTH_SHORT).show();

            }
        });

    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_explore) {
            Intent f = new Intent(Movie_Details.this, Explore.class);
            startActivity(f);
            finish();

            // Handle the camera action
        } else if (id == R.id.nav_favourites) {
            Intent f = new Intent(Movie_Details.this, FavouritesActivity.class);
            startActivity(f);
            finish();

        } else if (id == R.id.nav_rating) {
            Intent f = new Intent(Movie_Details.this, RatedActivity.class);
            startActivity(f);
            finish();

        } else if (id == R.id.nav_watchlist) {
            Intent f = new Intent(Movie_Details.this, WatchlistActivity.class);
            startActivity(f);
            finish();

        } else if (id == R.id.nav_people) {
            Intent f = new Intent(Movie_Details.this, PeopleActivity.class);
            startActivity(f);
            finish();


        } else if (id == R.id.nav_logout) {
            String sid = "";
            if (user != null)
                sid = user.session_id;

            if (sid!=null && !sid.isEmpty()){

                //do you want to logout dialog
            }

            else {

                startActivity(new Intent(Movie_Details.this, LoginActivity.class));
                fileList();
            }


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
