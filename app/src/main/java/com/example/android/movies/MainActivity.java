package com.example.android.movies;

import static com.example.android.movies.Data.Constants.GENRE;
import static com.example.android.movies.Data.Constants.KEYWORD;
import static com.example.android.movies.Data.Constants.SORT_BY;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.android.movies.Adapters.GridAdapter;
import com.example.android.movies.Data.RetrofitInstance;
import com.example.android.movies.Files.Result;
import com.example.android.movies.Files.Results;
import com.example.android.movies.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String BASE = "https://api.themoviedb.org/3";
    private static final String DISCOVER = "discover";
    private static final String MOVIE = "movie";
    private static final String SEARCH = "search";
    private static final String WITH_GENRES = "with_genres";
    private static final String QUERY = "query";
    private static final String API = "api_key";

    //TODO: Insert API-KEY here:
    public static final String API_KEY = "";
    private static final String PAGE = "page";

    private GridAdapter adapter;
    private int i;
    private String keyword;
    private String genre = "";
    private String sortBy = "";
    private String currentPart;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        i++;

        binding.progressBar.setVisibility(View.VISIBLE);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        setup();

        if (isOnline()) {

            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.empty.setVisibility(View.GONE);
            getMovies();

        } else {

            binding.recyclerView.setVisibility(View.GONE);
            binding.empty.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        switch (key) {

            case KEYWORD:

                keyword = sharedPreferences.getString(key, "");
                break;

            case SORT_BY:

                sortBy = sharedPreferences.getString(key, getResources().getString(R.string.popularity_value));
                break;

            case GENRE:

                genre = sharedPreferences.getString(key, getResources().getString(R.string.all));
                break;
        }

        getMovies();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).
                unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemSelected = item.getItemId();

        if (itemSelected == R.id.next_page) {

            startNextPage();
        } else if (itemSelected == R.id.previous_page) {

            startPreviousPage();
        } else if (itemSelected == R.id.search) {

            Intent searchIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(searchIntent);
        } else if (itemSelected == R.id.favorites) {

            Intent favoriteIntent = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(favoriteIntent);
        } else if (itemSelected == R.id.refresh) {

            getMovies();
        }

        return true;

    }

    private boolean isOnline() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private String buildUrl(String k, String g, String s) {

        Uri movieUrl = Uri.parse(BASE);
        Uri.Builder uriBuilder = movieUrl.buildUpon();

        if (g.equals(getString(R.string.all))) {

            g = "";
        }
        if (!k.equals("")) {

            String[] parts;

            parts = k.split("\\s+");
            currentPart = parts[0];
            if (k.contains(" ")) {

                for (int m = 1; m < parts.length; m++) {

                    currentPart += "+" + parts[m];
                }
            }
        }
        if (k.equals("")) {

            movieUrl = uriBuilder.
                    appendPath(DISCOVER).
                    appendPath(MOVIE).
                    appendQueryParameter(WITH_GENRES, g).
                    appendQueryParameter(SORT_BY, s).
                    appendQueryParameter(API, API_KEY).
                    appendQueryParameter(PAGE, String.valueOf(i)).
                    build();
        } else {

            movieUrl = uriBuilder.
                    appendPath(SEARCH).
                    appendPath(MOVIE).
                    appendQueryParameter(QUERY, currentPart).
                    appendQueryParameter(WITH_GENRES, g).
                    appendQueryParameter(SORT_BY, s).
                    appendQueryParameter(API, API_KEY).
                    appendQueryParameter(PAGE, String.valueOf(i)).
                    build();

        }

        return movieUrl.toString();
    }

    private void setup() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        keyword = sharedPreferences.getString(KEYWORD, "");
        sortBy = sharedPreferences.getString(SORT_BY, getString(R.string.popularity_value));
        genre = sharedPreferences.getString(GENRE, getString(R.string.all));
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void startNextPage() {

        buildUrl(keyword, genre, sortBy);
        i++;
        getMovies();
    }

    private void startPreviousPage() {

        if (i > 1) {

            buildUrl(keyword, genre, sortBy);
            i--;
            getMovies();
        } else {

            Toast.makeText(this, getString(R.string.this_is_the_first_page_info), Toast.LENGTH_SHORT).show();
        }
    }

    private void getMovies() {

        RetrofitInstance.getMovies().getMovies(buildUrl(keyword, genre, sortBy)).enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {

                if (response.body() != null && response.body().getResults().size() > 0) {

                    binding.pageTextView.setText(getString(R.string.page, i));

                    List<Result> results = response.body().getResults();
                    adapter = new GridAdapter((ArrayList<Result>) results);
                    binding.recyclerView.setAdapter(adapter);
                    binding.empty.setVisibility(View.GONE);
                } else {

                    binding.empty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {

                binding.empty.setVisibility(View.VISIBLE);
            }
        });

        binding.progressBar.setVisibility(View.INVISIBLE);
    }

}
