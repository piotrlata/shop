package com.example.shop.generator.strategy.impl;

import com.example.shop.domain.dao.Product;
import com.example.shop.generator.model.FileType;
import com.example.shop.generator.strategy.GeneratorStrategy;
import com.example.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class XlsGeneratorImpl implements GeneratorStrategy {
    private final ProductRepository productRepository;

    @Override
    public FileType getFileType() {
        return FileType.XLS;
    }

    @Override
    public byte[] generateFile() {
        log.info("xls");
        try {
            Workbook workbook = WorkbookFactory.create(false);
            Sheet sheet = workbook.createSheet("products");
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("id");
            row.createCell(1).setCellValue("name");
            row.createCell(2).setCellValue("price");
            row.createCell(3).setCellValue("description");
            row.createCell(4).setCellValue("quantity");
            List<Product> products = productRepository.findAll();
            int i = 1;

            for (Product product : products) {
                Row row1 = sheet.createRow(i);
                row1.createCell(0).setCellValue(product.getId());
                row1.createCell(1).setCellValue(product.getName());
                row1.createCell(2).setCellValue(product.getPrice());
                row1.createCell(3).setCellValue(product.getDescription());
                row1.createCell(4).setCellValue(product.getQuantity());
                i++;
            }
            sheet.setAutoFilter(new CellRangeAddress(0, products.size() + 1, 0, 4));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}