package com.duszki.blackjack.shared.events;

import java.util.HashMap;

public class RequestCurrRankingEvent {

    public RequestCurrRankingEvent(){

    }

    public HashMap<String, Integer> getPlayersSorted() {
        return playersSorted;
    }

    public void setPlayersSorted(HashMap<String, Integer> playersSorted) {
        this.playersSorted = playersSorted;
    }

    HashMap<String,Integer> playersSorted = new HashMap<>();


}
