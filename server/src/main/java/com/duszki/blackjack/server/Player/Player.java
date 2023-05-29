package com.duszki.blackjack.server.Player;

import com.duszki.blackjack.server.Card.Card;
import com.duszki.blackjack.server.Card.Deck;
import com.duszki.blackjack.server.Card.Hand;
import com.duszki.blackjack.server.Card.Shoe;

public class Player {
    private Hand playerHand;
    private int coins;
    public static Player init(int startingCoinsQuantity){
        Player player = new Player();
        player.playerHand = new Hand();
        player.coins = startingCoinsQuantity;

        return player;
    }
    public Hand getPlayerHand() {
        return playerHand;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }


}
