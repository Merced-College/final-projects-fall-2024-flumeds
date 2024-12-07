import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

public class UnoGame {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);// create scanner to get user input
        List<Player> players=new ArrayList<>();// create a dynamic list to store players, because we dont know in advance that how many player will be in the game
        System.out.println("Enter number of players: ");
        int totalPlayers=scanner.nextInt();
        for (int i=1;i<=totalPlayers;i++)
        {
            System.out.println("Enter name of Player"+i+": ");
            String name=scanner.next();
            players.add(new Player(name));

        }
        //create game object
        Game game = new Game(players);
        //call initializeGame() method
        game.initializeGame();
        //finally call playGame() method of game object
        game.playGame();
    }
}
