package com.autolib.helpdesk.download.controller;

import com.autolib.helpdesk.Config.aws.S3Directories;
import com.autolib.helpdesk.common.S3StorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.AgentLedgerProofs + fileName));
    }

    @GetMapping(value = "/agent-legder-proof/pdf/{mode}/{filename}", produces = "application/pdf")
    public ResponseEntity<InputStreamResource> _agent_legder_proof_pdf(@PathVariable("filename") String fileName,
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

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.AgentLedgerProofs + fileName));
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

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.InstituteLogos + fileName));
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

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.TaskFiles + taskId + "/" + fileName));
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

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.TaskFeatureFiles + featureId + "/" + fileName));
    }

    @RequestMapping(value = "/lead-attachments/{mode}/{leadId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadLeadAttachments(@PathVariable("fileName") String fileName,
                                                                       @PathVariable("leadId") String leadId, @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + leadId + " : " + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.LeadFiles + leadId + "/" + fileName));
    }

    @RequestMapping(value = "/lead-mail-templateattachments/{mode}/{templateId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadLeadMailTemplateAttachments(
            @PathVariable("fileName") String fileName, @PathVariable("templateId") String templateId,
            @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + templateId + " : " + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.LeadMailTemplate + templateId + "/" + fileName));
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

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.AgentSignatures + fileName));
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

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.AgentProfilePhotos + fileName));
    }

    @RequestMapping(value = "_service_invoices/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadServiceInvoicePDFFile(@PathVariable("fileName") String fileName)
            throws IOException {
        logger.info("Downloading _service_invoices File::" + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.ServicesInvoices + fileName));
    }

    @RequestMapping(value = "note-attachment/{entityId}/{noteId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadPDFFile(@PathVariable("fileName") String fileName,
                                                               @PathVariable("noteId") String noteId, @PathVariable("entityId") String entityId) throws IOException {

        logger.info("Downloading File::" + entityId + " : " + noteId + " : " + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.Deals + entityId + "/" + S3Directories.Notes + noteId + "/" + fileName));
    }

    @RequestMapping(value = "bill-attachment/{billId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> billAttachment(@PathVariable("fileName") String fileName,
                                                              @PathVariable("billId") String billId) throws IOException {

        logger.info("Downloading File::" + billId + " : " + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.Bills + billId + "/" + fileName));
    }

    @RequestMapping(value = "/download-deals-pdf/{mode}/{dealId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadQuotationPDFFile(@PathVariable("fileName") String fileName,
                                                                        @PathVariable("dealId") String dealId, @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + dealId + " : " + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.Deals + dealId + "/" + fileName));
    }

    @RequestMapping(value = "/download-invoice-pdf/{mode}/{invoiceId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadInvoicePDFFile(@PathVariable("fileName") String fileName,
                                                                      @PathVariable("invoiceId") String invoiceId, @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + invoiceId + " : " + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.Invoices + invoiceId + "/" + fileName));
    }

    @RequestMapping(value = "/download-delivery-challan-pdf/{mode}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadDCPDFFile(@PathVariable("fileName") String fileName,
                                                                 @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + " : " + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.DeliveryChallans + fileName));
    }

    @RequestMapping(value = "/download-receipt-pdf/{mode}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadReceiptPDFFile(@PathVariable("fileName") String fileName,
                                                                      @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + " : " + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.Receipts + fileName));
    }

    @RequestMapping(value = "/download-preamble-pdf/{mode}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadPreamblePDFFile(@PathVariable("fileName") String fileName,
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

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.PreambleDocuments + fileName));
    }

    @RequestMapping(value = "/download-purchase-input-pdf/{mode}/{orderId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadPurchaseInputOrderPDFFile(
            @PathVariable("fileName") String fileName, @PathVariable("orderId") String orderId,
            @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + orderId + " : " + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.PurchaseInputOrders + orderId + "/" + fileName));
    }

    @RequestMapping(value = "/payslip/{mode}/{employeeId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> payslip(@PathVariable("fileName") String fileName,
                                                       @PathVariable("employeeId") String employeeId, @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading File::" + employeeId + " : " + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.Payslips + employeeId + "/" + fileName));
    }

    @RequestMapping(value = "{ticketId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadPDFFile(@PathVariable("fileName") String fileName,
                                                               @PathVariable("ticketId") String ticketId) throws IOException {
        logger.info("Downloading File::" + ticketId + " : " + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.Tickets + ticketId + "/" + fileName));
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
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.CallReports + instituteId + "/" + fileName));
    }

    @RequestMapping(value = "/download-service-report-pdf/{mode}/{instituteId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadServiceReportPDFFile(@PathVariable("fileName") String fileName,
                                                                            @PathVariable("instituteId") String instituteId, @PathVariable("mode") String mode) throws IOException {
        logger.info("Downloading Service Report File::");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.ServiceReports + instituteId + "/" + fileName));
    }

    @RequestMapping(value = "/download-lettepad-pdf/{mode}/{letterPadId}/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadLetterpadPDFFile(@PathVariable("fileName") String fileName,
                                                                        @PathVariable("letterPadId") String letterPadId,
                                                                        @PathVariable("mode") String mode) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (mode.equalsIgnoreCase("view"))
            headers.add("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
        else
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

        return ResponseEntity.ok().headers(headers).body(s3StorageService.getFromS3AsInputStreamResource(S3Directories.LetterPads + letterPadId + "/" + fileName));
    }

    @RequestMapping(value = "download-lead-template", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadLeadTemplate() throws IOException {
        String fileName = "/lead-template.xls";
        String path = S3Directories.LeadTemplate + "lead-template.xls";
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
