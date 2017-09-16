package com.hackzurich.rotenpotatoes.backend.DataManager.solr;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;

/**
 * Created by kazi on 16.09.17.
 */
public class SolrRecord {

    @Id
    @Field
    private String id = UUID.randomUUID().toString();

    @Field("userId")
    private String userId;
    @Field("sessionId")
    private String sessionId;
    @Field("lat")
    private double lat;
    @Field("lng")
    private double lng;
    @Field("timestamp")
    private long timestamp;
    @Field("name")
    private String name;
    @Field("quantity")
    private double quantity;
    @Field("unit")
    private String unit;
    @Field("expirationDate")
    private Date expirationDate;
    @Field("labels")
    private List<String> labels;


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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "SolrRecord{" +
               "id='" + id + '\'' +
               ", userId='" + userId + '\'' +
               ", sessionId='" + sessionId + '\'' +
               ", lat=" + lat +
               ", lng=" + lng +
               ", timestamp=" + timestamp +
               ", name='" + name + '\'' +
               ", quantity=" + quantity +
               ", unit='" + unit + '\'' +
               ", expirationDate=" + expirationDate +
               ", labels=" + labels +
               '}';
    }
}
