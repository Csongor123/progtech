package hu.nye.game;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

import hu.nye.board.Board;
import hu.nye.databasehandler.DatabaseHandler;
import hu.nye.filehandler.FileHandler;
import hu.nye.player.Player;

/**
 * A játékot vezérlő osztály, amely a játék logikáját tartalmazza.
 */
public final class Game {
    private final Board currentBoard;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final FileHandler fileHandler = new FileHandler();
    private final DatabaseHandler databaseHandler = new DatabaseHandler();  // Hozzáadva az adatbázis kezelés

    public Game(final Board board,
                final Player playerOne, final Player playerTwo) {
        this.currentBoard = board;
        this.firstPlayer = playerOne;
        this.secondPlayer = playerTwo;
    }

    /**
     * Elindítja a játékot, és kezeli a fő játékmenetet.
     *
     * @param scanner a felhasználói bemenet olvasására szolgáló Scanner
     * @throws IOException ha fájlműveleti hiba lép fel
     */
    public void playGame(final Scanner scanner) throws IOException {
        System.out.println("Do you want to load a game from a file? (yes/no): ");
        String response = scanner.next();
        if (response.equalsIgnoreCase("yes")) {
            // Itt már nem kérjük be a fájl nevét, hanem alapértelmezetten a "board input.txt" fájlt olvassuk be
            String filename = "board input"; // Fájl név módosítása
            fileHandler.loadBoardFromFile(filename, currentBoard); // Betöltjük a board input.txt-t
        } else {
            currentBoard.clearBoard();
        }

        Player currentPlayer = this.firstPlayer;

        while (true) {
            currentBoard.printBoard();
            System.out.println(currentPlayer.getName()
                    + "'s turn (" + currentPlayer.getDisc() + "):");

            int col;
            if (currentPlayer == this.secondPlayer) {
                col = generateRandomMove();
                System.out.println("AI chose column: " + (char) ('a' + col));
            } else {
                System.out.println("Enter column (a-" + (char)
                        ('a' + (currentBoard.getCols() - 1)) + "): ");
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
                updatePlayerScore(currentPlayer.getName());
                break;
            }

            if (currentBoard.isFull()) {
                currentBoard.printBoard();
                System.out.println("It's a tie!");
                saveGame("tie");
                break;
            }

            currentPlayer = (currentPlayer == this.firstPlayer)
                    ? this.secondPlayer : this.firstPlayer;
        }
    }

    /**
     * Ment egy játékállást egy naplófájlba a játék végén.
     *
     * @param result a játék végeredménye (nyertes játékos neve vagy "tie")
     * @throws IOException ha hiba lép fel a fájl írásakor
     */
    public void saveGame(final String result) throws IOException {
        System.out.println("Saving game state...");
        try {
            fileHandler.appendBoardToLogFile(currentBoard.getBoardGrid(), result);
        } catch (IOException e) {
            throw new RuntimeException("Error saving game state: " + e.getMessage());
        }
        System.out.println("Game state appended to game_log.txt");
    }

    /**
     * Frissíti a játékos eredményeit az adatbázisban.
     *
     * @param playerName a játékos neve
     */
    public void updatePlayerScore(final String playerName) {
        try {
            databaseHandler.updatePlayerScore(playerName); // Az adatbázisban a játékos győzelmeinek frissítése
        } catch (SQLException e) {
            System.out.println("Error updating player score: " + e.getMessage());
        }
    }

    /**
     * Véletlenszerű oszlop kiválasztása az AI számára.
     *
     * @return egy oszlop indexe, amely nincs tele
     */
    public int generateRandomMove() {
        Random rand = new Random();
        int col;
        do {
            col = rand.nextInt(currentBoard.getCols());
        } while (currentBoard.isColumnFull(col));
        return col;
    }
}
