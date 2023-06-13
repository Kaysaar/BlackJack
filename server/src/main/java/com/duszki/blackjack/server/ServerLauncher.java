package com.duszki.blackjack.server;

import com.duszki.blackjack.server.Card.Card;
import com.duszki.blackjack.server.Card.Deck;
import com.duszki.blackjack.server.Card.Hand;
import com.duszki.blackjack.server.Card.Shoe;
import com.duszki.blackjack.server.Player.Player;
import com.duszki.blackjack.server.Player.PlayerServerDataParser;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Launches the server application.
 */
public class ServerLauncher {
    Server server;
    int turn = 0;
    Deck newDeck;
    HashMap<Integer, PlayerServerDataParser> storedData = new HashMap<>();
    static int beginningValue = 1000;
    static int points = 0;
    int currentTokensAtStake = 0;
    boolean gameMustEnd = false;
    boolean hasBegunSesion = false;
    boolean endTurn = false;
    boolean sesionEnded = false;
    public Map.Entry<Integer,PlayerServerDataParser> winnerOfRound(){
        Map.Entry<Integer, PlayerServerDataParser> winner = null;
        for (Map.Entry<Integer, PlayerServerDataParser> contender : storedData.entrySet()) {
            if(winner==null){
                winner = contender;
                continue;
            }
            int distanceFrom21ForCurrWinner = Math.abs(21-winner.getValue().getPlayerHand().getPoints());
            int distanceFrom21ForContender = Math.abs(21-contender.getValue().getPlayerHand().getPoints());
            if(distanceFrom21ForCurrWinner>distanceFrom21ForContender){
                winner = contender;
            }
            if(distanceFrom21ForCurrWinner==distanceFrom21ForContender){
                winner = null;
                break;
            }
        }
        return winner;
    }

    public boolean isRegistered(int connettion) {
        for (int Id : storedData.keySet()) {
            if (Id == connettion) {
                return true;
            }
        }
        return false;
    }

    Shoe allDecks = new Shoe();

    public ServerLauncher() throws IOException {
        server = new Server() {
            protected Connection newConnection() {
                // By providing our own connection implementation, we can store per
                // connection state without a connection ID to state look up.
                return new ServerConnettion();
            }
        };

        // For consistency, the classes to be sent over the network are
        // registered by the same method for both the client and server.
        Network.register(server);

        server.addListener(new Listener() {

            public void received(Connection c, Object object) {
                //Sending back Ping to client

                ServerConnettion connection = (ServerConnettion) c;
                if (!isRegistered(connection.getID())) {
                    PlayerServerDataParser playerData = new PlayerServerDataParser();
                    playerData.setCoins(beginningValue);
                    playerData.setPlayerHand(new Hand());

                    playerData.getPlayerHand().setPoints(points);
                    storedData.put(connection.getID(), playerData);

                }
                if (object instanceof Network.reqPlayerData) {
                    PlayerServerDataParser response = storedData.get(connection.getID());
                    Log.info("BLACKJACK-SERVER", "Client requested to send player data - sending");
                    server.sendToTCP(connection.getID(), response);

                }
                if (object instanceof Network.increasePoints) {
                    PlayerServerDataParser response = storedData.get(connection.getID());
                    response.getPlayerHand().setPoints(response.getPlayerHand().getPoints() + 10);
                    Log.info("BLACKJACK-SERVER", "Ping has been requested by Client, increasing points on hand by 10 ");
                    server.sendToTCP(connection.getID(), response);

                }
                if (object instanceof Network.decreasePoints) {
                    PlayerServerDataParser response = storedData.get(connection.getID());
                    response.getPlayerHand().setPoints(response.getPlayerHand().getPoints() - 10);
                    Log.info("BLACKJACK-SERVER","Ping has been requested by Client, decreasing points on hand by 10 ");
                    server.sendToTCP(connection.getID(), response);

                }
                if (object instanceof Network.increaseCash) {
                    PlayerServerDataParser response = storedData.get(connection.getID());
                    response.setCoins(response.getCoins() + 10);
                    Log.info("BLACKJACK-SERVER", "Ping has been requested by Client, increasing amount of cash by 10");
                    server.sendToTCP(connection.getID(), response);

                }
                if (object instanceof Network.decreaseCash) {
                    PlayerServerDataParser response = storedData.get(connection.getID());
                    response.setCoins(response.getCoins() - 10);
                    Log.info("BLACKJACK-SERVER","Ping has been requested by Client, decreasing amount of cash by 10");
                    server.sendToTCP(connection.getID(), response);

                }
                if (object instanceof Network.RequestType) {
                    PlayerServerDataParser response = storedData.get(connection.getID());
                    if (((Network.RequestType) object).request.equals(Request.STAND)) {
                        response.requestedEndTurn = true;
                    }  if (((Network.RequestType) object).request.equals(Request.DOUBLE_DOWN)) {
                        if(response.getCoins()<10){
                            response.serverResponseType = Response.DONT_HAVE_COINTS;
                            server.sendToTCP(connection.getID(), response);
                        }
                        else{
                            response.setCoins(response.getCoins()-10);
                            currentTokensAtStake +=10;
                            response.serverResponseType = Response.DELETE_COINS;
                            if(assignCardToPlayer(response)){
                                server.sendToTCP(connection.getID(), response);
                            }
                        }


                    }



                }

            }
        });
        server.bind(Network.port);
        server.start();
        Log.info("BLACKJACK-SERVER", "Waiting for Players to Join ");
        while (true) {
            boolean canStart = true;
            for (PlayerServerDataParser value : storedData.values()) {

                if (!value.agreedToPLay) {
                    canStart = false;
                }
            }
            if (canStart) break;

        }
        Log.info("BLACKJACK-SERVER", "All players accepeted");

        newDeck = allDecks.replaceDeckIfNeeded(new Deck());
        if ((newDeck == null)) {
            Log.error("BLACJACK-SERVER-ERROR", "Deck has not been initalized during gamestart");
            server.close();
        }
        if (!hasBegunSesion) {
            hasBegunSesion = true;
            assignCardAtStartOfRound();
        }
        Log.info("BLACKJACK-SERVER", "All Players Received first cards - Game started");
        while (!gameMustEnd) {
            endTurn = true;
            for (Map.Entry<Integer, PlayerServerDataParser> value : storedData.entrySet()) {
                if (!value.getValue().requestedEndTurn) {
                    endTurn = false;
                }
            }
            if (endTurn) {
                for (Map.Entry<Integer, PlayerServerDataParser> value : storedData.entrySet()) {
                   value.getValue().serverResponseType = Response.END_TURN;
                   value.getValue().currentTurn++;
                }
               server.sendToAllTCP(Response.END_TURN);
               server.sendToAllTCP(winnerOfRound().getValue());

            }
        }


    }

    private void assignCardAtStartOfRound() {
        for (Map.Entry<Integer, PlayerServerDataParser> value : storedData.entrySet()) {
            Card toAssign = newDeck.removeCard();
            if (toAssign == null) {
                allDecks.replaceDeckIfNeeded(newDeck);
            }
            value.getValue().getPlayerHand().addCard(toAssign);
            value.getValue().getPlayerHand().addCard(toAssign);
            server.sendToTCP(value.getKey(), Response.BEGIN_GAME);
            server.sendToTCP(value.getKey(), value.getValue());
        }
    }
    private boolean assignCardToPlayer( PlayerServerDataParser player) {
            Card toAssign = newDeck.removeCard();
            if (toAssign == null) {
                allDecks.replaceDeckIfNeeded(newDeck);
                if(newDeck==null){
                    gameMustEnd = true;
                    return false;
                }
            }
            player.getPlayerHand().addCard(toAssign);
            return true;

    }

    static class ServerConnettion extends Connection {
        public String name;
    }

    public static void main(String[] args) throws IOException {
        new ServerLauncher();
    }
}
