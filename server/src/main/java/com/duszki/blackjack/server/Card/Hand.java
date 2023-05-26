package com.duszki.blackjack.server.Card;

import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> cardsInHand;

    private int points;
    public Hand(){
        cardsInHand = new ArrayList<>();
        points = 0;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    void addCard(Card card){
        cardsInHand.add(card);

        if(card.getId()==1 && (this.points + 11 < 22)){
            card.setValue(11);
        }
        else if(this.points + card.getValue() > 21){
            for(Card cardd : this.cardsInHand){
                if(cardd.getId()==1){
                    card.setValue(1);
                }
                this.points -= 10;
                break;
            }
        }
        this.points += card.getValue();
    }

    void removeAll(){
        cardsInHand.clear();
    }
}
