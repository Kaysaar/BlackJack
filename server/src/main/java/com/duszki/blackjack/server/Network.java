package com.duszki.blackjack.server;

import com.duszki.blackjack.server.Card.Card;
import com.duszki.blackjack.server.Card.Deck;
import com.duszki.blackjack.server.Card.Hand;
import com.duszki.blackjack.server.Card.Shoe;
import com.duszki.blackjack.server.Player.Player;
import com.duszki.blackjack.server.Player.PlayerServerData;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Network {
    static public final int port = 5000;
    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(LocalDateTime.class);
        kryo.register(String[].class);
        kryo.register(Boolean.class);
        kryo.register(Network.reqPing.class);
        kryo.register(Date.class);
        kryo.register(Network.Ping.class);
        kryo.register(PlayerServerData.class);
        kryo.register(Card.class);
        kryo.register(Hand.class);
        kryo.register(ArrayList.class);
        kryo.register(Network.increaseCash.class);
        kryo.register(Network.increasePoints.class);
    }

    static public class Ping {
        public LocalDateTime pingTime;
    }
    static public class reqPing {
        public boolean requested;
    }
    static public class increasePoints {
        public boolean requested;
    }
    static public class increaseCash {
        public boolean requested;
    }

}
