package edu.brandeis.cosi103a.ip1;

import java.util.Scanner;

public class App 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        
        // Create a new game
        Game game = new Game(scanner);
        
        // Add players
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        
        game.addPlayer(player1);
        game.addPlayer(player2);
        
        // Play the game for 5 turns
        game.playGame(5);
        
        scanner.close();
    }
}