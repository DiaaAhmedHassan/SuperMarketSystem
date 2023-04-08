package com.mycompany.supermarket;

public class Client extends Person {
    private int home_number,paid,payment;
    private boolean isGolden;
    
    public Client(String id, String name, String telephone, int age, Adress adress,
            int home_number, int paid,int payment, boolean isGolden){
        super(id, name, telephone, age, adress);
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
