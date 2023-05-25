package com.duszki.blackjack.server.Card;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static com.duszki.blackjack.server.Card.Type.*;

public class Deck {
    public static final int STRONGCARD = 10;
    private ArrayList<Card> deck;

    public Deck(){
        deck = new ArrayList<>();
        addCards(Spades);
        addCards(Hearts);
        addCards(Diamonds);
        addCards(Clubs);
        Collections.shuffle(deck);
    }

    void addCards(Type colors){
        for (int i = 1; i < 11; i++) {
            Card card = new Card(i,i,colors);
            deck.add(card);
        }
        for (int i = 11; i < 14; i++) {
            Card card = new Card(i,STRONGCARD,colors);
            deck.add(card);
        }
    }
}
