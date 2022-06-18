package com.example.shop.generic.strategy.file.impl;

import com.example.shop.domain.dao.User;
import com.example.shop.generator.model.FileType;
import com.example.shop.generic.strategy.file.FileGeneratorStrategy;
import com.example.shop.repository.UserRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PdfFileGeneratorStrategy implements FileGeneratorStrategy {
    private final UserRepository userRepository;

    @Override
    public FileType getType() {
        return FileType.PDF;
    }

    @Override
    public byte[] generateFile() {
        log.info("pdf");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(byteArrayOutputStream));
        Document document = new Document(pdfDocument);
        Table table = new Table(4);
        List<User> users = userRepository.findAll();
        table.addCell("id");
        table.addCell("name");
        table.addCell("surname");
        table.addCell("email");
        for (User user : users) {
            table.addCell(user.getId().toString());
            table.addCell(user.getFirstName());
            table.addCell(user.getLastName());
            table.addCell(user.getEmail());
        }
        document.add(table);
        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
