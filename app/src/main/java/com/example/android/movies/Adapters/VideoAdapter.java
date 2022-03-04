package com.example.android.movies.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.movies.Files.Video;
import com.example.android.movies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by XXX on 12-Aug-18.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private static List<Video> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public VideoViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            imageView = view.findViewById(R.id.image_view_list);
            imageView.setOnClickListener(v -> {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + localDataSet.get(getAbsoluteAdapterPosition()).getKey()));
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
    public VideoAdapter(List<Video> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);

        return new VideoViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(VideoViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Picasso.get()
                .load("https://img.youtube.com/vi/" + localDataSet.get(position).getKey() + "/0.jpg")
                .error(R.drawable.no_thumb)
                .into(viewHolder.getImageView());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}