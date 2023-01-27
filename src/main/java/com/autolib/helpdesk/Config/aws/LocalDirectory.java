package com.autolib.helpdesk.Config.aws;

public class LocalDirectory {

    public static final String Temp = System.getProperty("java.io.tmpdir");
    public static final String LeadFiles = Temp + S3Directories.LeadFiles;
    public static final String LeadMailTemplate = Temp + S3Directories.LeadMailTemplate;
    public static final String LeadUploadTemp = LeadFiles + "upload-temp-files/";
    public static final String CallReports = Temp + S3Directories.CallReports;
    public static final String Deals = Temp + S3Directories.Deals;
    public static final String Notes = S3Directories.Notes;
    public static final String PreambleDocuments = Temp + S3Directories.PreambleDocuments;
    public static final String LetterPads = Temp + S3Directories.LetterPads;
    public static final String Invoices = Temp + S3Directories.Invoices;
    public static final String DeliveryChallans = Temp + S3Directories.DeliveryChallans;
    public static final String Receipts = Temp + S3Directories.Receipts;
    public static final String ServicesInvoices = Temp + S3Directories.ServicesInvoices;
    public static final String ServiceReports = Temp + S3Directories.ServiceReports;
    public static final String Tickets = Temp + S3Directories.Tickets;
    public static final String InstituteAMCInvoices = Temp + S3Directories.InstituteAMCInvoices;
    public static final String Bills = Temp + S3Directories.Bills;
    public static final String Payslips = Temp + S3Directories.Payslips;

}
