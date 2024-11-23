//package hu.nye.PlayerTest;


import hu.nye.player.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tesztosztály a Player osztályhoz.
 */
class PlayerTest {

    @Test
    void testPlayerInitialization() {
        // Teszt adatok
        String playerName = "Alice";
        char playerDisc = 'X';

        // Példányosítás
        Player player = new Player(playerName, playerDisc);

        // Ellenőrzés
        assertEquals(playerName, player.getName(), "A játékos neve nem egyezik");
        assertEquals(playerDisc, player.getDisc(), "A játékos korongja nem egyezik");
    }

    @Test
    void testPlayerWithDifferentNameAndDisc() {
        // Másik teszt adat
        String playerName = "Bob";
        char playerDisc = 'O';

        // Példányosítás
        Player player = new Player(playerName, playerDisc);

        // Ellenőrzés
        assertEquals(playerName, player.getName(), "A játékos neve nem egyezik");
        assertEquals(playerDisc, player.getDisc(), "A játékos korongja nem egyezik");
    }

    @Test
    void testPlayerNameNotNull() {
        // Teszt adat
        String playerName = "Charlie";
        char playerDisc = 'X';

        // Példányosítás
        Player player = new Player(playerName, playerDisc);

        // Ellenőrzés
        assertNotNull(player.getName(), "A játékos neve null");
    }

    @Test
    void testPlayerDiscIsValidCharacter() {
        // Teszt adat
        String playerName = "Dana";
        char playerDisc = 'O';

        // Példányosítás
        Player player = new Player(playerName, playerDisc);

        // Ellenőrzés
        assertTrue(player.getDisc() == 'X' || player.getDisc() == 'O', "A korong karaktere nem 'X' vagy 'O'");
    }
//Nem biztos, hogy jó}

