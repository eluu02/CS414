package com.tco.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tco.misc.Queen;

import static org.junit.jupiter.api.Assertions.*;

public class TestQueen {
    private Queen queen;
    private GameBoard board;

    @BeforeEach
    public void setUp() {
        queen = new Queen("White");
        board = new GameBoard();
    }

    @Test
    @DisplayName("ejpitera: test vertical movement Qd1 -> Qd4")
    public void testQueenVerticalMove() {
        assertTrue(queen.isLegalMove(3, 2, 3, 3, board));
    }

    @Test
    @DisplayName("ejpitera: test horizontal movement Qd4 -> Qa4")
    public void testQueenHorizontalMove() {
        assertTrue(queen.isLegalMove(3, 3, 0, 3, board));
    }

    @Test
    @DisplayName("ejpitera: test diagonal movement Qd1 -> Qa4")
    public void testQueenDiagonalMove() {
        assertTrue(queen.isLegalMove(3, 2, 6, 5, board));
    }

    @Test
    @DisplayName("ejpitera: test illegal move")
    public void testQueenIllegalMove() {
        assertFalse(queen.isLegalMove(3, 0, 3, 0, board));
        assertFalse(queen.isLegalMove(3, 2, 7, 3, board));
    }

    @Test
    @DisplayName("ejpitera: test capture")
    public void testQueenCapture() {
        assertTrue(queen.isLegalMove(0, 2, 0, 6, board));
        assertTrue(queen.isLegalMove(0, 2, 4, 6, board));
    }

    @Test
    @DisplayName("ejpitera: test illegal capture")
    public void testQueenIllegalCapture() {
        assertFalse(queen.isLegalMove(0, 2, 0, 1, board));
    }

    @Test
    @DisplayName("ejpitera: test blocked path")
    public void testQueenBlockedPath() {
        assertFalse(queen.isLegalMove(3, 0, 3, 7, board));
    }
}
