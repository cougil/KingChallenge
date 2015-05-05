package com.cougil.king.uri;

import com.cougil.king.GameUserSessionScores;
import com.cougil.king.handler.BaseHandler;
import com.cougil.king.handler.HighScoreListHandler;
import com.cougil.king.handler.LoginHandler;
import com.cougil.king.handler.UserScoreLevelHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URI;

public class URIFactoryHandler extends BaseHandler {

    public URIFactoryHandler(GameUserSessionScores gameUserSessionScores) {
        super(gameUserSessionScores);
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

    private HttpHandler getHandler(URI requestURI) {
        HttpHandler handler = null;
        if (URIMatcher.isLogin(requestURI.toString())) {
            handler = new LoginHandler(gameUserSessionScores);
        } else if (URIMatcher.isHighScoreList(requestURI.toString())) {
            handler = new HighScoreListHandler(gameUserSessionScores);
        } else if (URIMatcher.isUserScoreLevel(requestURI.toString())) {
            handler = new UserScoreLevelHandler(gameUserSessionScores);
        } else {
            System.out.println("---No handler for request: "+requestURI.toString());
        }
        return handler;
    }

}
