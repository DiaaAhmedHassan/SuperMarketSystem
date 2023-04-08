package com.mycompany.supermarket;

public class Client extends Person {
    private int home_number;
    private double payments;
    public boolean isGolden;
    
    public Client(String id, String name, String telephone, int age, Adress adress,
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
    
//    public boolean getIsGolden(){
//        return isGolden;
//    }
    
}
