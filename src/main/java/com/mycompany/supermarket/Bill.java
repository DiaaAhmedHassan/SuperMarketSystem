package com.mycompany.supermarket;
import java.util.*;

//1. the date is saved as the bill is initialized

public class Bill
{
    Calendar date = new GregorianCalendar();
    //ArrayList<Product> products;
    String[] products;
    double initial_price;
    Staff staff_member;
    String pos_id;

    
    //test constructor (staff is an object from Staff class)
    public Bill(String staff, String pos_id, String...products)
    {
        //this.staff_member = staff;
        this.pos_id = pos_id;
        this.products = products;
    };
    
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
    
    
}
