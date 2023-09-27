package com.vmo.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "fresher")
public class Fresher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "name must not be blank")
    @Column(name = "name")
    private String name;

    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}", message = "email is invalid")
    @Column(name = "email")
    private String email;

    @Column(name = "programming_language")
    private String programmingLanguage;

    @Min(value=0, message = "Score must be greater than or equal to zero")
    @Max(value=10, message = "Score must be less than or equal to ten")
    @Column(name = "score_1")
    private Float score1;

    @Min(value=0, message = "Score must be greater than or equal to zero")
    @Max(value=10, message = "Score must be less than or equal to ten")
    @Column(name = "score_2")
    private Float score2;

    @Min(value=0, message = "Score must be greater than or equal to zero")
    @Max(value=10, message = "Score must be less than or equal to ten")
    @Column(name = "score_3")
    private Float score3;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "center_id")
    private Center center;

    public Fresher() {
    }

    public Fresher(String name, String email, String programmingLanguage, Float score1, Float score2, Float score3) {
        this.name = name;
        this.email = email;
        this.programmingLanguage = programmingLanguage;
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }
}
