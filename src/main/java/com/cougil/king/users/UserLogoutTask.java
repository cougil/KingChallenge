package com.cougil.king.users;

import com.cougil.king.GameUserSessionScores;

import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class UserLogoutTask extends TimerTask {

    private volatile ConcurrentHashMap<String, UserSession> userSessions;

    public UserLogoutTask(ConcurrentHashMap<String, UserSession> userSessions) {
        this.userSessions = userSessions;
    }

    @Override
    public void run() {
        final Date now = new Date();
        for (UserSession userSession : userSessions.values()) {
            Date createdDate = userSession.getCreatedDate();
            if (now.getTime() - createdDate.getTime() > GameUserSessionScores.LOGOUT_TIMEOUT) {
                UserSession user = userSessions.remove(userSession.getSessionKey());
                System.out.println("Removed userSession ["+user+"]");
            }
        }
    }
}
