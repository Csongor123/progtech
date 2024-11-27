package hu.nye.PlayerTest;

import hu.nye.player.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tesztek a Player osztályhoz.
 */
class PlayerTest {

    @Test
    void testGetName() {
        // Arrange
        Player player = new Player("John", 'S');

        // Act
        String name = player.getName();

        // Assert
        assertEquals("John", name);
    }

    @Test
    void testGetDisc() {
        // Arrange
        Player player = new Player("Doe", 'P');

        // Act
        char disc = player.getDisc();

        // Assert
        assertEquals('P', disc);
    }

    @Test
    void testPlayerImmutable() {
        // Arrange
        Player player = new Player("Immutable", 'S');

        // Act
        String name = player.getName();
        char disc = player.getDisc();

        // Assert
        assertNotNull(name);
        assertNotNull(disc);
        assertEquals("Immutable", name);
        assertEquals('S', disc);
    }
}
