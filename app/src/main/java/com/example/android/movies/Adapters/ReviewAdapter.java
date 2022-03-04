package com.example.android.movies.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.movies.R;
import com.example.android.movies.Files.Review;

import java.util.List;

/**
 * Created by XXX on 11-Aug-18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private static List<Review> movieFileArrayList;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ReviewViewHolder extends RecyclerView.ViewHolder {

        private final TextView authorTextView;
        private final TextView contentTextView;

        public ReviewViewHolder(View view) {
            super(view);

            authorTextView = view.findViewById(R.id.author);
            contentTextView = view.findViewById(R.id.content);

            itemView.setOnClickListener(v -> {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(movieFileArrayList.get(getAbsoluteAdapterPosition()).getUrl()));
                itemView.getContext().startActivity(intent);
            });
        }

        public TextView getAuthorTextView() {
            return authorTextView;
        }
        public TextView getContentTextView() {
            return contentTextView;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public ReviewAdapter(List<Review> dataSet) {
        movieFileArrayList = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_reviews, viewGroup, false);

        return new ReviewAdapter.ReviewViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.getAuthorTextView().setText(movieFileArrayList.get(position).getAuthor());
        viewHolder.getContentTextView().setText(movieFileArrayList.get(position).getContent());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return movieFileArrayList.size();
    }
}
