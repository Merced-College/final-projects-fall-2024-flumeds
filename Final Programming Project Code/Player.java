import java.util.ArrayList;

// Following Class Represents a player in the UNO game.
public class Player {

    // The player name
    private String name;

    // A dynamic ArrayList  to store the cards in the player's hand
    //I used ArrayList because it dynamically adjusts its size as cards are added or removed from a player's hand, which is necessary for an UNO game.
    private ArrayList<Card> hand;

    // Constructor
    public Player(String name) {
        this.name = name; // Set the player's name
        hand = new ArrayList<>(); // Initialize an empty ArrayList to store the player's cards
    }

    // Adds a card to the player's hand
    public void addCard(Card card) {
        hand.add(card); // Appends the given card to the end of the ArrayList
    }

    // Removes a card from the player's hand at a specified index and returns it
    public Card playCard(int index) {
        return hand.remove(index); // Removes and returns the card at the specified index
    }

    // Retrieves a card from the player's hand without removing it
    public Card getCard(int index) {
        return hand.get(index); // Returns the card at the specified index
    }

    // Checks if the player has won the game by having no cards left in their hand
    public boolean hasWon() {
        return hand.isEmpty(); // Returns true if the ArrayList is empty, indicating a win
    }

    // Displays all the cards in the player's hand
    public void showHand() {
        System.out.println(name + "'s Hand:");
        // Loop through the hand and display each card with its index
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ": " + hand.get(i)); // Display card index and details
        }
    }

    // Prompts the player to choose a card from their hand
    public void showChooseCard() {
        System.out.println("Choose a Card (1-" + hand.size() + "): "); // Indicate the valid range
    }

    // Returns the total number of cards in the player's hand
    public int totalCards() {
        return hand.size(); // Returns the size of the ArrayList
    }

    // Retrieves the player's name
    public String getName() {
        return name;
    }
}
