package com.example.android.popularmovies.Utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    final static String API_KEY_QUERY = "api_key";

    private static final String THEMOVIEDB_URL = "http://api.themoviedb.org/3/";
    private static final String THEMOVIEDB_IMAGES_URL = "http://image.tmdb.org/t/p/";

    final static String MOVIE_PATH = "movie";
    final static String POPULAR_PATH = "popular";

    final static String BILLBOARD_IMAGE_SIZE_PATH = "w185";

    /**
     * Builds the URL used to talk to themoviedb APIs.
     *
     * @param apiKey The API key to query to https://www.themoviedb.org.
     * @return The URL to use to query movies information.
     */
    public static URL buildUrl(String apiKey) {
        Uri builtUri = Uri.parse(THEMOVIEDB_URL).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath(POPULAR_PATH)
                .appendQueryParameter(API_KEY_QUERY , apiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);
        return url;
    }

    /**
     * Builds the String Uri used to fetch the movies poster images.
     *
     * @param posterName Name of the poster image.
     * @return The String Uri from the selected movie poster.
     */
    public static String buildPosterPath(String posterName) {
        Uri builtUri = Uri.parse(THEMOVIEDB_IMAGES_URL).buildUpon()
                .appendPath(BILLBOARD_IMAGE_SIZE_PATH)
                .build();
        return builtUri.toString() + posterName;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
