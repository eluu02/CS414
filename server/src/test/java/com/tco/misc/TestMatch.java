package com.tco.misc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestMatch {
    private Match match;

    @BeforeEach
    @DisplayName("eluu02: Initialize Match and players")
    public void setUp() {
        int gameID = 1;
        int ownerID = 100;
        int playerID = 200;
        int[] players = {1, 2}; // Sample player IDs for two players
        //match = new Match(gameID, ownerID, "temp-board", false, "temp-date-time");
        match = new Match(gameID, ownerID, playerID, "temp_board", false, 1);
        //match.setPlayerID(playerID);
    }

    @Test
    @DisplayName("lavelle3: Test for correct returns when players should have the turn.")
    public void testIsTurn() {
        match.setPlayerControl(1);
        // Player 1 has turn initially
        assertTrue(match.isTurn(1), "Player 1 should have the turn");
        
        // Advance to the next turn (Player 2's turn)
        match.nextTurn();
        assertTrue(match.isTurn(2), "Player 2 should have the turn");
        
        // Advance back to Player 1's turn
        match.nextTurn();
        assertTrue(match.isTurn(1), "Player 1 should have the turn again");
    }

    @Test
    @DisplayName("lavelle3: Test for correct returns when players should not have the turn.")
    public void testNotIsTurn() {
        match.setPlayerControl(1);
        // Initially, Player 1 has the turn
        assertTrue(match.isTurn(1), "Player 1 should have the turn");

        // Player 2 should not have the turn initially
        assertFalse(match.isTurn(2), "Player 2 should not have the turn");
        
        // Advance to the next turn (Player 2's turn)
        match.nextTurn();
        
        // Now, Player 2 should have the turn
        assertTrue(match.isTurn(2), "Player 2 should have the turn");
        assertFalse(match.isTurn(1), "Player 1 should not have the turn after Player 2's turn");

        // Advance back to Player 1's turn
        match.nextTurn();
        assertTrue(match.isTurn(1), "Player 1 should have the turn again");
        assertFalse(match.isTurn(2), "Player 2 should not have the turn after Player 1's turn");
    }

    @Test
    @DisplayName("lavelle3: Test setting player control to a valid player.")
    public void testSetPlayerControlValid() {
        // Set control to Player 2
        match.setPlayerControl(2);
        assertTrue(match.isTurn(2), "Player 2 should have the turn after setting control to them.");
    
        // Set control back to Player 1
        match.setPlayerControl(1);
        assertTrue(match.isTurn(1), "Player 1 should have the turn after setting control to them.");
    }

    @Test
    @DisplayName("lavelle3: Test setting player control multiple times.")
    public void testSetPlayerControlMultiple() {
        // Set control to Player 1
        match.setPlayerControl(1);
        assertTrue(match.isTurn(1), "Player 1 should have the turn.");
    
        // Set control to Player 2
        match.setPlayerControl(2);
        assertTrue(match.isTurn(2), "Player 2 should have the turn.");
    
        // Set control back to Player 1
        match.setPlayerControl(1);
        assertTrue(match.isTurn(1), "Player 1 should have the turn again.");
    }
    
}