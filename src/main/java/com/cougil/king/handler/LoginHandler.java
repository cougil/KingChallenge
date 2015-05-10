package com.cougil.king.handler;

import com.cougil.king.service.GameService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Handler responsible of managing the requests for login a user.
 * <p>
 * It returns a session key valid for a fixed amount of time (by default 10 minutes)
 * <p>
 * Format: <code>GET /&lt;userid&gt;/login</code>
 * <p>
 * <code>Example: <br>
 * Request: GET /12345/login<br>
 * Response: 3fdd2a0da0b09ed0
 * </code>
 */
public class LoginHandler extends BaseHandler {

    public LoginHandler(GameService gameService) {
        super(gameService);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        long startTime = System.nanoTime();

        final String PATH = getPath(httpExchange);

        OutputStream os = httpExchange.getResponseBody();

        try {
            final Integer userId = Integer.parseInt(PATH.substring(1, PATH.indexOf("/login")));
            System.out.println("Login handler [" + userId + " - " + startTime + "] - Received request!");

            final String sessionKey= gameService.login(userId);

            httpExchange.sendResponseHeaders(200, sessionKey.length());
            os.write(sessionKey.getBytes());

            long elapsedTime = (System.nanoTime() - startTime) / 1000000;
            System.out.println("Login handler ["+ userId +" - "+startTime+"] - Return response: ["+sessionKey+"]. ElapsedTime: "+elapsedTime+" ms");

        } catch (NumberFormatException nfe) {
            replyError(httpExchange, "Invalid userId with path '" + PATH + "'", os);

        } finally {
            os.close();
        }

    }

}
