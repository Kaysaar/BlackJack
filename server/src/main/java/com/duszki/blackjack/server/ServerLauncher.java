package com.duszki.blackjack.server;

import com.duszki.blackjack.server.Card.*;
import com.duszki.blackjack.server.Player.Player;
import com.duszki.blackjack.server.Player.PlayerServerData;
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

//    HashMap<Integer, PlayerServerData> storedData = new HashMap<>();
    LinkedList<PlayerServerData> storedData = new LinkedList<>();


    static int beginningValue = 1000;
    static int points = 0;
    int currentTokensAtStake = 0;
    boolean gameMustEnd = false;
    boolean hasBegunSesion = false;
    boolean endTurn = false;
    boolean sesionEnded = false;
    public PlayerServerData winnerOfRound(){
      PlayerServerData winner = null;
        for (PlayerServerData contender : storedData) {
            if(winner==null){
                winner = contender;
                continue;
            }
            int distanceFrom21ForCurrWinner = Math.abs(21-winner.getPlayerHand().getPoints());
            int distanceFrom21ForContender = Math.abs(21-contender.getPlayerHand().getPoints());
            if(distanceFrom21ForCurrWinner>distanceFrom21ForContender){
                winner = contender;
            }
            if(distanceFrom21ForCurrWinner==distanceFrom21ForContender){
                winner = null;
            }
        }
        return winner;
    }



    Shoe allDecks = new Shoe();
    Dealer croupier = new Dealer();
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

//        server.addListener(new Listener() {
//
//            public void received(Connection c, Object object) {
//                //Sending back Ping to client
//
//                ServerConnettion connection = (ServerConnettion) c;
//                if (!isRegistered(connection.getID())) {
//                    PlayerServerData playerData = new PlayerServerData();
//                    playerData.setCoins(beginningValue);
//                    playerData.setPlayerHand(new Hand());
//
//                    playerData.getPlayerHand().setPoints(points);
//                    storedData.put(connection.getID(), playerData);
//
//                }
//                if (object instanceof Network.reqPlayerData) {
//                    PlayerServerData response = storedData.get(connection.getID());
//                    Log.info("BLACKJACK-SERVER", "Client requested to send player data - sending");
//                    server.sendToTCP(connection.getID(), response);
//
//                }
//                if (object instanceof Network.increasePoints) {
//                    PlayerServerData response = storedData.get(connection.getID());
//                    response.getPlayerHand().setPoints(response.getPlayerHand().getPoints() + 10);
//                    Log.info("BLACKJACK-SERVER", "Ping has been requested by Client, increasing points on hand by 10 ");
//                    server.sendToTCP(connection.getID(), response);
//
//                }
//                if (object instanceof Network.decreasePoints) {
//                    PlayerServerData response = storedData.get(connection.getID());
//                    response.getPlayerHand().setPoints(response.getPlayerHand().getPoints() - 10);
//                    Log.info("BLACKJACK-SERVER","Ping has been requested by Client, decreasing points on hand by 10 ");
//                    server.sendToTCP(connection.getID(), response);
//
//                }
//                if (object instanceof Network.increaseCash) {
//                    PlayerServerData response = storedData.get(connection.getID());
//                    response.setCoins(response.getCoins() + 10);
//                    Log.info("BLACKJACK-SERVER", "Ping has been requested by Client, increasing amount of cash by 10");
//                    server.sendToTCP(connection.getID(), response);
//
//                }
//                if (object instanceof Network.decreaseCash) {
//                    PlayerServerData response = storedData.get(connection.getID());
//                    response.setCoins(response.getCoins() - 10);
//                    Log.info("BLACKJACK-SERVER","Ping has been requested by Client, decreasing amount of cash by 10");
//                    server.sendToTCP(connection.getID(), response);
//
//                }
//                if (object instanceof Network.RequestType) {
//                    PlayerServerData response = storedData.get(connection.getID());
//                    if (((Network.RequestType) object).request.equals(Request.HIT)) {
//                        response.getPlayerHand().addCard(allDecks.getCardFromShoe());
//                    }
//                    if (((Network.RequestType) object).request.equals(Request.STAND)) {
//                        response.requestedEndTurn = true;
//                    }  if (((Network.RequestType) object).request.equals(Request.DOUBLE_DOWN)) {
//                        if(response.getCoins()<10){
//                            response.serverResponseType = Response.DONT_HAVE_COINTS;
//                            server.sendToTCP(connection.getID(), response);
//                        }
//                        else{
//                            response.setCoins(response.getCoins()-10);
//                            currentTokensAtStake +=10;
//                            response.serverResponseType = Response.DELETE_COINS;
//                            if(assignCardToPlayer(response)){
//                                server.sendToTCP(connection.getID(), response);
//                            }
//                        }
//
//
//                    }
//
//
//
//                }
//
//            }
//        });


        server.bind(Network.port);
        server.start();
        Log.info("BLACKJACK-SERVER", "Waiting for Players to Join ");
        while (true) {
            boolean canStart = true;
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

        }


    }

    private void assignCardAtStartOfRound() {
        for ( PlayerServerData value : storedData) {
            Card toAssign = newDeck.removeCard();
            if (toAssign == null) {
                allDecks.replaceDeckIfNeeded(newDeck);
                toAssign = newDeck.removeCard();
            }
            value.getPlayerHand().addCard(toAssign);
            toAssign = newDeck.removeCard();
            if (toAssign == null) {
                allDecks.replaceDeckIfNeeded(newDeck);
                toAssign = newDeck.removeCard();
            }
            value.getPlayerHand().addCard(toAssign);
            toAssign = newDeck.removeCard();
            if (toAssign == null) {
                allDecks.replaceDeckIfNeeded(newDeck);
                toAssign = newDeck.removeCard();
            }

            toAssign = newDeck.removeCard();
            if (toAssign == null) {
                allDecks.replaceDeckIfNeeded(newDeck);
                toAssign = newDeck.removeCard();
            }

            server.sendToTCP(value.getConnection().getID(), Response.BEGIN_GAME);
        }
    }
    private boolean assignCardToPlayer( PlayerServerData player) {
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
