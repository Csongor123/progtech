package hu.nye.game;

import hu.nye.board.Board;
import hu.nye.player.Player;
import hu.nye.FileHandler.FileHandler;

import java.util.Random;
import java.util.Scanner;

/**
 * A játékot vezérlő osztály, amely a játék logikáját tartalmazza.
 */
public final class Game {
    /**
     * A játék tábla.
     */
    private Board currentBoard;

    /**
     * Az első játékos.
     */
    private final Player playerOne;

    /**
     * A második játékos (AI).
     */
    private final Player playerTwo;

    /**
     * A fájlkezelő objektum a tábla mentésére és betöltésére.
     */
    private final FileHandler fileHandler = new FileHandler();

    /**
     * A Game konstruktor, amely inicializálja
     * a játék tábla objektumot és a játékosokat.
     *
     * @param board      a játék tábla
     * @param playerOne  az első játékos
     * @param playerTwo  a második játékos (AI)
     */
    public Game(final Board board, final Player
            playerOne, final Player playerTwo) {
        this.currentBoard = board;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    /**
     * A játék fő logikája, amely kezeli a játék köröket,
     * a felhasználói bemenetet és a győzelem ellenőrzését.
     *
     * @param scanner a bemeneti Scanner objektum
     */
    public void playGame(final Scanner scanner) {
        System.out.println("Do you want to load " +
                "a game from a file? (yes/no): ");
        String response = scanner.next();
        if (response.equalsIgnoreCase("yes")) {
            System.out.println("Enter the filename (without .txt): ");
            String filename = scanner.next();
            currentBoard.clearBoard();
            currentBoard = new Board(fileHandler.loadBoardFromFile(
                    filename, currentBoard.getRows(), currentBoard.getCols()));
        }

        Player currentPlayer = playerOne;

        while (true) {
            currentBoard.
                    printBoard();
            System.out.println(currentPlayer.getName() + "'s turn (" + currentPlayer.getDisc() + "):");

            int col;
            if (currentPlayer == playerTwo) {
                col = generateRandomMove();
                System.out.println("AI chose column: " + (char) ('a' + col));
            } else
            {
                System.out.println("Enter column (a-" +
                        (char) ('a' + (currentBoard.getCols() - 1)) + "): ");
                char colInput = scanner.next().charAt(0);
                col = colInput - 'a';
            }

            if (!currentBoard.dropDisc(col, currentPlayer.getDisc())) {
                System.out.println("Invalid move, try again.");
                continue;
            }

            if (currentBoard.checkWin(currentPlayer.getDisc())) {
                currentBoard.printBoard();
                System.out.println(currentPlayer.getName() + " wins!");
                saveGame(currentPlayer.getName());
                break;
            }

            if (currentBoard.isFull()) {
                currentBoard.printBoard();
                System.out.println("It's a tie!");
                saveGame("tie");
                break;
            }

            currentPlayer = (currentPlayer == playerOne)
                    ? playerTwo : playerOne;
        }
    }

    /**
     * Ment egy játékállást egy fájlba a játék végén.
     *
     * @param result a játék végeredménye (nyertes játékos neve vagy "tie")
     */
    private void saveGame(final String result) {
        System.out.println
                ("Saving game state...");
        String filename = "connectfour_" + System.currentTimeMillis()
                + "_" + result;
        fileHandler.saveBoardToFile(currentBoard.getBoardGrid(), filename);
        System.out.println("Game saved as " + filename + ".txt");
    }

    /**
     * Véletlenszerű oszlopot generál az AI játékos számára.
     *
     * @return a generált oszlop indexe
     */
    private int generateRandomMove() {
        Random rand = new Random();
        int col;
        do {
            col = rand.nextInt(currentBoard.getCols());
        } while (currentBoard.isColumnFull(col));
        return col;
    }
}
