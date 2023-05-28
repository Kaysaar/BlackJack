package com.duszki.blackjack.server;

//Template to be used in futher develompent

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.util.Scanner;

//TODO remove that file once client is completed
public class ClientTemplate implements Runnable {
    public static String requestType = null;


    public static void main(String[] args) throws IOException {
        Client client = new Client();
        ClientTemplate clientTemplate = new ClientTemplate();
        Thread inputListener = new Thread(clientTemplate);

        Network.register(client);
        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof Network.Ping response) {
                    System.out.println("Ping has been recived at " + response.pingTime.getHour() + ":" + response.pingTime.getMinute() + ":" + response.pingTime.getSecond());
                }
            }
        });
        client.start();
        client.connect(40000, "localhost", Network.port);
        inputListener.start();
        Network.reqPing request = new Network.reqPing();
        request.requested = true;
        client.sendTCP(request);

        while (client.isConnected()) {
            if (requestType == null) {
                continue;
            }
            if (requestType.equals("p")) {
                break;
            }
            if (requestType.equals("q")) {
                client.sendTCP(request);
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
        }
        scan.close();
    }

}
