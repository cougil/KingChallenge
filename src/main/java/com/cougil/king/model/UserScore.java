package com.cougil.king.model;

/**
 * This object holds the relationship between the userId and the score the user has submitted
 */
public class UserScore implements Comparable<UserScore> {

    private final Integer score;
    private final Integer userId;

    public UserScore(Integer score, Integer userId) {
        this.score = score;
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getScore() {
        return score;
    }

    @Override
    public int hashCode() {
        return userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (!(obj instanceof UserScore)) {
            return false;
        }
        UserScore userScore = (UserScore) obj;
        return (userScore.getUserId().equals(this.getUserId()));
    }

    @Override
    public String toString() {
        return "["+userId.toString()+"] - "+score.toString();
    }

    @Override
    public int compareTo(UserScore obj) {
        if (obj.getScore().equals(this.getScore())) return 0;
        else if (obj.getScore() > this.getScore()) return -1;
        else return 1;
    }
}
