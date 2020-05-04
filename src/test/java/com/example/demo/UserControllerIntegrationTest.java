package com.example.demo;

import com.example.demo.pojo.SignupRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@TestPropertySource(locations = "classpath:application.properties")

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testUserRegistrationWithValidDetails()
            throws Exception {

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUserName("test1234");
        signupRequest.setPassword("test1234");
        signupRequest.setConfirmPassword("test1234");
        signupRequest.setEmailAddress("test1234@test.com");
        signupRequest.setPreferredPhoneNumber("+353-888899999");
        signupRequest.setPhoneName("test1234");
        signupRequest.setPhoneModel("ANDROID");
        mvc.perform(post("/v1/user").content(TestUtils.asJsonString(signupRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(containsString("success")));
    }


    @Test
    public void testUserRegistrationWithInvalidPhoneModel()
            throws Exception {

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUserName("test1234");
        signupRequest.setPassword("test1234");
        signupRequest.setConfirmPassword("test1234");
        signupRequest.setEmailAddress("test1234@test.com");
        signupRequest.setPreferredPhoneNumber("+353-888899999");
        signupRequest.setPhoneName("test1234");
        signupRequest.setPhoneModel("ANDROIDD");
        mvc.perform(post("/v1/user").content(TestUtils.asJsonString(signupRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andExpect(content().string(containsString("Invalid Phone Model")));
    }

    @Test
    public void testUserRegistrationWithExistingDetails()
            throws Exception {

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUserName("test12345");
        signupRequest.setPassword("test12345");
        signupRequest.setConfirmPassword("test12345");
        signupRequest.setEmailAddress("test12345@test.com");
        signupRequest.setPreferredPhoneNumber("+353-998899999");
        signupRequest.setPhoneName("test1234");
        signupRequest.setPhoneModel("ANDROID");
        mvc.perform(post("/v1/user").content(TestUtils.asJsonString(signupRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(containsString("success")));
        // make another request with same details
        mvc.perform(post("/v1/user").content(TestUtils.asJsonString(signupRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void testUserRetrieval()
            throws Exception {
        // retrieve user with seeded data
        mvc.perform(get("/v1/user").header(HttpHeaders.AUTHORIZATION,
                "Basic dGVzdDUzNjY6dGVzdDEyMw==")).andExpect(status().isOk());
    }

    @Test
    public void testUserUpdationWithValidPreferredPhoneNumber()
            throws Exception {
        String seededPreferredPhoneNumber = "+353-9993501495";
        mvc.perform(put("/v1/user/66e012c2-36bb-4569-af0c-c7908cd7da36/preferred-phone-number").param("preferredPhoneNumber", seededPreferredPhoneNumber).header(HttpHeaders.AUTHORIZATION,
                "Basic dGVzdDUzNjY6dGVzdDEyMw==")).andExpect(status().isOk());
    }

    @Test
    public void testUserDeletionWithInvalidUserId()
            throws Exception {
        // delete user with seeded data
        mvc.perform(delete("/v1/user/12-36bb-4569-af0c-c7908cd7da31").header(HttpHeaders.AUTHORIZATION,
                "Basic dGVzdDUzNjY6dGVzdDEyMw==")).andExpect(status().isUnauthorized());
    }


    @Test
    public void testUserDeletion()
            throws Exception {
        // delete user with seeded data
        mvc.perform(delete("/v1/user/66e012c2-36bb-4569-af0c-c7908cd7da36").header(HttpHeaders.AUTHORIZATION,
                "Basic dGVzdDUzNjY6dGVzdDEyMw==")).andExpect(status().isOk());
    }

}

