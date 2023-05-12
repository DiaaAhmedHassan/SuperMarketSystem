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
    private String subDate;//date of initialization saved to CSV as a string
    

    public Client(int id, String name, String telephone, Address adress){
        super(id, name, telephone,adress);
        isGolden=false;
        payments = 0;
    }
    public Client(int id, String name, String telephone, Address adress, String subDate) {
        super(id, name, telephone, adress);
        isGolden = false;
        this.subDate = subDate;
        payments = 0;

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

    public String getSubDate() {
        return subDate;
    }

    public void setSubDate(String subDate) {
        this.subDate = subDate;
    }
    
    

    public double getPayments() {
        return payments;
    }
    
    
    public void setIsGolden(boolean isGolden){
        this.isGolden=isGolden;
    }

    @Override
    public String toString() {
        return getId() + "|"+ getName() +"|"+ getTelephone()+"|"+getAdress()+"|"+getSubDate()+ "|" + getPayments() +"\n";
    }
    public void display()
    {
        NewJFrame.ClientId.setText(String.valueOf(this.getId()));
        NewJFrame.ClientName.setText(this.getName());
        NewJFrame.ClientTelephone.setText(this.getTelephone());
        NewJFrame.AddressStr.setText(String.valueOf(this.getAdress()));
        NewJFrame.ClientSubDate.setText(this.getSubDate());
        
    }
    
        
    //toGolden method:
    //constantly checking for the golden client condition
    public void toGolden()
    {
        
        if(NewJFrame.checkDate(NewJFrame.ClientSubDate.getText(), "client"))
        {
            //initializing the golden client and adding it to the list
            
            GoldenClient newGolden;
            newGolden = new GoldenClient(this.getId(), this.getName(), this.getTelephone(), this.getAdress(), true,NewJFrame.ClientSubDate.getText(), NewJFrame.goldenBirthday.getText(), NewJFrame.goldenFav.getText());
            //NewJFrame.goldenClients.add();
            //writing to the file
            NewJFrame.goldenClients.add(newGolden);
            
            try
            {
            NewJFrame.writer = new BufferedWriter(new FileWriter("goldenData.csv"));
            NewJFrame.writer.write(("id,name,phone,address, subscribtion date, birthday, favorite product\n")+String.valueOf(NewJFrame.goldenClients).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(",", "").replaceAll("\\|", ",").replaceFirst(" ", ""));
            System.out.println("Added to file succefuly");
           
            //catch the excpetion
            }
            catch (IOException ex)
            {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
                try {
                    NewJFrame.writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    
    
   

}
