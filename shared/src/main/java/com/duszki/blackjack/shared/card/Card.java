package com.duszki.blackjack.shared.card;

import java.util.Arrays;
import java.util.List;

public class Card {
    private static final int STRONG_CARD_VALUE = 10;
    private static final int ACE_CARD_HIGH_VALUE = 11;

    private final String suit;
    private final String rank;

    public static List<String> getValidSuits() {
        return Arrays.asList("spades", "hearts", "diamonds", "clubs");
    }

    public static List<String> getValidRanks() {
        return Arrays.asList("ace", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "jack", "queen", "king");
    }

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public String toString() {
        return rank + " of " + suit;
    }

}
