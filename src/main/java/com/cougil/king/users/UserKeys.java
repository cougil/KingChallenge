package com.cougil.king.users;

public class UserKeys {

    public static String nextSessionId(Integer userId) {
        return Long.toHexString(Double.doubleToLongBits(Math.random()));
    }
}
