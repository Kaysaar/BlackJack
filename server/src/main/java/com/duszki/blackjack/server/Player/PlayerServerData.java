package com.duszki.blackjack.server.Player;

import com.duszki.blackjack.server.Card.Hand;
import com.duszki.blackjack.server.Request;
import com.duszki.blackjack.server.Response;

public class PlayerServerData {

    private Connection connection;

    private Hand playerHand;
    public boolean agreedToPlay = false;
    private int coins;
    public String playerName;
    public boolean hasLost;
    public boolean hasStand = false;

    public PlayerServerData(Connection connection, String playerName) {
        this.connection = connection;
        this.playerName = playerName;
        this.playerHand = new Hand();
        this.coins = 1000;
        this.hasLost = false;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

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
