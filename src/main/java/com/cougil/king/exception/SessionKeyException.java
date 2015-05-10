package com.cougil.king.exception;

/**
 * This exception may be thrown trying to get the session key value from the request
 */
public class SessionKeyException extends Exception {
    public SessionKeyException(Throwable cause) {
        super(cause);
    }
}
