package com.vmo.demo.service.impl;

import com.vmo.demo.dao.CenterRepository;
import com.vmo.demo.entity.Center;
import com.vmo.demo.exception.CenterNotFoundException;
import com.vmo.demo.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CenterServiceImpl implements CenterService {
    private CenterRepository centerRepository;

    @Autowired
    public CenterServiceImpl(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    @Override
    @Transactional
    public List<Center> findAll(Pageable pageable) {
        return centerRepository.findAll(pageable).getContent();
    }

    @Override
    @Transactional
    public Center save(Center center) {
        return centerRepository.save(center);
    }

    @Override
    @Transactional
    public Center findById(Integer centerId) {
        Optional<Center> result = centerRepository.findById(centerId);
        Center center = null;
        if (result.isPresent()) {
            center = result.get();
        } else {
            throw new CenterNotFoundException("Did not find center id - " + centerId);
        }
        return center;
    }

    @Override
    @Transactional
    public void deleteById(Integer centerId) {
        centerRepository.deleteById(centerId);
    }

    @Override
    @Transactional
    public void saveAll(List<Center> centerList) {
        centerRepository.saveAll(centerList);
    }
}
