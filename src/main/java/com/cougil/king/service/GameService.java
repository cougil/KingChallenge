package com.cougil.king.service;


import com.cougil.king.model.UserScore;

import java.util.SortedSet;

/**
 * Main class responsible of the core business of the game
 */
public interface GameService {

    /**
     * Makes the login process for the specified userId returning the session key associated to that user.
     * @param userId The id of the user
     * @return The session key associated to the specified user
     */
    String login(Integer userId);

    /**
     * Submits a new score for the user (identified by his session key) and level specified.
     * @param sessionKey Session key linked to a user
     * @param levelId Level identifier
     * @param score Score submitted
     */
    void score(String sessionKey, Integer levelId, Integer score);

    /**
     * Retrieves the high scores for the level specified. It returns only the highest score for each user and
     * no more than the 15 scores in total ordered by descending score order. It will return an empty {@link SortedSet}
     * if there are no scores for that level
     * @param levelId Level identifier
     * @return The list of user scores for the level specified
     */
    SortedSet<UserScore> getHighScoreList(Integer levelId);
}
