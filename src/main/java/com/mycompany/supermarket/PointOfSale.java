package com.mycompany.supermarket;

public class PointOfSale {
    private String ID;
    private Card cards;

    public PointOfSale() {
    }

    public PointOfSale(String ID, Card cards) {
        this.ID = ID;
        this.cards = cards;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Card getCards() {
        return cards;
    }

    public void setCards(Card cards) {
        this.cards = cards;
    }
    
}
