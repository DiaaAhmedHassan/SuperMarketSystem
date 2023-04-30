package com.mycompany.supermarket;

import static com.mycompany.supermarket.StaffForm.cardCode;
import static com.mycompany.supermarket.StaffForm.cardId;
import static com.mycompany.supermarket.StaffForm.cardUsername;
import static com.mycompany.supermarket.StaffForm.memberAddress;
import static com.mycompany.supermarket.StaffForm.memberAge;
import static com.mycompany.supermarket.StaffForm.memberId;
import static com.mycompany.supermarket.StaffForm.memberName;
import static com.mycompany.supermarket.StaffForm.memberPhone;
import static com.mycompany.supermarket.StaffForm.memberSalary;

public class Staff extends Person{
    private double salary;
    private int age;
    private Card card;
    


    public Staff(int id, String name, String telephone, int age, Address adress, double salary, Card card) {

        super(id, name, telephone, adress);
        this.salary = salary;
        this.age = age;
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
       
    public void setSalary(int salary){
        this.salary=salary;
}
    public double getSalary(){
        return salary;
    }

    @Override
    public String toString() {
        return super.getId()+"|"+super.getName()+"|"+super.getTelephone()+"|"+this.age+"|"+super.getAdress()+"|"+salary+"|"+card+"\n";
    }
    public void display()
    {
        StaffForm.memberId.setText(String.valueOf(this.getId()));
        StaffForm.memberName.setText(this.getName());
        StaffForm.memberAge.setText(String.valueOf(this.getAge()));
        StaffForm.memberPhone.setText(this.getTelephone());
        StaffForm.memberAddress.setText(this.getAdress().toString());
        StaffForm.memberSalary.setText(String.valueOf(this.getAdress()));
        StaffForm.cardId.setText(String.valueOf(this.getCard().getId()));
        StaffForm.cardUsername.setText(this.getCard().getUsername());
        StaffForm.cardCode.setText(this.getCard().getCode());
    }
    
}