package com.tco.misc;

import java.util.*;

public class Invitation {
    private int ownerID;
    private int inviteID;
    private List<String> invitedPlayerIDs; 

    public Invitation(int ownerID, int inviteID, List<String> invitedPlayerIDs) {
        this.ownerID = ownerID;
        this.inviteID = inviteID;
        this.invitedPlayerIDs = invitedPlayerIDs;
    }

    public int getInviteID(){
        return this.inviteID;
    }
    public int getOwnerID(){
        return this.ownerID;
    }

    public List<String> getInvitedPlayersID(){
        return this.invitedPlayerIDs;
    }
}