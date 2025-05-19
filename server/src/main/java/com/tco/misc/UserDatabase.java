package com.tco.misc;

import java.util.*;
import java.sql.*;
import java.util.UUID;

public class UserDatabase {
    
    Connection con = null;

    public UserDatabase() {
        connectDB();
    }
    
    private void connectDB() {
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

    public int validateCredentials(String username, String password, boolean createAcct) {
        try {
            if (userExists(username)) {
                System.out.println("test1 In");
                return validatePassword(username, password);
            } else if (createAcct){
                int createdUserID = createUser(username, password);
                return createdUserID;
            }
            System.out.println("Test2 In");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private boolean userExists(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM Users WHERE userName = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }
    
    private int validatePassword(String username, String password) throws SQLException {
        String query = "SELECT userID, userPassword FROM Users WHERE userName = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String userPassword = rs.getString("userPassword");
            if (userPassword.equals(password)) {
                return rs.getInt("userID");
            }
        }
        return -1;
    }

    private int createUser(String username, String password) throws SQLException {
        String query = "INSERT INTO Users (userID, userName, userPassword) VALUES (?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(query);
        int userID = generateUserID();
        System.out.println("Test: " + userID); 
        pstmt.setInt(1, userID);
        pstmt.setString(2, username);
        pstmt.setString(3, password);
        pstmt.executeUpdate();
        return userID;
    }

    private int generateUserID() throws SQLException {
        int userID = 1; // Start from 1
        String query = "SELECT COUNT(*) FROM Users WHERE userID = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        ResultSet rs;
    
        while (true) {
            pstmt.setInt(1, userID);
            rs = pstmt.executeQuery();
            rs.next();
    
            if (rs.getInt(1) == 0) { // No user with this ID
                break;
            }
    
            userID++; // Increment and try the next number
        }
    
        return userID;
    }
    
}