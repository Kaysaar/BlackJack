package com.duszki.blackjack.server.player;

import com.duszki.blackjack.server.card.Hand;
import com.duszki.blackjack.server.Request;
import com.duszki.blackjack.server.Response;

public class PlayerServerDataParser {
    private Hand playerHand;
    private int coins;
    public String name ;
    public boolean agreedToPLay = false;

    public PlayerServerDataParser() {
    }
    public int currentTurn = 0;

    public Hand getPlayerHand() {
        return playerHand;
    }
    public  Request requestType;
    public Response serverResponseType;
    public boolean requestedEndTurn = false;

    public void setPlayerHand(Hand playerHand) {
        this.playerHand = playerHand;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }





}
