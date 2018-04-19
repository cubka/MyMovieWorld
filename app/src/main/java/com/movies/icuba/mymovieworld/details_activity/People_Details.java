package com.movies.icuba.mymovieworld.details_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.movies.icuba.mymovieworld.R;
import com.movies.icuba.mymovieworld.SharedPrefrences;
import com.movies.icuba.mymovieworld.adapteri.cast_n_crew.CrewAdapter;
import com.movies.icuba.mymovieworld.api.ApiConstants;
import com.movies.icuba.mymovieworld.api.RestApi;
import com.movies.icuba.mymovieworld.models.MovieDetails;
import com.movies.icuba.mymovieworld.models.People;
import com.movies.icuba.mymovieworld.models.request_token.User;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

public class People_Details extends AppCompatActivity {
    User user;
    People people;

    String persnonId;

    @BindView(R.id.people_det_img)
    ImageView peopleDetailsImg;
    @BindView(R.id.name_text)
    TextView personName;
    @BindView(R.id.born_text)
    TextView  bornText;
    @BindView(R.id.place_of_b_text)
    TextView placeOfBorn;
    @BindView(R.id.bio_text)
    TextView bio_TXT;
    RestApi api;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peps);
        ButterKnife.bind(this);

        user = new User();
        user= SharedPrefrences.getUser(this);

        people = new People();

        people = SharedPrefrences.getPeople (this);
        Picasso.with(this).load(ApiConstants.imgLink + people.getProfile_path()).centerCrop().fit().into(peopleDetailsImg);
         persnonId = people.id;

         api = new RestApi(this);


        Call<People> call = api.getPeopleDetails(persnonId);
        call.enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {

                if (response.code() == 200) {

                    personName.setText(response.body().getName());
                    bornText.setText(response.body().getBirthday());
                    placeOfBorn.setText(response.body().getPlace_of_birth());
                    bio_TXT.setText(response.body().getBiography());


                }

            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {


            }
        });




    }
}
