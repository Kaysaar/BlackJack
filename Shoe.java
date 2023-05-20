import java.util.ArrayList;

public class Shoe {
    private ArrayList<Deck> shoe = new ArrayList<>();

    public Shoe(){
        for(int i = 0; i < 9; i++){
            Deck deck = new Deck();
            shoe.add(deck);
        }
    }
}
