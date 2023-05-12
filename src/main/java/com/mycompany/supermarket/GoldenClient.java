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
    String favProduct;
    
    
    public GoldenClient(int id, String name, String telephone, Address adress
                       , boolean isGolden,String subdate, String birthday, String favorite)
    {
        super(id, name, telephone, adress, subdate);
        this.birthday = birthday;
        
        Object found = NewJFrame.find("client", favorite);
        this.favProduct = favorite;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFavProduct() {
        return this.favProduct;
    }

    public void setFavProduct(String favorite) {
        this.favProduct = favorite;
    }
    
    public void displayGolden(){
        super.display();
        NewJFrame.goldenBirthday.setText(birthday);
        NewJFrame.goldenFav.setText(favProduct);
        
    }
    
   /*
   ToDo 1-fav_discount method 
        2-birthday discount method
*/
    @Override
    public String toString() {
        return getId() + "|" + getName() + "|" + getTelephone() + "|" + getAdress() + "|" + getSubDate() + "|" + getBirthday() +"|" + getFavProduct()+ "\n";
    }
}
