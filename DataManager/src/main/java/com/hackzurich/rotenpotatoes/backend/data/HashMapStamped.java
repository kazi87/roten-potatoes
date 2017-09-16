/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackzurich.rotenpotatoes.backend.data;
import com.hackzurich.rotenpotatoes.backend.data.Item;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author alex
 */
public class HashMapStamped {
    private long timestamp;
    private double lat;
    private double lng;
    private HashMap<String, ArrayList<Item>> map;

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestampe the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the map
     */
    public HashMap<String, ArrayList<Item>> getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(HashMap<String, ArrayList<Item>> map) {
        this.map = map;
    }

    /**
     * @return the lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * @param lat the lat to set
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * @return the lng
     */
    public double getLng() {
        return lng;
    }

    /**
     * @param lng the lng to set
     */
    public void setLng(double lng) {
        this.lng = lng;
    }
    
}
