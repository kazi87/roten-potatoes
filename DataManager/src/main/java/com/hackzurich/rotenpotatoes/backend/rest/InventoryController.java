package com.hackzurich.rotenpotatoes.backend.rest;

import com.hackzurich.rotenpotatoes.backend.DataManager.InventoryService;
import com.hackzurich.rotenpotatoes.backend.data.GeoInventory;
import com.hackzurich.rotenpotatoes.backend.data.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kazi on 16.09.17.
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "true", allowedHeaders = "*")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @RequestMapping(value = "/inventory", method = RequestMethod.GET)
    public Response getInventory(@RequestParam String category, @RequestParam long timestamp) {
        return inventoryService.getInventory(category, timestamp);
    }


    @RequestMapping(value = "/inventory", method = RequestMethod.POST)
    public ResponseEntity<Object> processInventory(@RequestBody GeoInventory inventory) {
        System.out.println("Received geoInventory: " + inventory);
        if (inventory == null || inventory.getItems() == null) {
            return ResponseEntity.badRequest().body("Null Body");
        }
        inventoryService.processInputData(inventory);
        return ResponseEntity.noContent().build();
    }

}
