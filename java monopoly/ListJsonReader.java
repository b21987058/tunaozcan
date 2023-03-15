import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;


public class ListJsonReader{
    public ArrayList<Chance> Chance_list = new ArrayList<Chance>();
    public ArrayList<CommunityChest> CommChest_list = new ArrayList<CommunityChest>();

    public ListJsonReader(){
        JSONParser processor = new JSONParser();
        try (Reader file = new FileReader("list.json")){
            JSONObject jsonfile = (JSONObject) processor.parse(file);
            JSONArray chanceList = (JSONArray) jsonfile.get("chanceList");
            for(Object i:chanceList){
                Chance temp_chance = new Chance();
                temp_chance.setItem(((JSONObject)i).get("item"));
				 /*You can reach items by using:
				 ((String)
				 And you can add these items to any datastructure (e.g. array, linkedlist, etc.)*/
                Chance_list.add(temp_chance);
            }
            JSONArray communityChestList = (JSONArray) jsonfile.get("communityChestList");
            for(Object i:communityChestList){
                CommunityChest temp_commChest = new CommunityChest();
                //You can reach items by using:
                temp_commChest.setItem(((JSONObject)i).get("item"));
                //And you can add these items to any datastructure (e.g. array, linkedlist, etc.)
                CommChest_list.add(temp_commChest);
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
    //You can add function if you want
}

