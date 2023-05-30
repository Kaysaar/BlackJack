package com.duszki.blackjack.server.Player;

import com.duszki.blackjack.server.Card.Hand;

public class PlayerServerData {
    private Hand playerHand;
    private int coins;

    public Hand getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(Hand playerHand) {
        this.playerHand = playerHand;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }





}
