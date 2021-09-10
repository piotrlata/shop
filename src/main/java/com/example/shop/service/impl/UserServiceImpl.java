package com.example.shop.service.impl;

import com.example.shop.domain.dao.User;
import com.example.shop.repository.RoleRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.security.SecurityUtils;
import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        roleRepository.findByName("ROLE_USER").ifPresent(role -> user.setRoles(Collections.singletonList(role)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User update(User user, Long id) {
        User userDb = findUserById(id);
        userDb.setEmail(user.getEmail());
        userDb.setFirstName(user.getFirstName());
        userDb.setLastName(user.getLastName());
        return userDb;
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Page<User> getPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findByEmail(SecurityUtils.getCurrentUserEmail())
                .orElseThrow(() -> new EntityNotFoundException("user not logged"));
    }
}
