package com.example.finalfurnishings.DataObjects;

import com.google.gson.annotations.SerializedName;

public class Listing {
    @SerializedName("item_id")
    private int itemId;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private double price;

    @SerializedName("description")
    private String description;

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Item No. " + itemId + ": " + name + "\n");
        sb.append("Price: " + price + "\n");
        if (description.length() > 0) sb.append("Description: " + description + "\n");
        return sb.toString();
    }
}
