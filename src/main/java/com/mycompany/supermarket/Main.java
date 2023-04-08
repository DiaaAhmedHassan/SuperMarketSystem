/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.supermarket;

/**
 *
 * @author DELL
 */
public class Main {

    public static void main(String[] args) {
        Bill b1 = new Bill("7oda", "11", "fish", "meat", "chicken", "milk", "eggs");
        
        b1.print_bill();
    }
}
