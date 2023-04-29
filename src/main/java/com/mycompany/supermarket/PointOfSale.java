package com.mycompany.supermarket;

import javax.swing.JOptionPane;

public class PointOfSale {
//    public static pocNum = 5;
    private String ID;//p1, p2, p3, p4 or p5
    private Card[] shift1 = new Card[5];
    private Card[] shift2 = new Card[5];
    private Card[] shift3 = new Card[5];

    public PointOfSale() {
    }

    public PointOfSale(String ID) {
        this.ID = ID;
    }
    
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    public void addMember(Card memberCard, int shift)
    {
        Card[] cards = null;
        switch(shift)
        {
            case 1:
                cards = shift1;
            break;
            case 2:
                cards = shift2;
            break;
            case 3:
                cards = shift3;
            break;
        }
        
        for(int i = 0; i < cards.length; i++)
        {
            if(cards[i] == null)
            {
                cards[i] = memberCard;
                break;
            }
            else
            {
                JOptionPane.showMessageDialog(null, "can't add this card", "null object", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    } 
}
