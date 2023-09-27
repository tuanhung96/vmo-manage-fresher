package com.vmo.manage_fresher.model.response;

public class NumberOfFresherEachScoreRange {
    private int score;
    private String scoreRange;

    public NumberOfFresherEachScoreRange() {
    }

    public NumberOfFresherEachScoreRange(int score, String scoreRange) {
        this.score = score;
        this.scoreRange = scoreRange;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getScoreRange() {
        return scoreRange;
    }

    public void setScoreRange(String scoreRange) {
        this.scoreRange = scoreRange;
    }
}
