package com.cougil.king.handler;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class URIMatcherTest {

    private URIMatcher uriMatcher;

    @Before
    public void setUp() throws Exception {
        uriMatcher = new URIMatcher();
    }

    @Test
    public void loginUriIsOkWithAValidUserId() throws Exception {
        assertTrue(uriMatcher.isLogin("/4711/login"));
    }

    @Test
    public void loginUriIsKoWithInvalidUserId() throws Exception {
        assertFalse(uriMatcher.isLogin("/asdf/login"));
    }

    @Test
    public void loginUriIsKoWithoutUserId() throws Exception {
        assertFalse(uriMatcher.isLogin("//login"));
    }

    @Test
    public void loginUriIsKoWithoutLogin() throws Exception {
        assertFalse(uriMatcher.isLogin("/"));
    }


    @Test
    public void testIsHighScoreList() throws Exception {

    }

    @Test
    public void testIsUserScoreLevel() throws Exception {

    }
}