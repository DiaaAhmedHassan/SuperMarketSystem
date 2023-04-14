package com.mycompany.supermarket;

public class Staff extends Person{
    private double salary;

    public Staff(String id, String name, String telephone, int age, Address adress, double salary) {
        super(id, name, telephone, age, adress);
        this.salary = salary;
    }
    
    
    
    public void setSalary(int salary){
        this.salary=salary;
}
    public double getSalary(){
        return salary;
    }

    @Override
    public String toString() {
        return super.getId()+"|"+super.getName()+"|"+super.getTelephone()+"|"+super.getAge()+"|"+super.getAdress()+"|"+salary+"\n";
    }
    
    
}