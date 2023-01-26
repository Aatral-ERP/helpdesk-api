package com.autolib.helpdesk.LeadManagement.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.stereotype.Controller;
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

import com.autolib.helpdesk.LeadManagement.model.LeadActivityReportRequest;
import com.autolib.helpdesk.LeadManagement.model.LeadDashboardRequest;
import com.autolib.helpdesk.LeadManagement.model.LeadRequest;
import com.autolib.helpdesk.LeadManagement.model.LeadSearchRequest;
import com.autolib.helpdesk.LeadManagement.model.LeadUploadDetail;
import com.autolib.helpdesk.LeadManagement.service.LeadDetailsUploadService;
import com.autolib.helpdesk.LeadManagement.service.LeadService;
import com.autolib.helpdesk.common.Util;
import com.autolib.helpdesk.jwt.JwtTokenUtil;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping("lead-management")
public class LeadController {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    JwtTokenUtil jwtUtil;

    @Autowired
    LeadService leadService;

    @Autowired
    LeadDetailsUploadService leadUploadService;

    @Value("${al.ticket.content-path}")
    private String contentPath;

    @Autowired
    S3StorageService s3StorageService;

    @PostMapping("/upload-lead-attachments")
    ResponseEntity<?> fileUpload(@RequestHeader(value = "Authorization") String token,
                                 @RequestParam("directoryName") String directoryName, @RequestParam("file") MultipartFile file) {
        logger.info("fileUpload req Starts::::::::" + directoryName + "::" + file.getOriginalFilename() + " :: " + file.getSize());
        Map<String, Object> resp = new HashMap<>();
        try {
            jwtUtil.isValidToken(token);

            if (directoryName.contains("temp-files")) {
                File directory = new File(LocalDirectory.LeadFiles + directoryName);
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
                s3StorageService.pushToAWS(S3Directories.LeadFiles + directoryName, file);
            }

            resp.putAll(Util.SuccessResponse());

        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse());
            ex.printStackTrace();
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("create-lead")
    public ResponseEntity<?> createLead(@RequestHeader(value = "Authorization") String token,
                                        @RequestBody LeadRequest leadReq) {

        logger.info("createLead starts:::" + leadReq);
        jwtUtil.isValidToken(token);
        Map<String, Object> resp = new HashMap<>();

        try {

            resp = leadService.createLead(leadReq);

        } catch (Exception e) {
            resp.putAll(Util.FailedResponse());
            e.printStackTrace();
        }

        logger.info("createLead ends:::");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("update-lead")
    public ResponseEntity<?> updateLead(@RequestHeader(value = "Authorization") String token,
                                        @RequestBody LeadRequest leadReq) {

        logger.info("updateLead starts:::" + leadReq);
        jwtUtil.isValidToken(token);
        Map<String, Object> resp = new HashMap<>();

        try {

            resp = leadService.updateLead(leadReq);

        } catch (Exception e) {
            resp.putAll(Util.FailedResponse());
            e.printStackTrace();
        }

        logger.info("updateLead ends:::");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("get-lead/{leadId}")
    public ResponseEntity<?> getLead(@RequestHeader(value = "Authorization") String token,
                                     @PathVariable("leadId") int leadId) {

        logger.info("getLead starts:::" + leadId);
        jwtUtil.isValidToken(token);
        Map<String, Object> resp = new HashMap<>();

        try {

            resp = leadService.getLead(leadId);

        } catch (Exception e) {
            resp.putAll(Util.FailedResponse());
            e.printStackTrace();
        }

        logger.info("getLead ends:::");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("delete-lead")
    public ResponseEntity<?> deleteLead(@RequestHeader(value = "Authorization") String token,
                                        @RequestBody LeadRequest leadReq) {

        logger.info("deleteLead starts:::" + leadReq);
        jwtUtil.isValidToken(token);
        Map<String, Object> resp = new HashMap<>();

        try {

            resp = leadService.deleteLead(leadReq);

        } catch (Exception e) {
            resp.putAll(Util.FailedResponse());
            e.printStackTrace();
        }

        logger.info("deleteLead ends:::");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("search-leads")
    public ResponseEntity<?> searchLeads(@RequestHeader(value = "Authorization") String token,
                                         @RequestBody LeadSearchRequest request) {

        logger.info("searchLeads starts:::" + request);
        jwtUtil.isValidToken(token);
        Map<String, Object> resp = new HashMap<>();

        try {

            resp = leadService.searchLeads(request);

        } catch (Exception e) {
            resp.putAll(Util.FailedResponse());
            e.printStackTrace();
        }

        logger.info("searchLeads ends:::");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("save-lead-task")
    public ResponseEntity<?> saveLeadTask(@RequestHeader(value = "Authorization") String token,
                                          @RequestBody LeadRequest leadReq) {

        logger.info("saveLeadTask starts:::" + leadReq);
        jwtUtil.isValidToken(token);
        Map<String, Object> resp = new HashMap<>();

        try {

            resp = leadService.saveLeadTask(leadReq);

        } catch (Exception e) {
            resp.putAll(Util.FailedResponse());
            e.printStackTrace();
        }

        logger.info("saveLeadTask ends:::");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("get-lead-tasks/{leadId}")
    public ResponseEntity<?> getLeadTasks(@RequestHeader(value = "Authorization") String token,
                                          @PathVariable("leadId") int leadId) {

        logger.info("getLeadTasks starts:::" + leadId);
        jwtUtil.isValidToken(token);
        Map<String, Object> resp = new HashMap<>();

        try {

            resp = leadService.getLeadTasks(leadId);

        } catch (Exception e) {
            resp.putAll(Util.FailedResponse());
            e.printStackTrace();
        }

        logger.info("getLeadTasks ends:::");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("delete-lead-task")
    public ResponseEntity<?> deleteLeadTask(@RequestHeader(value = "Authorization") String token,
                                            @RequestBody LeadRequest leadReq) {

        logger.info("deleteLeadTask starts:::" + leadReq);
        jwtUtil.isValidToken(token);
        Map<String, Object> resp = new HashMap<>();

        try {

            resp = leadService.deleteLeadTask(leadReq);

        } catch (Exception e) {
            resp.putAll(Util.FailedResponse());
            e.printStackTrace();
        }

        logger.info("deleteLeadTask ends:::");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("save-lead-meeting")
    public ResponseEntity<?> saveLeadMeeting(@RequestHeader(value = "Authorization") String token,
                                             @RequestBody LeadRequest leadReq) {

        logger.info("saveLeadMeeting starts:::" + leadReq);
        jwtUtil.isValidToken(token);
        Map<String, Object> resp = new HashMap<>();

        try {

            resp = leadService.saveLeadMeeting(leadReq);

        } catch (Exception e) {
            resp.putAll(Util.FailedResponse());
            e.printStackTrace();
        }

        logger.info("saveLeadMeeting ends:::");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("get-lead-meetings/{leadId}")
    public ResponseEntity<?> getLeadMeetings(@RequestHeader(value = "Authorization") String token,
                                             @PathVariable("leadId") int leadId) {

        logger.info("getLeadMeetings starts:::" + leadId);
        jwtUtil.isValidToken(token);
        Map<String, Object> resp = new HashMap<>();

        try {

            resp = leadService.getLeadMeetings(leadId);

        } catch (Exception e) {
            resp.putAll(Util.FailedResponse());
            e.printStackTrace();
        }

        logger.info("getLeadMeetings ends:::");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("delete-lead-meeting")
    public ResponseEntity<?> deleteLeadMeeting(@RequestHeader(value = "Authorization") String token,
                                               @RequestBody LeadRequest leadReq) {

        logger.info("deleteLeadMeeting starts:::" + leadReq);
        jwtUtil.isValidToken(token);
        Map<String, Object> resp = new HashMap<>();

        try {

            resp = leadService.deleteLeadMeeting(leadReq);

        } catch (Exception e) {
            resp.putAll(Util.FailedResponse());
            e.printStackTrace();
        }

        logger.info("deleteLeadMeeting ends:::");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("upload-lead-details")
    ResponseEntity<?> uploadLeadDetails(@RequestHeader(value = "Authorization") String token,
                                        @RequestParam("file") MultipartFile uploadedfile) {
        logger.info("uploadLeadDetails req Starts::::::::" + uploadedfile.getSize());
        Map<String, Object> resp = new HashMap<>();
        try {
            jwtUtil.isValidToken(token);

            File directory = new File(LocalDirectory.LeadUploadTemp);
            System.out.println(directory.getAbsolutePath());
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File file = new File(directory.getAbsoluteFile() + "/" + uploadedfile.getOriginalFilename());
            file.createNewFile();
            FileOutputStream fout = new FileOutputStream(file);
            fout.write(uploadedfile.getBytes());
            fout.close();

            resp = leadUploadService.validateLeadDetailExcel(file);

            if (!resp.isEmpty()) {
                resp.putAll(Util.FailedResponse("Error exists in Excel"));
                return new ResponseEntity<>(resp, HttpStatus.OK);
            }
            List<LeadUploadDetail> details = leadUploadService.leadMapper(file);

            resp = leadService.createMultipleLead(details);

            resp.putAll(Util.SuccessResponse());

        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse());
            ex.printStackTrace();
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("dashboard-data")
    ResponseEntity<?> getDashboardData(@RequestHeader(value = "Authorization") String token,
                                       @RequestBody LeadDashboardRequest request) {

        logger.info("getDashboardData starts:::" + request);
        jwtUtil.isValidToken(token);
        Map<String, Object> resp = new HashMap<>();

        try {

            resp = leadService.getDashboardData(request);

        } catch (Exception e) {
            resp.putAll(Util.FailedResponse());
            e.printStackTrace();
        }

        logger.info("getDashboardData ends:::");
        return new ResponseEntity<>(resp, HttpStatus.OK);

    }

    @RequestMapping("lead-activities-report")
    public ResponseEntity<?> getLeadActivityReport(@RequestBody LeadActivityReportRequest req) {
        Map<String, Object> respMap = new HashMap<>();
        logger.info("getLeadActivityReport Starts:::" + req.toString());
        //jwtUtil.isValidToken(token);
        try {

            respMap = leadService.getLeadActivityReport(req);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(respMap, HttpStatus.OK);
    }

}
