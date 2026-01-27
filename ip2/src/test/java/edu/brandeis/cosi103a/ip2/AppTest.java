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
    
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }
}
