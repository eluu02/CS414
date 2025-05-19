package com.tco.misc;

import java.util.List;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestMatchHandling {

    private MatchHandling matchHandling;

    @BeforeEach
    public void setUp() {
        matchHandling = new MatchHandling();
    }

    @Test
    @DisplayName("lavelle3: Creates a match with proper parameters.")
    public void testCreateMatch() throws Exception {
        int ownerID = 1;
        int playerID = 2;
        
        int matchID = matchHandling.createMatch(ownerID, playerID);
    
        assertNotEquals(0, matchID, "should have non-zero match id");

        String gameBoard = matchHandling.getGameBoard(matchID);
        assertNotNull(gameBoard, "should not be null");
    }

    @Test
    @DisplayName("lavelle3: Creates a match with improper parameters")
    public void testCreateMatchWithBadParameters() throws Exception {
        
        Integer ownerID = null;
        Integer playerID = null;

        Exception exception = assertThrows(Exception.class, () -> {
            matchHandling.createMatch(ownerID, playerID);
        });
    
        
        assertNotNull(exception, "should show an exception has been thrown");
    }

    @Test
    @DisplayName("lavelle3: tests update gameboard with proper board")
    public void testUpdateGameBoard() throws Exception {
        int ownerID = 1;
        int playerID = 2;
        int matchID = matchHandling.createMatch(ownerID, playerID);

        String newGameBoard = "updatedBoard";
        String prevMove = "A2";
        int playerTurn = 2;
        boolean gameFinished = false;

        matchHandling.updateGameBoard(newGameBoard, prevMove, playerTurn, matchID, gameFinished);

        String updatedGameBoard = matchHandling.getGameBoard(matchID);
        assertEquals(newGameBoard, updatedGameBoard, "game board should be updated");
    }

    @Test
    @DisplayName("lavelle3: Test update gameboard with invalid parameters")
    public void testUpdateGameBoardWithInvalidParameters() throws Exception {
        int ownerID = 1;
        int playerID = 2;
        int matchID = matchHandling.createMatch(ownerID, playerID);
    
        String newGameBoard = null;  
        String prevMove = null;      
        int playerTurn = -1;         
        boolean gameFinished = false;
    
        
        Exception exception = assertThrows(Exception.class, () -> {
            matchHandling.updateGameBoard(newGameBoard, prevMove, playerTurn, matchID, gameFinished);
        });
    
        
        assertNotNull(exception, "exception should be thrown");
       
    }

    @Test
    @DisplayName("lavelle3: test get game board with a proper board")
    public void testGetGameBoard() throws Exception {
        
        int ownerID = 1;
        int playerID = 2;
        
        int matchID = matchHandling.createMatch(ownerID, playerID);
    
        String retrievedGameBoard = matchHandling.getGameBoard(matchID);
    
        assertNotNull(retrievedGameBoard, "should not be null for existing match");
        GameBoard expectedGameBoard = new GameBoard();
        assertEquals(expectedGameBoard.saveBoard(), retrievedGameBoard, "should return original board");
    }

    @Test
    @DisplayName("lavelle3: Test get matches with proper matches")
    public void testGetAllMatches() throws Exception {
        int ownerID = 1;
        int playerID1 = 2;
        int playerID2 = 3;
    
        int matchID1 = matchHandling.createMatch(ownerID, playerID1);
        int matchID2 = matchHandling.createMatch(ownerID, playerID2);
    
        List<Match> matches = matchHandling.getAllMatches(ownerID);
    
        assertNotNull(matches, "match list should not be null");
        assertTrue(matches.size() >= 2, "should contain two matches for owner");
    
        boolean match1Found = matches.stream().anyMatch(match -> match.getMatchID() == matchID1);
        boolean match2Found = matches.stream().anyMatch(match -> match.getMatchID() == matchID2);
    
        assertTrue(match1Found, "first match should show up");
        assertTrue(match2Found, "second match should show up");
    }

    @Test
    @DisplayName("lavelle3: Test getting matches that are valid")
    public void testGetMatch() throws Exception {
        int ownerID = 1;
        int playerID = 2;
        
        int matchID = matchHandling.createMatch(ownerID, playerID);

        Match retrievedMatch = matchHandling.getMatch(ownerID, matchID);
    
        assertNotNull(retrievedMatch, "match should be found");
        assertEquals(matchID, retrievedMatch.getMatchID(), "match should be found");
    
        assertEquals(ownerID, retrievedMatch.getOwnerID(), "ownerID of match should match ownerID");
        assertEquals(playerID, retrievedMatch.getPlayerID(), "playerID should match playerID");
    }

    @Test
    @DisplayName("lavelle3: Test get match with a bad match")
    public void testGetMatchWithInvalidMatchID() throws Exception {
        int ownerID = 1;
        int playerID = 2;
        
        int validMatchID = matchHandling.createMatch(ownerID, playerID);
        
        int invalidMatchID = -1;
        Match retrievedMatch = matchHandling.getMatch(ownerID, invalidMatchID);
    
        assertNull(retrievedMatch, "match should be null");
    
        int invalidOwnerID = 999;
        retrievedMatch = matchHandling.getMatch(invalidOwnerID, validMatchID);
        
    
        assertNull(retrievedMatch, "match should be null");
    
        int invalidPlayerID = 999; 
        retrievedMatch = matchHandling.getMatch(ownerID, validMatchID); 
        matchHandling.updateGameBoard("invalidBoard", "A2", 2, validMatchID, false);
        
        assertNotNull(retrievedMatch, "match should still be found");
    }

    //test here for getFinishedMatches
    //test here for getCurrentMatches

    @Test
    @DisplayName("lavelle3: test verify match with valid matches")
    public void testVerifyMatch() throws Exception {
        int ownerID = 1;
        int playerID1 = 2;
        int playerID2 = 3;
    
        int matchID1 = matchHandling.createMatch(ownerID, playerID1);
        int matchID2 = matchHandling.createMatch(ownerID, playerID2);
    
        boolean isMatch1Valid = matchHandling.verifyMatch(ownerID, matchID1);
        boolean isMatch2Valid = matchHandling.verifyMatch(ownerID, matchID2);
    
        assertTrue(isMatch1Valid, "matchid1 should be found");
        assertTrue(isMatch2Valid, "matchid2 should be found");
    
        int nonExistentMatchID = 999;
        boolean isNonExistentMatchValid = matchHandling.verifyMatch(ownerID, nonExistentMatchID);
    
        assertFalse(isNonExistentMatchValid, "non existent match should not be found");
    }

    
    
}
