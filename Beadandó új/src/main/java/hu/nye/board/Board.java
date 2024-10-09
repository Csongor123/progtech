package hu.nye.board;

/**
 * Represents a game board for a connect-four type game.
 */
public final class Board {
    /** The number of rows on the board. */
    private final int rows;

    /** The number of columns on the board. */
    private final int cols;

    /** A 2D array representing the game board. */
    private final char[][] grid;

    /** The number of discs in a row needed to win. */
    private static final int WINNING_COUNT = 4;

    /**
     * Constructs a Board with the specified number of rows and columns.
     *
     * @param numRows the number of rows on the board.
     * @param numCols the number of columns on the board.
     */
    public Board(final int numRows, final int numCols) {
        this.rows = numRows;
        this.cols = numCols;
        this.grid = new char[rows][cols];
        initializeBoard();
    }

    /**
     * Initializes the game board with empty slots.
     */
    public void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = '.';  // Fill empty slots with '.' character
            }
        }
    }

    /**
     * Prints the current state of the game board.
     */
    public void printBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Drops a disc into the specified column on the board.
     *
     * @param col the column where the disc will be dropped.
     * @param disc the disc character to be placed on the board.
     * @return true if the disc was successfully placed, false otherwise.
     */
    public boolean dropDisc(final int col, final char disc) {
        if (col < 0 || col >= cols) {
            return false;  // Invalid column
        }

        for (int row = rows - 1; row >= 0; row--) {
            if (grid[row][col] == '.') {
                grid[row][col] = disc;
                return true;
            }
        }
        return false;  // The column is full
    }

    /**
     * Checks if the specified disc has won the game.
     *
     * @param disc the disc character to check for a win.
     * @return true if the disc has won, false otherwise.
     */
    public boolean checkWin(final char disc) {
        // Check for winning sequences (4 in a row) horizontally, vertically,
        // and diagonally
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == disc) {
                    if (checkDirection(i, j, 1, 0, disc) || // Horizontal
                            checkDirection(i, j, 0, 1, disc) || // Vertical
                            checkDirection(i, j, 1,
                                    1, disc) || // Diagonal (bottom-right)
                            checkDirection(i, j, 1, -1, disc)) {
                        // Diagonal (top-right)
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks a specific direction for a winning sequence.
     *
     * @param row the starting row.
     * @param col the starting column.
     * @param dRow the row direction.
     * @param dCol the column direction.
     * @param disc the disc character to check.
     * @return true if a winning sequence is found, false otherwise.
     */
    private boolean checkDirection(final int row, final int col,
                                   final int dRow, final int dCol,
                                   final char disc) {
        int count = 0;
        for (int i = 0; i < WINNING_COUNT; i++) {
            int newRow = row + i * dRow;
            int newCol = col + i * dCol;
            if (newRow >= 0 && newRow < rows && newCol >= 0
                    && newCol < cols && grid[newRow][newCol] == disc) {
                count++;
            } else {
                break;
            }
        }
        return count == WINNING_COUNT;
    }

    /**
     * Checks if the board is full.
     *
     * @return true if the board is full, false otherwise.
     */
    public boolean isFull() {
        for (int j = 0; j < cols; j++) {
            if (grid[0][j] == '.') {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a specific column is full.
     *
     * @param col the column to check.
     * @return true if the column is full, false otherwise.
     */
    public boolean isColumnFull(final int col) {
        return grid[0][col] != '.';
        // If the top row is not empty,
        // the column is full
    }

    /**
     * Returns the number of columns on the board.
     *
     * @return the number of columns.
     */
    public int getCols() {
        return cols;  // Returns the number of columns
    }
}
