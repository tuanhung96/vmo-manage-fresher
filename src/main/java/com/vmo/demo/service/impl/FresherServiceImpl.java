package com.vmo.demo.service.impl;

import com.vmo.demo.dao.FresherRepository;
import com.vmo.demo.dao.YourRepository;
import com.vmo.demo.entity.Fresher;
import com.vmo.demo.exception.FresherNotFoundException;
import com.vmo.demo.model.response.NumberOfFresherEachScoreRange;
import com.vmo.demo.service.FresherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FresherServiceImpl implements FresherService {

    private FresherRepository fresherRepository;
    private YourRepository yourRepository;

    @Autowired
    public FresherServiceImpl(FresherRepository fresherRepository, YourRepository yourRepository) {
        this.fresherRepository = fresherRepository;
        this.yourRepository = yourRepository;
    }

    @Override
    @Transactional
    public List<Fresher> findAll(Pageable pageable) {
        return fresherRepository.findAll(pageable).getContent();
    }

    @Override
    @Transactional
    public List<Fresher> findAll() {
        return fresherRepository.findAll();
    }

    @Override
    @Transactional
    public Fresher findById(Integer id) {
        Optional<Fresher> result = fresherRepository.findById(id);
        Fresher fresher = null;

        if (result.isPresent()) {
            fresher = result.get();
        } else {
            throw new FresherNotFoundException("Did not find fresher id - " + id);
        }
        return fresher;
    }

    @Override
    @Transactional
    public long countFresher() {
        return fresherRepository.count();
    }

    @Override
    @Transactional
    public Fresher save(Fresher fresher) {
        return fresherRepository.save(fresher);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        fresherRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Fresher> findByName(String name, Pageable pageable) {
        return fresherRepository.findByName(name, pageable);
    }

    @Override
    @Transactional
    public List<Fresher> findByProgrammingLanguage(String programmingLanguage, Pageable pageable) {
        return fresherRepository.findByProgrammingLanguage(programmingLanguage, pageable);
    }

    @Override
    @Transactional
    public List<Fresher> findByEmail(String email, Pageable pageable) {
        return fresherRepository.findByEmail(email, pageable);
    }

    @Override
    @Transactional
    public int countFresherByCenterId(Integer id) {
        return fresherRepository.countFresherByCenterId(id);
    }

    @Override
    @Transactional
    public List<NumberOfFresherEachScoreRange> getNumberOfFresherEachScoreRange() {
        return yourRepository.getNumberOfFresherEachScoreRange();
    }

}
