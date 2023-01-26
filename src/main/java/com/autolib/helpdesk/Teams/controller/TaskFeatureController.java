package com.autolib.helpdesk.Teams.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.autolib.helpdesk.Config.aws.LocalDirectory;
import com.autolib.helpdesk.Config.aws.S3Directories;
import com.autolib.helpdesk.common.S3StorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Teams.model.TaskFeatureRequest;
import com.autolib.helpdesk.Teams.service.TaskFeatureService;
import com.autolib.helpdesk.common.Util;
import com.autolib.helpdesk.jwt.JwtTokenUtil;

@RestController
@CrossOrigin("*")
@RequestMapping("task-features")
public class TaskFeatureController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    JwtTokenUtil jwtUtil;

    @Value("${al.ticket.content-path}")
    private String contentPath;

    @Autowired
    S3StorageService s3StorageService;

    @Autowired
    TaskFeatureService featureService;

    @PostMapping("create-task-feature")
    public ResponseEntity<?> createTask(@RequestHeader(value = "Authorization") String token,
                                        @RequestBody TaskFeatureRequest featureReq) {

        logger.info("createTask starts:::" + featureReq);
        jwtUtil.isValidToken(token);
        Map<String, Object> resp = new HashMap<>();

        try {

            resp = featureService.createTaskFeature(featureReq);

        } catch (Exception e) {
            resp.putAll(Util.FailedResponse());
            e.printStackTrace();
        }

        logger.info("createTask ends:::");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("get-task-feature/{taskFeatureId}")
    public ResponseEntity<?> getTaskFeature(@RequestHeader(value = "Authorization") String token,
                                            @PathVariable("taskFeatureId") int taskFeatureId) {

        logger.info("getTaskFeature starts:::" + taskFeatureId);
        jwtUtil.isValidToken(token);
        Map<String, Object> resp = new HashMap<>();

        try {

            resp = featureService.getTaskFeature(taskFeatureId);

        } catch (Exception e) {
            resp.putAll(Util.FailedResponse());
            e.printStackTrace();
        }

        logger.info("getTaskFeature ends:::");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("get-team-task-features/{teamId}")
    public ResponseEntity<?> getTeamTaskFeatures(@RequestHeader(value = "Authorization") String token,
                                                 @PathVariable("teamId") int teamId) {

        logger.info("getTeamTaskFeatures starts:::" + teamId);
        jwtUtil.isValidToken(token);
        Map<String, Object> resp = new HashMap<>();

        try {

            resp = featureService.getTeamTaskFeatures(teamId);

        } catch (Exception e) {
            resp.putAll(Util.FailedResponse());
            e.printStackTrace();
        }

        logger.info("getTeamTaskFeatures ends:::");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("delete-task-feature")
    public ResponseEntity<?> deleteTaskFeature(@RequestHeader(value = "Authorization") String token,
                                               @RequestBody TaskFeatureRequest featureReq) {

        logger.info("deleteTaskFeature starts:::" + featureReq);
        jwtUtil.isValidToken(token);
        Map<String, Object> resp = new HashMap<>();

        try {

            resp = featureService.deleteTaskFeature(featureReq);

        } catch (Exception e) {
            resp.putAll(Util.FailedResponse());
            e.printStackTrace();
        }

        logger.info("deleteTaskFeature ends:::");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("/upload-task-feature-attachments")
    ResponseEntity<?> fileUpload(@RequestHeader(value = "Authorization") String token,
                                 @RequestParam("directoryName") String directoryName, @RequestParam("file") MultipartFile file) {
        logger.info("fileUpload req Starts::::::::" + file.getSize());
        Map<String, Object> resp = new HashMap<>();
        try {
            jwtUtil.isValidToken(token);

            if (directoryName.contains("temp-files")) {
                File directory = new File(LocalDirectory.Temp + S3Directories.TaskFeatureFiles + directoryName);
                System.out.println(directory.getAbsolutePath());
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                File convertFile = new File(directory.getAbsoluteFile() + "/" + file.getOriginalFilename());
                convertFile.createNewFile();
                FileOutputStream fout = new FileOutputStream(convertFile);
                fout.write(file.getBytes());
                fout.close();
            } else {
                s3StorageService.pushToAWS(S3Directories.TaskFeatureFiles + directoryName, file);
            }

            resp.putAll(Util.SuccessResponse());

        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse());
            ex.printStackTrace();
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}
