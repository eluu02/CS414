package com.tco.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tco.misc.GameBoard;

import static org.junit.jupiter.api.Assertions.*;

public class TestCheckMate {
    
    @Test
    @DisplayName("ejpitera: checkmate, unable to evade, block, or capture attacker")
    public void testFoolsMate() {
        GameBoard board = new GameBoard();
        board.movePiece(5, 1, 5, 2);
        board.movePiece(4, 6, 4, 4);
        board.movePiece(6, 1, 6, 3);
        board.movePiece(3, 7, 7, 3);
        assertTrue(board.isCheckmate("White", 4, 0));
    }

    @Test
    @DisplayName("ejpitera: evade check by moving king")
    public void testEvadeCheck() {
        GameBoard board = new GameBoard("QW40,KB47,");
        assertTrue(board.isCheck("Black", 4, 7));
        assertFalse(board.isCheckmate("Black", 4, 7));
    }

    @Test
    @DisplayName("ejpitera: evade check by capturing attacker")
    public void testCaptureAttacker() {
        GameBoard board = new GameBoard("QW40,KB47,BB04,PB37,PB36,PB57,PB56,");
        assertTrue(board.isCheck("Black", 4, 7));
        assertFalse(board.isCheckmate("Black", 4, 7));
    }
}
