package BoardTest;

import hu.nye.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        // 6 sor és 7 oszlopos tábla létrehozása
        board = new Board(6, 7);
    }

    @Test
    public void testInitializeBoard() {
        board.initializeBoard();
        char[][] grid = board.getGrid();
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                assertEquals('.', grid[i][j], "Board should be initialized with empty cells.");
            }
        }
    }

    @Test
    public void testClearBoard() {
        board.dropDisc(0, 'S');
        board.clearBoard();
        char[][] grid = board.getGrid();
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                assertEquals('.', grid[i][j], "Board should be cleared with empty cells.");
            }
        }
    }

    @Test
    public void testDropDisc_ValidMove() {
        boolean result = board.dropDisc(0, 'S');
        assertTrue(result, "The disc should be dropped successfully.");
        assertEquals('S', board.getGrid()[5][0], "The disc should be at the bottom of the first column.");
    }

    @Test
    public void testDropDisc_InvalidMove_ColumnFull() {
        for (int i = 0; i < 6; i++) {
            board.dropDisc(0, 'S');
        }
        boolean result = board.dropDisc(0, 'S');
        assertFalse(result, "The disc should not be dropped into a full column.");
    }

    @Test
    public void testDropDisc_InvalidMove_OutOfBounds() {
        boolean result = board.dropDisc(7, 'S'); // 7. oszlop nem létezik
        assertFalse(result, "The disc should not be dropped into an invalid column.");
    }

    @Test
    public void testCheckWin_HorizontalWin() {
        board.dropDisc(0, 'S');
        board.dropDisc(1, 'S');
        board.dropDisc(2, 'S');
        board.dropDisc(3, 'S');
        assertTrue(board.checkWin('S'), "Horizontal win should be detected.");
    }

    @Test
    public void testCheckWin_VerticalWin() {
        board.dropDisc(0, 'S');
        board.dropDisc(0, 'S');
        board.dropDisc(0, 'S');
        board.dropDisc(0, 'S');
        assertTrue(board.checkWin('S'), "Vertical win should be detected.");
    }

    @Test
    public void testCheckWin_DiagonalWin_PositiveSlope() {
        // Hozz létre egy diagonális győzelmet a pozitív lejtésnél
        board.dropDisc(0, 'S'); // 5, 0
        board.dropDisc(1, 'P'); // 5, 1
        board.dropDisc(1, 'S'); // 4, 1
        board.dropDisc(2, 'P'); // 5, 2
        board.dropDisc(2, 'S'); // 4, 2
        board.dropDisc(3, 'P'); // 5, 3
        board.dropDisc(3, 'S'); // 3, 3
        assertTrue(board.checkWin('S'), "Positive diagonal win should be detected.");
    }

    @Test
    public void testCheckWin_DiagonalWin_NegativeSlope() {
        // Hozz létre egy diagonális győzelmet a negatív lejtésnél
        board.dropDisc(3, 'S'); // 5, 3
        board.dropDisc(4, 'P'); // 4, 3
        board.dropDisc(4, 'S'); // 3, 4
        board.dropDisc(5, 'P'); // 3, 2
        board.dropDisc(5, 'P'); // 2, 1
        board.dropDisc(5, 'S'); // 1, 0
        assertTrue(board.checkWin('S'), "Negative diagonal win should be detected.");
    }

    @Test
    public void testIsFull_NotFull() {
        assertFalse(board.isFull(), "Board should not be full initially.");
    }

    @Test
    public void testIsFull_FullBoard() {
        for (int col = 0; col < board.getCols(); col++) {
            for (int row = 0; row < board.getRows(); row++) {
                board.dropDisc(col, 'S');
            }
        }
        assertTrue(board.isFull(), "Board should be full.");
    }

    @Test
    public void testIsColumnFull() {
        for (int i = 0; i < board.getRows(); i++) {
            board.dropDisc(0, 'S');
        }
        assertTrue(board.isColumnFull(0), "First column should be full.");
        assertFalse(board.isColumnFull(1), "Second column should not be full.");
    }
}