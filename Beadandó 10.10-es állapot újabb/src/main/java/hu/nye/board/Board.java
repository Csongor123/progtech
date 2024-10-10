package hu.nye.board;
public final class Board {
    private final int rows;
    private final int cols;
    private final char[][] grid;

    public Board(final int numRows, final int numCols) {
        this.rows = numRows;
        this.cols = numCols;
        this.grid = new char[rows][cols];
        initializeBoard();
    }

    public Board(char[][] grid) {
        this.rows = grid.length;
        this.cols = grid[0].length;
        this.grid = grid;
    }

    public void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = '.';
            }
        }
    }

    // Getter metódusok hozzáadása
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
        for (int i = 0; i < rows; i++) {
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
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == disc) {
                    if (checkDirection(i, j, 1, 0, disc) ||
                            checkDirection(i, j, 0, 1, disc) ||
                            checkDirection(i, j, 1, 1, disc) ||
                            checkDirection(i, j, 1, -1, disc)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkDirection(final int row, final int col, final int dRow, final int dCol, final char disc) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int newRow = row + i * dRow;
            int newCol = col + i * dCol;
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol] == disc) {
                count++;
            } else {
                break;
            }
        }
        return count == 4;
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
}