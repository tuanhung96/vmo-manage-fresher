package com.vmo.manage_fresher.entity;

import javax.persistence.*;

@Entity
@Table(name = "fresher")
public class Fresher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "programming_language")
    private String programmingLanguage;

    @Column(name = "score_1")
    private Float score_1;

    @Column(name = "score_2")
    private Float score_2;

    @Column(name = "score_3")
    private Float score_3;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "center_id")
    private Center center;

    public Fresher() {
    }

    public Fresher(String name, String email, String programmingLanguage, Float score_1, Float score_2, Float score_3) {
        this.name = name;
        this.email = email;
        this.programmingLanguage = programmingLanguage;
        this.score_1 = score_1;
        this.score_2 = score_2;
        this.score_3 = score_3;
    }

    public Fresher(String name, String email, String programmingLanguage, Float score_1, Float score_2, Float score_3, Center center) {
        this.name = name;
        this.email = email;
        this.programmingLanguage = programmingLanguage;
        this.score_1 = score_1;
        this.score_2 = score_2;
        this.score_3 = score_3;
        this.center = center;
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

    public Float getScore_1() {
        return score_1;
    }

    public void setScore_1(Float score_1) {
        this.score_1 = score_1;
    }

    public Float getScore_2() {
        return score_2;
    }

    public void setScore_2(Float score_2) {
        this.score_2 = score_2;
    }

    public Float getScore_3() {
        return score_3;
    }

    public void setScore_3(Float score_3) {
        this.score_3 = score_3;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }
}
