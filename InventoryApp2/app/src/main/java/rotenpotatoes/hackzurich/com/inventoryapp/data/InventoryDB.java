package rotenpotatoes.hackzurich.com.inventoryapp.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * Created by kazi on 16.09.17.
 */

public class InventoryDB {
    private final List<Item> mockInventory;

    public InventoryDB() {
        mockInventory = new ArrayList<>();
        mockInventory.add(new Item("Potatoes", 1.2, "kg", getExpirationDate(30)));
        mockInventory.add(new Item("Tomatoes", 0.5, "kg", getExpirationDate(10)));
        mockInventory.add(new Item("Bananas", 2.0, "kg", getExpirationDate(7)));
        mockInventory.add(new Item("Bread", 1, "piece", getExpirationDate(4)));
        mockInventory.add(new Item("Butter", 1, "block", getExpirationDate(30)));
        mockInventory.add(new Item("Milk", 1, "liter", getExpirationDate(20)));
    }

    public List<Item> getRandomList(){
        Random random = new Random();
        int min = 3;
        int listSize = random.nextInt(mockInventory.size() - min) + min;
        return mockInventory.subList(0, listSize);
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
