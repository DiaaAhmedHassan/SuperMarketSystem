/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.supermarket;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
                   //System.out.println(dateDifference("11/11/2011", "11/11/2012"));
               }
           });
           
            //* workflow "decission"
            //1. the program will not be operating in real time
            //we will need to save the time in a file                           *Refused*
            //or
            //2. the program runs in the real time, we have the ability to move forward or backwards
            //in time to be able to see the effects imply on time passing       *Accepted*
            
            //* we need to:
            //check 2 dates equallity: product expiration                   done
            //check the difference between 2 dates: toGolden() condition    done
            //check for a spacific month and day : golden client birthday   done
           
            //* for the main function
            //track the number of "week in year" for the weekly report
            //current date
            
           
           
    }
    public static String format(Date c)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(c);
    }
    public static int dateDifference(String d1, String d2)
    {
        
        int days1 = 0, days2 = 0;//splitting
        String[] parts1 = d1.split("/");
        String[] parts2 = d2.split("/");
        
        //get number of days in each date and compare
        days1 = Integer.parseInt(parts1[0]) + Integer.parseInt(parts1[1]) * 30 + Integer.parseInt(parts1[2]) * 365;
        days2 = Integer.parseInt(parts2[0]) + Integer.parseInt(parts2[1]) * 30 + Integer.parseInt(parts2[2]) * 365;
        System.out.println(days1);
        System.out.println(days2);
        return days1 - days2;
    }
    
}
