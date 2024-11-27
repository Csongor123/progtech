package hu.nye;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import hu.nye.board.Board;
import hu.nye.databasehandler.DatabaseHandler;
import hu.nye.game.Game;
import hu.nye.player.Player;

/**
 * A Main osztály, amely a program belépési pontját definiálja,
 * és inicializálja a játékot.
 */
public final class Main {

    /**
     * A fő metódus, amely elindítja a játékot.
     *
     * @param args a parancssori argumentumok
     */
    public static void main(final String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Játékosok nevét kérjük be
        System.out.print("Enter name for Player 1 (Human): ");
        String player1Name = scanner.nextLine();

        // Játékosok létrehozása a megadott nevekkel
        Player player1 = new Player(player1Name, 'S');
        // Sárga - emberi játékos
        Player player2 = new Player("AI", 'P');         // Piros - gépi játékos

        // Adatbázis kapcsolat létrehozása
        DatabaseHandler databaseHandler = new DatabaseHandler();

        // Játékosok pontszámainak betöltése az adatbázisból
        DatabaseHandler.loadPlayerScores();

        // Játéktábla létrehozása fix mérettel (7 oszlop, 6 sor)
        final int rows = 6;
        final int cols = 7;
        Board board = new Board(rows, cols);

        // Játék elindítása
        Game game = new Game(board, player1, player2);
        game.playGame(scanner);

        // Highscore táblázat megjelenítése
        try {
            databaseHandler.displayHighScores();
        } catch (SQLException e) {
            System.out.println("Error displaying high scores: " + e.getMessage());
        }
    }

    /**
     * Privát konstruktor a Main osztály példányosításának megakadályozására.
     */
    private Main() {
        throw new UnsupportedOperationException();
    }
}
