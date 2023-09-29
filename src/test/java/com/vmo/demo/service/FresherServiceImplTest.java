package com.vmo.demo.service;

import com.vmo.demo.dao.FresherRepository;
import com.vmo.demo.entity.Fresher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FresherServiceImplTest {

    @Autowired
    private FresherRepository fresherRepository;
    @Autowired
    private FresherService fresherService;

    @Test
    void save() {
        Fresher fresher = new Fresher("Huong", "huong@funix.edu.vn", "Java", 9F, 9.0F, 9.5F);
        fresher.setDate(null, null, Instant.now(),Instant.now());
        fresherService.save(fresher);

        assertEquals("huong@funix.edu.vn", fresherRepository.findByEmail("huong@funix.edu.vn").getEmail());
    }

    @Test
    void findAll() {

    }
}
