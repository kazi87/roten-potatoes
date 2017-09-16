package com.hackzurich.rotenpotatoes.backend.DataManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kazi on 16.09.17.
 */
public class LabelService {

    private Map<String, List<String>> mock;

    public LabelService() {
        mock = new HashMap<>();
        mock.put("potatoes", Arrays.asList("vegatable", "healthy food", "cool"));
        mock.put("tomatoes", Arrays.asList("vegatable", "healthy food", "red"));
        mock.put("lemons", Arrays.asList("fruit", "yellow", "healthy food"));
        mock.put("butter", Arrays.asList("oil", "fat", "unhealthy food"));
        mock.put("onions", Arrays.asList("vegatable", "unhealthy food"));
        mock.put("bananas", Arrays.asList("fruid", "yellow", "healthy", "musli"));
        mock.put("milk", Arrays.asList("drink", "white", "cow", "butter"));
        mock.put("cola", Arrays.asList("drink", "unhealthy food", "dark", "pepsi"));
        mock.put("tea", Arrays.asList("drink", "green", "black", "leaf"));
        mock.put("chicken", Arrays.asList("food", "animal", "breast", "grill"));
        mock.put("chicken", Arrays.asList("food", "animal", "breast", "grill"));
        mock.put("feta", Arrays.asList("cheese", "salat", "olives", "milk"));
    }

    public List<String> getLabels(String searchTerm) {
        if (mock.containsKey(searchTerm.toLowerCase())) {
            return mock.get(searchTerm.toLowerCase());
        }
        return Collections.emptyList();
    }

}
