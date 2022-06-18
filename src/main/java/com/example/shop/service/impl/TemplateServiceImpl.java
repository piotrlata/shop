package com.example.shop.service.impl;

import com.example.shop.domain.dao.Template;
import com.example.shop.repository.TemplateRepository;
import com.example.shop.service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;

    @Override
    public Template save(Template template) {
        return templateRepository.save(template);
    }

    @Override
    public void delete(Long id) {
        templateRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Template update(Template template, Long id) {
        var templateDb = findTemplateById(id);
        templateDb.setName(template.getName());
        templateDb.setBody(template.getBody());
        templateDb.setSubject(template.getSubject());
        return templateDb;
    }

    @Override
    public Template findTemplateById(Long id) {
        return templateRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Template findByName(String templateName) {
        return templateRepository.findByName(templateName).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Page<Template> getPage(Pageable pageable) {
        return templateRepository.findAll(pageable);
    }


}
