package hu.nye.FileHandler;

import java.io.*;

public class FileHandler {

    // A tábla betöltése fájlból
    public char[][] loadBoardFromFile(String filename, int rows, int cols) {
        char[][] board = new char[rows][cols];

        try (BufferedReader reader = new BufferedReader(new FileReader(filename + ".txt"))) {
            for (int i = 0; i < rows; i++) {
                String line = reader.readLine();
                if (line != null && line.length() == cols) { // Ellenőrizd, hogy a sorok megfelelő hosszúak-e
                    for (int j = 0; j < cols; j++) {
                        board[i][j] = line.charAt(j);
                    }
                } else {
                    throw new IOException("Invalid row length in file.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            // Alapértelmezett táblát tölts fel, ha a fájl nem olvasható
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    board[i][j] = '.'; // Üres helyek jelölése
                }
            }
        }
        return board;
    }

    // A tábla mentése fájlba
    public void saveBoardToFile(char[][] board, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename + ".txt"))) {
            for (char[] row : board) {
                writer.write(row);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}