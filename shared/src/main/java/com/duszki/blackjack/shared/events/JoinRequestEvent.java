package com.duszki.blackjack.shared.events;

public class JoinRequestEvent {

    private String playerName;

    public JoinRequestEvent(String playerName) {
        this.playerName = playerName;

    }

    public JoinRequestEvent() {

    }

    public String getPlayerName() {
        return playerName;
    }

}
