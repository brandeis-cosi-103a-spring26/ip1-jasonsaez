package edu.brandeis.cosi103a.ip1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private List<Player> players;
    private List<Card> supply;
    private int turnCount;
    private Scanner scanner;

    public Game(Scanner scanner) {
        this.players = new ArrayList<>();
        this.supply = new ArrayList<>();
        this.turnCount = 0;
        this.scanner = scanner;
        initializeSupply();
    }

    private void initializeSupply() {
        // Add money cards to supply
        supply.add(new Card("Copper", CardType.MONEY, 0, 1));
        supply.add(new Card("Silver", CardType.MONEY, 3, 2));
        supply.add(new Card("Gold", CardType.MONEY, 6, 3));
        
        // Add victory cards to supply
        supply.add(new Card("Estate", CardType.VICTORY, 2, 1));
        supply.add(new Card("Duchy", CardType.VICTORY, 5, 3));
        supply.add(new Card("Province", CardType.VICTORY, 8, 6));
        
        // Add basic action cards
        supply.add(new Card("Village", CardType.ACTION, 3, 2));
        supply.add(new Card("Market", CardType.ACTION, 5, 1));
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.initializeDeck();
        player.drawCards(5);
    }

    public void playGame(int maxTurns) {
        System.out.println("Welcome to Automation: The Game!");
        System.out.println("Players: " + players.size());
        
        for (turnCount = 1; turnCount <= maxTurns; turnCount++) {
            System.out.println("\n=== Turn " + turnCount + " ===");
            for (Player player : players) {
                playTurn(player);
            }
        }
        
        endGame();
    }

    private void playTurn(Player player) {
        System.out.println("\n" + player.getName() + "'s turn:");
        
        // Action phase
        player.setActions(1);
        
        // Buy phase
        player.playMoneyCards();
        System.out.println("Money available: " + player.getMoney());
        
        // Show hand
        System.out.println("Hand:");
        for (int i = 0; i < player.getHand().size(); i++) {
            System.out.println("  " + i + ": " + player.getHand().get(i));
        }
        
        // Buy a card
        buyPhase(player);
        
        // Cleanup
        player.discardHand();
        player.drawCards(5);
    }

    private void buyPhase(Player player) {
        System.out.println("\nAvailable cards to buy:");
        for (int i = 0; i < supply.size(); i++) {
            Card card = supply.get(i);
            System.out.println("  " + i + ": " + card);
        }
        
        System.out.print("Enter card number to buy (or -1 to skip): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice >= 0 && choice < supply.size()) {
                Card cardToBuy = supply.get(choice);
                if (player.getMoney() >= cardToBuy.getCost()) {
                    player.buyCard(cardToBuy);
                    System.out.println("Bought: " + cardToBuy.getName());
                } else {
                    System.out.println("Not enough money!");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, skipping buy phase.");
        }
    }

    private void endGame() {
        System.out.println("\n=== Game Over ===");
        System.out.println("Final Scores:");
        
        Player winner = null;
        int highestScore = -1;
        
        for (Player player : players) {
            int score = player.calculateScore();
            System.out.println(player.getName() + ": " + score + " points");
            if (score > highestScore) {
                highestScore = score;
                winner = player;
            }
        }
        
        if (winner != null) {
            System.out.println("\n" + winner.getName() + " wins!");
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Card> getSupply() {
        return supply;
    }
}
