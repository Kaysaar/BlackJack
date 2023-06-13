package com.duszki.blackjack.server.Card;

import java.util.ArrayList;
import java.util.Collections;

import static com.duszki.blackjack.server.Card.Type.*;

public class Deck {
    public static final int STRONGCARD = 10;
    public ArrayList<Card> getCardsInsideDeck() {
        return cardsInsideDeck;
    }

    private final ArrayList<Card> cardsInsideDeck = new ArrayList<>();

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

    public Card getCardFromDeck(){
        if(cardsInsideDeck.isEmpty()){
            return null;
        }
        return cardsInsideDeck.get(0);
    }
}
