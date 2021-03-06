package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class DetailMovieActivity extends AppCompatActivity {
    private static final String LOG_TAG = MovieInfoAdapter.class.getSimpleName();

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    private ImageView posterImageView;
    private TextView userRateTextView;
    private TextView releaseDateTextView;
    private TextView synopsisTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        posterImageView = findViewById(R.id.movie_poster);
        userRateTextView = findViewById(R.id.movie_user_rating);
        releaseDateTextView = findViewById(R.id.movie_release_date);
        synopsisTextView = findViewById(R.id.movie_synopsis);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        MovieInfo movieInfo = (MovieInfo) getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (movieInfo  == null) {
            // EXTRA_MOVIE not found in intent
            closeOnError();
            return;
        }
        setTitle(movieInfo.originalTitle);

        String posterPath = NetworkUtils.buildPosterPath(movieInfo.posterPath);
        Picasso.with(this).load(posterPath).into(posterImageView);
        userRateTextView.setText(movieInfo.userRating);
        releaseDateTextView.setText(movieInfo.releaseDate);
        synopsisTextView.setText(movieInfo.synopsis);
    }


    private void closeOnError() {
        finish();
        Toast.makeText(this, "An Error has occurred.", Toast.LENGTH_SHORT).show();
    }
}
