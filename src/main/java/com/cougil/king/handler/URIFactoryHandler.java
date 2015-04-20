package com.cougil.king.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URI;

public class URIFactoryHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        HttpHandler handler = URIFactoryHandler.getHandler(httpExchange.getRequestURI());
        handler.handle(httpExchange);
    }

    public static HttpHandler getHandler(URI requestURI) {
        String path = requestURI.getPath();
        if (path.endsWith("/login")) {
            return new LoginHandler(requestURI);
        } else if (path.endsWith("/highscorelist")) {
            return new HighScoreListHandler(requestURI);
        } else {
            return new UserScoreLevelHandler(requestURI);
        }
    }

}
