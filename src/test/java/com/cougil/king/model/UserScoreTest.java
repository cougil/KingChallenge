package com.cougil.king.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserScoreTest {

    private UserScore userScore;

    @Before
    public void setUp() {
        userScore = new UserScore(1000, 111);
    }

    @Test
    public void userScoreShouldNotBeEqualToNull() {
        assertFalse(userScore.equals(null));
    }

    @Test
    public void userScoreShouldBeEqualToTheSameReference() {
        assertTrue(userScore.equals(userScore));
    }

    @Test
    public void userScoreShouldNotBeEqualToAnotherObject() {
        assertFalse(userScore.equals(new Integer(1)));
    }

    @Test
    public void userScoresWithSameUserIdShouldBeEquals() {
        UserScore sameUserId = new UserScore(222, userScore.getUserId());
        assertTrue(userScore.equals(sameUserId));
    }

    @Test
    public void comparingUserScoresWithSameScoreShouldReturnEquals() {
        UserScore sameScore = new UserScore(userScore.getScore(), 222);
        assertEquals(userScore.compareTo(sameScore), 0);
    }

    @Test
    public void comparingUserScoreWithAnotherGreaterScoreShouldReturnLessThanZero() {
        UserScore greaterScore = new UserScore(userScore.getScore()+1, 222);
        assertTrue(userScore.compareTo(greaterScore) < 0);
    }

    @Test
    public void comparingUserScoreWithAnotherLowerScoreShouldReturnGreaterThanZero() {
        UserScore upperScore = new UserScore(userScore.getScore()-1, 222);
        assertTrue(userScore.compareTo(upperScore) > 0);
    }


}
