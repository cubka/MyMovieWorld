package com.movies.icuba.mymovieworld.drawer;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.movies.icuba.mymovieworld.adapteri.PeopleViewAdapter;
import com.movies.icuba.mymovieworld.api.RestApi;
import com.movies.icuba.mymovieworld.models.People;
import com.movies.icuba.mymovieworld.models.People_Model;
import com.movies.icuba.mymovieworld.models.request_token.User;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    People model;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    PeopleViewAdapter adapter;
    People_Model people_model;

    @BindView(R.id.swipe)
    SwipeRefreshLayout refreshLayout;
    RestApi api;
    User user;
    String sessionId = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                PeopleActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        final ImageView imageView = view.findViewById(R.id.imageView);
        final TextView name = view.findViewById(R.id.name);
        final TextView username = view.findViewById(R.id.textView);

        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new GridLayoutManager(this, 1));
        api = new RestApi(PeopleActivity.this);


        user = SharedPrefrences.getUser(PeopleActivity.this);
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
                                Picasso.with(PeopleActivity.this).load("http://www.gravatar.com/avatar/" + user.avatar.gravatar.hash).into(imageView);


                            } else {
                                Toast.makeText(PeopleActivity.this, "error", Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                }
            });
        }


            Call<People_Model> call = api.getPeople();
            call.enqueue(new Callback<People_Model>() {
                @Override
                public void onResponse(Call<People_Model> call, Response<People_Model> response) {

                    if (response.code() == 200) {

                        people_model = response.body();
                        adapter = new PeopleViewAdapter(PeopleActivity.this, people_model.getResults());
                        recycler.setAdapter(adapter);

                    }

                }

                @Override
                public void onFailure(Call<People_Model> call, Throwable t) {


                }
            });

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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_explore) {
            Intent f = new Intent(PeopleActivity.this, Explore.class);
            startActivity(f);
            finish();


            // Handle the camera action
        } else if (id == R.id.nav_favourites) {
            Intent f = new Intent(PeopleActivity.this, FavouritesActivity.class);
            startActivity(f);
            finish();

        } else if (id == R.id.nav_rating) {
            Intent f = new Intent(PeopleActivity.this, RatedActivity.class);
            startActivity(f);
            finish();

        } else if (id == R.id.nav_watchlist) {
            Intent f = new Intent(PeopleActivity.this, WatchlistActivity.class);
            startActivity(f);
            finish();

        } else if (id == R.id.nav_people) {
            Intent f = new Intent(PeopleActivity.this, PeopleActivity.class);
            startActivity(f);
            finish();


        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}