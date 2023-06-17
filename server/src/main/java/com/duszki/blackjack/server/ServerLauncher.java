package com.duszki.blackjack.server;

import com.duszki.blackjack.server.Card.*;
import com.duszki.blackjack.server.Player.PlayerServerData;
import com.duszki.blackjack.shared.data.DataToTransfer;
import com.duszki.blackjack.shared.data.WinnerOfRound;
import com.duszki.blackjack.shared.models.Hand;
import com.duszki.blackjack.shared.player.PlayerTransferData;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.LinkedList;

import com.duszki.blackjack.shared.events.*;

/**
 * Launches the server application.
 */
public class ServerLauncher {
    public static final int MAX_PLAYERS = 5;
    Server server;
//    int turn = 0;
//    Deck newDeck;

    LinkedList<PlayerServerData> storedPlayerData = new LinkedList<>();
    public boolean isGameOver = false;

    private int currentPlayerCursor;


    public boolean hasGameStarted = false;

    public static final int COINS_AT_START = 1000;

//    static int points = 0;
//    int currentTokensAtStake = 0;
//    boolean gameMustEnd = false;
//    boolean hasBegunSesion = false;
//    boolean endTurn = false;
//    boolean sesionEnded = false;


    Shoe shoe;
    Dealer dealer;

//    Dealer croupier = new Dealer();

    public ServerLauncher() throws IOException {
        server = new Server(); /*{
            protected Connection newConnection() {
                // By providing our own connection implementation, we can store per
                // connection state without a connection ID to state look up.
                return new ServerConnettion();
            }
        }; */

        // For consistency, the classes to be sent over the network are
        // registered by the same method for both the client and server.
        Network.register(server);

        addAllListeners();

        bindServer(Network.port);

        server.start();


    }

    private void addAllListeners() {
        server.addListener(new Listener() {

            @Override
            public void received(Connection connection, Object object) {

                if (object instanceof JoinRequestEvent) {

                    JoinResponseEvent joinResponseEvent = new JoinResponseEvent();

                    if (hasGameStarted) {

                        joinResponseEvent.setSuccess(false);
                        joinResponseEvent.setMessage("Sorry, the game has already started!");
                        connection.sendTCP(joinResponseEvent);

                    } else {

                        JoinRequestEvent joinRequestEvent = (JoinRequestEvent) object;

                        if (storedPlayerData.size() < MAX_PLAYERS) {
                            PlayerServerData serverPlayer = new PlayerServerData(connection, joinRequestEvent.getPlayerName());

                            storedPlayerData.add(serverPlayer);

                            joinResponseEvent.setMessage("Welcome " + joinRequestEvent.getPlayerName() + "!");

                            joinResponseEvent.setSuccess(true);
                        } else {
                            joinResponseEvent.setMessage("Sorry, the game is full!");
                            joinResponseEvent.setSuccess(false);
                        }
                        connection.sendTCP(joinResponseEvent);
                    }
                }
            }

        });

        server.addListener(new Listener() {

            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof RequestGameStartEvent) {
                    RequestGameStartEvent requestGameStartEvent = (RequestGameStartEvent) object;
                    hasGameStarted = true;
                    initialize_game();
                }

            }
        });

        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof HitEvent) {

                    PlayerServerData currentPlayer = getCurrentTurnPlayer();

                    if(currentPlayer.getConnection() == connection) {
                        hit(currentPlayer);
                        server.sendToTCP(connection.getID(),packSinglePLayer(currentPlayer));
                        sendGameUpdateToPlayers(packAllPlayersData());
                        nextPlayer();
                    }


                }

            }
        });
        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof StandEvent) {

                    PlayerServerData currentPlayer = getCurrentTurnPlayer();

                    if(currentPlayer.getConnection() == connection) {
                        stand(currentPlayer);
                        server.sendToTCP(connection.getID(),packSinglePLayer(currentPlayer));
                        sendGameUpdateToPlayers(packAllPlayersData());
                        nextPlayer();
                    }


                }

            }
        });
        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof DoubleDownEvent) {

                    PlayerServerData currentPlayer = getCurrentTurnPlayer();
                    int currTokens = currentPlayer.getTokens();
                    int currStake = currentPlayer.getStake();
                    if(currentPlayer.getConnection() == connection) {
                        if(currStake==0||currStake*2>currTokens){
                            NotValidatedToDoEvent response = new NotValidatedToDoEvent();
                            response.setMessage("Double down event is now possible due to either not having enough money or nothing at stake yet");
                            server.sendToTCP(connection.getID(),response);
                            return;
                        }
                        doubleDown(currentPlayer);
                        server.sendToTCP(connection.getID(),packSinglePLayer(currentPlayer));
                        sendGameUpdateToPlayers(packAllPlayersData());
                        nextPlayer();
                    }


                }

            }
        });
    }

    public void bindServer(int port) {
        this.server.start();

        try {
            this.server.bind(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize_game() {
        shoe = new Shoe();

        dealer = new Dealer();

        currentPlayerCursor = 0;

        for (PlayerServerData player : storedPlayerData) {
            player.setPlayerHand(new Hand());
            player.setTokens(COINS_AT_START);
        }

    }

    public void startRound() {

        currentPlayerCursor = 0;

        for (PlayerServerData player : storedPlayerData) {
            player.getPlayerHand().clearHand();
            player.setHasStand(false);
            player.getPlayerHand().addCard(shoe.getCardFromShoe());
            player.getPlayerHand().addCard(shoe.getCardFromShoe());
        }
        dealer.getHand().addCard(shoe.getCardFromShoe());
        dealer.getHand().addCard(shoe.getCardFromShoe());
    }


    public void sendGameUpdateToPlayers(DataToTransfer updatedPackage) {
        server.sendToAllTCP(updatedPackage);
    }

    public DataToTransfer packAllPlayersData()  {

        DataToTransfer packedData = new DataToTransfer();
        packedData.dealerHand = dealer.getHand();
        for (PlayerServerData storedPlayerDatum : storedPlayerData) {
            packedData.otherPlayers.add(new PlayerTransferData(storedPlayerDatum.playerName,storedPlayerDatum.getPlayerHand().getCardsInHand()));
        }

        // TODO

        return packedData;
    }
    public PlayerTransferData packSinglePLayer(PlayerServerData player)  {
        PlayerTransferData toReturn = new PlayerTransferData(player.playerName,player.getPlayerHand().getCardsInHand());
        if(getCurrentTurnPlayer().getConnection().getID()==player.getConnection().getID()){
            toReturn.setYourTurn(true);
            toReturn.setGameOver(isGameOver);
            //TODO diffriceante when round has been lost and where round is not yet over
            toReturn.setHavelostRound(false);
        }


        return toReturn;
    }

    public void hit(PlayerServerData player) {
        player.getPlayerHand().addCard(shoe.getCardFromShoe());
    }

    public void stand(PlayerServerData player) {
        player.setHasStand(true);
    }

    public void doubleDown(PlayerServerData player) {

        int currentStake = player.getStake();

        player.setTokens(player.getTokens() - currentStake);
        player.setStake(currentStake * 2);


        player.getPlayerHand().addCard(shoe.getCardFromShoe());
        player.setHasStand(true);

//        player.getConnection().sendTCP(new EndTurnEvent());
//        player.getConnection().sendTCP(new TransferData());

    }

    public void nextPlayer() {
        PlayerServerData nextPlayer = null;
        int iterator =0;
        while(true){

            currentPlayerCursor = (currentPlayerCursor + 1) % storedPlayerData.size();
            nextPlayer  = storedPlayerData.get(currentPlayerCursor);
            if(!nextPlayer.getHasStand()){
                break;
            }
            iterator++;
            if(iterator>=20){
                nextPlayer=null;
                break;
            }

        }
        if(nextPlayer==null){
            endRound();
            return;
        }
        nextPlayer.getConnection().sendTCP(new YourTurnEvent());
    }
    public void endRound() {
        for (PlayerServerData storedPlayerDatum : storedPlayerData) {
            if (!storedPlayerDatum.getHasStand()) {
                return;
            }
        }
        for (PlayerServerData storedPlayerDatum : storedPlayerData) {
            storedPlayerDatum.setHasStand(false);
        }
        //TODO : Send Who is the winer of Round
        packAllPlayersData();
        server.sendToAllTCP(new WinnerOfRound());
    }


    public static void main(String[] args) throws IOException {
        new ServerLauncher();
    }

    public PlayerServerData getPlayerByConnection(Connection connection) {
        for (PlayerServerData serverPlayer : storedPlayerData) {
            if (serverPlayer.getConnection() == connection) {
                return serverPlayer;
            }
        }
        return null;
    }

    public PlayerServerData getCurrentTurnPlayer() {

        return storedPlayerData.get(currentPlayerCursor);

    }

}
