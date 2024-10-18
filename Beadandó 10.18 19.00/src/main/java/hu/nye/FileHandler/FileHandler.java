package hu.nye.FileHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A FileHandler osztály kezeli a játék
 * táblák fájlba való betöltését és fájlba való mentését.
 * Az osztály véglegesen lett tervezve, hogy megakadályozza a kiterjesztést.
 */
public class FileHandler {

    /**
     * Betölti a táblát a megadott fájlból.
     *
     * @param filename a fájl neve (anélkül, hogy megadnád a kiterjesztést)
     * @param rows     a táblázat sorainak száma
     * @param cols     a táblázat oszlopainak száma
     * @return a betöltött tábla
     * @throws IOException ha hiba lép fel a fájl beolvasásakor
     */
    public char[][] loadBoardFromFile(final String filename, final int rows,
                                      final int cols) throws IOException {
        char[][] board = new char[rows][cols];

        try (BufferedReader reader = new BufferedReader(
                new FileReader(filename + ".txt"))) {
            for (int i = 0; i < rows; i++) {
                String line = reader.readLine();
                if (line == null) {
                    throw new IOException("Not enough rows in file.");
                }
                if (line.length() != cols) {
                    throw new IOException("Invalid row length in file.");
                }
                for (int j = 0; j < cols; j++) {
                    board[i][j] = line.charAt(j);
                }
            }
        } catch (IOException e) {
            throw new IOException("Error reading file: " + e.getMessage());
        }
        return board;
    }

    /**
     * Ment egy táblát a megadott fájlba.
     *
     * @param board     a menteni kívánt tábla
     * @param filename  a fájl neve (anélkül, hogy megadnád a kiterjesztést)
     * @throws NullPointerException ha a tábla null
     * @throws IOException ha hiba lép fel a fájl írásakor
     */
    public void saveBoardToFile(final char[][] board,
                                final String filename) throws IOException {
        if (board == null) {
            throw new NullPointerException("Board cannot be null");
        }

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(filename + ".txt"))) {
            for (char[] row : board) {
                // Konvertáljuk a char[]-t String-gé
                writer.write(new String(row));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Error writing file: " + e.getMessage());
        }
    }
}
