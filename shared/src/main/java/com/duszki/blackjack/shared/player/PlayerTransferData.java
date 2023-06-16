package com.duszki.blackjack.shared.player;

import java.util.ArrayList;
import com.duszki.blackjack.shared.card.Card;

public class PlayerTransferData {

    String name;

    private ArrayList<Card> cards;

    public PlayerTransferData() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards){
        this.cards = cards;
    }

}
