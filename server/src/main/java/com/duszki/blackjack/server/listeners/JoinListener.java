package com.duszki.blackjack.server.listeners;

import com.duszki.blackjack.shared.events.JoinResponseEvent;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import com.duszki.blackjack.shared.events.JoinRequestEvent;
import com.duszki.blackjack.server.player.ServerPlayer;
public class JoinListener implements Listener {

    @Override
    public void received(Connection connection, Object object) {

        if(object instanceof JoinRequestEvent){

            JoinRequestEvent joinRequestEvent = (JoinRequestEvent) object;

            ServerPlayer serverPlayer = new ServerPlayer(connection, joinRequestEvent.getPlayerName());

            JoinResponseEvent joinResponseEvent = new JoinResponseEvent();

            connection.sendTCP(joinResponseEvent);


        }

    }


}
