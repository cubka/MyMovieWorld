package com.movies.icuba.mymovieworld.adapteri;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.movies.icuba.mymovieworld.fragments.UpcomingFragment;

import java.util.ArrayList;

/**
 * Created by icuba on 2/7/2018.
 */

public class FragmentAdapter extends FragmentPagerAdapter {


    ArrayList<Fragment> fragmenti = new ArrayList<>();
    ArrayList<String> titles = new ArrayList<String>();

    public void addFragment (Fragment fragment, String title){
        titles.add(title);
        fragmenti.add(fragment);

    }


    public FragmentAdapter(FragmentManager fm) {super(fm);}

    @Override
    public Fragment getItem(int position) {return fragmenti.get(position);}

    @Override
    public int getCount() {return fragmenti.size();}


    @Override
    public CharSequence getPageTitle(int position) {return titles.get(position);}


}