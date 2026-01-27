package edu.brandeis.cosi103a.ip2;

public class Card {
    private final String name;
    private final CardType type;
    private final int cost;
    private final int value;

    public Card(String name, CardType type, int cost, int value) {
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public CardType getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + " (" + type + ", Cost: " + cost + ", Value: " + value + ")";
    }
}
