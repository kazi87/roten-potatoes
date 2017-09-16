package rotenpotatoes.hackzurich.com.inventoryapp.data;

import java.util.List;

/**
 * Created by kazi on 16.09.17.
 */

public class GeoInventory {
    private long timestamp;
    private double lat;
    private double lng;
    private List<Item> items;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "GeoInventory{" +
                "timestamp=" + timestamp +
                ", lat=" + lat +
                ", lng=" + lng +
                ", items=" + items +
                '}';
    }
}
