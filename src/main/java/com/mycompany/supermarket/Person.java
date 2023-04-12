package com.mycompany.supermarket;

public abstract class Person
{
    private String id, name, telephone;
    private int age;
    private Address adress;

    public Person(String id, String name, String telephone, int age, Address adress) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.age = age;
        this.adress = adress;
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getAdress() {
        return adress;
    }

    public void setAdress(Address adress) {
        this.adress = adress;
    }
    
    
}
