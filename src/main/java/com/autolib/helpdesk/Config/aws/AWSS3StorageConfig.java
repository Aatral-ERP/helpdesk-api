package com.autolib.helpdesk.Config.aws;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.autolib.helpdesk.common.GlobalAccessUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class AWSS3StorageConfig {

    @Value("${al.aws.access_key}")
    String AWSAccessKeyId;

    @Value("${al.aws.secret_key}")
    String AWSSecretKey;

    @Bean
    public AmazonS3 s3Client() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (AWSAccessKeyId.startsWith("ENC-"))
            System.out.println(GlobalAccessUtil.encrypt(AWSAccessKeyId.replaceAll("ENC-", "")));
        else if (AWSAccessKeyId.startsWith("DEC-"))
            System.out.println(GlobalAccessUtil.decrypt(AWSAccessKeyId.replaceAll("DEC-", "")));

        if (AWSSecretKey.startsWith("ENC-"))
            System.out.println(GlobalAccessUtil.encrypt(AWSSecretKey.replaceAll("ENC-", "")));
        else if (AWSSecretKey.startsWith("DEC-"))
            System.out.println(GlobalAccessUtil.decrypt(AWSSecretKey.replaceAll("DEC-", "")));

        return new AmazonS3Client(new BasicAWSCredentials(AWSAccessKeyId, AWSSecretKey));
    }
}
