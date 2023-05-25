package com.duszki.blackjack.server;

//Template to be used in futher develompent

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

//TODO remove that file once client is completed
public class ClientTemplate {


    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.start();
        client.connect(5000, "localhost", 54555);
        Network.reqPing request = new Network.reqPing();
        request.requested = true;
        client.sendTCP(request);
        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof Network.Ping) {
                    Network.Ping response = (Network.Ping)object;
                    System.out.println("Ping has been recived at "+response.pingTime);
                }
            }
        });

    }

}
