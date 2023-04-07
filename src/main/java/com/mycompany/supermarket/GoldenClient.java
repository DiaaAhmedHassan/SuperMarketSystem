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
public class GoldenClient extends Client{
    Date birthday;
    Product fav_Product;
    
    
    public GoldenClient(String id, String name, String telephone, int age, Adress adress
            , int homeNumber, int paid, int payment, boolean isGolden, Date birthday, Product fav_product) {
        super(id, name, telephone, age, adress, homeNumber, paid, payment, isGolden);
        this.birthday = birthday;
        this.fav_Product = fav_product;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Product getFav_Product() {
        return fav_Product;
    }

    public void setFav_Product(Product fav_Product) {
        this.fav_Product = fav_Product;
    }
    
   /*
   ToDo 1-fav_discount method 
        2-birthday discount method
*/
    
}
