package com.cougil.king.users;

import java.util.Date;
import java.util.Map;
import java.util.TimerTask;

/**
 * This task is responsible of removing User Sessions {@link UserSession} valid only during a certain time
 * (by default 10 mins)
 */
public class UserLogoutTask extends TimerTask {

    private Map<String, UserSession> userSessions;

    public final long LOGOUT_TIMEOUT;

    /**
     * Creates a new task responsible of removing user sessions created previously
     * @param userSessions Map with the list of users and sessions created previously
     * @param logoutTimeout Default timeout in milliseconds until a user session must be removed
     */
    public UserLogoutTask(Map<String, UserSession> userSessions, final long logoutTimeout) {
        this.userSessions = userSessions;
        this.LOGOUT_TIMEOUT = logoutTimeout;
    }

    @Override
    public void run() {
        final Date now = new Date();
        for (UserSession userSession : userSessions.values()) {
            Date createdDate = userSession.getCreatedDate();
            if (now.getTime() - createdDate.getTime() > LOGOUT_TIMEOUT) {
                UserSession user = userSessions.remove(userSession.getSessionKey());
                System.out.println("Removed userSession ["+user+"]");
            }
        }
    }
}
