package com.example.android.movies;

import static com.example.android.movies.Data.Constants.ID;
import static com.example.android.movies.Data.Constants.TITLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.movies.Adapters.DetailFragmentAdapter;
import com.example.android.movies.Data.FavoriteMovie;
import com.example.android.movies.Data.MovieViewModel;
import com.example.android.movies.Data.RetrofitInstance;
import com.example.android.movies.Files.MovieDetails;
import com.example.android.movies.databinding.ActivityDetailBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayoutMediator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by XXX on 09-Aug-18.
 */

public class DetailsActivity extends AppCompatActivity {

    private int movieId;
    private MovieViewModel movieViewModel;
    private boolean isFavorite;
    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0);
        }

        Intent detailIntent = getIntent();
        Bundle bundle = new Bundle();

        movieId = detailIntent.getIntExtra(ID, 0);

        if (actionBar != null) {
            actionBar.setTitle(detailIntent.getStringExtra(TITLE));
        }

        bundle.putInt(ID, movieId);

        DetailFragmentAdapter detailFragmentAdapter = new DetailFragmentAdapter(this, bundle);

        binding.viewPager.setAdapter(detailFragmentAdapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {

            switch (position) {

                case 0:

                    tab.setText(getResources().getString(R.string.details));
                    break;
                case 1:

                    tab.setText(getResources().getString(R.string.videos));
                    break;
                case 2:

                    tab.setText(getResources().getString(R.string.reviews));
                    break;
            }

        }).attach();


        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieViewModel.getIdMovie(movieId).observe(this, favoriteMovie -> isFavorite = favoriteMovie != null);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        getMenuInflater().inflate(R.menu.detail_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        int itemSelected = item.getItemId();

        if (itemSelected == R.id.share) {

            Intent shareIntent = ShareCompat.IntentBuilder.from(this).
                    setChooserTitle("Check out this movie!!").
                    setType("text/plain").
                    setText("Title").
                    getIntent();
            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(shareIntent);
            }
        } else if (itemSelected == R.id.favorite) {

            RetrofitInstance.getMovies().
                    getMovieDetails(
                            "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + MainActivity.API_KEY + "&language=en-US")
                    .enqueue(new Callback<MovieDetails>() {
                        @Override
                        public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {

                            if (response.body() != null && !isFavorite) {

                                FavoriteMovie favoriteMovie = new FavoriteMovie(
                                        response.body().getOriginalTitle(),
                                        response.body().getPosterPath(),
                                        response.body().getOverview(),
                                        response.body().getReleaseDate(),
                                        response.body().getVoteAverage(),
                                        response.body().getId());

                                movieViewModel.insert(favoriteMovie);

                                Snackbar snackbar = Snackbar.make(binding.viewPager, getString(R.string.added_to_favorites), BaseTransientBottomBar.LENGTH_SHORT)
                                        .setAction("Undo", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {


                                        movieViewModel.deleteIdMovie(favoriteMovie);
                                    }
                                });
                                snackbar.show();
                            } else {

                                Snackbar snackbar = Snackbar.make(binding.viewPager, getString(R.string.already_favorite), BaseTransientBottomBar.LENGTH_SHORT);
                                snackbar.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MovieDetails> call, Throwable t) {

                            Toast.makeText(DetailsActivity.this, "Error loading data. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });

        } else {

            Intent backIntent = new Intent(DetailsActivity.this, MainActivity.class);
            startActivity(backIntent);
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        movieViewModel.getIdMovie(movieId).removeObservers(this);
    }
}
