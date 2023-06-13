package com.duszki.blackjack.server.Card;
import com.duszki.blackjack.server.Player.PlayerServerDataParser;

public class Dealer extends PlayerServerDataParser {
    public void drawCards(){
        Hand DealerHand = this.getPlayerHand();
        int CurrentPoints = DealerHand.getPoints();
    }

}
