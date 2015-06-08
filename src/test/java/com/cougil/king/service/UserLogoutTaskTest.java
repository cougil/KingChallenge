package com.cougil.king.service;

import com.cougil.king.model.SessionUsers;
import com.cougil.king.model.UserSession;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class UserLogoutTaskTest {

    private UserLogoutTask userLogoutTask;
    private SessionUsers sessionUsers;

    @Before
    public void setUp() {
        sessionUsers = mock(SessionUsers.class);
        userLogoutTask = new UserLogoutTask(sessionUsers, 1*1000); // 1 second of life
    }

    @Test
    public void withoutAnySessionsNoValueMustRemoved() {
        userLogoutTask.run();

        verify(sessionUsers, times(1)).values();
        verify(sessionUsers, times(0)).remove(anyString());
    }

    @Test
    public void oneSessionShouldNotBeRemovedIfItIsNewerThanTheLogoutTimeout() {

        userLogoutTask = new UserLogoutTask(sessionUsers, 30*1000); // 30 seconds of life

        Date date = new Date();
        date.setTime( date.getTime() - 5 * 1000); // 5 seconds before
        UserSession userSession = new UserSession(1, "12345", date);

        when(sessionUsers.values()).thenReturn(Arrays.asList(userSession));

        userLogoutTask.run();

        verify(sessionUsers, times(1)).values();
        assertTrue(sessionUsers.values().size() == 1);
        verify(sessionUsers, times(0)).remove(anyString());
    }

    @Test
    public void oneSessionShouldBeRemovedIfItsOlderThanTheLogoutTimeout() {

        Date date = new Date();
        date.setTime( date.getTime() - 5 * 1000); // 5 seconds before
        UserSession userSession = new UserSession(1, "12345", date);

        when(sessionUsers.remove(userSession.getSessionKey())).thenReturn(userSession);
        when(sessionUsers.values()).thenReturn(Arrays.asList(userSession));

        userLogoutTask.run();

        verify(sessionUsers, times(1)).values();
        assertTrue(sessionUsers.values().size() == 1);
        verify(sessionUsers, times(1)).remove(userSession.getSessionKey());
    }

}
