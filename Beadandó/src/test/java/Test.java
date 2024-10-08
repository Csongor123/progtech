package hu.nye;

import hu.nye.board.Board;
import hu.nye.player.Player;
import hu.nye.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class Test {

    private Board board;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setup() {
        board = new Board(6, 7);
        player1 = new Player("Human", 'S');
        player2 = new Player("AI", 'P');
    }

    @Test
    public void testBoardInitialization() {
        char[][] expectedBoard = {
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'}
        };

        board.initializeBoard();
        assertArrayEquals(expectedBoard, board.getGrid());
    }

    @Test
    public void testDropDisc() {
        board.dropDisc(0, player1.getDisc());
        assertEquals('S', board.getGrid()[5][0]);  // A legalsó sor, első oszlopban a sárga korong legyen

        board.dropDisc(0, player2.getDisc());
        assertEquals('P', board.getGrid()[4][0]);  // A második legalsó sorban legyen a piros
    }

    @Test
    public void testInvalidMove() {
        boolean result = board.dropDisc(7, player1.getDisc());  // Érvénytelen oszlop
        assertFalse(result);

        result = board.dropDisc(-1, player1.getDisc());  // Érvénytelen oszlop
        assertFalse(result);
    }

    @Test
    public void testVerticalWinCondition() {
        // Dropping 4 discs in the same column for player1
        board.dropDisc(0, player1.getDisc());
        board.dropDisc(0, player1.getDisc());
        board.dropDisc(0, player1.getDisc());
        board.dropDisc(0, player1.getDisc());

        assertTrue(board.checkWin(player1.getDisc()));  // Ellenőrizzük a győzelmi feltételt
    }

    @Test
    public void testHorizontalWinCondition() {
        // Dropping discs in a row for player2
        board.dropDisc(0, player2.getDisc());
        board.dropDisc(1, player2.getDisc());
        board.dropDisc(2, player2.getDisc());
        board.dropDisc(3, player2.getDisc());

        assertTrue(board.checkWin(player2.getDisc()));  // Ellenőrizzük a vízszintes győzelmi feltételt
    }

    @Test
    public void testGamePlay() {
        Game game = new Game(board, player1, player2);
        Scanner scanner = new Scanner("0\n1\n2\n3\n");

        game.playGame(scanner);

        assertTrue(board.checkWin(player1.getDisc()));  // Az emberi játékosnak győznie kell
    }
}

