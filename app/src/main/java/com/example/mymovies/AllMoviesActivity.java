package com.example.mymovies;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

public class AllMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_movies);
        MovieRecViewAdapter adapter = new MovieRecViewAdapter(this, "allMovies");
        RecyclerView moviesRecView = findViewById(R.id.moviesRecView);
        moviesRecView.setAdapter(adapter);
        moviesRecView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setMovies(Utils.getInstance().getAllMovies());
    }
}