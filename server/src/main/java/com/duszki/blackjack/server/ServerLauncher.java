package com.duszki.blackjack.server;

import com.duszki.blackjack.server.Card.*;
import com.duszki.blackjack.server.Player.PlayerServerData;
import com.duszki.blackjack.shared.data.DataToTransfer;
import com.duszki.blackjack.shared.models.Card;
import com.duszki.blackjack.shared.models.Hand;
import com.duszki.blackjack.shared.player.PlayerTransferData;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import com.duszki.blackjack.shared.events.*;
import com.esotericsoftware.minlog.Log;
import com.duszki.blackjack.shared.network.Network;

/**
 * Launches the server application.
 */
public class ServerLauncher {

    static public final int PORT = 5000;
    public static final boolean DEBUG = true;
    public static final int MAX_PLAYERS = 5;
    private static final int MAX_HAND_VALUE = 21;
    Server server;

    LinkedList<PlayerServerData> storedPlayerData = new LinkedList<>();
    public boolean isGameOver = false;

    private int currentPlayerCursor;
    public boolean finalRound = false;
    int currentStake = 0;

    public boolean hasGameStarted = false;

    public static final int COINS_AT_START = 1000;

    boolean allBetsPlaced = false;

    Shoe shoe;
    Dealer dealer;

    public ServerLauncher() throws IOException {

        if (DEBUG) {
//            Log.ERROR();
//            Log.WARN();
//            Log.INFO();
            Log.DEBUG();
//            Log.TRACE();
        }

        server = new Server();

        Network.register(server);

        addAllListeners();

        bindServer(PORT);

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

                    GameStartedEvent gameStartedEvent = new GameStartedEvent();

                    server.sendToAllTCP(gameStartedEvent);

                    startRound();

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    for (PlayerServerData player : storedPlayerData) {
                        server.sendToTCP(player.getConnection().getID(), packSinglePLayer(player));
                    }

                    DataToTransfer newData = packAllPlayersData();

                    sendGameUpdateToPlayers(newData);

                }

            }
        });

        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof HitEvent) {

                    PlayerServerData currentPlayer = getPlayerByConnection(connection);

                    if (currentPlayer.getConnection() == connection) {
                        if (!currentPlayer.getStand()) {
                            hit(currentPlayer);
                            server.sendToTCP(connection.getID(), packSinglePLayer(currentPlayer));
                            sendGameUpdateToPlayers(packAllPlayersData());
                        }

                    }


                }

            }
        });
        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof StandEvent) {

                    PlayerServerData currentPlayer = getPlayerByConnection(connection);

                    if (currentPlayer.getConnection() == connection) {
                        stand(currentPlayer);
                        server.sendToTCP(connection.getID(), packSinglePLayer(currentPlayer));
                        sendGameUpdateToPlayers(packAllPlayersData());
//                        nextPlayer();
                    }


                }

            }
        });

        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof DoubleDownEvent) {

                    PlayerServerData currentPlayer = getPlayerByConnection(connection);

                    if (currentPlayer.getPlayerHand().getCardsInHand().size() == 2) {
                        int currTokens = currentPlayer.getTokens();
                        int currStake = currentPlayer.getBet();

                        if (currentPlayer.getConnection() == connection) {
                            if (currStake == 0 || currStake * 2 > currTokens) {
                                NotValidatedToDoEvent response = new NotValidatedToDoEvent();
                                response.setMessage("Double down event is now possible due to either not having enough money or nothing at stake yet");
                                server.sendToTCP(connection.getID(), response);
                                return;
                            }
                            doubleDown(currentPlayer);
                            server.sendToTCP(connection.getID(), packSinglePLayer(currentPlayer));
                            sendGameUpdateToPlayers(packAllPlayersData());

                        }
                    }

                    nextPlayer();


                }

            }
        });


        server.addListener(new Listener() {
            @Override
            public void disconnected(Connection connection) {
                PlayerServerData player = getPlayerByConnection(connection);
                storedPlayerData.remove(player);
                if (storedPlayerData.size() == 0) {
                    resetGame();

                }
            }
        });

        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof PlaceBetEvent) {
                    PlaceBetEvent placeBetEvent = (PlaceBetEvent) object;

                    PlayerServerData currentPlayer = getPlayerByConnection(connection);

                    if (currentPlayer.getConnection() == connection) {
                        if (currentPlayer.getBet() == 0) {
                            int currTokens = currentPlayer.getTokens();
                            int currStake = placeBetEvent.getBet();
                            if (currStake > currTokens) {
                                NotValidatedToDoEvent response = new NotValidatedToDoEvent();
                                response.setMessage("Not enough money to place bet");
                                server.sendToTCP(connection.getID(), response);
                                return;
                            }
                            placeBet(currentPlayer, placeBetEvent.getBet());
                            server.sendToTCP(connection.getID(), packSinglePLayer(currentPlayer));
                            sendGameUpdateToPlayers(packAllPlayersData());
                        }

                    }
                }
            }
        });

    }

    private void placeBet(PlayerServerData currentPlayer, int bet) {

        currentPlayer.setBet(bet);
        currentPlayer.setTokens(currentPlayer.getTokens() - bet);

        // check if all players have placed their bets
        boolean allBetsPlaced = true;
        for (PlayerServerData player : storedPlayerData) {
            if (player.getBet() == 0) {
                allBetsPlaced = false;
                break;
            }
        }

        if (allBetsPlaced) {
            startRound();
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

    public void resetGame() {
        hasGameStarted = false;
        storedPlayerData.clear();
        dealer.getHand().clearHand();
        finalRound = false;
//        initialize_game();
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
        Card card_from_deck_first;
        Card card_from_deck_second;
        for (PlayerServerData player : storedPlayerData) {
            player.getPlayerHand().clearHand();
            player.setStand(false);
            card_from_deck_first = shoe.getCardFromShoe();
            card_from_deck_second = shoe.getCardFromShoe();
            if (card_from_deck_second == null || card_from_deck_first == null) {
                finalRound = true;
                shoe.lastDeckRound();
                card_from_deck_first = shoe.getCardFromShoe();
                card_from_deck_second = shoe.getCardFromShoe();
            }

            player.getPlayerHand().addCard(card_from_deck_first);
            player.getPlayerHand().addCard(card_from_deck_second);
        }
        card_from_deck_first = shoe.getCardFromShoe();
        card_from_deck_second = shoe.getCardFromShoe();
        if (card_from_deck_second == null || card_from_deck_first == null) {
            finalRound = true;
            shoe.lastDeckRound();
            card_from_deck_first = shoe.getCardFromShoe();
            card_from_deck_second = shoe.getCardFromShoe();
        }
        dealer.getHand().addCard(card_from_deck_first);
        dealer.getHand().addCard(card_from_deck_second);

        // send to all players
//        sendGameUpdateToPlayers(packAllPlayersData());
    }


    public void sendGameUpdateToPlayers(DataToTransfer updatedPackage) {
        server.sendToAllTCP(updatedPackage);
    }

    public DataToTransfer packAllPlayersData() {

        DataToTransfer packedData = new DataToTransfer();
        packedData.dealerHand = dealer.getHand();
        packedData.otherPlayers = new ArrayList<>();
        for (PlayerServerData storedPlayerData : storedPlayerData) {
            packedData.otherPlayers.add(new PlayerTransferData(storedPlayerData.playerName, storedPlayerData.getPlayerHand().getCardsInHand()));
        }

        // TODO

        return packedData;
    }

    public PlayerTransferData packSinglePLayer(PlayerServerData player) {
        PlayerTransferData toReturn = new PlayerTransferData(player.playerName, player.getPlayerHand().getCardsInHand());
        if (getCurrentTurnPlayer().getConnection().getID() == player.getConnection().getID()) {
            toReturn.setYourTurn(true);
            toReturn.setGameOver(isGameOver);
            //TODO diffriceante when round has been lost and where round is not yet over
            toReturn.setHavelostRound(false);
        }


        return toReturn;
    }

    public void hit(PlayerServerData player) {
        System.out.println("hit");
        System.out.println(player.getPlayerHand().getHandValue());
        player.getPlayerHand().addCard(shoe.getCardFromShoe());
        if (player.getPlayerHand().getHandValue() >= MAX_HAND_VALUE) {
            stand(player);
        }
    }

    public void stand(PlayerServerData player) {
        player.setStand(true);

        // if all players have stand then dealer will play
        if (allPlayersHaveStand()) {
            dealerPlay();

        } else {
            nextPlayer();
        }

    }

    public void doubleDown(PlayerServerData player) {

        int currentStake = player.getBet();

        player.setTokens(player.getTokens() - currentStake);
        player.setBet(currentStake * 2);


        player.getPlayerHand().addCard(shoe.getCardFromShoe());
        player.setStand(true);

//        player.getConnection().sendTCP(new EndTurnEvent());
//        player.getConnection().sendTCP(new TransferData());

    }

    public void nextPlayer() {
        PlayerServerData nextPlayer;
        int iterator = 0;
//        while (true) {

            currentPlayerCursor = (currentPlayerCursor + 1) % storedPlayerData.size();
            nextPlayer = storedPlayerData.get(currentPlayerCursor);
//            if (!nextPlayer.getStand()) {
//                break;
//            }
//            iterator++;
//            if (iterator >= 20) { // 20 is random number simply when where would be situation that everyone has stand so we wont have infinite loop
//                nextPlayer = null;
//                break;
//            }
//
//        }
//        if (nextPlayer == null) {
//            endRound();
//            return;
//        }
        nextPlayer.getConnection().sendTCP(new YourTurnEvent());
    }

    public void endRound() {

        checkWinners();

        for (PlayerServerData storedPlayerData : storedPlayerData) {

            storedPlayerData.setStand(false);
            storedPlayerData.setBet(0);
            storedPlayerData.getPlayerHand().clearHand();


        }

        packAllPlayersData();
        sendGameUpdateToPlayers(packAllPlayersData());

    }

    public boolean allPlayersHaveStand() {

        System.out.println(storedPlayerData.size());
        int i = 0;
        for (PlayerServerData storedPlayerData : storedPlayerData) {
            if (!storedPlayerData.getStand()) {
                System.out.println("not all players have stand");
                System.out.println(i++);
                return false;
            }
        }
        System.out.println("all players have stand");
        return true;

    }

    public void dealerPlay() {

        while (dealer.getHand().getHandValue() < 17) {
            dealer.getHand().addCard(shoe.getCardFromShoe());
        }

        // send to all players
        sendGameUpdateToPlayers(packAllPlayersData());

        endRound();

    }


    public static void main(String[] args) throws IOException {
        new ServerLauncher();
    }

    public void checkWinners() {
        for (PlayerServerData storedPlayerData : storedPlayerData) {
            if (storedPlayerData.getPlayerHand().getHandValue() <= MAX_HAND_VALUE) {
                if (storedPlayerData.getPlayerHand().getHandValue() > dealer.getHand().getHandValue()) {
                    storedPlayerData.setTokens(storedPlayerData.getTokens() + storedPlayerData.getBet() * 2);
                } else if (storedPlayerData.getPlayerHand().getHandValue() == dealer.getHand().getHandValue()) {
                    storedPlayerData.setTokens(storedPlayerData.getTokens() + storedPlayerData.getBet());
                }

            }
        }
    }

//    public List<PlayerServerData> selectWinnersOfRound() {
//        List<PlayerServerData> winners = new ArrayList<>();
//        boolean atLeastOneBelow21 = false;
//        int smallestDistanceFrom21 = Math.abs(dealer.getHand().getHandValue() - MAX_HAND_VALUE);
//        if (smallestDistanceFrom21 <= MAX_HAND_VALUE) {
//            atLeastOneBelow21 = true;
//        }
//        for (PlayerServerData storedPlayerData : storedPlayerData) {
//            if (Math.abs(storedPlayerData.getPlayerHand().getHandValue() - MAX_HAND_VALUE) < smallestDistanceFrom21) {
//                if (storedPlayerData.getPlayerHand().getHandValue() - MAX_HAND_VALUE >= 0) {
//                    atLeastOneBelow21 = true;
//                }
//                smallestDistanceFrom21 = (Math.abs(storedPlayerData.getPlayerHand().getHandValue() - MAX_HAND_VALUE));
//
//            }
//        }
//        for (PlayerServerData storedPlayerData : storedPlayerData) {
//            if (storedPlayerData.getPlayerHand().getHandValue() == smallestDistanceFrom21) {
//                if (storedPlayerData.getPlayerHand().getHandValue() <= MAX_HAND_VALUE) {
//                    winners.add(storedPlayerData);
//                }
//                if (storedPlayerData.getPlayerHand().getHandValue() > MAX_HAND_VALUE && !atLeastOneBelow21) {
//                    winners.add(storedPlayerData);
//                }
//
//            }
//        }
//
//        // If winners is empty then it means that dealer has won the round
//        return winners;
//    }

//    public void stakeDivision() {
//        int currentStake = 0;
//        for (PlayerServerData storedPlayerData : storedPlayerData) {
//            currentStake += storedPlayerData.getStake();
//            storedPlayerData.setStake(0);
//        }
//        List<PlayerServerData> winnersOfRound = selectWinnersOfRound();
//        if (winnersOfRound.isEmpty()) {
//            return;
//        }
//        int singleAward = currentStake / winnersOfRound.size();
//        for (PlayerServerData playerServerData : winnersOfRound) {
//            playerServerData.setTokens(playerServerData.getTokens() + singleAward);
//        }
//
//    }

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
