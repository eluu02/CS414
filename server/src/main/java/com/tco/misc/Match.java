package com.tco.misc;

import java.util.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Match {
    private int matchID;
    private int ownerID;
    private int playerID;
    private int playerTurn;
    private String gameboard;
    private String prevMove;
    private boolean gameFinished;
    private String matchScore;
    private String endCondition;
    private String startDateTime;
    private String endDateTime;

    //Constructor for creating match
    public Match(int matchID, int ownerID, int playerID, String gameboard, boolean gameFinished, int playerTurn) {
        this.matchID = matchID;
        this.ownerID = ownerID;
        this.playerID = playerID;
        this.gameboard = gameboard;
        this.gameFinished = gameFinished;
        this.playerTurn = playerTurn;

        this.startDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    //Constructor for finished match
    public Match(int matchID, int ownerID, int playerID, int playerTurn, String gameboard, String prevMove, boolean gameFinished, 
                String matchScore, String endCondition, String startDateTime, String endDateTime) {
                    this.matchID = matchID;
                    this.ownerID = ownerID;
                    this.playerID = playerID;
                    this.playerTurn = playerTurn;
                    this.gameboard = gameboard;
                    this.prevMove = prevMove;
                    this.gameFinished = gameFinished;
                    this.matchScore = matchScore;
                    this.endCondition = endCondition;
                    this.startDateTime = startDateTime;
                    this.endDateTime = endDateTime;
                }
    
    public void nextTurn() {
        if (playerTurn == 1) {
            playerTurn = 2;
        } else {
            playerTurn = 1;
        }
    }
    
    public void setPlayerControl(int playerTurn) {
        this.playerTurn = playerTurn;
    }
    
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void setGameboard(String gameboard) {
        this.gameboard = gameboard;
    }

    public void setPrevMove(String prevMove) {
        this.prevMove = prevMove;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public void setMatchScore(String matchScore) {
        this.matchScore = matchScore;
    }

    public void setEndCondition(String endCondition) {
        this.endCondition = endCondition;
    }

    public void setEndTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getMatchID() {
        return this.matchID;
    }

    public int getOwnerID() {
        return this.ownerID;
    }

    public int getPlayerID() {
        return this.playerID;
    }

    public String getGameboard() {
        return this.gameboard;
    }

    public String getPrevMove() {
        return this.prevMove;
    }

    public String getMatchScore() {
        return this.matchScore;
    }

    public String getEndCondition() {
        return this.endCondition;
    }

    public String getStartTime() {
        return this.startDateTime;
    }

    public String getEndTime() {
        return this.endDateTime;
    }

    public int getPlayerTurn() {
        return this.playerTurn;
    }

    public boolean getGameFinished() {
        return this.gameFinished;
    }

    public boolean isTurn(int turn) {
        return playerTurn == turn;
    }
}

