package com.example.android.movies.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by XXX on 05-Aug-18.
 */
@Entity(tableName = "favorites_table")
public class FavoriteMovie {

    public static final String titleColumn = "movie_title";
    public static final String idColumn = "movie_id";

    @PrimaryKey(autoGenerate = true)
    private int mId;
    @ColumnInfo(name = titleColumn)
    private String mTitle;
    private String mImage;
    private String mOverview;
    private String mDate;
    private double mVoteAverage;
    @ColumnInfo(name = idColumn)
    public int mMovieId;

    @Ignore
    public FavoriteMovie(String title, String image, String overview, String date, double voteAverage, int movieId) {

        this.mTitle = title;
        this.mImage = image;
        this.mOverview = overview;
        this.mDate = date;
        this.mVoteAverage = voteAverage;
        this.mMovieId = movieId;
    }

    public FavoriteMovie(int id, String title, String image, String overview, String date, double voteAverage, int movieId) {

        this.mId = id;
        this.mTitle = title;
        this.mImage = image;
        this.mOverview = overview;
        this.mDate = date;
        this.mVoteAverage = voteAverage;
        this.mMovieId = movieId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(double mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    public int getMovieId() {
        return mMovieId;
    }

    public void setMovieId(int moviemId) {
        this.mMovieId = moviemId;
    }
}

