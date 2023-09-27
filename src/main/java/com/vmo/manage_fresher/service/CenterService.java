package com.vmo.manage_fresher.service;

import com.vmo.manage_fresher.entity.Center;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CenterService {
    List<Center> findAll(Pageable pageable);

    Center save(Center center);

    Center findById(Integer centerId);

    void deleteById(Integer centerId);

    void saveAll(List<Center> centerList);
}
