package com.example.shop.controller;

import com.example.shop.domain.dto.TemplateDto;
import com.example.shop.mapper.TemplateMapper;
import com.example.shop.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class TemplateController {
    private final TemplateService templateService;
    private final TemplateMapper templateMapper;

    @PostMapping
    public TemplateDto saveTemplate(@RequestBody TemplateDto template) {
        return templateMapper.daoToDto(templateService.save(templateMapper.dtoToDao(template)));
    }

    @DeleteMapping("/{id}")
    public void deleteTemplate(@PathVariable Long id) {
        templateService.delete(id);
    }

    @PutMapping("/{id}")
    public TemplateDto updateTemplate(@RequestBody TemplateDto template, @PathVariable Long id) {
        return templateMapper.daoToDto(templateService.update(templateMapper.dtoToDao(template), id));
    }

    @GetMapping("/{id}")
    public TemplateDto templateById(@PathVariable Long id) {
        return templateMapper.daoToDto(templateService.findTemplateById(id));
    }
}
