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
               @Override
               public void run() {
                   new NewJFrame().setVisible(true);
               }
           });
           
           //- the program will not be operating in real time
           //we will need to save the time in a file
           //or
           //- the program runs in the real time, we have the ability to move forward or backwards
           //in time to be able to see the effects imply on time passing
           //-------------------
           //we need to
           //check 2 dates equallity: product expiration
           //check the difference between 2 dates: toGolden() condition
           //check for a spacific month and day : golden client birthday
           //check the number of the week in year for the weekly report
           
           
    }
}
