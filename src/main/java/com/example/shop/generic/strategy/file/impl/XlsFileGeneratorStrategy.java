package com.example.shop.generic.strategy.file.impl;

import com.example.shop.domain.dao.User;
import com.example.shop.generator.model.FileType;
import com.example.shop.generic.strategy.file.FileGeneratorStrategy;
import com.example.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class XlsFileGeneratorStrategy implements FileGeneratorStrategy {
    private final UserRepository userRepository;

    @Override
    public FileType getType() {
        return FileType.XLS;
    }

    @Override
    public byte[] generateFile() {
        log.info("xls");
        try {
            Workbook workbook = WorkbookFactory.create(false);
            Sheet sheet = workbook.createSheet("users");
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("id");
            row.createCell(1).setCellValue("firstName");
            row.createCell(2).setCellValue("lastName");
            row.createCell(3).setCellValue("email");
            row.createCell(4).setCellValue("password");
            List<User> users = userRepository.findAll();
            int i = 1;

            for (User user : users) {
                Row row1 = sheet.createRow(i);
                row1.createCell(0).setCellValue(user.getId());
                row1.createCell(1).setCellValue(user.getFirstName());
                row1.createCell(2).setCellValue(user.getLastName());
                row1.createCell(3).setCellValue(user.getEmail());
                row1.createCell(4).setCellValue(user.getPassword());
                i++;
            }
            sheet.setAutoFilter(new CellRangeAddress(0, users.size(), 0, 4));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
