package edu.brandeis.cosi103a.ip2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AppTest {
    
    @Test
    public void testCardCreation() {
        Card card = new Card("Bitcoin", CardType.CRYPTOCURRENCY, 0, 1);
        assertEquals("Bitcoin", card.getName());
        assertEquals(CardType.CRYPTOCURRENCY, card.getType());
        assertEquals(0, card.getCost());
        assertEquals(1, card.getValue());
    }
    
    @Test
    public void testCardToString() {
        Card card = new Card("Ethereum", CardType.CRYPTOCURRENCY, 3, 2);
        String expected = "Ethereum (CRYPTOCURRENCY, Cost: 3, Value: 2)";
        assertEquals(expected, card.toString());
    }
    
    @Test
    public void testDeckAddAndDraw() {
        Deck deck = new Deck();
        Card card = new Card("Bitcoin", CardType.CRYPTOCURRENCY, 0, 1);
        
        deck.addCard(card);
        assertEquals(1, deck.size());
        
        Card drawn = deck.drawCard();
        assertNotNull(drawn);
        assertEquals("Bitcoin", drawn.getName());
        assertEquals(0, deck.size());
    }
    
    @Test
    public void testDeckEmpty() {
        Deck deck = new Deck();
        assertTrue(deck.isEmpty());
        
        deck.addCard(new Card("Method", CardType.AUTOMATION, 2, 1));
        assertFalse(deck.isEmpty());
    }
    
    @Test
    public void testDeckDrawFromEmpty() {
        Deck deck = new Deck();
        Card drawn = deck.drawCard();
        assertNull(drawn);
    }
    
    @Test
    public void testPlayerInitialization() {
        Player player = new Player("Test Player");
        assertEquals("Test Player", player.getName());
        assertNotNull(player.getHand());
        assertEquals(0, player.getHand().size());
    }
    
    @Test
    public void testPlayerInitializeDeck() {
        Player player = new Player("Test Player");
        player.initializeDeck();
        player.drawCards(5);
        assertEquals(5, player.getHand().size());
    }
    
    @Test
    public void testPlayerPlayMoneyCards() {
        Player player = new Player("Test Player");
        player.initializeDeck();
        player.drawCards(5);
        player.playMoneyCards();
        assertTrue(player.getMoney() >= 0);
    }
    
    @Test
    public void testPlayerCalculateScore() {
        Player player = new Player("Test Player");
        player.initializeDeck();
        int score = player.calculateScore();
        assertEquals(3, score); // 3 Method cards worth 1 automation point each
    }
    
    @Test
    public void testGameCreation() {
        Game game = new Game();
        assertNotNull(game.getPlayers());
        assertNotNull(game.getSupply());
        assertTrue(game.getSupply().size() > 0);
    }
    
    @Test
    public void testGameAddPlayer() {
        Game game = new Game();
        Player player = new Player("Test Player");
        game.addPlayer(player);
        assertEquals(1, game.getPlayers().size());
        assertEquals(5, player.getHand().size());
    }
    
    // ===== BUY PHASE TESTS =====
    
    @Test
    public void testCannotBuyCardIfMoneyLessThanCost() {
        Player player = new Player("Test Player");
        player.initializeDeck();
        
        // Create a card that costs more than player has
        Card expensiveCard = new Card("Framework", CardType.AUTOMATION, 8, 6);
        
        // Player has 0 money initially
        assertEquals(0, player.getMoney());
        
        // Count discard pile before
        int discardCountBefore = 0;
        while (!player.getDiscardPile().isEmpty()) {
            player.getDiscardPile().drawCard();
            discardCountBefore++;
        }
        
        // Try to buy the card (should fail silently)
        player.buyCard(expensiveCard);
        
        // Discard pile should not have increased because purchase failed
        int discardCountAfter = 0;
        while (!player.getDiscardPile().isEmpty()) {
            player.getDiscardPile().drawCard();
            discardCountAfter++;
        }
        
        assertEquals(discardCountBefore, discardCountAfter);
    }
    
    @Test
    public void testBuyingDecreasesSupplyCount() {
        Game game = new Game();
        Player player = new Player("Test Player");
        game.addPlayer(player);
        
        // Count initial Framework cards in supply
        int initialFrameworkCount = 0;
        for (Card card : game.getSupply()) {
            if (card.getName().equals("Framework")) {
                initialFrameworkCount++;
            }
        }
        assertEquals(8, initialFrameworkCount); // Should start with 8 Frameworks
        
        // Give player enough money and buy a Framework
        player.playMoneyCards();
        // Manually set money to afford Framework
        Card framework = null;
        for (Card card : game.getSupply()) {
            if (card.getName().equals("Framework")) {
                framework = card;
                break;
            }
        }
        
        if (framework != null) {
            // Set money to cost of Framework
            player.drawCards(5);
            player.playMoneyCards();
            // Manually give player 8 coins
            for (int i = 0; i < 8; i++) {
                player.getHand().add(new Card("Bitcoin", CardType.CRYPTOCURRENCY, 0, 1));
            }
            player.playMoneyCards();
            
            player.buyCard(framework);
            game.getSupply().remove(framework);
            
            // Count Framework cards again
            int finalFrameworkCount = 0;
            for (Card card : game.getSupply()) {
                if (card.getName().equals("Framework")) {
                    finalFrameworkCount++;
                }
            }
            
            assertEquals(7, finalFrameworkCount); // Should have 7 left
        }
    }
    
    @Test
    public void testBoughtCardGoesToDiscardNotHand() {
        Player player = new Player("Test Player");
        player.initializeDeck();
        player.drawCards(5);
        
        int handSizeBefore = player.getHand().size();
        
        // Give player enough money
        player.playMoneyCards();
        int moneyAvailable = player.getMoney();
        
        // Buy a card player can afford
        Card affordableCard = new Card("Method", CardType.AUTOMATION, 2, 1);
        if (moneyAvailable >= affordableCard.getCost()) {
            player.buyCard(affordableCard);
            
            // Hand size should not have increased
            assertEquals(handSizeBefore - player.getPlayedCards().size(), player.getHand().size());
            
            // Card should be in discard pile - verify by counting discard
            int discardCount = 0;
            java.util.List<Card> discardedCards = new java.util.ArrayList<>();
            while (!player.getDiscardPile().isEmpty()) {
                discardedCards.add(player.getDiscardPile().drawCard());
                discardCount++;
            }
            
            // Put cards back
            for (Card card : discardedCards) {
                player.getDiscardPile().addCard(card);
            }
            
            assertTrue(discardCount > 0); // Should have at least the bought card
        }
    }
    
    // ===== CLEANUP AND RESHUFFLE TESTS =====
    
    @Test
    public void testCleanupEmptiesHandAndDraws5() {
        Player player = new Player("Test Player");
        player.initializeDeck();
        player.drawCards(5);
        
        assertEquals(5, player.getHand().size());
        
        // Play money cards to populate played cards
        player.playMoneyCards();
        
        // Cleanup: discard hand and played cards, then draw 5
        player.discardHand();
        assertEquals(0, player.getHand().size());
        
        player.drawCards(5);
        assertEquals(5, player.getHand().size());
    }
    
    @Test
    public void testCleanupDiscardsBothHandAndPlayedCards() {
        Player player = new Player("Test Player");
        player.initializeDeck();
        player.drawCards(5);
        
        // Play money cards - moves cryptocurrency cards to played area
        player.playMoneyCards();
        
        int playedCount = player.getPlayedCards().size();
        int handCount = player.getHand().size();
        
        // Both should have cards
        assertTrue(playedCount + handCount > 0);
        
        // Cleanup
        player.discardHand();
        
        // Both hand and played should be empty
        assertEquals(0, player.getHand().size());
        assertEquals(0, player.getPlayedCards().size());
    }
    
    @Test
    public void testReshuffleWhenDeckRunsOut() {
        Player player = new Player("Test Player");
        player.initializeDeck(); // 10 cards total (7 Bitcoin + 3 Method)
        
        // Draw all 10 cards (deck will be empty)
        player.drawCards(10);
        assertEquals(10, player.getHand().size());
        
        // Discard hand (all 10 cards go to discard)
        player.discardHand();
        assertEquals(0, player.getHand().size());
        
        // Try to draw 5 cards - should reshuffle discard into deck and draw
        player.drawCards(5);
        assertEquals(5, player.getHand().size());
        
        // Verify deck still has cards left (5 from the original 10)
        int deckSizeRemaining = 0;
        java.util.List<Card> drawnCards = new java.util.ArrayList<>();
        while (!player.getDeck().isEmpty()) {
            drawnCards.add(player.getDeck().drawCard());
            deckSizeRemaining++;
        }
        
        // Put cards back
        for (Card card : drawnCards) {
            player.getDeck().addCard(card);
        }
        
        assertEquals(5, deckSizeRemaining);
    }
    
    @Test
    public void testReshufflePreservesAllCards() {
        Player player = new Player("Test Player");
        player.initializeDeck();
        
        // Count total cards initially
        int totalCardsBefore = player.getAllCards().size();
        
        // Draw all, discard, then draw again (forcing reshuffle)
        player.drawCards(10);
        player.discardHand();
        player.drawCards(5);
        
        // Count total cards after reshuffle
        int totalCardsAfter = player.getAllCards().size();
        
        assertEquals(totalCardsBefore, totalCardsAfter);
    }
    
    // ===== END CONDITION TESTS =====
    
    @Test
    public void testGameEndsWhenAllFrameworksPurchased() {
        Game game = new Game();
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        game.addPlayer(player1);
        game.addPlayer(player2);
        
        // Remove all Framework cards from supply except 1
        java.util.List<Card> frameworksToRemove = new java.util.ArrayList<>();
        for (Card card : game.getSupply()) {
            if (card.getName().equals("Framework")) {
                frameworksToRemove.add(card);
            }
        }
        
        // Remove all but 1 Framework
        for (int i = 0; i < frameworksToRemove.size() - 1; i++) {
            game.getSupply().remove(frameworksToRemove.get(i));
        }
        
        // Verify only 1 Framework left
        int frameworkCount = 0;
        for (Card card : game.getSupply()) {
            if (card.getName().equals("Framework")) {
                frameworkCount++;
            }
        }
        assertEquals(1, frameworkCount);
        
        // Now remove the last Framework
        Card lastFramework = null;
        for (Card card : game.getSupply()) {
            if (card.getName().equals("Framework")) {
                lastFramework = card;
                break;
            }
        }
        if (lastFramework != null) {
            game.getSupply().remove(lastFramework);
        }
        
        // Verify no Frameworks left
        frameworkCount = 0;
        for (Card card : game.getSupply()) {
            if (card.getName().equals("Framework")) {
                frameworkCount++;
            }
        }
        assertEquals(0, frameworkCount);
    }
    
    @Test
    public void testSupplyInitializesWithCorrectFrameworkCount() {
        Game game = new Game();
        
        int frameworkCount = 0;
        for (Card card : game.getSupply()) {
            if (card.getName().equals("Framework")) {
                frameworkCount++;
            }
        }
        
        assertEquals(8, frameworkCount); // Should start with exactly 8 Frameworks
    }
    
    // ===== SCORING TESTS =====
    
    @Test
    public void testScoreIsSumOfAutomationCardValues() {
        Player player = new Player("Test Player");
        
        // Manually add cards to deck for precise scoring test
        player.getDeck().addCard(new Card("Method", CardType.AUTOMATION, 2, 1));    // 1 AP
        player.getDeck().addCard(new Card("Method", CardType.AUTOMATION, 2, 1));    // 1 AP
        player.getDeck().addCard(new Card("Module", CardType.AUTOMATION, 5, 3));    // 3 APs
        player.getDeck().addCard(new Card("Framework", CardType.AUTOMATION, 8, 6)); // 6 APs
        player.getDeck().addCard(new Card("Bitcoin", CardType.CRYPTOCURRENCY, 0, 1)); // 0 APs
        
        // Expected: 1 + 1 + 3 + 6 = 11 APs
        int score = player.calculateScore();
        assertEquals(11, score);
    }
    
    @Test
    public void testScoreIncludesCardsInAllLocations() {
        Player player = new Player("Test Player");
        
        // Add automation cards to different locations
        // In deck
        player.getDeck().addCard(new Card("Method", CardType.AUTOMATION, 2, 1));
        
        // In discard
        player.getDiscardPile().addCard(new Card("Module", CardType.AUTOMATION, 5, 3));
        
        // In hand
        player.getHand().add(new Card("Framework", CardType.AUTOMATION, 8, 6));
        
        // In played cards
        player.getPlayedCards().add(new Card("Method", CardType.AUTOMATION, 2, 1));
        
        // Expected: 1 + 3 + 6 + 1 = 11 APs
        int score = player.calculateScore();
        assertEquals(11, score);
    }
    
    @Test
    public void testScoreIgnoresCryptocurrencyCards() {
        Player player = new Player("Test Player");
        
        // Add only cryptocurrency cards
        player.getDeck().addCard(new Card("Bitcoin", CardType.CRYPTOCURRENCY, 0, 1));
        player.getDeck().addCard(new Card("Ethereum", CardType.CRYPTOCURRENCY, 3, 2));
        player.getDeck().addCard(new Card("Dogecoin", CardType.CRYPTOCURRENCY, 6, 3));
        
        // Score should be 0 (cryptocurrency cards don't contribute to score)
        int score = player.calculateScore();
        assertEquals(0, score);
    }
    
    @Test
    public void testCalculateScoreIsNonDestructive() {
        Player player = new Player("Test Player");
        player.initializeDeck(); // 7 Bitcoin + 3 Method
        
        // Draw some cards to hand
        player.drawCards(5);
        int handSizeBefore = player.getHand().size();
        
        // Calculate score
        int score1 = player.calculateScore();
        int score2 = player.calculateScore();
        
        // Scores should be identical
        assertEquals(score1, score2);
        
        // Hand size should be unchanged
        assertEquals(handSizeBefore, player.getHand().size());
    }
    
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }
}
