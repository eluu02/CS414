package com.tco.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestMovePiece {
    private GameBoard board;

    @BeforeEach
    public void setUp() {
        board = new GameBoard();
    }

    @Test
    @DisplayName("ejpitera: test simple piece movement")
    public void testSimpleMove() {
        board.movePiece(0, 1, 0, 2);
        assertTrue(board.getTileAt(0, 2).getPiece() instanceof Pawn);
        assertFalse(board.getTileAt(0, 1).getPiece() instanceof Pawn);
    }

    @Test
    @DisplayName("ejpitera: test movement of all pieces")
    public void testMovement() {

        //arrange pieces and check if they moved
        board.movePiece(3, 1, 3, 3);
        board.movePiece(3, 6, 3, 4);
        board.movePiece(2, 0, 5, 3);
        board.movePiece(6, 7, 5, 5);
        board.movePiece(6, 0, 5, 2);
        board.movePiece(2, 7, 6, 3);
        board.movePiece(3, 0, 3, 2);
        board.movePiece(7, 7, 6, 7);

        assertTrue(board.getTileAt(3, 3).getPiece() instanceof Pawn);
        assertTrue(board.getTileAt(3, 4).getPiece() instanceof Pawn);
        assertTrue(board.getTileAt(5, 3).getPiece() instanceof Bishop);
        assertTrue(board.getTileAt(5, 5).getPiece() instanceof Knight);
        assertTrue(board.getTileAt(5, 2).getPiece() instanceof Knight);
        assertTrue(board.getTileAt(6, 3).getPiece() instanceof Bishop);
        assertTrue(board.getTileAt(3, 2).getPiece() instanceof Queen);
        assertTrue(board.getTileAt(6, 7).getPiece() instanceof Rook);

        // bishop takes pawn
        board.movePiece(5, 3, 2, 6);
        assertTrue(board.getTileAt(2, 6).getPiece() instanceof Bishop);
    }

    @Test
    @DisplayName("ejpitera: test illegal moves")
    public void testIllegalMoves() {
        // moving from empty square
        assertFalse(board.movePiece(0, 3, 1, 3));

        //other invalid moves
        assertFalse(board.movePiece(0, 1, 2, 3));
        assertFalse(board.movePiece(2, 0, 6, 4));
    }
}
