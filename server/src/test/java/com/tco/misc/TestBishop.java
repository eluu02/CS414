package com.tco.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.tco.misc.Bishop;

public class TestBishop {
    private Bishop bishop;
    private GameBoard board;

    @BeforeEach
    public void setUp() {
        bishop = new Bishop("White");
        board = new GameBoard();
    }

    @Test
    @DisplayName("ejpitera: test Bh3 -> Be6")
    public void testBishopLegalMove() {
        assertTrue(bishop.isLegalMove(7, 2, 4, 5, board));
    }

    @Test
    @DisplayName("ejpitera: test illegal bishop moves")
    public void testBishopIllegalMove() {
        assertFalse(bishop.isLegalMove(5, 0, 5, 0, board));
        assertFalse(bishop.isLegalMove(7, 2, 3, 5, board));
    }

    @Test
    @DisplayName("ejpitera: test bishop capture")
    public void testBishopCapture() {
        assertTrue(bishop.isLegalMove(7, 2, 3, 6, board));
    }

    @Test
    @DisplayName("ejpitera: test illegal capture")
    public void testBishopIllegalCapture() {
        assertFalse(bishop.isLegalMove(2, 0, 1, 1, board));
    }

    @Test
    @DisplayName("ejpitera: test blocked path")
    public void testBishopBlockedPath() {
        assertFalse(bishop.isLegalMove(2, 0, 7, 5, board));
    }

    @Test
    @DisplayName("lavelle3: test move when location is blocked by friendly piece")
    public void testFriendlyPieceBlock() {
        board.getTileAt(5, 5).setPiece(new Bishop("White"));
        assertFalse(bishop.isLegalMove(3, 3, 7, 7, board));
    }

    @Test
    @DisplayName("lavelle3: test invalid move diagonally")
    public void testInvalidDiagonalPaths() {
        assertFalse(bishop.isLegalMove(3, 3, 6, 5, board));
        assertFalse(bishop.isLegalMove(3, 3, 2, 5, board));
    }

    @Test
    @DisplayName("lavelle3: test a midway blocked path when trying to move")
    public void testBlockedPathMidway() {
        board.getTileAt(4, 4).setPiece(new Bishop("Black"));
        assertFalse(bishop.isLegalMove(3, 3, 6, 6, board));
    }

    @Test
    @DisplayName("lavelle3: test proper handling of when piece is moved out of bounds")
    public void testMoveOutOfBounds() {
        assertFalse(bishop.isLegalMove(3, 3, 8, 8, board));
        assertFalse(bishop.isLegalMove(3, 3, -1, -1, board));
    }

    @Test
    @DisplayName("lavelle3: test proper handling of no move")
    public void testNoMove() {
        assertFalse(bishop.isLegalMove(3, 3, 3, 3, board));
    }

}
