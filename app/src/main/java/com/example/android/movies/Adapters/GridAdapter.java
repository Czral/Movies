package com.example.android.movies.Adapters;

import static com.example.android.movies.Data.Constants.ID;
import static com.example.android.movies.Data.Constants.TITLE;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.movies.DetailsActivity;
import com.example.android.movies.Files.Result;
import com.example.android.movies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by XXX on 25-Jun-18.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {

    private static ArrayList<Result> movieFileArrayList;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class GridViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        public GridViewHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.image_view_list);

            imageView.setOnClickListener(v -> {

                Intent intent = new Intent(itemView.getContext(), DetailsActivity.class);
                intent.putExtra(ID, movieFileArrayList.get(getAbsoluteAdapterPosition()).getId());
                intent.putExtra(TITLE, movieFileArrayList.get(getAbsoluteAdapterPosition()).getTitle());
                itemView.getContext().startActivity(intent);
            });
        }

        public ImageView getImageView() {
            return imageView;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public GridAdapter(ArrayList<Result> dataSet) {
        movieFileArrayList = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);

        return new GridViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull GridViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        if (movieFileArrayList.get(position).getPosterPath() == null) {

            viewHolder.getImageView().setImageResource(R.drawable.no_poster);
        } else {

            Picasso.get().
                    load("https://image.tmdb.org/t/p/w500" + movieFileArrayList.get(position).getPosterPath())
                    .error(R.drawable.no_poster)
                    .into(viewHolder.getImageView());
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return movieFileArrayList.size();
    }
}