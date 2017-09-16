package com.hackzurich.rotenpotatoes.backend.DataManager;

import java.util.Arrays;
import java.util.Date;

import com.hackzurich.rotenpotatoes.backend.data.GeoInventory;
import com.hackzurich.rotenpotatoes.backend.data.Item;
import com.hackzurich.rotenpotatoes.backend.data.Response;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by kazi on 16.09.17.
 */
public class InventoryServiceTest {

    private static InventoryService inventoryService;

    @BeforeClass
    public static void setUp() {
        inventoryService = new InventoryService();
    }

    @Test
    public void processInputData() throws Exception {
        GeoInventory geoInventory = new GeoInventory();
        geoInventory.setLat(47.376887);
        geoInventory.setLng(8.541694);

        Item item = new Item();
        item.setName("potato");
        item.setQuantity(1.3);
        item.setUnit("kg");

        Item item2 = new Item();
        item2.setName("milk");
        item2.setQuantity(1);
        item2.setUnit("liter");

        Item item3 = new Item();
        item3.setName("butter");
        item3.setQuantity(250);
        item3.setUnit("grams");

        geoInventory.setItems(Arrays.asList(item, item2, item3));
        long timestamp = new Date().getTime();
        geoInventory.setTimestamp(timestamp-1);
        

        inventoryService.processInputData(geoInventory);
        
        String category = "potato";
        timestamp = new Date().getTime();

        Response result = inventoryService.getInventory(category, timestamp);

        System.out.println(result);
    }

   
}