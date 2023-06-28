package com.duszki.blackjack.shared.events;

public class RequestCurrNumberOfPlayersInLobbyEvent {
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    int amount;

}
