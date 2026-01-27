# ip1-jasonsaez

This repository contains two Java projects:

## ip1 - Dice Game

A simple dice game where two players take turns rolling a die. Each player can choose to re-roll once per turn.

### Building and Running ip1
```bash
cd ip1
mvn clean package
java -cp target/ip1-1.0-SNAPSHOT.jar edu.brandeis.cosi103a.ip1.App
```

### Testing ip1
```bash
cd ip1
mvn test
```

---

## ip2 - Automation: The Game (ATG)

A deck-building card game prototype implemented in Java for educational purposes.

### Game Overview
Automation: The Game is a deck-building card game where two computer players compete to accumulate the most Automation Points (APs). Players start with a basic deck and improve it by purchasing new cards each turn using cryptocoins.

**This is an automated game** - 2 computer players compete against each other with no human input required.

### How to Play
1. **Setup**: 
   - Each player starts with 7 Bitcoin cards (cryptocoins) and 3 Method cards (automation points)
   - Players shuffle their starter decks and deal 5 cards as their initial hand
   - Starting player is chosen randomly
2. **Turn Structure**:
   - Draw 5 cards
   - Play all cryptocurrency cards to generate purchasing power
   - Computer automatically buys the most expensive affordable card
   - Cards are removed from the supply when purchased (limited quantities)
   - Discard hand and draw 5 new cards
3. **Winning**: The game ends when all Framework cards have been purchased. The player with the most Automation Points in their deck wins.
4. **End Game**: Final scores are tallied with complete deck listings showing all cards each player acquired.

### Card Types

The supply contains a limited quantity of each card type:

**Cryptocurrency Cards** (provide buying power):
- **Bitcoin** x60 (cost: 0, value: 1 cryptocoin)
- **Ethereum** x40 (cost: 3, value: 2 cryptocoins)
- **Dogecoin** x30 (cost: 6, value: 3 cryptocoins)

**Automation Cards** (provide Automation Points):
- **Method** x14 (cost: 2, value: 1 AP)
- **Module** x8 (cost: 5, value: 3 APs)
- **Framework** x8 (cost: 8, value: 6 APs)

### Building and Running ip2
```bash
cd ip2
mvn clean package
java -cp target/ip2-1.0-SNAPSHOT.jar edu.brandeis.cosi103a.ip2.App
```

The game will run automatically with 2 computer players competing against each other.

### Testing ip2
```bash
cd ip2
mvn test
```

### Project Structure
- `Card.java` - Card representation with type, cost, and value
- `CardType.java` - Enum for card types (CRYPTOCURRENCY, AUTOMATION)
- `Deck.java` - Deck management (shuffle, draw, add)
- `Player.java` - Player state (hand, deck, discard, played cards, cryptocoins)
- `Game.java` - Game flow, turn management, and automated player logic
- `App.java` - Main application entry point
- `AppTest.java` - Comprehensive unit tests