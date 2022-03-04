package com.example.android.movies.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.movies.Data.FavoriteMovie;
import com.example.android.movies.R;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by XXX on 06-Aug-18.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<FavoriteMovie> localDataSet;
    private LongClickDeleteEventListener listener;
    private ClickEventListener clickEventListener;

    public interface LongClickDeleteEventListener {
        void longClickDelete(int position);
    }

    public interface ClickEventListener {
        void click(int position);
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTextView;
        private final ImageView posterImageView;
        private final TextView overviewTextView;
        private final TextView releaseDateTextView;
        private final SmileRating smileRating;
        private final TextView ratingTextView;

        public FavoriteViewHolder(View view) {

            super(view);

            posterImageView = view.findViewById(R.id.movie_poster);
            titleTextView = view.findViewById(R.id.movie_title);
            overviewTextView = view.findViewById(R.id.movie_overview);
            releaseDateTextView = view.findViewById(R.id.release_date);
            smileRating = view.findViewById(R.id.smile_rating);
            ratingTextView = view.findViewById(R.id.rating_text);
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public ImageView getPosterImageView() {
            return posterImageView;
        }

        public TextView getOverviewTextView() {
            return overviewTextView;
        }

        public TextView getReleaseDateTextView() {
            return releaseDateTextView;
        }

        public SmileRating getSmileRating() {
            return smileRating;
        }

        public TextView getRatingTextView() {
            return ratingTextView;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public FavoriteAdapter(List<FavoriteMovie> dataSet,
                           LongClickDeleteEventListener eventListener,
                           ClickEventListener clickEventListener) {
        localDataSet = dataSet;
        this.listener = eventListener;
        this.clickEventListener = clickEventListener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_details_fragment, viewGroup, false);

        return new FavoriteViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(FavoriteViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTitleTextView().setText(localDataSet.get(position).getTitle());
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + localDataSet.get(position).getImage()).
                error(R.drawable.no_poster).
                into(viewHolder.getPosterImageView());
        viewHolder.getOverviewTextView().setText(localDataSet.get(position).getOverview());
        viewHolder.getReleaseDateTextView().setText(
                viewHolder.itemView.getContext().getString(R.string.release_date,
                        parseDate(localDataSet.get(position).getDate())));
        viewHolder.getRatingTextView().setText(String.valueOf(localDataSet.get(position).getVoteAverage()));

        double voteAverage = localDataSet.get(position).getVoteAverage();

        double voteRating = voteAverage / 2;

        if (voteRating < 1) {

            viewHolder.getSmileRating().setSelectedSmile(BaseRating.TERRIBLE, false);
        } else if (voteRating < 2) {

            viewHolder.getSmileRating().setSelectedSmile(BaseRating.BAD, false);
        } else if (voteRating < 3) {

            viewHolder.getSmileRating().setSelectedSmile(BaseRating.OKAY, false);
        } else if (voteRating < 4) {

            viewHolder.getSmileRating().setSelectedSmile(BaseRating.GOOD, false);
        } else {

            viewHolder.getSmileRating().setSelectedSmile(BaseRating.GREAT, false);
        }

        viewHolder.itemView.setOnLongClickListener(v -> {
            listener.longClickDelete(position);
            return false;
        });

        viewHolder.itemView.setOnClickListener(v -> clickEventListener.click(position));
    }

    @Override
    public int getItemCount() {

        if (localDataSet != null) {

            return localDataSet.size();
        }

        return 0;
    }

    private String parseDate(String dateString) {

        String s = "N/A";

        SimpleDateFormat dateFormat = new SimpleDateFormat(dateString, Locale.getDefault());
        try {

            Date date = dateFormat.parse(dateString);
            assert date != null;
            s = dateFormat.format(date);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return s;
    }
}