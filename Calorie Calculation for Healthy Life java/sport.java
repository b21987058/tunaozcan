public class sport {
    private String sportID;
    private String nameOfSport;
    private Integer calorieBurned;

    public String getSportID() {
        return sportID;
    }
    public void setSportID(String sportID) {
        this.sportID = sportID;
    }

    public String getNameOfSport() {
        return nameOfSport;
    }
    public void setNameOfSport(String nameOfSport) {
        this.nameOfSport = nameOfSport;
    }

    public Integer getCalorieBurned() {
        return calorieBurned;
    }
    public void setCalorieBurned(Integer calorieBurned) {
        this.calorieBurned = calorieBurned;
    }

    public sport(String sportID, String nameOfSport, Integer calorieBurned) {
        this.sportID = sportID;
        this.nameOfSport = nameOfSport;
        this.calorieBurned = calorieBurned;
    }
    public sport() {

    }
}
