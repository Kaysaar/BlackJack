package com.duszki.blackjack.server.Card;

import com.duszki.blackjack.server.Player.Player;
import com.duszki.blackjack.shared.models.Hand;

public class Dealer extends Player {

    private Hand dealerHand;

    public Dealer() {
        this.dealerHand = new Hand();
    }

    public Hand getHand() {
        return dealerHand;
    }
}
