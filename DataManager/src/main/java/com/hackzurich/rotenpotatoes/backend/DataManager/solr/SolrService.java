package com.hackzurich.rotenpotatoes.backend.DataManager.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
 * Created by kazi on 16.09.17.
 */
public class SolrService {

    HttpSolrClient solrClient;

    public SolrService() {
        solrClient = new HttpSolrClient("http://localhost:8983/solr/food_core");
    }

    public void index(SolrRecord record) throws IOException, SolrServerException {
        solrClient.addBean(record);
        solrClient.commit();

    }

    public List<SolrRecord> search(String searchTerm, long timestamp) {
        searchTerm = searchTerm.toLowerCase();
        long startTime = Math.max(timestamp - 1000 * 60 * 60 * 24, 0);
        SolrQuery query = new SolrQuery();
        query.setQuery(
            "(name:*" + searchTerm + "* OR labels:*" + searchTerm + "*) AND timestamp:[" + startTime + " TO "
            + timestamp + "]");
        query.setSort(SolrQuery.SortClause.desc("timestamp"));
        query.setStart(0);
        query.setRows(100);
        query.set("defType", "edismax");

//        query.addGetFieldStatistics("quantity");
//        query.setGetFieldStatistics(true);
        QueryResponse response = null;
        try {
            response = solrClient.query(query);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        SolrDocumentList results = response.getResults();
        List<SolrRecord> result = new ArrayList<>();
        for (int i = 0; i < results.size(); ++i) {
            SolrDocument element = results.get(i);
            SolrRecord sr = new SolrRecord();
            sr.setName(String.valueOf(element.get("name")));
            sr.setLng((Double) element.get("lng"));
            sr.setLat((Double) element.get("lat"));
            sr.setQuantity((Double) element.get("quantity"));
            sr.setUnit(String.valueOf(element.get("unit")));
            sr.setUserId(String.valueOf(element.get("userId")));
            sr.setExpirationDate(new Date((Long) element.get("timestamp")));
            System.out.println("Return solrRecord: " + results.get(i));
            result.add(sr);
        }
        return result;

    }

}
