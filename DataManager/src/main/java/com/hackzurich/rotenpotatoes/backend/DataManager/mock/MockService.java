package com.hackzurich.rotenpotatoes.backend.DataManager.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.hackzurich.rotenpotatoes.backend.data.GeoInventory;
import com.hackzurich.rotenpotatoes.backend.data.Item;

/**
 * Created by kazi on 17.09.17.
 */
public class MockService {

    private static String[] users = new String[]{"Kacper", "Michal", "Valya", "Alex"};

    private static List<String> items =
        Arrays.asList("Potatoes", "Tomatoes", "Lemons", "Butter", "Onions", "Bananas", "Milk", "Cola", "Tea", "Chicken",
                      "Feta", "Sugar", "Salt", "Oregano");

    private static Double[][]
        geo = new Double[][]{new Double[]{47.388383, 8.517376}, new Double[]{47.393573, 8.507907},
                             new Double[]{47.172590, 8.514571}, new Double[]{47.292590, 8.510571}};

    public static List<GeoInventory> get() {
        List<GeoInventory> result = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < users.length; i++) {
            GeoInventory gi = new GeoInventory();
            gi.setUserId(users[i]);
            long timestamp = new Date().getTime();
            gi.setTimestamp(timestamp);
            gi.setLat(geo[i][0]);
            gi.setLng(geo[i][1]);
            int itemsNumber = r.nextInt(items.size() - 10) + 10;
            gi.setItems(new ArrayList<>());
            for (int j = 0; j < itemsNumber; j++) {
                Collections.shuffle(items);
                Item item = new Item();
                item.setName(items.get(j));
                item.setUnit("g");
                item.setQuantity(r.nextInt(1000));
                item.setExpirationDate(new Date(timestamp + r.nextInt(1000 * 60 * 60 * 10)));
                gi.getItems().add(item);
            }
            result.add(gi);
        }

        return result;
    }

}
