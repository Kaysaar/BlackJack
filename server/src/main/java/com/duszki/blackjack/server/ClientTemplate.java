package com.duszki.blackjack.server;

//Template to be used in futher develompent

import com.duszki.blackjack.server.Player.Player;
import com.duszki.blackjack.server.Player.PlayerServerDataParser;
import com.duszki.blackjack.server.Player.PlayerServerDataParser;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.util.Scanner;

//TODO remove that file once client is completed
public class ClientTemplate implements Runnable {
    public static String requestType = null;


    public static void main(String[] args) throws IOException {
         Player player = Player.init();
        ClientTemplate clientTemplate = new ClientTemplate();
        Thread inputListener = new Thread(clientTemplate);

        Network.register(player.getClient());
        player.getClient().addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof PlayerServerDataParser playerServerData) {
                    player.setPlayerServerData(playerServerData);
                    System.out.println("Current amount of coins "+player.getPlayerServerData().getCoins());
                    System.out.println("\nCurrent amount of points in that round "+player.getPlayerServerData().getPlayerHand().getPoints());
                }
            }
        });
        player.getClient().start();
        player.getClient().connect(40000, "localhost", Network.port);
        inputListener.start();
        Network.increasePoints requestIncreasePoints = new Network.increasePoints();
        Network.increaseCash requestIncreaseCash = new Network.increaseCash();
        while (player.getClient().isConnected()) {
            if (requestType == null) {
                continue;
            }
            if (requestType.equals("p")) {
                break;
            }
            if (requestType.equals("q")) {
                requestType="";
            }
            if (requestType.equals("ip")) {
                player.getClient().sendTCP(requestIncreasePoints);
                requestType="";
            }
            if (requestType.equals("ic")) {
                player.getClient().sendTCP(requestIncreaseCash);
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
