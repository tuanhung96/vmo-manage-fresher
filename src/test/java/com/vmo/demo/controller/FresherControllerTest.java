package com.vmo.demo.controller;

import com.vmo.demo.dao.FresherRepository;
import com.vmo.demo.service.FresherService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
class FresherControllerTest {
    private static MockHttpServletRequest request;
    @PersistenceContext
    private EntityManager entityManager;
    @Mock
    private FresherService fresherServiceMock;
    @Autowired
    private FresherRepository fresherRepository;
    @Autowired
    private FresherService fresherService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private MockMvc mockMvc;
    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

    @BeforeAll
    public static void setUp() {
        request = new MockHttpServletRequest();
        request.setParameter("email", "hung@funix.edu.vn");
    }

    @BeforeEach
    public void setUpBeforeEach(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        jdbcTemplate.execute("insert into fresher (id, name, email, programming_language, score_1, score_2, score_3) " +
                "values (1, 'Hung', 'hung@funix.edu.vn', 'Java', 9.0, 9.0, 9.5)");
    }

    @Test
    void findFresherById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/freshers/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @AfterEach
    public void setUpAfter() {
        jdbcTemplate.execute("delete from fresher");
    }
}


