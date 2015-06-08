package com.cougil.king.server;

import com.sun.net.httpserver.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class GameServerTest {

    private GameServer gameServer;
    private HttpServer httpServer;

    @Before
    public void setUp() throws IOException {
        httpServer = mock(HttpServer.class);
        gameServer = new GameServer(httpServer);
    }

    @After
    public void tearDown() {
        gameServer.stop();
    }

    @Test
    public void startingTheServerShouldCreateAndStartTheHttpServer() {
        gameServer.start();
        verify(httpServer, times(1)).start();
    }

    @Test
    public void stoppingTheServerShouldStopTheHttpServer() {
        gameServer.start();
        gameServer.stop();
        verify(httpServer, times(1)).start();
        verify(httpServer, times(1)).stop(anyInt());
    }

    @Test(expected = java.net.BindException.class)
    public void startingTheServerTwiceInTheSamePortShouldNotBePossible() throws IOException {
        gameServer = new GameServer();
        gameServer.start();
        GameServer gameServerAux = new GameServer();
        gameServerAux.start();
    }

    @Test
    public void startingServersInDifferentPortsShouldStartTheHttpServers() throws IOException {
        gameServer = new GameServer(12345);
        gameServer.start();
        GameServer gameServerAux = new GameServer(6789);
        gameServerAux.start();

        assertNotNull(gameServerAux);
        assertNotNull(gameServer);

        gameServerAux.stop();
    }
}
