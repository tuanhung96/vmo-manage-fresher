package com.vmo.demo.service;

import com.vmo.demo.entity.Fresher;
import com.vmo.demo.model.response.NumberOfFresherEachScoreRange;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FresherService {
    List<Fresher> findAll(Pageable pageable);
    List<Fresher> findAll();

    Fresher findById(Integer fresherId);

    long countFresher();

    Fresher save(Fresher fresher);

    void deleteById(Integer id);

    List<Fresher> findByName(String name, Pageable pageable);

    List<Fresher> findByProgrammingLanguage(String programmingLanguage, Pageable pageable);

    List<Fresher> findByEmail(String email, Pageable pageable);

    Fresher findByEmail(String email);

    int countFresherByCenterId(Integer id);

    List<NumberOfFresherEachScoreRange> getNumberOfFresherEachScoreRange();

}
