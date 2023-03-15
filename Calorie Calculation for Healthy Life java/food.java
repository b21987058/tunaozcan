public class food {
    private String foodID;
    private String nameOfFood;
    private Integer calorieCount;



    public String getFoodID() {
        return foodID;
    }
    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }


    public String getNameOfFood() {

        return nameOfFood;
    }
    public void setNameOfFood(String nameOfFood) {

        this.nameOfFood = nameOfFood;
    }



    public Integer getCalorieCount() {

        return calorieCount;
    }
    public void setCalorieCount(Integer calorieCount) {

        this.calorieCount = calorieCount;
    }

    public food(String foodID, String nameOfFood, Integer calorieCount) {
        this.foodID = foodID;
        this.nameOfFood = nameOfFood;
        this.calorieCount = calorieCount;
    }
}
