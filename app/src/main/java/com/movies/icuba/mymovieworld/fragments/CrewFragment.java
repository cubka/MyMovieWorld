package com.movies.icuba.mymovieworld.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.movies.icuba.mymovieworld.R;
import com.movies.icuba.mymovieworld.SharedPrefrences;
import com.movies.icuba.mymovieworld.adapteri.MovieVievAdapter;
import com.movies.icuba.mymovieworld.adapteri.cast_n_crew.CrewAdapter;
import com.movies.icuba.mymovieworld.api.RestApi;
import com.movies.icuba.mymovieworld.models.MovieDetails;
import com.movies.icuba.mymovieworld.models.Movie_Model;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by icuba on 2/28/2018.
 */

public class CrewFragment extends android.support.v4.app.Fragment {


    private Unbinder mUnbind;
    CrewAdapter adapter;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    MovieDetails model;
    RestApi api;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.search_it)
    EditText search_;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        model= new MovieDetails();

        View view = inflater.inflate(R.layout.global_layout_one, null);
        mUnbind = ButterKnife.bind(this, view);

        api = new RestApi(getActivity());

        search_.setVisibility(View.GONE);

        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        model = SharedPrefrences.getmoviId(getActivity());


        Call<MovieDetails> call = api.getCredits(model.getId());
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {

                if (response.code() == 200) {


                    adapter = new CrewAdapter (getActivity(), response.body().getCrew());
                    recycler.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {


            }
        });





        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbind.unbind();
    }



}

