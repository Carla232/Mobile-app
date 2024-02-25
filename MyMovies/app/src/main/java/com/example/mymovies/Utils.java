package com.example.mymovies;

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
        //TODO: Adaugă date inițiale pentru filme
        allMovies.add(new Movie(1, "Inception", "Christopher Nolan", "Sci-Fi", "https://resizing.flixster.com/-XZAfHZM39UwaGJIFWKAE8fS0ak=/v3/t/assets/p7825626_p_v8_af.jpg", "O călătorie în adâncul subconștientului", "Dominic Cobb este un hoț priceput în arta 'extracției', furând secrete valoroase din adâncul subconștientului în timpul viselor, când mintea este cea mai vulnerabilă. Ultima sa misiune, care îi oferă șansa de a-și recăpăta viața veche, constă într-o sarcină aparent imposibilă: 'inceptia', adică implantarea unei idei în mintea unei persoane. Însă, pe măsură ce evenimentele progresează, Cobb și echipa sa trebuie să facă față demonilor personali și să deosebească între realitate și vis."));
        allMovies.add(new Movie(2, "The Shawshank Redemption", "Frank Darabont", "Drama", "data:image/jpeg;base64,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", "Povestea speranței neclintite a unui bărbat", "Andy Dufresne, un bancher de succes, este condamnat pe nedrept pentru uciderea soției sale și a iubitului acesteia și este trimis la închisoarea Shawshank. În ciuda asprimei vieții din închisoare, Andy păstrează speranța și își folosește inteligența pentru a îmbunătăți condițiile de viață ale celor din jur. Prietenia sa cu Red, un deținut veteran, și lupta neîncetată pentru dreptate și libertate formează coloana vertebrală a acestei povești emoționante despre reziliență și puterea spiritului uman."));
        allMovies.add(new Movie(3, "Interstellar", "Christopher Nolan", "Sci-Fi", "https://m.media-amazon.com/images/I/A1JVqNMI7UL._AC_UF894,1000_QL80_.jpg", "Călătoria interstelară în căutarea unei noi case", "Într-un viitor apropiat, Pământul este devastat de boli și secetă, iar umanitatea se confruntă cu extincția. Cooper, un pilot și inginer, se alătură unei misiuni disperate NASA de a călători dincolo de galaxia noastră pentru a descoperi dacă omenirea are un viitor pe vreuna dintre planetele din jurul unei găuri de vierme. Confruntat cu decizii imposibile și pierderi personale, Cooper trebuie să găsească o cale de a salva proiectul misiunii și de a se întoarce acasă la familia sa, explorând teme profunde precum dragostea, sacrificiul și locul umanității în univers."));
        allMovies.add(new Movie(4, "The Grand Budapest Hotel", "Wes Anderson", "Comedie", "https://i.scdn.co/image/ab67616d0000b273afda2a51e7bb55197fdeb4c0", "Aventuri excentrice într-un hotel european", "Aventurile unui legendar concierge la un faimos hotel european între cele două războaie mondiale și prietenia lui cu un tânăr angajat, care devine protejatul său. Filmul explorează furtul și recuperarea unei picturi renascentiste inestimabile, lupta pentru o imensă avere a familiei, toate pe fundalul bruscilor schimbări ce afectează continentul."));
        allMovies.add(new Movie(5, "Get Out", "Jordan Peele", "Horror", "https://musicart.xboxlive.com/7/ea0e3f00-0000-0000-0000-000000000002/504/image.jpg?w=1920&h=1080", "Tensiune și teroare într-o vizită familială", "Când un tânăr afro-american vizitează familia iubitei sale albe, descoperă un secret întunecat care îl pune într-un pericol mortal. 'Get Out' este o critică socială ascuțită îmbrăcată în hainele unui film de groază, explorând teme profunde precum rasismul, privilegiul și alienarea, în timp ce menține tensiunea și teroarea la cote maxime."));
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
