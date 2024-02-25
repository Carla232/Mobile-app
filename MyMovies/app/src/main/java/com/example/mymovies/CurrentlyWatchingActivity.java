package com.example.mymovies;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CurrentlyWatchingActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MovieRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currently_watching);
        recyclerView = findViewById(R.id.moviesRecView);
        adapter = new MovieRecViewAdapter(this, "CurrentlyWatching");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(CurrentlyWatchingActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        loadCurrentlyWatching();
    }

    private void loadCurrentlyWatching() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userId).collection("currentlyWatching")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Movie> currentlyWatching = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Movie movie = document.toObject(Movie.class);
                                currentlyWatching.add(movie);
                            }
                            adapter.setMovies((ArrayList<Movie>) currentlyWatching);
                        } else {
                            Log.d("CurrentlyWatchingActivity", "Eroare la obținerea documentelor: ", task.getException());
                        }
                    });
        } else {
            Log.d("CurrentlyWatchingActivity", "Niciun utilizator autentificat");
        }
    }
}