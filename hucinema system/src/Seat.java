public class Seat {
    String seat = "seat";
    String filmName;
    String hallName;
    String row_of_seat;
    String column_of_seat;
    String owner_name = null;
    String price_that_it_has_been_bought;

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
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

    public String getRow_of_seat() {
        return row_of_seat;
    }

    public void setRow_of_seat(String row_of_seat) {
        this.row_of_seat = row_of_seat;
    }

    public String getColumn_of_seat() {
        return column_of_seat;
    }

    public void setColumn_of_seat(String column_of_seat) {
        this.column_of_seat = column_of_seat;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getPrice_that_it_has_been_bought() {
        return price_that_it_has_been_bought;
    }

    public void setPrice_that_it_has_been_bought(String price_that_it_has_been_bought) {
        this.price_that_it_has_been_bought = price_that_it_has_been_bought;
    }

    public Seat(String seat, String filmName, String hallName, String row_of_seat, String column_of_seat, String owner_name, String price_that_it_has_been_bought) {
        this.seat = seat;
        this.filmName = filmName;
        this.hallName = hallName;
        this.row_of_seat = row_of_seat;
        this.column_of_seat = column_of_seat;
        this.owner_name = owner_name;
        this.price_that_it_has_been_bought = price_that_it_has_been_bought;
    }

    public Seat() {
    }
}
