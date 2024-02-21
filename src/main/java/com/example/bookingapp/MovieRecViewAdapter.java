package com.example.bookingapp;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import static com.example.bookingapp.MovieActivity.MOVIE_ID_KEY;

public class MovieRecViewAdapter extends RecyclerView.Adapter<MovieRecViewAdapter.ViewHolder> {
    private static final String TAG = "MovieRecViewAdapter";
    private ArrayList<Movie> movies = new ArrayList<>();
    private final Context mContext;
    private final String parentActivity;

    public MovieRecViewAdapter(Context mContext, String parentActivity) {
        this.mContext = mContext;
        this.parentActivity = parentActivity;
    }

// public MovieRecViewAdapter(Context mContext, String parentActivity) {
    //   this.mContext = mContext;
    //   this.parentActivity = this.parentActivity;
    // }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false);
        return new ViewHolder(view, this); // Pasează 'this' ca referință la adapter
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final int currentPosition = holder.getAdapterPosition();

        Log.d(TAG, "onBindViewHolder: Called");

        holder.txtName.setText(movies.get(currentPosition).getName());

        Glide.with(mContext)
                .asBitmap()
                .load(movies.get(currentPosition).getImageUrl())
                .into(holder.imgMovie);

        holder.parent.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, MovieActivity.class);
            intent.putExtra(MOVIE_ID_KEY, movies.get(currentPosition).getId());
            mContext.startActivity(intent);
        });

        holder.txtDirector.setText(movies.get(currentPosition).getDirector());
        holder.txtDescription.setText(movies.get(currentPosition).getShortDesc());

        if (movies.get(currentPosition).isExpanded()) {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);

            switch (parentActivity) {
                case "allMovies":
                    holder.btnDelete.setVisibility(View.GONE);
                    break;
                case "alreadyWatched":
                case "wantToWatch":
                case "currentlyWatching":
                case "favorites":
                    holder.btnDelete.setVisibility(View.VISIBLE);
                    holder.btnDelete.setOnClickListener(v -> {
                        String message = "Are you sure you want to delete " + movies.get(currentPosition).getName() + "?";
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage(message);
                        builder.setPositiveButton("Yes", (dialog, which) -> {
                            boolean removed = false;
                            switch (parentActivity) {
                                case "alreadyWatched":
                                    removed = Utils.getInstance().removeFromWatched(movies.get(currentPosition));
                                    break;
                                case "wantToWatch":
                                    removed = Utils.getInstance().removeFromWantToWatch(movies.get(currentPosition));
                                    break;
                                case "currentlyWatching":
                                    removed = Utils.getInstance().removeFromCurrentlyWatching(movies.get(currentPosition));
                                    break;
                                case "favorites":
                                    removed = Utils.getInstance().removeFromFavorites(movies.get(currentPosition));
                                    break;
                            }
                            if (removed) {
                                Toast.makeText(mContext, "Movie Removed", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("No", (dialogInterface, which) -> {
                            // No action required
                        });
                        builder.create().show();
                    });
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView parent;
        ImageView imgMovie, downArrow, upArrow;
        TextView txtName, txtDirector, txtDescription, btnDelete;
        RelativeLayout expandedRelLayout;
        private final MovieRecViewAdapter adapter;
        public ViewHolder(@NonNull View itemView, MovieRecViewAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            parent = itemView.findViewById(R.id.parent);
            imgMovie = itemView.findViewById(R.id.imgMovie);
            txtName = itemView.findViewById(R.id.txtMovieName);
            downArrow = itemView.findViewById(R.id.btnDownArrow);
            upArrow = itemView.findViewById(R.id.btnUpArrow);
            expandedRelLayout = itemView.findViewById(R.id.expandedRelLayout);
            txtDirector = itemView.findViewById(R.id.txtDirector);
            txtDescription = itemView.findViewById(R.id.txtShortDesc);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            downArrow.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Movie movie = this.adapter.movies.get(position);
                    movie.setExpanded(!movie.isExpanded());
                    this.adapter.notifyItemChanged(position);
                }
            });

            upArrow.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Movie movie = this.adapter.movies.get(position);
                    movie.setExpanded(!movie.isExpanded());
                    this.adapter.notifyItemChanged(position);
                }
            });
        }
    }
}