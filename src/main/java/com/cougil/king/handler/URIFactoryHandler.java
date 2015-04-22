package com.cougil.king.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URI;

public class URIFactoryHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        HttpHandler handler = getHandler(httpExchange.getRequestURI());
        if (handler != null) handler.handle(httpExchange);
    }

    private HttpHandler getHandler(URI requestURI) {
        final String path = requestURI.getPath();
        HttpHandler handler = null;
        if (URIMatcher.isLogin(path)) {
            handler = new LoginHandler(requestURI);
        } else if (URIMatcher.isHighScoreList(path)) {
            handler = new HighScoreListHandler(requestURI);
        } else if (URIMatcher.isUserScoreLevel(path)) {
            handler = new UserScoreLevelHandler(requestURI);
        }
        return handler;
    }

}
