package com.duszki.blackjack.server.Card;
import com.duszki.blackjack.server.Player.PlayerServerData;

public class Dealer extends PlayerServerData {
    public void drawCards(){
        Hand DealerHand = this.getPlayerHand();
        int CurrentPoints = DealerHand.getPoints();

        while(CurrentPoints < 17){
            DealerHand.addCard(); //do zrobienia jak dojdzie funkcjonalnosc serwera
        }
    }

}
