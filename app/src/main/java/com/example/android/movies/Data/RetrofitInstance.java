package com.example.android.movies.Data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;

    public static GetMovies getMovies() {

        if (retrofit == null) {

            String BASE = "https://api.themoviedb.org/3/";

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(GetMovies.class);
    }
}
