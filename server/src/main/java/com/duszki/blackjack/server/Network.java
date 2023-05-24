package com.duszki.blackjack.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import java.time.LocalDateTime;
import java.util.Date;

public class Network {
    static public final int port = 54555;
    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(String[].class);
        kryo.register(Date.class);
        kryo.register(Ping.class);
        kryo.register(reqPing.class);
    }

    static public class Ping {
        public LocalDateTime pingTime;
    }
    static public class reqPing {
        public boolean requested;
    }


}
