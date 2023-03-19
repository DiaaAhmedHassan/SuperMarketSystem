package com.mycompany.supermarket;

public class Client {
    private int home_number,paid,payment;
    private boolean isGolden;
    
    public Client(int home_number, int paid,int payment, boolean isGolden){
        this.home_number=home_number;
        this.paid=paid;
        this.payment=payment;
        this.isGolden=isGolden;
    }
    
    public void setHomeNumber(int home_number){
        this.home_number=home_number;
    }
    
    public int getHomeNumber(){
        return home_number;
    }
    
    public void setPaid(int paid){
        this.paid=paid;
    }
    
    public int getPaid(){
        return paid;
    }
    
    public void addPayment(int payment){
        this.payment=payment;
    }
    
    public void setIsGolden(boolean isGolden){
        this.isGolden=isGolden;
    }
    
    public boolean getIsGolden(){
        return isGolden;
    }
    
}
