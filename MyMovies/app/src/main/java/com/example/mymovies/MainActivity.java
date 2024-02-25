package com.example.mymovies;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnAllMovies, btnAlreadyWatched, btnWantToWatch, btnCurrentlyWatching, btnFavorite, btnLoginRegister, btnLogout;;
    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        updateButtonsVisibility();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        ImageView imgLogo = findViewById(R.id.imgLogo);
        Glide.with(this)
                .load("https://i0.1616.ro/media/2/2701/33658/20960526/3/filme-disney.jpg")
                .into(imgLogo);
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

        btnLoginRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            updateButtonsVisibility();
            Toast.makeText(MainActivity.this, "Ați fost deconectat", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
        updateButtonsVisibility();
        Utils.getInstance();
    }

    private void initViews() {
        btnAllMovies = findViewById(R.id.btnAllMovies);
        btnAlreadyWatched = findViewById(R.id.btnAlreadyWatched);
        btnWantToWatch = findViewById(R.id.btnWatchlist);
        btnCurrentlyWatching = findViewById(R.id.btnCurrentlyWatching);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnLoginRegister = findViewById(R.id.btnLoginRegister);
        btnLogout = findViewById(R.id.btnLogout);
    }
    private void updateButtonsVisibility() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            btnLogout.setVisibility(View.VISIBLE);
            btnLoginRegister.setVisibility(View.GONE);
        } else {
            btnLogout.setVisibility(View.GONE);
            btnLoginRegister.setVisibility(View.VISIBLE);
        }
    }
}