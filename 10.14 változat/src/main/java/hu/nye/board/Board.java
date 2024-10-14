package hu.nye.board;

import hu.nye.FileHandler.FileHandler;
import java.util.Arrays;

public final class Board {
    private final int rows;
    private final int cols;
    private final char[][] grid;
    private final FileHandler fileHandler;
    private static final int WINNING_COUNT = 4; // Győzelemhez szükséges lemezek száma

    public Board(final int numRows, final int numCols) {
        this.rows = numRows;
        this.cols = numCols;
        this.grid = new char[rows][cols];
        this.fileHandler = new FileHandler();
        initializeBoard();
    }

    public Board(char[][] grid) {
        this.rows = grid.length;
        this.cols = grid[0].length;
        this.grid = grid;
        this.fileHandler = new FileHandler();
    }

    public void clearBoard() {
        for (int i = 0; i < rows; i++) {
            Arrays.fill(grid[i], '.');
        }
    }

    public void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = '.';
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public char[][] getGrid() {
        return grid;
    }

    public void printBoard() {
        System.out.print("  ");
        for (int j = 0; j < cols; j++) {
            System.out.print((char) ('a' + j) + " ");
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean dropDisc(final int col, final char disc) {
        if (col < 0 || col >= cols) {
            return false;
        }

        for (int row = rows - 1; row >= 0; row--) {
            if (grid[row][col] == '.') {
                grid[row][col] = disc;
                return true;
            }
        }
        return false;
    }

    public boolean checkWin(final char disc) {
        // Ellenőrizd a sorokat, oszlopokat és diagonális győzelmet
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == disc) {
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

    private boolean checkDirection(final int row, final int col, final int deltaRow, final int deltaCol, final char disc) {
        int count = 0;
        for (int i = 0; i < WINNING_COUNT; i++) {
            int r = row + i * deltaRow;
            int c = col + i * deltaCol;
            // Ellenőrizzük a határokat
            if (r < 0 || r >= rows || c < 0 || c >= cols || grid[r][c] != disc) {
                break;
            }
            count++;
        }
        return count == WINNING_COUNT; // Győzelem, ha négyet találtunk
    }

    public boolean isFull() {
        for (int j = 0; j < cols; j++) {
            if (grid[0][j] == '.') {
                return false;
            }
        }
        return true;
    }

    public boolean isColumnFull(final int col) {
        return grid[0][col] != '.';
    }

    public void saveBoardToFile(String filename) {
        fileHandler.saveBoardToFile(grid, filename);
    }

    public void loadBoardFromFile(String filename) {
        char[][] loadedGrid = fileHandler.loadBoardFromFile(filename, rows, cols);
        for (int i = 0; i < rows; i++) {
            System.arraycopy(loadedGrid[i], 0, grid[i], 0, cols);
        }
    }
}
