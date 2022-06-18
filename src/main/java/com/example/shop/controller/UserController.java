package com.example.shop.controller;

import com.example.shop.domain.dto.UserDto;
import com.example.shop.mapper.UserMapper;
import com.example.shop.service.UserService;
import com.example.shop.validator.group.CreateUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated() && (hasRole('ADMIN') || @securityService.hasAccessToUser(#id))")
    @Operation(description = "get user", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public UserDto userById(@PathVariable Long id) {
        return userMapper.daoToDto(userService.findUserById(id));
    }

    @PostMapping
    @Validated(CreateUser.class)
    @PreAuthorize("isAnonymous()")
    @Operation(description = "save user")
    public UserDto saveUser(@RequestBody @Valid UserDto user) {
        return userMapper.daoToDto(userService.save(userMapper.dtoToDao(user)));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "get user page", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public Page<UserDto> userPage(@RequestParam int page, @RequestParam int size) {
        return userService.getPage(PageRequest.of(page, size)).map(userMapper::daoToDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() && (hasRole('ADMIN') || @securityService.hasAccessToUser(#id))")
    @Operation(description = "delete user", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated() && (hasRole('ADMIN') || @securityService.hasAccessToUser(#id))")
    @Operation(description = "update user", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public UserDto updateUser(@Valid @RequestBody UserDto user, @PathVariable Long id) {
        return userMapper.daoToDto(userService.update(userMapper.dtoToDao(user), id));
    }

    @GetMapping("/current")
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "get current user", security = {@SecurityRequirement(name = "bearer"),
            @SecurityRequirement(name = "basicAuth")})
    public UserDto getCurrentUser() {
        return userMapper.daoToDto(userService.getCurrentUser());
    }
}