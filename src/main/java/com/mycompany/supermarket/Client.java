package com.mycompany.supermarket;

import static com.mycompany.supermarket.NewJFrame.AddressStr;
import static com.mycompany.supermarket.NewJFrame.ClientId;
import static com.mycompany.supermarket.NewJFrame.ClientName;
import static com.mycompany.supermarket.NewJFrame.ClientTelephone;
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
    

    public Client(int id, String name, String telephone, Address adress){
        super(id, name, telephone,adress);
        isGolden=false;
    
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

    @Override
    public String toString() {
        return getId() + "|"+ getName() +"|"+ getTelephone()+"|"+getAdress()+"\n";
    }
    public void display()
    {
        NewJFrame.ClientId.setText(String.valueOf(this.getId()));
        NewJFrame.ClientName.setText(this.getName());
        NewJFrame.ClientTelephone.setText(this.getTelephone());
        NewJFrame.AddressStr.setText(String.valueOf(this.getAdress()));
    }
    
        
    //toGolden method:
    //constantly checking for the golden client condition
    //when true a message asking for the client's birthday and favProduct is prompted
}
