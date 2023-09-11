package com.vmo.manage_fresher.dao;

import com.vmo.manage_fresher.entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CenterRepository extends JpaRepository<Center, Integer> {
}
