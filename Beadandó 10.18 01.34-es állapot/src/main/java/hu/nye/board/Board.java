package hu.nye.board;

import hu.nye.FileHandler.FileHandler;
import java.io.IOException;
import java.util.Arrays;

/**
 * A játéktábla reprezentációja.
 */
public final class Board {
    /** A táblázat sorainak száma. */
    private final int rows;  // A táblázat sorainak száma
    /** A táblázat oszlopainak száma. */
    private final int cols;  // A táblázat oszlopainak száma
    /** A táblát reprezentáló karakterek mátrixa. */
    private final char[][] boardGrid;  // A tábla állapotát tároló tömb
    /** Fájlkezelő az állapot mentéséhez és betöltéséhez. */
    private final FileHandler fileHandler;
    /** Győzelemhez szükséges lemezek száma. */
    private static final int WINNING_COUNT = 4;

    /**
     * Létrehozza a táblát a megadott sor- és oszlopszámmal.
     *
     * @param numRows a sorok száma
     * @param numCols az oszlopok száma
     */
    public Board(final int numRows, final int numCols) {
        this.rows = numRows;
        this.cols = numCols;
        this.boardGrid = new char[rows][cols];
        this.fileHandler = new FileHandler();
        initializeBoard();
    }

    /**
     * Létrehozza a táblát a megadott állapotú mátrixból.
     *
     * @param grid a táblát reprezentáló karakterek mátrixa
     */
    public Board(final char[][] grid) {
        this.rows = grid.length;
        this.cols = grid[0].length;
        this.boardGrid = grid;
        this.fileHandler = new FileHandler();
    }

    /**
     * Törli a táblát, és visszaállítja az alapértelmezett állapotba.
     */
    public void clearBoard() {
        for (int i = 0; i < rows; i++) {
            Arrays.fill(boardGrid[i], '.');
        }
    }

    /**
     * Inicializálja a táblát alapértelmezett karakterekkel.
     */
    public void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            Arrays.fill(boardGrid[i], '.');
        }
    }

    /**
     * Visszaadja a táblázat sorainak számát.
     *
     * @return a sorok száma
     */
    public int getRows() {
        return rows;
    }

    /**
     * Visszaadja a táblázat oszlopainak számát.
     *
     * @return az oszlopok száma
     */
    public int getCols() {
        return cols;
    }

    /**
     * Visszaadja a táblát reprezentáló karakterek mátrixát.
     *
     * @return a tábla mátrixa
     */
    public char[][] getBoardGrid() {
        return boardGrid;
    }

    /**
     * Kiírja a táblázat állapotát a konzolra.
     */
    public void printBoard() {
        System.out.print("  ");
        for (int j = 0; j < cols; j++) {
            System.out.print((char) ('a' + j) + " ");
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < cols; j++) {
                System.out.print(boardGrid[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Egy lemezt helyez el a megadott oszlopban.
     *
     * @param col  az oszlop, ahová a lemezt el szeretnénk helyezni
     * @param disc a lemez, amelyet el szeretnénk helyezni
     * @return true, ha a lemez sikeresen elhelyezve
     */
    public boolean dropDisc(final int col, final char disc) {
        if (col < 0 || col >= cols) {
            return false;
        }

        for (int row = rows - 1; row >= 0; row--) {
            if (boardGrid[row][col] == '.') {
                boardGrid[row][col] = disc;
                return true;
            }
        }
        return false;
    }

    /**
     * Ellenőrzi, hogy a megadott lemezzel van-e győzelem.
     *
     * @param disc a vizsgált lemez
     * @return true, ha győzelem van
     */
    public boolean checkWin(final char disc) {
        // Ellenőrizzük a győzelmet minden pozícióból
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (boardGrid[row][col] == disc) {
                    if (checkDirection(row, col, 1, 0, disc)
                            || // Vízszintes
                            checkDirection(row, col, 0, 1, disc)
                            || // Függőleges
                            checkDirection(row, col, 1, 1, disc)
                            || // Pozitív diagonális
                            checkDirection(row, col, 1,
                                    -1, disc)) { // Negatív diagonális
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Ellenőrzi, hogy a megadott irányban van-e győzelem.
     *
     * @param row      az aktuális sor
     * @param col      az aktuális oszlop
     * @param deltaRow az irány a sorokban
     * @param deltaCol az irány az oszlopokban
     * @param disc     a vizsgált lemez
     * @return true, ha győzelem van
     */
    private boolean checkDirection(final int row, final int col,
                                   final int deltaRow, final int deltaCol,
                                   final char disc) {
        int count = 0;
        for (int i = 0; i < WINNING_COUNT; i++) {
            int r = row + i * deltaRow;
            int c = col + i * deltaCol;
            // Ellenőrizzük a határokat
            if (r < 0 || r >= rows || c < 0
                    || c >= cols || boardGrid[r][c] != disc) {
                break; // Megállunk, ha a feltételek nem teljesülnek
            }
            count++;
        }
        return count == WINNING_COUNT; // Győzelem, ha négyet találtunk
    }

    /**
     * Ellenőrzi, hogy a tábla tele van-e.
     *
     * @return true, ha a tábla tele van
     */
    public boolean isFull() {
        for (int j = 0; j < cols; j++) {
            if (boardGrid[0][j] == '.') {
                return false;
            }
        }
        return true;
    }

    /**
     * Ellenőrzi, hogy a megadott oszlop tele van-e.
     *
     * @param col az ellenőrizendő oszlop
     * @return true, ha az oszlop tele van
     */
    public boolean isColumnFull(final int col) {
        return boardGrid[0][col] != '.';
    }

    /**
     * Ment egy táblát a megadott fájlba.
     *
     * @param filename a menteni kívánt fájl neve
     */
    public void saveBoardToFile(final String filename) {
        try {
            fileHandler.saveBoardToFile(boardGrid, filename);
        } catch (IOException e) {
            System.err.println("Hiba a tábla mentésekor: " + e.getMessage());
            // Itt kezelheted a hibát, pl. jelezheted a felhasználónak
        }
    }

    /**
     * Betölti a táblát a megadott fájlból.
     *
     * @param filename a betölteni kívánt fájl neve
     */
    public void loadBoardFromFile(final String filename) {
        try {
            char[][] loadedGrid = fileHandler.
                    loadBoardFromFile(filename, rows, cols);
            for (int i = 0; i < rows; i++) {
                System.arraycopy(loadedGrid[i], 0, boardGrid[i], 0, cols);
            }
        } catch (IOException e) {
            System.err.println("Hiba a tábla betöltésekor: " + e.getMessage());
            // Itt kezelheted a hibát, pl. alapértelmezett tábla beállítása
            initializeBoard(); // Alapértelmezett tábla beállítása
        }
    }
}
