package com.duszki.blackjack.server;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.duszki.blackjack.server.Network;
import com.esotericsoftware.minlog.Log;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
/** Launches the server application. */
public class ServerLauncher {
    Server server;
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

                if (object instanceof Network.reqPing) {
                    Network.Ping response = new Network.Ping();
                    response.pingTime = LocalDateTime.now();
                    Log.info("BLACKJACK-SERVER","Ping has been requested by Client");
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
