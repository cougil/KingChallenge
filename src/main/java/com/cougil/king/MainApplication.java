package com.cougil.king;

import com.cougil.king.handler.URIFactoryHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class MainApplication {

    public static final int PORT = 8081;
    public static final int DEFAULT_BACKLOG = 0;

    public static void main( String[] args ) {
        MainApplication mainApplication = new MainApplication();
        mainApplication.run();
    }

    private void run() {
        HttpServer httpServer = null;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(PORT), DEFAULT_BACKLOG);
            httpServer.createContext("/", new URIFactoryHandler());
            httpServer.setExecutor(Executors.newCachedThreadPool());
            httpServer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
