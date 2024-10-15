package hu.nye.game;

import hu.nye.board.Board;
import hu.nye.player.Player;
import hu.nye.FileHandler.FileHandler;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private Board board;
    private final Player player1;
    private final Player player2;
    private final FileHandler fileHandler = new FileHandler();

    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void playGame(Scanner scanner) {
        System.out.println("Do you want to load a game from a file? (yes/no): ");
        String response = scanner.next();
        if (response.equalsIgnoreCase("yes")) {
            System.out.println("Enter the filename (without .txt): ");
            String filename = scanner.next();
            board.clearBoard();
            board = new Board(fileHandler.loadBoardFromFile(filename, board.getRows(), board.getCols()));
        }

        Player currentPlayer = player1;

        while (true) {
            board.printBoard();
            System.out.println(currentPlayer.getName() + "'s turn (" + currentPlayer.getDisc() + "):");

            int col;
            if (currentPlayer == player2) {
                col = generateRandomMove();
                System.out.println("AI chose column: " + (char)('a' + col));
            } else {
                System.out.println("Enter column (a-" + (char) ('a' + (board.getCols() - 1)) + "): ");
                char colInput = scanner.next().charAt(0);
                col = colInput - 'a';
            }

            if (!board.dropDisc(col, currentPlayer.getDisc())) {
                System.out.println("Invalid move, try again.");
                continue;
            }

            if (board.checkWin(currentPlayer.getDisc())) {
                board.printBoard();
                System.out.println(currentPlayer.getName() + " wins!");
                // Automatikus mentés a játék végén
                saveGame(currentPlayer.getName());
                break;
            }

            if (board.isFull()) {
                board.printBoard();
                System.out.println("It's a tie!");
                // Automatikus mentés a játék végén
                saveGame("tie");
                break;
            }

            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }
    }

    private void saveGame(String result) {
        System.out.println("Saving game state...");
        String filename = "connectfour_" + System.currentTimeMillis() + "_" + result; // Például: connectfour_1699999999999_win.txt
        fileHandler.saveBoardToFile(board.getBoardGrid(), filename);
        System.out.println("Game saved as " + filename + ".txt");
    }

    private int generateRandomMove() {
        Random rand = new Random();
        int col;
        do {
            col = rand.nextInt(board.getCols());
        } while (board.isColumnFull(col));
        return col;
    }
}