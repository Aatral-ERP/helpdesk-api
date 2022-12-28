package com.autolib.helpdesk.common;

import com.autolib.helpdesk.Sales.model.DealDCProductRawMaterial;
import com.autolib.helpdesk.Sales.model.DealDCProducts;
import com.autolib.helpdesk.Sales.model.DealDeliveryChallan;

public class SalesUtil {
    public static String composeDCGeneratedStockEntryRemark(DealDCProducts dcp, DealDeliveryChallan dc) {
        return String.format("DC #%s generated for invoice #%s", dc.getId(), dcp.getInvoiceNo());
    }

    public static String composeDCUpdatedStockEntryRemark(DealDCProducts dcp, DealDeliveryChallan dc) {
        return String.format("DC #%s updated for invoice #%s", dc.getId(), dcp.getInvoiceNo());
    }

    public static String composeDCDeletedStockEntryRemark(DealDCProducts dcp, DealDeliveryChallan dc) {
        return String.format("DC #%s deleted for invoice #%s", dc.getId(), dcp.getInvoiceNo());
    }

    public static String composeDCRawMaterialGeneratedStockEntryRemark(DealDCProductRawMaterial dcp, DealDeliveryChallan dc) {
        return String.format("DC #%s generated for invoice #%s as Raw Material for %s", dc.getId(), dcp.getInvoiceNo(), dcp.getName());
    }

    public static String composeDCRawMaterialUpdatedStockEntryRemark(DealDCProductRawMaterial dcp, DealDeliveryChallan dc) {
        return String.format("DC #%s updated for invoice #%s as Raw Material for %s", dc.getId(), dcp.getInvoiceNo(), dcp.getName());
    }

    public static String composeDCRawMaterialDeletedStockEntryRemark(DealDCProductRawMaterial dcp, DealDeliveryChallan dc) {
        return String.format("DC #%s deleted for invoice #%s as Raw Material for %s", dc.getId(), dcp.getInvoiceNo(), dcp.getName());
    }

    public static String stockEntryRemarkStartsWith(DealDeliveryChallan dc) {
        return String.format("DC #%s ", dc.getId());
    }
}
