
package com.tco.requests;

import com.tco.misc.*;
import com.tco.misc.BadRequestException;
import java.sql.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateInvRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(CreateUserRequest.class);
    private Integer userID;
    private String opponent;
    private boolean sucess;
    private String response;

    //protected String requestType;

    public CreateInvRequest() {
        super.requestType = "createuser";
    }

    public CreateInvRequest(Integer userID, String opponent) {
        super.requestType = "createuser";
        this.userID = userID;
        this.opponent = opponent;

    }

    public String getRequestType() {
        return requestType;
    }

    // Overrideable Methods
    @Override
    public void buildResponse() throws BadRequestException {
        // backend logic
        System.out.println("Creating Invite: \nOwner: " + this.userID + "\nOpponent: " + this.opponent );
        try{

            InvitationHandling ih = new InvitationHandling((userID + ""), opponent);
            ih.createInvite();
            ih.sendInvite();
            this.sucess = ih.verifyInvite();
            if (this.sucess) {
                this.response = "ID generated";
            } else {
                this.response = "Unable To Create Invite";
            }
        } catch (Exception e) {
            e.printStackTrace();
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

