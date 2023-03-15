import java.net.Inet4Address;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.io.*;
import java.lang.Math;

public class Main {
    public static void main(String args[]) throws Exception{
        File file = new File("people.txt");
        Scanner people_file = new Scanner(file);
        File file2 = new File("food.txt");
        Scanner food_file = new Scanner(file2);
        File file3 = new File("sport.txt");
        Scanner sports_file = new Scanner(file3);
        File file4 = new File("command.txt");
        Scanner command_file = new Scanner(file4);
        BufferedWriter output = new BufferedWriter(new FileWriter("monitoring.txt"));


        ArrayList<people> people_list =new ArrayList<>();
        String[] people_arr = null ;



        int ct_people =1;
        while (people_file.hasNextLine() && ct_people<100) {                 //getting input from people file, selecting each line and add it as an element of a multidimensional array list,
            //System.out.println(people_file.nextLine());                //then each line will convert to a people object
            people_arr = people_file.nextLine().split("\t");
            people nextPeople = new people(people_arr[0],people_arr[1],people_arr[2],Integer.parseInt(people_arr[3]),Integer.parseInt(people_arr[4]),people_arr[5],0,0,0);
            people_list.add(nextPeople);
            /*for (people str : people_list) //while ın disinda calisiyor
            {
                System.out.println(str.getName());
            }*/
            ct_people++;
        }



        ArrayList<food> food_list =new ArrayList<>();
        String[] food_arr = null ;

        int ct_food =1;
        while (food_file.hasNextLine() && ct_food<100) {
            //System.out.println(food_file.nextLine());
            food_arr = food_file.nextLine().split("\t");
            food nextFood = new food(food_arr[0],food_arr[1],Integer.parseInt(food_arr[2]));
            food_list.add(nextFood);
            /*for (food str : food_list) //while ın disinda calisiyor
            {
                System.out.println(str.getNameOfFood());
            }*/
            ct_food++;
        }



        ArrayList<sport> sport_list =new ArrayList<>();
        String[] sport_arr = null ;

        int ct_sport =1;
        while (sports_file.hasNextLine() && ct_sport<100) {
            //System.out.println(sports_file.nextLine());
            sport_arr = sports_file.nextLine().split("\t");
            sport nextSport = new sport(sport_arr[0],sport_arr[1],Integer.parseInt(sport_arr[2]));
            sport_list.add(nextSport);
            /*for (sport str : sport_list) //while ın disinda calisiyor
        {
            System.out.println(str.getNameOfSport());
        }*/
            ct_sport++;
        }

        String[] command_arr = null ;

        while (command_file.hasNextLine()){
            //System.out.println(command_file.nextLine());
            command_arr = command_file.nextLine().split("\t");
            //System.out.println(Arrays.toString(command_arr));
            if(command_arr[0].startsWith("print(")){ // bu kısımda ID'si verilen kisinin current calorie status'u printlenecek (isim yas
                //System.out.println("print yazdırıldı");
                command_arr[0] = command_arr[0].replace("print(",""); //ID yi rahatca okuyabilmek icin
                command_arr[0] = command_arr[0].replace(")","");
                for(people ppl : people_list){
                    if(ppl.getPersonID().equals(command_arr[0])){
                        //System.out.println(ppl.getName() + "\t" + (2022 - Integer.parseInt(ppl.getDateOfBirth())) + "\t" +  people.dailyCalorieNeeds(ppl.getGender(), ppl.getDateOfBirth(), ppl.getHeight(), ppl.getWeight()) + "\t" + ppl.getCalorieGained() + "\t" + ppl.getCalorieBurnedd() + "\t" + people.result(people.dailyCalorieNeeds(ppl.getGender(), ppl.getDateOfBirth(), ppl.getHeight(), ppl.getWeight()),ppl.getCalorieGained(),ppl.getCalorieBurnedd()) + "kcal"+"\n"+ "***************" + "\n");
                        output.write(ppl.getName() + "\t" + (2022 - Integer.parseInt(ppl.getDateOfBirth())) + "\t" +  people.dailyCalorieNeeds(ppl.getGender(), ppl.getDateOfBirth(), ppl.getHeight(), ppl.getWeight()) + "kcal" + "\t" + ppl.getCalorieGained()+ "kcal" + "\t" + ppl.getCalorieBurnedd()+ "kcal" + "\t" + people.result(people.dailyCalorieNeeds(ppl.getGender(), ppl.getDateOfBirth(), ppl.getHeight(), ppl.getWeight()),ppl.getCalorieGained(),ppl.getCalorieBurnedd()) + "kcal"+"\n"+ "***************" + "\n");
                    }
                }


            }
            else if(command_arr[0].equals("printWarn")){ // bu kisimda dailyCalorieNeeds'inden fazla yiyenler printlencek (calorie_taken-burned > dailyCalorieNeeds)
                //System.out.println("printWarn yazdırıldı");
                int ct_s =0;
                for(people ppl2 : people_list){
                    if(ppl2.getCalorieStatusChanged()==1 && (ppl2.getCalorieGained() - ppl2.getCalorieBurnedd())> people.dailyCalorieNeeds(ppl2.getGender(), ppl2.getDateOfBirth(), ppl2.getHeight(), ppl2.getWeight()) ){
                        ct_s++;
                        //System.out.println(ppl2.getName() + "\t" + (2022 - Integer.parseInt(ppl2.getDateOfBirth())) + "\t" +  people.dailyCalorieNeeds(ppl2.getGender(), ppl2.getDateOfBirth(), ppl2.getHeight(), ppl2.getWeight()) + "\t" + ppl2.getCalorieGained() + "\t" + ppl2.getCalorieBurnedd() + "\t" + "+"+people.result(people.dailyCalorieNeeds(ppl2.getGender(), ppl2.getDateOfBirth(), ppl2.getHeight(), ppl2.getWeight()),ppl2.getCalorieGained(),ppl2.getCalorieBurnedd()) + "kcal"+"\n"+ "***************" + "\n");
                        output.write(ppl2.getName() + "\t" + (2022 - Integer.parseInt(ppl2.getDateOfBirth())) + "\t" +  people.dailyCalorieNeeds(ppl2.getGender(), ppl2.getDateOfBirth(), ppl2.getHeight(), ppl2.getWeight())+ "kcal" + "\t" + ppl2.getCalorieGained()+ "kcal" + "\t" + ppl2.getCalorieBurnedd()+ "kcal" + "\t" + "+"+people.result(people.dailyCalorieNeeds(ppl2.getGender(), ppl2.getDateOfBirth(), ppl2.getHeight(), ppl2.getWeight()),ppl2.getCalorieGained(),ppl2.getCalorieBurnedd()) + "kcal"+"\n");
                    }
                    //break;
                }


                if(ct_s==0){
                    output.write("there is no such person"+"\n"+ "***************" + "\n");
                }
                else {
                    output.write("***************" + "\n");
                }

            }
            else if(command_arr[0].equals("printList")){ //bu kisimda su ana kadarki command.txt de verilen herkesin calorie statuslari printlencek
                //System.out.println("printList yazdırıldı");
                for(people ppl3 : people_list){
                    if(ppl3.getCalorieStatusChanged()==1){
                        //System.out.println(ppl3.getName() + "\t" + (2022 - Integer.parseInt(ppl3.getDateOfBirth())) + "\t" +  people.dailyCalorieNeeds(ppl3.getGender(), ppl3.getDateOfBirth(), ppl3.getHeight(), ppl3.getWeight())+ "kcal" + "\t" + ppl3.getCalorieGained()+ "kcal" + "\t" + ppl3.getCalorieBurnedd()+ "kcal" + "\t" + people.result(people.dailyCalorieNeeds(ppl3.getGender(), ppl3.getDateOfBirth(), ppl3.getHeight(), ppl3.getWeight()),ppl3.getCalorieGained(),ppl3.getCalorieBurnedd()) + "kcal"+"\n");
                        output.write(ppl3.getName() + "\t" + (2022 - Integer.parseInt(ppl3.getDateOfBirth())) + "\t" +  people.dailyCalorieNeeds(ppl3.getGender(), ppl3.getDateOfBirth(), ppl3.getHeight(), ppl3.getWeight())+ "kcal" + "\t" + ppl3.getCalorieGained()+ "kcal" + "\t" + ppl3.getCalorieBurnedd()+ "kcal" + "\t" + people.result(people.dailyCalorieNeeds(ppl3.getGender(), ppl3.getDateOfBirth(), ppl3.getHeight(), ppl3.getWeight()),ppl3.getCalorieGained(),ppl3.getCalorieBurnedd()) + "kcal"+"\n");
                    }
                }
                //System.out.println("***************" + "\n");
                output.write("***************" + "\n");
            }
            else if (command_arr[0].startsWith("1"))   { // bu kisimda da foodID ile basliyorsa kac kcal aldiği printlencek, sportID ile basliyorsa da kac kcal burnledigi printlencek + bu bilgiler depolancak

                //System.out.println(command_arr[1]+"yemek veya spor");
                if (command_arr[1].startsWith("20")){ //spor yapıp calorie burnlendiyse
                    //people_list.get(people_list.indexOf(command_arr[0])).setCalorieBurnedd(sport_list.get(sport_list.indexOf(command_arr[1])).getCalorieBurned() * (Integer.parseInt(command_arr[2]) / 60  ));
                    for (people elmt : people_list){
                        if(elmt.getPersonID().equals(command_arr[0])){
                            for (sport elmt2 : sport_list) {
                                if(elmt2.getSportID().equals(command_arr[1])) {
                                    elmt.setCalorieBurnedd(elmt2.getCalorieBurned()* (Integer.parseInt(command_arr[2]) / 60));
                                    elmt.setCalorieStatusChanged(1);

                                    output.write(command_arr[0] + "\t" + "has" + "\t" + "burned" + "\t" + elmt2.getCalorieBurned() * (Integer.parseInt(command_arr[2]) / 60) + "kcal" + "\t" + "thanks to " + elmt2.getNameOfSport() + "\n" + "***************" + "\n");
                                    //System.out.println(command_arr[0] + "\t" + "has" + "\t" + "burned" + "\t" + elmt2.getCalorieBurned() * (Integer.parseInt(command_arr[2]) / 60) + "kcal" + "\t" + "thanks to " + elmt2.getNameOfSport() + "\n" + "***************" + "\n");
                                    break;
                                }
                            }
                        }
                    }
                }
                else if(command_arr[1].startsWith("1")){ //yemek yiyip calorie gainliyorsa
                    for (people element : people_list){
                        if(element.getPersonID().equals(command_arr[0])){
                            for (food element2 : food_list){
                                if(element2.getFoodID().equals(command_arr[1])){
                                    element.setCalorieGained(element2.getCalorieCount()*Integer.parseInt(command_arr[2]));
                                    element.setCalorieStatusChanged(1);

                                    //System.out.println(command_arr[0] + "\t" + "has" + "\t" +"taken" + "\t" + element2.getCalorieCount() * Integer.parseInt(command_arr[2]) + "kcal" + "\t" + "from" + "\t" + element2.getNameOfFood() + "\n" + "***************" + "\n" );
                                    output.write(command_arr[0] + "\t" + "has" + "\t" +"taken" + "\t" + element2.getCalorieCount() * Integer.parseInt(command_arr[2]) + "kcal" + "\t" + "from" + "\t" + element2.getNameOfFood() + "\n" + "***************" + "\n" );

                                }
                            }
                        }
                    }
                }
            }
        }
        output.close();
    }

}
