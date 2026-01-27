package edu.brandeis.cosi103a.ip2;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private Deck deck;
    private Deck discardPile;
    private List<Card> hand;
    private List<Card> playedCards;
    private int money;
    private int actions;
    private boolean isHuman;

    public Player(String name, boolean isHuman) {
        this.name = name;
        this.deck = new Deck();
        this.discardPile = new Deck();
        this.hand = new ArrayList<>();
        this.playedCards = new ArrayList<>();
        this.money = 0;
        this.actions = 0;
        this.isHuman = isHuman;
    }

    public Player(String name) {
        this(name, false); // Default to automated player for backwards compatibility
    }

    public void initializeDeck() {
        // Start with basic cards: 7 Bitcoin (1 cryptocoin) and 3 Method (1 automation point)
        for (int i = 0; i < 7; i++) {
            deck.addCard(new Card("Bitcoin", CardType.CRYPTOCURRENCY, 0, 1));
        }
        for (int i = 0; i < 3; i++) {
            deck.addCard(new Card("Method", CardType.AUTOMATION, 2, 1));
        }
        deck.shuffle();
    }

    public void drawCards(int numCards) {
        for (int i = 0; i < numCards; i++) {
            Card card = deck.drawCard();
            if (card == null) {
                // Reshuffle discard pile into deck if deck is empty
                reshuffleDiscardIntoDeck();
                card = deck.drawCard();
            }
            if (card != null) {
                hand.add(card);
            }
        }
    }

    private void reshuffleDiscardIntoDeck() {
        List<Card> discardedCards = new ArrayList<>();
        while (!discardPile.isEmpty()) {
            discardedCards.add(discardPile.drawCard());
        }
        deck.addAll(discardedCards);
        deck.shuffle();
    }

    public void discardHand() {
        // Discard all cards in hand
        for (Card card : hand) {
            discardPile.addCard(card);
        }
        hand.clear();
        
        // Discard all played cards
        for (Card card : playedCards) {
            discardPile.addCard(card);
        }
        playedCards.clear();
    }

    public void playMoneyCards() {
        money = 0;
        // Move cryptocurrency cards from hand to played area and count their value
        List<Card> cardsToPlay = new ArrayList<>();
        for (Card card : hand) {
            if (card.getType() == CardType.CRYPTOCURRENCY) {
                cardsToPlay.add(card);
                money += card.getValue();
            }
        }
        // Remove played cards from hand and add to played area
        for (Card card : cardsToPlay) {
            hand.remove(card);
            playedCards.add(card);
        }
    }

    public void buyCard(Card card) {
        if (money >= card.getCost()) {
            discardPile.addCard(card);
            money -= card.getCost();
        }
    }

    public int calculateScore() {
        int score = 0;
        
        // Count automation points from deck (without removing cards)
        List<Card> deckCards = new ArrayList<>();
        while (!deck.isEmpty()) {
            Card card = deck.drawCard();
            if (card.getType() == CardType.AUTOMATION) {
                score += card.getValue();
            }
            deckCards.add(card);
        }
        
        // Put deck cards back
        for (Card card : deckCards) {
            deck.addCard(card);
        }
        
        // Count automation points from discard pile (without removing cards)
        List<Card> discardCards = new ArrayList<>();
        while (!discardPile.isEmpty()) {
            Card card = discardPile.drawCard();
            if (card.getType() == CardType.AUTOMATION) {
                score += card.getValue();
            }
            discardCards.add(card);
        }
        
        // Put discard cards back
        for (Card card : discardCards) {
            discardPile.addCard(card);
        }
        
        // Count automation points from hand (without modifying it)
        for (Card card : hand) {
            if (card.getType() == CardType.AUTOMATION) {
                score += card.getValue();
            }
        }
        
        return score;
    }
    
    public List<Card> getAllCards() {
        List<Card> allCards = new ArrayList<>();
        
        // Get all cards from deck
        List<Card> deckCards = new ArrayList<>();
        while (!deck.isEmpty()) {
            Card card = deck.drawCard();
            deckCards.add(card);
            allCards.add(card);
        }
        // Put deck cards back
        for (Card card : deckCards) {
            deck.addCard(card);
        }
        
        // Get all cards from discard pile
        List<Card> discardCards = new ArrayList<>();
        while (!discardPile.isEmpty()) {
            Card card = discardPile.drawCard();
            discardCards.add(card);
            allCards.add(card);
        }
        // Put discard cards back
        for (Card card : discardCards) {
            discardPile.addCard(card);
        }
        
        // Add cards from hand
        allCards.addAll(hand);
        
        // Add played cards
        allCards.addAll(playedCards);
        
        return allCards;
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getMoney() {
        return money;
    }

    public int getActions() {
        return actions;
    }

    public void setActions(int actions) {
        this.actions = actions;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean isHuman() {
        return isHuman;
    }
}
