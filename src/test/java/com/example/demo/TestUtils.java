package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {

    /**
     *  Used to convert objects to string to be sent in test cases
     * @param obj
     * @return
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
