package com.cougil.king.service;

import com.cougil.king.model.LevelScores;
import com.cougil.king.model.SessionUsers;
import com.cougil.king.model.UserScore;
import com.cougil.king.model.UserSession;
import com.cougil.king.util.CollectionUtils;

import java.util.SortedSet;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Default service class implementation and responsible of managing the main business of the game
 */
public class GameServiceImpl implements GameService {

    public static final long LOGOUT_TIMEOUT = 3 * 1000;
//    public static final long LOGOUT_TIMEOUT = 10 * 60 * 1000;    // 10 minutes

    private static final int MAX_SCORES = 15;

    private SessionUsers sessionUsers;

    private LevelScores levelScores;
    private final Timer userLogoutTimer;

    public GameServiceImpl() {
        sessionUsers = new SessionUsers();
        levelScores = new LevelScores();
        userLogoutTimer = new Timer(true);
    }

    @Override
    public String login(Integer userId) {
        final String sessionKey = UserSession.nextSessionId(userId);
        UserSession userSession = new UserSession(userId, sessionKey);
        sessionUsers.put(sessionKey, userSession);
        userLogoutTimer.schedule(new UserLogoutTask(sessionUsers, LOGOUT_TIMEOUT), LOGOUT_TIMEOUT);
        return sessionKey;
    }

    @Override
    public void score(String sessionKey, Integer levelId, Integer score) {
        UserSession userSession = sessionUsers.get(sessionKey);
        if (userSession != null) {
            UserScore userScore = new UserScore(score, userSession.getUserId());
            levelScores.saveUserScore(userScore, levelId);
        }
    }

    @Override
    public SortedSet<UserScore> getHighScoreList(Integer levelId) {
        ConcurrentHashMap<UserScore, Integer> userScores = levelScores.getUserScores(levelId);
        if (userScores == null) {
            userScores = new ConcurrentHashMap<UserScore, Integer>();
        }
        return CollectionUtils.keySetReverseOrder(userScores, MAX_SCORES);
    }

}
