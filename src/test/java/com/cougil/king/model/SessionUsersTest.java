package com.cougil.king.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SessionUsersTest {

    private SessionUsers sessionUsers;
    private UserSession userSession;

    @Before
    public void setUp() {
        sessionUsers = new SessionUsers();
        userSession = new UserSession(1234, "example");
    }

    @Test
    public void askingForUserSessionWhenThereIsNoSessionShouldReturnsNull() {
        UserSession userSession = sessionUsers.get("example");
        assertNull(userSession);
    }

    @Test
    public void askingForUserSessionWhenExistsShouldReturnThePreviouslyUserSession() {
        sessionUsers.put("example", userSession);

        UserSession loadedUserSession = sessionUsers.get("example");

        assertNotNull(loadedUserSession);
        assertEquals(loadedUserSession, userSession);
    }

    @Test
    public void removingAUserSessionWhenExistsShouldRemoveIt() {
        sessionUsers.put("example", userSession);

        UserSession previousUserSession = sessionUsers.remove("example");

        assertNotNull(previousUserSession);
        assertEquals(previousUserSession, userSession);
        assertNull(sessionUsers.get("example"));
    }

}
