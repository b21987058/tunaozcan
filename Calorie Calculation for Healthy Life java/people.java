import java.lang.Math;

public class people {
    private String personID;
    private String name;
    private String gender;
    private Integer weight;
    private Integer height;
    private String dateOfBirth;
    private Integer calorieGained;
    private Integer CalorieBurnedd;
    private Integer calorieStatusChanged;

    public people() {

    }

    public String getPersonID(){
        return personID;
    }
    public void setPersonID(String newPersonID){
        personID = newPersonID;
    }


    public String getName(){
        return name;
    }
    public void setName(String newName){
        name = newName;
    }


    public String getGender(){
        return gender;
    }
    public void setGender(String newGender){
        gender = newGender;
    }


    public Integer getWeight(){
        return weight;
    }
    public void setWeight(Integer newWeight){
        weight = newWeight;
    }


    public Integer getHeight(){
        return height;
    }
    public void setHeight(Integer newHeight){
        height = newHeight;
    }


    public String getDateOfBirth(){
        return dateOfBirth;
    }
    public void setDateOfBirth(String newDateOfBirth){
        dateOfBirth = newDateOfBirth;
    }


    public Integer getCalorieGained() {
        return calorieGained;
    }
    public void setCalorieGained(Integer calorieGained) {
        this.calorieGained += calorieGained;
    }


    public Integer getCalorieBurnedd() {
        return CalorieBurnedd;
    }
    public void setCalorieBurnedd(Integer CalorieBurnedd) {
        this.CalorieBurnedd += CalorieBurnedd;
    }


    public Integer getCalorieStatusChanged() {
        return calorieStatusChanged;
    }
    public void setCalorieStatusChanged(Integer calorieStatusChanged) {
        this.calorieStatusChanged = calorieStatusChanged;
    }


    public people(String personID, String name, String gender, Integer weight, Integer height, String dateOfBirth, Integer calorieGained, Integer CalorieBurnedd, Integer calorieStatusChanged) {
        this.personID = personID;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.dateOfBirth = dateOfBirth;
        this.calorieGained = calorieGained;
        this.CalorieBurnedd = CalorieBurnedd;
        this.calorieStatusChanged = calorieStatusChanged;
    }

    public static long dailyCalorieNeeds(String gender, String dateOfBirth, Integer height, Integer weight){
        if (gender.equals("male")){
            return Math.round(66+(13.75 * weight)+(5 * height)-(6.8 * (2022- Integer.parseInt(dateOfBirth))));
        }
        else {
            return Math.round(665+(9.6 * weight)+(1.7 * height)-(4.7 * (2022 - Integer.parseInt(dateOfBirth))));
        }
    }

    public static Integer result(long calorieNeed, Integer calorieGained, Integer calorieBurnedd ){
        return Math.round(calorieGained - (calorieNeed+calorieBurnedd));
    }

}
