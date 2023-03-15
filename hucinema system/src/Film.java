public class Film {
    String film = "film";
    String filmName;
    String trailerPath;
    String duration;

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getTrailerPath() {
        return trailerPath;
    }

    public void setTrailerPath(String trailerPath) {
        this.trailerPath = trailerPath;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Film(String film, String filmName, String trailerPath, String duration) {
        this.film = film;
        this.filmName = filmName;
        this.trailerPath = trailerPath;
        this.duration = duration;
    }

    public Film() {
    }
}
