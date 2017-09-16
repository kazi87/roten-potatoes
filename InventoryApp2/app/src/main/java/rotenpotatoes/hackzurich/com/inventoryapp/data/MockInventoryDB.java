package rotenpotatoes.hackzurich.com.inventoryapp.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * Created by kazi on 16.09.17.
 */

public class MockInventoryDB {
    private final List<Item> mockInventory;

    public MockInventoryDB() {
        mockInventory = new ArrayList<>();
        mockInventory.add(new Item("Potatoes", 1.2, "kg", getExpirationDate(30)));
        mockInventory.add(new Item("Tomatoes", 0.5, "kg", getExpirationDate(10)));
        mockInventory.add(new Item("Onions", 0.4, "kg", getExpirationDate(10)));
        mockInventory.add(new Item("Bananas", 2.0, "kg", getExpirationDate(7)));
        mockInventory.add(new Item("Bread", 1, "piece", getExpirationDate(4)));
        mockInventory.add(new Item("Butter", 1, "block", getExpirationDate(30)));
        mockInventory.add(new Item("Milk", 1, "liter", getExpirationDate(10)));
        mockInventory.add(new Item("Tea", 2, "box", getExpirationDate(320)));
        mockInventory.add(new Item("Salomon", 0.3, "kg", getExpirationDate(10)));
        mockInventory.add(new Item("Chicken", 0.55, "kg", getExpirationDate(10)));
        mockInventory.add(new Item("Coca-cola", 2, "liter", getExpirationDate(120)));
        mockInventory.add(new Item("Yogurt", 0.25, "liter", getExpirationDate(10)));
        mockInventory.add(new Item("Creme", 0.1, "liter", getExpirationDate(10)));
        mockInventory.add(new Item("Sugar", 1, "kg", getExpirationDate(120)));
        mockInventory.add(new Item("Flour", 1, "kg", getExpirationDate(120)));
        mockInventory.add(new Item("Salt", 0.5, "kg", getExpirationDate(220)));
        mockInventory.add(new Item("Olives Olive", 1, "liter", getExpirationDate(100)));
        mockInventory.add(new Item("Oil", 0.5, "liter", getExpirationDate(100)));
        mockInventory.add(new Item("Feta", 0.25, "kg", getExpirationDate(10)));
    }

    public List<Item> getRandomList(){
        Random random = new Random();
        int min = 3;
        int listSize = random.nextInt(mockInventory.size() - min) + min;
        List<Item> itemList = mockInventory.subList(0, listSize);
        long seed = System.nanoTime();
        Collections.shuffle(itemList, new Random(seed));
        return  itemList;
    }

    public GeoInventory getGeoInventory(double lat, double lng){
        GeoInventory geoInv = new GeoInventory();
        geoInv.setItems(getRandomList());
        geoInv.setLat(lat);
        geoInv.setLng(lng);
        return geoInv;
    }

    private Date getExpirationDate(int future){
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, future);
        return cal.getTime();
    }

}
