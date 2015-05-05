package com.cougil.king.users;


import java.util.Date;

public class UserSession {
    private final Integer userId;
    private final String sessionKey;
    private final Date createdDate;

    public UserSession(Integer userId, String sessionKey) {
        this.userId = userId;
        this.sessionKey = sessionKey;
        this.createdDate = new Date();
    }

    public Integer getUserId() {
        return userId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public Date getCreatedDate() {
        return createdDate;
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
