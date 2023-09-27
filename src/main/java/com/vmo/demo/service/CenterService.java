package com.vmo.demo.service;

import com.vmo.demo.entity.Center;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CenterService {
    List<Center> findAll(Pageable pageable);

    Center save(Center center);

    Center findById(Integer centerId);

    void deleteById(Integer centerId);

    void saveAll(List<Center> centerList);
}
