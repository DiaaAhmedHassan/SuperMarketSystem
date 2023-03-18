/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.supermarket;

import java.util.Date;

/**
 *
 * @author DELL
 */
public class Product {
    private String id, name ,category;
    private double BuyingPrice, sellingPrice;
    private Date expirationDate;
    private static int  ITEMS_NUMBER;

    public Product(String id, String name, String category, double BuyingPrice, double sellingPrice, Date expirationDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.BuyingPrice = BuyingPrice;
        this.sellingPrice = sellingPrice;
        this.expirationDate = expirationDate;
        ITEMS_NUMBER++;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getBuyingPrice() {
        return BuyingPrice;
    }

    public void setBuyingPrice(double BuyingPrice) {
        this.BuyingPrice = BuyingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }


    
    
    
    
}
