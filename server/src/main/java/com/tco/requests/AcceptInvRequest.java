package com.tco.requests;

import com.tco.misc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcceptInvRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(AcceptInvRequest.class);
    private Integer userID;
    private Integer inviteID;
    private Integer matchID;
    private boolean success;
    private String response;

    public AcceptInvRequest() {
        super.requestType = "acceptinv";
    }

    public AcceptInvRequest(Integer userID, Integer inviteID) {
        super.requestType = "acceptinv";
        this.userID = userID;
        this.inviteID = inviteID;
    }

    public String getRequestType() {
        return super.requestType;
    }

    @Override
    public void buildResponse() throws BadRequestException {

        // Placeholder game board
        System.out.println("Accepting invitation: " + this.inviteID);
        this.response = "Testing";
        this.matchID = 0;
        
        try{
            // Blank IH since we are just getting ownerID
            InvitationHandling ih = new InvitationHandling("","");
            String ownerID = ih.acceptInvite(Integer.toString(this.inviteID));
            // delete invite
            MatchHandling mh = new MatchHandling();
            this.matchID = mh.createMatch(Integer.parseInt(ownerID), userID); 
            this.success = mh.verifyMatch(userID, matchID);           
            if (this.success) {
                this.response = "Invitation Accepted, Match Created.";
            } else {
                this.response = "ERROR: Accepting Invitation or Creating Match";
            }
            System.out.println(this.response);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
             
        
        log.trace("buildResponse -> {}", this); 
    }
/*
    public String getResponseMessage() {
        return "Response Test Msg";
    }
     */
}
