package hu.nye;

import hu.nye.board.Board;
import hu.nye.game.Game;
import hu.nye.player.Player;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Bekérjük a játékosok neveit
        System.out.print("Enter name for Player 1 (Human): ");
        String player1Name = scanner.nextLine();

        // Játékosok létrehozása a bekért nevekkel
        Player player1 = new Player(player1Name, 'S');  // Sárga - emberi játékos
        Player player2 = new Player("AI", 'P');         // Piros - gépi játékos

        // Játéktábla létrehozása fix 7x6 mérettel
        Board board = new Board(6, 7);

        // Játék elindítása
        Game game = new Game(board, player1, player2);
        game.playGame(scanner);
    }
}
