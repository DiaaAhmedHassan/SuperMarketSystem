package com.mycompany.supermarket;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Client extends Person {
    private int home_number;
    private double payments;
    public boolean isGolden;
    private Date c = Calendar.getInstance().getTime();//date of each instance initialization.
    private String dateSubscribed;//date of initialization saved to CSV as a string
    
    public Client(String id, String name, String telephone, int age, Address adress,
            int home_number ,int payment, boolean isGolden){
        super(id, name, telephone, age, adress);
        this.home_number=home_number;
        this.payments=payment;
        this.isGolden=isGolden;
    }
    
    public void setHomeNumber(int home_number){
        this.home_number=home_number;
    }
    
    public int getHomeNumber(){
        return home_number;
    }
    
    public void addPayments(int payment){
        this.payments=payment;
    }

    public double getPayments() {
        return payments;
    }
    
    
    public void setIsGolden(boolean isGolden){
        this.isGolden=isGolden;
    }
    
        
}
