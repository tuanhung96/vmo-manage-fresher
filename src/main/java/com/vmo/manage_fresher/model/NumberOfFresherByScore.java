package com.vmo.manage_fresher.model;

public class NumberOfFresherByScore {
    private String scoreRange;
    private int numberOfFresher;

    public NumberOfFresherByScore() {
    }

    public NumberOfFresherByScore(String scoreRange, int numberOfFresher) {
        this.scoreRange = scoreRange;
        this.numberOfFresher = numberOfFresher;
    }

    public String getScoreRange() {
        return scoreRange;
    }

    public void setScoreRange(String scoreRange) {
        this.scoreRange = scoreRange;
    }

    public int getNumberOfFresher() {
        return numberOfFresher;
    }

    public void setNumberOfFresher(int numberOfFresher) {
        this.numberOfFresher = numberOfFresher;
    }
}
