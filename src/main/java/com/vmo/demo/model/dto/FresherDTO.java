package com.vmo.demo.model.dto;

import com.vmo.demo.entity.Fresher;

public class FresherDTO {
    private String name;
    private String email;
    private String programmingLanguage;
    private Float score1;
    private Float score2;
    private Float score3;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public Float getScore1() {
        return score1;
    }

    public void setScore1(Float score1) {
        this.score1 = score1;
    }

    public Float getScore2() {
        return score2;
    }

    public void setScore2(Float score2) {
        this.score2 = score2;
    }

    public Float getScore3() {
        return score3;
    }

    public void setScore3(Float score3) {
        this.score3 = score3;
    }

    public Fresher convertToFresher() {
        return new Fresher(name, email, programmingLanguage, score1, score2, score3);
    }
}
