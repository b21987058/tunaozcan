import java.io.Serializable;
import java.util.Objects;

public class Station implements Serializable {
    static final long serialVersionUID = 55L;

    public Point coordinates;
    public String description;

    public Station(Point coordinates, String description) {
        this.coordinates = coordinates;
        this.description = description;
    }

    public String toString() {
        return this.description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return coordinates.equals(station.coordinates) && description.equals(station.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, description);
    }
}
