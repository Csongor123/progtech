package BoardTest;

import hu.nye.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(6, 7); // 6 sor, 7 oszlop
        board.clearBoard(); // Tisztázza a táblát
    }

    @Test
    void testDropDisc() {
        assertTrue(board.dropDisc(0, 'X'));
        assertEquals('X', board.getBoardGrid()[5][0]); // A legalsó sorba kell kerülnie
    }

    @Test
    void testCheckWin_HorizontalWin() {
        board.dropDisc(0, 'X');
        board.dropDisc(1, 'X');
        board.dropDisc(2, 'X');
        board.dropDisc(3, 'X');
        assertTrue(board.checkWin('X')); // Vízszintes győzelem
    }

    @Test
    void testCheckWin_VerticalWin() {
        board.dropDisc(0, 'O');
        board.dropDisc(0, 'O');
        board.dropDisc(0, 'O');
        board.dropDisc(0, 'O');
        assertTrue(board.checkWin('O')); // Függőleges győzelem
    }

    @Test
    void testCheckWin_PositiveDiagonalWin() {
        board.dropDisc(0, 'X');
        board.dropDisc(1, 'O');
        board.dropDisc(1, 'X');
        board.dropDisc(2, 'O');
        board.dropDisc(2, 'O');
        board.dropDisc(2, 'X');
        board.dropDisc(3, 'O');
        board.dropDisc(3, 'O');
        board.dropDisc(3, 'O');
        board.dropDisc(3, 'X'); // Pozitív diagonális győzelem
        assertTrue(board.checkWin('X'));
    }

    @Test
    void testCheckWin_NegativeDiagonalWin() {
        board.dropDisc(3, 'X');
        board.dropDisc(4, 'O');
        board.dropDisc(4, 'X');
        board.dropDisc(5, 'O');
        board.dropDisc(5, 'O');
        board.dropDisc(5, 'X');
        board.dropDisc(6, 'O');
        board.dropDisc(6, 'O');
        board.dropDisc(6, 'O');
        board.dropDisc(6, 'X'); // Negatív diagonális győzelem
        assertTrue(board.checkWin('X'));
    }

    @Test
    void testIsFull() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                board.dropDisc(i, 'X'); // Töltsük fel az oszlopokat
            }
        }
        assertTrue(board.isFull()); // Az összes lemez elhelyezve
    }

    @Test
    void testIsColumnFull() {
        // Töltsd fel az első oszlopot
        for (int i = 0; i < 6; i++) {
            board.dropDisc(0, 'X'); // Lemezeket helyezünk el az első oszlopban
        }

        // Most az oszlopnak tele kell lennie
        assertTrue(board.isColumnFull(0)); // Ennek most igaznak kell lennie
    }
}
