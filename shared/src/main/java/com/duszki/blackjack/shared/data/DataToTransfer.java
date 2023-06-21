package com.duszki.blackjack.shared.data;

import com.duszki.blackjack.shared.models.Hand;
import com.duszki.blackjack.shared.player.HandTransferData;
import java.util.ArrayList;

import com.duszki.blackjack.shared.player.*;

public class DataToTransfer {



    public ArrayList<PlayerTransferData> otherPlayers;

    public Hand dealerHand;



    public DataToTransfer() {

    }


    public ArrayList<PlayerTransferData> getOtherPlayers() {
        return otherPlayers;
    }

    public void setOtherPlayers(ArrayList<PlayerTransferData> otherPlayers) {
        this.otherPlayers = otherPlayers;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public void setDealerHand(Hand dealerHand) {
        this.dealerHand = dealerHand;
    }

}
