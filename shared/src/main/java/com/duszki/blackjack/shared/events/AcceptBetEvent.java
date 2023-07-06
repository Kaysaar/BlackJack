package com.duszki.blackjack.shared.events;

public class AcceptBetEvent {

    boolean accept;

    public AcceptBetEvent() {
    }

    public AcceptBetEvent(boolean accept) {
        this.accept = accept;
    }

    public boolean isAccepted() {
        return accept;
    }

}
