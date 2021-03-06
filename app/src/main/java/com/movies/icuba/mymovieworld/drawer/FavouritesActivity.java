package com.movies.icuba.mymovieworld.drawer;

import android.content.ClipData;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.movies.icuba.mymovieworld.Explore;
import com.movies.icuba.mymovieworld.R;
import com.movies.icuba.mymovieworld.SharedPrefrences;
import com.movies.icuba.mymovieworld.adapteri.MovieVievAdapter;
import com.movies.icuba.mymovieworld.api.RestApi;
import com.movies.icuba.mymovieworld.models.Favorites_Model;
import com.movies.icuba.mymovieworld.models.Movie_Model;
import com.movies.icuba.mymovieworld.models.request_token.User;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouritesActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    String sessionId = "";
    int accId = 0;
    User user;
    RestApi api;
    MovieVievAdapter adapter;
    Movie_Model model;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    MenuItem menuItem;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_layout);


        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                FavouritesActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        user = new User();
        model = new Movie_Model();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        final ImageView imageView = view.findViewById(R.id.imageView);
        final TextView name = view.findViewById(R.id.name);
        final TextView username = view.findViewById(R.id.textView);
        menuItem = navigationView.getMenu().findItem(R.id.nav_logout);


        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new GridLayoutManager(FavouritesActivity.this, 2));
        api = new RestApi(this);


        user = SharedPrefrences.getUser(FavouritesActivity.this);
        if (user != null)
            sessionId = user.session_id;

        if (sessionId != null && !sessionId.isEmpty()) {

            api.checkInternet(new Runnable() {
                @Override
                public void run() {
                    Call<User> call = api.getAccDet(sessionId);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                            if (response.isSuccessful()) {

                                user = response.body();

                                name.setText(user.getName());
                                username.setText(user.getUsername());
                                Picasso.with(FavouritesActivity.this).load("http://www.gravatar.com/avatar/" + user.avatar.gravatar.hash).into(imageView);


                            } else {
                                Toast.makeText(FavouritesActivity.this, "error", Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                }
            });

            api.checkInternet(new Runnable() {
                @Override
                public void run() {
                    Call<Movie_Model> call = api.getFavorite(sessionId);
                    call.enqueue(new Callback<Movie_Model>() {
                        @Override
                        public void onResponse(Call<Movie_Model> call, Response<Movie_Model> response) {

                            if (response.isSuccessful()) {


                                adapter = new MovieVievAdapter(FavouritesActivity.this, response.body().getResults());
                                recycler.setAdapter(adapter);

                            } else {
                                Toast.makeText(FavouritesActivity.this, "error", Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<Movie_Model> call, Throwable t) {

                        }
                    });
                }
            });


           menuItem.setTitle("LOGOUT");

        }

        else {
            menuItem.setTitle("LOGIN");
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
        getMenuInflater().inflate(R.menu.explore, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_explore) {
            Intent f = new Intent(FavouritesActivity.this, Explore.class);
            startActivity(f);
            finish();

            // Handle the camera action
        } else if (id == R.id.nav_favourites) {
            Intent f = new Intent(FavouritesActivity.this, FavouritesActivity.class);
            startActivity(f);
            finish();

        } else if (id == R.id.nav_rating) {
            Intent f = new Intent(FavouritesActivity.this, RatedActivity.class);
            startActivity(f);
            finish();

        } else if (id == R.id.nav_watchlist) {
            Intent f = new Intent(FavouritesActivity.this, WatchlistActivity.class);
            startActivity(f);
            finish();

        } else if (id == R.id.nav_people) {
            Intent f = new Intent(FavouritesActivity.this, PeopleActivity.class);
            startActivity(f);
            finish();


        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
