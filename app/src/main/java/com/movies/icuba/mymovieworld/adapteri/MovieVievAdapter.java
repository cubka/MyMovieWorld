package com.movies.icuba.mymovieworld.adapteri;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.movies.icuba.mymovieworld.R;
import com.movies.icuba.mymovieworld.SharedPrefrences;
import com.movies.icuba.mymovieworld.api.RestApi;
import com.movies.icuba.mymovieworld.details_activity.Movie_Details;
import com.movies.icuba.mymovieworld.models.Favorites_Model;
import com.movies.icuba.mymovieworld.models.Movie;
import com.movies.icuba.mymovieworld.models.request_token.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by icuba on 2/13/2018.
 */

public class MovieVievAdapter extends RecyclerView.Adapter<MovieVievAdapter.ViewHolder> {


        ArrayList<Movie> movies;
//        OnRowClickListener onMovieClickListener;
        Context context;


//
public MovieVievAdapter(Context context,ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;

        }



    @Override
public MovieVievAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rec_layout_movie_look,parent, false);
        ViewHolder viewHolder = new ViewHolder (view);
        return viewHolder;
        }

@Override
public void onBindViewHolder(final MovieVievAdapter.ViewHolder holder, final int position) {
    final Movie movie = movies.get(position);
    int id = movies.get(position).id;
    Picasso.with(context).load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(holder.movieImg);
    holder.movieName.setText(movie.getTitle().toString());
    holder.movieRating.setText(movie.getVote_average());
    holder.movieWatchlist.setText("");


    holder.movieImg.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, Movie_Details.class);
            SharedPrefrences.addMovie(movie, context);
            context.startActivity(intent);
        }
    });



    holder.heart_view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            User user = new User();
            Favorites_Model favorites_model = new Favorites_Model();

            user = SharedPrefrences.getUser(context);


            String session_id = user.session_id;

            favorites_model.media_type = "movie";
            favorites_model.media_id = movie.id;
            favorites_model.favorite = true;

            if (session_id != null && !session_id.isEmpty()) {
                RestApi api2 = new RestApi(context);

                Call<Movie> call = api2.addFavorite("json/application", session_id, favorites_model);

                call.enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        if (response.code() == 200) {


                            SharedPrefrences.addFav(response.body(), context);
                            holder.heart_view.setImageResource(R.drawable.favourites_full_hdpi);






                        }
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {

                        Toast.makeText(context, "Error, cannot apply to Favorites", Toast.LENGTH_SHORT).show();

                    }
                });


            }




        }
    });


}

@Override
public int getItemCount() {
        return movies.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.movie_img)
    ImageView movieImg;
    @BindView(R.id.movie_nameTXT)
    TextView movieName;
    @BindView(R.id.movie_ratingTXT)
    TextView movieRating;
    @BindView(R.id.movie_watchlistTXT)
    TextView movieWatchlist;
    @BindView(R.id.movies_rec_layout)
    LinearLayout moviesLayout;
    @BindView(R.id.heart)
    ImageView heart_view;
    @BindView(R.id.star)
    ImageView star_icon;
    @BindView(R.id.tv)
    ImageView watch_icon;


    public ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);


    }
}
}
