package com.duszki.blackjack.server.card;
import com.duszki.blackjack.server.player.PlayerServerDataParser;

public class Dealer extends PlayerServerDataParser {
    public void drawCards(){
        Hand DealerHand = this.getPlayerHand();
        int CurrentPoints = DealerHand.getPoints();
    }

}
