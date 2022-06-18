package com.example.shop.generic.strategy.file.impl;

import com.example.shop.generator.model.FileType;
import com.example.shop.generic.strategy.file.FileGeneratorStrategy;
import com.example.shop.repository.UserRepository;
import com.example.shop.security.SecurityUtils;
import com.example.shop.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class XlsFileGeneratorStrategy implements FileGeneratorStrategy {
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public FileType getType() {
        return FileType.XLS;
    }

    @SneakyThrows
    @Override
    public byte[] generateFile() {
        log.info("xls");
        try (var workbook = WorkbookFactory.create(false)) {

            var sheet = workbook.createSheet("users");
            var row = sheet.createRow(0);
            row.createCell(0).setCellValue("id");
            row.createCell(1).setCellValue("firstName");
            row.createCell(2).setCellValue("lastName");
            row.createCell(3).setCellValue("email");
            var users = userRepository.findAll();
            int i = 1;

            for (var user : users) {
                var row1 = sheet.createRow(i);
                row1.createCell(0).setCellValue(user.getId());
                row1.createCell(1).setCellValue(user.getFirstName());
                row1.createCell(2).setCellValue(user.getLastName());
                row1.createCell(3).setCellValue(user.getEmail());
                i++;
            }
            sheet.setAutoFilter(new CellRangeAddress(0, users.size(), 0, 3));
            var byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            byte[] byteArrayFile = byteArrayOutputStream.toByteArray();
            emailService.sendEmail(SecurityUtils.getCurrentUserEmail(), "reportFile", Collections.emptyMap(), byteArrayFile, "report.xls");
            return byteArrayFile;
        }
    }
}
