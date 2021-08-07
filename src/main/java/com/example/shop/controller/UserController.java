package com.example.shop.controller;

import com.example.shop.domain.dto.UserDto;
import com.example.shop.mapper.UserMapper;
import com.example.shop.service.UserService;
import com.example.shop.validator.group.CreateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/users")
@RequiredArgsConstructor
@RestController
@Validated
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    // /api/users/id
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated() && (hasRole('ADMIN') || @securityService.hasAccessToUser(#id))")
    public UserDto userById(@PathVariable Long id) {
        return userMapper.daoToDto(userService.findUserById(id));
    }

    @Validated(CreateUser.class)
    @PostMapping
    @PreAuthorize("isAnonymous()")
    public UserDto saveUser(@RequestBody @Valid UserDto user) {
        return userMapper.daoToDto(userService.save(userMapper.dtoToDao(user)));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserDto> userPage(@RequestParam int page, @RequestParam int size) {
        return userService.getPage(PageRequest.of(page, size)).map(userMapper::daoToDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() && (hasRole('ADMIN') || @securityService.hasAccessToUser(#id))")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated() && (hasRole('ADMIN') || @securityService.hasAccessToUser(#id))")
    public UserDto updateUser(@Valid @RequestBody UserDto user, @PathVariable Long id) {
        return userMapper.daoToDto(userService.update(userMapper.dtoToDao(user), id));
    }
}