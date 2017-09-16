package com.hackzurich.rotenpotatoes.backend.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.hackzurich.rotenpotatoes.backend.data.GeoInventory;
import com.hackzurich.rotenpotatoes.backend.data.Response;
import com.hackzurich.rotenpotatoes.backend.data.Item;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kazi on 16.09.17.
 */
@RestController
public class InventoryController {

    @RequestMapping("/inventory")
    public Response getInventory(@RequestParam String category, @RequestParam long timestamp){
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
        item2.setName("Milk");
        item2.setQuantity(1);
        item2.setUnit("liter");

        geoInventory.setItems(Arrays.asList(item, item));
        geoInv.add(geoInventory);
        inventory.setGeoInventories(geoInv);
        return inventory;
    }

}
