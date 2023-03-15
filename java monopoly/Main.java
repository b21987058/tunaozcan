import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.objects.annotations.Property;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;
import java.util.List;
import java.io.FileWriter;
import java.io.BufferedWriter;


public class Main {


    public static void main(String[] args) throws Exception{


        Player Player1 = new Player();
        Player Player2 = new Player();
        Banker banker = new Banker();
        Player1.setName("Player 1");
        Player2.setName("Player 2");
        Player1.setMoney(15000);//each player starts the game with 15 000 Turkish Liras
        Player2.setMoney(15000);
        banker.setName("Banker");
        banker.setMoney(100000);// the banker starts the game with 100 000 Turkish Liras

        PropertyJsonReader jr=new PropertyJsonReader();
        for (int i=0; i<jr.Land_list.size(); i++) {
            Land current = jr.Land_list.get(i);
            System.out.println(current.getId() +" " + current.getName()+ " " + current.getCost()+ " "+ current.getOwned());
        }
        System.out.println();
        for (int i=0; i<jr.Comp_list.size(); i++) {
            Company current = jr.Comp_list.get(i);
            System.out.println(current.getId() +" " + current.getName()+ " " + current.getCost());
        }
        System.out.println();
        for (int i=0; i<jr.Rail_list.size(); i++) {
            Railroads current = jr.Rail_list.get(i);
            System.out.println(current.getId() +" " + current.getName()+ " " + current.getCost());
        }
        System.out.println();
        System.out.println("1 GO"+"\n"+"3 Community Chest"+"\n"+"5 Income Tax"+"\n"+"8 Chance"+"\n"+"11 Jail"+"\n"+"18 Community Chest"+"\n"+"21 Free Parking"+"\n"+"23 Chance"+"\n"+"31 Go to Jail"+"\n"+"34 Community Chest"+"\n"+"37 Chance"+"\n"+"39 Super Tax"+"\n");
        System.out.println();
        ListJsonReader ljr = new ListJsonReader();
        for (int i=0; i<ljr.Chance_list.size(); i++) {
            Chance current = ljr.Chance_list.get(i);
            System.out.println(current.getItem());
        }
        System.out.println();
        for (int i=0; i<ljr.CommChest_list.size(); i++) {
            CommunityChest current = ljr.CommChest_list.get(i);
            System.out.println(current.getItem());
        }
        System.out.println();

        int temp_chance = 0; //sans kartlarının hangisinde sıra oldugu counterı
        int temp_commChest = 0; //kamu fonu kartlarının hangisinde sıra oldugu counterı

        int temp_position1 = 1; //the initial position of the Player 1 , TEMP POSITION CAN NOT BE HIGHER THAN 40 (41th square equals GO square which is the 1st)
        int temp_position2 = 1;//the initial position of the Player 2

        int temp_rent = 0;

        ArrayList<Object> p1_prop = new ArrayList<Object>();
        ArrayList<Object> p2_prop = new ArrayList<Object>();
        String  property_list = "";
        String property_list2 = "";




        File file = new File(args[0]);
        Scanner command_file = new Scanner(file);
        BufferedWriter output = new BufferedWriter(new FileWriter("output.txt"));
        System.out.println();
        String[] command_arr = null;

        while (command_file.hasNextLine()){ //getting input from command file
            //System.out.println(command_file.nextLine());
            command_arr = command_file.nextLine().split(";");
            //System.out.println(Arrays.toString(command_arr));
            if (command_arr[0].equals("Player 1")){ //The turn of Player 1
                temp_position1 = temp_position1 + Integer.parseInt(command_arr[1]);//dice
                if (temp_position1 > 40){
                    temp_position1 = temp_position1 -40;
                    Player1.setMoney(Player1.getMoney()+200); //GO ' dan gecerse 200 TL alır kasadan
                    banker.setMoney(banker.getMoney()-200);
                }
                //System.out.println(temp_position1+"****");
                if(temp_position1== 2|| temp_position1==4 || temp_position1==7 || temp_position1==9 || temp_position1==10 || temp_position1==12 || temp_position1==14 ||temp_position1==17||temp_position1==19||temp_position1==20||temp_position1==22||temp_position1==24||temp_position1==27||temp_position1==28||temp_position1==30||temp_position1==32||temp_position1==33||temp_position1==35||temp_position1==38||temp_position1==40){
                    //checking if the position is a Land property
                    for(Land lnd: jr.Land_list){
                        if(lnd.getId().equals(temp_position1)){
                            if(lnd.getOwned().equals(0)){ //player 1 in geldigi yerdeki Land bos oldugunda yani satın alacagı zaman
                                lnd.setOwned(1); //satın alımın gerceklesmesi (tapu verilmesi gibi)
                                Player1.setMoney(Player1.getMoney()- lnd.getCost()); //ödeme yapılması
                                banker.setMoney(banker.getMoney()+ lnd.getCost()); //paranın kasaya girmesi
                                if(Player1.getMoney()<0){
                                    //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                    output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                }
                                else {
                                    p1_prop.add(lnd.getName());
                                    //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + Player1.getName() + " bought " + lnd.getName());
                                    output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + Player1.getName() + " bought " + lnd.getName()+"\n");
                                }
                            }
                            else{
                                //KİRA
                                if(lnd.getCost()<2001){
                                    temp_rent= lnd.getCost()*40/100;
                                    Player1.setMoney(Player1.getMoney()-temp_rent);
                                    if(Player1.getMoney()<0){
                                       // System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                    }
                                    else {
                                        Player2.setMoney(Player2.getMoney() + temp_rent);
                                        //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + lnd.getName());
                                        output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + lnd.getName()+"\n");
                                    }
                                }
                                else if(lnd.getCost()>2000 && lnd.getCost()<3001){
                                    temp_rent= lnd.getCost()*30/100;
                                    Player1.setMoney(Player1.getMoney()-temp_rent);
                                    if(Player1.getMoney()<0){
                                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                    }
                                    else {
                                        Player2.setMoney(Player2.getMoney() + temp_rent);
                                        //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + lnd.getName());
                                        output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + lnd.getName()+"\n");
                                    }
                                }
                                else if(lnd.getCost()>3000 && lnd.getCost()<4001){
                                    temp_rent= lnd.getCost()*35/100;
                                    Player1.setMoney(Player1.getMoney()-temp_rent);
                                    if(Player1.getMoney()<0){
                                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                    }
                                    else {
                                        Player2.setMoney(Player2.getMoney() + temp_rent);
                                        //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + lnd.getName());
                                        output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + lnd.getName()+"\n");
                                    }
                                }
                            }
                        }
                    }
                }
                else if(temp_position1==13 ||temp_position1==29){ //checking if the new position is a Company property
                    for(Company cmp: jr.Comp_list){
                        if(cmp.getId().equals(temp_position1)){
                            if(cmp.getOwned().equals(0)){ //player 1 in geldigi yerdeki Company bos oldugunda yani satın alacagı zaman
                                cmp.setOwned(1); //satın alım gerceklesmesi
                                Player1.setMoney(Player1.getMoney()- cmp.getCost()); //paranın ödenmesi
                                if(Player1.getMoney()<0){
                                    //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                    output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                }
                                else {
                                    p1_prop.add(cmp.getName());
                                    banker.setMoney(banker.getMoney() + cmp.getCost());
                                    //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + Player1.getName() + " bought " + cmp.getName());
                                    output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + Player1.getName() + " bought " + cmp.getName()+"\n");
                                }

                            }
                            else{
                                //KİRA
                                temp_rent= Integer.parseInt(command_arr[1]) * 4; //the rent is 4 times the dice at company properties
                                Player1.setMoney(Player1.getMoney()-temp_rent);
                                if(Player1.getMoney()<0){
                                    //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                    output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                }
                                else {
                                    Player2.setMoney(Player2.getMoney() + temp_rent);
                                    //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + cmp.getName());
                                    output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + cmp.getName()+"\n");
                                }
                            }
                        }
                    }
                }
                else if(temp_position1== 6||temp_position1==16||temp_position1==26||temp_position1==36){ //checking if the new position is a Railroad property
                    for(Railroads rrd: jr.Rail_list){
                        if(rrd.getId().equals(temp_position1)){
                            if(rrd.getOwned().equals(0)){ //player 1 in geldigi yerdeki Railroad bos oldugunda yani satın alacagı zaman
                                rrd.setOwned(1);
                                Player1.setMoney(Player1.getMoney()-rrd.getCost()); //paranın ödenmesi
                                if(Player1.getMoney()<0){
                                    //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                    output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                }
                                else {
                                    p1_prop.add(rrd.getName());
                                    banker.setMoney(banker.getMoney() + rrd.getCost());
                                    //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + Player1.getName() + " bought " + rrd.getName());
                                    output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + Player1.getName() + " bought " + rrd.getName()+"\n");
                                }
                            }
                            else{
                                //KİRA
                                int rrd_ct=0; // the amount of railroads owned
                                for(Railroads rrd2: jr.Rail_list){

                                    if(rrd2.getOwned()==1){
                                        rrd_ct++;
                                    }
                                }
                                temp_rent= rrd_ct * 25;
                                Player1.setMoney(Player1.getMoney()-temp_rent);
                                if(Player1.getMoney()<0){
                                    //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                    output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                }
                                else {
                                    Player2.setMoney(Player2.getMoney() + temp_rent);
                                    //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + rrd.getName());
                                    output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + rrd.getName()+"\n");
                                }
                            }
                        }
                    }
                }

                else if (temp_position1==5||temp_position1==39){ //TAX
                    Player1.setMoney(Player1.getMoney()-100);
                    if(Player1.getMoney()<0){
                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                    }
                    else {
                        banker.setMoney(banker.getMoney() + 100);
                        //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + Player1.getName() + "\t" + "Player 1 paid Tax");
                        output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + Player1.getName() + "\t" + "Player 1 paid Tax"+"\n");
                    }
                }
                else if(temp_position1==8||temp_position1==23||temp_position1==37){ //CHANCE
                    if(temp_chance==0){ //Advance to Go (Collect $200)
                        temp_position1 = 1;
                        Player1.setMoney(Player1.getMoney()+200);
                        String draw = "Advance to Go (Collect $200)";
                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player1" +" draw "+ draw);
                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player1" +" draw "+ draw+"\n");
                    }
                    else if(temp_chance==1){ //Advance to Leicester Square
                        temp_position1=27;
                        for (Land lnd: jr.Land_list){
                            if(lnd.getOwned().equals(0)){ //SAHİBİ YOKSA ALIR
                                lnd.setOwned(1);
                                Player1.setMoney(Player1.getMoney()- lnd.getCost());
                                if(Player1.getMoney()<0){
                                   // System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                    output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                }
                                else {
                                    p1_prop.add(lnd.getName());
                                    banker.setMoney(banker.getMoney() + lnd.getCost());
                                    //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 draw Advance to Leicester Square Player 1 bought Leicester Square");
                                    output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 draw Advance to Leicester Square Player 1 bought Leicester Square"+"\n" );
                                }
                            }
                            else{
                                //KİRA
                                if(lnd.getCost()<2001){
                                    temp_rent= lnd.getCost()*40/100;
                                    Player1.setMoney(Player1.getMoney()-temp_rent);
                                    if(Player1.getMoney()<0){
                                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                    }
                                    else {
                                        Player2.setMoney(Player2.getMoney() + temp_rent);
                                        //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + lnd.getName());
                                        output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + lnd.getName()+"\n");
                                    }
                                }
                                else if(lnd.getCost()>2000 && lnd.getCost()<3001){
                                    temp_rent= lnd.getCost()*30/100;
                                    Player1.setMoney(Player1.getMoney()-temp_rent);
                                    if(Player1.getMoney()<0){
                                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                    }
                                    else {
                                        Player2.setMoney(Player2.getMoney() + temp_rent);
                                        //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + lnd.getName());
                                        output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + lnd.getName()+"\n");
                                    }
                                }
                                else if(lnd.getCost()>3000 && lnd.getCost()<4001){
                                    temp_rent= lnd.getCost()*35/100;
                                    Player1.setMoney(Player1.getMoney()-temp_rent);
                                    if(Player1.getMoney()<0){
                                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                    }
                                    else {
                                        Player2.setMoney(Player2.getMoney() + temp_rent);
                                        //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + lnd.getName());
                                        output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + lnd.getName()+"\n");
                                    }
                                }
                            }
                        }
                    }
                    else if(temp_chance==2){ //Go back 3 spaces
                        if(temp_position1==8){ //goes to 5: tax square
                            temp_position1=5;
                            Player1.setMoney(Player1.getMoney()-100);
                            if(Player1.getMoney()<0){
                                //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                            }
                            else {
                                banker.setMoney(banker.getMoney() + 100);
                               // System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 draw Go back 3 spaces Player 1 paid Tax");
                                output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 draw Go back 3 spaces Player 1 paid Tax"+"\n");
                            }
                        }
                        else if(temp_position1==23){ //goes to 20: Vine Street
                            temp_position1=20;
                            for(Land lnd: jr.Land_list){
                                if(lnd.getOwned().equals(0)){ //SAHİBİ YOKSA ALIR
                                    lnd.setOwned(1);
                                    Player1.setMoney(Player1.getMoney()- lnd.getCost());
                                    if(Player1.getMoney()<0){
                                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                    }
                                    else {
                                        p1_prop.add(lnd.getName());
                                        banker.setMoney(banker.getMoney() + lnd.getCost());
                                        //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 draw Go back 3 spaces Player 1 bought Vine Street");
                                        output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 draw Go back 3 spaces Player 1 bought Vine Street"+"\n");
                                    }
                                }
                                else{
                                    //KİRA
                                    if(lnd.getCost()<2001){
                                        temp_rent= lnd.getCost()*40/100;
                                        Player1.setMoney(Player1.getMoney()-temp_rent);
                                        if(Player1.getMoney()<0){
                                            //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                            output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                        }
                                        else {
                                            Player2.setMoney(Player2.getMoney() + temp_rent);
                                            //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + lnd.getName());
                                            output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + lnd.getName()+"\n");
                                        }
                                    }
                                    else if(lnd.getCost()>2000 && lnd.getCost()<3001){
                                        temp_rent= lnd.getCost()*30/100;
                                        Player1.setMoney(Player1.getMoney()-temp_rent);
                                        if(Player1.getMoney()<0){
                                            //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                            output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                        }
                                        else {
                                            Player2.setMoney(Player2.getMoney() + temp_rent);
                                            //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + lnd.getName());
                                            output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + lnd.getName()+"\n");
                                        }
                                    }
                                    else if(lnd.getCost()>3000 && lnd.getCost()<4001){
                                        temp_rent= lnd.getCost()*35/100;
                                        Player1.setMoney(Player1.getMoney()-temp_rent);
                                        if(Player1.getMoney()<0){
                                            //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                            output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                        }
                                        else {
                                            Player2.setMoney(Player2.getMoney() + temp_rent);
                                            //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + lnd.getName());
                                            output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 paid rent for" + lnd.getName()+"\n");
                                        }
                                    }
                                }
                            }
                        }
                        else if(temp_position1==37){
                            temp_position1=34;
                            //COMMUNITY CHEST
                            if(temp_commChest==0){ //Advance to Go (Collect $200)
                                temp_position1 = 1;
                                Player1.setMoney(Player1.getMoney()+200);
                                //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Advance to Go (Collect $200)");
                                output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Advance to Go (Collect $200)"+"\n");
                            }
                            else if(temp_commChest==1){ //Bank error in your favor - collect $75
                                Player1.setMoney(Player1.getMoney()+75);
                                banker.setMoney(banker.getMoney()-75);
                                //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Bank error in your favor - collect $75");
                                output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Bank error in your favor - collect $75"+"\n");
                            }
                            else if (temp_commChest==2){ //Doctor's fees - Pay $50
                                Player1.setMoney(Player1.getMoney()-50);
                                if(Player1.getMoney()<0){
                                    //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                    output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                }
                                else {
                                    banker.setMoney(banker.getMoney() + 50);
                                    //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 draw Doctor's fees - Pay $50");
                                    output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 draw Doctor's fees - Pay $50"+"\n");
                                }
                            }
                            else if(temp_commChest==3){ //It is your birthday Collect $10 from each player
                                Player1.setMoney(Player1.getMoney()+10);
                                Player2.setMoney(Player2.getMoney()-10);
                                //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw It is your birthday Collect $10 from each player");
                                output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw It is your birthday Collect $10 from each player"+"\n");
                                if(Player2.getMoney()<0){
                                    //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                    output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                }

                            }
                            else if(temp_commChest==4){ //Grand Opera Night - collect $50 from every player for opening night seats
                                Player1.setMoney(Player1.getMoney()+50);
                                Player2.setMoney(Player2.getMoney()-50);
                                //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Grand Opera Night - collect $50 from every player for opening night seats");
                                output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Grand Opera Night - collect $50 from every player for opening night seats"+"\n");
                                if(Player2.getMoney()<0){
                                    //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                    output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                }
                            }
                            else if(temp_commChest==5){ //Income Tax refund - collect $20
                                Player1.setMoney(Player1.getMoney()+20);
                                banker.setMoney(banker.getMoney()-20);
                                //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Income Tax refund - collect $20");
                                output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Income Tax refund - collect $20"+"\n");
                            }
                            else if(temp_commChest==6){ //Life Insurance Matures - collect $100
                                Player1.setMoney(Player1.getMoney()+100);
                                banker.setMoney(banker.getMoney()-100);
                                //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Life Insurance Matures - collect $100");
                                output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Life Insurance Matures - collect $100"+"\n");
                            }
                            else if(temp_commChest==7){ //Pay Hospital Fees of $100
                                Player1.setMoney(Player1.getMoney()-100);
                                if(Player1.getMoney()<0){
                                    //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                    output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                }
                                else {
                                    banker.setMoney(banker.getMoney() + 100);
                                    //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 draw Pay Hospital Fees of $100");
                                    output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 draw Pay Hospital Fees of $100"+"\n");
                                }
                            }
                            else if (temp_commChest==8){ //Pay School Fees of $50
                                Player1.setMoney(Player1.getMoney()-50);
                                if(Player1.getMoney()<0){
                                    //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                    output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                }
                                else {
                                    banker.setMoney(banker.getMoney() + 50);
                                    //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 draw Pay School Fees of $50");
                                    output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 draw Pay School Fees of $50"+"\n");
                                }
                            }
                            else if (temp_commChest==9){ //You inherit $100
                                Player1.setMoney(Player1.getMoney()+100);
                                banker.setMoney(banker.getMoney()-100);
                                //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw You inherit $100");
                                output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw You inherit $100"+"\n");
                            }
                            else if (temp_commChest==10){ //From sale of stock you get $50
                                Player1.setMoney(Player1.getMoney()+50);
                                banker.setMoney(banker.getMoney()-50);
                                //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw From sale of stock you get $50");
                                output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw From sale of stock you get $50"+"\n");
                            }
                            temp_commChest++;

                        }
                    }
                    else if (temp_chance==3){ //Pay poor tax of $15
                        Player1.setMoney(Player1.getMoney()-15);
                        if(Player1.getMoney()<0){
                            //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                            output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                        }
                        else {
                            banker.setMoney(banker.getMoney() + 15);
                            //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 draw Pay poor tax of $15 Player 1 paid Tax");
                            output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 draw Pay poor tax of $15 Player 1 paid Tax"+"\n");
                        }
                    }
                    else if(temp_chance==4){ //Your building loan matures - collect $150
                        Player1.setMoney((Player1.getMoney()+150));
                        banker.setMoney(banker.getMoney()-150);
                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Your building loan matures - collect $150");
                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Your building loan matures - collect $150"+"\n");
                    }
                    else if(temp_chance==5){ //You have won a crossword competition - collect $100
                        Player1.setMoney(Player1.getMoney()+100);
                        banker.setMoney(banker.getMoney()-100);
                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw You have won a crossword competition - collect $100");
                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw You have won a crossword competition - collect $100"+"\n");
                    }
                    temp_chance++;
                    if(temp_chance>5){
                        temp_chance=0;
                    }
                }
                else if (temp_position1==3||temp_position1==18||temp_position1==34){ //COMMUNITY CHEST
                    if(temp_commChest==0){ //Advance to Go (Collect $200)
                        temp_position1 = 1;
                        Player1.setMoney(Player1.getMoney()+200);
                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Advance to Go (Collect $200)");
                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Advance to Go (Collect $200)"+"\n");
                    }
                    else if(temp_commChest==1){ //Bank error in your favor - collect $75
                        Player1.setMoney(Player1.getMoney()+75);
                        banker.setMoney(banker.getMoney()-75);
                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Bank error in your favor - collect $75");
                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Bank error in your favor - collect $75"+"\n");
                    }
                    else if (temp_commChest==2){ //Doctor's fees - Pay $50
                        Player1.setMoney(Player1.getMoney()-50);
                        banker.setMoney(banker.getMoney()+50);
                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Doctor's fees - Pay $50");
                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Doctor's fees - Pay $50"+"\n");
                    }
                    else if(temp_commChest==3){ //It is your birthday Collect $10 from each player
                        Player1.setMoney(Player1.getMoney()+10);
                        Player2.setMoney(Player2.getMoney()-10);
                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw It is your birthday Collect $10 from each player");
                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw It is your birthday Collect $10 from each player"+"\n");
                    }
                    else if(temp_commChest==4){ //Grand Opera Night - collect $50 from every player for opening night seats
                        Player1.setMoney(Player1.getMoney()+50);
                        Player2.setMoney(Player2.getMoney()-50);
                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Grand Opera Night - collect $50 from every player for opening night seats");
                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Grand Opera Night - collect $50 from every player for opening night seats"+"\n");
                    }
                    else if(temp_commChest==5){ //Income Tax refund - collect $20
                        Player1.setMoney(Player1.getMoney()+20);
                        banker.setMoney(banker.getMoney()-20);
                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Income Tax refund - collect $20");
                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Income Tax refund - collect $20"+"\n");
                    }
                    else if(temp_commChest==6){ //Life Insurance Matures - collect $100
                        Player1.setMoney(Player1.getMoney()+100);
                        banker.setMoney(banker.getMoney()-100);
                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Life Insurance Matures - collect $100");
                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Life Insurance Matures - collect $100"+"\n");
                    }
                    else if(temp_commChest==7){ //Pay Hospital Fees of $100
                        Player1.setMoney(Player1.getMoney()-100);
                        banker.setMoney(banker.getMoney()+100);
                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Pay Hospital Fees of $100");
                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw Pay Hospital Fees of $100"+"\n");
                    }
                    else if (temp_commChest==8){ //Pay School Fees of $50
                        Player1.setMoney(Player1.getMoney()-50);
                        if(Player1.getMoney()<0){
                            //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                            output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                        }
                        else {
                            banker.setMoney(banker.getMoney() + 50);
                            //System.out.println(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 draw Pay School Fees of $50");
                            output.write(Player1.getName() + "\t" + command_arr[1] + "\t" + temp_position1 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 1 draw Pay School Fees of $50"+"\n");
                        }
                    }
                    else if (temp_commChest==9){ //You inherit $100
                        Player1.setMoney(Player1.getMoney()+100);
                        banker.setMoney(banker.getMoney()-100);
                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw You inherit $100");
                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw You inherit $100"+"\n");
                    }
                    else if (temp_commChest==10){ //From sale of stock you get $50
                        Player1.setMoney(Player1.getMoney()+50);
                        banker.setMoney(banker.getMoney()-50);
                        //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw From sale of stock you get $50");
                        output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 draw From sale of stock you get $50"+"\n");
                    }
                    temp_commChest++;
                }
                else if(temp_position1==1){ //when Player 1 is at GO

                }
                else if (temp_position1==11){ //Jail
                    //allah kurtarsın kardesim
                    //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 went to jail");
                    output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 went to jail"+"\n");

                    //3 tur bekletme
                }
                else if(temp_position1==21){ //Free Parking
                    //topla gel kardesim topla, Hooop
                }
                else if(temp_position1==31){ //Go to Jail
                    temp_position1= 11;
                    //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 went to jail");
                    output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 1 went to jail"+"\n");
                    //3 tur bekletme
                }


            }
            else if(command_arr[0].equals("Player 2")){ //The turn of Player 2
                temp_position2 = temp_position2 + Integer.parseInt(command_arr[1]);
                //System.out.println(temp_position2+"**");
                if (temp_position2 > 40){
                    temp_position2 = temp_position2 -40;
                    Player2.setMoney(Player2.getMoney()+200); //GO ' dan gecerse 200 TL alır kasadan
                    banker.setMoney(banker.getMoney()-200);
                }
                //System.out.println(temp_position1+"****");
                if(temp_position2== 2|| temp_position2==4 || temp_position2==7 || temp_position2==9 || temp_position2==10 || temp_position2==12 || temp_position2==14 ||temp_position2==17||temp_position2==19||temp_position2==20||temp_position2==22||temp_position2==24||temp_position2==27||temp_position2==28||temp_position2==30||temp_position2==32||temp_position2==33||temp_position2==35||temp_position2==38||temp_position2==40){
                    //checking if the position is a Land property
                    for(Land lnd: jr.Land_list){
                        if(lnd.getId().equals(temp_position2)){
                            if(lnd.getOwned().equals(0)){ //player 2 in geldigi yerdeki Land bos oldugunda yani satın alacagı zaman
                                lnd.setOwned(1); //satın alımın gerceklesmesi (tapu verilmesi gibi)
                                Player2.setMoney(Player2.getMoney()- lnd.getCost()); //ödeme yapılması
                                if(Player2.getMoney()<0){
                                    System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                    output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                }
                                else {
                                    p2_prop.add(lnd.getName());
                                    banker.setMoney(banker.getMoney() + lnd.getCost()); //paranın kasaya girmesi
                                    //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + Player2.getName() + " bought " + lnd.getName());
                                    output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + Player2.getName() + " bought " + lnd.getName()+"\n");
                                }
                            }
                            else{
                                //KİRA
                                if(lnd.getCost()<2001){
                                    temp_rent= lnd.getCost()*40/100;
                                    Player2.setMoney(Player2.getMoney()-temp_rent);
                                    if(Player2.getMoney()<0){
                                        //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                        output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                    }
                                    else {
                                        Player1.setMoney(Player1.getMoney() + temp_rent);
                                        //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + lnd.getName());
                                        output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + lnd.getName()+"\n");
                                    }
                                }
                                else if(lnd.getCost()>2000 && lnd.getCost()<3001){
                                    temp_rent= lnd.getCost()*30/100;
                                    Player2.setMoney(Player2.getMoney()-temp_rent);
                                    if(Player2.getMoney()<0){
                                        //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                        output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                    }
                                    else {
                                        Player1.setMoney(Player1.getMoney() + temp_rent);
                                        //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + lnd.getName());
                                        output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + lnd.getName()+"\n");
                                    }
                                }
                                else if(lnd.getCost()>3000 && lnd.getCost()<4001){
                                    temp_rent= lnd.getCost()*35/100;
                                    Player2.setMoney(Player2.getMoney()-temp_rent);
                                    if(Player2.getMoney()<0){
                                        //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                        output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                    }
                                    else {
                                        Player1.setMoney(Player1.getMoney() + temp_rent);
                                        //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + lnd.getName());
                                        output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + lnd.getName()+"\n");
                                    }
                                }
                            }
                        }
                    }
                }
                else if(temp_position2==13 ||temp_position2==29){ //checking if the new position is a Company property
                    for(Company cmp: jr.Comp_list){
                        if(cmp.getId().equals(temp_position2)){
                            if(cmp.getOwned().equals(0)){ //player 1 in geldigi yerdeki Company bos oldugunda yani satın alacagı zaman
                                cmp.setOwned(1); //satın alım gerceklesmesi
                                Player2.setMoney(Player2.getMoney()- cmp.getCost()); //paranın ödenmesi
                                if(Player2.getMoney()<0){
                                    //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                    output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                }
                                else {
                                    p2_prop.add(cmp.getName());
                                    banker.setMoney(banker.getMoney() + cmp.getCost());
                                    //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + Player2.getName() + " bought " + cmp.getName());
                                    output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + Player2.getName() + " bought " + cmp.getName()+"\n");
                                }

                            }
                            else{
                                //KİRA
                                temp_rent= Integer.parseInt(command_arr[1]) * 4; //the rent is 4 times the dice at company properties
                                Player2.setMoney(Player2.getMoney()-temp_rent);
                                if(Player2.getMoney()<0){
                                    //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                    output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                }
                                else {
                                    Player1.setMoney(Player1.getMoney() + temp_rent);
                                    //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + cmp.getName());
                                    output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + cmp.getName()+"\n");
                                }
                            }
                        }
                    }
                }
                else if(temp_position2== 6||temp_position2==16||temp_position2==26||temp_position2==36){ //checking if the new position is a Railroad property
                    for(Railroads rrd: jr.Rail_list){
                        if(rrd.getId().equals(temp_position2)){
                            if(rrd.getOwned().equals(0)){ //player 1 in geldigi yerdeki Railroad bos oldugunda yani satın alacagı zaman
                                rrd.setOwned(1);
                                Player2.setMoney(Player2.getMoney()-rrd.getCost()); //paranın ödenmesi
                                if(Player2.getMoney()<0){
                                    //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                    output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                }
                                else {
                                    p2_prop.add(rrd.getName());
                                    banker.setMoney(banker.getMoney() + rrd.getCost());
                                    //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + Player2.getName() + " bought " + rrd.getName());
                                    output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + Player2.getName() + " bought " + rrd.getName()+"\n");
                                }
                            }
                            else{
                                //KİRA
                                int rrd_ct=0; // the amount of railroads owned
                                for(Railroads rrd2: jr.Rail_list){

                                    if(rrd2.getOwned()==1){
                                        rrd_ct++;
                                    }
                                }
                                temp_rent= rrd_ct * 25;
                                Player2.setMoney(Player2.getMoney()-temp_rent);
                                if(Player2.getMoney()<0){
                                    //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                    output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                }
                                else {
                                    Player1.setMoney(Player1.getMoney() + temp_rent);
                                    //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + rrd.getName());
                                    output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + rrd.getName()+"\n");
                                }
                            }
                        }
                    }
                }

                else if (temp_position2==5||temp_position2==39){ //TAX
                    Player2.setMoney(Player2.getMoney()-100);
                    if(Player2.getMoney()<0){
                        //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                        output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                    }
                    else {
                        banker.setMoney(banker.getMoney() + 100);
                        //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid Tax");
                        output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid Tax"+"\n");
                    }
                }
                else if(temp_position2==8||temp_position2==23||temp_position2==37){ //CHANCE
                    if(temp_chance==0){ //Advance to Go (Collect $200)
                        temp_position2 = 1;
                        Player2.setMoney(Player2.getMoney()+200);
                        String draw = "Advance to Go (Collect $200)";
                        //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2" +" draw "+ draw);
                        output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2" +" draw "+ draw+"\n");
                    }
                    else if(temp_chance==1){ //Advance to Leicester Square
                        temp_position2=27;
                        for (Land lnd: jr.Land_list){
                            if(lnd.getOwned().equals(0)){ //SAHİBİ YOKSA ALIR
                                lnd.setOwned(1);
                                Player2.setMoney(Player2.getMoney()- lnd.getCost());
                                if(Player2.getMoney()<0){
                                    //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                    output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                }
                                else {
                                    p2_prop.add(lnd.getName());
                                    banker.setMoney(banker.getMoney() + lnd.getCost());
                                    //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Advance to Leicester Square Player 2 bought Leicester Square");
                                    output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Advance to Leicester Square Player 2 bought Leicester Square"+"\n");
                                }
                            }
                            else{
                                //KİRA
                                if(lnd.getCost()<2001){
                                    temp_rent= lnd.getCost()*40/100;
                                    Player2.setMoney(Player2.getMoney()-temp_rent);
                                    if(Player2.getMoney()<0){
                                        //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                        output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                    }
                                    else {
                                        Player1.setMoney(Player1.getMoney() + temp_rent);
                                        //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + lnd.getName());
                                        output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + lnd.getName()+"\n");
                                    }
                                }
                                else if(lnd.getCost()>2000 && lnd.getCost()<3001){
                                    temp_rent= lnd.getCost()*30/100;
                                    Player2.setMoney(Player2.getMoney()-temp_rent);
                                    if(Player2.getMoney()<0){
                                        //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                        output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                    }
                                    else {
                                        Player1.setMoney(Player1.getMoney() + temp_rent);
                                        //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + lnd.getName());
                                        output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + lnd.getName()+"\n");
                                    }
                                }
                                else if(lnd.getCost()>3000 && lnd.getCost()<4001){
                                    temp_rent= lnd.getCost()*35/100;
                                    Player2.setMoney(Player2.getMoney()-temp_rent);
                                    if(Player2.getMoney()<0){
                                        //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                        output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                    }
                                    else {
                                        Player1.setMoney(Player1.getMoney() + temp_rent);
                                        //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + lnd.getName());
                                        output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + lnd.getName()+"\n");
                                    }
                                }
                            }
                        }
                    }
                    else if(temp_chance==2){ //Go back 3 spaces
                        if(temp_position2==8){ //goes to 5: tax square
                            temp_position2=5;
                            Player2.setMoney(Player2.getMoney()-100);
                            if(Player2.getMoney()<0){
                                //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                            }
                            else {
                                banker.setMoney(banker.getMoney() + 100);
                                //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Go back 3 spaces Player 2 paid Tax");
                                output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Go back 3 spaces Player 2 paid Tax"+"\n");
                            }
                        }
                        else if(temp_position2==23){ //goes to 20: Vine Street
                            temp_position2=20;
                            for(Land lnd: jr.Land_list){
                                if(lnd.getOwned().equals(0)){ //SAHİBİ YOKSA ALIR
                                    lnd.setOwned(1);
                                    Player2.setMoney(Player2.getMoney()- lnd.getCost());
                                    if(Player2.getMoney()<0){
                                        //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                        output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                    }
                                    else {
                                        p2_prop.add(lnd.getName());
                                        banker.setMoney(banker.getMoney() + lnd.getCost());
                                        //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Go back 3 spaces Player 2 bought Vine Street");
                                        output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Go back 3 spaces Player 2 bought Vine Street"+"\n");
                                    }
                                }
                                else{
                                    //KİRA
                                    if(lnd.getCost()<2001){
                                        temp_rent= lnd.getCost()*40/100;
                                        Player2.setMoney(Player2.getMoney()-temp_rent);
                                        if(Player2.getMoney()<0){
                                            //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                            output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                        }
                                        else {
                                            Player1.setMoney(Player1.getMoney() + temp_rent);
                                            //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + lnd.getName());
                                            output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + lnd.getName()+"\n");
                                        }
                                    }
                                    else if(lnd.getCost()>2000 && lnd.getCost()<3001){
                                        temp_rent= lnd.getCost()*30/100;
                                        Player2.setMoney(Player2.getMoney()-temp_rent);
                                        if(Player2.getMoney()<0){
                                            //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                            output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                        }
                                        else {
                                            Player1.setMoney(Player1.getMoney() + temp_rent);
                                            //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + lnd.getName());
                                            output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + lnd.getName()+"\n");
                                        }
                                    }
                                    else if(lnd.getCost()>3000 && lnd.getCost()<4001){
                                        temp_rent= lnd.getCost()*35/100;
                                        Player2.setMoney(Player2.getMoney()-temp_rent);
                                        if(Player2.getMoney()<0){
                                            //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                            output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                        }
                                        else {
                                            Player1.setMoney(Player1.getMoney() + temp_rent);
                                            //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + lnd.getName());
                                            output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 paid rent for " + lnd.getName()+"\n");
                                        }
                                    }
                                }
                            }
                        }
                        else if(temp_position2==37){
                            temp_position2=34;
                            //COMMUNITY CHEST
                            if(temp_commChest==0){ //Advance to Go (Collect $200)
                                temp_position2 = 1;
                                Player2.setMoney(Player2.getMoney()+200);
                                //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Advance to Go (Collect $200)");
                                output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Advance to Go (Collect $200)"+"\n");
                            }
                            else if(temp_commChest==1){ //Bank error in your favor - collect $75
                                Player2.setMoney(Player2.getMoney()+75);
                                banker.setMoney(banker.getMoney()-75);
                                //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Bank error in your favor - collect $75");
                                output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Bank error in your favor - collect $75"+"\n");
                            }
                            else if (temp_commChest==2){ //Doctor's fees - Pay $50
                                Player2.setMoney(Player2.getMoney()-50);
                                if(Player2.getMoney()<0){
                                    //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                    output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                }
                                else {
                                    banker.setMoney(banker.getMoney() + 50);
                                    //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Doctor's fees - Pay $50");
                                    output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Doctor's fees - Pay $50"+"\n");
                                }
                            }
                            else if(temp_commChest==3){ //It is your birthday Collect $10 from each player
                                Player2.setMoney(Player2.getMoney()+10);
                                Player1.setMoney(Player1.getMoney()-10);
                                //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw It is your birthday Collect $10 from each player");
                                output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw It is your birthday Collect $10 from each player"+"\n");
                                if(Player1.getMoney()<0){
                                    //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                    output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                }
                            }
                            else if(temp_commChest==4){ //Grand Opera Night - collect $50 from every player for opening night seats
                                Player2.setMoney(Player2.getMoney()+50);
                                Player1.setMoney(Player1.getMoney()-50);
                                //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Grand Opera Night - collect $50 from every player for opening night seats");
                                output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Grand Opera Night - collect $50 from every player for opening night seats"+"\n");
                                if(Player1.getMoney()<0){
                                    //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                                    output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                                }
                            }
                            else if(temp_commChest==5){ //Income Tax refund - collect $20
                                Player2.setMoney(Player2.getMoney()+20);
                                banker.setMoney(banker.getMoney()-20);
                                //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Income Tax refund - collect $20");
                                output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Income Tax refund - collect $20"+"\n");
                            }
                            else if(temp_commChest==6){ //Life Insurance Matures - collect $100
                                Player2.setMoney(Player2.getMoney()+100);
                                banker.setMoney(banker.getMoney()-100);
                                //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Life Insurance Matures - collect $100");
                                output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Life Insurance Matures - collect $100"+"\n");
                            }
                            else if(temp_commChest==7){ //Pay Hospital Fees of $100
                                Player2.setMoney(Player2.getMoney()-100);
                                if(Player2.getMoney()<0){
                                    //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                    output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                }
                                else {
                                    banker.setMoney(banker.getMoney() + 100);
                                    //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Pay Hospital Fees of $100");
                                    output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Pay Hospital Fees of $100"+"\n");
                                }
                            }
                            else if (temp_commChest==8){ //Pay School Fees of $50
                                Player2.setMoney(Player2.getMoney()-50);
                                if(Player2.getMoney()<0){
                                    //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                                    output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                                }
                                else {
                                    banker.setMoney(banker.getMoney() + 50);
                                    //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Pay School Fees of $50");
                                    output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Pay School Fees of $50"+"\n");
                                }
                            }
                            else if (temp_commChest==9){ //You inherit $100
                                Player2.setMoney(Player2.getMoney()+100);
                                banker.setMoney(banker.getMoney()-100);
                                //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw You inherit $100");
                                output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw You inherit $100"+"\n");
                            }
                            else if (temp_commChest==10){ //From sale of stock you get $50
                                Player2.setMoney(Player2.getMoney()+50);
                                banker.setMoney(banker.getMoney()-50);
                                //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw From sale of stock you get $50");
                                output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw From sale of stock you get $50"+"\n");
                            }
                            temp_commChest++;

                        }
                    }
                    else if (temp_chance==3){ //Pay poor tax of $15
                        Player2.setMoney(Player2.getMoney()-15);
                        if(Player2.getMoney()<0){
                            //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                            output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                        }
                        else {
                            banker.setMoney(banker.getMoney() + 15);
                            //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Pay poor tax of $15 Player 1 paid Tax");
                            output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Pay poor tax of $15 Player 1 paid Tax"+"\n");
                        }
                    }
                    else if(temp_chance==4){ //Your building loan matures - collect $150
                        Player2.setMoney((Player2.getMoney()+150));
                        banker.setMoney(banker.getMoney()-150);
                        //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Your building loan matures - collect $150");
                        output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Your building loan matures - collect $150"+"\n");
                    }
                    else if(temp_chance==5){ //You have won a crossword competition - collect $100
                        Player2.setMoney(Player2.getMoney()+100);
                        banker.setMoney(banker.getMoney()-100);
                        //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw You have won a crossword competition - collect $100");
                        output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw You have won a crossword competition - collect $100"+"\n");
                    }
                    temp_chance++;
                    if(temp_chance>5){
                        temp_chance=0;
                    }
                }
                else if (temp_position1==3||temp_position1==18||temp_position1==34){ //COMMUNITY CHEST
                    if(temp_commChest==0){ //Advance to Go (Collect $200)
                        temp_position2 = 1;
                        Player2.setMoney(Player2.getMoney()+200);
                        //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Advance to Go (Collect $200)");
                        output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Advance to Go (Collect $200)"+"\n");
                    }
                    else if(temp_commChest==1){ //Bank error in your favor - collect $75
                        Player2.setMoney(Player2.getMoney()+75);
                        banker.setMoney(banker.getMoney()-75);
                        //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Bank error in your favor - collect $75");
                        output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Bank error in your favor - collect $75"+"\n");
                    }
                    else if (temp_commChest==2){ //Doctor's fees - Pay $50
                        Player2.setMoney(Player2.getMoney()-50);
                        if(Player2.getMoney()<0){
                            //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                            output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                        }
                        else {
                            banker.setMoney(banker.getMoney() + 50);
                            //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Doctor's fees - Pay $50");
                            output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Doctor's fees - Pay $50"+"\n");
                        }
                    }
                    else if(temp_commChest==3){ //It is your birthday Collect $10 from each player
                        Player2.setMoney(Player2.getMoney()+10);
                        Player1.setMoney(Player1.getMoney()-10);
                        //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw It is your birthday Collect $10 from each player");
                        output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw It is your birthday Collect $10 from each player"+"\n");
                        if(Player1.getMoney()<0){
                            //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                            output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                        }
                    }
                    else if(temp_commChest==4){ //Grand Opera Night - collect $50 from every player for opening night seats
                        Player2.setMoney(Player2.getMoney()+50);
                        Player1.setMoney(Player1.getMoney()-50);
                        //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Grand Opera Night - collect $50 from every player for opening night seats");
                        output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Grand Opera Night - collect $50 from every player for opening night seats"+"\n");
                        if(Player1.getMoney()<0){
                            //System.out.println(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt");
                            output.write(Player1.getName()+"\t"+command_arr[1]+"\t"+temp_position1+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player1.getName()+"goes bankrupt"+"\n");
                        }
                    }
                    else if(temp_commChest==5){ //Income Tax refund - collect $20
                        Player2.setMoney(Player2.getMoney()+20);
                        banker.setMoney(banker.getMoney()-20);
                        //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Income Tax refund - collect $20");
                        output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Income Tax refund - collect $20"+"\n");
                    }
                    else if(temp_commChest==6){ //Life Insurance Matures - collect $100
                        Player2.setMoney(Player2.getMoney()+100);
                        banker.setMoney(banker.getMoney()-100);
                        //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Life Insurance Matures - collect $100");
                        output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw Life Insurance Matures - collect $100"+"\n");
                    }
                    else if(temp_commChest==7){ //Pay Hospital Fees of $100
                        Player2.setMoney(Player2.getMoney()-100);
                        if(Player2.getMoney()<0){
                            //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                            output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                        }
                        else {
                            banker.setMoney(banker.getMoney() + 100);
                            //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Pay Hospital Fees of $100");
                            output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Pay Hospital Fees of $100"+"\n");
                        }
                    }
                    else if (temp_commChest==8){ //Pay School Fees of $50
                        Player2.setMoney(Player2.getMoney()-50);
                        if(Player2.getMoney()<0){
                            //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt");
                            output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+0+"\t"+Player2.getMoney()+"\t"+Player2.getName()+"goes bankrupt"+"\n");
                        }
                        else {
                            banker.setMoney(banker.getMoney() + 50);
                            //System.out.println(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Pay School Fees of $50");
                            output.write(Player2.getName() + "\t" + command_arr[1] + "\t" + temp_position2 + "\t" + Player1.getMoney() + "\t" + Player2.getMoney() + "\t" + "Player 2 draw Pay School Fees of $50"+"\n");
                        }
                    }
                    else if (temp_commChest==9){ //You inherit $100
                        Player2.setMoney(Player2.getMoney()+100);
                        banker.setMoney(banker.getMoney()-100);
                        //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw You inherit $100");
                        output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw You inherit $100"+"\n");
                    }
                    else if (temp_commChest==10){ //From sale of stock you get $50
                        Player2.setMoney(Player2.getMoney()+50);
                        banker.setMoney(banker.getMoney()-50);
                        //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw From sale of stock you get $50");
                        output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 draw From sale of stock you get $50"+"\n");
                    }
                    temp_commChest++;
                }
                else if(temp_position2==1){ //when Player 2 is at GO

                }
                else if (temp_position2==11){ //Jail
                    //allah kurtarsın kardesim
                    //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 went to jail");
                    output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 went to jail"+"\n");
                    //3 tur bekletme
                }
                else if(temp_position2==21){ //Free Parking
                    //topla gel kardesim topla, Hooop
                }
                else if(temp_position2==31){ //Go to Jail
                    temp_position2= 11;
                    //System.out.println(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 went to jail");
                    output.write(Player2.getName()+"\t"+command_arr[1]+"\t"+temp_position2+"\t"+Player1.getMoney()+"\t"+Player2.getMoney()+"\t"+"Player 2 went to jail"+"\n");
                    //3 tur bekletme
                }
            }
            else if(command_arr[0].equals("show()")){ //Displaying the current status of the both Players' and the Banker's
                //System.out.println("i am working AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

                property_list ="";
                for(Object prop: p1_prop){
                    property_list = property_list +prop+",";
                }
                property_list2="";
                for(Object prop2: p2_prop){
                    property_list2 = property_list2 +prop2+",";
                }
                //System.out.println("-------------------------------------------------------------------------------------------------------------------------");
                output.write("-------------------------------------------------------------------------------------------------------------------------"+"\n");
                //System.out.println(Player1.getName()+"\t"+Player1.getMoney()+"\t"+"have: "+property_list );
                output.write(Player1.getName()+"\t"+Player1.getMoney()+"\t"+"have: "+property_list +"\n");
                //System.out.println(Player2.getName()+"\t"+Player2.getMoney()+"\t"+"have: "+property_list2);
                output.write(Player2.getName()+"\t"+Player2.getMoney()+"\t"+"have: "+property_list2+"\n");
                //System.out.println("Banker"+"\t"+banker.getMoney());
                output.write("Banker"+"\t"+banker.getMoney()+"\n");
                if(Math.max(Player1.getMoney(),Player2.getMoney())==Player1.getMoney()) {
                    //System.out.println("Winner" + "\t" + Player1.getName());
                    output.write("Winner" + "\t" + Player1.getName()+"\n");
                }
                else if(Math.max(Player1.getMoney(),Player2.getMoney())==Player2.getMoney()){
                    //System.out.println("Winner" + "\t" + Player2.getName());
                    output.write("Winner" + "\t" + Player2.getName()+"\n");
                }
                //System.out.println("----------------------------------------------------------------------------------------------------------------------------");
                output.write("----------------------------------------------------------------------------------------------------------------------------"+"\n");
            }
        }
        property_list ="";
        for(Object prop: p1_prop){
            property_list = property_list +prop+",";
        }
        property_list2="";
        for(Object prop2: p2_prop){
            property_list2 = property_list2 +prop2+",";
        }
        //System.out.println("-------------------------------------------------------------------------------------------------------------------------");
        output.write("-------------------------------------------------------------------------------------------------------------------------"+"\n");
        //System.out.println(Player1.getName()+"\t"+Player1.getMoney()+"\t"+"have: "+property_list );
        output.write(Player1.getName()+"\t"+Player1.getMoney()+"\t"+"have: "+property_list +"\n");
        //System.out.println(Player2.getName()+"\t"+Player2.getMoney()+"\t"+"have: "+property_list2);
        output.write(Player2.getName()+"\t"+Player2.getMoney()+"\t"+"have: "+property_list2+"\n");
        //System.out.println("Banker"+"\t"+banker.getMoney());
        output.write("Banker"+"\t"+banker.getMoney()+"\n");
        if(Math.max(Player1.getMoney(),Player2.getMoney())==Player1.getMoney()) {
            //System.out.println("Winner" + "\t" + Player1.getName());
            output.write("Winner" + "\t" + Player1.getName()+"\n");
        }
        else if(Math.max(Player1.getMoney(),Player2.getMoney())==Player2.getMoney()){
            //System.out.println("Winner" + "\t" + Player2.getName());
            output.write("Winner" + "\t" + Player2.getName()+"\n");
        }
        //System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        output.write("----------------------------------------------------------------------------------------------------------------------------"+"\n");


        output.close();
    }
}
