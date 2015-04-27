package com.cougil.king.handler;

import com.cougil.king.users.SessionUsers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class LoginHandler extends BaseHandler {

    public LoginHandler(SessionUsers sessionUsers) {
        super(sessionUsers);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        long startTime = System.nanoTime();

        final String PATH = getPath(httpExchange);

        OutputStream os = httpExchange.getResponseBody();

        try {
            final Integer userId = Integer.parseInt(PATH.substring(1, PATH.indexOf("/login")));
            System.out.println("Login handler [" + userId + " - " + startTime + "] - Received request!");
            final String sessionKey= sessionUsers.login(userId);
            randomSleep();

            httpExchange.sendResponseHeaders(200, sessionKey.length());
            os.write(sessionKey.getBytes());

            long elapsedTime = (System.nanoTime() - startTime) / 1000000;
            System.out.println("Login handler ["+ userId +" - "+startTime+"] - Return response. ElapsedTime: "+elapsedTime+" ms");

        } catch (NumberFormatException nfe) {
            replyError(httpExchange, "Invalid userId with path '" + PATH + "'", os);

        } finally {
            os.close();
        }

    }

}
