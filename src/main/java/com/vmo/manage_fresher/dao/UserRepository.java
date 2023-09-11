package com.vmo.manage_fresher.dao;

import com.vmo.manage_fresher.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.email = ?1")
    User findByEmail(String email);
}
