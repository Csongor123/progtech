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
    private final FileHandler fileHandler = new FileHandler(); // Fájlból betöltéshez

    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void playGame(Scanner scanner) {
        // Kérdezzük meg, hogy akarják-e fájlból betölteni a játékot
        System.out.println("Do you want to load a game from a file? (yes/no): ");
        String response = scanner.next();
        if (response.equalsIgnoreCase("yes")) {
            System.out.println("Enter the filename: ");
            String filename = scanner.next();
            board = new Board(fileHandler.loadBoardFromFile(filename, board.getRows(), board.getCols()));
        }

        Player currentPlayer = player1;  // Sárga (emberi játékos) kezd

        while (true) {
            board.printBoard();
            System.out.println(currentPlayer.getName() + "'s turn (" + currentPlayer.getDisc() + "):");

            int col;
            if (currentPlayer == player2) {
                col = generateRandomMove();  // Gépi játékos véletlen lép
                System.out.println("AI chose column: " + col);
            } else {
                System.out.println("Enter column (0-" + (board.getCols() - 1) + "): ");
                col = scanner.nextInt();
            }

            // Lépés érvényességének ellenőrzése
            if (!board.dropDisc(col, currentPlayer.getDisc())) {
                System.out.println("Invalid move, try again.");
                continue;
            }

            // Nyertes vizsgálata
            if (board.checkWin(currentPlayer.getDisc())) {
                board.printBoard();
                System.out.println(currentPlayer.getName() + " wins!");
                break;
            }

            // Döntetlen vizsgálata
            if (board.isFull()) {
                board.printBoard();
                System.out.println("It's a tie!");
                break;
            }

            // Váltás a másik játékosra
            currentPlayer = (currentPlayer == player1) ? player2 : player1;

            // Lehetőség van a játék mentésére minden lépés után
            System.out.println("Do you want to save the game? (yes/no): ");
            response = scanner.next();
            if (response.equalsIgnoreCase("yes")) {
                System.out.println("Enter the filename: ");
                String filename = scanner.next();
                fileHandler.saveBoardToFile(board.getGrid(), filename);
            }
        }
    }

    private int generateRandomMove() {
        Random rand = new Random();
        int col;
        do {
            col = rand.nextInt(board.getCols());
        } while (board.isColumnFull(col));  // Ellenőrzi, hogy a generált oszlop nem tele van-e
        return col;
    }
}
