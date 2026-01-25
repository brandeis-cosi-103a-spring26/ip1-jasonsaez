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
        // Add cryptocurrency cards to supply
        supply.add(new Card("Bitcoin", CardType.CRYPTOCURRENCY, 0, 1));
        supply.add(new Card("Ethereum", CardType.CRYPTOCURRENCY, 3, 2));
        supply.add(new Card("Dogecoin", CardType.CRYPTOCURRENCY, 6, 3));
        
        // Add automation cards to supply
        supply.add(new Card("Method", CardType.AUTOMATION, 2, 1));
        supply.add(new Card("Module", CardType.AUTOMATION, 5, 3));
        supply.add(new Card("Framework", CardType.AUTOMATION, 8, 6));
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
        System.out.println("Cryptocoins available: " + player.getMoney());
        
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
                    System.out.println("Not enough cryptocoins!");
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
            System.out.println(player.getName() + ": " + score + " Automation Points");
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
