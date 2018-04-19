package com.movies.icuba.mymovieworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.movies.icuba.mymovieworld.adapteri.FragmentAdapter;
import com.movies.icuba.mymovieworld.api.RestApi;
import com.movies.icuba.mymovieworld.drawer.FavouritesActivity;
import com.movies.icuba.mymovieworld.drawer.LoginActivity;
import com.movies.icuba.mymovieworld.drawer.PeopleActivity;
import com.movies.icuba.mymovieworld.drawer.RatedActivity;
import com.movies.icuba.mymovieworld.drawer.WatchlistActivity;
import com.movies.icuba.mymovieworld.fragments.NowPlayingFragment;
import com.movies.icuba.mymovieworld.fragments.PopularFragment;
import com.movies.icuba.mymovieworld.fragments.TopRatedFragment;
import com.movies.icuba.mymovieworld.fragments.UpcomingFragment;
import com.movies.icuba.mymovieworld.models.request_token.User;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Explore extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.pager)ViewPager fragmentpager;
    @BindView(R.id.tablayout)TabLayout mojtablayout;
    String sessionId = "";
    User user;
    RestApi api;
    @BindView(R.id.search)
    EditText search_;



    @Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_explore);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    ButterKnife.bind(this);
    setUpViewPager(fragmentpager);


    api = new RestApi(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        Explore.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        final ImageView imageView = view.findViewById(R.id.imageView);
        final TextView name = view.findViewById(R.id.name);
        final TextView username = view.findViewById(R.id.textView);


        user =  SharedPrefrences.getUser(Explore.this);
       if (user!=null)
        sessionId = user.session_id;


        if(sessionId!=null&&!sessionId.isEmpty()){

            api.checkInternet(new Runnable() {
                @Override
                public void run() {
                    Call<User> call = api.getAccDet (sessionId);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                            if (response.isSuccessful()){

                                user = response.body();

                                name.setText(user.getName());
                                username.setText(user.getUsername());
                                Picasso.with(Explore.this).load("http://www.gravatar.com/avatar/"+user.avatar.gravatar.hash).into(imageView);


                            }

                            else {
                                Toast.makeText(Explore.this, "error", Toast.LENGTH_SHORT).show();
                            }



                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                }
            });

        }
        else  {


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



@SuppressWarnings("StatementWithEmptyBody")
@Override
public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_explore) {

        // Handle the camera action
        } else if (id == R.id.nav_favourites) {
        Intent f = new Intent(Explore.this, FavouritesActivity.class);
        startActivity(f);
            finish();

        } else if (id == R.id.nav_rating) {
        Intent f = new Intent(Explore.this, RatedActivity.class);
        startActivity(f);
            finish();

        } else if (id == R.id.nav_watchlist) {
        Intent f = new Intent(Explore.this, WatchlistActivity.class);
        startActivity(f);
            finish();

        } else if (id == R.id.nav_people) {
        Intent f = new Intent(Explore.this, PeopleActivity.class);
        startActivity(f);
        finish();


        } else if (id == R.id.nav_logout) {
            Intent f = new Intent(Explore.this, LoginActivity.class);
            startActivity(f);
            finish();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
        }

    private void setUpViewPager(ViewPager mojpager) {

        FragmentAdapter adapter = new FragmentAdapter(this.getSupportFragmentManager());


        adapter.addFragment(new PopularFragment(),"Popular");
        adapter.addFragment(new TopRatedFragment(),"Top Rated");
        adapter.addFragment(new UpcomingFragment(),"Upcoming");
        adapter.addFragment(new NowPlayingFragment(),"Now Playing");
        mojpager.setAdapter(adapter);
    }
        }
