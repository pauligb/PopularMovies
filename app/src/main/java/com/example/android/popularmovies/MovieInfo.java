package com.example.android.popularmovies;

public class MovieInfo {

    String title;
    String posterPath;
    int image; // drawable reference id

    public MovieInfo(String vTitle, String vPosterPath)
    {
        this.title = vTitle;
        this.posterPath = vPosterPath;
    }
}
