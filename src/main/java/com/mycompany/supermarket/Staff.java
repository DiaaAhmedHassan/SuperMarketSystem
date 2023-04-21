package com.mycompany.supermarket;

public class Staff extends Person{
    private double salary;
    private int age;

    public Staff(String id, String name, String telephone, int age, Address adress, double salary) {
        super(id, name, telephone, adress);
        this.salary = salary;
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
        return super.getId()+"|"+super.getName()+"|"+super.getTelephone()+"|"+this.age+"|"+super.getAdress()+"|"+salary+"\n";
    }
    
    
}