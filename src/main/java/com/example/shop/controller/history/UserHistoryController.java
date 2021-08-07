package com.example.shop.controller.history;

import com.example.shop.domain.dto.UserDto;
import com.example.shop.mapper.UserRevisionMapper;
import com.example.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/history/users")
@RequiredArgsConstructor
public class UserHistoryController {
    private final UserRepository userRepository;
    private final UserRevisionMapper userRevisionMapper;

    @GetMapping("/{id}")
    public Page<UserDto> getUserHistory(@PathVariable Long id, @RequestParam int page, @RequestParam int size) {
        return userRepository.findRevisions(id, PageRequest.of(page, size)).map(userRevisionMapper::daoToDto);
    }
}