package com.vmo.manage_fresher.service;

import com.vmo.manage_fresher.dao.FresherRepository;
import com.vmo.manage_fresher.entity.Fresher;
import com.vmo.manage_fresher.exception.FresherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FresherServiceImpl implements FresherService {

    private FresherRepository fresherRepository;

    @Autowired
    public FresherServiceImpl(FresherRepository fresherRepository) {
        this.fresherRepository = fresherRepository;
    }

    @Override
    @Transactional
    public List<Fresher> findAll(Pageable pageable) {
        return fresherRepository.findAll(pageable).getContent();
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
}
