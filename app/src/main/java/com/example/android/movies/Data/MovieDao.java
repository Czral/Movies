package com.example.android.movies.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import android.database.Cursor;

import java.util.List;

import retrofit2.http.DELETE;

/**
 * Created by XXX on 05-Aug-18.
 */

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteMovie favoriteMovie);

    @Query("SELECT * FROM favorites_table")
    LiveData<List<FavoriteMovie>> getAllMovies();

    @Query("DELETE FROM favorites_table")
    void deleteAllMovies();

    @Delete
    void deleteMovie(FavoriteMovie movie);

    @Query("SELECT * FROM favorites_table WHERE movie_id =:id")
    LiveData<FavoriteMovie> getIdMovies(int id);

}
