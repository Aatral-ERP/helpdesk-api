package com.autolib.helpdesk.download.controller;

import com.autolib.helpdesk.Config.aws.S3Directories;
import com.autolib.helpdesk.common.DirectoryUtil;
import com.autolib.helpdesk.common.S3StorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RestController
@RequestMapping("download")
@CrossOrigin("*")
public class DownloadFile {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Value("${al.ticket.content-path}")
    private String contentPath;

    @Autowired
    S3StorageService s3StorageService;

    @GetMapping(value = "/agent-legder-proof/image/{mode}/{filename}", produces = "image/*")
    public ResponseEntity<InputStreamResource> _agent_legder_proof(@PathVariable("filename") String fileName,
                                                                   @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.AgentLedgerProofs + "/" + fileName));
    }

    @GetMapping(value = "/agent-legder-proof/pdf/{mode}/{filename}", produces = "application/pdf")
    public ResponseEntity<InputStreamResource> _agent_legder_proof_pdf(@PathVariable("filename") String fileName,
                                                                       @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + fileName);
        String path = contentPath + "/_agent_legder_proof/" + fileName + "";
        InputStreamResource resource = getFileFromPath(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.AgentLedgerProofs + "/" + fileName));
    }

    @GetMapping(value = "/institute-logo/{filename}", produces = "image/png")
    public ResponseEntity<InputStreamResource> InstituteLogo(@PathVariable("filename") String fileName)
            throws IOException {
        logger.info("Downloading File::" + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.InstituteLogos + "/" + fileName));
    }

    @RequestMapping(value = "/task-attachments/{mode}/{taskId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadTaskAttachments(@PathVariable("fileName") String fileName,
                                                                       @PathVariable("taskId") String taskId, @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + taskId + " : " + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.TaskFiles + "/" + taskId + "/" + fileName));
    }

    @RequestMapping(value = "/task-feature-attachments/{mode}/{featureId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadTaskFeatureAttachments(@PathVariable("fileName") String fileName,
                                                                              @PathVariable("featureId") String featureId, @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + featureId + " : " + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.TaskFeatureFiles + "/" + featureId + "/" + fileName));
    }

    @RequestMapping(value = "/lead-attachments/{mode}/{leadId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadLeadAttachments(@PathVariable("fileName") String fileName,
                                                                       @PathVariable("leadId") String leadId, @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + leadId + " : " + fileName);
        String path = contentPath + DirectoryUtil.leadRootDirectory + leadId + "/" + fileName;
        InputStreamResource resource = getFileFromPath(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @RequestMapping(value = "/lead-mail-templateattachments/{mode}/{templateId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadLeadMailTemplateAttachments(
            @PathVariable("fileName") String fileName, @PathVariable("templateId") String templateId,
            @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + templateId + " : " + fileName);
        String path = contentPath + DirectoryUtil.leadMailTemplateDirectory + templateId + "/" + fileName;
        InputStreamResource resource = getFileFromPath(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @GetMapping(value = "/profile-signature/{filename}", produces = "image/png")
    public ResponseEntity<InputStreamResource> profileSign(@PathVariable("filename") String fileName)
            throws IOException {
        logger.info("Downloading File::" + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.AgentSignatures + "/" + fileName));
    }

    @GetMapping(value = "/profile-photos/{filename}", produces = "image/png")
    public ResponseEntity<InputStreamResource> profilePhoto(@PathVariable("filename") String fileName)
            throws IOException {
        logger.info("Downloading File::" + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.AgentProfilePhotos + "/" + fileName));
    }

    @RequestMapping(value = "_service_invoices/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadServiceInvoicePDFFile(@PathVariable("fileName") String fileName)
            throws IOException {
        logger.info("Downloading _service_invoices File::" + fileName);
        String path = "";

        InputStreamResource resource = null;
        HttpHeaders headers = new HttpHeaders();
        File file = null;
        try {

            path = contentPath + "_service_invoices/" + fileName + "";

            System.out.println(path);

            file = new File(path);
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("\r\nFile Not FOUND Exception::: " + fileName);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("\r\nFile Download Exception::: " + fileName);
        }
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length()).body(resource);
    }

    @RequestMapping(value = "_receipts/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadReceipt(@PathVariable("fileName") String fileName)
            throws IOException {
        logger.info("Downloading Receipt File::" + fileName);
        String path = "";

        InputStreamResource resource = null;
        HttpHeaders headers = new HttpHeaders();
        File file = null;
        try {

            path = contentPath + "_receipts/" + fileName + "";

            System.out.println(path);

            file = new File(path);
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("\r\nFile Not FOUND Exception::: " + fileName);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("\r\nFile Download Exception::: " + fileName);
        }
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length()).body(resource);
    }

    @RequestMapping(value = "note-attachment/{entityId}/{noteId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadPDFFile(@PathVariable("fileName") String fileName,
                                                               @PathVariable("noteId") String noteId, @PathVariable("entityId") String entityId) throws IOException {

        logger.info("Downloading File::" + entityId + " : " + noteId + " : " + fileName);
        String path = "";
        InputStreamResource resource = null;
        HttpHeaders headers = new HttpHeaders();
        File file = null;
        try {

            path = contentPath + "Deals/" + entityId + "/Notes/" + noteId + "/" + fileName + "";

            System.out.println(path);

            file = new File(path);
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("\r\nFile Not FOUND Exception::: " + entityId + " : " + noteId + " : " + fileName);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("\r\nFile Download Exception:::" + entityId + " : " + noteId + " : " + fileName);
        }
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length()).body(resource);
    }

    @RequestMapping(value = "bill-attachment/{billId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> billAttachment(@PathVariable("fileName") String fileName,
                                                              @PathVariable("billId") String billId) throws IOException {

        logger.info("Downloading File::" + billId + " : " + fileName);
        String path = "";
        InputStreamResource resource = null;
        HttpHeaders headers = new HttpHeaders();
        File file = null;
        try {

            path = contentPath + "Bills/" + billId + "/" + fileName;

            System.out.println(path);

            file = new File(path);
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("\r\nFile Not FOUND Exception::: " + billId + " : " + fileName);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("\r\nFile Download Exception:::" + billId + " : " + fileName);
        }
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length()).body(resource);
    }

    @RequestMapping(value = "/download-deals-pdf/{mode}/{dealId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadQuotationPDFFile(@PathVariable("fileName") String fileName,
                                                                        @PathVariable("dealId") String dealId, @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + dealId + " : " + fileName);
        String path = contentPath + "/Deals/" + dealId + "/" + fileName + "";
        InputStreamResource resource = getFileFromPath(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @RequestMapping(value = "/download-invoice-pdf/{mode}/{invoiceId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadInvoicePDFFile(@PathVariable("fileName") String fileName,
                                                                      @PathVariable("invoiceId") String invoiceId, @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + invoiceId + " : " + fileName);
        String path = contentPath + "/Invoices/" + invoiceId + "/" + fileName + "";
        InputStreamResource resource = getFileFromPath(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @RequestMapping(value = "/download-delivery-challan-pdf/{mode}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadDCPDFFile(@PathVariable("fileName") String fileName,
                                                                 @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + " : " + fileName);
        String path = contentPath + "/Delivery_Challans/" + fileName + "";
        InputStreamResource resource = getFileFromPath(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @RequestMapping(value = "/download-receipt-pdf/{mode}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadReceiptPDFFile(@PathVariable("fileName") String fileName,
                                                                      @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + " : " + fileName);
        String path = contentPath + "/Receipts/" + "/" + fileName + "";
        InputStreamResource resource = getFileFromPath(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @RequestMapping(value = "/download-preamble-pdf/{mode}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadPreamblePDFFile(@PathVariable("fileName") String fileName,
                                                                       @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + fileName);
        String path = contentPath + "/_preamble_documents/" + fileName + "";
        InputStreamResource resource = getFileFromPath(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @RequestMapping(value = "/download-purchase-input-pdf/{mode}/{orderId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadPurchaseInputOrderPDFFile(
            @PathVariable("fileName") String fileName, @PathVariable("orderId") String orderId,
            @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + orderId + " : " + fileName);
        String path = contentPath + "/Purchase_Input_Orders/" + fileName + "";
        InputStreamResource resource = getFileFromPath(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @RequestMapping(value = "/payslip/{mode}/{employeeId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> payslip(@PathVariable("fileName") String fileName,
                                                       @PathVariable("employeeId") String employeeId, @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + employeeId + " : " + fileName);
        String path = contentPath + "/Payslips/" + employeeId + "/" + fileName + "";
        InputStreamResource resource = getFileFromPath(path);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @RequestMapping(value = "{ticketId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadPDFFile(@PathVariable("fileName") String fileName,
                                                               @PathVariable("ticketId") String ticketId) throws IOException {
        logger.info("Downloading File::" + ticketId + " : " + fileName);
        String path = "";

        InputStreamResource resource = null;
        HttpHeaders headers = new HttpHeaders();
        File file = null;
        try {

            path = contentPath + ticketId + "/" + fileName + "";

            System.out.println(path);
            file = new File(path);
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            logger.error("\r\nFile Not FOUND Exception::: " + ticketId + " " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("\r\nFile Download Exception::: " + ticketId + " " + fileName);
        }
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length()).body(resource);
    }

    InputStreamResource getFileFromPath(String path) {
        InputStreamResource resource = null;
        File file = null;
        try {
            file = new File(path);
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            logger.error("\r\nFile Not FOUND Exception::: " + path);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("\r\nFile Download Exception::: " + path);
        }
        return resource;
    }

    @RequestMapping(value = "/download-call-report-pdf/{mode}/{instituteId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadCallReportPDFFile(@PathVariable("fileName") String fileName,
                                                                         @PathVariable("instituteId") String instituteId, @PathVariable("mode") String mode) throws IOException {

        String path = contentPath + "/CallReports/" + instituteId + "/" + fileName + "";
        logger.info("Downloading Call Report File::" + path);
        InputStreamResource resource = getFileFromPath(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @RequestMapping(value = "/download-service-report-pdf/{mode}/{instituteId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadServiceReportPDFFile(@PathVariable("fileName") String fileName,
                                                                            @PathVariable("instituteId") String instituteId, @PathVariable("mode") String mode) throws IOException {

        String path = contentPath + "/ServiceReports/" + instituteId + "/" + fileName + "";
        logger.info("Downloading Service Report File::" + path);
        InputStreamResource resource = getFileFromPath(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @RequestMapping(value = "/download-lettepad-pdf/{mode}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadLetterpadPDFFile(@PathVariable("fileName") String fileName,
                                                                        @PathVariable("mode") String mode) throws IOException {

        String path = contentPath + "/Letterpads/" + fileName + "";
        logger.info("Downloading Letterpads File::" + path);
        InputStreamResource resource = getFileFromPath(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @RequestMapping(value = "download-lead-template", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadLeadTemplate() throws IOException {
        String fileName = "/lead-template.xls";
        String path = contentPath + DirectoryUtil.leadTemplateDirectory + "/lead-template.xls";
        logger.info("Downloading LeadTemplate File::" + path);
        InputStreamResource resource = getFileFromPath(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(resource);
    }

}
