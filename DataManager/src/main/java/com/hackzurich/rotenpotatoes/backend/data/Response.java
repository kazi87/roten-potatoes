package com.hackzurich.rotenpotatoes.backend.data;

import java.util.Date;
import java.util.List;

/**
 * Created by kazi on 16.09.17.
 */
public class Response {

    private long timestamp;
    private List<GeoInventory> geoInventories;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<GeoInventory> getGeoInventories() {
        return geoInventories;
    }

    public void setGeoInventories(List<GeoInventory> geoInventories) {
        this.geoInventories = geoInventories;
    }
}
