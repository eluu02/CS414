package com.tco.misc;

import java.util.*;
import java.util.UUID;

public class MatchHandling {

    MatchDatabase mb;
    
    public MatchHandling() {
        mb = new MatchDatabase();
    }

    // public Match fetchMatch(matchID)
        // gets match from DB and returns Match Datatype
        // return match

    public int createMatch(int ownerID, int playerID) throws Exception {
        try {

            
            
            // create default gameboard
            // saveGame
            
            /*
            * CREATE TABLE IF NOT EXISTS Matches ( 
                matchID         INT             NOT NULL,
                ownerID         INT             NOT NULL,
                playerID        INT, 
                playerTurn      INT, 1
                gameBoard       VARCHAR(8000)    NOT NULL, new gb
                prevMove        VARCHAR(10), 
                gameFinished    BOOLEAN         NOT NULL,
                matchScore      VARCHAR(50), 
                endCondition    VARCHAR(50), 
                startDateTime   DATETIME        NOT NULL,
                endDateTime     DATETIME,
                PRIMARY KEY(matchID),
                );
            */
                
            
            GameBoard gb = new GameBoard();
            String gameboard = gb.saveBoard();
            int matchID = mb.generateMatchID();
            int playerTurn = 1;
            boolean gameFinished = false;
            Match match = new Match(matchID, ownerID, playerID, gameboard, gameFinished, playerTurn);

            mb.createMatch(match);

            return matchID;
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateGameBoard(String gameboard, String prevMove, int playerTurn, int matchID, boolean gameFinished) throws Exception {
        try {
            mb.updateGameBoard(gameboard, prevMove, playerTurn, matchID, gameFinished);    
        } catch (Exception e) {
            throw e;
        }
    }

    public void endMatch(String gameboard, String prevMove, int playerTurn, int matchID, boolean gameFinished, String endCondition, String endDateTime) throws Exception {
        try {
            mb.endMatchDB(gameboard, prevMove, playerTurn, matchID, gameFinished, endCondition, endDateTime);
        } catch (Exception e) {
            throw e;
        }
    }

    public String getGameBoard(int matchID) throws Exception {
        try {
            return mb.getGameBoard(matchID);
        } catch (Exception e) {
            throw e;
        }
    }   

    public List<Match> getAllMatches(int userID) throws Exception { 
        try {
            return mb.getAllMatches(userID);
        } catch (Exception e) {
            throw e;
        }
    }

    public Match getMatch(int userID, int matchID) throws Exception { 
        try {
            List<Match> list = mb.getAllMatches(userID);
            System.out.println("Fetching Match: " + matchID);
            for (Match match : list) {
                System.out.println(match.getMatchID());
                if (match.getMatchID() == matchID) {
                    return match; // Match found
                }
            }
            return null; // No match found
        } catch (Exception e) {
            throw e; // Rethrow the exception
        }
    }
    

    public List<Match> getFinishedMathes(int userID) throws Exception {
        try {
            return mb.getFinishedMatches(userID);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Match> getCurrentMatches(int userID) throws Exception {
        try {
            return mb.getCurrentMatches(userID);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean verifyMatch(int userID, int matchID) throws Exception {
        try {
            List<Match> list = mb.getAllMatches(userID);
            System.out.println("Verifying Matches: ");
            for (Match match : list) {
                System.out.println(match.getMatchID());
                if (match.getMatchID() == matchID) {
                    return true; // Match found
                }
            }
            return false; // No match found
        } catch (Exception e) {
            throw e; // Rethrow the exception
        }
    }

    
}
