public class Hall {
    String hall = "hall";
    String filmName;
    String hallName;
    String price_per_seat;
    String row;
    String column;

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getPrice_per_seat() {
        return price_per_seat;
    }

    public void setPrice_per_seat(String price_per_seat) {
        this.price_per_seat = price_per_seat;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Hall(String hall, String filmName, String hallName, String price_per_seat, String row, String column) {
        this.hall = hall;
        this.filmName = filmName;
        this.hallName = hallName;
        this.price_per_seat = price_per_seat;
        this.row = row;
        this.column = column;
    }

    public Hall() {
    }
}
