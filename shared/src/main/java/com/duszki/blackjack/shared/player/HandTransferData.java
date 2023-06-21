package com.duszki.blackjack.shared.player;

import java.util.ArrayList;
import com.duszki.blackjack.shared.models.Card;
public class HandTransferData {

        private ArrayList<Card> cards;

        public HandTransferData() {

        }

        public void addCard(Card card) {
            cards.add(card);
        }
}
