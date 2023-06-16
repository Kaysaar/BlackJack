package com.duszki.blackjack.server.Card;

public class Dealer {

    private Hand dealerHand;

    public Dealer() {
        this.dealerHand = new Hand();
    }

    public Hand getHand() {
        return dealerHand;
    }
}
