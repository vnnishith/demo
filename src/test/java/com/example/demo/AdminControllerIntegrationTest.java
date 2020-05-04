package com.example.demo;

import com.example.demo.pojo.SignupRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testUsersRetrievalWithValidAuthorization()
            throws Exception {
        mvc.perform(get("/v1/admin/list-users").header(HttpHeaders.AUTHORIZATION,
                "Access-Token 2cb9fd19-dc06-46a8-989b-534915602ed6")).andExpect(status().isOk());
    }

    @Test
    public void testUsersRetrievalWithInvalidAuthorization()
            throws Exception {
        mvc.perform(get("/v1/admin/list-users").header(HttpHeaders.AUTHORIZATION,
                "Access-Token 2cb9fd19-dc06-46a8-989b-53491560d6")).andExpect(status().isUnauthorized());
    }


}

