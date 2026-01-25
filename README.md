# ip1-jasonsaez

## Automation: The Game (ATG)

A deck-building card game prototype implemented in Java for educational purposes.

### Game Overview
Automation: The Game is a deck-building card game where players compete to accumulate the most Automation Points (APs). Players start with a basic deck and improve it by purchasing new cards each turn using cryptocoins.

### How to Play
1. **Setup**: Each player starts with 7 Bitcoin cards (cryptocoins) and 3 Method cards (automation points)
2. **Turn Structure**:
   - Draw 5 cards
   - Play all cryptocurrency cards to generate purchasing power
   - Buy one card from the supply
   - Discard hand and draw 5 new cards
3. **Winning**: After a set number of turns, the player with the most Automation Points wins

### Card Types

**Cryptocurrency Cards** (provide buying power):
- **Bitcoin** x60 (cost: 0, value: 1 cryptocoin)
- **Ethereum** x40 (cost: 3, value: 2 cryptocoins)
- **Dogecoin** x30 (cost: 6, value: 3 cryptocoins)

**Automation Cards** (provide Automation Points):
- **Method** x14 (cost: 2, value: 1 AP)
- **Module** x8 (cost: 5, value: 3 APs)
- **Framework** x8 (cost: 8, value: 6 APs)

### Building and Running
```bash
cd ip1
mvn clean package
java -cp target/ip1-1.0-SNAPSHOT.jar edu.brandeis.cosi103a.ip1.App
```

### Testing
```bash
cd ip1
mvn test
```

### Project Structure
- `Card.java` - Card representation with type, cost, and value
- `CardType.java` - Enum for card types (CRYPTOCURRENCY, AUTOMATION)
- `Deck.java` - Deck management (shuffle, draw, add)
- `Player.java` - Player state (hand, deck, discard, cryptocoins)
- `Game.java` - Game flow and turn management
- `App.java` - Main application entry point
- `AppTest.java` - Comprehensive unit tests