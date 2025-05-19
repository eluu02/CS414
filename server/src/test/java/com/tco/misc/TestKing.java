package com.tco.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.tco.misc.King;

public class TestKing {
    private King king;
    private GameBoard board;

    @BeforeEach
    public void setUp() {
        king = new King("White");
        board = new GameBoard();
    }
 
    @Test
    @DisplayName("ejpitera: test Kc3 -> Kb3")
    public void testKingMoveLeft() {
        assertTrue(king.isLegalMove(2, 2, 1, 2, board));
    }

    @Test
    @DisplayName("ejpitera: test Kc3 -> Kc4")
    public void testKingMoveUp() {
        assertTrue(king.isLegalMove(2, 2, 2, 3, board));
    }

    @Test 
    @DisplayName("ejpitera: test Kc3 -> Kb4")
    public void testKingMoveDiagonal() {
        assertTrue(king.isLegalMove(2, 2, 1, 3, board));
    }

    @Test  
    @DisplayName("ejpitera: test illegal king move Kc3 -> Kh8")
    public void testKingIllegalMove() {
        assertFalse(king.isLegalMove(2, 2, 7, 7, board));
    }

    @Test
    @DisplayName("ejpitera: test capture with king")
    public void testCaptureWithKing() {
        board.getTileAt(4, 3).setPiece(new Pawn("Black"));
        assertTrue(king.isLegalMove(4, 2, 4, 3, board));
    }

    @Test
    @DisplayName("ejpitera: test illegal capture")
    public void testKingIllegalCapture() {
        assertFalse(king.isLegalMove(4, 0, 4, 1, board));
    }
}
