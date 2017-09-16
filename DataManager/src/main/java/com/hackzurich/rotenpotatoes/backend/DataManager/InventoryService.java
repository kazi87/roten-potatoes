package com.hackzurich.rotenpotatoes.backend.DataManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.hackzurich.rotenpotatoes.backend.DataManager.solr.SolrRecord;
import com.hackzurich.rotenpotatoes.backend.DataManager.solr.SolrService;
import com.hackzurich.rotenpotatoes.backend.data.GeoInventory;
import com.hackzurich.rotenpotatoes.backend.data.Item;
import com.hackzurich.rotenpotatoes.backend.data.Response;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by kazi on 16.09.17.
 */
public class InventoryService {

    @Autowired
    private SolrService solrService;

    @Autowired
    private LabelService labelService;

    public void processInputData(GeoInventory inventory) {
        String uid = UUID.randomUUID().toString();
        for (Item item : inventory.getItems()) {
            SolrRecord record = new SolrRecord();
            record.setSessionId(uid);
            record.setLat(inventory.getLat());
            record.setLng(inventory.getLng());
            record.setName(item.getName());
            record.setQuantity(item.getQuantity());
            record.setUnit(item.getUnit());
            record.setTimestamp(inventory.getTimestamp());
            record.setUserId(inventory.getUserId());
            //  FIXME: Implement and use expiration service
            record.setExpirationDate(null);

            List<String> labels = labelService.getLabels(item.getName());
            System.out.println(
                "Return labels for item: " + item.getName() + ": " + Arrays.toString(labels.toArray(new String[0])));
            record.setLabels(labels);
            try {
                solrService.index(record);
                System.out.println("Indexed a solar record:" + record);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Response getInventory(String searchTerm, long timestamp) {
        Response response = new Response();

        List<SolrRecord> records = solrService.search(searchTerm, timestamp);

        if (records == null) {
            return response;
        }

        List<GeoInventory> geoInventories = new ArrayList<>();
        for (SolrRecord record : records) {

            if (ignoreRecord(geoInventories, record)) {
                continue;
            }

            GeoInventory geoInventory = null;
            // search for an existing GeoLocation with given Lat/Lgn
            for (GeoInventory gi : geoInventories) {
                if (gi.getLat() == record.getLat() && gi.getLng() == record.getLng()) {
                    geoInventory = gi;
                    break;
                }
            }
            if (geoInventory == null) {
                geoInventory = new GeoInventory();
            }
            geoInventory.setLng(record.getLng());
            geoInventory.setLat(record.getLat());
            geoInventory.setUserId(record.getUserId());
            geoInventory.setTimestamp(record.getTimestamp());
            geoInventory.setSessionId(record.getSessionId());

            Item item = new Item();
            item.setName(record.getName());
            item.setQuantity(record.getQuantity());
            item.setUnit(record.getUnit());
            item.setExpirationDate(record.getExpirationDate());

            if(geoInventory.getItems() == null){
                geoInventory.setItems(new ArrayList<>());
            }
            geoInventory.getItems().add(item);
            geoInventories.add(geoInventory);
        }
        response.setGeoInventories(geoInventories);

        return response;
    }

    private boolean ignoreRecord(List<GeoInventory> geoInventories, SolrRecord record) {
        for (GeoInventory gi : geoInventories) {
            if (gi.getUserId() != null && gi.getUserId().equalsIgnoreCase(record.getUserId()) && gi.getSessionId() != record.getSessionId()) {
                System.out.println("Ignore old users record:" + gi.getUserId());
                return true;

            }
        }
        return false;
    }
}
