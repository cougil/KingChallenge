package com.cougil.king.handler;

import com.cougil.king.exception.ScoreException;
import com.cougil.king.exception.SessionKeyException;
import com.cougil.king.users.SessionUsers;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.URLDecoder;

public class UserScoreLevelHandler extends BaseHandler {

    public UserScoreLevelHandler(SessionUsers sessionUsers) {
        super(sessionUsers);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        long startTime = System.nanoTime();

        final String PATH = getPath(httpExchange);
        final String QUERY = getQuery(httpExchange);

        OutputStream os = httpExchange.getResponseBody();

        try {
            final Integer levelId = Integer.parseInt(PATH.substring(1, PATH.indexOf("/score")));
            final Integer score = getScore(httpExchange);
            final String sessionKey = getSessionKey(QUERY);

            System.out.println("User score handler [" + levelId + "/" + score + " - " + startTime + "] - Received request!");

            sessionUsers.score(sessionKey, score, levelId);
            randomSleep();

            httpExchange.sendResponseHeaders(200, sessionKey.length());
            os.write(sessionKey.getBytes());

            long elapsedTime = (System.nanoTime() - startTime) / 1000000;
            System.out.println("User score handler ["+ levelId +"/"+ score + " - "+startTime+"] - Return response. ElapsedTime: "+elapsedTime+" ms");

        } catch (NumberFormatException nfe) {
            replyError(httpExchange, "Invalid levelId with path '" + PATH + "'", os);

        } catch (SessionKeyException ske) {
            replyError(httpExchange, "Error retrieving sessionKey with query '" + QUERY + "'", os);

        } catch (ScoreException se) {
            replyError(httpExchange, "Error retrieving score with path '" + PATH + "'", os);

        } finally {
            os.close();
        }

    }

    private String getSessionKey(String query) throws SessionKeyException {
        String sessionKey = null;
        String[] pairs = query.split("&");
        try {
            for (String pair : pairs) {
                int index = pair.indexOf("=");
                String field = URLDecoder.decode(pair.substring(0, index), "UTF-8");
                if (field.equalsIgnoreCase("sessionkey")) {
                    sessionKey = URLDecoder.decode(pair.substring(index + 1), "UTF-8");
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new SessionKeyException(e.getCause());
        }
        return sessionKey;
    }

    private Integer getScore(HttpExchange httpExchange) throws ScoreException {
        Integer score = null;
        if ((httpExchange.getRequestMethod().equalsIgnoreCase("POST"))) {
            InputStream is = httpExchange.getRequestBody();
            BufferedReader reader = new BufferedReader( new InputStreamReader(is) );
            try {
                String value = reader.readLine();
                score = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new ScoreException(e.getCause());

            } catch (IOException e) {
                throw new ScoreException(e.getCause());

            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new ScoreException(e.getCause());
                }
            }
        }
        return score;
    }
}
