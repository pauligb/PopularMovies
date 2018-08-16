package com.example.android.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.popularmovies.Utilities.JsonUtils;
import com.example.android.popularmovies.Utilities.NetworkUtils;

import java.util.ArrayList;

public class BillboardActivity extends AppCompatActivity  implements MoviesInformationAsyncTask.OnTaskCompleted {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    public static Activity mainActivity;

    private ArrayList<MovieInfo> movieInfoArrayList;
    private GridView moviesListView;
    private MovieInfoAdapter movieInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billboard);
        setTitle("Popular Movies");

        moviesListView = findViewById(R.id.billboard_movies_list);
        mainActivity = this;

        NetworkUtils.API_KEY = getResources().getString(R.string.themoviedb_api_key);
        if(NetworkUtils.API_KEY.isEmpty()) {
            Log.e(TAG, "There is no API KEY");
        }

        if(!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
            Log.v(TAG, "No Network connection.");
            return;
        }

        new MoviesInformationAsyncTask(this)
                .execute(MoviesInformationAsyncTask.POPULAR_MOVIES_ID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.billboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
            // TODO: Print a message that there is no internet connection or do something else.
            Log.v(TAG, "No Network connection.");
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_popular_movies:
                setTitle("Popular Movies");
                new MoviesInformationAsyncTask(this)
                        .execute(MoviesInformationAsyncTask.POPULAR_MOVIES_ID);
                break;
            case R.id.action_top_rated:
                setTitle("Top Rated Movies");
                new MoviesInformationAsyncTask(this)
                        .execute(MoviesInformationAsyncTask.TOP_RATED_MOVIES_ID);
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    private void launchDetailMovieActivity(int position) {
        Intent intent = new Intent(this, DetailMovieActivity.class);
        MovieInfo movieInfo = movieInfoArrayList.get(position);
        // This is valid since MovieInfo is Parcelable
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieInfo);
        startActivity(intent);
    }

    // This comes from the MoviesInformationAsyncTask.OnTaskCompleted
    @Override
    public void onTaskCompleted(String popularMoviesPageJson) {
        movieInfoArrayList =
                JsonUtils.parsePopularMoviesPageJson(popularMoviesPageJson);
        movieInfoAdapter = new MovieInfoAdapter(BillboardActivity.mainActivity, movieInfoArrayList);
        moviesListView.setAdapter(movieInfoAdapter);
        moviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                launchDetailMovieActivity(position);
            }
        });
    }
}
