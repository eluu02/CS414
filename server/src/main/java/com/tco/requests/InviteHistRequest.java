
package com.tco.requests;

import com.tco.misc.*;
import com.tco.misc.BadRequestException;
import java.sql.*;
import java.util.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InviteHistRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(CreateUserRequest.class);
    private Integer userID;
    private String sentInv;
    private String recInv;
    private boolean response;

    //protected String requestType;

    public InviteHistRequest() {
        super.requestType = "invhist";
    }

    public InviteHistRequest(Integer userID) {
        super.requestType = "invhist";
        this.userID = userID;

    }

    private String serializeMatches(List<Invitation> invites) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(invites);
        } catch (Exception e) {
            e.printStackTrace();
            return "[]"; // Return an empty JSON array in case of error
        }
    }

    public String getRequestType() {
        return requestType;
    }

    // Overrideable Methods
    @Override
    public void buildResponse() throws BadRequestException {
        // backend logic
        System.out.println("Fetching Invites: \nUserID: " + this.userID);
        try{
            InvitationDatabase db = new InvitationDatabase();
            List<Invitation> sentInvL = db.getSentInvitations(this.userID);
            List<Invitation> recInvL = db.getReceivedInvitations(this.userID);

            this.sentInv = serializeMatches(sentInvL);
            this.recInv = serializeMatches(recInvL);
            System.out.println("Sent Invites: " + this.sentInv);
            System.out.println("Received Invites: " + this.recInv);
            this.response = true;
        } catch (Exception e) {
            e.printStackTrace();
            this.response = false;
        }
        

        
        /*
        try {

            
            
            int inviteID = db.generateInviteID();
            db.createInviteDB(inviteID, this.userID);
            String[] values = this.opponent.split(",");
            for (String value : values) {
                String trimmedValue = value.trim(); // Remove leading/trailing whitespace
                int opponentID = db.getUserID(value);
                db.sendInviteDB(inviteID, (opponentID + "")); // Call a function with the trimmed value
            }
            if (inviteID >= 0){
                this.response = "ID generated";
            } else {
                this.response = "Unable To Create Invite";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
        
        log.trace("buildResponse -> {}", this);
    }

}

