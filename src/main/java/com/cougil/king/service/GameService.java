package com.cougil.king.service;


import com.cougil.king.users.UserScore;

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

    void score(String sessionKey, Integer levelId, Integer score);

    SortedSet<UserScore> getHighScoreList(Integer levelId);
}
