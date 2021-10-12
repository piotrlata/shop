package com.example.shop.domain.dao;

import com.example.shop.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OrderStatus orderStatus;
    @CreatedDate
    private LocalDateTime orderDate;
    private LocalDateTime dispatchDate;
    @ManyToOne
    private User user;
}