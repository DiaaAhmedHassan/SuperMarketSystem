package com.mycompany.supermarket;
import java.util.*;

//1. the date is saved as the bill is initialized

public class Bill
{
    Calendar date = new GregorianCalendar();
    //ArrayList<Product> products;
    Client client;
    String[] products;
    double initialPrice;
    Staff staffMember;
    String posId;

    
    //test constructor
    //client => Client class
    //staff => Staff class
    public Bill(Client client, String staff, String pos_id, String...products)
    {
        this.client = client;
        //this.staff_member = staff;
        this.posId = pos_id;
        this.products = products;
    }
    
    private double getInitialPrice()
    {
        double price = 0;
        for(String product : products)
        {
            //price += product.sellingPrice;
        }
        return price;
    }
    public void print_bill()
    {
        for(String product : products)
        {
            System.out.println(product);
        }
    }
    public void getFinalPrice()
    {
        if(client.isGolden)
        {
            /////////
        }else{
            if(client.getPayments() >= 4000)
            {
                discount_5(initialPrice);
            }
        }
    }
    public static double discount_5(double initial_cost)
    {
        double new_cost = 0;
        if(initial_cost >= 4000)
        {
            new_cost = initial_cost * 0.05;
        }
        return new_cost;
    }
    
    
}
