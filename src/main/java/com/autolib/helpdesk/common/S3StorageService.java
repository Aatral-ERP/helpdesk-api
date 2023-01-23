package com.autolib.helpdesk.common;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Component
public class S3StorageService {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private static final String SUFFIX = "/";
    @Value("${al.aws.bucket_name}")
    private String BUCKET_NAME;
    @Value("${al.aws.client_folder_name}")
    private String CLIENT_FOLDER_NAME;

    @Autowired
    AmazonS3 s3client;

    public void pushToAWS(String directory, MultipartFile file) throws IOException {
        pushToAWS(directory, convertMultipartToFile(file), file.getOriginalFilename());
    }

    public void pushToAWS(String directory, MultipartFile file, String fileName) throws IOException {
        pushToAWS(directory, convertMultipartToFile(file), fileName);
    }

    public void pushToAWS(String directory, File file) {
        pushToAWS(directory, file, file.getName());
    }

    public void pushToAWS(String directory, File file, String fileName) {
        String key = CLIENT_FOLDER_NAME + SUFFIX + directory + SUFFIX + fileName;
        logger.info("PUT :: " + CLIENT_FOLDER_NAME + "::" + key);
        s3client.putObject(new PutObjectRequest(BUCKET_NAME, key, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public void deleteFromS3(String fileURL) {
        logger.info("DELETE :: " + CLIENT_FOLDER_NAME + "::" + fileURL);
        s3client.deleteObject(new DeleteObjectRequest(BUCKET_NAME, fileURL));
    }

    public java.io.InputStream getFromS3(String key) {
        logger.info("GET :: " + CLIENT_FOLDER_NAME + "::" + key);
        return s3client.getObject(new GetObjectRequest(BUCKET_NAME, CLIENT_FOLDER_NAME + SUFFIX + key)).getObjectContent().getDelegateStream();
    }

    public InputStreamResource getFromS3AsInputStreamResource(String key) {
        return new InputStreamResource(getFromS3(key));
    }

    public byte[] getFromS3AsByteArray(String key) {
        try {
            return IOUtils.toByteArray(getFromS3(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static File convertMultipartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        try (InputStream is = file.getInputStream()) {
            Files.copy(is, convFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        return convFile;
    }

}