package com.cougil.king;

import com.cougil.king.server.GameServer;

import java.io.IOException;

/**
 * Main class. Entry point to launch the Application
 */
public class Application {

    public static void main( String[] args ) {
        GameServer gameServer = null;
        try {
            gameServer = new GameServer();
            gameServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
