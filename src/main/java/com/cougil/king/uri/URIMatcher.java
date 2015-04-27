package com.cougil.king.uri;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URIMatcher {
    final static Pattern loginPattern = Pattern.compile("/\\d+/login", Pattern.CASE_INSENSITIVE);
    final static Pattern userScoreLevelPattern = Pattern.compile("/\\d+/score\\?sessionkey=\\w+", Pattern.CASE_INSENSITIVE);
    final static Pattern highScorePattern = Pattern.compile("/\\d+/highscorelist", Pattern.CASE_INSENSITIVE);

    public static boolean isLogin(String path) {
        Matcher matcher = loginPattern.matcher(path);
        return matcher.matches();
    }

    public static boolean isHighScoreList(String path) {
        Matcher matcher = highScorePattern.matcher(path);
        return matcher.matches();
    }

    public static boolean isUserScoreLevel(String path) {
        Matcher matcher = userScoreLevelPattern.matcher(path);
        return matcher.matches();
    }

}
