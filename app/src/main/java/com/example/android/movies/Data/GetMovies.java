package com.example.android.movies.Data;

import com.example.android.movies.Files.MovieDetails;
import com.example.android.movies.Files.Results;
import com.example.android.movies.Files.Reviews;
import com.example.android.movies.Files.Videos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GetMovies {

    @GET()
    Call<Results> getMovies(@Url String url);

    @GET()
    Call<MovieDetails> getMovieDetails(@Url String url);

    @GET()
    Call<Videos> getMovieVideos(@Url String url);

    @GET()
    Call<Reviews> getMovieReviews(@Url String url);

    @GET()
    Call<Results> getMoviesWithParameters(@Url String url);

}
