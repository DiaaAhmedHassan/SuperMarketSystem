/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.supermarket;

import static com.mycompany.supermarket.NewJFrame.ProductBuyingPrice;
import static com.mycompany.supermarket.NewJFrame.ProductID;
import static com.mycompany.supermarket.NewJFrame.expirationDate;
import static com.mycompany.supermarket.NewJFrame.numberItem;
import static com.mycompany.supermarket.NewJFrame.productCategory;
import static com.mycompany.supermarket.NewJFrame.productName;
import static com.mycompany.supermarket.NewJFrame.productSellingPrice;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class Product {
    private int id;
    private String name ,category;
    private double BuyingPrice, sellingPrice;
    private String expirationDate;
    private int  itemNo;

    public Product(int id, String name, String category, double BuyingPrice, double sellingPrice, String expirationDate, int itemNo) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.BuyingPrice = BuyingPrice;
        this.sellingPrice = sellingPrice;
        this.expirationDate = expirationDate;
        this.itemNo = itemNo;
    }

    public int getItemNo() {
        return itemNo;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return id+"|"+name+"|"+category+"|"+BuyingPrice+"|"+sellingPrice+"|"+expirationDate+"|"+itemNo+"\n";
    }
    public void display()
    {
        NewJFrame.ProductID.setText(String.valueOf(this.getId()));
        NewJFrame.productName.setText(this.getName());
        NewJFrame.productCategory.setText(this.getCategory());
        NewJFrame.ProductBuyingPrice.setText(String.valueOf(this.getBuyingPrice()));
        NewJFrame.productSellingPrice.setText(String.valueOf(this.getSellingPrice()));
        NewJFrame.expirationDate.setText(this.getExpirationDate());
        NewJFrame.numberItem.setText(String.valueOf(this.getItemNo()));
    }
    
    


    
    
    
    
}
