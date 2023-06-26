package com.duszki.blackjack.shared.data;

import com.duszki.blackjack.shared.models.Hand;

public class PlayerData {

    private Hand hand;

    private int tokens;

    private int bet;

    private String name;

    public PlayerData() {

    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
