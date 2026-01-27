package edu.brandeis.cosi103a.ip1;

public class App 
{
    public static void main(String[] args) 
    {
        System.out.println("=== Automation: The Game ===");
        
        // Create automated game with 2 computer players
        Game game = new Game();
        Player player1 = new Player("Computer 1", false);
        Player player2 = new Player("Computer 2", false);
        
        game.addPlayer(player1);
        game.addPlayer(player2);
        
        // Play the game until Framework cards run out (max 100 turns as safety limit)
        game.playGame(100);
    }
}