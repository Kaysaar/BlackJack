package com.duszki.blackjack.shared.network;

import com.duszki.blackjack.shared.data.GameUpdateData;
import com.duszki.blackjack.shared.data.PlayerData;
import com.duszki.blackjack.shared.events.*;
import com.duszki.blackjack.shared.models.Card;
import com.duszki.blackjack.shared.models.Hand;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Network {

    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();

        kryo.register(String.class);
        kryo.register(Boolean.class);

        kryo.register(Date.class);

        kryo.register(HashMap.class);

        kryo.register(Card.class);
        kryo.register(Hand.class);
        kryo.register(ArrayList.class);

        // events
        kryo.register(JoinRequestEvent.class);
        kryo.register(JoinResponseEvent.class);
        kryo.register(DoubleDownEvent.class);
        kryo.register(EndTurnEvent.class);
        kryo.register(GameStartedEvent.class);
        kryo.register(NotValidatedToDoEvent.class);
        kryo.register(RequestGameStartEvent.class);
        kryo.register(StandEvent.class);
        kryo.register(YourTurnEvent.class);
        kryo.register(RequestCurrRankingEvent.class);

        kryo.register(HitEvent.class);
        kryo.register(RequestBetEvent.class);
        kryo.register(PlaceBetEvent.class);

        kryo.register(RoundStartEvent.class);

        kryo.register(GameUpdateData.class);
        kryo.register(PlayerData.class);

        kryo.register(EndOfGameEvent.class);

        kryo.register(AcceptBetEvent.class);

        kryo.register(CurrentRoundWinnings.class);


    }

}
