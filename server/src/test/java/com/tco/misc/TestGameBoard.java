package com.tco.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;



public class TestGameBoard {
    private GameBoard gameBoard;
    private GameBoard loadedBoard;

    @BeforeEach
    public void setup() {
        gameBoard = new GameBoard();
        loadedBoard = new GameBoard("RW00,NW10,BW20,QW30,KW40,BW50,NW60,RW70,PW01,PW11,PW21,PW31,PW41,PW51,PW61,PW71,PB06,PB16,PB26,PB36,PB46,PB56,PB66,PB76,RB07,NB17,BB27,QB37,KB47,BB57,NB67,RB77,");
    }

    @Test
    @DisplayName("GameBoard should be initialized with 64 tiles")
    public void testBoardInitialization() {
        assertEquals(64, gameBoard.getBoard().size(), "Board should contain 64 tiles");
        assertEquals(64, loadedBoard.getBoard().size(), "Board should contain 64 tiles");
    }

    @Test
    @DisplayName("Each tile should have a team assigned correctly based on its row")
    public void testTileTeamAssignment() {
        ArrayList<Tile> board = gameBoard.getBoard();
        ArrayList<Tile> board2 = loadedBoard.getBoard();

        for (Tile tile : board) {
            int row = tile.getY();
            if (row <= 1) {
                assertEquals("White", tile.getTeam(), "Rows 1-2 should be assigned to team white");
            } else if (row >= 6) {
                assertEquals("Black", tile.getTeam(), "Rows 7-8 should be assigned to team black");
            }
        }

        for (Tile tile : board2) {
            int row = tile.getY();
            if (row <= 1) {
                assertEquals("White", tile.getTeam(), "Rows 1-2 should be assigned to team white");
            } else if (row >= 6) {
                assertEquals("Black", tile.getTeam(), "Rows 7-8 should be assigned to team black");
            }
        }
    }

    @Test
    @DisplayName("Each tile should have a ChessPiece object initialized")
    public void testPieceInitialization() {
        ArrayList<Tile> board = gameBoard.getBoard();
        ArrayList<Tile> board2 = loadedBoard.getBoard();

        for (Tile tile : board) {
            int row = tile.getY();
            int column = tile.getX();
            if((row == 0) || (row == 1) || (row == 6) || (row == 7)) {
                assertNotNull(tile.getPiece(), "Each tile should have a ChessPiece assigned");

            } else {
                assertNull(tile.getPiece(), "Each tile should be empty at the start");
            }
        }

        for (Tile tile : board2) {
            int row = tile.getY();
            int column = tile.getX();
            if((row == 0) || (row == 1) || (row == 6) || (row == 7)) {
                assertNotNull(tile.getPiece(), "Each tile should have a ChessPiece assigned");

            } else {
                assertNull(tile.getPiece(), "Each tile should be empty at the start");
            }
        }
    }

    @Test
    @DisplayName("ejpitera: getTileAt method")
    public void testGetTileAt() {
        ArrayList<Tile> board = gameBoard.getBoard();

        assertTrue(gameBoard.getTileAt(0, 0).getPiece().name == "Rook"); 
        assertTrue(gameBoard.getTileAt(1, 0).getPiece().name == "Knight");
        assertTrue(gameBoard.getTileAt(2, 0).getPiece().name == "Bishop");
        assertTrue(gameBoard.getTileAt(3, 0).getPiece().name == "Queen");
        assertTrue(gameBoard.getTileAt(4, 0).getPiece().name == "King");
        assertTrue(gameBoard.getTileAt(0, 1).getPiece().name == "Pawn");
    }
}
