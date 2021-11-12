package com.example.shop.service;

import com.example.shop.domain.dao.Template;

public interface TemplateService {
    Template save(Template template);

    void delete(Long id);

    Template update(Template template, Long id);

    Template findTemplateById(Long id);

    Template findByName(String templateName);
}
