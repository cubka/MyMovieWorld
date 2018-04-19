package com.movies.icuba.mymovieworld.details_activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.movies.icuba.mymovieworld.R;
import com.movies.icuba.mymovieworld.adapteri.FragmentAdapter;
import com.movies.icuba.mymovieworld.api.RestApi;
import com.movies.icuba.mymovieworld.fragments.CastFragment;
import com.movies.icuba.mymovieworld.fragments.CrewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Cast_and_Crew extends AppCompatActivity {


    @BindView(R.id.pager)
    ViewPager fragmentpager;
    @BindView(R.id.tablayout)
    TabLayout mojtablayout;
    RestApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_and__crew);
        ButterKnife.bind(this);
        setUpViewPager(fragmentpager);
        api = new RestApi(this);
    }

    private void setUpViewPager(ViewPager mojpager) {

        FragmentAdapter adapter = new FragmentAdapter(this.getSupportFragmentManager());


        adapter.addFragment(new CastFragment(), "Cast");
        adapter.addFragment(new CrewFragment(), "Crew");

        mojpager.setAdapter(adapter);
    }
}
