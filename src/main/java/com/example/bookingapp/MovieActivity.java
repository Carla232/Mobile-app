package com.example.bookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity {

    public static final String MOVIE_ID_KEY = "movieId";

    private TextView txtMovieName, txtDirector, txtGenre, txtDescription;
    private Button btnAddToWatchlist, btnAddToWatched, btnAddToCurrentlyWatching, btnaddToFavorite;
    private ImageView movieImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        initViews();
//        String longDescription = "A young woman named Aomame follows a taxi driver’s enigmatic suggestion and begins to notice puzzling discrepancies in the world around her. She has entered, she realizes, a parallel existence, which she calls 1Q84 “Q is for ‘question mark.’ A world that bears a question.” Meanwhile, an aspiring writer named Tengo takes on a suspect ghostwriting project. He becomes so wrapped up with the work and its unusual author that, soon, his previously placid life begins to come unraveled."
//                + "\n" + "As Aomame’s and Tengo’s narratives converge over the course of this single year, we learn of the profound and tangled connections that bind them ever closer: a beautiful, dyslexic teenage girl with a unique vision; a mysterious religious cult that instigated a shoot-out with the metropolitan police; a reclusive, wealthy dowager who runs a shelter for abused women; a hideously ugly private investigator; a mild-mannered yet ruthlessly efficient bodyguard; and a peculiarly insistent television-fee collector."
//                + "\n" + "A love story, a mystery, a fantasy, a novel of self-discovery, a dystopia to rival George Orwell’s 1Q84 is Haruki Murakami’s most ambitious undertaking yet: an instant best seller in his native Japan, and a tremendous feat of imagination from one of our most revered contemporary writers.";
//
//  TOD: Get the data from recycler view in here
//        Book book = new Book(1,"1Q84","Haruki Murakami",1350,"https://images-na.ssl-images-amazon.com/images/I/41FdmYnaNuL._SX322_BO1,204,203,200_.jpg","A work of maddening brilliance",longDescription);


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
        ArrayList<Movie> favoriteMovies = Utils.getInstance().getFavoriteMovies();

        boolean existInFavoriteMovies = false;
        for(Movie m : favoriteMovies){
            if(m.getId() == movie.getId()){
                existInFavoriteMovies = true;
                break;
            }
        }
        if(existInFavoriteMovies){
            btnaddToFavorite.setEnabled(false);
        }else {
            btnaddToFavorite.setOnClickListener(v -> {
                if(Utils.getInstance().addToFavorite(movie)){
                    Toast.makeText(MovieActivity.this, "Movie Added to Favorites", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MovieActivity.this, FavoriteActivity.class);
                        startActivity(intent);
                    }else{
                    Toast.makeText(MovieActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
            });
        }
    }

    private void handleCurrentlyWatchingMovies(final Movie movie) {
        ArrayList<Movie> currentlyWatchingMovies = Utils.getInstance().getCurrentlyWatchingMovies();

        boolean existInCurrentlyWatchingMovies = false;
        for(Movie m : currentlyWatchingMovies){
            if(m.getId() == movie.getId()){
                existInCurrentlyWatchingMovies = true;
                break;
            }
        }
        if(existInCurrentlyWatchingMovies){
            btnAddToCurrentlyWatching.setEnabled(false);
        }else {
            btnAddToCurrentlyWatching.setOnClickListener(v -> {
                if(Utils.getInstance().addToCurrentlyWatching(movie)){
                    Toast.makeText(MovieActivity.this, "Movie Added to Currently Watching", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MovieActivity.this, CurrentlyWatchingActivity.class);
                        startActivity(intent);
                    }else{
                    Toast.makeText(MovieActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
            });
        }
    }

    private void handleWatchlist(final Movie movie) {
        ArrayList<Movie> watchlistMovies = Utils.getInstance().getWantToWatchMovies();

        boolean existInWatchlistMovies = false;
        for(Movie m : watchlistMovies){
            if(m.getId() == movie.getId()){
                existInWatchlistMovies = true;
                break;
            }
        }
        if(existInWatchlistMovies){
            btnAddToWatchlist.setEnabled(false);
        }else {
            btnAddToWatchlist.setOnClickListener(v -> {
                if(Utils.getInstance().addToWantToWatch(movie)){
                    Toast.makeText(MovieActivity.this, "Movie Added to Watchlist", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MovieActivity.this, WantToWatchActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MovieActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * Enable and Disable button,
     * Add the book to Already Read Book ArrayList
     * @param movie
     */
    private void handleWatchedMovies(final Movie movie) {
        ArrayList<Movie> watchedMovies = Utils.getInstance().getWatchedMovies();

        boolean existInWatchedMovies = false;
        for(Movie m : watchedMovies){
            if(m.getId() == movie.getId()){
                existInWatchedMovies = true;
                break;
            }
        }
        if(existInWatchedMovies){
            btnAddToWatched.setEnabled(false);
        }else {
            btnAddToWatched.setOnClickListener(v -> {
                if(Utils.getInstance().addToWatched(movie)){
                    Toast.makeText(MovieActivity.this, "Movie Added to Watched", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MovieActivity.this, AlreadyWatchedMoviesActivity.class);
                        startActivity(intent);
                    }else{
                    Toast.makeText(MovieActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
            });
        }
    }

    private void setData(Movie movie) {
        txtMovieName.setText(movie.getName());
        txtDirector.setText(getString(R.string.movie_director, movie.getDirector()));
        txtGenre.setText(getString(R.string.movie_genre, movie.getGenre()));
        txtDescription.setText(movie.getShortDesc()); // sau movie.getLongDesc(), conform necesității

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
        btnaddToFavorite = findViewById(R.id.btnAddToFavorite);
        btnAddToWatchlist = findViewById(R.id.btnAddToWatchlist);

        movieImage = findViewById(R.id.imgMovie);
    }
}