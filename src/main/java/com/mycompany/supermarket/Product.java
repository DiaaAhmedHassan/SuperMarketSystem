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
    private String expirationDate;
    private static int  itemNo;

    public Product(String id, String name, String category, double BuyingPrice, double sellingPrice, String expirationDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.BuyingPrice = BuyingPrice;
        this.sellingPrice = sellingPrice;
        this.expirationDate = expirationDate;
        itemNo++;
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

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    public String weeklyTopItems(){
        //to do return weekly top sold items
        return "";
    }
    
    public int decreaseItemNo(){
        return itemNo--;
    }
    
    public double weeklyDiscount(){
        //todo weekly discount code
        return 0;
    }

    @Override
    public String toString() {
        return id+"/"+name+"/"+category+"/"+BuyingPrice+"/"+sellingPrice+"/"+expirationDate+"\n";
    }
    
    


    
    
    
    
}
