package com.example.android.movies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.android.movies.Adapters.FavoriteAdapter;
import com.example.android.movies.Data.FavoriteMovie;
import com.example.android.movies.Data.MovieViewModel;
import com.example.android.movies.databinding.ActivityFavoriteBinding;

import java.util.List;


public class FavoriteActivity extends AppCompatActivity
        implements FavoriteAdapter.LongClickDeleteEventListener,
        FavoriteAdapter.ClickEventListener {

    private FavoriteAdapter adapter;
    private List<FavoriteMovie> fMovies;
    private MovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityFavoriteBinding binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        binding.list.setLayoutManager(new LinearLayoutManager(this));

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieViewModel.getAllMovies().observe(this, favoriteMovies -> {

            fMovies = favoriteMovies;

            adapter = new FavoriteAdapter(favoriteMovies,
                    FavoriteActivity.this,
                    FavoriteActivity.this);
            binding.list.setAdapter(adapter);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.favorites_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.delete) {

            movieViewModel.deleteAllMovies();
        } else {

            Intent main = new Intent(FavoriteActivity.this, MainActivity.class);
            startActivity(main);
        }
        return true;
    }

    @Override
    public void longClickDelete(int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to remove this movie from Favorites list?")
                .setPositiveButton(android.R.string.yes, (dialogInterface, i) -> {

                    movieViewModel.deleteIdMovie(fMovies.get(position));
                    dialogInterface.dismiss();
                })
                .setNegativeButton(android.R.string.no, (dialogInterface, i) -> dialogInterface.dismiss());

        builder.create().show();
    }

    @Override
    public void click(int position) {

        FavoriteMovie favoriteMovie = fMovies.get(position);
        int movieId = favoriteMovie.getMovieId();

        String stringUri = "https://www.themoviedb.org/movie/" + movieId;

        Intent openIntent = new Intent(Intent.ACTION_VIEW);
        openIntent.setData(Uri.parse(stringUri));
        startActivity(openIntent);

    }
}
