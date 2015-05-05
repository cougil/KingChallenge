package com.cougil.king;

import com.cougil.king.uri.URIFactoryHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class GameServer {

    public static final int PORT = 8081;
    public static final int DEFAULT_BACKLOG = 0;

    private GameUserSessionScores gameUserSessionScores = new GameUserSessionScores();

    public void start() {
        HttpServer httpServer = null;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(PORT), DEFAULT_BACKLOG);
            httpServer.createContext("/", new URIFactoryHandler(gameUserSessionScores));
            httpServer.setExecutor(Executors.newCachedThreadPool());
            httpServer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
