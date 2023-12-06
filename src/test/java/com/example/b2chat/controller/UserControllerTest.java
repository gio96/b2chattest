package com.example.b2chat.controller;

import com.example.b2chat.security.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
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

    private final static String jsonAuthentication= "{\"username\":\"john_doe\",\"password\":\"StrongPwd1!\"}";

    //TODO validar errores

    @Test
    void testCreateUser() throws Exception {
        // Given
        String jsonBody = "{\"username\":\"pepito\",\"password\":\"StrongPwd1!\",\"email\":\"pepito@gmail.com\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/auth/generateToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAuthentication))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.token").exists())
                .andDo(result -> {
                    // Obtener el token de la respuesta
                    String token = result.getResponse().getContentAsString();

                    ObjectMapper objectMapper = new ObjectMapper();
                    JwtResponse tokenResponse = objectMapper.readValue(token, JwtResponse.class);

                    // Usar el token en otra solicitud
                    mockMvc.perform(MockMvcRequestBuilders
                                    .post("/v1/users")
                                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getToken())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(jsonBody))
                            .andExpect(status().is2xxSuccessful())
                            .andExpect(jsonPath("$.username").value("pepito"))
                            .andExpect(jsonPath("$.email").value("pepito@gmail.com"));
                });
    }

    @Test
    void testGetUserById() throws Exception {
        // Given

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/auth/generateToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthentication))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.token").exists())
                .andDo(result -> {
                    // Obtener el token de la respuesta
                    String token = result.getResponse().getContentAsString();

                    ObjectMapper objectMapper = new ObjectMapper();
                    JwtResponse tokenResponse = objectMapper.readValue(token, JwtResponse.class);

                    // Usar el token en otra solicitud
                    mockMvc.perform(MockMvcRequestBuilders
                                    .get("/v1/users/{userId}", 1L)
                                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getToken())
                                    .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().is2xxSuccessful())
                            .andExpect(jsonPath("$.username").value("john_doe"))
                            .andExpect(jsonPath("$.email").value("john.doe@example.com"));
                });

    }

    @Test
    void testUpdateUser() throws Exception {
        // Given
        String jsonBody = "{\"username\":\"pepita\",\"password\":\"StrongPwd1!\",\"email\":\"pepita@gmail.com\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/auth/generateToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthentication))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.token").exists())
                .andDo(result -> {
                    // Obtener el token de la respuesta
                    String token = result.getResponse().getContentAsString();

                    ObjectMapper objectMapper = new ObjectMapper();
                    JwtResponse tokenResponse = objectMapper.readValue(token, JwtResponse.class);

                    // Usar el token en otra solicitud
                    mockMvc.perform(MockMvcRequestBuilders
                                    .put("/v1/users/{userId}", 2L)
                                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getToken())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(jsonBody))
                            .andExpect(status().is2xxSuccessful())
                            .andExpect(jsonPath("$.username").value("pepita"))
                            .andExpect(jsonPath("$.email").value("pepita@gmail.com"));
                });

    }

    @Test
    void testDeleteUser() throws Exception {
        // Given
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/auth/generateToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthentication))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.token").exists())
                .andDo(result -> {
                    // Obtener el token de la respuesta
                    String token = result.getResponse().getContentAsString();

                    ObjectMapper objectMapper = new ObjectMapper();
                    JwtResponse tokenResponse = objectMapper.readValue(token, JwtResponse.class);

                    mockMvc.perform(MockMvcRequestBuilders
                                    .delete("/v1/users/{userId}", 3L)
                                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getToken())
                                    .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().is2xxSuccessful());
                });


    }
}
