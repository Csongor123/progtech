package hu.nye.GameTest;

import hu.nye.board.Board;
import hu.nye.databasehandler.DatabaseHandler;
import hu.nye.filehandler.FileHandler;
import hu.nye.game.Game;
import hu.nye.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tesztosztály a Game osztályhoz.
 */
class GameTest {

    private Board mockBoard;
    private Player mockPlayerOne;
    private Player mockPlayerTwo;
    private Game game;

    @BeforeEach
    void setUp() {
        mockBoard = mock(Board.class);
        mockPlayerOne = mock(Player.class);
        mockPlayerTwo = mock(Player.class);
        game = new Game(mockBoard, mockPlayerOne, mockPlayerTwo);
    }

    @Test
    void testGenerateRandomMove() {
        when(mockBoard.getCols()).thenReturn(7);
        when(mockBoard.isColumnFull(anyInt())).thenReturn(false);

        int move = game.generateRandomMove();

        assertTrue(move >= 0 && move < 7, "A generált oszlop nem megfelelő intervallumban van.");
        verify(mockBoard, atLeastOnce()).getCols();
        verify(mockBoard, atLeastOnce()).isColumnFull(anyInt());
    }

    @Test
    void testPlayGame_NoLoadAndPlayerWin() throws IOException {
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.next()).thenReturn("no").thenReturn("a");

        when(mockPlayerOne.getName()).thenReturn("Player One");
        when(mockPlayerOne.getDisc()).thenReturn('X');
        when(mockBoard.dropDisc(0, 'X')).thenReturn(true);
        when(mockBoard.checkWin('X')).thenReturn(true); // Nyerési feltétel teljesül
        when(mockBoard.isFull()).thenReturn(false);

        game.playGame(mockScanner);

        verify(mockBoard).clearBoard();
        verify(mockBoard, atLeastOnce()).printBoard();
        verify(mockBoard).dropDisc(0, 'X');
        verify(mockBoard).checkWin('X');
        verify(mockBoard, never()).isFull(); // Nem ellenőrizzük a telítettséget, mert nyert valaki
    }

    @Test
    void testPlayGame_LoadGameAndTie() throws IOException {
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.next()).thenReturn("yes").thenReturn("a");

        when(mockPlayerOne.getName()).thenReturn("Player One");
        when(mockPlayerOne.getDisc()).thenReturn('X');
        when(mockBoard.dropDisc(0, 'X')).thenReturn(true);
        when(mockBoard.checkWin('X')).thenReturn(false);
        when(mockBoard.isFull()).thenReturn(true); // Döntetlen feltétel

        game.playGame(mockScanner);

        verify(mockBoard, never()).clearBoard();
        verify(mockBoard, atLeastOnce()).printBoard();
        verify(mockBoard).dropDisc(0, 'X');
        verify(mockBoard).checkWin('X');
        verify(mockBoard).isFull();
    }

    @Test
    void testUpdatePlayerScore() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException {
        DatabaseHandler mockDatabaseHandler = mock(DatabaseHandler.class);
        var updatePlayerScoreMethod = Game.class.getDeclaredMethod("updatePlayerScore", String.class);
        updatePlayerScoreMethod.setAccessible(true);

        assertDoesNotThrow(() -> updatePlayerScoreMethod.invoke(game, "Player One"));
        verify(mockDatabaseHandler, times(1)).updatePlayerScore("Player One");
    }

    @Test
    void testSaveGame() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        FileHandler mockFileHandler = mock(FileHandler.class);
        char[][] mockBoardGrid = {{'X', 'O'}, {'O', 'X'}};
        when(mockBoard.getBoardGrid()).thenReturn(mockBoardGrid);

        var saveGameMethod = Game.class.getDeclaredMethod("saveGame", String.class);
        saveGameMethod.setAccessible(true);
        saveGameMethod.invoke(game, "Player X wins");

        verify(mockFileHandler).appendBoardToLogFile(mockBoardGrid, "Player X wins");
    }
}
