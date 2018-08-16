package com.example.android.popularmovies;

public class MovieInfo {

    String originalTitle;
    String title;
    String posterPath;
    String synopsis; // Overview in themoviedb and Synopsis for Udacity project
    String userRating; // Vote Average in themoviedb and User rating for Udacity project
    String releaseDate;

    public MovieInfo(String vOriginalTitle, String vTitle, String vPosterPath, String vSynopsis, String vUserRating, String vReleaseDate)
    {
        this.originalTitle = vOriginalTitle;
        this.title = vTitle;
        this.posterPath = vPosterPath;
        this.synopsis = vSynopsis;
        this.userRating = vUserRating;
        this.releaseDate = vReleaseDate;
    }
}
