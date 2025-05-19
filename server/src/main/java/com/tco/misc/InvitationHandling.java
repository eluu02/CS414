package com.tco.misc;

import java.util.UUID;
import java.util.*;
import com.tco.misc.InvitationDatabase;
import java.sql.*;

public class InvitationHandling {
    private String ownerID;
    private int invitedPlayerID;
    private String invitationID;
    private InvitationDatabase invitationDB;

    public InvitationHandling(String ownerID, String invitedPlayerID) throws Exception {
        invitationDB = new InvitationDatabase();
        this.ownerID = ownerID;
        try {
            if(!(invitedPlayerID.equals("") || invitedPlayerID == null)) {
                this.invitedPlayerID = invitationDB.getUserID(invitedPlayerID);

            }
        
        } catch (Exception e) {
            throw e;
        }
    }

    public void createInvite() throws Exception {
        try {
            invitationID = Integer.toString(invitationDB.generateInviteID());
            invitationDB.createInviteDB(invitationID, ownerID);
        } catch (Exception e) {
            throw e;
        }
    }

    public void sendInvite() throws Exception {
        try {
            invitationDB.sendInviteDB(invitationID, Integer.toString(invitedPlayerID));
        } catch (Exception e) {
            throw e;
        }
    }

    public String acceptInvite(String inviteID) throws Exception {
        try {
            String acceptedOwnerID = invitationDB.acceptedInvite(inviteID);
            return acceptedOwnerID;
        } catch (Exception e) {
            throw e;
        }
    }

    public void declineInvite(String inviteID, int playerID) throws Exception {
        try {
            invitationDB.declineInviteDB(inviteID, playerID);
        } catch (Exception e) {
            throw e;
        }
        
    }

    public void cancelInvite() throws Exception {
        try {
            invitationDB.cancelInviteDB(invitationID);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean verifyInvite() throws Exception {
        try {
            List<Invitation> list = invitationDB.getSentInvitations(Integer.parseInt(ownerID));
            System.out.println("Verifying Invite: ");
            for (Invitation invitation : list) {
                System.out.println(invitation.getInviteID());
                if (Integer.toString(invitation.getInviteID()).equals(this.invitationID)) {
                    return true; // Match found
                }
            }
            return false; // No match found
        } catch (Exception e) {
            throw e; // Rethrow the exception
        }
    }

    public boolean verifyDecline(int inviteID) throws Exception {
        try {
            List<Invitation> list = invitationDB.getReceivedInvitations(Integer.parseInt(ownerID));
            System.out.println("Verifying Decline: ");
            for (Invitation invitation : list) {

                System.out.println(invitation.getInviteID() + " == " + inviteID);
                if (invitation.getInviteID() == inviteID) {
                    return false; // Match found
                }
            }
            return true; // No match found
        } catch (Exception e) {
            throw e; // Rethrow the exception
        }
    }

    public String getInvitationID() {
        return invitationID;
    }
}
