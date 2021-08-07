package com.example.shop.controller;

import com.example.shop.domain.dao.Basket;
import com.example.shop.domain.dao.Product;
import com.example.shop.domain.dao.User;
import com.example.shop.domain.dto.ProductDto;
import com.example.shop.repository.BasketRepository;
import com.example.shop.repository.ProductRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class BasketControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "qweasd")
    void shouldUpdateBasket() throws Exception {
        User user = userRepository.save(User.builder()
                .firstName("asd")
                .lastName("qwe")
                .email("qweasd")
                .password("asdqweqw")
                .build());
        Product product = productRepository.save(Product.builder()
                .quantity(2)
                .build());
        basketRepository.save(Basket.builder()
                .user(user)
                .product(product)
                .quantity(1)
                .build());
        mockMvc.perform(put("/api/baskets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(ProductDto.builder()
                                .id(product.getId())
                                .quantity(1)
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username = "qweasd")
    void shouldNotUpdateBasketWithoutEnoughQuantity() throws Exception {
        User user = userRepository.save(User.builder()
                .firstName("asd")
                .lastName("qwe")
                .email("qweasd")
                .password("asdqweqw")
                .build());
        Product product = productRepository.save(Product.builder()
                .quantity(2)
                .build());
        basketRepository.save(Basket.builder()
                .user(user)
                .product(product)
                .quantity(1)
                .build());
        mockMvc.perform(put("/api/baskets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(ProductDto.builder()
                                .id(product.getId())
                                .quantity(2)
                                .build())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username = "qweasd")
    void shouldNotUpdateBasketWithoutProduct() throws Exception {
        User user = userRepository.save(User.builder()
                .firstName("asd")
                .lastName("qwe")
                .email("qweasd")
                .password("asdqweqw")
                .build());
        mockMvc.perform(put("/api/baskets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(ProductDto.builder()
                                .id(1L)
                                .quantity(2)
                                .build())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username = "qweasd")
    void shouldDeleteProductFromBasket() throws Exception {
        User user = userRepository.save(User.builder()
                .firstName("asd")
                .lastName("qwe")
                .email("qweasd")
                .password("asdqweqw")
                .build());
        Product product = productRepository.save(Product.builder()
                .quantity(2)
                .build());
        basketRepository.save(Basket.builder()
                .user(user)
                .product(product)
                .quantity(1)
                .build());
        mockMvc.perform(delete("/api/baskets/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
//poprawic
    @Test
    void shouldNotDeleteProduct() throws Exception {
        mockMvc.perform(delete("/api/baskets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(ProductDto.builder()
                                .id(1L)
                                .quantity(1)
                                .build())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username = "qweasd")
    void shouldClearBasket() throws Exception {
        User user = userRepository.save(User.builder()
                .firstName("asd")
                .lastName("qwe")
                .email("qweasd")
                .password("asdqweqw")
                .build());
        Product product = productRepository.save(Product.builder()
                .quantity(2)
                .build());
        basketRepository.save(Basket.builder()
                .user(user)
                .product(product)
                .quantity(1)
                .build());
        mockMvc.perform(delete("/api/baskets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(Basket.builder()
                                .user(user)
                                .product(product)
                                .quantity(1)
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
