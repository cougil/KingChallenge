package com.cougil.king.handler;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class URIMatcherTest {

    private URIMatcher uriMatcher;

    @Before
    public void setUp() {
    }

    @Test
    public void loginUriShouldHaveValidUserId() {
        assertTrue(uriMatcher.isLogin("/4711/login"));
    }

    @Test
    public void loginUriIsInvalidWithInvalidUserId() {
        assertFalse(uriMatcher.isLogin("/asdf/login"));
    }

    @Test
    public void loginUriIsInvalidWithoutUserId() {
        assertFalse(uriMatcher.isLogin("//login"));
    }

    @Test
    public void rootUriIsInvalidLoginUri() {
        assertFalse(uriMatcher.isLogin("/"));
    }

    @Test
    public void userScoreUriShouldHaveValidSessionKeyAndLevelId() {
        assertTrue(uriMatcher.isUserScoreLevel("/4711/score?sessionkey=asdfghi"));
    }

    @Test
    public void userScoreUriIsInvalidWithEmptySessionKey() {
        assertFalse(uriMatcher.isUserScoreLevel("/4711/score?sessionkey="));
    }

    @Test
    public void userScoreUriIsInvalidWithoutSessionKey() {
        assertFalse(uriMatcher.isUserScoreLevel("/4711/score"));
    }

    @Test
    public void userScoreUriIsInvalidWithInvalidLevelId() {
        assertFalse(uriMatcher.isUserScoreLevel("/asdf/score?sessionkey=asdfghi"));
    }

    @Test
    public void userScoreUriIsInvalidWithoutLevelId() {
        assertFalse(uriMatcher.isUserScoreLevel("//score?sessionkey=asdfghi"));
    }

    @Test
    public void rootUriIsInvalidUserScoreUri() {
        assertFalse(uriMatcher.isUserScoreLevel("/"));
    }

    @Test
    public void highScoreListUriShouldHaveValidLevelId() {
        assertTrue(uriMatcher.isHighScoreList("/4711/highscorelist"));
    }

    @Test
    public void highScoreListUriIsInvalidWithInvalidLevelId() {
        assertFalse(uriMatcher.isHighScoreList("/asdf/highscorelist"));
    }

    @Test
    public void highScoreListUriIsInvalidWithoutLevelId() {
        assertFalse(uriMatcher.isUserScoreLevel("//highscorelist"));
    }

    @Test
    public void rootUriIsInvalidHighScoreListUri() {
        assertFalse(uriMatcher.isHighScoreList("/"));
    }


}