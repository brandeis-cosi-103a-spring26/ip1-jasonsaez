package edu.brandeis.cosi103a.ip1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private List<Player> players;
    private List<Card> supply;
    private int turnCount;
    private Random random;
    private Scanner scanner;

    public Game() {
        this(null);
    }

    public Game(Scanner scanner) {
        this.players = new ArrayList<>();
        this.supply = new ArrayList<>();
        this.turnCount = 0;
        this.random = new Random();
        this.scanner = scanner;
        initializeSupply();
    }

    private void initializeSupply() {
        // Add cryptocurrency cards to supply with specified quantities
        // Bitcoin x60 (cost: 0, value: 1)
        for (int i = 0; i < 60; i++) {
            supply.add(new Card("Bitcoin", CardType.CRYPTOCURRENCY, 0, 1));
        }
        
        // Ethereum x40 (cost: 3, value: 2)
        for (int i = 0; i < 40; i++) {
            supply.add(new Card("Ethereum", CardType.CRYPTOCURRENCY, 3, 2));
        }
        
        // Dogecoin x30 (cost: 6, value: 3)
        for (int i = 0; i < 30; i++) {
            supply.add(new Card("Dogecoin", CardType.CRYPTOCURRENCY, 6, 3));
        }
        
        // Add automation cards to supply with specified quantities
        // Method x14 (cost: 2, value: 1)
        for (int i = 0; i < 14; i++) {
            supply.add(new Card("Method", CardType.AUTOMATION, 2, 1));
        }
        
        // Module x8 (cost: 5, value: 3)
        for (int i = 0; i < 8; i++) {
            supply.add(new Card("Module", CardType.AUTOMATION, 5, 3));
        }
        
        // Framework x8 (cost: 8, value: 6)
        for (int i = 0; i < 8; i++) {
            supply.add(new Card("Framework", CardType.AUTOMATION, 8, 6));
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.initializeDeck();
        player.drawCards(5);
    }

    public void playGame(int maxTurns) {
        System.out.println("Welcome to Automation: The Game!");
        System.out.println("Players: " + players.size());
        
        // Randomly choose starting player
        Collections.shuffle(players, random);
        System.out.println("Starting player: " + players.get(0).getName());
        
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
        displayHand(player);
        
        // Buy a card (automated or human)
        buyPhase(player);
        
        // Cleanup
        player.discardHand();
        player.drawCards(5);
    }
    
    private void displayHand(Player player) {
        // Count each card type in hand
        java.util.Map<String, Integer> cardCounts = new java.util.LinkedHashMap<>();
        java.util.Map<String, Card> cardTypes = new java.util.LinkedHashMap<>();
        
        for (Card card : player.getHand()) {
            String key = card.getName();
            cardCounts.put(key, cardCounts.getOrDefault(key, 0) + 1);
            if (!cardTypes.containsKey(key)) {
                cardTypes.put(key, card);
            }
        }
        
        for (String cardName : cardCounts.keySet()) {
            Card card = cardTypes.get(cardName);
            int count = cardCounts.get(cardName);
            System.out.println("  " + card.getName() + " x" + count + 
                             " (" + card.getType() + ", Cost: " + card.getCost() + 
                             ", Value: " + card.getValue() + ")");
        }
    }

    private void buyPhase(Player player) {
        if (supply.isEmpty()) {
            System.out.println("\nSupply is empty. No cards to buy.");
            return;
        }
        
        System.out.println("\nAvailable cards to buy:");
        displaySupply();
        
        if (player.isHuman()) {
            // Human player - get input
            humanBuyPhase(player);
        } else {
            // Automated buying strategy: buy the most expensive card the player can afford
            automatedBuyPhase(player);
        }
    }
    
    private void displaySupply() {
        // Count each card type in supply
        java.util.Map<String, Integer> cardCounts = new java.util.LinkedHashMap<>();
        java.util.Map<String, Card> cardTypes = new java.util.LinkedHashMap<>();
        
        for (Card card : supply) {
            String key = card.getName();
            cardCounts.put(key, cardCounts.getOrDefault(key, 0) + 1);
            if (!cardTypes.containsKey(key)) {
                cardTypes.put(key, card);
            }
        }
        
        int index = 0;
        for (String cardName : cardCounts.keySet()) {
            Card card = cardTypes.get(cardName);
            int count = cardCounts.get(cardName);
            System.out.println("  " + index + ": " + card.getName() + " x" + count + 
                             " (" + card.getType() + ", Cost: " + card.getCost() + 
                             ", Value: " + card.getValue() + ")");
            index++;
        }
    }

    private void humanBuyPhase(Player player) {
        if (scanner == null) {
            System.out.println("Error: Scanner not available for human player. Skipping buy.");
            return;
        }

        System.out.print("Enter card number to buy (or -1 to skip): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice == -1) {
                System.out.println("Skipping buy.");
            } else {
                // Get unique card types
                java.util.List<String> cardNames = new java.util.ArrayList<>();
                for (Card card : supply) {
                    if (!cardNames.contains(card.getName())) {
                        cardNames.add(card.getName());
                    }
                }
                
                if (choice >= 0 && choice < cardNames.size()) {
                    String selectedCardName = cardNames.get(choice);
                    // Find the first card of this type in supply
                    Card cardToBuy = null;
                    for (Card card : supply) {
                        if (card.getName().equals(selectedCardName)) {
                            cardToBuy = card;
                            break;
                        }
                    }
                    
                    if (cardToBuy != null && player.getMoney() >= cardToBuy.getCost()) {
                        player.buyCard(cardToBuy);
                        supply.remove(cardToBuy);
                        System.out.println("Bought: " + cardToBuy.getName());
                    } else if (cardToBuy != null) {
                        System.out.println("Not enough cryptocoins!");
                    }
                } else {
                    System.out.println("Invalid choice. Skipping buy.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Skipping buy.");
        }
    }

    private void automatedBuyPhase(Player player) {
        Card cardToBuy = null;
        for (Card card : supply) {
            if (player.getMoney() >= card.getCost()) {
                if (cardToBuy == null || card.getCost() > cardToBuy.getCost()) {
                    cardToBuy = card;
                }
            }
        }
        
        if (cardToBuy != null) {
            player.buyCard(cardToBuy);
            supply.remove(cardToBuy);
            System.out.println("Bought: " + cardToBuy.getName());
        } else {
            System.out.println("Cannot afford any cards, skipping buy.");
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
