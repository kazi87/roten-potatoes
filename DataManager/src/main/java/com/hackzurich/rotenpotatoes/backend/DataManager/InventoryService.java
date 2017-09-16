package com.hackzurich.rotenpotatoes.backend.DataManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.hackzurich.rotenpotatoes.backend.data.GeoInventory;
import com.hackzurich.rotenpotatoes.backend.data.Item;
import com.hackzurich.rotenpotatoes.backend.data.Response;

/**
 * Created by kazi on 16.09.17.
 */
public class InventoryService {

    public void processInputData(GeoInventory inventory) {
        //  HERE DO THE MAGIC WITH DATA - create a map, reverse map, etc...
    }

    public Response getInventory(String category, long timestamp) {
        //  HERE WE SHOULD READ DATA FROM THE CACHE/MAP
        
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
    }
}
