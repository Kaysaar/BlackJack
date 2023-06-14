package com.duszki.blackjack.server;
<<<<<<< Updated upstream
import com.duszki.blackjack.server.Card.Hand;
import com.duszki.blackjack.server.Card.Shoe;
import com.duszki.blackjack.server.Player.PlayerServerData;
=======

import com.duszki.blackjack.server.card.*;
import com.duszki.blackjack.server.listeners.JoinListener;
import com.duszki.blackjack.server.player.PlayerServerDataParser;
>>>>>>> Stashed changes
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
/** Launches the server application. */
public class ServerLauncher {
    Server server;
    HashMap<Integer,PlayerServerData> storedData = new HashMap<>();
    int beginningValue=200;
    boolean hasBegunSesion = false;
<<<<<<< Updated upstream
    public boolean isRegistered(int connettion){
=======
    boolean endTurn = false;
    boolean sesionEnded = false;

    public Map.Entry<Integer, PlayerServerDataParser> winnerOfRound() {
        Map.Entry<Integer, PlayerServerDataParser> winner = null;
        for (Map.Entry<Integer, PlayerServerDataParser> contender : storedData.entrySet()) {
            if (winner == null) {
                winner = contender;
                continue;
            }
            int distanceFrom21ForCurrWinner = Math.abs(21 - winner.getValue().getPlayerHand().getPoints());
            int distanceFrom21ForContender = Math.abs(21 - contender.getValue().getPlayerHand().getPoints());
            if (distanceFrom21ForCurrWinner > distanceFrom21ForContender) {
                winner = contender;
            }
            if (distanceFrom21ForCurrWinner == distanceFrom21ForContender) {
                winner = null;
                break;
            }
        }
        return winner;
    }

    public boolean isRegistered(int connettion) {
>>>>>>> Stashed changes
        for (int Id : storedData.keySet()) {
            if(Id==connettion){
                return true;
            }
        }
        return false;
    }
    Shoe allDecks = new Shoe();
<<<<<<< Updated upstream
    public ServerLauncher () throws IOException {
        server = new Server() {
            protected Connection newConnection () {
                // By providing our own connection implementation, we can store per
                // connection state without a connection ID to state look up.
                return new ServerConnettion();
            }
        };
=======
    Dealer croupier = new Dealer();

    public ServerLauncher() throws IOException {
        this.server = new Server(); //{
//            protected Connection newConnection() {
//                // By providing our own connection implementation, we can store per
//                // connection state without a connection ID to state look up.
//                return new ServerConnettion();
//            }
//        };
>>>>>>> Stashed changes

        // For consistency, the classes to be sent over the network are
        // registered by the same method for both the client and server.
        Network.register(server);


        this.server.addListener(new JoinListener());

        server.addListener(new Listener() {

            public void received (Connection c, Object object) {
                //Sending back Ping to client

                ServerConnettion connection = (ServerConnettion)c;
                if(!isRegistered(connection.getID())){
                    PlayerServerData playerData  = new PlayerServerData();
                    playerData.setCoins(beginningValue);
                    playerData.setPlayerHand(new Hand());
                    playerData.getPlayerHand().setPoints(20);
                    storedData.put(connection.getID(),playerData);

                }
                if (object instanceof Network.reqPing) {
                   PlayerServerData response = storedData.get(connection.getID());
                   Log.info("BLACKJACK-SERVER","Ping has been requested by Client, sending client data");
                   server.sendToTCP(connection.getID(), response);

                }
                if (object instanceof Network.increasePoints) {
<<<<<<< Updated upstream
                    PlayerServerData response = storedData.get(connection.getID());
                    response.getPlayerHand().setPoints(response.getPlayerHand().getPoints()+10);
                    Log.info("BLACKJACK-SERVER","Ping has been requested by Client, increasing points on hand by 10 ");
=======
                    PlayerServerDataParser response = storedData.get(connection.getID());
                    response.getPlayerHand().setPoints(response.getPlayerHand().getPoints() + 10);
                    Log.info("BLACKJACK-SERVER", "Ping has been requested by Client, increasing points on hand by 10 ");
                    server.sendToTCP(connection.getID(), response);

                }
                if (object instanceof Network.decreasePoints) {
                    PlayerServerDataParser response = storedData.get(connection.getID());
                    response.getPlayerHand().setPoints(response.getPlayerHand().getPoints() - 10);
                    Log.info("BLACKJACK-SERVER", "Ping has been requested by Client, decreasing points on hand by 10 ");
>>>>>>> Stashed changes
                    server.sendToTCP(connection.getID(), response);

                }
                if (object instanceof Network.increaseCash) {
                    PlayerServerData response = storedData.get(connection.getID());
                    response.setCoins(response.getCoins()+10);
                    Log.info("BLACKJACK-SERVER","Ping has been requested by Client, increasing amount of cash by 10");
                    server.sendToTCP(connection.getID(), response);

                }
<<<<<<< Updated upstream

            }
        });
        server.bind(Network.port);
        server.start();
=======
                if (object instanceof Network.decreaseCash) {
                    PlayerServerDataParser response = storedData.get(connection.getID());
                    response.setCoins(response.getCoins() - 10);
                    Log.info("BLACKJACK-SERVER", "Ping has been requested by Client, decreasing amount of cash by 10");
                    server.sendToTCP(connection.getID(), response);

                }
                if (object instanceof Network.RequestType) {
                    PlayerServerDataParser response = storedData.get(connection.getID());
                    if (((Network.RequestType) object).request.equals(Request.HIT)) {
                        response.getPlayerHand().addCard(allDecks.getCardFromShoe());
                    }
                    if (((Network.RequestType) object).request.equals(Request.STAND)) {
                        response.requestedEndTurn = true;
                    }
                    if (((Network.RequestType) object).request.equals(Request.DOUBLE_DOWN)) {
                        if (response.getCoins() < 10) {
                            response.serverResponseType = Response.DONT_HAVE_COINTS;
                            server.sendToTCP(connection.getID(), response);
                        } else {
                            response.setCoins(response.getCoins() - 10);
                            currentTokensAtStake += 10;
                            response.serverResponseType = Response.DELETE_COINS;
                            if (assignCardToPlayer(response)) {
                                server.sendToTCP(connection.getID(), response);
                            }
                        }


                    }


                }

            }
        });


        bindServer(Network.port);


        Log.info("BLACKJACK-SERVER", "Waiting for Players to Join ");
        while (true) {
            boolean canStart = true;
            for (PlayerServerDataParser value : storedData.values()) {
>>>>>>> Stashed changes

        // Open a window to provide an easy way to stop the server.
        JFrame frame = new JFrame("Chat Server");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosed (WindowEvent evt) {
                server.stop();
            }
<<<<<<< Updated upstream
        });
        frame.getContentPane().add(new JLabel("Close to stop the chat server."));
        frame.setSize(320, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

=======
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

    public void bindServer(int port) {
        this.server.start();

        try {
            this.server.bind(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void assignCardAtStartOfRound() {
        for (Map.Entry<Integer, PlayerServerDataParser> value : storedData.entrySet()) {
            Card toAssign = newDeck.removeCard();
            if (toAssign == null) {
                allDecks.replaceDeckIfNeeded(newDeck);
                toAssign = newDeck.removeCard();
            }
            value.getValue().getPlayerHand().addCard(toAssign);
            toAssign = newDeck.removeCard();
            if (toAssign == null) {
                allDecks.replaceDeckIfNeeded(newDeck);
                toAssign = newDeck.removeCard();
            }
            value.getValue().getPlayerHand().addCard(toAssign);
            toAssign = newDeck.removeCard();
            if (toAssign == null) {
                allDecks.replaceDeckIfNeeded(newDeck);
                toAssign = newDeck.removeCard();
            }
            croupier.getPlayerHand().addCard(toAssign);
            toAssign = newDeck.removeCard();
            if (toAssign == null) {
                allDecks.replaceDeckIfNeeded(newDeck);
                toAssign = newDeck.removeCard();
            }
            croupier.getPlayerHand().addCard(toAssign);
            server.sendToTCP(value.getKey(), Response.BEGIN_GAME);
            server.sendToTCP(value.getKey(), value.getValue());
        }
    }

    private boolean assignCardToPlayer(PlayerServerDataParser player) {
        Card toAssign = newDeck.removeCard();
        if (toAssign == null) {
            allDecks.replaceDeckIfNeeded(newDeck);
            if (newDeck == null) {
                gameMustEnd = true;
                return false;
            }
        }
        player.getPlayerHand().addCard(toAssign);
        return true;

    }
>>>>>>> Stashed changes

    static class ServerConnettion extends Connection {
        public String name;
    }
        public static void main(String[] args) throws IOException {
        new ServerLauncher();
    }
}
