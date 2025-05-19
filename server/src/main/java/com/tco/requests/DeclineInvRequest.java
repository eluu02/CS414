    
package com.tco.requests;

import com.tco.misc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeclineInvRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(AcceptInvRequest.class);
    private Integer userID;
    private Integer inviteID;
    private boolean success;
    private String response;

    public DeclineInvRequest() {
        super.requestType = "declineinv";
    }

    public DeclineInvRequest(Integer userID, Integer inviteID) {
        super.requestType = "declineinv";
        this.userID = userID;
        this.inviteID = inviteID;
    }

    public String getRequestType() {
        return super.requestType;
    }

    @Override
    public void buildResponse() throws BadRequestException {

        // Placeholder game board
        this.success = false;
        try{
            // Blank IH since we are just getting ownerID
            InvitationHandling ih = new InvitationHandling(Integer.toString(this.userID), "");
            ih.declineInvite(Integer.toString(this.inviteID), this.userID);
            this.success = ih.verifyDecline(this.inviteID);
            if (this.success) {
                System.out.println("Invitation Declined");
                this.response = "Invitation Declined";
            } else {
                System.out.println("ERROR: Declining Invitation");
                this.response = "ERROR: Declining Invitation";
            }
            
            
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
