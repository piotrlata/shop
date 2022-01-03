package com.example.shop.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {
    public AmazonS3 s3() {
        BasicAWSCredentials AWSCredentials = new BasicAWSCredentials("AKIA2BG6DMRCJLZEGHF4", "4eMxkZiiMrZZRftbDae19YCMJLi/TxQbEuzQqdi9");
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(AWSCredentials))
                .build();
    }
}
