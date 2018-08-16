package com.example.android.popularmovies;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieInfoAdapter extends ArrayAdapter<MovieInfo> {
    private static final String LOG_TAG = MovieInfoAdapter.class.getSimpleName();

    public MovieInfoAdapter(Activity context, List<MovieInfo> movieInfoList) {
        super(context, 0, movieInfoList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieInfo movieInfo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_billboard_movie, parent, false);
        }

        ImageView iconView = (ImageView) convertView.findViewById(R.id.list_item_image);
        String posterPath = NetworkUtils.buildPosterPath(movieInfo.posterPath);
        Picasso.with(getContext()).load(posterPath).into(iconView);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.list_item_title);
        titleTextView.setText(movieInfo.title);

        return convertView;
    }
}
