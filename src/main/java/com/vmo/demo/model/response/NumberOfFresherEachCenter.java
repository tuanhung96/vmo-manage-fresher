package com.vmo.demo.model.response;

public class NumberOfFresherEachCenter {
    private Integer centerId;
    private String centerName;
    private Integer numberOfFresher;

    public NumberOfFresherEachCenter() {
    }

    public NumberOfFresherEachCenter(Integer centerId, String centerName, Integer numberOfFresher) {
        this.centerId = centerId;
        this.centerName = centerName;
        this.numberOfFresher = numberOfFresher;
    }

    public Integer getCenterId() {
        return centerId;
    }

    public void setCenterId(Integer centerId) {
        this.centerId = centerId;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public Integer getNumberOfFresher() {
        return numberOfFresher;
    }

    public void setNumberOfFresher(Integer numberOfFresher) {
        this.numberOfFresher = numberOfFresher;
    }
}
