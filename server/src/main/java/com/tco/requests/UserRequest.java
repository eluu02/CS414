package com.tco.requests;

import com.tco.misc.*;
import com.tco.misc.BadRequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(UserRequest.class);
    private String username;
    private String password;
    private Integer userID;

    //protected String requestType;

    public UserRequest() {
        super.requestType = "user";
    }

    public UserRequest(String username, String password) {
        super.requestType = "user";
        this.username = username;
        this.password = password;
    }

    public String getRequestType() {
        return requestType;
    }

    // Overrideable Methods
    @Override
    public void buildResponse() throws BadRequestException {
        // backend logic
        UserDatabase db = new UserDatabase();
        
        //commented out for now as my (Ethan L.) implementations are slightly different
        //this.userID = db.validateCredentials(this.username, this.password);

        log.trace("buildResponse -> {}", this);
    }

}
