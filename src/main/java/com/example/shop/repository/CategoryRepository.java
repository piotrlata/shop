package com.example.shop.repository;

import com.example.shop.domain.dao.Category;
import com.example.shop.domain.dao.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, RevisionRepository<Category, Long, Integer> {
}
