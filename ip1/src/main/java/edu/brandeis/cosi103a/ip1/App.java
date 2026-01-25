package edu.brandeis.cosi103a.ip1;

public class App 
{
    public static void main(String[] args) 
    {
        // Create a new game with automated players
        Game game = new Game();
        
        // Add 2 automated players
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        
        game.addPlayer(player1);
        game.addPlayer(player2);
        
        // Play the game for 10 turns (automated, no human input)
        game.playGame(10);
    }
}