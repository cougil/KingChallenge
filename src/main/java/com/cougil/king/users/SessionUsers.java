package com.cougil.king.users;

import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class SessionUsers {

    // 10 minutes
    //private static final long LOGOUT_TIMEOUT = 10 * 60 * 1000;

    public static final long LOGOUT_TIMEOUT = 3 * 1000;

    // User sessions: we will save the 'sessionKey' as a key and the 'userId' as a value
    private volatile ConcurrentHashMap<String, UserSession>  userSessions;

    // Level scores: we will save the 'scores' first, and then the 'userId'
    private volatile ConcurrentSkipListMap<Integer, Integer> levelScores;

    public SessionUsers() {
        userSessions = new ConcurrentHashMap<String, UserSession>();
        levelScores = new ConcurrentSkipListMap<Integer, Integer>();

        Timer userLogoutTimer = new Timer(true);
        userLogoutTimer.schedule(new UserLogoutTask(userSessions), 0, LOGOUT_TIMEOUT);
    }

    public String login(Integer userId) {
        final String sessionKey = UUID.randomUUID().toString();
        UserSession userSession = new UserSession(userId, sessionKey);
        userSessions.put(sessionKey, userSession);
        return sessionKey;
    }

    public void score(String sessionKey, Integer score, Integer levelId) {
//        UserSession userSession = new UserSession();
//        userSession = userSessions.remove(sessionKey, score);
    }
}
