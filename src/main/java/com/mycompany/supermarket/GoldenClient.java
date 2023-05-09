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
    String birthday;
    Product favProduct;
    
    
    public GoldenClient(int id, String name, String telephone, Address adress
                        , double payment, boolean isGolden, String birthday, String favorite)
    {
        super(id, name, telephone, adress);
        this.birthday = birthday;
        
        Object found = NewJFrame.find("client", favorite);
        Product pro = (Product) found;
        this.favProduct = pro;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Product getFavProduct() {
        return this.favProduct;
    }

    public void setFavProduct(Product favorite) {
        this.favProduct = favorite;
    }
    
   /*
   ToDo 1-fav_discount method 
        2-birthday discount method
*/
    @Override
    public String toString() {
        return getId() + "|" + getName() + "|" + getTelephone() + "|" + getAdress() + "|" + getSubDate() + "|" + getBirthday() +"|" + getFavProduct().getName() + "\n";
    }
}
