    
package com.tco.requests;

import com.tco.misc.*;
import com.tco.misc.BadRequestException;
import java.sql.*;
import java.util.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenMatchRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(CreateUserRequest.class);
    private Integer userID;
    private Integer matchID;
    private String match;
    private boolean response;

    //protected String requestType;

    public OpenMatchRequest() {
        super.requestType = "openmatch";
    }

    public OpenMatchRequest(Integer userID) {
        super.requestType = "openmatch";
        this.userID = userID;

    }

    private String serializeMatch(Match match) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(match);
        } catch (Exception e) {
            e.printStackTrace();
            return "[]"; // Return an empty JSON array in case of error
        }
    }

    public String getRequestType() {
        return requestType;
    }

    // Need to update to matches instead of invitations
    @Override
    public void buildResponse() throws BadRequestException {
        // backend logic
        System.out.println("Fetching Matches: \nUserID: " + this.userID);
        try{
            MatchHandling mh = new MatchHandling();
            Match m = mh.getMatch(userID.intValue(), matchID.intValue());

            this.match = serializeMatch(m);
            System.out.println("Match: " + this.match);
            this.response = true;
        } catch (Exception e) {
            e.printStackTrace();
            this.response = false;
        }
        
        log.trace("buildResponse -> {}", this);
    }

}


