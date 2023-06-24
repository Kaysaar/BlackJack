package com.duszki.blackjack.shared.events;

public class PlaceBetEvent {

    private int bet;

    public PlaceBetEvent() {

    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getBet() {
        return this.bet;
    }

}
