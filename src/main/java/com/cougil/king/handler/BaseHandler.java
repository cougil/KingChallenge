package com.cougil.king.handler;

import com.cougil.king.users.SessionUsers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public abstract class BaseHandler implements HttpHandler {

    protected SessionUsers sessionUsers;

    public BaseHandler(SessionUsers sessionUsers) {
        this.sessionUsers = sessionUsers;
    }

    protected String getPath(HttpExchange httpExchange) {
        return httpExchange.getRequestURI().getPath().toLowerCase();
    }

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

    protected void replyError(HttpExchange httpExchange, String message, OutputStream os) throws IOException {
        httpExchange.sendResponseHeaders(500, message.length());
        os.write(message.getBytes());
    }
}
