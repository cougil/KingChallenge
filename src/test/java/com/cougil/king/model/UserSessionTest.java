package com.cougil.king.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserSessionTest {

    private UserSession userSession;

    @Before
    public void setUp() {
        userSession = new UserSession(111, UserSession.nextSessionId(111));
    }

    @Test
    public void userSessionShouldNotBeEqualToNull() {
        assertFalse(userSession.equals(null));
    }

    @Test
    public void userSessionShouldBeEqualToTheSameReference() {
        assertTrue(userSession.equals(userSession));
    }

    @Test
    public void userSessionShouldNotBeEqualToAnotherObject() {
        assertFalse(userSession.equals(new Integer(1)));
    }

    @Test
    public void userSessionsWithSameUserIdShouldBeEquals() {
        UserSession sameUserId = new UserSession(userSession.getUserId(), UserSession.nextSessionId(222));
        assertTrue(userSession.equals(sameUserId));
    }

    @Test
    public void callingForSessionIdReturnsANonEmptyString() {
        String sessionId= UserSession.nextSessionId(1);
        assertNotNull(sessionId);
        assertTrue(sessionId.length() > 0);
    }

}
