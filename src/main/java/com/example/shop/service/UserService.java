package com.example.shop.service;

import com.example.shop.domain.dao.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User save(User user);

    void delete(Long id);

    User update(User user, Long id);

    User findUserById(Long id);

    Page<User> getPage(Pageable pageable);

    User getCurrentUser();
}