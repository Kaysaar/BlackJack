package com.duszki.blackjack.shared.events;

public class JoinResponseEvent {

    private boolean success;

    public JoinResponseEvent() {
    }

    public void setSuccess() {
        this.success = true;
    }

    public void setUnsuccess() {
        this.success = false;
    }

}
