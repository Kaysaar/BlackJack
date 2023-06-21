package com.duszki.blackjack.shared.network;

import com.duszki.blackjack.shared.data.DataToTransfer;
import com.duszki.blackjack.shared.data.WinnersOfRound;
import com.duszki.blackjack.shared.events.*;
import com.duszki.blackjack.shared.models.Card;
import com.duszki.blackjack.shared.models.Hand;
import com.duszki.blackjack.shared.player.HandTransferData;
import com.duszki.blackjack.shared.player.PlayerTransferData;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import java.util.ArrayList;
import java.util.Date;

public class Network {

    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();

        kryo.register(String.class);
        kryo.register(Boolean.class);

        kryo.register(Date.class);

        kryo.register(Card.class);
        kryo.register(Hand.class);
        kryo.register(ArrayList.class);

        kryo.register(JoinRequestEvent.class);
        kryo.register(JoinResponseEvent.class);
        kryo.register(DoubleDownEvent.class);
        kryo.register(EndTurnEvent.class);
        kryo.register(GameStartedEvent.class);
        kryo.register(NotValidatedToDoEvent.class);
        kryo.register(RequestGameStartEvent.class);
        kryo.register(StandEvent.class);
        kryo.register(YourTurnEvent.class);
        kryo.register(HandTransferData.class);
        kryo.register(PlayerTransferData.class);
        kryo.register(DataToTransfer.class);
        kryo.register(WinnersOfRound.class);
        kryo.register(HitEvent.class);


    }

}
