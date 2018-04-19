package com.movies.icuba.mymovieworld.drawer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.movies.icuba.mymovieworld.Explore;
import com.movies.icuba.mymovieworld.R;
import com.movies.icuba.mymovieworld.SharedPrefrences;
import com.movies.icuba.mymovieworld.api.RestApi;
import com.movies.icuba.mymovieworld.models.request_token.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_logo_img)
    ImageView imageView_login;
    @BindView(R.id.login_username)
    EditText editText_login;
    @BindView(R.id.login_password)
    EditText editText_password;
    @BindView(R.id.con_as_guest)
    TextView guest;
    @BindView(R.id.login_btn)
    Button login_button;
    RestApi api;
    String req_token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        api = new RestApi(LoginActivity.this);


    }

    @OnClick(R.id.login_btn)
    public void onLoginBtnClick(View view) {

        api.checkInternet(new Runnable() {
            @Override
            public void run() {
                Call<User> call = api.getFirstTocken();
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user =response.body();


                        createLoginId(user);

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });

        finish();
    }

    private void createLoginId(final User user) {
        api.checkInternet(new Runnable() {
            @Override
            public void run() {
                Call<User> call = api.getLogin(editText_login.getText().toString(), editText_password.getText().toString(), user.request_token);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user =response.body();


                        createSession(user);




                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void createSession(final User user) {

        api.checkInternet(new Runnable() {
            @Override
            public void run() {
                Call<User> call = api.getSession (user.request_token);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {



                        SharedPrefrences.addUser(response.body(), LoginActivity.this);


                        startActivity(new Intent(LoginActivity.this,Explore.class));

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });


    }
}
