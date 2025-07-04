package com.tco.server;

import com.tco.misc.BadRequestException;
import com.tco.misc.JSONValidator;
import com.tco.requests.ConfigRequest;
import com.tco.requests.Request;

import com.tco.requests.TestRequest;
import com.tco.requests.UserRequest;
import com.tco.requests.LoginRequest;
import com.tco.requests.CreateUserRequest;
import com.tco.requests.CreateInvRequest;
import com.tco.requests.AcceptInvRequest;
import com.tco.requests.DeclineInvRequest;
import com.tco.requests.InviteHistRequest;
import com.tco.requests.MatchHistRequest;
import com.tco.requests.OpenMatchRequest;
import com.tco.requests.MoveRequest;



import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import static spark.Spark.*;

public final class MicroServer {

    private static final Logger log = LoggerFactory.getLogger(MicroServer.class);
    private static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_SERVER_ERROR = 500;

    MicroServer(int serverPort) {
        configureServer(serverPort);
        processRestfulAPIrequests();
    }

    /* Configure MicroServices Here. */

    private void processRestfulAPIrequests() {
        path("/api", () -> {
            before("/*", (req, res) -> logRequest(req));
            post("/config", (req, res) -> processHttpRequest(req, res, ConfigRequest.class));
            post("/test", (req, res) -> processHttpRequest(req, res, TestRequest.class));
            post("/user", (req, res) -> processHttpRequest(req, res, UserRequest.class));
            post("/login", (req, res) -> processHttpRequest(req, res, LoginRequest.class));
            post("/createuser", (req, res) -> processHttpRequest(req, res, CreateUserRequest.class));
            post("/createinv", (req, res) -> processHttpRequest(req, res, CreateInvRequest.class));
            post("/acceptinv", (req, res) -> processHttpRequest(req, res, AcceptInvRequest.class));
            post("/declineinv", (req, res) -> processHttpRequest(req, res, DeclineInvRequest.class));
            post("/invhist", (req, res) -> processHttpRequest(req, res, InviteHistRequest.class));
            post("/matchhist", (req, res) -> processHttpRequest(req, res, MatchHistRequest.class));
            post("/openmatch", (req, res) -> processHttpRequest(req, res, OpenMatchRequest.class));
            post("/movepiece", (req, res) -> processHttpRequest(req, res, MoveRequest.class));

        });
    }

    /* You shouldn't need to change what is found below. */

    private String processHttpRequest(spark.Request httpRequest, spark.Response httpResponse, Type requestType) {
        setupResponse(httpResponse);
        String jsonString = httpRequest.body();
        try {
            JSONValidator.validate(jsonString, requestType);
            Request requestObj = new Gson().fromJson(jsonString, requestType);
            return buildJSONResponse(requestObj);
        } catch (IOException | BadRequestException e) {
            log.info("Bad Request - {}", e.getMessage());
            httpResponse.status(HTTP_BAD_REQUEST);
        } catch (Exception e) {
            log.info("Server Error - ", e);
            httpResponse.status(HTTP_SERVER_ERROR);
        }
        return jsonString;
    }

    private void setupResponse(spark.Response response) {
        response.type("application/json");
        response.header("Access-Control-Allow-Origin", "*");
        response.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
        response.status(HTTP_OK);
    }

    private String buildJSONResponse(Request request) throws BadRequestException {
        request.buildResponse();
        String responseBody = new Gson().toJson(request);
        log.trace("Response - {}", responseBody);
        return responseBody;
    }

    private void logRequest(spark.Request request) {
        String message = "Request - "
                + "[" + dateTimeFormat.format(LocalDateTime.now()) + "] "
                + request.ip() + " "
                + "\"" + request.requestMethod() + " "
                + request.pathInfo() + " "
                + request.protocol() + "\" "
                + "[" + request.body() + "]";
        log.info(message);
    }

    private void configureServer(int serverPort) {
        port(serverPort);
        String keystoreFile = System.getenv("KEYSTORE_FILE");
        String keystorePassword = System.getenv("KEYSTORE_PASSWORD");
        if (keystoreProvided(keystoreFile, keystorePassword)) {
            secure(keystoreFile, keystorePassword, null, null);
            log.info("MicroServer running using HTTPS on port {}.", serverPort);
        } else {
            log.info("MicroServer running using HTTP on port {}.", serverPort);
        }

        // To Serve Static Files (SPA)

        staticFiles.location("/public/");
        redirect.get("/", "/index.html");
    }

    private boolean keystoreProvided(String keystoreFile, String keystorePassword) {
        return (keystoreFile != null && keystorePassword != null);
    }
}
