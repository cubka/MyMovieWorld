package com.movies.icuba.mymovieworld.adapteri.cast_n_crew;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.movies.icuba.mymovieworld.R;
import com.movies.icuba.mymovieworld.models.Cast;
import com.movies.icuba.mymovieworld.models.People;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by icuba on 2/28/2018.
 */

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    ArrayList<Cast> casts = new ArrayList<>();
    Context context;

    public CastAdapter(Context context, ArrayList<Cast> casts) {
        this.context = context;
        this.casts = casts;
    }

    @Override
    public CastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cast_n_crewe_recview, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(CastAdapter.ViewHolder holder, int position) {

        final Cast _cast = casts.get(position);
        holder.peopleName.setText(_cast.getName().toString());
        holder.peopleCredit.setText("Playing as : " +  _cast.getCharacter().toString());
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500"+_cast.getProfile_path()).into(holder.peopleImg);

    }

    @Override
    public int getItemCount() {
        return casts.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cast_n_crew_image)
        ImageView peopleImg;
        @BindView(R.id.person_known_as)
        TextView peopleCredit;
        @BindView(R.id.person_name)
        TextView peopleName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
