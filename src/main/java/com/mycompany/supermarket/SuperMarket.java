/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.supermarket;

/**
 *
 * @author DELL
 */
public class SuperMarket {

    public static void main(String[] args) {
           java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
        Bill b1 = new Bill("7oda", "11", "fish", "meat", "chicken", "milk", "eggs");
        b1.printBill();
    }
}
