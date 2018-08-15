package com.example.android.popularmovies.Utilities;

import android.support.annotation.NonNull;

import com.example.android.popularmovies.MovieInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static MovieInfo parseMovieInfoJObject(JSONObject jObject) {
        String title;
        String posterPath;
        try {
            title = jObject.getString("title");
            posterPath = jObject.getString("poster_path");
        }catch (JSONException e) {
            throw new RuntimeException(e);
        }

        MovieInfo movieInfo = new MovieInfo(title, posterPath);
        return movieInfo;
    }

    public static ArrayList<MovieInfo> parsePopularMoviesPageJson(String json) {
        ArrayList<MovieInfo> results = new ArrayList<MovieInfo>();

        try {
            // JSON Objects
            JSONObject jPopularMoviesPage = new JSONObject(json);
            JSONArray jResults = jPopularMoviesPage.getJSONArray("results");

            // Populating Movies information
            for(int i = 0; i < jResults.length();++i){
                results.add(parseMovieInfoJObject(jResults.getJSONObject(i)));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return results;
    }
}
