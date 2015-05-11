package com.cougil.king.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class LevelScoresTest {

    private LevelScores levelScores;
    private Integer level;

    @Before
    public void setUp() {
        levelScores = new LevelScores();
        level = new Integer(100);
    }

    @Test
    public void askingForUserScoresWhenNoUserScoresForThatLevelShouldReturnNull() {
        Map userScores = levelScores.getUserScores(level);
        assertNull(userScores);
    }

    @Test
    public void askingForUserScoresWhenUserScoresForDifferentLevelIdShouldReturnNull() {
        UserScore userScore = new UserScore(1111, 12345);
        levelScores.saveUserScore(userScore, level + 100);

        Map userScores = levelScores.getUserScores(level);
        assertNull(userScores);
    }

    @Test
    public void savingAUserScoreShouldAddItToTheCorrespondingLevelId() {
        ConcurrentHashMap<UserScore, Integer>  userScores = levelScores.getUserScores(level);
        assertNull(userScores);

        UserScore userScore = new UserScore(11111, 12345);
        levelScores.saveUserScore(userScore, level);

        userScores = levelScores.getUserScores(level);
        assertNotNull(userScores);
        assertTrue(userScores.size() == 1);
        Integer score = userScores.values().iterator().next();
        assertEquals(score.intValue(), userScore.getScore().intValue());
    }

    @Test
    public void askingForUserScoresWhenUserScoresForSameLevelIdShouldReturnTheMapWithHisScore() {
        UserScore userScore1 = new UserScore(11111, 12345);
        levelScores.saveUserScore(userScore1, level);

        UserScore userScore2 = new UserScore(2222, 6789);
        levelScores.saveUserScore(userScore2, level);

        Map userScores = levelScores.getUserScores(level);

        assertNotNull(userScores);
        assertTrue(userScores.size() == 2);
        assertTrue(userScores.containsKey(userScore2));
    }

    @Test
    public void addingAUserScoreToTheMapShouldKeepIt() {
        ConcurrentHashMap<UserScore, Integer> userScores = new ConcurrentHashMap<UserScore, Integer>();
        UserScore userScore = new UserScore(11111, 12345);

        levelScores.putUserScore(userScores, userScore);
        assertTrue(userScores.containsKey(userScore));
        assertTrue(userScores.containsValue(userScore.getScore()));
    }

    @Test
    public void addingTheSameUserScoreWithLowestScoreToTheMapShouldKeepThePreviousScore() {
        ConcurrentHashMap<UserScore, Integer> userScores = new ConcurrentHashMap<UserScore, Integer>();

        UserScore upperUserScore = new UserScore(10000, 12345);
        levelScores.putUserScore(userScores, upperUserScore);

        UserScore lowerUserScore = new UserScore(10, 12345);
        levelScores.putUserScore(userScores, lowerUserScore);

        assertTrue(userScores.size() == 1);

        UserScore savedUserScore = userScores.keys().nextElement();
        assertEquals(savedUserScore, upperUserScore);

        Integer score = userScores.values().iterator().next();
        assertEquals(score.intValue(), upperUserScore.getScore().intValue());
    }

    @Test
    public void addingTheSameUserScoreWithHighestScoreToTheMapShouldKeepTheLastScore() {
        ConcurrentHashMap<UserScore, Integer> userScores = new ConcurrentHashMap<UserScore, Integer>();

        UserScore lowerUserScore = new UserScore(10, 12345);
        levelScores.putUserScore(userScores, lowerUserScore);

        UserScore upperUserScore = new UserScore(10000, 12345);
        levelScores.putUserScore(userScores, upperUserScore);

        assertTrue(userScores.size() == 1);

        UserScore savedUserScore = userScores.keys().nextElement();
        assertEquals(savedUserScore, upperUserScore);

        Integer score = userScores.values().iterator().next();
        assertEquals(score.intValue(), upperUserScore.getScore().intValue());
    }

    @Test
    public void addingTheSameUserScoreWithSameScoreToTheMapShouldKeepTheSameScore() {
        ConcurrentHashMap<UserScore, Integer> userScores = new ConcurrentHashMap<UserScore, Integer>();

        UserScore userScore = new UserScore(10000, 12345);
        levelScores.putUserScore(userScores, userScore);

        UserScore sameUserScore = new UserScore(userScore.getScore(), 12345);
        levelScores.putUserScore(userScores, sameUserScore);

        assertTrue(userScores.size() == 1);

        UserScore savedUserScore = userScores.keys().nextElement();
        assertEquals(savedUserScore, userScore);

        Integer score = userScores.values().iterator().next();
        assertEquals(score.intValue(), userScore.getScore().intValue());
    }

}
