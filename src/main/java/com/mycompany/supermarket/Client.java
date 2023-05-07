package com.mycompany.supermarket;

import static com.mycompany.supermarket.NewJFrame.AddressStr;
import static com.mycompany.supermarket.NewJFrame.ClientId;
import static com.mycompany.supermarket.NewJFrame.ClientName;
import static com.mycompany.supermarket.NewJFrame.ClientTelephone;
import static com.mycompany.supermarket.NewJFrame.clientResult;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Person {
    private int home_number;
    private double payments;
    public boolean isGolden;
    private String dateSubscribed;//date of initialization saved to CSV as a string
    

    public Client(int id, String name, String telephone, Address adress){
        super(id, name, telephone,adress);
        isGolden=false;
    
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

    public String getDateSubscribed() {
        return dateSubscribed;
    }

    public void setDateSubscribed(String dateSubscribed) {
        this.dateSubscribed = dateSubscribed;
    }
    
    

    public double getPayments() {
        return payments;
    }
    
    
    public void setIsGolden(boolean isGolden){
        this.isGolden=isGolden;
    }

    @Override
    public String toString() {
        return getId() + "|"+ getName() +"|"+ getTelephone()+"|"+getAdress()+"|"+dateSubscribed+"\n";
    }
    public void display()
    {
        NewJFrame.ClientId.setText(String.valueOf(this.getId()));
        NewJFrame.ClientName.setText(this.getName());
        NewJFrame.ClientTelephone.setText(this.getTelephone());
        NewJFrame.AddressStr.setText(String.valueOf(this.getAdress()));
        NewJFrame.ClientSubDate.setText(this.getDateSubscribed());
        
    }
    
        
    //toGolden method:
    //constantly checking for the golden client condition
    public void toGolden()
    {
        
        if(NewJFrame.checkDate(NewJFrame.ClientSubDate.getText(), "client"))
        {
            //initializing the golden client and adding it to the list
            GoldenClient newGolden;
            newGolden = new GoldenClient(this.getId(), this.getName(), this.getTelephone(), this.getAdress(), this.getPayments(), true, NewJFrame.goldenBirthday.getText(), NewJFrame.goldenFav.getText());
            //NewJFrame.goldenClients.add();
            //writing to the file
            NewJFrame.goldenClients.add(newGolden);
            
            try
            {
            NewJFrame.writer = new BufferedWriter(new FileWriter("goldenData.csv"));
            NewJFrame.writer.write(("id,name,phone,address, subscribtion date, birthday, favorite product\n")+String.valueOf(NewJFrame.goldenClients).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(",", "").replaceAll("\\|", ","));
            NewJFrame.writer.close();
            System.out.println("Added to file succefuly");
           
            //catch the expetion
            }
            catch (IOException ex)
            {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
    }

}
