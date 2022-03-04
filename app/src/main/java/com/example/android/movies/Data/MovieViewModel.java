package com.example.android.movies.Data;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Created by XXX on 05-Aug-18.
 */

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository mRepository;
    private LiveData<List<FavoriteMovie>> movies;

    public MovieViewModel(Application application) {

        super(application);
        mRepository = new MovieRepository(application);
        movies = mRepository.getAllFavoriteMovies();
    }

    public LiveData<List<FavoriteMovie>> getAllMovies() {

        return movies;
    }

    public void insert(FavoriteMovie favoriteMovie) {

        mRepository.insert(favoriteMovie);
    }

    public LiveData<FavoriteMovie> getIdMovie(int id) {

        return mRepository.getIdMovie(id);
    }

    public void deleteIdMovie(FavoriteMovie movie) {

        mRepository.deleteIdMovie(movie);
    }
    public void deleteAllMovies() {

        mRepository.deleteAllMovies();
    }

}
