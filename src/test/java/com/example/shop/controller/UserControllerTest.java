package com.example.shop.controller;

import com.example.shop.domain.dao.User;
import com.example.shop.domain.dto.UserDto;
import com.example.shop.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveUser() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserDto.builder()
                                .email("asdasdasd")
                                .firstName("Adam")
                                .lastName("ASeweq")
                                .password("asdqweqweasd")
                                .confirmedPassword("asdqweqweasd")
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("Adam"))
                .andExpect(jsonPath("$.lastName").value("ASeweq"))
                .andExpect(jsonPath("$.email").value("asdasdasd"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.confirmedPassword").doesNotExist());
    }

    @Test
    void shouldNotSaveUserWithDifferentPasswords() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserDto.builder()
                                .email("asdasdasd")
                                .firstName("Adam")
                                .lastName("ASeweq")
                                .password("asdqweqweasd")
                                .confirmedPassword("asdaw")
                                .build())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void shouldNotSaveUserWithoutValidFields() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserDto.builder()
                                .email("")
                                .firstName("")
                                .lastName("")
                                .password("qweasd")
                                .confirmedPassword("qweasd")
                                .build())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void shouldForbiddenWhenUserIsNotLoggedForGettingUserPage() throws Exception {
        mockMvc.perform(get("/api/users")
                        .queryParam("page", "0")
                        .queryParam("size", "10"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser
    void shouldForbiddenWhenUserIsWithoutAdminRole() throws Exception {
        mockMvc.perform(get("/api/users")
                        .queryParam("page", "0")
                        .queryParam("size", "10"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetUserPage() throws Exception {
        userRepository.save(User.builder()
                .firstName("qwe")
                .lastName("asd")
                .email("asdwq")
                .build());
        mockMvc.perform(get("/api/users")
                        .queryParam("page", "0")
                        .queryParam("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    void shouldNotUpdateWhenUserIsNotLogged() throws Exception {
        mockMvc.perform(put("/api/users/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserDto.builder()
                                .firstName("asd")
                                .lastName("zxc")
                                .email("qweasd")
                                .build())))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username = "qweasdzxc")
    void shouldNotUpdateWhenUserHasNotAccessToUser() throws Exception {
        User user = userRepository.save(User.builder()
                .firstName("qwe")
                .lastName("asdweq")
                .email("zxcsadw")
                .build());
        mockMvc.perform(put("/api/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserDto.builder()
                                .firstName("asdqw")
                                .lastName("ascawfg")
                                .email("asdqw")
                                .build())))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username = "asdqwe", roles = "ADMIN")
    void shouldUpdateUser() throws Exception {
        User user = userRepository.save(User.builder()
                .firstName("asd")
                .lastName("qwe")
                .email("qweasd")
                .password("asdqweqw")
                .build());
        mockMvc.perform(put("/api/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserDto.builder()
                                .firstName("qwe")
                                .lastName("asd")
                                .email("asdqwe")
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void shouldNotDeleteUserWithoutLoginUser() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser
    void shouldNotDeleteUserWithoutAccess() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username = "qweasd")
    void shouldDeleteUserWithAccess() throws Exception {
        User user = userRepository.save(User.builder()
                .firstName("asd")
                .lastName("qwe")
                .email("qweasd")
                .password("asdqweqw")
                .build());
        mockMvc.perform(delete("/api/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteUserWithAdminAccess() throws Exception {
        User user = userRepository.save(User.builder()
                .firstName("asd")
                .lastName("qwe")
                .email("qweasd")
                .password("asdqweqw")
                .build());
        mockMvc.perform(delete("/api/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}