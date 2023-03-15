import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;


public class PropertyJsonReader {
    private LinkedList<Square> squares = new LinkedList<Square>();
    public ArrayList<Land> Land_list = new ArrayList<Land>();
    public ArrayList<Railroads> Rail_list = new ArrayList<Railroads>();
    public ArrayList<Company> Comp_list = new ArrayList<Company>();

    public PropertyJsonReader() {
        JSONParser processor = new JSONParser();
        try (Reader file = new FileReader("property.json")){
            JSONObject jsonfile = (JSONObject) processor.parse(file);
            JSONArray Land = (JSONArray) jsonfile.get("1");
            for(Object i:Land){
                Land land_prop = new Land();
                //You can reach items by using statements below:
                land_prop.setId(Integer.parseInt((String)((JSONObject)i).get("id")));;
                land_prop.setName(((JSONObject) i).get("name"));
                land_prop.setCost(Integer.parseInt((String)((JSONObject)i).get("cost")));
                //And you can add these items to any data structure (e.g. array, linkedlist etc.
                Land_list.add(land_prop);




            }
            JSONArray RailRoad = (JSONArray) jsonfile.get("2");
            for(Object i:RailRoad){
                Railroads temp_rail = new Railroads();
                //You can reach items by using statements below:
                temp_rail.setId(Integer.parseInt((String)((JSONObject)i).get("id")));
                temp_rail.setName(((JSONObject)i).get("name"));
                temp_rail.setCost(Integer.parseInt((String)((JSONObject)i).get("cost")));
                //And you can add these items to any data structure (e.g. array, linkedlist etc.
                Rail_list.add(temp_rail);
            }

            JSONArray Company = (JSONArray) jsonfile.get("3");
            for(Object i:Company){
                Company temp_comp = new Company();
                //You can reach items by using statements below:
                temp_comp.setId(Integer.parseInt((String)((JSONObject)i).get("id")));
                temp_comp.setName(((JSONObject)i).get("name"));
                temp_comp.setCost(Integer.parseInt((String)((JSONObject)i).get("cost")));
                //And you can add these items to any data structure (e.g. array, linkedlist etc.
                Comp_list.add(temp_comp);
            }

        } catch (IOException e){
            e.printStackTrace();
        } catch (ParseException e){
            e.printStackTrace();
        }
    }
    //You can add function(s) if you want
}