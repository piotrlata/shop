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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    void shouldForbiddenWhenDeleteProduct() throws Exception {
        mockMvc.perform(delete("/api/baskets/1"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username = "qweasd")
    void shouldGetProducts() throws Exception {
        User user = userRepository.save(User.builder()
                .firstName("asd")
                .lastName("qwe")
                .email("qweasd")
                .password("asdqweqw")
                .build());
        Product product = productRepository.save(Product.builder()
                .quantity(2)
                .build());
        Basket basket = basketRepository.save(Basket.builder()
                .user(user)
                .product(product)
                .quantity(1)
                .build());
        mockMvc.perform(get("/api/baskets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldNotGetProductsWithoutLoggedUser() throws Exception {
        mockMvc.perform(get("/api/baskets"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser
    void shouldNotGetProductsWithoutCurrentLoggedUser() throws Exception {
        mockMvc.perform(get("/api/baskets"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username = "qweasd", roles = "ADMIN")
    void shouldAddProduct() throws Exception {
        User user = userRepository.save(User.builder()
                .firstName("asd")
                .lastName("qwe")
                .email("qweasd")
                .password("asdqweqw")
                .build());
        Product product = productRepository.save(Product.builder()
                .quantity(10)
                .build());
        basketRepository.save(Basket.builder()
                .user(user)
                .product(product)
                .quantity(4)
                .build());
        mockMvc.perform(post("/api/baskets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(ProductDto.builder()
                                .id(product.getId())
                                .quantity(4)
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void shouldNotAddProductWithoutLoggedUser() throws Exception {
        mockMvc.perform(post("/api/baskets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(ProductDto.builder()
                                .id(1L)
                                .quantity(4)
                                .build())))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username = "qweasd")
    void shouldNotAddProductWithToHighQuantity() throws Exception {
        User user = userRepository.save(User.builder()
                .firstName("asd")
                .lastName("qwe")
                .email("qweasd")
                .password("asdqweqw")
                .build());
        Product product = productRepository.save(Product.builder()
                .quantity(10)
                .build());
        basketRepository.save(Basket.builder()
                .user(user)
                .product(product)
                .quantity(4)
                .build());
        mockMvc.perform(post("/api/baskets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(ProductDto.builder()
                                .id(product.getId())
                                .quantity(22)
                                .build())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username = "qweasd")
    void shouldDeleteAllProductsFromBasket() throws Exception {
        User user = userRepository.save(User.builder()
                .firstName("asd")
                .lastName("qwe")
                .email("qweasd")
                .password("asdqwe")
                .build());
        Product product = productRepository.save(Product.builder()
                .quantity(10)
                .build());
        basketRepository.save(Basket.builder()
                .user(user)
                .product(product)
                .quantity(4)
                .build());
        mockMvc.perform(delete("/api/baskets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void shouldNotDeleteProductsFromBasketWithoutLoggedUser() throws Exception {
        mockMvc.perform(delete("/api/baskets"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
