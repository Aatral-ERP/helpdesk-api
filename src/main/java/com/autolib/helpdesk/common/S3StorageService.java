package com.autolib.helpdesk.common;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.autolib.helpdesk.Config.aws.S3Directories;
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
    private static final String SLASH = "/";
    @Value("${al.aws.bucket_name}")
    private String BUCKET_NAME;
    @Value("${al.aws.client_folder_name}")
    private String CLIENT_FOLDER_NAME;

    @Autowired
    AmazonS3 s3Client;

    public void pushToAWS(String s3Directory, MultipartFile file) {
        pushToAWS(s3Directory, convertMultipartToFile(file), file.getOriginalFilename());
    }

    public void pushToAWS(String s3Directory, MultipartFile file, String fileName) {
        pushToAWS(s3Directory, convertMultipartToFile(file), fileName);
    }

    public void pushToAWS(String s3Directory, File file) {
        pushToAWS(s3Directory, file, file.getName());
    }

    public void pushLocalFileToAWS(String s3Directory, String localPath) {
        pushToAWS(s3Directory, new File(S3Directories.LocalDirectory + localPath));
    }

    public void pushToAWS(String s3Directory, File file, String fileName) {
        String key = CLIENT_FOLDER_NAME + SLASH + s3Directory + SLASH + fileName;
        logger.info("PUT :: " + CLIENT_FOLDER_NAME + "::" + key);
        try {
            s3Client.putObject(new PutObjectRequest(BUCKET_NAME, key, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteFromS3(String key) {
        logger.info("DELETE :: " + CLIENT_FOLDER_NAME + "::" + key);
        try {
            s3Client.deleteObject(new DeleteObjectRequest(BUCKET_NAME, CLIENT_FOLDER_NAME + SLASH + key));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void copyObjectS3(String from, String to) {
        logger.info("COPY :: " + CLIENT_FOLDER_NAME + "::" + from + " --> " + to);
        try {
            CopyObjectRequest copyObjRequest = new CopyObjectRequest(BUCKET_NAME, CLIENT_FOLDER_NAME + SLASH + from, BUCKET_NAME, CLIENT_FOLDER_NAME + SLASH + to);
            s3Client.copyObject(copyObjRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public java.io.InputStream getFromS3(String key) {
        logger.info("GET :: " + CLIENT_FOLDER_NAME + "::" + key);
        try {
            return s3Client.getObject(new GetObjectRequest(BUCKET_NAME, CLIENT_FOLDER_NAME + SLASH + key)).getObjectContent().getDelegateStream();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public InputStreamResource getFromS3AsInputStreamResource(String key) {
        try {
            return new InputStreamResource(getFromS3(key));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public byte[] getFromS3AsByteArray(String key) {
        try {
            return IOUtils.toByteArray(getFromS3(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static File convertMultipartToFile(MultipartFile file) {
        try {
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();

            try (InputStream is = file.getInputStream()) {
                Files.copy(is, convFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            return convFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}