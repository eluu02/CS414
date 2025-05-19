package com.tco.misc;

import com.tco.misc.*;
import java.util.*;
import java.sql.*;

public class InvitationDatabase {
    
    Connection con = null;

    public InvitationDatabase() {
        connectDB();
    }

    public void connectDB() {
        try  {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //String url = "jdbc:mysql://faure.cs.colostate.edu:43100/cs414_team14";
            String url = "jdbc:mariadb://faure.cs.colostate.edu:3306/cs414_team14";

            con = DriverManager.getConnection(url, "cs414_team14", "VOyTLnO8ac");
            System.out.println("--- Connected to database ---");
            System.out.println("URL: " + url);
            System.out.println("Connection: " + con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Invitation> getSentInvitations(int ownerID) throws SQLException {
        List<Invitation> sentInvitations = new ArrayList<>();
        String query = "SELECT inviteID, playerIDs FROM Invitations WHERE ownerID = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, ownerID);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int inviteID = rs.getInt("inviteID");
            String playerIDs = rs.getString("playerIDs");
            List<String> players = Arrays.asList(playerIDs.split("\t"));
            Invitation invite = new Invitation(ownerID, inviteID, players);
            sentInvitations.add(invite);
        }
        return sentInvitations;
    }

    public List<Invitation> getReceivedInvitations(int playerID) throws SQLException {
        List<Invitation> receivedInvitations = new ArrayList<>();
        String query = "SELECT inviteID, ownerID, playerIDs FROM Invitations WHERE playerIDs LIKE ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, "%" + playerID + "%");
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int inviteID = rs.getInt("inviteID");
            int ownerID = rs.getInt("ownerID");
            String playerIDs = rs.getString("playerIDs");

            List<String> players = Arrays.asList(playerIDs.split("\t"));
            if (players.contains(String.valueOf(playerID))) {
                Invitation invite = new Invitation(ownerID, inviteID, players);
                receivedInvitations.add(invite);
            }
        }
        return receivedInvitations;
    }

    public void removePlayerInvite(int inviteID, int playerID) throws SQLException {
        String query = "SELECT playerIDs FROM Invitations WHERE inviteID = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, inviteID);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String playerIDs = rs.getString("playerIDs");
            //Split assuming that playerIDs are tab separated, can use any other delimiter we desire
            List<String> players = new ArrayList<>(Arrays.asList(playerIDs.split("\t")));
            players.remove(String.valueOf(playerID));
            
            String updatedPlayerIDs = String.join("\t", players);
            String updateQuery = "UPDATE Invitations SET playerIDs = ? WHERE inviteID = ?";
            pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, updatedPlayerIDs);
            pstmt.setInt(2, inviteID);
            pstmt.executeUpdate();
        }
    }

    public String acceptedInvite(String inviteID) throws SQLException {
        String query = "SELECT ownerID FROM Invitations WHERE inviteID = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, inviteID);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            int ownerID = rs.getInt("ownerID");
            String deleteQuery = "DELETE FROM Invitations WHERE inviteID = ?";
            PreparedStatement deletePstmt = con.prepareStatement(deleteQuery);
            deletePstmt.setString(1, inviteID);
            deletePstmt.executeUpdate();
            return Integer.toString(ownerID);
        } else {
            throw new SQLException("Invitation with inviteID " + inviteID + " not found.");
        }
    }

    public void createInviteDB(String inviteID, String ownerID) throws SQLException {
        String query = "INSERT INTO Invitations (inviteID, ownerID) VALUES (?, ?)";
        PreparedStatement pstmt = con.prepareStatement(query);

        pstmt.setInt(1, Integer.parseInt(inviteID));
        pstmt.setString(2, ownerID);
        pstmt.executeUpdate();
    }

    public void sendInviteDB(String inviteID, String invitedPlayerID) throws SQLException {
        String query = "SELECT playerIDs FROM Invitations WHERE inviteID = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, inviteID);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String playerIDs = rs.getString("playerIDs");
            List<String> players = new ArrayList<>();

            if (playerIDs != null && !playerIDs.isEmpty()) {
                players.addAll(Arrays.asList(playerIDs.split("\t")));
            }

            if (players.contains(invitedPlayerID)) {
                throw new SQLException("Player ID " + invitedPlayerID + " already exists in the invite list for inviteID " + inviteID);
            }
            players.add(invitedPlayerID);

            String updatedPlayerIDs = String.join("\t", players);
    
            String updateQuery = "UPDATE Invitations SET playerIDs = ? WHERE inviteID = ?";
            PreparedStatement updatePstmt = con.prepareStatement(updateQuery);
            updatePstmt.setString(1, updatedPlayerIDs);
            updatePstmt.setString(2, inviteID);
            updatePstmt.executeUpdate();
        } else {
            throw new SQLException("Invitation with inviteID " + inviteID + " not found.");
        }
    }

    public void cancelInviteDB(String inviteID) throws SQLException {
        String checkQuery = "SELECT inviteID FROM Invitations WHERE inviteID = ?";
        PreparedStatement checkPstmt = con.prepareStatement(checkQuery);
        checkPstmt.setString(1, inviteID);
        ResultSet rs = checkPstmt.executeQuery();
    
        if (rs.next()) {
            String deleteQuery = "DELETE FROM Invitations WHERE inviteID = ?";
            PreparedStatement deletePstmt = con.prepareStatement(deleteQuery);
            deletePstmt.setString(1, inviteID);
            deletePstmt.executeUpdate();
        } else {
            throw new SQLException("Invitation with inviteID " + inviteID + " not found.");
        }
    }

    public int getUserID(String username) throws SQLException {
        String query = "SELECT userID FROM Users WHERE userName = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            return rs.getInt("userID");
        } else {
            throw new SQLException("User with username " + username + " not found.");
        }
    }

    public int generateInviteID() throws SQLException {
        int inviteID = 1; // Start from 1
        String query = "SELECT COUNT(*) FROM Invitations WHERE inviteID = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        ResultSet rs;
    
        while (true) {
            pstmt.setInt(1, inviteID);
            rs = pstmt.executeQuery();
            rs.next();
    
            if (rs.getInt(1) == 0) { // No user with this ID
                break;
            }
    
            inviteID++; // Increment and try the next number
        }
    
        return inviteID;
    }

    public void declineInviteDB(String inviteID, int playerID) throws SQLException {
        String selectQuery = "SELECT playerIDs FROM Invitations WHERE inviteID = ?";
        PreparedStatement selectPstmt = con.prepareStatement(selectQuery);
        selectPstmt.setString(1, inviteID);
        ResultSet rs = selectPstmt.executeQuery();

        if (rs.next()) {
            String playerIDs = rs.getString("playerIDs");

            List<String> players = new ArrayList<>(Arrays.asList(playerIDs.split("\t")));
            if (players.contains(String.valueOf(playerID))) {
                players.remove(String.valueOf(playerID));
            } else {
                throw new SQLException("Player ID " + playerID + " not found in inviteID " + inviteID);
            }

            String updatedPlayerIDs = String.join("\t", players);
            String updateQuery = "UPDATE Invitations SET playerIDs = ? WHERE inviteID = ?";
            PreparedStatement updatePstmt = con.prepareStatement(updateQuery);
            updatePstmt.setString(1, updatedPlayerIDs);
            updatePstmt.setString(2, inviteID);
            updatePstmt.executeUpdate();
        } else {
            throw new SQLException("Invitation with inviteID " + inviteID + " not found.");
        }
    }
}
