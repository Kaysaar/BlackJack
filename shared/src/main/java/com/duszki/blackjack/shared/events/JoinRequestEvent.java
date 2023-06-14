package com.duszki.blackjack.shared.events;

public class JoinRequestEvent {

    private String playerName;

    JoinRequestEvent(){

    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}
