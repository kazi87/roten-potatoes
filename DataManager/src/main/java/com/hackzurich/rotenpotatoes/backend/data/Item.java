package com.hackzurich.rotenpotatoes.backend.data;

/**
 * Created by kazi on 16.09.17.
 */
public class Item {
    private String name;
    private double quantity;
    private String unit;

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
}
