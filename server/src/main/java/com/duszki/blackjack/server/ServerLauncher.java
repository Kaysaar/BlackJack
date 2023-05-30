package com.duszki.blackjack.server;
import com.duszki.blackjack.server.Card.Hand;
import com.duszki.blackjack.server.Card.Shoe;
import com.duszki.blackjack.server.Player.PlayerServerData;
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
    public boolean isRegistered(int connettion){
        for (int Id : storedData.keySet()) {
            if(Id==connettion){
                return true;
            }
        }
        return false;
    }
    Shoe allDecks = new Shoe();
    public ServerLauncher () throws IOException {
        server = new Server() {
            protected Connection newConnection () {
                // By providing our own connection implementation, we can store per
                // connection state without a connection ID to state look up.
                return new ServerConnettion();
            }
        };

        // For consistency, the classes to be sent over the network are
        // registered by the same method for both the client and server.
        Network.register(server);

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
                    PlayerServerData response = storedData.get(connection.getID());
                    response.getPlayerHand().setPoints(response.getPlayerHand().getPoints()+10);
                    Log.info("BLACKJACK-SERVER","Ping has been requested by Client, increasing points on hand by 10 ");
                    server.sendToTCP(connection.getID(), response);

                }
                if (object instanceof Network.increaseCash) {
                    PlayerServerData response = storedData.get(connection.getID());
                    response.setCoins(response.getCoins()+10);
                    Log.info("BLACKJACK-SERVER","Ping has been requested by Client, increasing amount of cash by 10");
                    server.sendToTCP(connection.getID(), response);

                }

            }
        });
        server.bind(Network.port);
        server.start();

        // Open a window to provide an easy way to stop the server.
        JFrame frame = new JFrame("Chat Server");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosed (WindowEvent evt) {
                server.stop();
            }
        });
        frame.getContentPane().add(new JLabel("Close to stop the chat server."));
        frame.setSize(320, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    static class ServerConnettion extends Connection {
        public String name;
    }
        public static void main(String[] args) throws IOException {
        new ServerLauncher();
    }
}
