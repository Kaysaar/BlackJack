package com.duszki.blackjack.server.Card;

import com.duszki.blackjack.shared.card.Card;

import java.util.ArrayList;

public class Hand {

    private static final int ACE_LOW_VALUE = 1;
    private static final int ACE_HIGH_VALUE = 11;
    private static final int HAND_MAX_VALUE = 21;
    private static final int STRONG_CARD_VALUE = 10;
    private ArrayList<Card> cardsInHand;

    private int handValue;

    public Hand() {
        cardsInHand = new ArrayList<>();
        handValue = 0;
    }

    public int getHandValue() {
        return handValue;
    }

    public void addCard(Card card) {
        cardsInHand.add(card);
        updateHandValue();
    }

    private void updateHandValue() {
        int points = 0;

        int aceCount = 0;

        for (Card card : cardsInHand) {
            switch (card.getRank()) {
                case "ace" -> {
                    aceCount++;
                }
                case "jack", "queen", "king" -> points += STRONG_CARD_VALUE;
                default -> points += Integer.parseInt(card.getRank());
            }
        }

        for (int i = 0; i < aceCount; i++) {
            if (points + ACE_HIGH_VALUE > HAND_MAX_VALUE) {
                points += ACE_LOW_VALUE;
            } else {
                points += ACE_HIGH_VALUE;
            }
        }

        handValue = points;

    }

    public void clearHand() {
        cardsInHand.clear();
    }
}
