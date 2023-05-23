package Card;
public class Card {

    private final int value;
    private final Type color;

    public Card(int value, Type color){
        this.value = value;
        this.color = color;
    }

    public int getValue() {
        return value;
    }

    public Type getColor() {
        return color;
    }
}

