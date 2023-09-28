package com.vmo.demo.model.response;

import java.time.LocalDate;

public class JoinDateOfFresherWithLocalDateType {
    private LocalDate joinDate;
    private LocalDate graduateDate;

    public JoinDateOfFresherWithLocalDateType(LocalDate joinDate, LocalDate graduateDate) {
        this.joinDate = joinDate;
        this.graduateDate = graduateDate;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public LocalDate getGraduateDate() {
        return graduateDate;
    }

    public void setGraduateDate(LocalDate graduateDate) {
        this.graduateDate = graduateDate;
    }
}
