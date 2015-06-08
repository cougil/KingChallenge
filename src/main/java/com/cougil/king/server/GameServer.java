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
    private HttpServer httpServer;
    private GameService gameService;

    public GameServer() throws IOException {
        this(DEFAULT_PORT);
    }

    public GameServer(final int PORT) throws IOException {
        this(HttpServer.create(new InetSocketAddress(PORT), DEFAULT_BACKLOG));
    }

    public GameServer(HttpServer httpServer) {
        this.httpServer = httpServer;
        this.gameService = new GameServiceImpl();
    }

    public void start() {

        Runtime.getRuntime().addShutdownHook(new ShutdownGameServer());

        httpServer.createContext("/", new URIFactoryHandler(gameService));
        httpServer.setExecutor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*100));
        httpServer.start();

        System.out.println("Server started.");
    }

    public void stop() {
        httpServer.stop(0);
    }

    private class ShutdownGameServer extends Thread {
        public void run() {
            httpServer.stop(0);
        }

    }
}
