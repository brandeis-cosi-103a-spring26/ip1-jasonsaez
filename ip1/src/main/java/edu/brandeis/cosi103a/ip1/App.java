package edu.brandeis.cosi103a.ip1;

import java.util.Scanner;

public class App 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Automation: The Game ===");
        System.out.println("1. Human vs Human");
        System.out.println("2. Human vs Computer");
        System.out.println("3. Computer vs Computer");
        System.out.print("Select game mode (1-3): ");
        
        int mode = 3; // Default to automated
        try {
            mode = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Using Computer vs Computer mode.");
        }
        
        Game game;
        Player player1;
        Player player2;
        
        switch (mode) {
            case 1: // Human vs Human
                game = new Game(scanner);
                player1 = new Player("Player 1", true);
                player2 = new Player("Player 2", true);
                break;
            case 2: // Human vs Computer
                game = new Game(scanner);
                player1 = new Player("Human Player", true);
                player2 = new Player("Computer Player", false);
                break;
            case 3: // Computer vs Computer
            default:
                game = new Game();
                player1 = new Player("Computer 1", false);
                player2 = new Player("Computer 2", false);
                break;
        }
        
        game.addPlayer(player1);
        game.addPlayer(player2);
        
        // Play the game until Framework cards run out (max 100 turns as safety limit)
        game.playGame(100);
        
        if (scanner != null) {
            scanner.close();
        }
    }
}