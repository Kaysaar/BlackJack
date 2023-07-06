package com.duszki.blackjack.shared.events;

import java.util.HashMap;

public class CurrentRoundWinnings {

    private HashMap<String, Integer> currentWinnings;

    public CurrentRoundWinnings() {

    }

    public HashMap<String, Integer> getCurrentWinnings() {
        return currentWinnings;
    }

    public void setCurrentWinnings(HashMap<String, Integer> currentWinnings) {
        this.currentWinnings = currentWinnings;
    }


}
