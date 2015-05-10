package com.cougil.king.model;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Object responsible of managing the relationship between the levels with the users and the score they
 * have achieved. It stores a reference of a {@link java.util.concurrent.ConcurrentHashMap} that saves
 * the level as a key, and another {@link java.util.concurrent.ConcurrentHashMap} as a value. The
 * second map contains the {@link UserScore} as a key and his score as a value
 */
public class LevelScores {

    // Level scores: we will save the 'levelId' as a key and the list of scores (@UserScore) as a value
    private ConcurrentHashMap<Integer, ConcurrentHashMap<UserScore, Integer>> mapLevelScores;

    public LevelScores() {
        mapLevelScores = new ConcurrentHashMap<Integer, ConcurrentHashMap<UserScore, Integer>>();
    }

    /**
     * Returns the map with the user scores for the specified level id.
     * @param levelId Level id whose associated user scores are to be returned
     * @return Map with the user scores
     */
    public ConcurrentHashMap<UserScore, Integer> getUserScores(Integer levelId) {
        return mapLevelScores.get(levelId);
    }

    /**
     * Saves a new user score with the specified level id
     * @param userScore User score to ve saved
     * @param levelId Level id
     */
    public void saveUserScore(UserScore userScore, Integer levelId) {
        ConcurrentHashMap<UserScore, Integer> userScores = mapLevelScores.get(levelId);
        if (userScores == null) {
            mapLevelScores.putIfAbsent(levelId, new ConcurrentHashMap<UserScore, Integer>());
            userScores = mapLevelScores.get(levelId);
        }

        putUserScore(userScores, userScore);
    }

    private void putUserScore(ConcurrentHashMap<UserScore, Integer> userScores, UserScore userScore) {
        Integer oldScore = userScores.putIfAbsent(userScore, userScore.getScore());
        userScores.remove(userScore);
        // Just in case the previously score of the user is greater than the actual, we will save it again
        if (oldScore != null && oldScore > userScore.getScore()) {
            userScore = new UserScore(oldScore, userScore.getUserId());
        }
        userScores.put(userScore, userScore.getScore());
    }

}
