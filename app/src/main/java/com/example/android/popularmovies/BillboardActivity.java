package com.example.android.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.android.popularmovies.Utilities.JsonUtils;
import com.example.android.popularmovies.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class BillboardActivity extends AppCompatActivity {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    public static Activity mainActivity;
    private GridView moviesListView;
    private MovieInfoAdapter movieInfoAdapter;

    public boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billboard);

        moviesListView = findViewById(R.id.billboard_movies_list);
        mainActivity = this;

        String api_key = getResources().getString(R.string.themoviedb_api_key);
        if(api_key.isEmpty()) {
            Log.e(TAG, "There is no API KEY");
        }

        if(isNetworkAvailable(getApplicationContext())) {
            new FetchBillboardInformationTask().execute();
        } else {
            // TODO: Print a message that there is no internet connection or do something else.
            Log.v(TAG, "No Network connection.");
        }
    }

    public class FetchBillboardInformationTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String api_key = getResources().getString(R.string.themoviedb_api_key);
            if(api_key.isEmpty()) {
                Log.e(TAG, "R.string.themoviedb_api_key does not contain a key.");
                return null;
            }

            URL url = NetworkUtils.buildUrl(api_key);
            try {
                return NetworkUtils.getResponseFromHttpUrl(url);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String popularMoviesPageJson) {
            Log.v(TAG, popularMoviesPageJson);

            ArrayList<MovieInfo> movieInfoArrayList =
                    JsonUtils.parsePopularMoviesPageJson(popularMoviesPageJson);
            movieInfoAdapter = new MovieInfoAdapter(BillboardActivity.mainActivity, movieInfoArrayList);
            moviesListView.setAdapter(movieInfoAdapter);
        }
    }
}
