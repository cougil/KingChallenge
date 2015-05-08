package com.cougil.king.handler;

import com.cougil.king.service.GameService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * Base handler to use in the application
 */
public abstract class BaseHandler implements HttpHandler {

    protected GameService gameService;

    public BaseHandler(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Returns the decoded path of the requested URI in lower case
     * @param httpExchange HTTP request received
     * @return The path provided in lower case
     */
    protected String getPath(HttpExchange httpExchange) {
        return httpExchange.getRequestURI().getPath().toLowerCase();
    }

    /**
     * Returns the decoded query component of the requested URI in lower case
     * @param httpExchange HTTP request received
     * @return The query provided in lower case
     */
    protected String getQuery(HttpExchange httpExchange) {
        return httpExchange.getRequestURI().getQuery().toLowerCase();
    }

    protected void randomSleep() {
        try {
            Random r = new Random();
            int rand = (int) (r.nextDouble()*2+1)*1000;
            Thread.sleep(rand);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Provides a mechanism to return an 500 error as a response to the HTTP requested
     * @param httpExchange The HTTP request exchanged that will be used to return the message
     * @param message Message to be returned
     * @param os The stream to use to send the response body
     * @throws IOException
     */
    protected void replyError(HttpExchange httpExchange, String message, OutputStream os) throws IOException {
        httpExchange.sendResponseHeaders(500, message.length());
        os.write(message.getBytes());
    }
}
