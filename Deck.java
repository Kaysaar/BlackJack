import java.util.ArrayList;

public class Deck {
    public static final int STRONGCARD = 10;
    private ArrayList<Card> deck;

    public Deck(){
        deck = new ArrayList<>();
        for (int j = 1; j<5; j++) {
            for (int i = 2; i < 12; i++) {
                Card card = new Card(i);
                deck.add(card);
            }

            for (int i = 0; i < 3; i++){
                Card card = new Card(STRONGCARD);
                deck.add(card);
            }
        }
    }
}
