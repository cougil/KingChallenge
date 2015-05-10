package com.cougil.king.exception;

/**
 * Base exception that the application may want to catch
 */
public class BaseException extends Exception {
    public BaseException(Throwable cause) {
        super(cause);
    }
}
