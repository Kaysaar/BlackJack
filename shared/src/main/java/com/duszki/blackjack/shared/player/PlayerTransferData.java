package com.duszki.blackjack.shared.player;

import java.util.ArrayList;
import com.duszki.blackjack.shared.models.Card;

public class PlayerTransferData {

    String name;

    private ArrayList<Card> cards;

    private int balance;

    public PlayerTransferData(String Name , ArrayList<Card> Cards ) {
        setName(Name);
        setCards(Cards);
    }

    public PlayerTransferData() {

    }
    public boolean isYourTurn;
    public boolean isGameOver;
    public boolean hasLostRound;
    public boolean getHaveLostRound() {
        return isGameOver;
    }

    public void setHavelostRound(boolean _hasLostRound) {
        hasLostRound = _hasLostRound;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }


    public boolean isYourTurn() {
        return isYourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        isYourTurn = yourTurn;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards){
        this.cards = cards;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

}
