package hu.nye.game;

import hu.nye.board.Board;
import hu.nye.player.Player;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private final Board board;
    private final Player player1;
    private final Player player2;

    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void playGame(Scanner scanner) {
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

            if (!board.dropDisc(col, currentPlayer.getDisc())) {
                System.out.println("Invalid move, try again.");
                continue;
            }


            if (board.checkWin(currentPlayer.getDisc())) {
                board.printBoard();
                System.out.println(currentPlayer.getName() + " wins!");
                break;
            }

            if (board.isFull()) {
                board.printBoard();
                System.out.println("It's a tie!");
                break;
            }

            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }
    }


    private int generateRandomMove() {
        Random rand = new Random();

        // Ellenőrizzük, hogy van-e legalább egy szabad oszlop
        int cols = board.getCols();
        if (cols <= 0) {
            throw new IllegalStateException("The board has no available columns.");
        }

        // Olyan oszlopot keresünk, ami nincs tele
        int col;
        do {
            col = rand.nextInt(cols);
        } while (board.isColumnFull(col));  // Feltételezzük, hogy van egy ilyen metódus

        return col;
    }

}