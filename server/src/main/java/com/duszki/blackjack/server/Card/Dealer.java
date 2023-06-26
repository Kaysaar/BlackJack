package com.duszki.blackjack.server.Card;

import com.duszki.blackjack.shared.models.Hand;

public class Dealer {

    private Hand dealerHand;

    public Dealer() {
        this.dealerHand = new Hand();
    }

    public Hand getHand() {
        return dealerHand;
    }

    public void setHand(Hand hand) {
        this.dealerHand = hand;
    }

}
