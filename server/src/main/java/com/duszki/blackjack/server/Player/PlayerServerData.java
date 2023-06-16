package com.duszki.blackjack.server.Player;

import com.duszki.blackjack.server.Card.Hand;
import com.esotericsoftware.kryonet.Connection;

public class PlayerServerData {

    private Connection connection;

    private Hand playerHand;
    private boolean agreedToPlay = false;
    private int tokens;
    private String playerName;
    private boolean hasLost;
    private boolean hasStand;

    private int stake; // stake is the amount of tokens the player bets in a round

    public PlayerServerData(Connection connection, String playerName) {
        this.connection = connection;
        this.playerName = playerName;
//        this.playerHand = new Hand();
//        this.coins = 1000;
        this.hasStand = false;
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

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public void setHasStand(boolean hasStand) {
        this.hasStand = hasStand;
    }

    public boolean getHasStand() {
        return this.hasStand;
    }

    public void setHasLost(boolean hasLost) {
        this.hasLost = hasLost;
    }

    public boolean getHasLost() {
        return this.hasLost;
    }

    public int getStake() {
        return stake;
    }

    public void setStake(int i) {
        this.stake = i;
    }
}
