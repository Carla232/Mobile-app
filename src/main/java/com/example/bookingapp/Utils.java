package com.example.bookingapp;

import java.util.ArrayList;

public class Utils {

    private static Utils instance;

    private ArrayList<Movie> allMovies;
    private ArrayList<Movie> watchedMovies;
    private ArrayList<Movie> wantToWatchMovies;
    private ArrayList<Movie> currentlyWatchingMovies;
    private ArrayList<Movie> favoriteMovies;

    private Utils() {
        if (allMovies == null) {
            allMovies = new ArrayList<>();
            // Inițializarea listei de filme
            initData();
        }
        if (watchedMovies == null) {
            watchedMovies = new ArrayList<>();
        }
        if (wantToWatchMovies == null) {
            wantToWatchMovies = new ArrayList<>();
        }
        if (currentlyWatchingMovies == null) {
            currentlyWatchingMovies = new ArrayList<>();
        }
        if (favoriteMovies == null) {
            favoriteMovies = new ArrayList<>();
        }
    }
    private void initData() {
        //TODO: add initial data
            // Adaugă date inițiale pentru filme
            allMovies.add(new Movie(1, "Inception", "Christopher Nolan", "Sci-Fi", "https://link_to_image.com/inception.jpg", "O călătorie în adâncul subconștientului", "Descriere detaliată a filmului Inception."));
            allMovies.add(new Movie(2, "The Shawshank Redemption", "Frank Darabont", "Drama", "https://link_to_image.com/shawshank.jpg", "Povestea speranței neclintite a unui bărbat", "Descriere detaliată a filmului The Shawshank Redemption."));
            allMovies.add(new Movie(3, "Interstellar", "Christopher Nolan", "Sci-Fi", "https://link_to_image.com/interstellar.jpg", "Călătoria interstelară în căutarea unei noi case", "Descriere detaliată a filmului Interstellar."));

    }

    public static synchronized Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    public ArrayList<Movie> getAllMovies() {
        return allMovies;
    }

    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public ArrayList<Movie> getWantToWatchMovies() {
        return wantToWatchMovies;
    }

    public ArrayList<Movie> getCurrentlyWatchingMovies() {
        return currentlyWatchingMovies;
    }

    public ArrayList<Movie> getFavoriteMovies() {
        return favoriteMovies;
    }

    public Movie getMovieById(int id) {
        for (Movie movie : allMovies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }

    public boolean addToWatched(Movie movie) {
        return watchedMovies.add(movie);
    }

    public boolean addToWantToWatch(Movie movie) {
        return wantToWatchMovies.add(movie);
    }

    public boolean addToCurrentlyWatching(Movie movie) {
        return currentlyWatchingMovies.add(movie);
    }

    public boolean addToFavorite(Movie movie) {
        return favoriteMovies.add(movie);
    }

    public boolean removeFromWatched(Movie movie) {
        return watchedMovies.remove(movie);
    }

    public boolean removeFromWantToWatch(Movie movie) {
        return wantToWatchMovies.remove(movie);
    }

    public boolean removeFromCurrentlyWatching(Movie movie) {
        return currentlyWatchingMovies.remove(movie);
    }

    public boolean removeFromFavorites(Movie movie) {
        return favoriteMovies.remove(movie);
    }
}
