package com.example.android.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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

import java.net.URL;
import java.util.ArrayList;

public class BillboardActivity extends AppCompatActivity {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    public static Activity mainActivity;

    final public static int POPULAR_MOVIES_ID = 0;
    final public static int TOP_RATED_MOVIES_ID = 1;

    public static ArrayList<MovieInfo> movieInfoArrayList;
    private GridView moviesListView;
    private MovieInfoAdapter movieInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billboard);
        setTitle("Popular Movies");

        moviesListView = findViewById(R.id.billboard_movies_list);
        mainActivity = this;

        String api_key = getResources().getString(R.string.themoviedb_api_key);
        if(api_key.isEmpty()) {
            Log.e(TAG, "There is no API KEY");
        }

        if(!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
            Log.v(TAG, "No Network connection.");
            return;
        }

        new FetchBillboardInformationTask().execute(POPULAR_MOVIES_ID);
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
                new FetchBillboardInformationTask().execute(POPULAR_MOVIES_ID);
                break;
            case R.id.action_top_rated:
                setTitle("Top Rated Movies");
                new FetchBillboardInformationTask().execute(TOP_RATED_MOVIES_ID);
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    public class FetchBillboardInformationTask extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... params) {

            if(params.length == 0) {
                return null;
            }

            String api_key = getResources().getString(R.string.themoviedb_api_key);
            if(api_key.isEmpty()) {
                Log.e(TAG, "R.string.themoviedb_api_key does not contain a key.");
                return null;
            }

            URL url;
            switch (params[0]) {
                case POPULAR_MOVIES_ID:
                    url = NetworkUtils.buildPopularUrl(api_key);
                    break;
                case TOP_RATED_MOVIES_ID:
                    url = NetworkUtils.buildTopRatedUrl(api_key);
                    break;
                default:
                url = NetworkUtils.buildPopularUrl(api_key);
            }

            try {
                return NetworkUtils.getResponseFromHttpUrl(url);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String popularMoviesPageJson) {
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

    private void launchDetailMovieActivity(int position) {
        Intent intent = new Intent(this, DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_POSITION, position);
        startActivity(intent);
    }
}
