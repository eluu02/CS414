package com.tco.misc;

import java.util.*;
import java.sql.*;

public class MatchDatabase {
    
    Connection con = null;

    public MatchDatabase() {
        connectDB();
    }

    public void connectDB() {
        try  {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // String url = "jdbc:mysql://faure.cs.colostate.edu:43100/cs414_team14";
            String url = "jdbc:mariadb://faure.cs.colostate.edu:3306/cs414_team14";

            con = DriverManager.getConnection(url, "cs414_team14", "VOyTLnO8ac");
            System.out.println("--- Connected to database ---");
            System.out.println("URL: " + url);
            System.out.println("Connection: " + con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Match> getAllMatches(int userID) throws SQLException {
        List<Match> matches = new ArrayList<>();
        String query = "SELECT * FROM Matches WHERE ownerID = ? OR playerID = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, userID);
        pstmt.setInt(2, userID);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            matches.add(createMatchFromResultSet(rs));
        }
        return matches;
    }

    public List<Match> getFinishedMatches(int userID) throws SQLException {
        List<Match> matches = new ArrayList<>();
        String query = "SELECT * FROM Matches WHERE (ownerID = ? OR playerID = ?) AND gameFinished = true";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, userID);
        pstmt.setInt(2, userID);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            matches.add(createMatchFromResultSet(rs));
        }
        return matches;
    }

    public List<Match> getCurrentMatches(int userID) throws SQLException {
        List<Match> matches = new ArrayList<>();
        String query = "SELECT * FROM Matches WHERE (ownerID = ? OR playerID = ?) AND gameFinished = false";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, userID);
        pstmt.setInt(2, userID);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            matches.add(createMatchFromResultSet(rs));
        }
        return matches;
    }
/*
    public void createMatch(int ownerID, int matchID) throws SQLException {
        String query = "INSERT INTO Matches (matchID, ownerID, gameBoard, gameFinished, startDateTime) " +
                        "VALUES (?, ?, ?, NOW())";

        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, matchID);
        pstmt.setInt(2, ownerID);
        pstmt.setString(3, "");
        pstmt.setBoolean(5, false);
    }
*/
public void createMatch(Match match) throws SQLException {
    String query = "INSERT INTO Matches (matchID, ownerID, playerID, playerTurn, gameBoard, prevMove, gameFinished, " +
                   "matchScore, endCondition, startDateTime, endDateTime) " +
                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // Prepare the statement
    PreparedStatement pstmt = con.prepareStatement(query);

    pstmt.setInt(1, match.getMatchID());                       // Set matchID
    pstmt.setInt(2, match.getOwnerID());                      // Set ownerID
    pstmt.setInt(3, match.getPlayerID());                     // Set playerID
    pstmt.setInt(4, match.getPlayerTurn());                        // Set playerTurn
    pstmt.setString(5, match.getGameboard());                 // Set gameBoard
    pstmt.setString(6, match.getPrevMove());                  // Set prevMove
    pstmt.setBoolean(7, match.getGameFinished());              // Set gameFinished
    pstmt.setString(8, match.getMatchScore());                // Set matchScore
    pstmt.setString(9, match.getEndCondition());              // Set endCondition
    pstmt.setString(10, match.getStartTime());                // Set startDateTime
    pstmt.setString(11, match.getStartTime());                  // Set endDateTime

    // Execute the statement
    pstmt.executeUpdate();
}

/*
    public void updateGameBoard(String boardStr, String prevMove, int playerTurn, int matchID, boolean gameFinished) throws SQLException {
        String query = "UPDATE Matches SET gameBoard = ?, gameFinished = ? WHERE matchID = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, boardStr);            
        pstmt.setBoolean(2, gameFinished);         
        pstmt.setInt(3, matchID);
    }
*/
    public void updateGameBoard(String boardStr, String prevMove, int playerTurn, int matchID, boolean gameFinished) throws SQLException {
        String query = "UPDATE Matches SET gameBoard = ?, prevMove = ?, playerTurn = ?, gameFinished = ? WHERE matchID = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, boardStr);           // Update gameBoard
        pstmt.setString(2, prevMove);           // Update prevMove
        pstmt.setInt(3, playerTurn);            // Update playerTurn
        pstmt.setBoolean(4, gameFinished);      // Update gameFinished
        pstmt.setInt(5, matchID);               // Specify the matchID to update

        // Execute the update
        pstmt.executeUpdate();
    }
    public String getGameBoard(int matchID) throws SQLException {
        String query = "SELECT gameBoard FROM Matches WHERE matchID = ?";
        String gameBoard = null;

        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, matchID);
        
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            gameBoard = rs.getString("gameboard");
        }
        return gameBoard;
    }

    private Match createMatchFromResultSet(ResultSet rs) throws SQLException {
        return new Match(
            rs.getInt("matchID"),
            rs.getInt("ownerID"),
            rs.getInt("playerID"),
            rs.getInt("playerTurn"),
            rs.getString("gameBoard"),
            rs.getString("prevMove"),
            rs.getBoolean("gameFinished"),
            rs.getString("matchScore"),
            rs.getString("endCondition"),
            rs.getTimestamp("startDateTime") != null ? rs.getTimestamp("startDateTime").toString() : null,
            rs.getTimestamp("endDateTime") != null ? rs.getTimestamp("endDateTime").toString() : null
        );
    }

    public int generateMatchID() throws SQLException {
        int matchID = 1; // Start from 1
        String query = "SELECT COUNT(*) FROM Matches WHERE matchID = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        ResultSet rs;
    
        while (true) {
            pstmt.setInt(1, matchID);
            rs = pstmt.executeQuery();
            rs.next();
    
            if (rs.getInt(1) == 0) { // No user with this ID
                break;
            }
    
            matchID++; // Increment and try the next number
        }
    
        return matchID;
    }

    public void endMatchDB(String gameboard, String prevMove, int playerTurn, int matchID, boolean gameFinished, String endCondition, String endDateTime) throws SQLException{
        String query = "UPDATE Matches SET gameBoard = ?, prevMove = ?, playerTurn = ?, gameFinished = ?, endCondition = ?, endDateTime = ? WHERE matchID = ?";
    
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, gameboard);            
        pstmt.setString(2, prevMove);             
        pstmt.setInt(3, playerTurn);              
        pstmt.setBoolean(4, gameFinished);   
        pstmt.setString(5, endCondition);         
        pstmt.setString(6, endDateTime);          
        pstmt.setInt(7, matchID);                 
        pstmt.executeUpdate();
    }
}
