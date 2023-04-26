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
    
    
    public GoldenClient(int id, String name, String telephone, Address adress
            , int payment, boolean isGolden, Date birthday, Product fav_product) {
        super(id, name, telephone, adress);
        this.birthday = birthday;
        this.fav_Product = fav_product;
    }

    public String getBirthday() {
        return SuperMarket.format(this.birthday);
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
    public boolean checkBirthday()
    {
        //return SuperMarket.dateDifference(currentDate, this.getBirthday());
        //waiting for the current date to be difined !!
        //String[] current = currentDate.split("/");
        //String[] birthday = this.getBirthday().split("/");
        //return current[0].equals(birthday[0]) && current[1].equals(birthday[1])
        return false;
    }
}
