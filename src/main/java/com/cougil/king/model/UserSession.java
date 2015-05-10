package com.cougil.king.model;


import java.util.Date;

/**
 * This object holds the relationship between the userId and the sessionKey linked to that user plus
 * the exact moment when the session was created
 */
public class UserSession {
    private final Integer userId;
    private final String sessionKey;
    private final Date createdDate;

    public UserSession(Integer userId, String sessionKey) {
        this.userId = userId;
        this.sessionKey = sessionKey;
        this.createdDate = new Date();
    }

    /**
     * Returns a new session id for the specified user. This implementation does not create a fixed value
     * for each user, it is completely pseudo-random for each call. So any new call to that method creates new
     * session ids, regarding whether or not the user was already logged in
     * @param userId user id
     * @return The new session id for the user
     */
    public static String nextSessionId(Integer userId) {
        return Long.toHexString(Double.doubleToLongBits(Math.random()));
    }

    public Integer getUserId() {
        return userId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public Date getCreatedDate() {
        return (Date)createdDate.clone();
    }

    @Override
    public int hashCode() {
        return userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UserSession)) {
            return false;
        }
        return ((UserSession) obj).getUserId().equals(this.getUserId());
    }

    @Override
    public String toString() {
        return userId.toString();
    }
}
