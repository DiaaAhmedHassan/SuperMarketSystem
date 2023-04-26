package com.mycompany.supermarket;

public class Card {
    private String cardId;
    private String cardUsername;
    private String code;
    

    public Card(String cardId, String name, String code) {
        this.cardId = cardId;
        this.cardUsername = name;
        this.code = code;
        
    }

    public String getId() {
        return cardId;
    }

    public void setId(String id) {
        this.cardId = id;
    }

    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return cardUsername;
    }

    public void setUsername(String username) {
        this.cardUsername = username;
    }

    @Override
    public String toString() {
        return cardId+"-"+getUsername()+"-"+ code;
    }
    
    
    

    
    
    
}
