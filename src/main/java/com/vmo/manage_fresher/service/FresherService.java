package com.vmo.manage_fresher.service;

import com.vmo.manage_fresher.entity.Fresher;
import com.vmo.manage_fresher.model.response.NumberOfFresherEachScoreRange;
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

    int countFresherByCenterId(Integer id);

    List<NumberOfFresherEachScoreRange> getNumberOfFresherEachScoreRange();

}
