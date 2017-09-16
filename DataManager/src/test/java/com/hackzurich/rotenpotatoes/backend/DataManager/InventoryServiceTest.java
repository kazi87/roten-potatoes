package com.hackzurich.rotenpotatoes.backend.DataManager;

import java.util.Arrays;
import java.util.Date;

import com.hackzurich.rotenpotatoes.backend.data.GeoInventory;
import com.hackzurich.rotenpotatoes.backend.data.Item;
import com.hackzurich.rotenpotatoes.backend.data.Response;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by kazi on 16.09.17.
 */
public class InventoryServiceTest {

    private InventoryService inventoryService;

    @Before
    public void setUp() {
        inventoryService = new InventoryService();
    }

    @Test
    public void processInputData() throws Exception {
        GeoInventory geoInventory = new GeoInventory();
        geoInventory.setLat(47.376887);
        geoInventory.setLng(8.541694);

        Item item = new Item();
        item.setName("Potatoes");
        item.setQuantity(1.3);
        item.setUnit("kg");

        Item item2 = new Item();
        item2.setName("Milk");
        item2.setQuantity(1);
        item2.setUnit("liter");

        Item item3 = new Item();
        item2.setName("Butter");
        item2.setQuantity(250);
        item2.setUnit("grams");

        geoInventory.setItems(Arrays.asList(item, item2, item3));

        inventoryService.processInputData(geoInventory);
    }

    @Test
    public void getInventory() throws Exception {
        String category = "Potatoes";
        long timestamp = new Date().getTime();

        Response result = inventoryService.getInventory(category, timestamp);

        System.out.println(result);
    }

}