//Following class represents a Card in the Game
public class Card {
    //used to store color of card
    private String color;

    //used to store vale or symbol of card
    private String value;

    //constructor
    public Card(String color, String value) {
        this.color = color;
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public String getValue() {
        return value;
    }

    public void setColor(String color) {
        this.color = color;
    }

    //String representation of Card
    @Override
    public String toString() {
        return color + " " + value;
    }
}
