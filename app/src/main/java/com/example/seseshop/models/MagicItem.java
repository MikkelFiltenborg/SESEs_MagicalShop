package com.example.seseshop.models;

import java.io.Serializable;

public class MagicItem implements Serializable
{
    private int itemId;
    private int itemAmount;
    private double itemCost;
    private boolean itemAvailable = false; //Set to true if amount is 1 or more.
    private String itemName;
    private String itemDesc;
    private String itemImg; //img path, url, or resource name.

    public MagicItem(
            int itemId,
            int itemAmount,
            double itemCost,
            String itemName,
            String itemDesc,
            String itemImg) {
        this.itemId = itemId;
        this.itemAmount = itemAmount;
        this.itemCost = itemCost;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.itemImg = itemImg;
        this.itemAvailable = itemAmount > 0;
    }


    //    Id of the magical item.
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    //    Amount of magical items.
    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
        this.itemAvailable = itemAmount > 0;
    }

    //    Price of the magical item.
    public double getItemCost() {
        return itemCost;
    }

    public void setItemCost(double itemCost) {
        this.itemCost = itemCost;
    }

    //    Is magical item in Stock?
    public boolean isItemAvailable() {
        return itemAvailable;
    }

    public void setItemAvailable(boolean itemAvailable) {
        this.itemAvailable = itemAvailable;
    }

    //    Name of the magical item.
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    //    Description of the magical item.
    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    //    Image of the magical item.
    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }
}
