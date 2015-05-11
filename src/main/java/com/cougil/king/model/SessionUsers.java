package com.cougil.king.model;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Object responsible of managing the relationship between the session keys linked to each user session.
 * It stores a reference of a {@link java.util.concurrent.ConcurrentHashMap} that saves the session key as
 * a key and {@link com.cougil.king.model.UserSession} as a value.
 */
public class SessionUsers {

    // User sessions: we will save the 'sessionKey' as a key and the user session (@UserSession) as a value
    private ConcurrentHashMap<String, UserSession> sessionUsers;

    public SessionUsers() {
        sessionUsers = new ConcurrentHashMap<String, UserSession>();
    }

    public UserSession put(String sessionKey, UserSession value) {
        return sessionUsers.put(sessionKey, value);
    }

    public UserSession remove(String sessionKey) {
        return sessionUsers.remove(sessionKey);
    }

    public UserSession get(String sessionKey) {
        return sessionUsers.get(sessionKey);
    }

    public Collection<UserSession> values() {
        return sessionUsers.values();
    }

}
