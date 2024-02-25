package com.example.mymovies;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import static com.example.mymovies.MovieActivity.MOVIE_ID_KEY;

public class MovieRecViewAdapter extends RecyclerView.Adapter<MovieRecViewAdapter.ViewHolder> {
    private static final String TAG = "MovieRecViewAdapter";
    private ArrayList<Movie> movies = new ArrayList<>();
    private final Context mContext;
    private final String parentActivity;

    public MovieRecViewAdapter(Context mContext, String parentActivity) {
        this.mContext = mContext;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int currentPosition = holder.getAdapterPosition();
        Log.d(TAG, "onBindViewHolder: Apelat");
        final Movie movie = movies.get(position);
        holder.txtName.setText(movie.getName());
        Glide.with(mContext).load(movie.getImageUrl()).into(holder.imgMovie);
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
            if ("allMovies".equals(parentActivity)) {
                holder.btnDelete.setVisibility(View.GONE);
            } else {
                holder.btnDelete.setVisibility(View.VISIBLE);
            }
            String collection;
            switch (parentActivity) {
                case "CurrentlyWatching":
                    collection = "currentlyWatching";
                    break;
                case "FavoriteMovies":
                    collection = "favorites";
                    break;
                case "AlreadyWatched":
                    collection = "watched";
                    break;
                case "WantToWatch":
                    collection = "watchlist";
                    break;
                default:
                    collection = "allMovies";
                    break;
            }
            final String finalCollection = collection;
            holder.btnDelete.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Sunteți sigur că doriți să ștergeți " + movie.getName() + "?");
                builder.setPositiveButton("Da", (dialog, which) -> {
                    if (!"allMovies".equals(finalCollection)) {
                        deleteUserMovie(String.valueOf(movie.getId()), finalCollection);
                    } else {
                        Toast.makeText(mContext, "Colecția nu este setată corect", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Nu", (dialog, which) -> dialog.dismiss());
                AlertDialog dialog = builder.create();
                dialog.show();
            });
        }
    }
    private void deleteUserMovie(String movieId,String collection) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userId).collection(collection).document(movieId)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(mContext, "Filmul a fost șters", Toast.LENGTH_SHORT).show();
                        notifyItemRemoved(Integer.parseInt(movieId));
                    })
                    .addOnFailureListener(e -> Toast.makeText(mContext, "Ștergerea filmului a eșuat", Toast.LENGTH_SHORT).show());
        }
    }
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = new ArrayList<>(movies);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView parent;
        ImageView imgMovie, downArrow, upArrow;
        TextView txtName, txtDirector, txtDescription;
        ImageView btnDelete;
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