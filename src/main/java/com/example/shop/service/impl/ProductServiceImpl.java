package com.example.shop.service.impl;

import com.example.shop.config.properties.FilePropertiesConfig;
import com.example.shop.domain.dao.Product;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final FilePropertiesConfig filePropertiesConfig;

    @Override
    @Transactional
    public Product save(Product product, MultipartFile image) {
        productRepository.save(product);
        Path path = Paths.get(filePropertiesConfig.getProduct(), product.getId() + "." + FilenameUtils.getExtension(image.getOriginalFilename()));
        try {
            Files.copy(image.getInputStream(), path);
            product.setFilePath(path.toString());
        } catch (IOException e) {
            log.error("couldn't save image file", e);
        }
        return product;
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Product update(Product product, MultipartFile image, Long id) {
        var productDb = findProductById(id);
        productDb.setName(product.getName());
        productDb.setPrice(product.getPrice());
        productDb.setDescription(product.getDescription());
        productDb.setQuantity(product.getQuantity());
        Path path = Paths.get(filePropertiesConfig.getProduct(), product.getId() + "." + FilenameUtils.getExtension(image.getOriginalFilename()));
        try {
            Files.copy(image.getInputStream(), path);
            product.setFilePath(path.toString());
        } catch (IOException e) {
            log.error("couldn't save image file", e);
        }
        return productDb;
    }

    @Override
    public Product findProductById(Long id) {
        log.info("object with id {} not in cache", id);
        return productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Page<Product> getPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
