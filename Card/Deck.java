package Card;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static Card.Type.*;

public class Deck {
    public static final int STRONGCARD = 10;
    private ArrayList<Card> deck;

    public Deck(){
        deck = new ArrayList<>();
        addCards(Spades);
        addCards(Hearts);
        addCards(Diamonds);
        addCards(Clubs);
        Collections.shuffle(deck);
    }

    void addCards(Type colors){
        for (int i = 1; i < 14; i++) {
            Card card = new Card(i,colors);
            deck.add(card);
        }
    }
}
