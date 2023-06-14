package com.duszki.blackjack.shared.events;

import com.duszki.blackjack.shared.*;

import java.util.ArrayList;
import java.util.LinkedList;

public class TransferGameStateEvent {

    public ArrayList<HandTransfer> otherPlayersHands;
    public int yourPoints;

    public HandTransfer dealerHand;

    public HandTransfer yourHand;

    public boolean isYourTurn;

    public boolean isGameOver;

    public TransferGameStateEvent() {

    }

}
