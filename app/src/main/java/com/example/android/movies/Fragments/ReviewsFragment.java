package com.example.android.movies.Fragments;

import static com.example.android.movies.Data.Constants.ID;

import android.os.Bundle;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.movies.Adapters.ReviewAdapter;
import com.example.android.movies.Files.Reviews;
import com.example.android.movies.MainActivity;
import com.example.android.movies.R;
import com.example.android.movies.Data.RetrofitInstance;
import com.example.android.movies.databinding.ActivityDetailsFragmentBinding;
import com.example.android.movies.databinding.ActivityReviewsBinding;
import com.example.android.movies.databinding.ListReviewsVideosBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by XXX on 09-Aug-18.
 */

public class ReviewsFragment extends Fragment {

    public ReviewsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ListReviewsVideosBinding binding = ListReviewsVideosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.reviewsList.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

        Bundle bundle = getArguments();
        assert bundle != null;
        int id = bundle.getInt(ID);

        String url = "https://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=" + MainActivity.API_KEY;

        RetrofitInstance.getMovies().getMovieReviews(url).enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {

                if (response.body() != null && response.body().getResults().size() > 0) {

                    binding.emptyTextViewVideo.setVisibility(View.GONE);
                    ReviewAdapter reviewAdapter = new ReviewAdapter(response.body().getResults());
                    binding.reviewsList.setAdapter(reviewAdapter);
                } else {

                    binding.emptyTextViewVideo.setVisibility(View.VISIBLE);
                    binding.emptyTextViewVideo.setText(getString(R.string.no_reviews));
                }

            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {

                binding.emptyTextViewVideo.setVisibility(View.VISIBLE);
                binding.emptyTextViewVideo.setText(getString(R.string.no_reviews));
            }
        });

        registerForContextMenu(root);

        return root;
    }

}
