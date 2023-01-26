package com.autolib.helpdesk.Config.aws;

public class LocalDirectory {

    public static final String Temp = System.getProperty("java.io.tmpdir");

    public static final String LeadFiles = Temp + S3Directories.LeadFiles;
}
