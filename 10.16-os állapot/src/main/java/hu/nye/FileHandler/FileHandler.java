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
     */
    public char[][] loadBoardFromFile(final String filename,
                                      final int rows, final int cols) {
        char[][] board = new char[rows][cols];

        try (BufferedReader reader = new BufferedReader(
                new FileReader(filename + ".txt"))) {
            for (int i = 0; i < rows; i++) {
                String line = reader.readLine();
                if (line != null && line.length() == cols) {
                    for (int j = 0; j < cols; j++) {
                        board[i][j] = line.charAt(j);
                    }
                } else {
                    throw new IOException("Invalid row length in file.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    board[i][j] = '.'; // Üres helyek jelölése
                }
            }
        }
        return board;
    }

    /**
     * Ment egy táblát a megadott fájlba.
     *
     * @param board     a menteni kívánt tábla
     * @param filename  a fájl neve (anélkül, hogy megadnád a kiterjesztést)
     */
    public void saveBoardToFile(final char[][] board, final String filename) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(filename + ".txt"))) {
            for (char[] row : board) {
                writer.write(row);
                writer.newLine();
            }
            writer.write(""); // Új sor hozzáadása a fájl végén
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
