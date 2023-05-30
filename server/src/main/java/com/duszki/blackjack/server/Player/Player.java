package com.duszki.blackjack.server.Player;

import com.duszki.blackjack.server.Card.Hand;
import com.esotericsoftware.kryonet.Client;

public class Player {
    public Client getClient() {
        return client;
    }

    private Client client;

    public void setPlayerServerData(PlayerServerData playerServerData) {
        this.playerServerData = playerServerData;
    }

    private PlayerServerData playerServerData;

    public static Player init(){
        Player player = new Player();
        player.client = new Client();
        return player;
    }

    public PlayerServerData getPlayerServerData() {
        return playerServerData;
    }
}
