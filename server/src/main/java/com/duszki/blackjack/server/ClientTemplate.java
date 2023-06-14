package com.duszki.blackjack.server;

//Template to be used in futher develompent

<<<<<<< Updated upstream
import com.duszki.blackjack.server.Player.Player;
import com.duszki.blackjack.server.Player.PlayerServerData;
import com.esotericsoftware.kryonet.Client;
=======
import com.duszki.blackjack.server.player.ServerPlayer;
import com.duszki.blackjack.server.player.PlayerServerDataParser;
>>>>>>> Stashed changes
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.util.Scanner;

//TODO remove that file once client is completed
public class ClientTemplate implements Runnable {
    public static String requestType = null;


    public static void main(String[] args) throws IOException {
         ServerPlayer serverPlayer = ServerPlayer.init();
        ClientTemplate clientTemplate = new ClientTemplate();
        Thread inputListener = new Thread(clientTemplate);

        Network.register(serverPlayer.getClient());
        serverPlayer.getClient().addListener(new Listener() {
            public void received(Connection connection, Object object) {
<<<<<<< Updated upstream
                if (object instanceof PlayerServerData playerServerData) {
                    player.setPlayerServerData(playerServerData);
                    System.out.println("Current amount of coins "+player.getPlayerServerData().getCoins());
                    System.out.println("\nCurrent amount of points in that round "+player.getPlayerServerData().getPlayerHand().getPoints());
=======
                if (object instanceof PlayerServerDataParser playerServerData) {
                    serverPlayer.setPlayerServerData(playerServerData);
                    System.out.println("Current amount of coins "+ serverPlayer.getPlayerServerData().getCoins());
                    System.out.println("\nCurrent amount of points in that round "+ serverPlayer.getPlayerServerData().getPlayerHand().getPoints());
>>>>>>> Stashed changes
                }
            }
        });
        serverPlayer.getClient().start();
        serverPlayer.getClient().connect(40000, "localhost", Network.port);
        inputListener.start();
        Network.reqPing request = new Network.reqPing();
        request.requested = true;
        player.getClient().sendTCP(request);
        Network.increasePoints requestIncreasePoints = new Network.increasePoints();
        Network.increaseCash requestIncreaseCash = new Network.increaseCash();
        while (serverPlayer.getClient().isConnected()) {
            if (requestType == null) {
                continue;
            }
            if (requestType.equals("p")) {
                break;
            }
            if (requestType.equals("q")) {
                player.getClient().sendTCP(request);
                requestType="";
            }
            if (requestType.equals("ip")) {
                serverPlayer.getClient().sendTCP(requestIncreasePoints);
                requestType="";
            }
            if (requestType.equals("ic")) {
                serverPlayer.getClient().sendTCP(requestIncreaseCash);
                requestType="";
            }

        }
        while (inputListener.isAlive()){
            requestType="p";
        }

    }


    @Override
    public void run() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            String firstName = scan.nextLine();
            if (firstName.equals("p")) {
                requestType = "p";
                break;
            }
            if (firstName.equals("q")) {
                requestType = "q";
            }
            if (firstName.equals("ip")) {
                requestType = "ip";
            }
            if (firstName.equals("ic")) {
                requestType = "ic";
            }
        }
        scan.close();
    }

}
