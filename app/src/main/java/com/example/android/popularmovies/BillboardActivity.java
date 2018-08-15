package com.example.android.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.popularmovies.Utilities.NetworkUtils;

import java.net.URL;

public class BillboardActivity extends AppCompatActivity {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billboard);
        new FetchBillboardInformationTask().execute();

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
        protected void onPostExecute(String movies) {
            Log.v(TAG, movies);
        }
    }
}
