package com.duszki.blackjack.shared.events;

public class EndOfGameEvent {

    private String username;

    private int conins;

    public EndOfGameEvent() {

    }

    public EndOfGameEvent(String username, int coins) {
        this.username = username;
        this.conins = coins;
    }

    public String getUsername() {
        return this.username;
    }

    public int getCoins() {
        return this.conins;
    }

}
