package hu.nye;

import hu.nye.board.Board;
import hu.nye.game.Game;
import hu.nye.player.Player;

import java.util.Scanner;

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
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Bekérjük a játékos nevét
        System.out.print("Enter name for Player 1 (Human): ");
        String player1Name = scanner.nextLine();

        // Játékosok létrehozása a bekért nevekkel
        Player player1 = new Player(player1Name, 'S');
        // Sárga - emberi játékos
        Player player2 = new Player("AI", 'P');         // Piros - gépi játékos

        // Játéktábla létrehozása fix mérettel (7 oszlop, 6 sor)
        final int rows = 6;
        final int cols = 7;
        Board board = new Board(rows, cols);

        // Játék elindítása
        Game game = new Game(board, player1, player2);
        game.playGame(scanner);
    }

    /**
     * Privát konstruktor a Main osztály példányosításának megakadályozására.
     */
    private Main() {
        throw new UnsupportedOperationException();
    }
}
