package com.duszki.blackjack.server.Player;

import com.esotericsoftware.kryonet.Client;

public class Player {
    public Client getClient() {
        return client;
    }

    private Client client;

    public void setPlayerServerData(PlayerServerDataParser playerServerDataParser) {
        this.playerServerDataParser = playerServerDataParser;
    }

    private PlayerServerDataParser playerServerDataParser;

    public static Player init(){
        Player player = new Player();
        player.client = new Client();
        return player;
    }

    public PlayerServerDataParser getPlayerServerData() {
        return playerServerDataParser;
    }
}
