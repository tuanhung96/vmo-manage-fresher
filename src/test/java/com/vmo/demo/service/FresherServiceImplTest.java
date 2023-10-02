package com.vmo.demo.service;

import com.vmo.demo.dao.FresherRepository;
import com.vmo.demo.entity.Fresher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FresherServiceImplTest {
    @Mock
    private FresherRepository fresherRepositoryMock;
    @Autowired
    private FresherRepository fresherRepository;
    @Autowired
    private FresherService fresherService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUpDatabase() {
        jdbcTemplate.execute("insert into fresher (id, name, email, programming_language, score_1, score_2, score_3) " +
                "values (1, 'Hung', 'hung@funix.edu.vn', 'Java', 9.0, 9.0, 9.5)");
    }

    @Test
    void save() {
        Fresher fresher = new Fresher("Huong", "huong@funix.edu.vn", "Java", 9F, 9.0F, 9.5F);
        fresher.setDate(null, null, Instant.now(),Instant.now());
        fresherService.save(fresher);

        assertEquals("huong@funix.edu.vn", fresherRepository.findByEmail("huong@funix.edu.vn").getEmail());
    }

    @Test
    void deleteById() {
        Optional<Fresher> fresher = fresherRepository.findById(1);
        assertTrue(fresher.isPresent());
        fresherService.deleteById(1);
        fresher = fresherRepository.findById(1);
        assertFalse(fresher.isPresent());
    }

    @Test
    void findAll() {
        List<Fresher> fresherList = fresherService.findAll();
        assertEquals(1, fresherList.size());
    }

    @Test
    void count() {
        assertEquals(1, fresherService.countFresher());
    }

    @Test
    void findById() {
        assertEquals("hung@funix.edu.vn", fresherService.findById(1).getEmail());
    }

    @AfterEach
    public void setUpAfter() {
        jdbcTemplate.execute("delete from fresher");
    }
}
