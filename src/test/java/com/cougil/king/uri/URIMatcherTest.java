package com.cougil.king.uri;

import com.cougil.king.users.UserSession;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class URIMatcherTest {

    @Before
    public void setUp() {
    }

    @Test
    public void loginUriShouldHaveValidUserId() {
        assertTrue(URIMatcher.isLogin("/4711/login"));
    }

    @Test
    public void loginUriIsInvalidWithInvalidUserId() {
        assertFalse(URIMatcher.isLogin("/asdf/login"));
    }

    @Test
    public void loginUriIsInvalidWithoutUserId() {
        assertFalse(URIMatcher.isLogin("//login"));
    }

    @Test
    public void rootUriIsInvalidLoginUri() {
        assertFalse(URIMatcher.isLogin("/"));
    }

    @Test
    public void userScoreUriShouldHaveValidSessionKeyAndLevelId() {
        assertTrue(URIMatcher.isUserScoreLevel("/4711/score?sessionkey="+ UserSession.nextSessionId(1000)));
    }

    @Test
    public void userScoreUriIsInvalidWithEmptySessionKey() {
        assertFalse(URIMatcher.isUserScoreLevel("/4711/score?sessionkey="));
    }

    @Test
    public void userScoreUriIsInvalidWithoutSessionKey() {
        assertFalse(URIMatcher.isUserScoreLevel("/4711/score"));
    }

    @Test
    public void userScoreUriIsInvalidWithInvalidLevelId() {
        assertFalse(URIMatcher.isUserScoreLevel("/asdf/score?sessionkey=asdfghi"));
    }

    @Test
    public void userScoreUriIsInvalidWithoutLevelId() {
        assertFalse(URIMatcher.isUserScoreLevel("//score?sessionkey=asdfghi"));
    }

    @Test
    public void rootUriIsInvalidUserScoreUri() {
        assertFalse(URIMatcher.isUserScoreLevel("/"));
    }

    @Test
    public void highScoreListUriShouldHaveValidLevelId() {
        assertTrue(URIMatcher.isHighScoreList("/4711/highscorelist"));
    }

    @Test
    public void highScoreListUriIsInvalidWithInvalidLevelId() {
        assertFalse(URIMatcher.isHighScoreList("/asdf/highscorelist"));
    }

    @Test
    public void highScoreListUriIsInvalidWithoutLevelId() {
        assertFalse(URIMatcher.isUserScoreLevel("//highscorelist"));
    }

    @Test
    public void rootUriIsInvalidHighScoreListUri() {
        assertFalse(URIMatcher.isHighScoreList("/"));
    }


}