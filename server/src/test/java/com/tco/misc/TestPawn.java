package com.tco.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tco.misc.Pawn;

import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;

public class TestPawn {
    private Pawn whitePawn;
    private Pawn blackPawn;
    private GameBoard board;

    @BeforeEach
    public void setUp() {
        whitePawn = new Pawn("White");
        blackPawn = new Pawn("Black");
        board = new GameBoard();
    }

    @Test
    @DisplayName("ejpitera: white pawn moving one square forward a2 -> a3")
    public void testWhitePawnForwardOne() {
        assertTrue(whitePawn.isLegalMove(0, 1, 0, 2, board));
    }

    @Test
    @DisplayName("ejpitera: black pawn moving one square forward a7 -> a6")
    public void testBlackPawnForwardOne() {
        assertTrue(blackPawn.isLegalMove(0, 6, 0, 5, board));
    }

    @Test
    @DisplayName("ejpitera: white pawn moving two squares off starting tile a2 -> a4")
    public void testWhitePawnForwardTwo() {
        assertTrue(whitePawn.isLegalMove(0, 1, 0, 3, board));
    }

    @Test  
    @DisplayName("ejpitera: black pawn moving two squares off starting tile a7 -> a5")
    public void testBlackPawnForwardTwo() {
        assertTrue(blackPawn.isLegalMove(0, 6, 0, 4, board));
    }

    @Test
    @DisplayName("ejpitera: illegal pawn move such as moving backwards or sideways")
    public void testIllegalPawnMove() {
        assertFalse(whitePawn.isLegalMove(0, 2, 0, 1, board));
        assertFalse(blackPawn.isLegalMove(0, 5, 0, 6, board));
        assertFalse(whitePawn.isLegalMove(0, 2, 1, 2, board));
        assertFalse(blackPawn.isLegalMove(0, 5, 1, 5, board));
    }

    @Test  
    @DisplayName("ejpitera: pawns capturing diagonally")
    public void testPawnCapture() {
        assertTrue(whitePawn.isLegalMove(0, 5, 1, 6, board));
        assertTrue(blackPawn.isLegalMove(0, 2, 1, 1, board));
    }

    @Test
    @DisplayName("ejpitera: illegal pawn capture")
    public void testIllegalPawnCapture() {
        Tile tile = board.getTileAt(1, 2);
        tile.setPiece(new Pawn("White"));
        assertFalse(whitePawn.isLegalMove(0, 1, 1, 2, board));

        tile = board.getTileAt(1, 5);
        tile.setPiece(new Pawn("Black"));
        assertFalse(blackPawn.isLegalMove(0, 6, 1, 5, board));
    }

    @Test
    @DisplayName("ejpitera: illegal inital double move")
    public void testIllegalDoubleMove() {
        board.getTileAt(0, 2).setPiece(new Pawn("White"));
        assertFalse(whitePawn.isLegalMove(0, 1, 0, 3, board));
    }
}
