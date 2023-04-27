package com.mycompany.supermarket;

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
    
    
}