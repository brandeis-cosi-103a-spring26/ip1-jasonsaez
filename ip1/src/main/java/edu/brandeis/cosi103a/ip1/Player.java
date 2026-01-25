package edu.brandeis.cosi103a.ip1;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private Deck deck;
    private Deck discardPile;
    private List<Card> hand;
    private int money;
    private int actions;

    public Player(String name) {
        this.name = name;
        this.deck = new Deck();
        this.discardPile = new Deck();
        this.hand = new ArrayList<>();
        this.money = 0;
        this.actions = 0;
    }

    public void initializeDeck() {
        // Start with basic cards: 7 copper (1 money) and 3 estates (1 victory point)
        for (int i = 0; i < 7; i++) {
            deck.addCard(new Card("Copper", CardType.MONEY, 0, 1));
        }
        for (int i = 0; i < 3; i++) {
            deck.addCard(new Card("Estate", CardType.VICTORY, 2, 1));
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
        for (Card card : hand) {
            discardPile.addCard(card);
        }
        hand.clear();
    }

    public void playMoneyCards() {
        money = 0;
        for (Card card : hand) {
            if (card.getType() == CardType.MONEY) {
                money += card.getValue();
            }
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
        
        // Count victory points from deck (without removing cards)
        List<Card> deckCards = new ArrayList<>();
        while (!deck.isEmpty()) {
            Card card = deck.drawCard();
            if (card.getType() == CardType.VICTORY) {
                score += card.getValue();
            }
            deckCards.add(card);
        }
        
        // Put deck cards back
        for (Card card : deckCards) {
            deck.addCard(card);
        }
        
        // Count victory points from discard pile (without removing cards)
        List<Card> discardCards = new ArrayList<>();
        while (!discardPile.isEmpty()) {
            Card card = discardPile.drawCard();
            if (card.getType() == CardType.VICTORY) {
                score += card.getValue();
            }
            discardCards.add(card);
        }
        
        // Put discard cards back
        for (Card card : discardCards) {
            discardPile.addCard(card);
        }
        
        // Count victory points from hand (without modifying it)
        for (Card card : hand) {
            if (card.getType() == CardType.VICTORY) {
                score += card.getValue();
            }
        }
        
        return score;
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
}
