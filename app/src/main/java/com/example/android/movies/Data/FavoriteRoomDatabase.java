package com.example.android.movies.Data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by XXX on 05-Aug-18.
 */

@Database(entities = {FavoriteMovie.class}, version = 1, exportSchema = false)
public abstract class FavoriteRoomDatabase extends RoomDatabase {

    private static FavoriteRoomDatabase INSTANCE;
    public abstract MovieDao movieDao();
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public static FavoriteRoomDatabase getDatabase(Context context) {

        if (INSTANCE == null) {

            synchronized (FavoriteRoomDatabase.class) {

                INSTANCE = Room.databaseBuilder(context,
                        FavoriteRoomDatabase.class,
                        "movies_database").
                        build();
            }
        }

        return INSTANCE;
    }

}
