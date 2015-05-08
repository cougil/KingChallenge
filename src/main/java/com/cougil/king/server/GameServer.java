package com.cougil.king.server;

import com.cougil.king.handler.URIFactoryHandler;
import com.cougil.king.service.GameService;
import com.cougil.king.service.GameServiceImpl;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Object responsible of creating the {@link com.sun.net.httpserver.HttpServer} linking the URIs to handle with the
 * game logic
 */
public class GameServer {

    public static final int DEFAULT_PORT = 8081;
    public static final int DEFAULT_BACKLOG = 0;
    private final int PORT;

    private GameService gameService;

    public GameServer() {
        this(DEFAULT_PORT);
    }

    public GameServer(int port) {
        this.PORT = port;
        this.gameService = new GameServiceImpl();
    }

    public void start() {
        HttpServer httpServer = null;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(PORT), DEFAULT_BACKLOG);
            httpServer.createContext("/", new URIFactoryHandler(gameService));
            httpServer.setExecutor(Executors.newCachedThreadPool());
            httpServer.start();
            System.out.println("Server started.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
