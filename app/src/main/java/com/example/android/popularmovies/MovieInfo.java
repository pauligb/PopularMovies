package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieInfo implements Parcelable {
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

    // This is where you write the values you want to save to the `Parcel`.  
    // The `Parcel` class has methods defined to help you save all of your values.  
    // Note that there are only methods defined for simple values, lists, and other Parcelable objects.  
    // You may need to make several classes Parcelable to send the data you want.
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(originalTitle);
        out.writeString(title);
        out.writeString(posterPath);
        out.writeString(synopsis);
        out.writeString(userRating);
        out.writeString(releaseDate);
    }

    // Using the `in` variable, we can retrieve the values that 
    // we originally wrote into the `Parcel`.  This constructor is usually 
    // private so that only the `CREATOR` field can access.
    private MovieInfo(Parcel in) {
        originalTitle = in.readString();
        title = in.readString();
        posterPath = in.readString();
        synopsis = in.readString();
        userRating = in.readString();
        releaseDate = in.readString();
    }

    // In the vast majority of cases you can simply return 0 for this.  
    // There are cases where you need to use the constant `CONTENTS_FILE_DESCRIPTOR`
    // But this is out of scope of this tutorial
    @Override
    public int describeContents() {
        return 0;
    }

    // After implementing the `Parcelable` interface, we need to create the 
    // `Parcelable.Creator<MovieInfo> CREATOR` constant for our class; 
    // Notice how it has our class specified as its type.  
    public static final Parcelable.Creator<MovieInfo> CREATOR
            = new Parcelable.Creator<MovieInfo>() {

        // This simply calls our new constructor (typically private) and 
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public MovieInfo createFromParcel(Parcel in) {
            return new MovieInfo(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };














}
