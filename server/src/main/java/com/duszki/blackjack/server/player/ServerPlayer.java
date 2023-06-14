package com.duszki.blackjack.server.player;

import com.duszki.blackjack.server.card.Hand;
import com.esotericsoftware.kryonet.Connection;

public class ServerPlayer {
    private Connection connection;
    private String playerName;

    private Hand playerHand;

    private int playerPoints;

    public ServerPlayer(Connection connection, String playerName) {
        this.connection = connection;
        this.playerName = playerName;
    }

    public Connection getConnection() {
        return connection;
    }

//    public void setPlayerServerData(PlayerServerDataParser playerServerDataParser) {
//        this.playerServerDataParser = playerServerDataParser;
//    }

//    private PlayerServerDataParser playerServerDataParser;

//    public static Player init(){
//        Player player = new Player();
//        player.client = new Client();
//        return player;
//    }

//    public PlayerServerDataParser getPlayerServerData() {
//        return playerServerDataParser;
//    }
}
