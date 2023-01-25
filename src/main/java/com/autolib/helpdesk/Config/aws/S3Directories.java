package com.autolib.helpdesk.Config.aws;

public class S3Directories {

    public static final String LocalDirectory = System.getProperty("java.io.tmpdir");
    public static final String AgentProfilePhotos = "_profile_photos/";
    public static final String AgentSignatures = "_profile_signatures/";
    public static final String AgentLedgerProofs = "_agent_legder_proof/";
    public static final String InstituteLogos = "InstituteLogo/";
    public static final String TaskFiles = "_task_files/";
    public static final String TaskFeatureFiles = "_task_feature_files/";
    public static final String LetterPads = "Letterpads/";
    public static final String PurchaseInputOrders = "Purchase_Input_Orders/";

}
