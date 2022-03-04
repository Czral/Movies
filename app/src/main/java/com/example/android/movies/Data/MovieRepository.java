package com.example.android.movies.Data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import android.os.AsyncTask;

import com.example.android.movies.Files.Reviews;

import java.util.List;

/**
 * Created by XXX on 05-Aug-18.
 */

public class MovieRepository {

    private MovieDao mMovieDao;
    private LiveData<List<FavoriteMovie>> mAllMovies;
    private FavoriteRoomDatabase db;

    MovieRepository(Application application) {

        db = FavoriteRoomDatabase.getDatabase(application);
        mMovieDao = db.movieDao();
        mAllMovies = mMovieDao.getAllMovies();
    }

    public void insert(FavoriteMovie favoriteMovie) {

        FavoriteRoomDatabase.databaseWriteExecutor.execute(() -> mMovieDao.insert(favoriteMovie));
    }

    public LiveData<List<FavoriteMovie>> getAllFavoriteMovies() {

        return mAllMovies;
    }

    public LiveData<FavoriteMovie> getIdMovie(int id) {

        return mMovieDao.getIdMovies(id);
    }

    public void deleteIdMovie(FavoriteMovie movie) {

        FavoriteRoomDatabase.databaseWriteExecutor.execute(() -> mMovieDao.deleteMovie(movie));
    }

    public void deleteAllMovies() {

        FavoriteRoomDatabase.databaseWriteExecutor.execute(() -> mMovieDao.deleteAllMovies());
    }
}

