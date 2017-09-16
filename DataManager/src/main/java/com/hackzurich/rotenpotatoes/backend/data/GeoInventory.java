package com.hackzurich.rotenpotatoes.backend.data;

import java.util.List;

/**
 * Created by kazi on 16.09.17.
 */
public class GeoInventory {

    //  session id
    private String sessionId;
    private String userId;
    private double lat;
    private double lng;
    private long timestamp;
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

    public void setTimestamp(long timestamp_) {
        this.timestamp = timestamp_;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "GeoInventory{" +
               "sessionId='" + sessionId + '\'' +
               ", userId='" + userId + '\'' +
               ", lat=" + lat +
               ", lng=" + lng +
               ", timestamp=" + timestamp +
               ", items=" + items +
               '}';
    }
}
