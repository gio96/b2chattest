package com.example.b2chat.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //TODO validar errores

    @Test
    void testCreateUser() throws Exception {
        // Given
        String jsonBody = "{\"username\":\"pepito\",\"password\":\"StrongPwd1!\",\"email\":\"pepito@gmail.com\"}";

        // When
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody));

        // Then
        result.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.username").value("pepito"))
                .andExpect(jsonPath("$.email").value("pepito@gmail.com"));
    }

    @Test
    void testGetUserById() throws Exception {
        // Given

        // When
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/users/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void testUpdateUser() throws Exception {
        // Given
        String jsonBody = "{\"username\":\"pepita\",\"password\":\"StrongPwd1!\",\"email\":\"pepita@gmail.com\"}";

        // When
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/users/{userId}", 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody));

        // Then
        result.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.username").value("pepita"))
                .andExpect(jsonPath("$.email").value("pepita@gmail.com"));
    }

    @Test
    void testDeleteUser() throws Exception {
        // Given

        // When
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/users/{userId}", 3L)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andExpect(status().is2xxSuccessful());
    }
}
