package com.tco.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tco.misc.Knight;

import static org.junit.jupiter.api.Assertions.*;
public class TestKnight {
    private Knight knight;
    private GameBoard board;

    @BeforeEach
    public void setUp() {
        knight = new Knight("White");
        board = new GameBoard();
    }

    @Test
    @DisplayName("ejpitera: test legal knight moves")
    public void testKnightLegalMove() {
        //Nb1 -> Nc3
        assertTrue(knight.isLegalMove(1, 0, 2, 2, board));
        //Nb1 -> Nd2
        assertTrue(knight.isLegalMove(1, 2, 3, 3, board));
    }

    @Test
    @DisplayName("ejpitera: test illegal knight move")
    public void testKnightIllegalMove() {
        //same position
        assertFalse(knight.isLegalMove(1,0,1,0, board));
        //Nb1 -> Nd4
        assertFalse(knight.isLegalMove(1,0,3,3, board));
    }

    @Test
    @DisplayName("ejpitera: test knight capture")
    public void testKnightCapture() {
        Knight blackKnight = new Knight("Black");
        assertTrue(knight.isLegalMove(7, 4, 6, 6, board));
        assertFalse(blackKnight.isLegalMove(7, 4, 6, 6, board));
    }
}
