# ip1-jasonsaez

## Automation: The Game (ATG)

A deck-building card game prototype implemented in Java for educational purposes.

### Game Overview
Automation: The Game is a deck-building card game where players compete to accumulate the most Automation Points (APs). Players start with a basic deck and improve it by purchasing new cards each turn using cryptocoins.

**The game supports multiple play modes:**
- **Human vs Human** - Two human players compete
- **Human vs Computer** - Human player competes against an automated AI
- **Computer vs Computer** - Watch two automated AI players compete

### How to Play
1. **Setup**: 
   - Each player starts with 7 Bitcoin cards (cryptocoins) and 3 Method cards (automation points)
   - Players shuffle their starter decks and deal 5 cards as their initial hand
   - Starting player is chosen randomly
2. **Turn Structure**:
   - Draw 5 cards
   - Play all cryptocurrency cards to generate purchasing power
   - Select a card to buy from the supply (human chooses, computer buys most expensive affordable)
   - Cards are removed from the supply when purchased (limited quantities)
   - Discard hand and draw 5 new cards
3. **Winning**: The game ends when all Framework cards have been purchased. The player with the most Automation Points in their deck wins.

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

### Building and Running
```bash
cd ip1
mvn clean package
java -cp target/ip1-1.0-SNAPSHOT.jar edu.brandeis.cosi103a.ip1.App
```

When you run the game, you'll be prompted to select a game mode:
1. Human vs Human - Both players make their own card choices
2. Human vs Computer - You play against an automated opponent
3. Computer vs Computer - Watch two AI players compete automatically

### Testing
```bash
cd ip1
mvn test
```

### Project Structure
- `Card.java` - Card representation with type, cost, and value
- `CardType.java` - Enum for card types (CRYPTOCURRENCY, AUTOMATION)
- `Deck.java` - Deck management (shuffle, draw, add)
- `Player.java` - Player state (hand, deck, discard, cryptocoins, human/AI flag)
- `Game.java` - Game flow, turn management, and both human and automated player logic
- `App.java` - Main application entry point with game mode selection
- `AppTest.java` - Comprehensive unit tests