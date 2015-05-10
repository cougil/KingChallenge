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

    public Collection<UserSession> values() {
        return sessionUsers.values();
    }

    public UserSession put(String key, UserSession value) {
        return sessionUsers.put(key, value);
    }

    public UserSession remove(Object key) {
        return sessionUsers.remove(key);
    }

    public UserSession get(Object key) {
        return sessionUsers.get(key);
    }

}
