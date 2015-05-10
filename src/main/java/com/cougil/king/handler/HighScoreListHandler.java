package com.cougil.king.handler;

import com.cougil.king.model.UserScore;
import com.cougil.king.service.GameService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

/**
 * Handler responsible of managing the requests asking for the high score list for a specific level.
 * <p>
 * It returns a comma separated list of values in descending score order. Only the highest score for each user
 * counts and if the user hasn't submitted a score for the level, no score is presented for that user. Also
 * a request for a high score list of a level without any scores submitted, will return an empty string.
 * <p>
 * Format: <code>GET /&lt;levelid&gt;/highscorelist</code>
 * <p>
 * <code>Example: <br>
 * Request: GET /42/highscorelist<br>
 * Response: 1000=12345,230=800,222=750,10=600
 * </code>
 */
public class HighScoreListHandler extends BaseHandler {

    public HighScoreListHandler(GameService gameService) {
        super(gameService);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        long startTime = System.nanoTime();

        final String PATH = getPath(httpExchange);
        OutputStream os = httpExchange.getResponseBody();

        try {
            final Integer levelId = Integer.parseInt(PATH.substring(1, PATH.indexOf("/highscorelist")));
            System.out.println("HighScoreList handler [" + levelId + " - " + startTime + "] - Received request!");

            Set<UserScore> userScores = gameService.getHighScoreList(levelId);
            final String response = createCSV(userScores);

            System.out.println("HighScoreList return - [" + response + "]");

            httpExchange.sendResponseHeaders(200, response.length());
            os.write(response.getBytes());

            long elapsedTime = (System.nanoTime() - startTime) / 1000000;
            System.out.println("HighScoreList handler ["+ levelId +" - "+startTime+"] - Return response: ["+response+"]. ElapsedTime: "+elapsedTime+" ms");

        } catch (NumberFormatException nfe) {
            replyError(httpExchange, "Invalid levelId with path '" + PATH + "'", httpExchange.getResponseBody());
        } catch (Exception e) {
            e.printStackTrace();
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
        if (sb.length() > 0) sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
