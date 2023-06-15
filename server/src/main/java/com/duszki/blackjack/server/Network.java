package com.duszki.blackjack.server;

import com.duszki.blackjack.server.Card.Card;
import com.duszki.blackjack.server.Card.Hand;
import com.duszki.blackjack.server.Player.PlayerServerData;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

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
        kryo.register(reqPlayerData.class);
        kryo.register(Date.class);
        kryo.register(Network.Ping.class);
        kryo.register(PlayerServerData.class);
        kryo.register(Card.class);
        kryo.register(Hand.class);
        kryo.register(ArrayList.class);
        kryo.register(Network.increaseCash.class);
        kryo.register(Network.increasePoints.class);
        kryo.register(Network.decreaseCash.class);
        kryo.register(Network.decreasePoints.class);
        kryo.register(Request.class);
        kryo.register(RequestType.class);
    }

    static public class Ping {
        public LocalDateTime pingTime;
    }

    static public class reqPlayerData {
        public boolean requested;
    }
    static public class increasePoints {
        public boolean requested;
    }
    static public class increaseCash {
        public boolean requested;
    }
    static public class decreaseCash {
        public boolean requested;
    }
    static public class decreasePoints {
        public boolean requested;
    }
    static public class RequestType{
        public Request request;
    }

}
