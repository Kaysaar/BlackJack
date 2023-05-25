package com.duszki.blackjack.server.Card;

public class Card {
    private final int id;
    private final int value;
    private final Type color;

    public Card(int  id, int value, Type color){
        this.id = id;
        this.value = value;
        this.color = color;
    }
    public int getValue() {
        return value;
    }

    public Type getColor() {
        return color;
    }
    public int getId() {
        return id;
    }
}
