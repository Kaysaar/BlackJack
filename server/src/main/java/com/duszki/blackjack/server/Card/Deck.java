package com.duszki.blackjack.server.Card;

import com.duszki.blackjack.shared.card.Card;

import java.util.LinkedList;
import java.util.List;

public class Deck {

    public LinkedList<Card> getCardsInDeck() {
        return cardsInDeck;
    }

    private LinkedList<Card> cardsInDeck;

    public Deck() {

        cardsInDeck = new LinkedList<>();

        List<String> cardRanks = Card.getValidRanks();
        List<String> cardSuits = Card.getValidSuits();

        for (String suit : cardSuits) {
            for (String rank : cardRanks) {
                cardsInDeck.add(new Card(suit, rank));
            }
        }


    }
    public Card removeCard(){
        if(cardsInDeck.isEmpty()){
            return null;
        }
        return cardsInDeck.poll();
    }
}
