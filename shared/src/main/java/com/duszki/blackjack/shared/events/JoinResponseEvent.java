package com.duszki.blackjack.shared.events;

public class JoinResponseEvent {

    private boolean success;

    private String message;

    public JoinResponseEvent() {
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
