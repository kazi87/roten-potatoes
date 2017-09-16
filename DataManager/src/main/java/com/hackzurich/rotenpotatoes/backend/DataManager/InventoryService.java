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
    //private static final int PAST_LOOKUP = 2000;
    private static final long PAST_LOOKUP = 900000000;
    
    //Hacky database 
    private static ArrayList<HashMapStamped> inventory_history = new ArrayList<HashMapStamped>();
    private static ArrayList<String> tags = new ArrayList<String>();
    private static HashMap<String, String> food2tags = new HashMap<String, String>();
    
 
    
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
        
        //System.out.println("food2tags is " + food2tags);
        //System.out.println("tags is " + tags);
        
        HashMapStamped map = new HashMapStamped();
        
        //initialize the map  (TODO not completely efficient because we still have in the map the empty tags)
        //System.out.println("tags size is" + tags.size());
        for(int i=0; i<tags.size(); i++){
            //System.out.println("putting empty array in");
            //System.out.println("tags at i is " + tags.get(i));
            //System.out.println("booom");
            //System.out.println("map si " + map.getMap());
            map.getMap().put(tags.get(i), new ArrayList<Item>());
            //System.out.println("finished empty array in");
        }
        
        
        //System.out.println("map is" +map.getMap());
        
        //for every item in the inventory 
            //get the labels for the item 
            //add it to the corresponding key in the map 
        
        //System.out.println("inventory get items is " + inventory.getItems());
        for(int i=0; i<inventory.getItems().size(); i++){
              Item item =inventory.getItems().get(i);
              //System.out.println("food2tags is " +food2tags);
              String item_tag= food2tags.get(item.getName());
              //for each one of those tags, add that item to the map under the corresponding tag 
              //System.out.println("loop over inventory");
              //System.out.println("item is " + item);
              //System.out.println("item_tag is " + item_tag);
              //System.out.println("map is" + map.getMap());
              //System.out.println("item_tags has size" + item_tags.size());
              map.getMap().get(item_tag).add(item); 
              
              //System.out.println("adding the name itself");
              
              //also add the name itself as its own category
              ArrayList<Item> items_dummy= new ArrayList<Item>();
              items_dummy.add(item);
              map.getMap().put(item.getName(),items_dummy); 
        }
        
        //System.out.println("map is" +map.getMap());
        
        
        map.setTimestamp(inventory.getTimestamp());
        
        System.out.println("last map is" +map.getMap());
        
        return map;
    }
    
    public void processInputData(GeoInventory inventory) {
        //  HERE DO THE MAGIC WITH DATA - create a map, reverse map, etc...
        
        System.out.println("entering process input data");
        HashMapStamped map= inventory2map(inventory);
        if (inventory_history.isEmpty()){
            System.out.println("adding to empty map");
            inventory_history.add(map);
            System.out.println("added to empty map");
        }else{
            //insert in sortd inventory by time 
            System.out.println("adding to partil map");
            inventory_history.add(map);
            System.out.println("sorting");
            Collections.sort(inventory_history, new TimestampComparer());
        }        
        System.out.println("finished: " + inventory_history.size());
        assert(true);
    }

    public Response getInventory(String category, long timestamp) {
        //  HERE WE SHOULD READ DATA FROM THE CACHE/MAP
        
        //System.out.println("get inverotry enter");
        Response inventory = new Response();
        inventory.setTimestamp(new Date(timestamp));
        List<GeoInventory> geoInv = new ArrayList<>();
        
         System.out.println("inventory history in gt inventory has size " + inventory_history.size());
        
        if (inventory_history.isEmpty()){
            return inventory;
        }

        System.out.println("inventory history" + inventory_history);
        for (int i = 0; i < inventory_history.size(); i++) {
            System.out.println("timestamp is" + inventory_history.get(i).getTimestamp());
        }
       
        
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
                
                for(int i=0; i<items_in_category.size(); i++){
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
