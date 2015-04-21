package com.cougil.king.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URIMatcher {
    final static Pattern loginPattern = Pattern.compile("/\\d+/login");
    final static Pattern userScoreLevelPattern = Pattern.compile("");
    final static Pattern highScorePattern = Pattern.compile("");

    public boolean isLogin(String  path) {
        Matcher matcher = loginPattern.matcher(path);
        return matcher.matches();
        //return path.endsWith("/login");
    }

    public boolean isHighScoreList(String path) {
        Matcher matcher = highScorePattern.matcher(path);
        return matcher.matches();
        //return path.endsWith("/highscorelist");
    }

    public boolean isUserScoreLevel(String path) {
        Matcher matcher = userScoreLevelPattern.matcher(path);
        return matcher.matches();
    }

}
