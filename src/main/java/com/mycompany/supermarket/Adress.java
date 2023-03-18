package com.mycompany.supermarket;

public class Adress
{
    private String town, street;
    private int homeNumber;

    public Adress(String town, String street, int homeNumber) {
        this.town = town;
        this.street = street;
        this.homeNumber = homeNumber;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(int homeNumber) {
        this.homeNumber = homeNumber;
    }
    
    
}
