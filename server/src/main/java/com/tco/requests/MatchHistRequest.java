
package com.tco.requests;

import com.tco.misc.*;
import com.tco.misc.BadRequestException;
import java.sql.*;
import java.util.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatchHistRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(CreateUserRequest.class);
    private Integer userID;
    private String matches;
    private boolean response;

    //protected String requestType;

    public MatchHistRequest() {
        super.requestType = "matchhist";
    }

    public MatchHistRequest(Integer userID) {
        super.requestType = "matchhist";
        this.userID = userID;

    }

    private String serializeMatches(List<Match> matches) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(matches);
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
            MatchDatabase db = new MatchDatabase();
            List<Match> matchesL = db.getAllMatches(userID.intValue());

            this.matches = serializeMatches(matchesL);
            System.out.println("Matches: " + this.matches);
            this.response = true;
        } catch (Exception e) {
            e.printStackTrace();
            this.response = false;
        }
        
        log.trace("buildResponse -> {}", this);
    }

}

