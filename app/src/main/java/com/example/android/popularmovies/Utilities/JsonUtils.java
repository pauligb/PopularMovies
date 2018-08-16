package com.example.android.popularmovies.Utilities;

import android.support.annotation.NonNull;

import com.example.android.popularmovies.MovieInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static MovieInfo parseMovieInfoJObject(JSONObject jObject) {
        String originalTitle;
        String title;
        String posterPath;
        String synopsis; // Overview in themoviedb and Synopsis for Udacity project
        String userRating; // Vote Average in themoviedb and User rating for Udacity project
        String releaseDate;

        try {
            originalTitle = jObject.getString("original_title");
            title = jObject.getString("title");
            posterPath = jObject.getString("poster_path");
            synopsis = jObject.getString("overview");
            userRating = jObject.getString("vote_average");
            releaseDate = jObject.getString("release_date");
        }catch (JSONException e) {
            throw new RuntimeException(e);
        }

        MovieInfo movieInfo = new MovieInfo(originalTitle,title, posterPath,synopsis, userRating, releaseDate);
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
