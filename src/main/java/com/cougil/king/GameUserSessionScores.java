package com.cougil.king;

import com.cougil.king.users.UserLogoutTask;
import com.cougil.king.users.UserScore;
import com.cougil.king.users.UserSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class GameUserSessionScores {

    // 10 minutes
    public static final long LOGOUT_TIMEOUT = 10 * 60 * 1000;

    // 10 secs
    //public static final long LOGOUT_TIMEOUT = 10 * 1000;

    private static final int MAX_SCORES = 15;

    // User sessions: we will save the 'sessionKey' as a key and the user session (@UserSession) as a value
    private volatile ConcurrentHashMap<String, UserSession> sessionUsers;

    // Level scores: we will save the 'level' as a key and the list of scores (@UserScore) as a value
    private volatile ConcurrentHashMap<Integer, ConcurrentHashMap<UserScore,Integer>> levelScores;

    public GameUserSessionScores() {
        sessionUsers = new ConcurrentHashMap<String, UserSession>();
        levelScores = new ConcurrentHashMap<Integer, ConcurrentHashMap<UserScore,Integer>>();

        Timer userLogoutTimer = new Timer(true);
        userLogoutTimer.schedule(new UserLogoutTask(sessionUsers), 0, LOGOUT_TIMEOUT);
    }

    public String login(Integer userId) {
        final String sessionKey = UserSession.nextSessionId(userId);
        UserSession userSession = new UserSession(userId, sessionKey);
        sessionUsers.put(sessionKey, userSession);
        return sessionKey;
    }

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

/*
            UserScore userScore = new UserScore(score, userSession.getUserId());
            test(userScores, userScore);
            Integer oldScore = null;
            if (userScores.containsKey(userScore)) {
                oldScore = userScores.get(userScore);
                if (oldScore != null && oldScore > score) {
                    userScores.replace(userScore, oldScore);
                }
            } else {
                userScores.put(userScore, score);
            }
*/

//                oldScore = userScores.putIfAbsent(userScore, score);
//                if (oldScore == null) {
//                    userScores.put(userScore, score);
//                } else if (oldScore < score) {
//                    userScores.replace(userScore, score);
//                }

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

    private void test(ConcurrentSkipListMap<UserScore, Integer> userScores, UserScore userScore) {
        int i=0;
        for (UserScore u: userScores.keySet()){
            System.out.println(i+" "+userScore+" VS "+u+" = "+u.equals(userScore)+" >> "+u.hashCode()+" "+userScore.hashCode());
            System.out.println(i+" containsKey "+userScores.containsKey(userScore));
        }
    }

    public SortedSet<UserScore> highScoreList(Integer levelId) {
        ConcurrentHashMap<UserScore, Integer> userScores = levelScores.get(levelId);
        return sortedUserScores(userScores);
    }

    private SortedSet<UserScore> sortedUserScores(ConcurrentHashMap<UserScore, Integer> userScores) {
        if (userScores == null) {
            userScores = new ConcurrentHashMap<UserScore, Integer>();
        }
        return sortedSetNotRepeatedValues(userScores, MAX_SCORES);
    }

    private <K,V> SortedSet<K> sortedSetNotRepeatedValues(ConcurrentHashMap<K, V> map, final int max_values) {
        SortedSet<K> sortedSet = new ConcurrentSkipListSet<K>(Collections.reverseOrder());
        Iterator<K> it = listKeySet(map).listIterator();
        for (int i = 0; i<max_values && it.hasNext();) {
            K key = it.next();
            if (sortedSet.add(key)) i++;
        }
        return sortedSet;
    }

    private <K,V> List<K> listKeySet(ConcurrentHashMap<K,V> map) {
        List<K> list= Collections.synchronizedList(new ArrayList<K>(map.keySet()));
        Collections.sort(list, Collections.reverseOrder());
        return list;
    }

}
