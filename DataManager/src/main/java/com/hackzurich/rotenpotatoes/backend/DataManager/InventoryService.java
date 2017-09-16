package com.hackzurich.rotenpotatoes.backend.DataManager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

import com.hackzurich.rotenpotatoes.backend.data.GeoInventory;
import com.hackzurich.rotenpotatoes.backend.data.Item;
import com.hackzurich.rotenpotatoes.backend.data.Response;
import com.hackzurich.rotenpotatoes.backend.data.HashMapStamped;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 * Created by kazi on 16.09.17.
 */

class TimestampComparer implements Comparator<HashMapStamped> {
    @Override
    public int compare(HashMapStamped a, HashMapStamped b) {
        return (int) (a.getTimestamp() - b.getTimestamp());
    }
}

public class InventoryService {
    private static final int PAST_LOOKUP = 2000;
    
    //Hacky database 
    ArrayList<HashMapStamped> inventory_history = new ArrayList<HashMapStamped>();
    ArrayList<String> tags = new ArrayList<String>();
    HashMap<String, ArrayList<String>> food2tags = new HashMap<String, ArrayList<String>>();
    
 
    
    //TODO initiwlize the tags with something 
    //todo initialize the food2tags map
    
    public static String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    public void initialize_data() throws IOException{
        tags.clear();
        food2tags.clear();
        
        String food2tag_file = readFile("./resources/food2tag_simple.json");
        food2tags = new ObjectMapper().readValue(food2tag_file, HashMap.class);
        
        String tags_file = readFile("./resources/tags_simple.json");
        ObjectMapper mapper = new ObjectMapper();
        //tags = mapper.readValue(tags_file, (Class<T>) String[].class);
        tags = mapper.readValue(tags_file, new TypeReference<List<String>>(){});
        
        
        
        //System.out.println("food2tags is " + food2tags);
        //System.out.println("tags is " + tags);
        
        
    }
    
    
    
    public HashMapStamped inventory2map(GeoInventory inventory){
        
        try {
            //TODO fucking bad to read the file everythme but it doesnt fucking matter now
            initialize_data();
        } catch (IOException ex) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("food2tags is " + food2tags);
        System.out.println("tags is " + tags);
        
        HashMapStamped map = new HashMapStamped();
        
        //initialize the map  (TODO not completely efficient because we still have in the map the empty tags)
        System.out.println("tags size is" + tags.size());
        for(int i=0; i<tags.size(); i++){
            System.out.println("putting empty array in");
            //map.getMap().put(tags.get(i), new ArrayList<Item>());
            System.out.println("finished empty array in");
        }
        
        
        System.out.println("map is" +map);
        
        //for every item in the inventory 
            //get the labels for the item 
            //add it to the corresponding key in the map 
        
        for(int i=0; i<inventory.getItems().size(); i++){
              Item item =inventory.getItems().get(i);
              ArrayList<String> item_tags= food2tags.get(item.getName());
              //for each one of those tags, add that item to the map under the corresponding tag 
              for(int j=1; j<item_tags.size(); j++){
                 map.getMap().get(item_tags.get(j)).add(item); 
              }
              //also add the name itself as its own category
               map.getMap().get(item.getName()).add(item); 
        }
        
        System.out.println("map is" +map);
        
        
        map.setTimestamp(inventory.getTimestamp());
        
        System.out.println("map is" +map);
        
        return map;
    }
    
    public void processInputData(GeoInventory inventory) {
        //  HERE DO THE MAGIC WITH DATA - create a map, reverse map, etc...
        HashMapStamped map= inventory2map(inventory);
        if (inventory_history.isEmpty()){
            inventory_history.add(map);
        }else{
            //insert in sortd inventory by time 
            inventory_history.add(map);
            Collections.sort(inventory_history, new TimestampComparer());
        }        
        
    }

    public Response getInventory(String category, long timestamp) {
        //  HERE WE SHOULD READ DATA FROM THE CACHE/MAP
        
        Response inventory = new Response();
        inventory.setTimestamp(new Date(timestamp));
        List<GeoInventory> geoInv = new ArrayList<>();
        
        //get items from that category and x in time back
        long last_timestamp=inventory_history.get(inventory_history.size() - 1).getTimestamp();
        for(int t=(int) last_timestamp; t>Math.min(last_timestamp-PAST_LOOKUP,0); t--){
          //at that point in the history we have a fridge
            //check all its items that match the category and put in the response 
            HashMapStamped map=inventory_history.get(t);
            ArrayList<Item> items_in_category= map.getMap().get(category);
            
            if (!items_in_category.isEmpty()){
                GeoInventory geoInventory = new GeoInventory();
                geoInventory.setLat(map.getLat());
                geoInventory.setLng(map.getLng());
                
                for(int i=1; i<items_in_category.size(); i++){
                    Item item = new Item();
                    item.setName(items_in_category.get(i).getName());
                    item.setQuantity(items_in_category.get(i).getQuantity());
                    item.setUnit(items_in_category.get(i).getUnit());
                    geoInventory.appendItem(item);
                }
                geoInv.add(geoInventory);
                
            }
            
            
            
        }
        
        inventory.setGeoInventories(geoInv);
        
        return inventory;
        
        /*
        Response inventory = new Response();
        inventory.setTimestamp(new Date(timestamp));
        List<GeoInventory> geoInv = new ArrayList<>();
        GeoInventory geoInventory = new GeoInventory();
        geoInventory.setLat(80.0000);
        geoInventory.setLng(23.0022);
        Item item = new Item();
        item.setName("Potatoes");
        item.setQuantity(1.3);
        item.setUnit("kg");

        Item item2 = new Item();
        item2.setName(category);
        item2.setQuantity(1);
        item2.setUnit("liter");

        geoInventory.setItems(Arrays.asList(item, item));
        geoInv.add(geoInventory);
        inventory.setGeoInventories(geoInv);
        return inventory;

        */
    }
}
