package org.example;

import org.example.repos.LoginRepo;
import org.example.repos.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LogLoaderTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LoginRepo loginRepo;

    @BeforeEach
    public void beforeSetup() {
        userRepo.deleteAll();
        loginRepo.deleteAll();
    }

    @Test
    void load() throws Exception {
        mockMvc.perform(post("/loaders/log_load"))
                .andExpect(status().isOk());

        Assertions.assertEquals(1, userRepo.count());
        Assertions.assertEquals(1, loginRepo.count());
    }
}
