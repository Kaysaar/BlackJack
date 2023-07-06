package com.duszki.blackjack.server;

import com.duszki.blackjack.server.Card.Deck;
import com.duszki.blackjack.shared.models.Card;
import org.junit.Test;
import org.junit.Assert;

public class DeckTest {

    @Test
    public void testCard() {
        Card card = new Card("Spades", "Ace");
        Assert.assertEquals("Spades", card.getSuit());
        Assert.assertEquals("Ace", card.getRank());
    }

    @Test
    public void testDeck() {
        Deck deck = new Deck();
        Assert.assertEquals(52, deck.getCardsInDeck().size());
    }

    @Test
    public void testShuffle() {
        Deck deck = new Deck();
        Deck deck2 = new Deck();

        Card card = deck.getCardsInDeck().pop();
        Card card2 = deck2.getCardsInDeck().pop();

        Assert.assertNotEquals(card, card2);
    }

    @Test
    public void testRemoveCard() {
        Deck deck = new Deck();
        Card card = deck.removeCard();
        Assert.assertEquals(51, deck.getCardsInDeck().size());

        Card card2 = deck.removeCard();
        Assert.assertEquals(50, deck.getCardsInDeck().size());

        Assert.assertNotEquals(card, card2);

    }

}
