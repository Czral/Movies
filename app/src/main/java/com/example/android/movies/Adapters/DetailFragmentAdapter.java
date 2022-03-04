package com.example.android.movies.Adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.android.movies.Fragments.DetailsFragment;
import com.example.android.movies.Fragments.ReviewsFragment;
import com.example.android.movies.Fragments.VideosFragment;

/**
 * Created by XXX on 09-Aug-18.
 */

public class DetailFragmentAdapter extends FragmentStateAdapter {

    private Bundle b;

    public DetailFragmentAdapter(@NonNull FragmentActivity fragmentActivity, Bundle bundle) {
        super(fragmentActivity);

        b = bundle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {

            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(this.b);
            return detailsFragment;
        }
        else if (position == 1) {

            VideosFragment videosFragment = new VideosFragment();
            videosFragment.setArguments(this.b);
            return videosFragment;
        }
        else  {

            ReviewsFragment reviewsFragment = new ReviewsFragment();
            reviewsFragment.setArguments(this.b);
            return reviewsFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
