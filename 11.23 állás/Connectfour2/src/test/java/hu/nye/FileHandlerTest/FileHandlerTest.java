package hu.nye.FileHandlerTest;

import hu.nye.board.Board;
import hu.nye.filehandler.FileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    private FileHandler fileHandler;

    @BeforeEach
    void setUp() {
        fileHandler = new FileHandler();
    }

    @Test
    void testLoadBoardFromFile() throws IOException {
        // Ideiglenes fájl létrehozása és tartalom írása
        Path tempFile = Files.createTempFile("testBoard", ".txt");
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            writer.write("XOX\nOXO\nXOX");
        }

        // Board inicializálása
        Board board = new Board(3, 3);

        // Metódus tesztelése
        fileHandler.loadBoardFromFile(tempFile.toFile().getAbsolutePath().replace(".txt", ""), board);

        // Ellenőrzés
        assertEquals('X', board.getBoardGrid()[0][0]);
        assertEquals('O', board.getBoardGrid()[0][1]);
        assertEquals('X', board.getBoardGrid()[0][2]);
        assertEquals('O', board.getBoardGrid()[1][0]);
        assertEquals('X', board.getBoardGrid()[1][1]);
        assertEquals('O', board.getBoardGrid()[1][2]);

        // Ideiglenes fájl törlése
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testAppendBoardToLogFile_Success() throws IOException {
        // Ideiglenes fájl létrehozása
        Path tempFile = Files.createTempFile("gameLog", ".txt");

        // Példa tábla
        char[][] board = {
                {'X', 'O', 'X'},
                {'O', 'X', 'O'},
                {'X', 'X', 'O'}
        };

        // Metódus tesztelése
        fileHandler.appendBoardToLogFile(board, "Player X wins", tempFile.toFile().getAbsolutePath());

        // Ellenőrzés
        try (BufferedReader reader = Files.newBufferedReader(tempFile)) {
            String firstLine = reader.readLine();
            assertEquals("Game Result: Player X wins", firstLine);

            String secondLine = reader.readLine();
            assertEquals("XOX", secondLine);

            String thirdLine = reader.readLine();
            assertEquals("OXO", thirdLine);

            String fourthLine = reader.readLine();
            assertEquals("XXO", fourthLine);
        }

        // Ideiglenes fájl törlése
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testAppendBoardToLogFile_NullBoard() {
        // Null tábla tesztelése
        assertThrows(NullPointerException.class, () -> fileHandler.appendBoardToLogFile(null, "Player O wins"));
    }

    @Test
    void testAppendBoardToLogFile_IOException() {
        // Írásvédett fájl létrehozása
        File readOnlyFile = new File("readonly_log.txt");
        try {
            readOnlyFile.createNewFile();
            readOnlyFile.setWritable(false);

            char[][] board = {
                    {'X', 'O', 'X'},
                    {'O', 'X', 'O'},
                    {'X', 'X', 'O'}
            };

            // IOException ellenőrzése
            assertThrows(IOException.class, () -> fileHandler.appendBoardToLogFile(board, "Tie", readOnlyFile.getAbsolutePath()));

        } catch (IOException e) {
            fail("Teszt fájl előkészítése nem sikerült.");
        } finally {
            readOnlyFile.setWritable(true);
            readOnlyFile.delete();
        }
    }

    @Test
    void testAppendBoardToLogFile_NormalCase() throws IOException {
        // Ideiglenes fájl létrehozása
        Path tempLogFile = Files.createTempFile("game_log", ".txt");
        try {
            // A függvény tesztelése normál esetben
            char[][] board = {
                    {'X', 'O', 'X'},
                    {'O', 'X', 'O'},
                    {'X', 'O', 'X'}
            };
            fileHandler.appendBoardToLogFile(board, "Player1 wins", tempLogFile.toString());

            // Ellenőrzés, hogy a fájl tartalma megfelelő-e
            String fileContent = Files.readString(tempLogFile);
            assertTrue(fileContent.contains("Game Result: Player1 wins"));
            assertTrue(fileContent.contains("XOX"));
            assertTrue(fileContent.contains("OXO"));
            assertTrue(fileContent.contains("XOX"));
        } finally {
            Files.deleteIfExists(tempLogFile);
        }
    }

}
