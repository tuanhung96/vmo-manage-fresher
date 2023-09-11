package com.vmo.manage_fresher.dao;

import com.vmo.manage_fresher.entity.Fresher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FresherRepository extends JpaRepository<Fresher, Integer> {
    @Query(value = "select * from fresher where name ilike %?1%", nativeQuery = true)
    List<Fresher> findByName(String name, Pageable pageable);

    @Query(value = "select * from fresher where programming_language ilike %?1%", nativeQuery = true)
    List<Fresher> findByProgrammingLanguage(String programmingLanguage, Pageable pageable);

    @Query(value = "select * from fresher where email ilike %?1%", nativeQuery = true)
    List<Fresher> findByEmail(String email, Pageable pageable);

    @Query(value = "select count(*) from fresher where center_id = ?1", nativeQuery = true)
    int countFresherByCenterId(Integer id);
}
