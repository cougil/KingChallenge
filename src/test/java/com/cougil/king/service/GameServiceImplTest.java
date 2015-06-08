package com.cougil.king.service;

import com.cougil.king.model.LevelScores;
import com.cougil.king.model.SessionUsers;
import com.cougil.king.model.UserSession;
import org.junit.Before;
import org.junit.Test;

import java.util.Timer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GameServiceImplTest {

    @Before
    public void setUp() {
    }

    @Test
    public void loginShouldCreateANewSessionKeyAndAddANewUserSessionWithThisSessionKey() {
        SessionUsers sessionUsers = mock(SessionUsers.class);
        LevelScores levelScores = mock(LevelScores.class);
        Timer timer = mock(Timer.class);

        UserSession userSession = new UserSession(12345, UserSession.nextSessionId(12345));

        GameServiceImpl gameService = spy( new GameServiceImpl(sessionUsers, levelScores, timer) );
        doReturn(userSession).when(gameService).createUserSession(userSession.getUserId());

//        when(gameService.createUserSession(userSession.getUserId(), userSession.getSessionKey())).thenReturn(userSession);

        String sessionKey = gameService.login(12345);

        verify(sessionUsers, times(1)).put(eq(sessionKey), eq(userSession));
        assertEquals(sessionKey, userSession.getSessionKey());
    }
}
