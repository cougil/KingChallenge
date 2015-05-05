package com.cougil.king.handler;

import com.cougil.king.GameUserSessionScores;
import com.cougil.king.users.UserScore;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

public class HighScoreListHandler extends BaseHandler {

    public HighScoreListHandler(GameUserSessionScores gameUserSessionScores) {
        super(gameUserSessionScores);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        long startTime = System.nanoTime();

        final String PATH = getPath(httpExchange);

        OutputStream os = httpExchange.getResponseBody();

        try {
            final Integer levelId = Integer.parseInt(PATH.substring(1, PATH.indexOf("/highscorelist")));
            System.out.println("HighScoreList handler [" + levelId + " - " + startTime + "] - Received request!");

            Set<UserScore> userScores = gameUserSessionScores.highScoreList(levelId);
            final String response = createCSV(userScores);
            randomSleep();

            httpExchange.sendResponseHeaders(200, response.length());
            os.write(response.getBytes());

            long elapsedTime = (System.nanoTime() - startTime) / 1000000;
            System.out.println("HighScoreList handler ["+ levelId +" - "+startTime+"] - Return response: ["+response+"]. ElapsedTime: "+elapsedTime+" ms");

        } catch (NumberFormatException nfe) {
            replyError(httpExchange, "Invalid levelId with path '" + PATH + "'", os);

        } finally {
            os.close();
        }
    }

    private String createCSV(Set<UserScore> userScores) {
        StringBuffer sb = new StringBuffer();
        for(UserScore userScore : userScores) {
            sb= sb.append(userScore.getScore());
            sb= sb.append("=");
            sb= sb.append(userScore.getUserId());
            sb= sb.append(",");
        }
        return sb.toString();
    }
}
