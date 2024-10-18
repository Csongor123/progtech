package hu.nye.GameTest;

import hu.nye.board.Board;
import hu.nye.game.Game;
import hu.nye.player.Player;
import hu.nye.FileHandler.FileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTest {

    private Game game;
    private Board board;
    private Player firstPlayer;
    private Player secondPlayer;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        board = Mockito.mock(Board.class);
        firstPlayer = Mockito.mock(Player.class);
        secondPlayer = Mockito.mock(Player.class);
        scanner = Mockito.mock(Scanner.class);

        when(firstPlayer.getDisc()).thenReturn('S');
        when(secondPlayer.getDisc()).thenReturn('P');

        game = new Game(board, firstPlayer, secondPlayer);
    }

    @Test
    void testPlayGame_LoadsGameFromFile() throws IOException {
        when(scanner.next()).thenReturn("yes", "testFile");
        when(board.loadBoardFromFile("testFile")).thenReturn(null);

        game.playGame(scanner);

        verify(board).loadBoardFromFile("testFile");
        verify(board).clearBoard();
    }

    @Test
    void testPlayGame_ClearsBoardIfNoLoad() throws IOException {
        when(scanner.next()).thenReturn("no");

        game.playGame(scanner);

        verify(board).clearBoard();
    }

    @Test
    void testPlayGame_AIMove() throws IOException {
        when(scanner.next()).thenReturn("no");
        when(board.isFull()).thenReturn(false);
        when(board.dropDisc(anyInt(), anyChar())).thenReturn(true);
        when(firstPlayer.getName()).thenReturn("Player 1");
        when(secondPlayer.getName()).thenReturn("AI");
        when(secondPlayer.getDisc()).thenReturn('P');

        // Simulate an AI move
        when(board.checkWin('P')).thenReturn(false);
        when(board.isColumnFull(anyInt())).thenReturn(false);

        game.playGame(scanner);

        verify(board, atLeastOnce()).dropDisc(anyInt(), eq('P'));
    }

    @Test
    void testPlayGame_PlayerWin() throws IOException {
        when(scanner.next()).thenReturn("no", "a", "b", "c");
        when(board.dropDisc(0, 'S')).thenReturn(true);
        when(board.dropDisc(1, 'P')).thenReturn(true);
        when(board.dropDisc(2, 'S')).thenReturn(true);
        when(board.dropDisc(3, 'P')).thenReturn(true);
        when(board.dropDisc(4, 'S')).thenReturn(true);
        when(board.checkWin('S')).thenReturn(true);

        game.playGame(scanner);

        verify(board).printBoard();
        verify(board).checkWin('S');
        verify(board).dropDisc(0, 'S');
        verify(board).dropDisc(1, 'P');
        assertEquals("Player 1 wins!", "Player 1 wins!");
    }

    @Test
    void testPlayGame_Tie() throws IOException {
        when(scanner.next()).thenReturn("no");
        when(board.isFull()).thenReturn(true);
        when(board.checkWin(anyChar())).thenReturn(false);

        game.playGame(scanner);

        verify(board).printBoard();
        assertEquals("It's a tie!", "It's a tie!");
    }
}
