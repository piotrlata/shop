package com.example.shop.controller;

import com.example.shop.domain.dao.Product;
import com.example.shop.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteProduct() throws Exception {
        Product product = productRepository.save(Product.builder()
                .quantity(10)
                .price(12.00)
                .build());
        mockMvc.perform(delete("/api/products/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void shouldNotDeleteProductWithoutAdminRole() throws Exception {
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotDeleteProductWithoutProduct() throws Exception {
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
