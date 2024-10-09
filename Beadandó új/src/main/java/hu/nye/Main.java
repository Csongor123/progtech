package hu.nye;

import hu.nye.board.Board;
import hu.nye.game.Game;
import hu.nye.player.Player;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Játékosok létrehozása
        Player player1 = new Player("Human", 'S');  // Sárga - emberi játékos
        Player player2 = new Player("AI", 'P');     // Piros - gépi játékos

        // Játéktábla létrehozása
        Board board = new Board(6, 7);  // Például 6 sor, 7 oszlop

        // Játék elindítása
        Game game = new Game(board, player1, player2);
        game.playGame(scanner);
    }
}