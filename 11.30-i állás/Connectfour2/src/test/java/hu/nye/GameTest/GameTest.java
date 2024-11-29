package hu.nye.GameTest;
import hu.nye.board.Board;
import hu.nye.game.Game;
import hu.nye.player.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private Board board;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        board = new Board(6, 7); // 6 sor és 7 oszlop
        player1 = new Player("Player 1", 'X');
        player2 = new Player("Player 2", 'O');
        game = new Game(board, player1, player2);
    }



    @Test
    void testGenerateRandomMove() {
        // Teszteljük, hogy a `generateRandomMove` érvényes oszlopot ad vissza
        int col = game.generateRandomMove();
        assertTrue(col >= 0 && col < 7, "A generált oszlop index érvénytelen");
    }

    @Test
    void testSaveGame() throws IOException {
        // Teszteljük a játékállás mentését
        game.saveGame("Player 1 wins");

        // Mivel a tényleges fájlírást nem ellenőrizzük itt, csak azt biztosítjuk, hogy a metódus lefusson hiba nélkül
        assertDoesNotThrow(() -> game.saveGame("Player 1 wins"), "A mentés során hiba történt");
    }

    @Test
    void testUpdatePlayerScore() {
        // Teszteljük, hogy a játékos eredményének frissítése lefut hiba nélkül
        assertDoesNotThrow(() -> game.updatePlayerScore("Player 1"), "Az eredmények frissítése során hiba történt");
    }
}
