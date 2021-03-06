package com.hackzurich.rotenpotatoes.backend.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kazi on 16.09.17.
 */
public class GeoInventory {

    private double lat;
    private double lng;
    private long timestamp;
    private ArrayList<Item> items=new ArrayList<Item>();

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

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
    
    public void appendItem(Item item){
        this.items.add(item);
    }
    
    public long getTimestamp(){
        return timestamp;
    }
    
    public void setTimestamp(long timestamp_){
        this.timestamp=timestamp_;
    }
    

    @Override
    public String toString() {
        return "GeoInventory{" +
               "lat=" + lat +
               ", lng=" + lng +
               ", items=" + items +
               '}';
    }
}
