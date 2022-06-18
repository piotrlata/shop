package com.example.shop.service;

import com.example.shop.domain.dao.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TemplateService {
    Template save(Template template);

    void delete(Long id);

    Template update(Template template, Long id);

    Template findTemplateById(Long id);

    Template findByName(String templateName);

    Page<Template> getPage(Pageable pageable);
}
