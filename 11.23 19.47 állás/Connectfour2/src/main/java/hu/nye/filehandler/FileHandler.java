package hu.nye.filehandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import hu.nye.board.Board;

/**
 * A FileHandler osztály kezeli a játék
 * táblák fájlba való betöltését és fájlba való mentését.
 * Az osztály véglegesen lett tervezve, hogy megakadályozza a kiterjesztést.
 */
public class FileHandler {

    /**
     * Betölti a táblát a megadott fájlból és beállítja azt a Board objektumban.
     *
     * @param filename a fájl neve (anélkül, hogy megadnád a kiterjesztést)
     * @param board    a Board objektum, amelybe be kell tölteni a táblát
     * @throws IOException ha hiba lép fel a fájl beolvasásakor
     */
    public void loadBoardFromFile(final String filename, final Board board) throws
            IOException {
        try (BufferedReader reader = new BufferedReader(new
                FileReader(filename + ".txt"))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null && row < board.getRows()) {
                for (int col = 0; col < line.length() && col < board.getCols(); col++) {
                    board.getBoardGrid()[row][col] = line.charAt(col);
                }
                row++;
            }
        }
    }

    /**
     * Ment egy táblát egy megadott naplófájlba a játék eredményével együtt.
     *
     * @param board     a menteni kívánt játéktábla, amely egy kétdimenziós karaktertömb
     * @param result    a játék eredménye, például a győztes játékos neve vagy "tie"
     * @param logFileName a fájl neve, ahová a tábla mentésre kerül
     * @throws NullPointerException ha a tábla null
     * @throws IOException          ha hiba lép fel a fájl írásakor
     */
    public void appendBoardToLogFile(final char[][] board, final String result,
                                     final String logFileName)
            throws IOException {
        if (board == null) {
            throw new NullPointerException("Board cannot be null");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
            writer.write("Game Result: " + result);
            writer.newLine();
            for (char[] row : board) {
                writer.write(Arrays.toString(row).replace(", ", "").replace("[", "").replace("]", ""));
                writer.newLine();
            }
            writer.newLine(); // Üres sor a következő játék előtt
        } catch (IOException e) {
            throw new IOException("Error writing to log file: " + e.getMessage());
        }
    }

    /**
     * Ment egy táblát egy naplófájlba.
     *
     * @param board     a menteni kívánt tábla
     * @param result    a játék eredménye ("győztes neve" vagy "tie")
     * @throws NullPointerException ha a tábla null
     * @throws IOException ha hiba lép fel a fájl írásakor
     */
    public void appendBoardToLogFile(final char[][] board, final String result)
            throws IOException {
        if (board == null) {
            throw new NullPointerException("Board cannot be null");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("game_log.txt", true))) {
            writer.write("Game Result: " + result);
            writer.newLine();
            for (char[] row : board) {
                writer.write(Arrays.toString(row).replace(", ", "").replace("[", "").replace("]", ""));
                writer.newLine();
            }
            writer.newLine(); // Üres sor a következő játék előtt
        } catch (IOException e) {
            throw new IOException("Error writing to log file: " + e.getMessage());
        }
    }
}
