package com.cougil.king.service;

import com.cougil.king.users.UserLogoutTask;
import com.cougil.king.users.UserScore;
import com.cougil.king.users.UserSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Default service class implementation and responsible of managing the main business of the game
 */
public class GameServiceImpl implements GameService {

    // 10 minutes
    public static final long LOGOUT_TIMEOUT = 10 * 60 * 1000;

    private static final int MAX_SCORES = 15;

    // User sessions: we will save the 'sessionKey' as a key and the user session (@UserSession) as a value
    private ConcurrentHashMap<String, UserSession> sessionUsers;

    // Level scores: we will save the 'level' as a key and the list of scores (@UserScore) as a value
    private ConcurrentHashMap<Integer, ConcurrentHashMap<UserScore,Integer>> levelScores;

    public GameServiceImpl() {
        sessionUsers = new ConcurrentHashMap<String, UserSession>();
        levelScores = new ConcurrentHashMap<Integer, ConcurrentHashMap<UserScore,Integer>>();

        Timer userLogoutTimer = new Timer(true);
        userLogoutTimer.schedule(new UserLogoutTask(sessionUsers, LOGOUT_TIMEOUT), 0, LOGOUT_TIMEOUT);
    }

    @Override
    public String login(Integer userId) {
        final String sessionKey = UserSession.nextSessionId(userId);
        UserSession userSession = new UserSession(userId, sessionKey);
        sessionUsers.put(sessionKey, userSession);
        return sessionKey;
    }

    @Override
    public void score(String sessionKey, Integer levelId, Integer score) {
        UserSession userSession = sessionUsers.get(sessionKey);
        if (userSession != null) {
            ConcurrentHashMap<UserScore, Integer> userScores = levelScores.get(levelId);
            if (userScores == null) {
                levelScores.putIfAbsent(levelId, new ConcurrentHashMap<UserScore, Integer>());
                userScores = levelScores.get(levelId);
            }

            UserScore userScore = new UserScore(score, userSession.getUserId());
            putUserScore(userScores, userScore);

        }
    }

    private void putUserScore(ConcurrentHashMap<UserScore, Integer> userScores, UserScore userScore) {
        Integer oldScore = userScores.putIfAbsent(userScore, userScore.getScore());
        userScores.remove(userScore);
        if (oldScore != null && oldScore > userScore.getScore()) {
            userScore = new UserScore(oldScore, userScore.getUserId());
        }
        userScores.put(userScore, userScore.getScore());
    }

    @Override
    public SortedSet<UserScore> getHighScoreList(Integer levelId) {
        ConcurrentHashMap<UserScore, Integer> userScores = levelScores.get(levelId);
        return sortedUserScores(userScores);
    }

    private SortedSet<UserScore> sortedUserScores(ConcurrentHashMap<UserScore, Integer> userScores) {
        if (userScores == null) {
            userScores = new ConcurrentHashMap<UserScore, Integer>();
        }
        return keySetReverseOrder(userScores, MAX_SCORES);
    }

    private <K,V> SortedSet<K> keySetReverseOrder(ConcurrentHashMap<K, V> map, final int MAX_VALUES) {
        SortedSet<K> sortedSet = new ConcurrentSkipListSet<K>(Collections.reverseOrder());
        List<K> list = reversedListKeySet(map.keySet());
        for (int i=0,j = 0; i<MAX_VALUES && j<list.size();j++) {
            K key = list.get(i);
            if (sortedSet.add(key)) i++;
        }
        return sortedSet;
    }

    private <K> List<K> reversedListKeySet(Set<K> keyset) {
        List<K> list= Collections.synchronizedList(new ArrayList<K>(keyset));
        Collections.sort(list, Collections.reverseOrder());
        return list;
    }

}
