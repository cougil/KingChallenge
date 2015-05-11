package com.cougil.king.handler;

import com.cougil.king.server.URIMatcher;
import com.cougil.king.service.GameService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URI;

/**
 * Handler responsible of setup the different {@link java.net.URI} accepted by the HTTP server with the
 * right {@link com.sun.net.httpserver.HttpHandler} that will attend the incoming request
 */
public class URIFactoryHandler extends BaseHandler {

    public URIFactoryHandler(GameService gameService) {
        super(gameService);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        HttpHandler handler = getHandler(httpExchange.getRequestURI());
        if (handler != null) {
            handler.handle(httpExchange);
        } else {
            httpExchange.getResponseBody().close();
        }
    }

    public HttpHandler getHandler(URI requestURI) {
        HttpHandler handler = null;
        if (URIMatcher.isLogin(requestURI.toString())) {
            handler = new LoginHandler(gameService);
        } else if (URIMatcher.isHighScoreList(requestURI.toString())) {
            handler = new HighScoreListHandler(gameService);
        } else if (URIMatcher.isUserScoreLevel(requestURI.toString())) {
            handler = new UserScoreLevelHandler(gameService);
        } else {
            System.out.println("---No handler for request: "+requestURI.toString());
        }
        return handler;
    }

}
