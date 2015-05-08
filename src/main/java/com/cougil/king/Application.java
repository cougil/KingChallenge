package com.cougil.king;

import com.cougil.king.server.GameServer;

/**
 * Main class. Entry point to launch the Application
 */
public class Application {

    public static void main( String[] args ) {
        GameServer gameServer = new GameServer();
        gameServer.start();
    }
}
