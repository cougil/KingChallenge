package com.cougil.king.handler;

import com.cougil.king.users.SessionUsers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class HighScoreListHandler extends BaseHandler {

    public HighScoreListHandler(SessionUsers sessionUsers) {
        super(sessionUsers);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "HighScoreListHandler!";
        System.out.println(response);
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
