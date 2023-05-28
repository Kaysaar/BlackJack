package com.duszki.blackjack.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;

import java.time.LocalDateTime;
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

    }

    static public class Ping {
        public LocalDateTime pingTime;
    }
    static public class reqPing {
        public boolean requested;
    }


}
