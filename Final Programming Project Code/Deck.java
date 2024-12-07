import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private ArrayList<Card> deck; //deck list to store all cards


    //constructor
    public Deck() {
        deck = new ArrayList<>();//initializes the deck arraylist
        initializeDeck();//call initializeDeck function
    }

    /*
   1. Deck Initialization Algorithm

   Creates a complete UNO deck with all colors, numbers, and special cards.
   Steps:
    1. Define array for colors (Red, Green, Blue, Yellow)
    2. Define array for values (0-9, Skip, Reverse, Draw Two).
    3.For each color, create two copies of each card except for 0 (only one copy).
      Use nested loop the outer loop will iterate through each color, and the inner loop will iterate through each value
      Create a new card for each color and each value then add it to deck, then again create a new card except for card with value one and then add the card onto deck
    4.Add four Wild Cards with value = color change.
      Add four Wild with value=Draw Four.

     */
    private void initializeDeck() {
        String[] colors = {"Red", "Green", "Blue", "Yellow"};
        String[] values = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Skip", "Reverse", "Draw Two"};

        for (String color : colors) {
            for (String value : values) {
                deck.add(new Card(color, value));
                if (!value.equals("0")) deck.add(new Card(color, value)); // Two copies except 0
            }
        }

        for (int i = 0; i < 4; i++) {
            deck.add(new Card("Wild", "Color Change"));
            deck.add(new Card("Wild", "Draw Four"));
        }
    }


    /*
    2. Deck randomize Shuffle Algorithm

    Steps:
      1.Start from the end of the deck
      2.Iterate from the last index (deck.size() - 1) to the first index (0).

      3. Generate a random index:
          Use random.nextInt(i + 1) to generate a random index between 0 and i (inclusive).

      4. Swap cards:
         Swap the card at the current index i with the card at the randomly chosen index j.

      5. Repeat:
         Continue until all cards have been shuffled.
     */
    public void shuffle() {
        Random random = new Random(); // Random number generator

        //loop on deck, starts from the last element of deck
        for (int i = deck.size() - 1; i > 0; i--) {
            // generate and pick a random index from 0 to i
            int j = random.nextInt(i + 1);

            // Swap deck[i] with the element at deck[j]
            Card temp = deck.get(i); //temp car for swapping
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }
    }

    public Card drawCard() {
        //check if deck is empty
        if (deck.isEmpty()) {
            //display the message that deck is empty
            System.out.println("The deck is empty!");
        }
        //remove the last card from deck
        Card card= deck.remove(deck.size() - 1);

        //return the removed card
        return card;
    }

    public void returnCardToDeck(Card card) {
        deck.add(card);
    }
}
