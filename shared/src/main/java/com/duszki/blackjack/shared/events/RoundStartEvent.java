package com.duszki.blackjack.shared.events;

import com.duszki.blackjack.shared.data.GameUpdateData;

public class RoundStartEvent {

    private GameUpdateData gameUpdateData;

    public RoundStartEvent() {

    }

    public GameUpdateData getGameUpdateData() {
        return gameUpdateData;
    }

    public void setGameUpdateData(GameUpdateData gameUpdateData) {
        this.gameUpdateData = gameUpdateData;
    }

}
