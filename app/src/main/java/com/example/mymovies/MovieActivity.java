package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class MovieActivity extends AppCompatActivity {

    public static final String MOVIE_ID_KEY = "movieId";

    private TextView txtMovieName, txtDirector, txtGenre, txtDescription;
    private Button btnAddToWatchlist, btnAddToWatched, btnAddToCurrentlyWatching, btnAddToFavorite;
    private ImageView movieImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        initViews();
        Intent intent = getIntent();
        if(intent != null) {
            int movieId = intent.getIntExtra(MOVIE_ID_KEY, -1);
            if(movieId != -1) {
                Movie incomingMovie = Utils.getInstance().getMovieById(movieId);
                if(incomingMovie != null) {
                    setData(incomingMovie);

                    handleWatchlist(incomingMovie);
                    handleWatchedMovies(incomingMovie);
                    handleCurrentlyWatchingMovies(incomingMovie);
                    handleFavoriteMovies(incomingMovie);
                }
            }
        }
    }

    private void handleFavoriteMovies(final Movie movie) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(userId).collection("favorites").document(String.valueOf(movie.getId()));

            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        btnAddToFavorite.setEnabled(false);
                    } else {
                        btnAddToFavorite.setEnabled(true);
                        btnAddToFavorite.setOnClickListener(v -> addToFavorites(movie));
                    }
                } else {
                    Log.d("MovieActivity", "Eroare cu: ", task.getException());
                }
            });
        } else {
            Toast.makeText(this, "Trebuie să vă autentificați pentru a adăuga la favorite", Toast.LENGTH_SHORT).show();
        }
    }

    private void addToFavorites(Movie movie) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            Map<String, Object> movieData = new HashMap<>();
            movieData.put("id", movie.getId());
            movieData.put("name", movie.getName());
            movieData.put("director", movie.getDirector());
            movieData.put("genre", movie.getGenre());
            movieData.put("longDesc", movie.getLongDesc());
            movieData.put("shortDesc", movie.getShortDesc());
            movieData.put("imageUrl", movie.getImageUrl());
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userId).collection("favorites").document(String.valueOf(movie.getId()))
                    .set(movieData)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(MovieActivity.this, "Film adăugat la Favorite", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> Toast.makeText(MovieActivity.this, "Eroare la adăugarea filmului la favorite", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Trebuie să vă autentificați pentru a adăuga la favorite", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleCurrentlyWatchingMovies(final Movie movie) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            DocumentReference docRef = db.collection("users").document(userId).collection("currentlyWatching").document(String.valueOf(movie.getId()));
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        btnAddToCurrentlyWatching.setEnabled(false);
                    } else {
                        btnAddToCurrentlyWatching.setEnabled(true);
                        btnAddToCurrentlyWatching.setOnClickListener(v -> addToCurrentlyWatching(movie));
                    }
                } else {
                    Log.d("MovieActivity", "Eroare cu: ", task.getException());
                }
            });
        } else {
            Toast.makeText(this, "Trebuie să vă autentificați pentru a adăuga la vizionare curentă", Toast.LENGTH_SHORT).show();
        }
    }

    private void addToCurrentlyWatching(Movie movie) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            Map<String, Object> movieData = new HashMap<>();
            movieData.put("id", movie.getId());
            movieData.put("name", movie.getName());
            movieData.put("director", movie.getDirector());
            movieData.put("genre", movie.getGenre());
            movieData.put("longDesc", movie.getLongDesc());
            movieData.put("shortDesc", movie.getShortDesc());
            movieData.put("imageUrl", movie.getImageUrl());
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userId).collection("currentlyWatching").document(String.valueOf(movie.getId()))
                    .set(movieData)
                    .addOnSuccessListener(aVoid -> Toast.makeText(MovieActivity.this, "Film adăugat la Vizionare Curentă", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(MovieActivity.this, "Eroare la adăugarea filmului la vizionare curentă", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Trebuie să vă autentificați pentru a adăuga la vizionare curentă", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleWatchlist(final Movie movie) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            DocumentReference docRef = db.collection("users").document(userId).collection("watchlist").document(String.valueOf(movie.getId()));
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        btnAddToWatchlist.setEnabled(false);
                    } else {
                        btnAddToWatchlist.setEnabled(true);
                        btnAddToWatchlist.setOnClickListener(v -> addToWatchlist(movie));
                    }
                } else {
                    Log.d("MovieActivity", "Eroare cu: ", task.getException());
                }
            });
        } else {
            Toast.makeText(this, "Trebuie să vă autentificați pentru a adăuga ca dorit", Toast.LENGTH_SHORT).show();
        }
    }

    private void addToWatchlist(Movie movie) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            Map<String, Object> movieData = new HashMap<>();
            movieData.put("id", movie.getId());
            movieData.put("name", movie.getName());
            movieData.put("director", movie.getDirector());
            movieData.put("genre", movie.getGenre());
            movieData.put("longDesc", movie.getLongDesc());
            movieData.put("shortDesc", movie.getShortDesc());
            movieData.put("imageUrl", movie.getImageUrl());
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userId).collection("watchlist").document(String.valueOf(movie.getId()))
                    .set(movieData)
                    .addOnSuccessListener(aVoid -> Toast.makeText(MovieActivity.this, "Film adăugat ca Dorit", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(MovieActivity.this, "Eroare la adăugarea filmului dorit", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Trebuie să vă autentificați pentru a adăuga ca dorit", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Enable and Disable button,
     * Add the book to Already Read Book ArrayList
     * @param movie
     */
    private void handleWatchedMovies(final Movie movie) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            DocumentReference docRef = db.collection("users").document(userId).collection("watched").document(String.valueOf(movie.getId()));
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        btnAddToWatched.setEnabled(false);
                    } else {
                        btnAddToWatched.setEnabled(true);
                        btnAddToWatched.setOnClickListener(v -> addToWatched(movie));
                    }
                } else {
                    Log.d("MovieActivity", "Eroare cu: ", task.getException());
                }
            });
        } else {
            Toast.makeText(this, "Trebuie să vă autentificați pentru a adăuga la vizionat", Toast.LENGTH_SHORT).show();
        }
    }

    private void addToWatched(Movie movie) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            Map<String, Object> movieData = new HashMap<>();
            movieData.put("id", movie.getId());
            movieData.put("name", movie.getName());
            movieData.put("director", movie.getDirector());
            movieData.put("genre", movie.getGenre());
            movieData.put("longDesc", movie.getLongDesc());
            movieData.put("shortDesc", movie.getShortDesc());
            movieData.put("imageUrl", movie.getImageUrl());
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userId).collection("watched").document(String.valueOf(movie.getId()))
                    .set(movieData)
                    .addOnSuccessListener(aVoid -> Toast.makeText(MovieActivity.this, "Film adăugat la Vizionat", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(MovieActivity.this, "Eroare la adăugarea filmului la vizionat", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Trebuie să vă autentificați pentru a adăuga la vizionat", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData(Movie movie) {
        txtMovieName.setText(movie.getName());
        txtDirector.setText(getString(R.string.movie_director, movie.getDirector()));
        txtGenre.setText(getString(R.string.movie_genre, movie.getGenre()));
        txtDescription.setText(movie.getLongDesc());

        Glide.with(this)
                .asBitmap()
                .load(movie.getImageUrl())
                .into(movieImage);
    }

    private void initViews() {
        txtDirector = findViewById(R.id.txtDirectorName);
        txtMovieName = findViewById(R.id.txtMovieName);
        txtGenre = findViewById(R.id.txtGenre);
        txtDescription = findViewById(R.id.txtDescription);

        btnAddToWatched = findViewById(R.id.btnAddToWatchedList);
        btnAddToCurrentlyWatching = findViewById(R.id.btnAddToCurrentlyWatching);
        btnAddToFavorite = findViewById(R.id.btnAddToFavorite);
        btnAddToWatchlist = findViewById(R.id.btnAddToWatchlist);

        movieImage = findViewById(R.id.imgMovie);
    }

}