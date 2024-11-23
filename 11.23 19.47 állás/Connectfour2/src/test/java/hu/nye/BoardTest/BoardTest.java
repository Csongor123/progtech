package hu.nye.BoardTest;
import hu.nye.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        // A tesztek előtt új Board objektumot hozunk létre
        board = new Board(6, 7);  // 6 sor és 7 oszlop
    }


    @Test
    void testInitializeBoard() {
        // Ellenőrizzük, hogy a tábla megfelelően van inicializálva (pontokkal)
        char[][] grid = board.getBoardGrid();
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                assertEquals('.', grid[i][j], "Tábla inicializálása nem megfelelő");
            }
        }
    }

    @Test
    void testDropDiscValidMove() {
        // Ellenőrizzük, hogy a lemez sikeresen lehelyezhető egy oszlopba
        assertTrue(board.dropDisc(3, 'X'), "Lemez elhelyezése nem sikerült");
        assertEquals('X', board.getBoardGrid()[5][3], "A lemez nem a várt helyen van");
    }

    @Test
    void testDropDiscInvalidMove() {
        // Ellenőrizzük, hogy a lemez nem helyezhető el érvénytelen oszlopba
        assertFalse(board.dropDisc(7, 'X'), "Érvénytelen oszlopba helyezhető lemezt elfogadott");
        assertFalse(board.dropDisc(-1, 'O'), "Érvénytelen oszlopba helyezhető lemezt elfogadott");
    }

    @Test
    void testDropDiscColumnFull() {
        // Megtöltjük az oszlopot és ellenőrizzük, hogy nem lehet új lemezt letenni
        for (int i = 0; i < 6; i++) {
            board.dropDisc(2, 'X');
        }
        assertFalse(board.dropDisc(2, 'O'), "Nem várt módon tudtunk lemezt tenni egy tele oszlopba");
    }

    @Test
    void testCheckWinHorizontal() {
        // Teszteljük a vízszintes győzelmet
        board.dropDisc(0, 'X');
        board.dropDisc(1, 'X');
        board.dropDisc(2, 'X');
        board.dropDisc(3, 'X');
        assertTrue(board.checkWin('X'), "Hibásan nem találtunk vízszintes győzelmet");
    }

    @Test
    void testCheckWinVertical() {
        // Teszteljük a függőleges győzelmet
        board.dropDisc(0, 'X');
        board.dropDisc(0, 'X');
        board.dropDisc(0, 'X');
        board.dropDisc(0, 'X');
        assertTrue(board.checkWin('X'), "Hibásan nem találtunk függőleges győzelmet");
    }

    @Test
    void testCheckWinDiagonal() {
        // Teszteljük a diagonális győzelmet
        board.dropDisc(0, 'X');
        board.dropDisc(1, 'X');
        board.dropDisc(1, 'X');
        board.dropDisc(2, 'X');
        board.dropDisc(2, 'X');
        board.dropDisc(2, 'X');
        board.dropDisc(3, 'X');
        assertTrue(board.checkWin('X'), "Hibásan nem találtunk diagonális győzelmet");
    }

    @Test
    void testIsFull() {
        // Töltsük fel a táblát 7 oszlopban 6 lemezzel
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                board.dropDisc(i, (j % 2 == 0) ? 'X' : 'O'); // Felváltva 'X' és 'O' lemezek
            }
        }
        // Ellenőrizzük, hogy a tábla valóban tele van
        assertTrue(board.isFull(), "A tábla nem lett teljes");
    }


    @Test
    void testIsColumnFull() {
        // Teszteljük, hogy az oszlopok tele vannak-e
        for (int i = 0; i < 6; i++) {
            board.dropDisc(0, 'X');
        }
        assertTrue(board.isColumnFull(0), "A tele oszlopot nem érzékeli");
        assertFalse(board.isColumnFull(1), "A nem tele oszlopot tévesen teli oszlopnak érzékeli");
    }

    @Test
    void testClearBoard() {
        // Teszteljük a táblát törlés funkciót
        board.dropDisc(0, 'X');
        board.clearBoard();
        assertEquals('.', board.getBoardGrid()[5][0], "A tábla törlését követően nem tiszta");
    }

    @Test
    void testLoadSavedBoard() {
        // Teszteljük, hogy a táblát sikeresen betölthetjük
        char[][] savedBoard = new char[6][7];
        savedBoard[0][0] = 'X';
        board.loadSavedBoard(savedBoard);
        assertEquals('X', board.getBoardGrid()[0][0], "A táblát nem sikerült betölteni");
    }
}
