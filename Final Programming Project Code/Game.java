import java.util.*;

// Represents the core logic of the UNO game, managing players, the deck, and the discard pile
public class Game {

    // A queue to manage the turn order of players
    //I used Queue to store player because the Queue ensures that players take their turns in the correct order (FIFO: First-In, First-Out).
    private Queue<Player> players;

    // A stack to manage the discard pile, where the last card played is always on top
    //I used Stack to store discardPile because A Stack inherently provides Last-In, First-Out (LIFO)  functionality, where the most recently played card (push()) is the first card retrieved (peek() or pop()).
    private Stack<Card> discardPile;

    // The deck of cards used in the game (Object of Deck class)
    private Deck deck;

    // Tracks the next selected color in case of a "Wild" or "Draw Four" card
    private String nextColor = null;

    // Flags for special card effects
    private boolean drawFour = false; // Indicates if the "Draw Four" effect is active
    private boolean skip = false;    // Indicates if the next player's turn should be skipped
    private boolean reverse = false; // Indicates if the play order should be reversed
    private boolean drawTwo = false; // Indicates if the "Draw Two" effect is active

    // Constructor to initialize the game with a list of players
    public Game(List<Player> playerList) {
        // Initialize the queue with players, using a LinkedList for efficient operations
        players = new LinkedList<>(playerList);

        // Initialize the discard pile as a stack, representing the last card played
        discardPile = new Stack<>();

        // Initialize the deck of cards
        deck = new Deck();

        // Shuffle the deck to randomize the cards at the start of the game
        deck.shuffle();
    }


    

    public void initializeGame() {
        // Deal 7 cards to each player
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.addCard(deck.drawCard());
            }
        }

        // Draw the first card for the discard pile
        Card firstCard;
        do {
            firstCard = deck.drawCard();
            if (isSpecialCard(firstCard)) {
                deck.returnCardToDeck(firstCard); // Place the card back into the deck
                deck.shuffle(); // Shuffle the deck to maintain randomness
            }
        } while (isSpecialCard(firstCard));

        discardPile.push(firstCard); // Push a valid card onto the discard pile
    }

    // Helper method to check if a card is special
    private boolean isSpecialCard(Card card) {
        String value = card.getValue(); // Assuming Card has a getValue() method
        return value.equalsIgnoreCase("Skip") || value.equalsIgnoreCase("Reverse") ||
                value.equalsIgnoreCase("Draw Two") || value.equalsIgnoreCase("Draw Four") ||
                value.equalsIgnoreCase("Color Change");
    }


    /*

    3.PlayGame Algorithm:

     Step 1: Start Game Loop
        Input: players queue, deck, discardPile, and game status flags (drawTwo, drawFour, reverse, nextColor, skip).
        Start an infinite loop to continue the game until a player wins.

    Step 2: Process Current Player's Turn
        Dequeue the first player from the players queue as the currentPlayer.
        If there are no special game effects (e.g., drawTwo, drawFour, reverse, skip, nextColor) then Display the top card of the discardPile.
        If a color was previously selected (e.g., via a wild card) then Display the selected color (nextColor).
        Announce the currentPlayer's turn.

    Step 3: Check for Special Game Effects
        Check Victory Condition If currentPlayer.hasWon(), announce the player as the winner and exit the loop.

        Handle Draw Two:
        If drawTwo is active then Call drawTwo(currentPlayer) to draw two cards.
        Requeue currentPlayer and continue to the next turn.

        Handle Draw Four:
        If drawFour is active then Call drawFour(currentPlayer) to draw four cards.
        Requeue currentPlayer and continue to the next turn.

        Handle Skip:
        If skip is active then Call skipPlayer(currentPlayer) to skip the turn.
        Continue to the next turn.

   Step 4: Player Chooses and Plays a Card
        Show the currentPlayer's hand and prompt them to choose a card by index.
        Ensure the selected index is within range. If invalid, re-prompt until valid.
        Retrieve the chosen Card from the player's hand.
        If a previously selected color (nextColor) exists then Update the top card's color to nextColor.

  Step 5: Check for Special Card Actions

       Skip Card:
       If the card is a Skip and matches the top card’s color or value then Announce the Skip action, set skip to true, place the card on the discardPile, and requeue currentPlayer.

       Wild Draw Four:
       If the card is Wild Activate drawFour, prompt the player to select a new color, and set nextColor.
       Place the card on the discardPile.

       Draw Two:
       If the card is Draw Two and matches the top card’s color or value then Activate drawTwo, prompt the player to select a new color, and set nextColor.
       Place the card on the discardPile.

       Reverse Card:
       If the card is Reverse and matches the top card’s color then Announce the Reverse action, reverse the game direction, and requeue currentPlayer.

       Wild Change Color:
       If the card is Wild Change Color then Prompt the player to select a new color, set nextColor, and place the card on the discardPile.

       Regular Card:
       If the card matches the top card’s color or value then Announce the card placement and place it on the discardPile.

       Invalid Card:
       If the card cannot be placed then Announce the invalid choice and draw a card from the deck for the currentPlayer.

   Step 6: End Player's Turn
        Requeue the currentPlayer at the end of the players queue.
        Continue the game loop.

  Step 7: End Game
        Exit the loop once a player wins
     */
    public void playGame() {
        Scanner scanner=new Scanner(System.in);//create scanner object to get input from user
        //create whole play game logic inside infinite while loop so that the loop never ends until a player wins
        while (true) {
            Player currentPlayer = players.poll();    //get a player from players Queue

            //check if previous player did not place a card with following  drawTwo,drawFour, reverse, or color change or skip
            if(!drawTwo && !drawFour && !reverse && nextColor==null && !skip)
            System.out.println("Current Card: " + discardPile.peek());// display top card of discard Pile (from Stack)

            //if next color is not null then display next color to placed instead of top card from discardPile Stack
            if(nextColor!=null)
            {
                System.out.println("Selected color: " + nextColor);
            }
            // Display current player's name
            System.out.println(currentPlayer.getName() + "'s Turn.");


            // Logic for playing cards or drawing cards

            if (currentPlayer.hasWon()) {
                System.out.println(currentPlayer.getName() + " wins!");
                break;
            }

            //if previously draw Two card was placed by last player
            if(drawTwo) {
                //call drawTwo method
                drawTwo(currentPlayer);
                //add player to queue
                players.offer(currentPlayer);
                continue;
            }
            //if previously draw Four card was placed by last player
            else if(drawFour)
            {
                //call drawTwo method
                drawFour(currentPlayer);
                //add player to queue
                players.offer(currentPlayer);
                continue;
            }

            //if previously Skip card was placed by last player
            if(skip)
            {
                skipPlayer(currentPlayer);
                continue;
            }

                //display card of current player
                currentPlayer.showHand();
            //display msg to use to choose a card from a range of number like choose a card (1-4)
                currentPlayer.showChooseCard();

                int index = scanner.nextInt();//input user choice
            //check if user enter index in a valid range
                while (index <= 0 || index > currentPlayer.totalCards()) {
                    System.out.println("Invalid input");
                    //display card of current player
                    currentPlayer.showHand();
                    currentPlayer.showChooseCard();
                    index = scanner.nextInt();
                }


                //get chosen card from hand of user, but not removed it from hand
                Card card = currentPlayer.getCard(--index);


                //get the top card from pileCardStack
               Card pileCard=discardPile.peek();

               //if color is not null set the top color
            if(nextColor!=null)
                pileCard.setColor(nextColor);




            //if chosen card value is skip
             if( (card.getValue().equalsIgnoreCase("Skip")))
            {
                //check if color or symbol matches with the top card of discardPile stack
                if(card.getColor().equalsIgnoreCase(pileCard.getColor()) || card.getValue().equalsIgnoreCase(pileCard.getValue()) )
                {
                    //display a msg on console that user has placed the card
                    System.out.println("Player "+currentPlayer.getName()+" Placed  "+card.getColor()+" Card with Symbol "+card.getValue());

                    skip=true;//set skip flag to true
                    //remove the card from player's hand
                    card= currentPlayer.playCard(index);
                    //place the card on the top of discardPile's stack
                    discardPile.push(card);
                    //add the player to queue again
                    players.offer(currentPlayer);
                    //go to the start of the loop
                    continue;
                }


            }

             //if previous player chooses a color for next move
             if(nextColor!=null)
            {

                //if color matches with the current card color that current player is going to placed on discardPile
                if(nextColor.equalsIgnoreCase(card.getColor()))
                {
                    card= currentPlayer.playCard(index);
                    discardPile.push(card);
                }
                //if color does not matches
                else
                {
                    System.out.println("Invalid card chosen, you have to Draw a card from deck");
                    currentPlayer.addCard(deck.drawCard());
                    System.out.println("card is picked");
                }
                nextColor=null;
                //add player to queue
                players.offer(currentPlayer);
                continue;
            }

             //if card is wild and value is drawFour
           else if(card.getColor().equalsIgnoreCase("Wild") && card.getValue().equalsIgnoreCase("Draw Four"))
            {
                drawFour=true; // set drawFour Flag so that next player should draw 4 cards from Deck
                System.out.println("Player "+currentPlayer.getName()+" Placed Wild Card with value Draw Four");

                //get color choice from player for next move
                System.out.println("**Select next color**");
                System.out.println("1.Red\t2.Green\t3.Blue\t4.Yellow");
                System.out.println("Choose a Color (1-4): ");
                int color=scanner.nextInt();
                while (color<1 || color>4)
                {
                    System.out.println("Invalid Choice! ");
                    System.out.println("1.Red\t2.Green\t3.Blue\t4.Yellow");
                    System.out.println("Choose a Color (1-4): ");
                    color=scanner.nextInt();
                }
                switch (color)
                {
                    case 1:
                        nextColor="Red";
                        break;
                    case 2:
                        nextColor="Green";
                        break;
                    case 3:
                        nextColor="Blue";
                        break;
                    case 4:
                        nextColor="Yellow";
                        break;

                }

                card= currentPlayer.playCard(index);
                discardPile.push(card);

            }

           //if card color matches with top card and value is drawTwo
            else if(card.getColor().equalsIgnoreCase(pileCard.getColor()) && card.getValue().equalsIgnoreCase("Draw Two"))
            {
                drawTwo=true;//set drawTwo flag to true
                System.out.println("Player "+currentPlayer.getName()+" Placed  Card with value Draw Two");
                System.out.println("**Select next color**");
                System.out.println("1.Red\t2.Green\t3.Blue\t4.Yellow");
                System.out.println("Choose a Color (1-4): ");
                int color=scanner.nextInt();
                while (color<1 || color>4)
                {
                    System.out.println("Invalid Choice! ");
                    System.out.println("1.Red\t2.Green\t3.Blue\t4.Yellow");
                    System.out.println("Choose a Color (1-4): ");
                    color=scanner.nextInt();
                }
                switch (color)
                {
                    case 1:
                        nextColor="Red";
                        break;
                    case 2:
                        nextColor="Green";
                        break;
                    case 3:
                        nextColor="Blue";
                        break;
                    case 4:
                        nextColor="Yellow";
                        break;

                }
                card= currentPlayer.playCard(index);
                discardPile.push(card);
            }
             //if card color matches with top card and value is Reverse
            else if(card.getColor().equals(pileCard.getColor()) && card.getValue().equalsIgnoreCase("Reverse"))
            {
                System.out.println("Player "+currentPlayer.getName()+" Placed  Card with value Reverse");
                card= currentPlayer.playCard(index);
                discardPile.push(card);
               reverse(currentPlayer);
               continue;
            }

             //if card is wild and value is Color change
            else if(card.getColor().equalsIgnoreCase("Wild") && card.getValue().equalsIgnoreCase("Color Change"))
            {
                System.out.println("Player "+currentPlayer.getName()+" Placed  Wild Card with value Change Color ");

                //get player color choice
                System.out.println("1.Red\t2.Green\t3.Blue\t4.Yellow");
                System.out.println("Choose a Color (1-4): ");
                int color=scanner.nextInt();
                while (color<1 || color>4)
                {
                    System.out.println("Invalid Choice! ");
                    System.out.println("1.Red\t2.Green\t3.Blue\t4.Yellow");
                    System.out.println("Choose a Color (1-4): ");
                    color=scanner.nextInt();
                }
                switch (color)
                {
                    case 1:
                        nextColor="Red";
                        break;
                    case 2:
                        nextColor="Green";
                        break;
                    case 3:
                        nextColor="Blue";
                        break;
                    case 4:
                        nextColor="Yellow";
                        break;

                }


                card= currentPlayer.playCard(index);
                discardPile.push(card);
            }

            //if card color match or value match with top card
            else if(card.getColor().equals(pileCard.getColor()) || card.getValue().equals(pileCard.getValue()))
            {
                System.out.println("Player "+currentPlayer.getName()+" Placed  "+card.getColor()+" Card with value "+card.getValue());
                card= currentPlayer.playCard(index);
                discardPile.push(card);
            }

            //if Player placed invalid card , he should draw a card from deck
            else
            {
                System.out.println("The chosen card cannot be placed! ");
                System.out.println("You must Draw a Card from deck");
                currentPlayer.addCard(deck.drawCard());
            }




            //add player to queue
            players.offer(currentPlayer);
        }
    }

    // Method to handle skipping a player's turn
    private void skipPlayer(Player currentPlayer) {
        // Reset the skip flag to false as the action is being processed
        skip = false;
        // Inform all players that the current player is being skipped
        System.out.println("Player " + currentPlayer.getName() + " is skipped");
        // Add the skipped player back to the end of the queue for the next round
        players.offer(currentPlayer);
    }

    // Method to handle the "Draw Four" card action
    public void drawFour(Player currentPlayer) {
        // Reset the drawFour flag to false as the action is being processed
        drawFour = false;
        // Loop to make the current player draw 4 cards from the deck
        for (int i = 0; i < 4; i++) {
            // Add a card from the deck to the player's hand
            currentPlayer.addCard(deck.drawCard());
        }
        // Inform all players about the action
        System.out.println("Player " + currentPlayer.getName() + " has picked 4 cards from the deck");
        // Display the total number of cards the player now has
        System.out.println("Player " + currentPlayer.getName() + " now has " + currentPlayer.totalCards() + " cards");
        // Print a separator for better readability in the console
        System.out.println("---------------------------------------------------------------------------------");
    }

    // Method to handle the "Draw Two" card action
    public void drawTwo(Player currentPlayer) {
        // Reset the drawTwo flag to false as the action is being processed
        drawTwo = false;
        // Loop to make the current player draw 2 cards from the deck
        for (int i = 0; i < 2; i++) {
            // Add a card from the deck to the player's hand
            currentPlayer.addCard(deck.drawCard());
        }
        // Inform all players about the action
        System.out.println("Player " + currentPlayer.getName() + " has picked 2 cards from the deck");
        // Display the total number of cards the player now has
        System.out.println("Player " + currentPlayer.getName() + " now has " + currentPlayer.totalCards() + " cards");
        // Print a separator for better readability in the console
        System.out.println("---------------------------------------------------------------------------------");
    }




    //following method will reverse Queue
    public void reverse(Player currentPlayer)
    {

        Stack<Player> stack = new Stack<>();

        // Step 1: Transfer all elements from the queue to the stack
        while (!players.isEmpty()) {
            stack.push(players.poll());
        }

        // Step 2: Transfer all elements from the stack back to the queue
        players.offer(currentPlayer);
        while (!stack.isEmpty()) {
            players.offer(stack.pop());
        }
        currentPlayer=players.poll();
        players.offer(currentPlayer);
    }
}
