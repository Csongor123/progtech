package FileHandlerTest;

import hu.nye.FileHandler.FileHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

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
    void testLoadBoardFromFile_NotEnoughRows() throws IOException {
        // Létrehozunk egy ideiglenes fájlt nem elegendő sorral
        Path tempFile = Files.createTempFile("notEnoughRows", ".txt");
        Files.writeString(tempFile, ".......\n.......");

        // Ellenőrizzük, hogy a kivétel dobódik-e
        Exception exception = assertThrows(IOException.class, () -> {
            fileHandler.loadBoardFromFile(tempFile.toString().replace(".txt", ""), rows, cols);
        });

        assertTrue(exception.getMessage().contains("Not enough rows in file"));

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

        // Kiírjuk az eredeti fájl tartalmát konzolra a hiba diagnosztizálásához
        System.out.println("Fájlból olvasott tartalom (nyers formátumban):");
        for (char c : fileContent.toCharArray()) {
            System.out.print((int)c + " ");
        }
        System.out.println("\n");

        // Itt az összes új sort \n-re cseréljük, hogy platformfüggetlen legyen
        fileContent = fileContent.replace(System.lineSeparator(), "\n");

        // Elvárt tartalom definiálása, egységesen \n sorvégződéssel
        String expectedContent = ".......\n.......\n.......\n.......\n.......\n.......\n";

        // Kiírjuk az elvárt tartalmat konzolra is
        System.out.println("Elvárt tartalom (nyers formátumban):");
        for (char c : expectedContent.toCharArray()) {
            System.out.print((int)c + " ");
        }
        System.out.println("\n");

        // Összehasonlítjuk az elvárt és a tényleges tartalmat
        assertEquals(expectedContent.trim(), fileContent.trim());

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
