package com.cougil.king.handler;

import com.cougil.king.model.UserSession;
import com.cougil.king.service.GameService;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class URIFactoryHandlerTest {

    private URIFactoryHandler uriFactoryHandler;

    @Before
    public void setUp() {
        uriFactoryHandler = new URIFactoryHandler( mock(GameService.class) );
    }

    @Test
    public void loginURIShouldReturnALoginHandler() throws URISyntaxException {
        URI uri = new URI("/4711/login");
        assertTrue(uriFactoryHandler.getHandler(uri) instanceof LoginHandler);
    }

    @Test
    public void highScoreURIShouldReturnAHighScoreListHandler() throws URISyntaxException {
        URI uri = new URI("/4711/highscorelist");
        assertTrue(uriFactoryHandler.getHandler(uri) instanceof HighScoreListHandler);
    }

    @Test
    public void userScoreURIShouldReturnAUserScoreHandler() throws URISyntaxException {
        URI uri = new URI("/4711/score?sessionkey="+ UserSession.nextSessionId(1000));
        assertTrue(uriFactoryHandler.getHandler(uri) instanceof UserScoreLevelHandler);
    }

    @Test
    public void rootURIShouldReturnNullHandler() throws URISyntaxException {
        URI uri = new URI("/");
        assertNull(uriFactoryHandler.getHandler(uri));
    }

    @Test
    public void invalidURIShouldReturnNullHandler() throws URISyntaxException {
        URI uri = new URI("/example");
        assertNull(uriFactoryHandler.getHandler(uri));
    }

}
