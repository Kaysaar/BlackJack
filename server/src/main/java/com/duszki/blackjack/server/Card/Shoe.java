package com.duszki.blackjack.server.Card;

import java.util.ArrayList;

public class Shoe {
    private ArrayList<Deck> deckList = new ArrayList<>();

    public Shoe(){
        int deckQuantity = 8;
        for(int i = 0; i < deckQuantity; i++){
            Deck deck = new Deck();
            deckList.add(deck);
        }
    }
    Deck getFirstAvailableDeck(){
        if(deckList.isEmpty()){
            return null;
        }
        return deckList.get(0);

    }
    void removeEmptyDeck(Deck currDeck){
        if(currDeck.getCardsInsideDeck().isEmpty()){
            deckList.remove(currDeck);
        }
    }
    public Deck replaceDeckIfNeeded(Deck currDeck){
        removeEmptyDeck(currDeck);
        if(deckList.isEmpty()){
            return null;
        }
        return getFirstAvailableDeck();
    }



}
