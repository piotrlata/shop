package com.example.shop.controller;

import com.example.shop.generator.GeneratorFactory;
import com.example.shop.generator.model.FileType;
import com.example.shop.generic.GenericFactory;
import com.example.shop.generic.strategy.file.FileGeneratorStrategy;
import com.example.shop.generic.strategy.mail.MailSenderStrategy;
import com.example.shop.generic.strategy.mail.model.MailType;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {
    private final GeneratorFactory generatorFactory;
    private final GenericFactory<FileType, FileGeneratorStrategy> fileFactory;
    private final GenericFactory<MailType, MailSenderStrategy> mailFactory;

    @GetMapping
    @ApiOperation(value = "generate file by type")
    public ResponseEntity<byte[]> testFactory(@RequestParam FileType fileType) {
        byte[] file = generatorFactory.getMapValue(fileType).generateFile();
        var httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_LENGTH, Integer.toString(file.length));
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=report." + fileType.toString().toLowerCase(Locale.ROOT));
        return ResponseEntity.ok().headers(httpHeaders).body(file);
    }

    @GetMapping("/generic")
    @ApiOperation(value = "generate file by type")
    public ResponseEntity<byte[]> testGenericFactory(@RequestParam FileType fileType) {
        byte[] file = fileFactory.getStrategyByType(fileType).generateFile();
        var httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_LENGTH, Integer.toString(file.length));
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=report." + fileType.toString().toLowerCase(Locale.ROOT));
        return ResponseEntity.ok().headers(httpHeaders).body(file);
    }

    @GetMapping("mail")
    @ApiOperation(value = "test method for generic flyweight pattern")
    public void testMailFactory(@RequestParam MailType mailType) {
        mailFactory.getStrategyByType(mailType).sendMail();

    }
}