# Memory Game

Java/Swing desktop memory game with Number Memory and Sequence Memory modes. High scores are persisted to SQLite.

## Tech Stack

- **Java 17**
- **Swing** 
- **SQLite** 
- **Gradle 9.1** 

## Build & Run

**Requirements:** Java 17 or later

```bash
# Build
./gradlew build
# Run
./gradlew run
```

On Windows:
```cmd
gradlew.bat build
gradlew.bat run
```

## Game Modes

- **Number Memory** - Memorize numbers displayed on screen; recall them in the text box. Difficulty increases with level (one more digit per level).
- **Sequence Memory** - memorize and recreate the pattern shown on a 3x3 grid.
