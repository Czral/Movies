package com.example.android.movies.Fragments;

import static com.example.android.movies.Data.Constants.ID;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.movies.Adapters.VideoAdapter;
import com.example.android.movies.MainActivity;
import com.example.android.movies.R;
import com.example.android.movies.Data.RetrofitInstance;
import com.example.android.movies.Files.Videos;
import com.example.android.movies.databinding.ListReviewsVideosBinding;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by XXX on 09-Aug-18.
 */

public class VideosFragment extends Fragment {

    public VideosFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ListReviewsVideosBinding binding = ListReviewsVideosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle bundle = getArguments();
        assert bundle != null;
        int id = bundle.getInt(ID);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            binding.reviewsList.setLayoutManager(new GridLayoutManager(container.getContext(), 3));
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            binding.reviewsList.setLayoutManager(new GridLayoutManager(getActivity().getBaseContext(), 2));
        }

        String url = "https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=" + MainActivity.API_KEY;

        RetrofitInstance.getMovies().getMovieVideos(url).enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {

                if (response.body() != null && response.body().getResults().size() > 0) {

                    binding.emptyTextViewVideo.setVisibility(View.GONE);
                    VideoAdapter videoAdapter = new VideoAdapter(response.body().getResults());
                    binding.reviewsList.setAdapter(videoAdapter);
                } else {

                    binding.emptyTextViewVideo.setVisibility(View.VISIBLE);
                    binding.emptyTextViewVideo.setText(getString(R.string.no_videos));
                }
            }

            @Override
            public void onFailure(Call<Videos> call, Throwable t) {

                binding.emptyTextViewVideo.setVisibility(View.VISIBLE);
                binding.emptyTextViewVideo.setText(getString(R.string.no_videos));
            }
        });

        registerForContextMenu(root);

        return root;
    }
}
