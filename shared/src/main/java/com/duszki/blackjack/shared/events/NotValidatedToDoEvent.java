package com.duszki.blackjack.shared.events;

public class NotValidatedToDoEvent {
    public String message;

    public String getMessage() {
        return message;
    }

    public int status; //0 - cant bet anymore

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
