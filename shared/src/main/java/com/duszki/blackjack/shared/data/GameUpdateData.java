package com.duszki.blackjack.shared.data;

import com.duszki.blackjack.shared.models.Hand;

import java.util.ArrayList;

public class GameUpdateData {

    private PlayerData yourData;

    private ArrayList<PlayerData> otherPlayersData;

    private Hand dealerHand;

    public GameUpdateData() {
        yourData = new PlayerData();
        otherPlayersData = new ArrayList<>();
        Hand dealerHand = new Hand();
    }

    public PlayerData getYourData() {
        return yourData;
    }

    public void setYourData(PlayerData yourData) {
        this.yourData = yourData;
    }

    public ArrayList<PlayerData> getOtherPlayersData() {
        return otherPlayersData;
    }

    public void setOtherPlayersData(ArrayList<PlayerData> otherPlayersData) {
        this.otherPlayersData = otherPlayersData;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public void setDealerHand(Hand dealerHand) {
        this.dealerHand = dealerHand;
    }

}
