package FileHandlerTest;

import hu.nye.FileHandler.FileHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A FileHandler osztály tesztelése.
 */
class FileHandlerTest {

    private final FileHandler fileHandler = new FileHandler();
    private final int rows = 6;
    private final int cols = 7;

    @Test
    void testLoadBoardFromFile_ValidFile() throws IOException {
        // Létrehozunk egy ideiglenes fájlt és beleírjuk a teszt táblát
        Path tempFile = Files.createTempFile("testBoard", ".txt");
        Files.writeString(tempFile, ".......\n.......\n.......\n.......\n.......\n.......");

        char[][] expectedBoard = {
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'}
        };

        // A fájl betöltése és ellenőrzés
        char[][] board = fileHandler.loadBoardFromFile(tempFile.toString().replace(".txt", ""), rows, cols);
        assertArrayEquals(expectedBoard, board);

        // Takarítsuk el az ideiglenes fájlt
        Files.delete(tempFile);
    }

    @Test
    void testLoadBoardFromFile_InvalidRowLength() throws IOException {
        // Létrehozunk egy ideiglenes fájlt hibás sorhosszal
        Path tempFile = Files.createTempFile("invalidBoard", ".txt");
        Files.writeString(tempFile, "......\n.......\n.......\n.......\n.......\n.......");

        // Ellenőrizzük, hogy a kivétel dobódik-e
        Exception exception = assertThrows(IOException.class, () -> {
            fileHandler.loadBoardFromFile(tempFile.toString().replace(".txt", ""), rows, cols);
        });

        assertTrue(exception.getMessage().contains("Invalid row length in file"));

        // Takarítsuk el az ideiglenes fájlt
        Files.delete(tempFile);
    }

    @Test
    void testSaveBoardToFile_ValidBoard() throws IOException {
        // Létrehozunk egy teszt táblát
        char[][] board = {
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'}
        };

        // Létrehozunk egy ideiglenes fájlt
        Path tempFile = Files.createTempFile("savedBoard", ".txt");

        // Tábla mentése a fájlba
        fileHandler.saveBoardToFile(board, tempFile.toString().replace(".txt", ""));

        // Ellenőrizzük, hogy a fájl tartalma megfelelő-e
        String fileContent = Files.readString(tempFile);
        String expectedContent = ".......\n.......\n.......\n.......\n.......\n.......";
        assertEquals(expectedContent, fileContent.trim());  // Az extra üres sor elkerülése

        // Takarítsuk el az ideiglenes fájlt
        Files.delete(tempFile);
    }

    @Test
    void testSaveBoardToFile_InvalidBoard() {
        // Próbálunk null táblát menteni, ellenőrizzük, hogy kivétel dobódik-e
        assertThrows(NullPointerException.class, () -> {
            fileHandler.saveBoardToFile(null, "invalidFile");
        });
    }
}
