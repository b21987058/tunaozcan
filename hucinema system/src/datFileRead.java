import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class datFileRead {
     static ArrayList<Users> usersArrayList = new ArrayList<>();
     static ArrayList<Film> filmArrayList = new ArrayList<>();
     static ArrayList<Hall> hallArrayList = new ArrayList<>();
     static ArrayList<Seat> seatArrayList = new ArrayList<>();
    public static void datFileRead(String path){
        BufferedReader br = null;
        try {
            File file = new File(path); // java.io.File
            FileReader fr = new FileReader(file); // java.io.FileReader
            br = new BufferedReader(fr); // java.io.BufferedReader
            String line;
            String[] line_arr;
            String[] film_arr;
            String[] hall_arr;
            String[] seat_arr;
            while ((line = br.readLine()) != null) {
                // process the line

                if(line.startsWith("user")) {
                    //System.out.println(line);
                    line_arr = line.split("\t");
                    Users users = new Users(line_arr[0],line_arr[1],line_arr[2],line_arr[3],line_arr[4]);
                    usersArrayList.add(users);
                }
                else if(line.startsWith("film")){
                    System.out.println(line);
                    film_arr = line.split("\t");
                    Film film = new Film(film_arr[0],film_arr[1],film_arr[2],film_arr[3]);
                    filmArrayList.add(film);
                }
                else  if (line.startsWith("hall")){
                    //System.out.println(line);
                    hall_arr = line.split("\t");
                    Hall hall= new Hall(hall_arr[0],hall_arr[1],hall_arr[2],hall_arr[3],hall_arr[4],hall_arr[5]);
                    hallArrayList.add(hall);
                }
                else if(line.startsWith("seat")){
                    //System.out.println(line);
                    seat_arr = line.split("\t");
                    Seat seat = new Seat(seat_arr[0],seat_arr[1],seat_arr[2],seat_arr[3],seat_arr[4],seat_arr[5],seat_arr[6]);
                    seatArrayList.add(seat);
                }
                else {
                    System.out.println(line);
                }
            }
        }
        catch(IOException e) { e.printStackTrace();}
        finally
        {
            try { if (br != null) br.close(); }
            catch(IOException e) { e.printStackTrace(); }
        }
    }

}
