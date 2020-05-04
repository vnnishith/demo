package com.example.demo;

import com.example.demo.pojo.PhoneRequest;
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
public class PhoneControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testNewPhoneAdditionWithValidDetails()
            throws Exception {

        PhoneRequest phoneRequest = new PhoneRequest();
        phoneRequest.setPhoneNumber("+353-388899999");
        phoneRequest.setPhoneName("test1234");
        phoneRequest.setPhoneModel("ANDROID");
        mvc.perform(post("/v1/user/fa7ee011-2c08-478c-9313-254549fd2640/phone").content(TestUtils.asJsonString(phoneRequest))
                .contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION,"Basic dGVzdDUzNjp0ZXN0MTIz")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(containsString("success")));
    }

    @Test
    public void testUserPhoneDeletionWithInvalidPhoneNumber()
            throws Exception {
        String phoneNumber = "+353-088899999";
        mvc.perform(delete("/v1/user/fa7ee011-2c08-478c-9313-254549fd2640/phone").param("phoneNumber",phoneNumber)
                .contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION,"Basic dGVzdDUzNjp0ZXN0MTIz")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void testUserPhoneDeletion()
            throws Exception {
        PhoneRequest phoneRequest = new PhoneRequest();
        phoneRequest.setPhoneNumber("+353-588899999");
        phoneRequest.setPhoneName("test1234samp");
        phoneRequest.setPhoneModel("ANDROID");
        mvc.perform(post("/v1/user/fa7ee011-2c08-478c-9313-254549fd2640/phone").content(TestUtils.asJsonString(phoneRequest))
                .contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION,"Basic dGVzdDUzNjp0ZXN0MTIz")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(containsString("success")));
        mvc.perform(delete("/v1/user/fa7ee011-2c08-478c-9313-254549fd2640/phone").param("phoneNumber","+353-588899999")
                .contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION,"Basic dGVzdDUzNjp0ZXN0MTIz")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(containsString("Sucessfully Removed")));
    }
}

