package com.duszki.blackjack.server.card;

import java.util.Collections;
import java.util.LinkedList;

import static com.duszki.blackjack.server.card.Type.*;

public class Deck {
    public static final int STRONGCARD = 10;
    public LinkedList<Card> getCardsInsideDeck() {
        return cardsInsideDeck;
    }

    private final LinkedList<Card> cardsInsideDeck = new LinkedList<>();

    public Deck(){
        addCards(Spades);
        addCards(Hearts);
        addCards(Diamonds);
        addCards(Clubs);
        Collections.shuffle(cardsInsideDeck);
    }

    void addCards(Type colors){
        for (int i = 1; i < 11; i++) {
            Card card = new Card(i,i,colors);
            cardsInsideDeck.add(card);
        }
        for (int i = 11; i < 14; i++) {
            Card card = new Card(i,STRONGCARD,colors);
            cardsInsideDeck.add(card);
        }
    }
    public Card removeCard(){
        if(cardsInsideDeck.isEmpty()){
            return null;
        }
        return cardsInsideDeck.poll();
    }
}
