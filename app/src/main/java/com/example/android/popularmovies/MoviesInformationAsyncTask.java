package com.example.android.popularmovies;

import android.os.AsyncTask;
import android.util.Log;
import com.example.android.popularmovies.Utilities.NetworkUtils;

import java.net.URL;

public class MoviesInformationAsyncTask extends AsyncTask<Integer, Void, String> {
    private static final String TAG = MoviesInformationAsyncTask.class.getSimpleName();

    final public static int POPULAR_MOVIES_ID = 0;
    final public static int TOP_RATED_MOVIES_ID = 1;

    public interface OnTaskCompleted {
        void onTaskCompleted(String response);
    }

    private OnTaskCompleted taskCompleted;

    public MoviesInformationAsyncTask(OnTaskCompleted activityTaskCompleted){
        this.taskCompleted = activityTaskCompleted;
    }

    @Override
    protected String doInBackground(Integer... params) {

        if(params.length == 0) {
            return null;
        }

        String api_key = NetworkUtils.API_KEY;
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
        if(taskCompleted == null) {
            return;
        }
        taskCompleted.onTaskCompleted(popularMoviesPageJson);
    }
}