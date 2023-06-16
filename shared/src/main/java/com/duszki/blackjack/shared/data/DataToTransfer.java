package com.duszki.blackjack.shared.data;

import com.duszki.blackjack.shared.player.HandTransferData;
import java.util.ArrayList;

import com.duszki.blackjack.shared.player.*;

public class DataToTransfer {


    public int yourTokens;

    public ArrayList<PlayerTransferData> otherPlayers;

    public HandTransferData dealerHand;

    public HandTransferData yourHand;

    public boolean isYourTurn;

    public boolean isGameOver;

    public DataToTransfer() {

    }
    public int getYourTokens() {
        return yourTokens;
    }

    public void setYourTokens(int yourTokens) {
        this.yourTokens = yourTokens;
    }


    public ArrayList<PlayerTransferData> getOtherPlayers() {
        return otherPlayers;
    }

    public void setOtherPlayers(ArrayList<PlayerTransferData> otherPlayers) {
        this.otherPlayers = otherPlayers;
    }

    public HandTransferData getDealerHand() {
        return dealerHand;
    }

    public void setDealerHand(HandTransferData dealerHand) {
        this.dealerHand = dealerHand;
    }

    public HandTransferData getYourHand() {
        return yourHand;
    }

    public void setYourHand(HandTransferData yourHand) {
        this.yourHand = yourHand;
    }

    public boolean isYourTurn() {
        return isYourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        isYourTurn = yourTurn;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}
