package hu.nye.board;

import java.util.Arrays;   // Standard Java package


/**
 * A játéktábla reprezentációja.
 */
public final class Board {
    /** A táblázat sorainak száma. */
    private int rows; // A táblázat sorainak száma
    /** A táblázat oszlopainak száma. */
    private final int cols; // A táblázat oszlopainak száma
    /** A táblát reprezentáló karakterek mátrixa. */
    private final char[][] boardGrid; // A tábla állapotát tároló tömb
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
        initializeBoard();
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
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (boardGrid[row][col] == disc) {
                    if (checkDirection(row, col, 1, 0, disc) || // Vízszintes
                            checkDirection(row, col, 0, 1, disc) || // Függőleges
                            checkDirection(row, col, 1, 1, disc) || // Pozitív diagonális
                            checkDirection(row, col, 1, -1, disc)) { // Negatív diagonális
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
            if (r < 0 || r >= rows || c < 0 || c >= cols || boardGrid[r][c] != disc) {
                break;
            }
            count++;
        }
        return count == WINNING_COUNT;
    }

    /**
     * Ellenőrzi, hogy a tábla tele van-e.
     *
     * @return true, ha a tábla tele van
     */
    public boolean isFull() {
        // Minden oszlopot ellenőrizni kell, hogy az első sorban lévő értékek ne befolyásolják a teljes tábla állapotát
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (boardGrid[i][j] == '.') { // Ha van üres hely
                    return false;
                }
            }
        }
        return true; // Ha nincs üres hely
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
     * Betölti a táblát a fájlból.
     *
     * @param savedBoard a fájlból betöltött karakter mátrix
     */
    public void loadSavedBoard(final char[][] savedBoard) {
        for (int i = 0; i < rows; i++) {
            System.arraycopy(savedBoard[i], 0, boardGrid[i], 0, cols);
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
