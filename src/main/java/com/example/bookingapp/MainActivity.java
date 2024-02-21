package com.example.bookingapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnAllMovies, btnAlreadyWatched, btnWantToWatch, btnCurrentlyWatching, btnFavorite, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        btnAllMovies.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AllMoviesActivity.class);
            startActivity(intent);
        });

        btnAlreadyWatched.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AlreadyWatchedMoviesActivity.class);
            startActivity(intent);
        });

        btnCurrentlyWatching.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CurrentlyWatchingActivity.class);
            startActivity(intent);
        });

        btnWantToWatch.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, WantToWatchActivity.class);
            startActivity(intent);
        });

        btnFavorite.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(intent);
        });

        btnAbout.setOnClickListener(view -> {
            // Implement the logic for About button if needed
        });

        Utils.getInstance();
    }

    private void initViews() {
        btnAllMovies = findViewById(R.id.btnAllMovies);
        btnAlreadyWatched = findViewById(R.id.btnAlreadyWatched);
        btnWantToWatch = findViewById(R.id.btnWatchlist);
        btnCurrentlyWatching = findViewById(R.id.btnCurrentlyWatching);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnAbout = findViewById(R.id.btnAbout);
    }
}