# ip1-jasonsaez

## Automation: The Game (ATG)

A deck-building card game prototype implemented in Java for educational purposes.

### Game Overview
Automation: The Game is a simplified deck-building card game where players compete to accumulate the most victory points. Players start with a basic deck and improve it by buying new cards each turn.

### How to Play
1. **Setup**: Each player starts with 7 Copper cards (money) and 3 Estate cards (victory points)
2. **Turn Structure**:
   - Draw 5 cards
   - Play all money cards to generate purchasing power
   - Buy one card from the supply
   - Discard hand and draw 5 new cards
3. **Winning**: After a set number of turns, the player with the most victory points wins

### Card Types
- **Money Cards**: Copper (1), Silver (2), Gold (3) - provide buying power
- **Victory Cards**: Estate (1VP), Duchy (3VP), Province (6VP) - score points
- **Action Cards**: Village, Market - provide special abilities (basic implementation)

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
- `CardType.java` - Enum for card types (MONEY, ACTION, VICTORY)
- `Deck.java` - Deck management (shuffle, draw, add)
- `Player.java` - Player state (hand, deck, discard, money)
- `Game.java` - Game flow and turn management
- `App.java` - Main application entry point
- `AppTest.java` - Comprehensive unit tests