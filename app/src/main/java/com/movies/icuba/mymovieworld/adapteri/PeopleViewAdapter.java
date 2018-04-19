package com.movies.icuba.mymovieworld.adapteri;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.movies.icuba.mymovieworld.R;
import com.movies.icuba.mymovieworld.SharedPrefrences;
import com.movies.icuba.mymovieworld.details_activity.People_Details;
import com.movies.icuba.mymovieworld.models.KnownFor;
import com.movies.icuba.mymovieworld.models.People;
import com.movies.icuba.mymovieworld.other.OnRowClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by icuba on 2/10/2018.
 */

public class PeopleViewAdapter extends RecyclerView.Adapter<PeopleViewAdapter.ViewHolder> {

    ArrayList<People> peoples = new ArrayList<>();
    Context context;
    OnRowClickListener onPeopleClick;


    public PeopleViewAdapter(Context context, ArrayList<People> peoples) {
        this.context = context;
        this.peoples = peoples;
    }



    @Override
    public PeopleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rec_layout_people_look, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(PeopleViewAdapter.ViewHolder holder, int position) {

        final People _people = peoples.get(position);
        holder.peopleName.setText(_people.getName().toString());
        holder.peopleRating.setText(Double.toString(Double.parseDouble(_people.getPopularity())));
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500"+_people.getProfile_path()).into(holder.peopleImg);
        for (KnownFor known : _people.getKnown_for()  ) {

            holder.knownFor.setText(holder.knownFor.getText().toString() + known.getTitle());
        }

        holder.goDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, People_Details.class);
                SharedPrefrences.addPeople(_people, context);
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return peoples.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.people_img)
        ImageView peopleImg;
        @BindView(R.id.people_rating)
        TextView peopleRating;
        @BindView(R.id.people_bioTXT)
        TextView knownFor;
        @BindView(R.id.people_name)
        TextView peopleName;
        @BindView(R.id.go_people_det)
        ImageView goDetails;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
