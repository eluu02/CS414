package com.tco.requests;

import com.tco.misc.BadRequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(TestRequest.class);
    private String str;
    //protected String requestType;

    public TestRequest() {
        super.requestType = "test";
    }

    public TestRequest(String s) {
        super.requestType = "test";
        this.str = s;
        
    }

    public String getRequestType() {
        return requestType;
    }

    // Overrideable Methods
    @Override
    public void buildResponse() throws BadRequestException {
        // backend logic
        System.out.println(this.str);
        this.str = getResponseMessage();
        log.trace("buildResponse -> {}", this);
    }

    public String getResponseMessage() {
        return "Response Test Msg";
    }

}
