package com.duszki.blackjack;

import com.esotericsoftware.kryonet.Client;

public class NetworkManager {

    private static Client client;

    public static void setClient(Client client) {
        NetworkManager.client = client;
    }

    public static Client getClient() {
        return client;
    }

}
