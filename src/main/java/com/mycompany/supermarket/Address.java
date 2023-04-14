package com.mycompany.supermarket;

public class Address
{
    private String town, street;
    private int homeNumber;

    public Address(int homeNumber, String street,String town ) {
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

    @Override
    public String toString() {
        return street+"-"+town+"-"+homeNumber;
    }
    
    
    
    
}
