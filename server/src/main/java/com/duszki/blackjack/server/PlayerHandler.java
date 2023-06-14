package com.duszki.blackjack.server;

import com.duszki.blackjack.server.player.ServerPlayer;
import com.esotericsoftware.kryonet.Connection;

import java.util.LinkedList;

public class PlayerHandler {

    public static PlayerHandler PLAYER_HANDLER_INSTANCE = new PlayerHandler();

    private LinkedList<ServerPlayer> serverPlayers;

    public PlayerHandler() {
        serverPlayers = new LinkedList<>();
    }

    public void addPlayer(ServerPlayer serverPlayer) {
        serverPlayers.add(serverPlayer);
    }

    public ServerPlayer getPlayerByConnection(Connection connection) {
        for (ServerPlayer serverPlayer : serverPlayers) {
            if (serverPlayer.getConnection() == connection) {
                return serverPlayer;
            }
        }
        return null;
    }

    public void removePlayer(ServerPlayer serverPlayer) {
        serverPlayers.remove(serverPlayer);
    }

    public LinkedList<ServerPlayer> getPlayers() {
        return serverPlayers;
    }

}
