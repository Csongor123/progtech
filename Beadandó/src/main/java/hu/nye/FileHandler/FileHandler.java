package hu.nye.FileHandler;

import java.io.*;

public class FileHandler {

    public char[][] loadBoardFromFile(String filename, int rows, int cols) {
        char[][] board = new char[rows][cols];

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            for (int i = 0; i < rows; i++) {
                String line = reader.readLine();
                for (int j = 0; j < cols; j++) {
                    board[i][j] = line.charAt(j);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return board;
    }

    public void saveBoardToFile(char[][] board, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (char[] row : board) {
                writer.write(row);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
