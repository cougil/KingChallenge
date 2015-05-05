package com.cougil.king;

import com.cougil.king.users.UserKeys;
import com.cougil.king.users.UserLogoutTask;
import com.cougil.king.users.UserScore;
import com.cougil.king.users.UserSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class GameUserSessionScores {

    // 10 minutes
    public static final long LOGOUT_TIMEOUT = 10 * 60 * 1000;

    // 10 secs
    //public static final long LOGOUT_TIMEOUT = 10 * 1000;

    private static final int MAX_SCORES = 15;

    // User sessions: we will save the 'sessionKey' as a key and the user session (@UserSession) as a value
    private volatile ConcurrentHashMap<String, UserSession> sessionUsers;

    // Level scores: we will save the 'level' as a key and the list of scores (@UserScore) as a value
    private volatile ConcurrentHashMap<Integer, ConcurrentSkipListMap<UserScore,Integer>> levelScores;

    public GameUserSessionScores() {
        sessionUsers = new ConcurrentHashMap<String, UserSession>();
        levelScores = new ConcurrentHashMap<Integer, ConcurrentSkipListMap<UserScore,Integer>>();

        Timer userLogoutTimer = new Timer(true);
        userLogoutTimer.schedule(new UserLogoutTask(sessionUsers), 0, LOGOUT_TIMEOUT);
    }

    public String login(Integer userId) {
        final String sessionKey = UserKeys.nextSessionId(userId);
        UserSession userSession = new UserSession(userId, sessionKey);
        sessionUsers.put(sessionKey, userSession);
        return sessionKey;
    }

    public void score(String sessionKey, Integer levelId, Integer score) {
        UserSession userSession = sessionUsers.get(sessionKey);
        if (userSession != null) {
            ConcurrentSkipListMap<UserScore, Integer> userScores = levelScores.get(levelId);
            if (userScores == null) {
                levelScores.putIfAbsent(levelId, new ConcurrentSkipListMap<UserScore, Integer>(Collections.reverseOrder()));
                userScores = levelScores.get(levelId);
            }

            UserScore userScore = new UserScore(score, userSession.getUserId());
            Integer oldScore = userScores.putIfAbsent(userScore, score);
            if (oldScore != null && oldScore < score) {
                userScores.put(userScore, score);
            }
        }
    }

    public Set<UserScore> highScoreList(Integer levelId) {
        ConcurrentSkipListMap<UserScore, Integer> userScores = levelScores.get(levelId);
        return sortedUserScores(userScores);
    }

    private Set<UserScore> sortedUserScores(ConcurrentSkipListMap<UserScore, Integer> userScores) {
        if (userScores == null) {
            userScores = new ConcurrentSkipListMap<UserScore, Integer>();
        }
        return sortedMap(userScores, MAX_SCORES).keySet();
    }

    private <K,V> SortedMap<K,V> sortedMap(SortedMap<K,V> map, final int max_values) {
        Iterator<K> it = map.keySet().iterator();
        for (int i = 0; i<max_values && it.hasNext(); i++) {
            it.next();
        }
        if (it.hasNext()) {
            return map.headMap(it.next());
        } else {
            return map;
        }
    }

}
