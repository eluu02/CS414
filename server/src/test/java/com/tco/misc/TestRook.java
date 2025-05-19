package com.tco.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tco.misc.Rook;

import static org.junit.jupiter.api.Assertions.*;

public class TestRook {
    private Rook rook;
    private GameBoard board;

    @BeforeEach
    public void setUp() {
        rook = new Rook("White");
        board = new GameBoard();
    }

    @Test
    @DisplayName("ejpitera: test vertical movement of rook Ra1 -> Ra6")
    public void testRookVerticalMovement() {
        assertTrue(rook.isLegalMove(0, 2, 0, 5, board));
    }

    @Test
    @DisplayName("ejpitera: test horizontal movement of rook Ra3 -> Rh3")
    public void testRookHorizontalMovement() {
        assertTrue(rook.isLegalMove(0, 2, 7, 2, board));
    }

    @Test
    @DisplayName("ejpitera: test illegal rook move Ra3 -> Rd6")
    public void testIllegalRookMove() {
        assertFalse(rook.isLegalMove(0, 2, 3, 5, board));
    }

    @Test
    @DisplayName("ejpitera: test rook capture")
    public void testRookCapture() {
        assertTrue(rook.isLegalMove(0, 2, 0, 6, board));
    }

    @Test
    @DisplayName("ejpitera: test illegal capture")
    public void testIllegalRookCapture() {
        assertFalse(rook.isLegalMove(0, 0, 0, 1, board));
    }

    @Test
    @DisplayName("ejpitera: test blocked path")
    public void testRookBlockedPath() {
        assertFalse(rook.isLegalMove(0, 0, 0, 5, board));
    }
}
