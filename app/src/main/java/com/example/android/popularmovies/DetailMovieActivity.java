package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class DetailMovieActivity extends AppCompatActivity {
    private static final String LOG_TAG = MovieInfoAdapter.class.getSimpleName();

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        MovieInfo movieInfo = BillboardActivity.movieInfoArrayList.get(position);
        Log.v(LOG_TAG, movieInfo.originalTitle);
    }


    private void closeOnError() {
        finish();
        Toast.makeText(this, "An Error has occurred.", Toast.LENGTH_SHORT).show();
    }
}
