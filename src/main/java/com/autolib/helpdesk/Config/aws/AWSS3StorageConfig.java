package com.autolib.helpdesk.Config.aws;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSS3StorageConfig {

    @Value("${al.aws.access_key}")
    String AWSAccessKeyId;

    @Value("${al.aws.secret_key}")
    String AWSSecretKey;

    @Bean
    public AmazonS3 s3Client() {
        return new AmazonS3Client(new BasicAWSCredentials(AWSAccessKeyId, AWSSecretKey));
    }
}
