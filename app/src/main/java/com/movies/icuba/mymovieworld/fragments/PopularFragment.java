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
import android.widget.Toast;

import com.movies.icuba.mymovieworld.R;
import com.movies.icuba.mymovieworld.adapteri.MovieVievAdapter;
import com.movies.icuba.mymovieworld.api.RestApi;
import com.movies.icuba.mymovieworld.models.Movie_Model;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by icuba on 2/6/2018.
 */

public class PopularFragment extends android.support.v4.app.Fragment {


        private Unbinder mUnbind;
        MovieVievAdapter adapter;
        @BindView(R.id.recycler)
        RecyclerView recycler;
        Movie_Model model;
        RestApi api;
        @BindView(R.id.swipe)
        SwipeRefreshLayout mSwipeRefreshLayout;
        @BindView(R.id.search_it)
        EditText search_;



        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

                View view = inflater.inflate(R.layout.global_layout_one, null);
                mUnbind = ButterKnife.bind(this, view);


                api = new RestApi(getActivity());

                recycler.setHasFixedSize(true);
                recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                                mSwipeRefreshLayout.setRefreshing(false);
                                refreshRecycleView();

                        }
                });

                search_.setVisibility(View.GONE);

                search_.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                            api.checkInternet(new Runnable() {
                                @Override
                                public void run() {
                                    retrofit2.Call<Movie_Model> call = api.getSearchedMovie(search_.getText().toString());
                                    call.enqueue(new Callback<Movie_Model>() {
                                        @Override
                                        public void onResponse(Call<Movie_Model> call, Response<Movie_Model> response) {

                                            if (response.isSuccessful()){
                                                model=response.body();
                                                adapter = new MovieVievAdapter(getActivity(), model.getResults());

                                                if (search_.getText().length() >= 2){
                                                    recycler.setAdapter(adapter);
                                                }

                                            }

//
                                        }

                                        @Override
                                        public void onFailure(Call<Movie_Model> call, Throwable t) {
                                            Toast.makeText(getActivity(), "Someting is wrong", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }
                            });


                        }
                });

                model = new Movie_Model();


                api.checkInternet(new Runnable() {
                        @Override
                        public void run() {
                                retrofit2.Call<Movie_Model> call = api.getPopular();
                                call.enqueue(new Callback<Movie_Model>() {
                                        @Override
                                        public void onResponse(Call<Movie_Model> call, Response<Movie_Model> response) {

                                            model = response.body();
                                            adapter = new MovieVievAdapter(getActivity(), model.getResults());
                                            recycler.setAdapter(adapter);
                                        }

                                        @Override
                                        public void onFailure(Call<Movie_Model> call, Throwable t) {

                                        }
                                });
                        }
                });

                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


                return view;
        }

        @Override
        public void onDestroy() {
                super.onDestroy();
                mUnbind.unbind();
        }

        public void refreshRecycleView() {
                api.checkInternet(new Runnable() {
                        @Override
                        public void run() {
                                retrofit2.Call<Movie_Model> call = api.getPopular();
                                call.enqueue(new Callback<Movie_Model>() {
                                        @Override
                                        public void onResponse(Call<Movie_Model> call, Response<Movie_Model> response) {

                                            model=response.body();
                                            adapter = new MovieVievAdapter(getActivity(), model.getResults());
                                            recycler.setAdapter(adapter);
                                        }

                                        @Override
                                        public void onFailure(Call<Movie_Model> call, Throwable t) {

                                        }
                                });
                        }
                });


        }
}
