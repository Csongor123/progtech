package hu.nye.BoardTest;

import hu.nye.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(6, 7); // Inicializáljuk a 6x7-es táblát
        board.clearBoard(); // Töröljük a tábla állapotát
    }

    @Test
    void testDropDisc_ValidMove() {
        assertTrue(board.dropDisc(0, 'S'));
        assertEquals('S', board.getBoardGrid()[5][0]);
    }

    @Test
    void testDropDisc_OutOfBounds() {
        assertFalse(board.dropDisc(-1, 'S'));
        assertFalse(board.dropDisc(7, 'S'));
    }

    @Test
    void testDropDisc_ColumnFull() {
        for (int i = 0; i < 6; i++) {
            board.dropDisc(0, 'S');
        }
        assertFalse(board.dropDisc(0, 'S')); // Ellenőrizzük, ha tele van az oszlop
    }

    @Test
    void testCheckWin_HorizontalWin() {
        board.dropDisc(0, 'S');
        board.dropDisc(1, 'S');
        board.dropDisc(2, 'S');
        board.dropDisc(3, 'S');
        assertTrue(board.checkWin('S'));
    }

    @Test
    void testCheckWin_VerticalWin() {
        board.dropDisc(0, 'P');
        board.dropDisc(0, 'P');
        board.dropDisc(0, 'P');
        board.dropDisc(0, 'P');
        assertTrue(board.checkWin('P'));
    }

    @Test
    void testCheckWin_PositiveDiagonalWin() {
        board.dropDisc(0, 'S');
        board.dropDisc(1, 'P');
        board.dropDisc(1, 'S');
        board.dropDisc(2, 'P');
        board.dropDisc(2, 'P');
        board.dropDisc(2, 'S');
        board.dropDisc(3, 'P');
        board.dropDisc(3, 'P');
        board.dropDisc(3, 'P');
        board.dropDisc(3, 'S');
        assertTrue(board.checkWin('S'));
    }

    @Test
    void testCheckWin_NegativeDiagonalWin() {
        board.dropDisc(3, 'S');
        board.dropDisc(4, 'P');
        board.dropDisc(4, 'S');
        board.dropDisc(5, 'P');
        board.dropDisc(5, 'P');
        board.dropDisc(5, 'S');
        board.dropDisc(6, 'P');
        board.dropDisc(6, 'P');
        board.dropDisc(6, 'P');
        board.dropDisc(6, 'S');
        assertTrue(board.checkWin('S'));
    }

    @Test
    void testCheckWin_NoWin() {
        board.dropDisc(0, 'S');
        board.dropDisc(1, 'P');
        board.dropDisc(2, 'S');
        board.dropDisc(3, 'P');
        board.dropDisc(4, 'S');
        assertFalse(board.checkWin('S'));
    }

    @Test
    void testIsFull() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                board.dropDisc(i, 'S');
            }
        }
        assertTrue(board.isFull());
    }

    @Test
    void testIsFull_NotFull() {
        board.dropDisc(0, 'S');
        assertFalse(board.isFull());
    }

    @Test
    void testIsColumnFull() {
        for (int i = 0; i < 6; i++) {
            board.dropDisc(0, 'S');
        }
        assertTrue(board.isColumnFull(0));
    }

    @Test
    void testClearBoard() {
        board.dropDisc(0, 'S');
        board.dropDisc(1, 'P');
        board.clearBoard();
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                assertEquals('.', board.getBoardGrid()[row][col]);
            }
        }
    }

    @Test
    void testSaveAndLoadBoard() {
        board.dropDisc(0, 'S');
        board.saveBoardToFile("test_save.txt");

        Board newBoard = new Board(6, 7);
        newBoard.loadBoardFromFile("test_save.txt");

        assertEquals(board.getBoardGrid()[5][0], newBoard.getBoardGrid()[5][0]);
    }
}
