    

package com.tco.requests;

import com.tco.misc.*;
import com.tco.misc.BadRequestException;
import java.sql.*;
import java.util.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoveRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(CreateUserRequest.class);
    private Integer userID;
    private Integer matchID;
    private String move;
    private boolean isValid;
    private String response;

    //protected String requestType;

    public MoveRequest() {
        super.requestType = "movepiece";
    }

    public MoveRequest(Integer userID, Integer matchID, String move) {
        super.requestType = "movepiece";
        this.userID = userID;
        this.matchID = matchID;
        this.move = move;
    }

    public String getRequestType() {
        return requestType;
    }

    // Overrideable Methods
    @Override
    public void buildResponse() throws BadRequestException {
        // backend logic
        
        // get match from db
        this.isValid = false;
        this.response = "";
        System.out.println("Checking Move: \nMatchID: " + this.matchID);
        try{
            MatchHandling mh = new MatchHandling();
            Match m = mh.getMatch(userID.intValue(), matchID.intValue());
            this.isValid = false;
            // is turn
            if(m.getGameFinished()){
                this.response = "Game already finished.";
            }
            else if((((m.getOwnerID() == this.userID) && (m.getPlayerTurn() == 1))) || (((m.getPlayerID() == this.userID) && (m.getPlayerTurn() == 2)))) {
                // check if the piece color is valid for the given player
                GameBoard gb = new GameBoard(m.getGameboard());
                this.isValid = gb.movePiece(Character.getNumericValue(this.move.charAt(0)), Character.getNumericValue(this.move.charAt(1)), Character.getNumericValue(this.move.charAt(2)), Character.getNumericValue(this.move.charAt(3)));
                System.out.println(this.isValid);
                if(isValid){
                    boolean gameFinished = false;
                    // Check for Check
                        // If Checkmate gameFinished = true
                        // If check prevMove = move + "+"
                            // Does movePiece ensure moves where check is true?
                    
                    int nextTurn = 1;
                    if(m.getPlayerTurn() == 1) nextTurn = 2;
                    mh.updateGameBoard(gb.saveBoard(), this.move, nextTurn, this.matchID, gameFinished);
                    this.response = "Piece Moved";
                    this.isValid = true;
                }






            } else {
                this.response = "Waiting for Opponents Turn.";
            }


        } catch (Exception e) {
            e.printStackTrace();
            this.response = "ERROR";
        }
        // populate gameboard object with data from Match

        // validate turn etc

        // give gameboard move to be validated

            // if move is valid
                // get new gameboard string with saveBoard()
                // checkState() 1 check 2 checkmate 
                    // if check prevMove "XXX" + "C"
                // save updated board to db
            // else
                // response invalid
        
        log.trace("buildResponse -> {}", this);
    }

}


