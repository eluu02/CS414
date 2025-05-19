package com.tco.misc;

import java.util.List;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testInvitationHandling {

    private InvitationHandling invitationHandling;
    private InvitationDatabase invitationDB;
    private String ownerID;
    private String invitedPlayerID;
    private String invitationID;

    @BeforeEach
    public void setup() throws Exception {
        
        invitationDB = new InvitationDatabase();
        ownerID = "testOwner";
        invitedPlayerID = "testPlayer";
        invitationHandling = new InvitationHandling(ownerID, invitedPlayerID);
        invitationHandling.createInvite();
        invitationID = invitationHandling.getInvitationID();
    }

    @Test
    @DisplayName("lavelle3: Test invitation creation")
    public void testInvitationCreation() {
        assertNotNull(invitationID, "Invitation ID should not be null");
    }

    @Test
    @DisplayName("lavelle3: Test getting sent invitations")
    public void testGetSentInvitations() throws Exception {
        List<Invitation> sentInvitations = invitationDB.getSentInvitations(Integer.parseInt(ownerID));
        assertFalse(sentInvitations.isEmpty(), "Sent invitations should not be empty");
    }

    @Test
    @DisplayName("lavelle3: Test accepting an invitation")
    public void testAcceptInvitation() throws Exception {
        String owner = invitationDB.acceptedInvite(invitationID);
        assertEquals(ownerID, owner, "Owner ID should match the one who created the invitation");
    }

    @Test
    @DisplayName("lavelle3: Test canceling an invitation")
    public void testCancelInvitation() throws Exception {
        invitationDB.cancelInviteDB(invitationID);
        assertThrows(SQLException.class, () -> invitationDB.getSentInvitations(Integer.parseInt(ownerID)));
    }
    
}
