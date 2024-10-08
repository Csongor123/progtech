package hu.nye.board;

public class Board {
    private final int rows; /**a változó a táblázat sorainak számát tárolja*/
    private final int cols; /**a változó a táblázat oszlopainak számát tárolja*/
    private final char[][] grid; /**a kétdimenziós karaktertömb  a játéktáblát reprezentálja.*/


    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];
        initializeBoard();
    }

    public void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = '.';
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean dropDisc(int col, char disc) {
        if (col < 0 || col >= cols) {
            return false;  // Érvénytelen oszlop
        }

        for (int row = rows - 1; row >= 0; row--) {
            if (grid[row][col] == '.') {
                grid[row][col] = disc;
                return true;
            }
        }
        return false;  // Az oszlop tele van
    }

    public boolean checkWin(char disc) {
        // Függőleges, vízszintes és átlós ellenőrzések (4 azonos egymás után)
        // Ezt részletesen meg kell írni, de itt egy egyszerűsített példa:
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == disc) {
                    if (checkDirection(i, j, 1, 0, disc) || // Vízszintes
                            checkDirection(i, j, 0, 1, disc) || // Függőleges
                            checkDirection(i, j, 1, 1, disc) || // Átlós (le-fel)
                            checkDirection(i, j, 1, -1, disc)) { // Átlós (fel-le)
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int row, int col, int dRow, int dCol, char disc) {
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

    public int getCols() {
        return 0;
    }
}
