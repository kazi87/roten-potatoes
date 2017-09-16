package rotenpotatoes.hackzurich.com.inventoryapp.data;

import java.util.Date;

/**
 * Created by kazi on 16.09.17.
 */

public class Article {
    private String name;
    private double quantity;
    private String unit;
    private Date expirationDate;

    public Article(String name, double quantity, String unit, Date expirationDate) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }
}
