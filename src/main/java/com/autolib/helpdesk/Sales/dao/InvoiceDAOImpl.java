package com.autolib.helpdesk.Sales.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.InfoDetails;
import com.autolib.helpdesk.Agents.entity.Product;
import com.autolib.helpdesk.Agents.repository.AgentRepository;
import com.autolib.helpdesk.Agents.repository.InfoDetailsRepository;
import com.autolib.helpdesk.Agents.repository.ProductsRepository;
import com.autolib.helpdesk.Agents.service.AgentService;
import com.autolib.helpdesk.Institutes.model.AMCDetails;
import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.Institutes.model.InstituteContact;
import com.autolib.helpdesk.Institutes.model.InstituteProducts;
import com.autolib.helpdesk.Institutes.repository.InstituteAmcRepository;
import com.autolib.helpdesk.Institutes.repository.InstituteProductRepository;
import com.autolib.helpdesk.Sales.model.Deal;
import com.autolib.helpdesk.Sales.model.DealPayments;
import com.autolib.helpdesk.Sales.model.DealProducts;
import com.autolib.helpdesk.Sales.model.DealRequest;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoice;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceContacts;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceProducts;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceReminder;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceReminderRequest;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceReminderResponse;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceRequest;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceResponse;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceSearchRequest;
import com.autolib.helpdesk.Sales.model.Invoice.InvoiceEmail;
import com.autolib.helpdesk.Sales.model.Invoice.InvoiceEmailAttachments;
import com.autolib.helpdesk.Sales.model.Invoice.InvoiceEmailReminderSettings;
import com.autolib.helpdesk.Sales.repository.DealDeliveryChallanRepository;
import com.autolib.helpdesk.Sales.repository.DealPaymentsRepository;
import com.autolib.helpdesk.Sales.repository.DealProductsRepository;
import com.autolib.helpdesk.Sales.repository.DealsRepository;
import com.autolib.helpdesk.Sales.repository.Invoice.DealInvoiceContactsRepository;
import com.autolib.helpdesk.Sales.repository.Invoice.DealInvoiceProductsRepository;
import com.autolib.helpdesk.Sales.repository.Invoice.DealInvoiceReminderRepository;
import com.autolib.helpdesk.Sales.repository.Invoice.DealInvoiceRepository;
import com.autolib.helpdesk.Sales.repository.Invoice.InvoiceEmailAttachmentRepository;
import com.autolib.helpdesk.Sales.repository.Invoice.InvoiceEmailReminderSettingsRepository;
import com.autolib.helpdesk.Sales.repository.Invoice.InvoiceEmailRepository;
import com.autolib.helpdesk.common.EmailModel;
import com.autolib.helpdesk.common.EmailSender;
import com.autolib.helpdesk.common.EnumUtils.ServiceUnder;
import com.autolib.helpdesk.common.Util;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRRtfExporter;

@SuppressWarnings("deprecation")
@Repository
public class InvoiceDAOImpl implements InvoiceDAO {

    @Value("${al.ticket.content-path}")
    private String contentPath;

    @Autowired
    InvoiceEmailReminderSettingsRepository reminderSettingRepo;

    @Autowired
    DealInvoiceProductsRepository invProdRepo;

    @Autowired
    DealInvoiceContactsRepository invContactsRepo;

    @Autowired
    DealInvoiceRepository invRepo;

    @Autowired
    DealDeliveryChallanRepository dcRepo;

    @Autowired
    DealsRepository dealRepo;

    @Autowired
    DealProductsRepository dealProdRepo;

    @Autowired
    InstituteAmcRepository instAmcRepo;

    @Autowired
    ProductsRepository prodRepo;

    @Autowired
    InstituteProductRepository instProdRepo;

    @Autowired
    AgentService agentService;

    @Autowired
    InfoDetailsRepository infoDetailRepo;
    @Autowired
    AgentRepository agentRepo;

    @Autowired
    DealPaymentsRepository paymentRepo;

    @Autowired
    InstituteProductRepository instProductRepo;

    @Autowired
    InvoiceEmailRepository invEmailRepo;

    @Autowired
    InvoiceEmailAttachmentRepository invEmailAttRepo;

    @Autowired
    EmailSender emailSender;

    @Autowired
    EntityManager em;

    @Autowired
    DealInvoiceReminderRepository invReminderRepo;

    @Transactional
    @Override
    public Map<String, Object> saveDealInvoice(DealInvoiceRequest dealInvReq) {
        Map<String, Object> resp = new HashMap<>();

        try {
            Deal deal = dealRepo.findById(dealInvReq.getDealInvoice().getDealId());
            List<DealInvoice> _deal_invoices = invRepo.findByDealId(dealInvReq.getDealInvoice().getDealId());
            System.out.println(_deal_invoices);
            Double totalAmount = _deal_invoices.stream()
                    .filter(inv -> inv.getId() != dealInvReq.getDealInvoice().getId())
                    .mapToDouble(DealInvoice::getGrandTotal).sum();
            totalAmount = totalAmount + dealInvReq.getDealInvoice().getGrandTotal();
            DealInvoice ditemp = invRepo.findByInvoiceNo(dealInvReq.getDealInvoice().getInvoiceNo());

            if (dealInvReq.getDealInvoice().getId() == 0 && ditemp != null) {
                resp.putAll(Util.invalidMessage("Invoice No Already Exist"));
            } else if (ditemp != null && dealInvReq.getDealInvoice().getId() > 0 && ditemp != null
                    && dealInvReq.getDealInvoice().getId() != ditemp.getId()) {
                resp.putAll(Util.invalidMessage("Invoice No Already Exist with another Deal"));
            } else if (totalAmount > deal.getGrandTotal()) {
                System.out.println(totalAmount + " :: " + deal.getGrandTotal());
                resp.putAll(Util.invalidMessage("Invoice amount exceeds deal amount."));
            } else {

                boolean newInvoice = dealInvReq.getDealInvoice().getId() > 0 ? false : true;

                if (dealInvReq.getDealInvoice().getId() != 0) {
                    invProdRepo.deleteByInvoiceId(dealInvReq.getDealInvoice().getId());

                    invContactsRepo.deleteByInvoiceId(dealInvReq.getDealInvoice().getId());
                }

                dealInvReq.setDealInvoice(invRepo.save(dealInvReq.getDealInvoice()));

                dealInvReq.getDealInvoiceProducts().forEach(dp -> {
                    dp.setInvoiceId(dealInvReq.getDealInvoice().getId());
                    dp.setInvoiceNo(dealInvReq.getDealInvoice().getInvoiceNo());
                    dp.setDealId(dealInvReq.getDealInvoice().getDealId());
                });

                dealInvReq.setDealInvoiceProducts(invProdRepo.saveAll(dealInvReq.getDealInvoiceProducts()));

                for (InstituteContact ic : dealInvReq.getInstituteContacts()) {
                    invContactsRepo.save(new DealInvoiceContacts(dealInvReq.getDealInvoice().getId(), ic));
                }

                List<DealInvoice> invoices = invRepo.findByDealId(dealInvReq.getDealInvoice().getDealId());

                String invoice_nos = invoices.stream().map(inv -> inv.getInvoiceNo()).collect(Collectors.joining(";"));

                em.createQuery("update Deal d set d.invoiceNo = :invoice_nos where d.id = :dealId")
                        .setParameter("invoice_nos", invoice_nos)
                        .setParameter("dealId", dealInvReq.getDealInvoice().getDealId()).executeUpdate();

                if (newInvoice) {

                    // Updating Stock only if sales
                    if (dealInvReq.getDealInvoice().getDealType().equalsIgnoreCase("sales")) {
//						List<InstituteProducts> instituteProducts = new ArrayList<>();
//
//						for (DealInvoiceProducts dp : dealInvReq.getDealInvoiceProducts()) {
//
//							// Stock Entry
//							StockEntry se = new StockEntry();
//							se.setProductId(dp.getProductId());
//							se.setEntryDate(new Date());
//							se.setEntryBy("--system-generated--");
//							se.setQuantity(dp.getQuantity());
//							se.setType("Deduct");
//							se.setRemarks(dealInvReq.getDealInvoice().getInvoiceNo() + " dt. "
//									+ Util.sdfFormatter(dealInvReq.getDealInvoice().getInvoiceDate(), "dd/MM/yyyy"));
//
//							agentService.addStockEntry(se);
//
//						}

                    } else if (dealInvReq.getDealInvoice().getDealType().equalsIgnoreCase("amc")) {
                        List<InstituteProducts> instituteProducts = new ArrayList<>();
                        for (DealInvoiceProducts dp : dealInvReq.getDealInvoiceProducts()) {

                            InstituteProducts ip = instProductRepo.findByInstituteAndProduct(
                                    dealInvReq.getDealInvoice().getInstitute(), new Product(dp.getProductId()));

                            if (ip != null) {
                                ip.setAmcAmount(dp.getPrice());
                                ip.setCurrentServiceUnder(ServiceUnder.valueOf("AMC"));
                                ip.setLastAMCPaidDate(dealInvReq.getDealInvoice().getInvoiceDate());
                                ip.setAmcExpiryDate(dealInvReq.getDealInvoice().getAmcToDate());

                                instituteProducts.add(ip);
                            } else {
                                System.out.println("Else::::" + dp.getName());
                                ip = new InstituteProducts();

                                ip.setInstitute(dealInvReq.getDealInvoice().getInstitute());
                                ip.setProduct(new Product(dp.getProductId()));
                                ip.setQuantity(dp.getQuantity());

                                ip.setAmcAmount(dp.getPrice());
                                ip.setCurrentServiceUnder(ServiceUnder.valueOf("AMC"));
                                ip.setLastAMCPaidDate(dealInvReq.getDealInvoice().getInvoiceDate());
                                ip.setAmcExpiryDate(dealInvReq.getDealInvoice().getAmcToDate());

                                instituteProducts.add(ip);
                            }
                        }
                        instProductRepo.saveAll(instituteProducts);
                    }

                    // Updating InstituteProducs
                    List<InstituteProducts> instProducts = instProdRepo
                            .findByInstitute(dealInvReq.getDealInvoice().getInstitute());
                    List<InstituteProducts> _instProducts_new = new ArrayList<>();
                    List<Integer> ids = dealInvReq.getDealInvoiceProducts().stream()
                            .map(DealInvoiceProducts::getProductId).collect(Collectors.toList());

                    List<Product> products = prodRepo.findAllById(ids);

                    if (dealInvReq.getDealInvoice().getDealType().equalsIgnoreCase("sales")) {

                        dealInvReq.getDealInvoiceProducts().forEach(dp -> {
                            Optional<InstituteProducts> _ip_opt = instProducts.stream()
                                    .filter(ip -> ip.getProduct().getId() == dp.getProductId()).findFirst();
                            InstituteProducts ip = new InstituteProducts();
                            Optional<Product> prod = products.stream().filter(pr -> pr.getId() == dp.getProductId())
                                    .findFirst();
                            if (_ip_opt.isPresent()) {
                                ip = _ip_opt.get();
                                ip.setQuantity(ip.getQuantity() + dp.getQuantity());
                                ip.setCurrentServiceUnder(ServiceUnder.valueOf("Warranty"));

                                if (dealInvReq.getDealInvoice().getAmcToDate() != null)
                                    ip.setAmcExpiryDate(dealInvReq.getDealInvoice().getAmcToDate());
                                else {
                                    if (prod.isPresent()) {
                                        System.out.println(prod.get().getName() + " :: " + prod.get().getWarranty());
                                        ip.setAmcExpiryDate(getExpiryDate(prod.get().getWarranty()));
                                    } else {
                                        ip.setAmcExpiryDate(new Date());
                                    }
                                }

                            } else {
                                ip.setInstitute(dealInvReq.getDealInvoice().getInstitute());
                                ip.setProduct(new Product(dp.getProductId()));
                                ip.setCurrentServiceUnder(ServiceUnder.valueOf("Warranty"));
                                ip.setQuantity(ip.getQuantity() + dp.getQuantity());

                                if (dealInvReq.getDealInvoice().getAmcToDate() != null)
                                    ip.setAmcExpiryDate(dealInvReq.getDealInvoice().getAmcToDate());
                                else {
                                    if (prod.isPresent()) {
                                        System.out.println(prod.get().getName() + " :: " + prod.get().getWarranty());
                                        ip.setAmcExpiryDate(getExpiryDate(prod.get().getWarranty()));
                                    } else {
                                        ip.setAmcExpiryDate(new Date());
                                    }
                                }
                            }
                            _instProducts_new.add(ip);
                        });
                    }
                    instProdRepo.saveAll(_instProducts_new);

                    System.out.println("updating Invoice Reminder if any::");
                    // updating Invoice Reminder if any
                    List<DealInvoiceReminder> reminders = invReminderRepo
                            .findByDealId(dealInvReq.getDealInvoice().getDealId());

                    System.out.println("updating Invoice Reminder if any::" + reminders.size());

                    reminders.stream().filter(rem -> rem.getInvoiceId() == 0).filter(rem -> {

                        System.out.println(Util.sdfFormatter(rem.getReminderDate()) + ":::"
                                + Util.sdfFormatter(dealInvReq.getDealInvoice().getInvoiceDate()) + "::"
                                + Util.sdfFormatter(rem.getReminderDate())
                                .equals(Util.sdfFormatter(dealInvReq.getDealInvoice().getInvoiceDate())));
                        return Util.sdfFormatter(rem.getReminderDate())
                                .equals(Util.sdfFormatter(dealInvReq.getDealInvoice().getInvoiceDate()));

                    }).forEach(rem -> {
                        rem.setInvoiceId(dealInvReq.getDealInvoice().getId());
                        rem.setInvoiceNo(dealInvReq.getDealInvoice().getInvoiceNo());
                    });

                    System.out.println(reminders.toString());

                    invReminderRepo.saveAll(reminders);

                }
                resp.putAll(Util.SuccessResponse());
            }

        } catch (

                Exception Ex) {
            Ex.printStackTrace();
            resp.putAll(Util.FailedResponse(Ex.getMessage()));
        }
        resp.put("DealInvoice", dealInvReq.getDealInvoice());
        resp.put("DealInvoiceProducts", dealInvReq.getDealInvoiceProducts());
        resp.put("DealInvoiceContacts", dealInvReq.getInstituteContacts());

        return resp;
    }

    Date getExpiryDate(String warranty) {
        Calendar calendar = Calendar.getInstance();
        try {
            warranty = warranty.replace(" years", "").replace("year", "").trim();

            calendar.add(Calendar.YEAR, Integer.parseInt(warranty));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println(warranty + " :: " + calendar.getTime());
        return calendar.getTime();

    }

    @Override
    public Map<String, Object> getDealInvoice(int invoiceId) {
        // TODO Auto-generated method stub
        Map<String, Object> resp = new HashMap<>();
        DealInvoice dealInvoice = new DealInvoice();
        List<DealInvoiceProducts> dealInvoiceProducts = new ArrayList<>();
        List<DealInvoiceContacts> dealInvoiceContacts = new ArrayList<>();

        try {
            dealInvoice = invRepo.findById(invoiceId);

            if (dealInvoice != null) {
                dealInvoiceProducts = invProdRepo.findByInvoiceId(invoiceId);

                dealInvoiceContacts = invContactsRepo.findByInvoiceId(invoiceId);
            }

            resp.putAll(Util.SuccessResponse());

        } catch (Exception Ex) {
            Ex.printStackTrace();
            resp.putAll(Util.FailedResponse(Ex.getMessage()));
        }
        resp.put("DealInvoice", dealInvoice);
        resp.put("DealInvoiceProducts", dealInvoiceProducts);
        resp.put("DealInvoiceContacts", dealInvoiceContacts);
        return resp;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getDealInvoicesReport(DealInvoiceSearchRequest req) {

        Map<String, Object> resp = new HashMap<>();
        List<DealInvoice> invoices = new ArrayList<>();
        try {

            String dealFilter = "";

            /* Deals Filter starts */

            if (req.getInstitutes() != null && req.getInstitutes().size() > 0) {
                String instituteIds = "'0'";
                for (Institute inst : req.getInstitutes()) {
                    instituteIds = instituteIds + ",'" + inst.getInstituteId() + "'";
                }
                dealFilter = dealFilter + " and di.institute in (" + instituteIds + ") ";
            }
            if (req.getAgents() != null && req.getAgents().size() > 0) {
                String agents = "'0'";
                for (Agent agnt : req.getAgents()) {
                    agents = agents + ",'" + agnt.getEmailId() + "'";
                }
                dealFilter = dealFilter + " and di.createdBy in (" + agents + ") ";
            }

            if (req.getDealCreatedDateFrom() != null && req.getDealCreatedDateTo() != null) {
                dealFilter = dealFilter + " and di.createddatetime between '"
                        + Util.sdfFormatter(req.getDealCreatedDateFrom()) + "' and '"
                        + Util.sdfFormatter(req.getDealCreatedDateTo()) + " 23:59:59'";
            }

            if (req.getDealModifiedDateFrom() != null && req.getDealModifiedDateTo() != null) {
                dealFilter = dealFilter + " and di.lastupdatedatetime between '"
                        + Util.sdfFormatter(req.getDealModifiedDateFrom()) + "' and '"
                        + Util.sdfFormatter(req.getDealModifiedDateTo()) + " 23:59:59'";
            }

            if (req.getDealInvoiceProducts() != null && req.getDealInvoiceProducts().size() > 0) {
                String productIds = "0";
                for (DealInvoiceProducts dp : req.getDealInvoiceProducts()) {
                    productIds = productIds + "," + dp.getId();
                }
                dealFilter = dealFilter
                        + " and di.id in ( select dp.invoiceId from DealInvoiceProducts dp where dp.productId in ("
                        + productIds + "))";
            }
            if (req.getDealType() != null && req.getDealType() != "") {
                dealFilter = dealFilter + " and di.dealType = '" + req.getDealType() + "'";
            }

            /* Deals Filter ends and Deal Invoice starts */

            if (req.getInvoiceNo() != null && req.getInvoiceNo() != "") {
                dealFilter = dealFilter + " and di.invoiceNo like '%" + req.getInvoiceNo() + "%'";
            }
            if (req.getPoNo() != null && req.getPoNo() != "") {
                dealFilter = dealFilter + " and di.purchaseOrderNo like '%" + req.getPoNo() + "%'";
            }
            if (req.getSoNo() != null && req.getSoNo() != "") {
                dealFilter = dealFilter + " and di.salesOrderNo like '%" + req.getSoNo() + "%'";
            }
            if (req.getInvoiceSubject() != null && req.getInvoiceSubject() != "") {
                dealFilter = dealFilter + " and di.subject like '%" + req.getInvoiceSubject() + "%'";
            }
            if (req.getInvoiceDateFrom() != null && req.getInvoiceDateTo() != null) {
                dealFilter = dealFilter + " and di.invoiceDate between '" + Util.sdfFormatter(req.getInvoiceDateFrom())
                        + "' and '" + Util.sdfFormatter(req.getInvoiceDateTo()) + "'";
            }
            if (req.getInvoiceDueDateFrom() != null && req.getInvoiceDueDateTo() != null) {
                dealFilter = dealFilter + " and di.dueDate between '" + Util.sdfFormatter(req.getInvoiceDueDateFrom())
                        + "' and '" + Util.sdfFormatter(req.getInvoiceDueDateTo()) + "'";
            }
            if (req.getInvoiceStatus() != null && req.getInvoiceStatus() != "") {
                dealFilter = dealFilter + " and di.invoiceStatus = '" + req.getInvoiceStatus() + "'";
            }
            if (req.getGstMonth() != null && req.getGstMonth() != "") {
                dealFilter = dealFilter + " and di.gstMonth = '" + req.getGstMonth() + "'";
            }
            if (req.getGstYear() != null && req.getGstYear() != "") {
                dealFilter = dealFilter + " and di.gstYear = '" + req.getGstYear() + "'";
            }
            if (req.getPaidStatus() != null && req.getPaidStatus() != "") {
                if (req.getPaidStatus().equalsIgnoreCase("Not Paid"))
                    dealFilter = dealFilter + " and di.paidAmount = 0 ";
                else if (req.getPaidStatus().equalsIgnoreCase("Partially Paid"))
                    dealFilter = dealFilter + " and di.paidAmount > 0 and di.paidAmount < di.grandTotal";
                else if (req.getPaidStatus().equalsIgnoreCase("All Pending Payments"))
                    dealFilter = dealFilter + " and di.paidAmount < di.grandTotal ";
                else if (req.getPaidStatus().equalsIgnoreCase("Paid"))
                    dealFilter = dealFilter + " and di.paidAmount >= di.grandTotal ";
                else if (req.getPaidStatus().equalsIgnoreCase("Overdue"))
                    dealFilter = dealFilter + " and di.dueDate > '" + Util.sdfFormatter(new Date())
                            + "' and di.paidAmount < di.grandTotal ";
            }

            Query query = em.createQuery("select di from DealInvoice di where 2 > 1 " + dealFilter, DealInvoice.class);

            invoices = query.getResultList();

            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("DealInvoices", invoices);
        return resp;

    }

    @Override
    public Map<String, Object> generateInvoicePDFTemplate1Lite(DealInvoiceRequest dealReq) {
        Map<String, Object> resp = new HashMap<>();

        try {

            dealReq.setDealInvoice(invRepo.findById(dealReq.getDealInvoice().getId()));

            dealReq.setDealInvoiceProducts(invProdRepo.findByInvoiceId(dealReq.getDealInvoice().getId()));

            System.out.println(dealReq.toString());

            InputStream stream = this.getClass()
                    .getResourceAsStream("/reports/FinalTemplates/Template_1/Template_1_Lite.jrxml");

            final Map<String, Object> parameters = new HashMap<>();

            InfoDetails info = infoDetailRepo.findById(1);
            Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

            parameters.put("cmp_name", info.getCmpName());
            parameters.put("cmp_address", info.getCompanyAddressHTML1());
            parameters.put("cmp_logo_url", info.getLogoAsFile());
            parameters.put("dealtype_label",
                    dealReq.getDealInvoice().getInvoiceStatus().equalsIgnoreCase("proforma invoice")
                            ? "PROFORMA INVOICE"
                            : "INVOICE");

            parameters.put("is_discount_applied", dealReq.isDiscountApplied());
            parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
            parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
            parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
            parameters.put("for_label", "For " + info.getCmpName());
            parameters.put("designation", dealReq.getDesignation());
            parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
            parameters.put("online_payment_url", info.getInstamojoPaymentURL());

            parameters.put("terms", dealReq.getDealInvoice().getTerms().replaceAll("\n", "<br>"));

            String deal_date_label = "", deal_date_text = "";

            deal_date_label = deal_date_label + "Invoice No. : <br>";
            deal_date_text = deal_date_text + String.valueOf(dealReq.getDealInvoice().getInvoiceNo()) + "<br>";
            deal_date_label = deal_date_label + "Invoice Date : <br>";
            deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealInvoice().getInvoiceDate()) + "<br>";
            deal_date_label = deal_date_label + "Due Date : <br>";
            deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealInvoice().getDueDate()) + "<br>";

            if (dealReq.getDealInvoice().getPurchaseOrderNo() != null
                    && dealReq.getDealInvoice().getPurchaseOrderNo() != "") {
                deal_date_label = deal_date_label + "PO Number : <br>";
                deal_date_text = deal_date_text + String.valueOf(dealReq.getDealInvoice().getPurchaseOrderNo())
                        + "<br>";
            }
            if (dealReq.getDealInvoice().getSalesOrderNo() != null
                    && dealReq.getDealInvoice().getSalesOrderNo() != "") {
                deal_date_label = deal_date_label + "Sales Order No : <br>";
                deal_date_text = deal_date_text + String.valueOf(dealReq.getDealInvoice().getSalesOrderNo()) + "<br>";
            }

            parameters.put("deal_date_label", deal_date_label);
            parameters.put("deal_date_text", deal_date_text);

            parameters.put("grand_total_text", "Grand Total : ");
            parameters.put("grand_total",
                    "Rs." + Util.decimalFormatter(
                            (dealReq.getDealInvoice().getGrandTotal() - dealReq.getDealInvoice().getPaidAmount())
                                    + dealReq.getDealInvoice().getShippingCost()));
            parameters.put("amount_in_words",
                    Util.EnglishNumberToWords(
                            (dealReq.getDealInvoice().getGrandTotal() - dealReq.getDealInvoice().getPaidAmount())
                                    + dealReq.getDealInvoice().getShippingCost()));

            if (dealReq.getDealInvoice().getDealType().equalsIgnoreCase("AMC")
                    && dealReq.getDealInvoice().getAmcFromDate() != null
                    && dealReq.getDealInvoice().getAmcToDate() != null) {
                String subject = "Sub: " + dealReq.getDealInvoice().getSubject()
                        + " <br>AMC Charges for the following items for the period of "
                        + Util.sdfFormatter(dealReq.getDealInvoice().getAmcFromDate(), "dd/MM/yyyy") + " to "
                        + Util.sdfFormatter(dealReq.getDealInvoice().getAmcToDate(), "dd/MM/yyyy");
                parameters.put("subject", subject);
            } else {
                parameters.put("subject", "Sub: " + dealReq.getDealInvoice().getSubject());
            }

            parameters.put("billing_to", dealReq.getBillingToAddress());
            parameters.put("shipping_to", dealReq.getBillingToAddress());

            String price_summary_label = "", price_summary_text = "";

            price_summary_label = price_summary_label + "Original Rate : <br>";
            price_summary_text = price_summary_text + "Rs."
                    + Util.decimalFormatter(dealReq.getDealInvoice().getSubTotal()) + "<br>";

            if (dealReq.getDealInvoice().getDiscount() > 0) {
                price_summary_label = price_summary_label + "Discount : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter(dealReq.getDealInvoice().getDiscount()) + "<br>";
            }

            price_summary_label = price_summary_label + "Taxable Amount : <br>";
            price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(
                    dealReq.getDealInvoice().getSubTotal() - dealReq.getDealInvoice().getDiscount()) + "<br>";

            if (dealReq.getDealInvoice().getGstType().equalsIgnoreCase("IGST")) {
                price_summary_label = price_summary_label + "IGST : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter(dealReq.getDealInvoice().getTax()) + "<br>";
            } else {
                price_summary_label = price_summary_label + "CGST : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter(dealReq.getDealInvoice().getTax() / 2) + "<br>";

                price_summary_label = price_summary_label + "SGST : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter((dealReq.getDealInvoice().getTax() / 2)) + "<br>";
            }

            if (dealReq.getDealInvoice().getAdjustment() > 0) {
                price_summary_label = price_summary_label + "Adjustment : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + String.format("%.2f", dealReq.getDealInvoice().getAdjustment()) + "<br>";
            }

            price_summary_label = price_summary_label + "Grand Total : <br>";
            price_summary_text = price_summary_text + "Rs."
                    + Util.decimalFormatter(dealReq.getDealInvoice().getGrandTotal()) + "<br>";

            price_summary_label = price_summary_label + "Paid Amount : <br>";
            price_summary_text = price_summary_text + ""
                    + Util.decimalFormatter(dealReq.getDealInvoice().getPaidAmount()) + "<br>";

            if (dealReq.getDealInvoice().getShippingCost() > 0) {
                price_summary_label = price_summary_label + "Shipment Amount : <br>";
                price_summary_text = price_summary_text + ""
                        + Util.decimalFormatter(dealReq.getDealInvoice().getShippingCost()) + "<br>";
            }

            parameters.put("price_summary_label", price_summary_label);
            parameters.put("price_summary_text", price_summary_text);

            System.out.println(parameters.toString());

            List<Map<String, String>> datasource = new ArrayList<>();

            List<Integer> partIds = dealReq.getDealInvoiceProducts().stream().mapToInt(DealInvoiceProducts::getPartId)
                    .distinct().sorted().boxed().collect(Collectors.toList());

            int sno = 1;
            for (int partId : partIds) {
                Map<String, String> data = new HashMap<>();

                List<DealInvoiceProducts> dip = dealReq.getDealInvoiceProducts().stream()
                        .filter(_dp -> _dp.getPartId() == partId).collect(Collectors.toList());

                data.put("sno", sno + ".");
                data.put("total",
                        Util.decimalFormatter(dip.stream().map(prod -> (double) prod.getPrice() * prod.getQuantity())
                                .mapToDouble(amount -> amount).sum()));

                int _part_sno = 1;
                String name_description = "";
                for (DealInvoiceProducts prod : dip) {
                    if (!name_description.isEmpty()) {
                        name_description = name_description + "<br>";
                    }
                    name_description = name_description + _part_sno + ") " + prod.getNameQuantityAsHTMLText();
                    data.put("name_description", name_description);
                    _part_sno = _part_sno + 1;
                }
                datasource.add(data);
                sno = sno + 1;
            }

            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

            final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

            File directory = new File(contentPath + "/Invoices/" + dealReq.getDealInvoice().getId());
            System.out.println(directory.getAbsolutePath());
            if (!directory.exists()) {
                System.out.println("Directory created ::" + directory.getAbsolutePath());
                directory.mkdirs();
            }

            if (dealReq.getExportType() != null && dealReq.getExportType().equalsIgnoreCase("rtf")) {

                dealReq.getDealInvoice()
                        .setFilename(dealReq.getDealInvoice().getInvoiceNo().replaceAll("/", "-") + ".rtf");
                final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealInvoice().getFilename();
                System.out.println(filePath);

//				 Export the report to a RTF file.
                JRRtfExporter exporter = new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
                exporter.exportReport();

            } else {

                dealReq.getDealInvoice()
                        .setFilename(dealReq.getDealInvoice().getInvoiceNo().replaceAll("/", "-") + ".pdf");
                final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealInvoice().getFilename();
                System.out.println(filePath);

                // Export the report to a PDF file.
                JasperExportManager.exportReportToPdfFile(print, filePath);

            }

            invRepo.save(dealReq.getDealInvoice());

            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("DealInvoice", dealReq.getDealInvoice());
        return resp;
    }

    @Override
    public Map<String, Object> generateInvoicePDFTemplate2Lite(DealInvoiceRequest dealReq) {
        Map<String, Object> resp = new HashMap<>();

        try {

            dealReq.setDealInvoice(invRepo.findById(dealReq.getDealInvoice().getId()));

            dealReq.setDealInvoiceProducts(invProdRepo.findByInvoiceId(dealReq.getDealInvoice().getId()));

            System.out.println(dealReq.toString());

            InputStream stream = this.getClass().getResourceAsStream("/reports/Invoice/Invoice_Template_2_Lite.jrxml");

            final Map<String, Object> parameters = new HashMap<>();

            InfoDetails info = infoDetailRepo.findById(1);
            Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

            parameters.put("cmp_name", info.getCmpName());
            parameters.put("cmp_address", info.getCompanyAddressHTML2());
            parameters.put("cmp_logo_url", info.getLogoAsFile());
            parameters.put("invoice_label",
                    dealReq.getDealInvoice().getInvoiceStatus().equalsIgnoreCase("proforma invoice")
                            ? "PROFORMA INVOICE"
                            : "INVOICE");

            parameters.put("is_discount_applied", dealReq.isDiscountApplied());
            parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
            parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
            parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
            parameters.put("for_label", "For " + info.getCmpName());
            parameters.put("designation", dealReq.getDesignation());
            parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
            parameters.put("online_payment_url", info.getInstamojoPaymentURL());

            parameters.put("terms", dealReq.getDealInvoice().getTerms().replaceAll("\n", "<br>"));
            parameters.put("invoice_date", Util.sdfFormatter(dealReq.getDealInvoice().getInvoiceDate()));
            parameters.put("due_date", Util.sdfFormatter(dealReq.getDealInvoice().getDueDate()));
            parameters.put("invoice_no", String.valueOf(dealReq.getDealInvoice().getInvoiceNo()));
            parameters.put("sales_order_no", String.valueOf(dealReq.getDealInvoice().getSalesOrderNo()));
            parameters.put("purchase_order_no", String.valueOf(dealReq.getDealInvoice().getPurchaseOrderNo()));

            parameters.put("balance_amount",
                    "Rs." + Util.decimalFormatter(
                            (dealReq.getDealInvoice().getGrandTotal() - dealReq.getDealInvoice().getPaidAmount())
                                    + dealReq.getDealInvoice().getShippingCost()));
            parameters.put("amount_in_words",
                    Util.EnglishNumberToWords(
                            (dealReq.getDealInvoice().getGrandTotal() - dealReq.getDealInvoice().getPaidAmount())
                                    + dealReq.getDealInvoice().getShippingCost()));

            if (dealReq.getDealInvoice().getDealType().equalsIgnoreCase("AMC")
                    && dealReq.getDealInvoice().getAmcFromDate() != null
                    && dealReq.getDealInvoice().getAmcToDate() != null) {
                String subject = "Sub: " + dealReq.getDealInvoice().getSubject()
                        + " <br>AMC Charges for the following items for the period of "
                        + Util.sdfFormatter(dealReq.getDealInvoice().getAmcFromDate(), "dd/MM/yyyy") + " to "
                        + Util.sdfFormatter(dealReq.getDealInvoice().getAmcToDate(), "dd/MM/yyyy");
                parameters.put("subject", subject);
            } else {
                parameters.put("subject", "Sub: " + dealReq.getDealInvoice().getSubject());
            }

            parameters.put("billing_to", dealReq.getDealInvoice().getBillingAddress());
            parameters.put("shipping_to", dealReq.getDealInvoice().getShippingAddress());

            String price_summary_label = "", price_summary_text = "";

            price_summary_label = price_summary_label + "Original Rate : <br>";
            price_summary_text = price_summary_text + "Rs."
                    + Util.decimalFormatter(dealReq.getDealInvoice().getSubTotal()) + "<br>";

            if (dealReq.getDealInvoice().getDiscount() > 0) {
                price_summary_label = price_summary_label + "Discount : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter(dealReq.getDealInvoice().getDiscount()) + "<br>";
            }

            price_summary_label = price_summary_label + "Taxable Amount : <br>";
            price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(
                    dealReq.getDealInvoice().getSubTotal() - dealReq.getDealInvoice().getDiscount()) + "<br>";

            if (dealReq.getDealInvoice().getGstType().equalsIgnoreCase("IGST")) {
                price_summary_label = price_summary_label + "IGST : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter(dealReq.getDealInvoice().getTax()) + "<br>";
            } else {
                price_summary_label = price_summary_label + "CGST : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter(dealReq.getDealInvoice().getTax() / 2) + "<br>";

                price_summary_label = price_summary_label + "SGST : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter((dealReq.getDealInvoice().getTax() / 2)) + "<br>";
            }

            if (dealReq.getDealInvoice().getAdjustment() > 0) {
                price_summary_label = price_summary_label + "Adjustment : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + String.format("%.2f", dealReq.getDealInvoice().getAdjustment()) + "<br>";
            }

            price_summary_label = price_summary_label + "Grand Total : <br>";
            price_summary_text = price_summary_text + "Rs."
                    + Util.decimalFormatter(dealReq.getDealInvoice().getGrandTotal()) + "<br>";

            price_summary_label = price_summary_label + "Paid Amount : <br>";
            price_summary_text = price_summary_text + ""
                    + Util.decimalFormatter(dealReq.getDealInvoice().getPaidAmount()) + "<br>";

            if (dealReq.getDealInvoice().getShippingCost() > 0) {
                price_summary_label = price_summary_label + "Shipment Amount : <br>";
                price_summary_text = price_summary_text + ""
                        + Util.decimalFormatter(dealReq.getDealInvoice().getShippingCost()) + "<br>";
            }

            parameters.put("price_summary_label", price_summary_label);
            parameters.put("price_summary_text", price_summary_text);

            System.out.println(parameters.toString());

            List<Map<String, String>> datasource = new ArrayList<>();

            dealReq.getDealInvoiceProducts().forEach(prod -> {
                Map<String, String> data = new HashMap<>();

                data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
                data.put("quantity", prod.getQuantityAsHTMLText());
                data.put("price", Util.decimalFormatter(prod.getRateAmount()));
                data.put("rate", Util.decimalFormatter(prod.getPrice()));
                data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
                if (dealReq.getDealInvoice().getGstType().equals("IGST")) {
                    data.put("gst_percent", prod.getGstPercentage() + "%");
                    data.put("gst_amount", Util.decimalFormatter(prod.getGSTAmount()));
                } else {
                    data.put("sgst_percent", Util.getSGSTCGSTPercentage(prod.getGstPercentage()) + "%");
                    data.put("sgst_amount", Util.decimalFormatter(prod.getGSTAmount() / 2));
                    data.put("cgst_percent", Util.getSGSTCGSTPercentage(prod.getGstPercentage()) + "%");
                    data.put("cgst_amount", Util.decimalFormatter(prod.getGSTAmount() / 2));
                }

                datasource.add(data);
            });

            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

            final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

            File directory = new File(contentPath + "/Invoices/" + dealReq.getDealInvoice().getId());
            System.out.println(directory.getAbsolutePath());
            if (!directory.exists()) {
                System.out.println("Directory created ::" + directory.getAbsolutePath());
                directory.mkdirs();
            }

            if (dealReq.getExportType() != null && dealReq.getExportType().equalsIgnoreCase("rtf")) {

                dealReq.getDealInvoice()
                        .setFilename(dealReq.getDealInvoice().getInvoiceNo().replaceAll("/", "-") + ".rtf");
                final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealInvoice().getFilename();
                System.out.println(filePath);

//				 Export the report to a RTF file.
                JRRtfExporter exporter = new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
                exporter.exportReport();

            } else {

                dealReq.getDealInvoice()
                        .setFilename(dealReq.getDealInvoice().getInvoiceNo().replaceAll("/", "-") + ".pdf");
                final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealInvoice().getFilename();
                System.out.println(filePath);

                // Export the report to a PDF file.
                JasperExportManager.exportReportToPdfFile(print, filePath);

            }

            invRepo.save(dealReq.getDealInvoice());
            resp.putAll(Util.SuccessResponse());
        } catch (

                Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("DealInvoice", dealReq.getDealInvoice());
        return resp;
    }

    @Override
    public Map<String, Object> generateInvoicePDFTemplate3Lite(DealInvoiceRequest dealReq) {
        Map<String, Object> resp = new HashMap<>();

        try {

            dealReq.setDealInvoice(invRepo.findById(dealReq.getDealInvoice().getId()));

            dealReq.setDealInvoiceProducts(invProdRepo.findByInvoiceId(dealReq.getDealInvoice().getId()));

            System.out.println(dealReq.toString());

            InputStream stream = this.getClass()
                    .getResourceAsStream("/reports/FinalTemplates/Template_3/Template_3_Lite.jrxml");

            final Map<String, Object> parameters = new HashMap<>();

            InfoDetails info = infoDetailRepo.findById(1);
            Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

            parameters.put("cmp_name", info.getCmpName());
            parameters.put("cmp_address", info.getCompanyAddressHTML1());
            parameters.put("cmp_logo_url", info.getLogoAsFile());
            parameters.put("dealtype_label",
                    dealReq.getDealInvoice().getInvoiceStatus().equalsIgnoreCase("proforma invoice")
                            ? "PROFORMA INVOICE"
                            : "INVOICE");

            parameters.put("is_discount_applied", dealReq.isDiscountApplied());
            parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
            parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
            parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
            parameters.put("for_label", "For " + info.getCmpName());
            parameters.put("designation", dealReq.getDesignation());
            parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
            parameters.put("online_payment_url", info.getInstamojoPaymentURL());

            parameters.put("terms", dealReq.getDealInvoice().getTerms().replaceAll("\n", "<br>"));

            String deal_date_label = "", deal_date_text = "";

            deal_date_label = deal_date_label + "Invoice No : <br>";
            deal_date_text = deal_date_text + String.valueOf(dealReq.getDealInvoice().getInvoiceNo()) + "<br>";
            deal_date_label = deal_date_label + "Invoice Date : <br>";
            deal_date_text = deal_date_text
                    + String.valueOf(Util.sdfFormatter(dealReq.getDealInvoice().getInvoiceDate())) + "<br>";
            deal_date_label = deal_date_label + "Due Date : <br>";
            deal_date_text = deal_date_text + String.valueOf(Util.sdfFormatter(dealReq.getDealInvoice().getDueDate()))
                    + "<br>";
            deal_date_label = deal_date_label + "PO No : <br>";
            deal_date_text = deal_date_text + String.valueOf(dealReq.getDealInvoice().getPurchaseOrderNo()) + "<br>";
            deal_date_label = deal_date_label + "Sales Order No : <br>";
            deal_date_text = deal_date_text + String.valueOf(dealReq.getDealInvoice().getSalesOrderNo()) + "<br>";

            parameters.put("deal_date_label", deal_date_label);
            parameters.put("deal_date_text", deal_date_text);

            parameters.put("grand_total_text", "Grand Total : ");

            parameters.put("grand_total",
                    "Rs." + Util.decimalFormatter(
                            (dealReq.getDealInvoice().getGrandTotal() - dealReq.getDealInvoice().getPaidAmount())
                                    + dealReq.getDealInvoice().getShippingCost()));
            parameters.put("amount_in_words",
                    Util.EnglishNumberToWords(
                            (dealReq.getDealInvoice().getGrandTotal() - dealReq.getDealInvoice().getPaidAmount())
                                    + dealReq.getDealInvoice().getShippingCost()));

            if (dealReq.getDealInvoice().getDealType().equalsIgnoreCase("AMC")
                    && dealReq.getDealInvoice().getAmcFromDate() != null
                    && dealReq.getDealInvoice().getAmcToDate() != null) {
                String subject = "Sub: " + dealReq.getDealInvoice().getSubject()
                        + " <br>AMC Charges for the following items for the period of "
                        + Util.sdfFormatter(dealReq.getDealInvoice().getAmcFromDate(), "dd/MM/yyyy") + " to "
                        + Util.sdfFormatter(dealReq.getDealInvoice().getAmcToDate(), "dd/MM/yyyy");
                parameters.put("subject", subject);
            } else {
                parameters.put("subject", "Sub: " + dealReq.getDealInvoice().getSubject());
            }

            parameters.put("billing_to", dealReq.getDealInvoice().getBillingAddress());
            parameters.put("shipping_to", dealReq.getDealInvoice().getShippingAddress());

            String price_summary_label = "", price_summary_text = "";

            price_summary_label = price_summary_label + "Original Rate : <br>";
            price_summary_text = price_summary_text + "Rs."
                    + Util.decimalFormatter(dealReq.getDealInvoice().getSubTotal()) + "<br>";

            if (dealReq.getDealInvoice().getDiscount() > 0) {
                price_summary_label = price_summary_label + "Discount : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter(dealReq.getDealInvoice().getDiscount()) + "<br>";
            }

            price_summary_label = price_summary_label + "Taxable Amount : <br>";
            price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(
                    dealReq.getDealInvoice().getSubTotal() - dealReq.getDealInvoice().getDiscount()) + "<br>";

            if (dealReq.getDealInvoice().getGstType().equalsIgnoreCase("IGST")) {
                price_summary_label = price_summary_label + "IGST : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter(dealReq.getDealInvoice().getTax()) + "<br>";
            } else {
                price_summary_label = price_summary_label + "CGST : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter(dealReq.getDealInvoice().getTax() / 2) + "<br>";

                price_summary_label = price_summary_label + "SGST : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter((dealReq.getDealInvoice().getTax() / 2)) + "<br>";
            }

            if (dealReq.getDealInvoice().getAdjustment() > 0) {
                price_summary_label = price_summary_label + "Adjustment : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + String.format("%.2f", dealReq.getDealInvoice().getAdjustment()) + "<br>";
            }

            price_summary_label = price_summary_label + "Grand Total : <br>";
            price_summary_text = price_summary_text + "Rs."
                    + Util.decimalFormatter(dealReq.getDealInvoice().getGrandTotal()) + "<br>";

            if (dealReq.getDealInvoice().getPaidAmount() > 0) {
                price_summary_label = price_summary_label + "Paid Amount : <br>";
                price_summary_text = price_summary_text + ""
                        + Util.decimalFormatter(dealReq.getDealInvoice().getPaidAmount()) + "<br>";
            }

            if (dealReq.getDealInvoice().getShippingCost() > 0) {
                price_summary_label = price_summary_label + "Shipment Amount : <br>";
                price_summary_text = price_summary_text + ""
                        + Util.decimalFormatter(dealReq.getDealInvoice().getShippingCost()) + "<br>";
            }

            parameters.put("price_summary_label", price_summary_label);
            parameters.put("price_summary_text", price_summary_text);

            parameters.put("deal_amount_label", "Grand Total :");
            parameters.put("deal_amount_text", "Rs." + Util.decimalFormatter(dealReq.getDealInvoice().getGrandTotal()));

            System.out.println(parameters.toString());

            List<Map<String, String>> datasource = new ArrayList<>();

            List<Integer> partIds = dealReq.getDealInvoiceProducts().stream().mapToInt(DealInvoiceProducts::getPartId)
                    .distinct().sorted().boxed().collect(Collectors.toList());

            int sno = 1;
            for (int partId : partIds) {
                Map<String, String> data = new HashMap<>();

                List<DealInvoiceProducts> dip = dealReq.getDealInvoiceProducts().stream()
                        .filter(_dp -> _dp.getPartId() == partId).collect(Collectors.toList());

                data.put("sno", sno + ".");
                data.put("total",
                        Util.decimalFormatter(dip.stream().map(prod -> (double) prod.getPrice() * prod.getQuantity())
                                .mapToDouble(amount -> amount).sum()));

                int _part_sno = 1;
                String name_description = "";
                for (DealInvoiceProducts prod : dip) {
                    if (!name_description.isEmpty()) {
                        name_description = name_description + "<br>";
                    }
                    name_description = name_description + _part_sno + ") " + prod.getNameQuantityAsHTMLText();
                    data.put("name_description", name_description);
                    _part_sno = _part_sno + 1;
                }
                datasource.add(data);
                sno = sno + 1;
            }

            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

            final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

            File directory = new File(contentPath + "/Invoices/" + dealReq.getDealInvoice().getId());
            System.out.println(directory.getAbsolutePath());
            if (!directory.exists()) {
                System.out.println("Directory created ::" + directory.getAbsolutePath());
                directory.mkdirs();
            }

            if (dealReq.getExportType() != null && dealReq.getExportType().equalsIgnoreCase("rtf")) {

                dealReq.getDealInvoice()
                        .setFilename(dealReq.getDealInvoice().getInvoiceNo().replaceAll("/", "-") + ".rtf");
                final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealInvoice().getFilename();
                System.out.println(filePath);

//				 Export the report to a RTF file.
                JRRtfExporter exporter = new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
                exporter.exportReport();

            } else {

                dealReq.getDealInvoice()
                        .setFilename(dealReq.getDealInvoice().getInvoiceNo().replaceAll("/", "-") + ".pdf");
                final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealInvoice().getFilename();
                System.out.println(filePath);

                // Export the report to a PDF file.
                JasperExportManager.exportReportToPdfFile(print, filePath);

            }

            invRepo.save(dealReq.getDealInvoice());
            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("DealInvoice", dealReq.getDealInvoice());
        return resp;
    }

    @Override
    public Map<String, Object> generateInvoicePDFTemplate1(DealInvoiceRequest dealReq) {
        Map<String, Object> resp = new HashMap<>();

        try {

            dealReq.setDealInvoice(invRepo.findById(dealReq.getDealInvoice().getId()));

            dealReq.setDealInvoiceProducts(invProdRepo.findByInvoiceId(dealReq.getDealInvoice().getId()));

            System.out.println(dealReq.toString());

            InputStream stream = null;

            if (dealReq.getDealInvoice().getGstType().equals("IGST")) {
                stream = this.getClass()
                        .getResourceAsStream("/reports/FinalTemplates/Template_1/Template_1_IGST.jrxml");
            } else {
                stream = this.getClass()
                        .getResourceAsStream("/reports/FinalTemplates/Template_1/Template_1_CGST_SGST.jrxml");
            }

            final Map<String, Object> parameters = new HashMap<>();

            InfoDetails info = infoDetailRepo.findById(1);
            Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

            parameters.put("cmp_name", info.getCmpName());
            parameters.put("cmp_address", info.getCompanyAddressHTML1());
            parameters.put("cmp_logo_url", info.getLogoAsFile());
            parameters.put("dealtype_label",
                    dealReq.getDealInvoice().getInvoiceStatus().equalsIgnoreCase("proforma invoice")
                            ? "PROFORMA INVOICE"
                            : "INVOICE");

            parameters.put("is_discount_applied", dealReq.isDiscountApplied());
            parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
            parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
            parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
            parameters.put("for_label", "For " + info.getCmpName());
            parameters.put("designation", dealReq.getDesignation());
            parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
            parameters.put("online_payment_url", info.getInstamojoPaymentURL());

            parameters.put("terms", dealReq.getDealInvoice().getTerms().replaceAll("\n", "<br>"));
//			parameters.put("invoice_date", );
//			parameters.put("due_date", Util.sdfFormatter(dealReq.getDealInvoice().getDueDate()));
//			parameters.put("invoice_no", String.valueOf(dealReq.getDealInvoice().getInvoiceNo()));
//			parameters.put("sales_order_no", String.valueOf(dealReq.getDealInvoice().getSalesOrderNo()));
//			parameters.put("purchase_order_no", String.valueOf(dealReq.getDealInvoice().getPurchaseOrderNo()));

            String deal_date_label = "", deal_date_text = "";

            deal_date_label = deal_date_label + "Invoice No. : <br>";
            deal_date_text = deal_date_text + String.valueOf(dealReq.getDealInvoice().getInvoiceNo()) + "<br>";
            deal_date_label = deal_date_label + "Invoice Date : <br>";
            deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealInvoice().getInvoiceDate()) + "<br>";
            deal_date_label = deal_date_label + "Due Date : <br>";
            deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealInvoice().getDueDate()) + "<br>";

            if (dealReq.getDealInvoice().getPurchaseOrderNo() != null
                    && dealReq.getDealInvoice().getPurchaseOrderNo() != "") {
                deal_date_label = deal_date_label + "PO Number : <br>";
                deal_date_text = deal_date_text + String.valueOf(dealReq.getDealInvoice().getPurchaseOrderNo())
                        + "<br>";
            }
            if (dealReq.getDealInvoice().getSalesOrderNo() != null
                    && dealReq.getDealInvoice().getSalesOrderNo() != "") {
                deal_date_label = deal_date_label + "Sales Order No : <br>";
                deal_date_text = deal_date_text + String.valueOf(dealReq.getDealInvoice().getSalesOrderNo()) + "<br>";
            }

            parameters.put("deal_date_label", deal_date_label);
            parameters.put("deal_date_text", deal_date_text);

            parameters.put("grand_total_text", "Grand Total : ");
            parameters.put("grand_total",
                    "Rs." + Util.decimalFormatter(
                            (dealReq.getDealInvoice().getGrandTotal() - dealReq.getDealInvoice().getPaidAmount())
                                    + dealReq.getDealInvoice().getShippingCost()));
            parameters.put("amount_in_words",
                    Util.EnglishNumberToWords(
                            (dealReq.getDealInvoice().getGrandTotal() - dealReq.getDealInvoice().getPaidAmount())
                                    + dealReq.getDealInvoice().getShippingCost()));

            if (dealReq.getDealInvoice().getDealType().equalsIgnoreCase("AMC")
                    && dealReq.getDealInvoice().getAmcFromDate() != null
                    && dealReq.getDealInvoice().getAmcToDate() != null) {
                String subject = "Sub: " + dealReq.getDealInvoice().getSubject()
                        + " <br>AMC Charges for the following items for the period of "
                        + Util.sdfFormatter(dealReq.getDealInvoice().getAmcFromDate(), "dd/MM/yyyy") + " to "
                        + Util.sdfFormatter(dealReq.getDealInvoice().getAmcToDate(), "dd/MM/yyyy");
                parameters.put("subject", subject);
            } else {
                parameters.put("subject", "Sub: " + dealReq.getDealInvoice().getSubject());
            }

            parameters.put("billing_to", dealReq.getDealInvoice().getBillingAddress());
            parameters.put("shipping_to", dealReq.getDealInvoice().getShippingAddress());

            String price_summary_label = "", price_summary_text = "";

            price_summary_label = price_summary_label + "Original Rate : <br>";
            price_summary_text = price_summary_text + "Rs."
                    + Util.decimalFormatter(dealReq.getDealInvoice().getSubTotal()) + "<br>";

            if (dealReq.getDealInvoice().getDiscount() > 0) {
                price_summary_label = price_summary_label + "Discount : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter(dealReq.getDealInvoice().getDiscount()) + "<br>";
            }

            price_summary_label = price_summary_label + "Taxable Amount : <br>";
            price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(
                    dealReq.getDealInvoice().getSubTotal() - dealReq.getDealInvoice().getDiscount()) + "<br>";

            if (dealReq.getDealInvoice().getGstType().equalsIgnoreCase("IGST")) {
                price_summary_label = price_summary_label + "IGST : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter(dealReq.getDealInvoice().getTax()) + "<br>";
            } else {
                price_summary_label = price_summary_label + "CGST : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter(dealReq.getDealInvoice().getTax() / 2) + "<br>";

                price_summary_label = price_summary_label + "SGST : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter((dealReq.getDealInvoice().getTax() / 2)) + "<br>";
            }

            if (dealReq.getDealInvoice().getAdjustment() > 0) {
                price_summary_label = price_summary_label + "Adjustment : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + String.format("%.2f", dealReq.getDealInvoice().getAdjustment()) + "<br>";
            }

            price_summary_label = price_summary_label + "Grand Total : <br>";
            price_summary_text = price_summary_text + "Rs."
                    + Util.decimalFormatter(dealReq.getDealInvoice().getGrandTotal()) + "<br>";

            price_summary_label = price_summary_label + "Paid Amount : <br>";
            price_summary_text = price_summary_text + ""
                    + Util.decimalFormatter(dealReq.getDealInvoice().getPaidAmount()) + "<br>";

            if (dealReq.getDealInvoice().getShippingCost() > 0) {
                price_summary_label = price_summary_label + "Shipment Amount : <br>";
                price_summary_text = price_summary_text + ""
                        + Util.decimalFormatter(dealReq.getDealInvoice().getShippingCost()) + "<br>";
            }

            parameters.put("price_summary_label", price_summary_label);
            parameters.put("price_summary_text", price_summary_text);

            System.out.println(parameters.toString());

            List<Map<String, String>> datasource = new ArrayList<>();

            List<Integer> partIds = dealReq.getDealInvoiceProducts().stream().mapToInt(DealInvoiceProducts::getPartId)
                    .distinct().sorted().boxed().collect(Collectors.toList());

            int sno = 1;
            for (int partId : partIds) {

                for (DealInvoiceProducts prod : dealReq.getDealInvoiceProducts()) {
                    if (prod.getPartId() == partId) {

                        Map<String, String> data = new HashMap<>();

                        data.put("sno", sno + ".");
                        data.put("type", "product");
                        data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
                        data.put("quantity", prod.getQuantityAsHTMLText());
                        data.put("price", Util.decimalFormatter(prod.getRateAmount()));
                        data.put("rate", Util.decimalFormatter(prod.getPrice()));
                        data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
                        if (dealReq.getDealInvoice().getGstType().equals("IGST")) {
                            data.put("gst_percent", prod.getGstPercentage() + "%");
                            data.put("gst_amount", Util.decimalFormatter(prod.getGSTAmount()));
                        } else {
                            data.put("sgst_percent", Util.getSGSTCGSTPercentage(prod.getGstPercentage()) + "%");
                            data.put("sgst_amount", Util.decimalFormatter(prod.getGSTAmount() / 2));
                            data.put("cgst_percent", Util.getSGSTCGSTPercentage(prod.getGstPercentage()) + "%");
                            data.put("cgst_amount", Util.decimalFormatter(prod.getGSTAmount() / 2));
                        }

                        datasource.add(data);
                        sno = sno + 1;
                    }
                }

                if (partIds.size() > 1) {
                    Map<String, String> data = new HashMap<>();

                    Double totalAmount = dealReq.getDealInvoiceProducts().stream()
                            .filter(prod -> prod.getPartId() == partId).mapToDouble(DealInvoiceProducts::getTotalAmount)
                            .sum();

                    data.put("sno", "");
                    data.put("type", "subtotal");
                    data.put("name_description", "");
                    data.put("quantity", "");
                    data.put("price", "");
                    data.put("rate", "");
                    data.put("total", Util.decimalFormatter(totalAmount));
                    if (dealReq.getDealInvoice().getGstType().equals("IGST")) {
                        data.put("gst_percent", "");
                        data.put("gst_amount", "Sub Total");
                    } else {
                        data.put("sgst_percent", "");
                        data.put("sgst_amount", "");
                        data.put("cgst_percent", "");
                        data.put("cgst_amount", "Sub Total");
                    }

                    datasource.add(data);
                }
            }

//			dealReq.getDealInvoiceProducts().forEach(prod -> {
//				Map<String, String> data = new HashMap<>();
//
//				data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
//				data.put("quantity", prod.getQuantityAsHTMLText());
//				data.put("price", Util.decimalFormatter(prod.getRateAmount()));
//				data.put("rate", Util.decimalFormatter(prod.getPrice()));
//				data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
//				if (dealReq.getDealInvoice().getGstType().equals("IGST")) {
//					data.put("gst_percent", prod.getGstPercentage() + "%");
//					data.put("gst_amount", Util.decimalFormatter(prod.getGSTAmount()));
//				} else {
//					data.put("sgst_percent", Util.getSGSTCGSTPercentage(prod.getGstPercentage()) + "%");
//					data.put("sgst_amount", Util.decimalFormatter(prod.getGSTAmount() / 2));
//					data.put("cgst_percent", Util.getSGSTCGSTPercentage(prod.getGstPercentage()) + "%");
//					data.put("cgst_amount", Util.decimalFormatter(prod.getGSTAmount() / 2));
//				}
//
//				datasource.add(data);
//			});

            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

            final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

            File directory = new File(contentPath + "/Invoices/" + dealReq.getDealInvoice().getId());
            System.out.println(directory.getAbsolutePath());
            if (!directory.exists()) {
                System.out.println("Directory created ::" + directory.getAbsolutePath());
                directory.mkdirs();
            }

            if (dealReq.getExportType() != null && dealReq.getExportType().equalsIgnoreCase("rtf")) {

                dealReq.getDealInvoice()
                        .setFilename(dealReq.getDealInvoice().getInvoiceNo().replaceAll("/", "-") + ".rtf");
                final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealInvoice().getFilename();
                System.out.println(filePath);

//				 Export the report to a RTF file.
                JRRtfExporter exporter = new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
                exporter.exportReport();

            } else {

                dealReq.getDealInvoice()
                        .setFilename(dealReq.getDealInvoice().getInvoiceNo().replaceAll("/", "-") + ".pdf");
                final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealInvoice().getFilename();
                System.out.println(filePath);

                // Export the report to a PDF file.
                JasperExportManager.exportReportToPdfFile(print, filePath);

            }

            invRepo.save(dealReq.getDealInvoice());

            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("DealInvoice", dealReq.getDealInvoice());
        return resp;
    }

    @Override
    public Map<String, Object> generateInvoicePDFTemplate2(DealInvoiceRequest dealReq) {
        Map<String, Object> resp = new HashMap<>();

        try {

            dealReq.setDealInvoice(invRepo.findById(dealReq.getDealInvoice().getId()));

            dealReq.setDealInvoiceProducts(invProdRepo.findByInvoiceId(dealReq.getDealInvoice().getId()));

            System.out.println(dealReq.toString());

            InputStream stream = null;

            if (dealReq.getDealInvoice().getGstType().equals("IGST")) {
                stream = this.getClass().getResourceAsStream("/reports/Invoice/Invoice_Template_2_IGST.jrxml");
            } else {
                stream = this.getClass().getResourceAsStream("/reports/Invoice/Invoice_Template_2_CGST_SGST.jrxml");
            }

            final Map<String, Object> parameters = new HashMap<>();

            InfoDetails info = infoDetailRepo.findById(1);
            Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

            parameters.put("cmp_name", info.getCmpName());
            parameters.put("cmp_address", info.getCompanyAddressHTML2());
            parameters.put("cmp_logo_url", info.getLogoAsFile());
            parameters.put("invoice_label",
                    dealReq.getDealInvoice().getInvoiceStatus().equalsIgnoreCase("proforma invoice")
                            ? "PROFORMA INVOICE"
                            : "INVOICE");

            parameters.put("is_discount_applied", dealReq.isDiscountApplied());
            parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
            parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
            parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
            parameters.put("for_label", "For " + info.getCmpName());
            parameters.put("designation", dealReq.getDesignation());
            parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
            parameters.put("online_payment_url", info.getInstamojoPaymentURL());

            parameters.put("terms", dealReq.getDealInvoice().getTerms().replaceAll("\n", "<br>"));
            parameters.put("invoice_date", Util.sdfFormatter(dealReq.getDealInvoice().getInvoiceDate()));
            parameters.put("due_date", Util.sdfFormatter(dealReq.getDealInvoice().getDueDate()));
            parameters.put("invoice_no", String.valueOf(dealReq.getDealInvoice().getInvoiceNo()));
            parameters.put("sales_order_no", String.valueOf(dealReq.getDealInvoice().getSalesOrderNo()));
            parameters.put("purchase_order_no", String.valueOf(dealReq.getDealInvoice().getPurchaseOrderNo()));

            parameters.put("balance_amount",
                    "Rs." + Util.decimalFormatter(
                            (dealReq.getDealInvoice().getGrandTotal() - dealReq.getDealInvoice().getPaidAmount())
                                    + dealReq.getDealInvoice().getShippingCost()));
            parameters.put("amount_in_words",
                    Util.EnglishNumberToWords(
                            (dealReq.getDealInvoice().getGrandTotal() - dealReq.getDealInvoice().getPaidAmount())
                                    + dealReq.getDealInvoice().getShippingCost()));

            if (dealReq.getDealInvoice().getDealType().equalsIgnoreCase("AMC")
                    && dealReq.getDealInvoice().getAmcFromDate() != null
                    && dealReq.getDealInvoice().getAmcToDate() != null) {
                String subject = "Sub: " + dealReq.getDealInvoice().getSubject()
                        + " <br>AMC Charges for the following items for the period of "
                        + Util.sdfFormatter(dealReq.getDealInvoice().getAmcFromDate(), "dd/MM/yyyy") + " to "
                        + Util.sdfFormatter(dealReq.getDealInvoice().getAmcToDate(), "dd/MM/yyyy");
                parameters.put("subject", subject);
            } else {
                parameters.put("subject", "Sub: " + dealReq.getDealInvoice().getSubject());
            }

            parameters.put("billing_to", dealReq.getDealInvoice().getBillingAddress());
            parameters.put("shipping_to", dealReq.getDealInvoice().getShippingAddress());

            String price_summary_label = "", price_summary_text = "";

            price_summary_label = price_summary_label + "Original Rate : <br>";
            price_summary_text = price_summary_text + "Rs."
                    + Util.decimalFormatter(dealReq.getDealInvoice().getSubTotal()) + "<br>";

            if (dealReq.getDealInvoice().getDiscount() > 0) {
                price_summary_label = price_summary_label + "Discount : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter(dealReq.getDealInvoice().getDiscount()) + "<br>";
            }

            price_summary_label = price_summary_label + "Taxable Amount : <br>";
            price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(
                    dealReq.getDealInvoice().getSubTotal() - dealReq.getDealInvoice().getDiscount()) + "<br>";

            if (dealReq.getDealInvoice().getGstType().equalsIgnoreCase("IGST")) {
                price_summary_label = price_summary_label + "IGST : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter(dealReq.getDealInvoice().getTax()) + "<br>";
            } else {
                price_summary_label = price_summary_label + "CGST : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter(dealReq.getDealInvoice().getTax() / 2) + "<br>";

                price_summary_label = price_summary_label + "SGST : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter((dealReq.getDealInvoice().getTax() / 2)) + "<br>";
            }

            if (dealReq.getDealInvoice().getAdjustment() > 0) {
                price_summary_label = price_summary_label + "Adjustment : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + String.format("%.2f", dealReq.getDealInvoice().getAdjustment()) + "<br>";
            }

            price_summary_label = price_summary_label + "Grand Total : <br>";
            price_summary_text = price_summary_text + "Rs."
                    + Util.decimalFormatter(dealReq.getDealInvoice().getGrandTotal()) + "<br>";

            price_summary_label = price_summary_label + "Paid Amount : <br>";
            price_summary_text = price_summary_text + ""
                    + Util.decimalFormatter(dealReq.getDealInvoice().getPaidAmount()) + "<br>";

            if (dealReq.getDealInvoice().getShippingCost() > 0) {
                price_summary_label = price_summary_label + "Shipment Amount : <br>";
                price_summary_text = price_summary_text + ""
                        + Util.decimalFormatter(dealReq.getDealInvoice().getShippingCost()) + "<br>";
            }

            parameters.put("price_summary_label", price_summary_label);
            parameters.put("price_summary_text", price_summary_text);

            System.out.println(parameters.toString());

            List<Map<String, String>> datasource = new ArrayList<>();

            dealReq.getDealInvoiceProducts().forEach(prod -> {
                Map<String, String> data = new HashMap<>();

                data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
                data.put("quantity", prod.getQuantityAsHTMLText());
                data.put("price", Util.decimalFormatter(prod.getRateAmount()));
                data.put("rate", Util.decimalFormatter(prod.getPrice()));
                data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
                if (dealReq.getDealInvoice().getGstType().equals("IGST")) {
                    data.put("gst_percent", prod.getGstPercentage() + "%");
                    data.put("gst_amount", Util.decimalFormatter(prod.getGSTAmount()));
                } else {
                    data.put("sgst_percent", Util.getSGSTCGSTPercentage(prod.getGstPercentage()) + "%");
                    data.put("sgst_amount", Util.decimalFormatter(prod.getGSTAmount() / 2));
                    data.put("cgst_percent", Util.getSGSTCGSTPercentage(prod.getGstPercentage()) + "%");
                    data.put("cgst_amount", Util.decimalFormatter(prod.getGSTAmount() / 2));
                }

                datasource.add(data);
            });

            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

            final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

            File directory = new File(contentPath + "/Invoices/" + dealReq.getDealInvoice().getId());
            System.out.println(directory.getAbsolutePath());
            if (!directory.exists()) {
                System.out.println("Directory created ::" + directory.getAbsolutePath());
                directory.mkdirs();
            }

            if (dealReq.getExportType() != null && dealReq.getExportType().equalsIgnoreCase("rtf")) {

                dealReq.getDealInvoice()
                        .setFilename(dealReq.getDealInvoice().getInvoiceNo().replaceAll("/", "-") + ".rtf");
                final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealInvoice().getFilename();
                System.out.println(filePath);

//				 Export the report to a RTF file.
                JRRtfExporter exporter = new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
                exporter.exportReport();

            } else {

                dealReq.getDealInvoice()
                        .setFilename(dealReq.getDealInvoice().getInvoiceNo().replaceAll("/", "-") + ".pdf");
                final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealInvoice().getFilename();
                System.out.println(filePath);

                // Export the report to a PDF file.
                JasperExportManager.exportReportToPdfFile(print, filePath);

            }

            invRepo.save(dealReq.getDealInvoice());
            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("DealInvoice", dealReq.getDealInvoice());
        return resp;
    }

    @Override
    public Map<String, Object> generateInvoicePDFTemplate3(DealInvoiceRequest dealReq) {
        Map<String, Object> resp = new HashMap<>();

        try {

            dealReq.setDealInvoice(invRepo.findById(dealReq.getDealInvoice().getId()));

            dealReq.setDealInvoiceProducts(invProdRepo.findByInvoiceId(dealReq.getDealInvoice().getId()));

            System.out.println(dealReq.toString());

            InputStream stream = null;

            if (dealReq.getDealInvoice().getGstType().equals("IGST")) {
                stream = this.getClass()
                        .getResourceAsStream("/reports/FinalTemplates/Template_3/Template_3_IGST.jrxml");
            } else {
                stream = this.getClass()
                        .getResourceAsStream("/reports/FinalTemplates/Template_3/Template_3_CGST_SGST.jrxml");
            }

//			if (dealReq.getDealInvoice().getGstType().equals("IGST")) {
//				stream = this.getClass().getResourceAsStream("/reports/Deal_Templates/Deal_Template_3_IGST.jrxml");
//			} else {
//				stream = this.getClass().getResourceAsStream("/reports/Deal_Templates/Deal_Template_3_CGST_SGST.jrxml");
//			}

            final Map<String, Object> parameters = new HashMap<>();

            InfoDetails info = infoDetailRepo.findById(1);
            Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

            parameters.put("cmp_name", info.getCmpName());
            parameters.put("cmp_address", info.getCompanyAddressHTML1());
            parameters.put("cmp_logo_url", info.getLogoAsFile());
            parameters.put("dealtype_label",
                    dealReq.getDealInvoice().getInvoiceStatus().equalsIgnoreCase("proforma invoice")
                            ? "PROFORMA INVOICE"
                            : "INVOICE");

            parameters.put("is_discount_applied", dealReq.isDiscountApplied());
            parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
            parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
            parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
            parameters.put("for_label", "For " + info.getCmpName());
            parameters.put("designation", dealReq.getDesignation());
            parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
            parameters.put("online_payment_url", info.getInstamojoPaymentURL());

            parameters.put("terms", dealReq.getDealInvoice().getTerms().replaceAll("\n", "<br>"));
//			parameters.put("invoice_date", Util.sdfFormatter(dealReq.getDealInvoice().getInvoiceDate()));
//			parameters.put("due_date", Util.sdfFormatter(dealReq.getDealInvoice().getDueDate()));
//			parameters.put("invoice_no", String.valueOf(dealReq.getDealInvoice().getInvoiceNo()));
//			parameters.put("sales_order_no", String.valueOf(dealReq.getDealInvoice().getSalesOrderNo()));
//			parameters.put("purchase_order_no", String.valueOf(dealReq.getDealInvoice().getPurchaseOrderNo()));

            String deal_date_label = "", deal_date_text = "";

            deal_date_label = deal_date_label + "Invoice No : <br>";
            deal_date_text = deal_date_text + String.valueOf(dealReq.getDealInvoice().getInvoiceNo()) + "<br>";
            deal_date_label = deal_date_label + "Invoice Date : <br>";
            deal_date_text = deal_date_text
                    + String.valueOf(Util.sdfFormatter(dealReq.getDealInvoice().getInvoiceDate())) + "<br>";
            deal_date_label = deal_date_label + "Due Date : <br>";
            deal_date_text = deal_date_text + String.valueOf(Util.sdfFormatter(dealReq.getDealInvoice().getDueDate()))
                    + "<br>";
            deal_date_label = deal_date_label + "PO No : <br>";
            deal_date_text = deal_date_text + String.valueOf(dealReq.getDealInvoice().getPurchaseOrderNo()) + "<br>";
            deal_date_label = deal_date_label + "Sales Order No : <br>";
            deal_date_text = deal_date_text + String.valueOf(dealReq.getDealInvoice().getSalesOrderNo()) + "<br>";

            parameters.put("deal_date_label", deal_date_label);
            parameters.put("deal_date_text", deal_date_text);

            parameters.put("grand_total_text", "Grand Total : ");

            parameters.put("grand_total",
                    "Rs." + Util.decimalFormatter(
                            (dealReq.getDealInvoice().getGrandTotal() - dealReq.getDealInvoice().getPaidAmount())
                                    + dealReq.getDealInvoice().getShippingCost()));
            parameters.put("amount_in_words",
                    Util.EnglishNumberToWords(
                            (dealReq.getDealInvoice().getGrandTotal() - dealReq.getDealInvoice().getPaidAmount())
                                    + dealReq.getDealInvoice().getShippingCost()));

            if (dealReq.getDealInvoice().getDealType().equalsIgnoreCase("AMC")
                    && dealReq.getDealInvoice().getAmcFromDate() != null
                    && dealReq.getDealInvoice().getAmcToDate() != null) {
                String subject = "Sub: " + dealReq.getDealInvoice().getSubject()
                        + " <br>AMC Charges for the following items for the period of "
                        + Util.sdfFormatter(dealReq.getDealInvoice().getAmcFromDate(), "dd/MM/yyyy") + " to "
                        + Util.sdfFormatter(dealReq.getDealInvoice().getAmcToDate(), "dd/MM/yyyy");
                parameters.put("subject", subject);
            } else {
                parameters.put("subject", "Sub: " + dealReq.getDealInvoice().getSubject());
            }

            parameters.put("billing_to", dealReq.getDealInvoice().getBillingAddress());
            parameters.put("shipping_to", dealReq.getDealInvoice().getShippingAddress());

            String price_summary_label = "", price_summary_text = "";

            price_summary_label = price_summary_label + "Original Rate : <br>";
            price_summary_text = price_summary_text + "Rs."
                    + Util.decimalFormatter(dealReq.getDealInvoice().getSubTotal()) + "<br>";

            if (dealReq.getDealInvoice().getDiscount() > 0) {
                price_summary_label = price_summary_label + "Discount : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter(dealReq.getDealInvoice().getDiscount()) + "<br>";
            }

            price_summary_label = price_summary_label + "Taxable Amount : <br>";
            price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(
                    dealReq.getDealInvoice().getSubTotal() - dealReq.getDealInvoice().getDiscount()) + "<br>";

            if (dealReq.getDealInvoice().getGstType().equalsIgnoreCase("IGST")) {
                price_summary_label = price_summary_label + "IGST : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter(dealReq.getDealInvoice().getTax()) + "<br>";
            } else {
                price_summary_label = price_summary_label + "CGST : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter(dealReq.getDealInvoice().getTax() / 2) + "<br>";

                price_summary_label = price_summary_label + "SGST : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + Util.decimalFormatter((dealReq.getDealInvoice().getTax() / 2)) + "<br>";
            }

            if (dealReq.getDealInvoice().getAdjustment() > 0) {
                price_summary_label = price_summary_label + "Adjustment : <br>";
                price_summary_text = price_summary_text + "Rs."
                        + String.format("%.2f", dealReq.getDealInvoice().getAdjustment()) + "<br>";
            }

            price_summary_label = price_summary_label + "Grand Total : <br>";
            price_summary_text = price_summary_text + "Rs."
                    + Util.decimalFormatter(dealReq.getDealInvoice().getGrandTotal()) + "<br>";

            if (dealReq.getDealInvoice().getPaidAmount() > 0) {
                price_summary_label = price_summary_label + "Paid Amount : <br>";
                price_summary_text = price_summary_text + ""
                        + Util.decimalFormatter(dealReq.getDealInvoice().getPaidAmount()) + "<br>";
            }

            if (dealReq.getDealInvoice().getShippingCost() > 0) {
                price_summary_label = price_summary_label + "Shipment Amount : <br>";
                price_summary_text = price_summary_text + ""
                        + Util.decimalFormatter(dealReq.getDealInvoice().getShippingCost()) + "<br>";
            }

            parameters.put("price_summary_label", price_summary_label);
            parameters.put("price_summary_text", price_summary_text);

            parameters.put("deal_amount_label", "Grand Total :");
            parameters.put("deal_amount_text", "Rs." + Util.decimalFormatter(dealReq.getDealInvoice().getGrandTotal()));

            System.out.println(parameters.toString());

            List<Map<String, String>> datasource = new ArrayList<>();

            List<Integer> partIds = dealReq.getDealInvoiceProducts().stream().mapToInt(DealInvoiceProducts::getPartId)
                    .distinct().sorted().boxed().collect(Collectors.toList());

            int sno = 1;
            for (int partId : partIds) {

                for (DealInvoiceProducts prod : dealReq.getDealInvoiceProducts()) {
                    if (prod.getPartId() == partId) {

                        Map<String, String> data = new HashMap<>();

                        data.put("sno", sno + ".");
                        data.put("type", "product");
                        data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
                        data.put("quantity", prod.getQuantityAsHTMLText());
                        data.put("price", Util.decimalFormatter(prod.getRateAmount()));
                        data.put("rate", Util.decimalFormatter(prod.getPrice()));
                        data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
                        if (dealReq.getDealInvoice().getGstType().equals("IGST")) {
                            data.put("gst_percent", prod.getGstPercentage() + "%");
                            data.put("gst_amount", Util.decimalFormatter(prod.getGSTAmount()));
                        } else {
                            data.put("sgst_percent", Util.getSGSTCGSTPercentage(prod.getGstPercentage()) + "%");
                            data.put("sgst_amount", Util.decimalFormatter(prod.getGSTAmount() / 2));
                            data.put("cgst_percent", Util.getSGSTCGSTPercentage(prod.getGstPercentage()) + "%");
                            data.put("cgst_amount", Util.decimalFormatter(prod.getGSTAmount() / 2));
                        }

                        datasource.add(data);
                        sno = sno + 1;
                    }
                }

                if (partIds.size() > 1) {
                    Map<String, String> data = new HashMap<>();

                    Double totalAmount = dealReq.getDealInvoiceProducts().stream()
                            .filter(prod -> prod.getPartId() == partId).mapToDouble(DealInvoiceProducts::getTotalAmount)
                            .sum();

                    data.put("sno", "");
                    data.put("type", "subtotal");
                    data.put("name_description", "");
                    data.put("quantity", "");
                    data.put("price", "");
                    data.put("rate", "");
                    data.put("total", Util.decimalFormatter(totalAmount));
                    if (dealReq.getDealInvoice().getGstType().equals("IGST")) {
                        data.put("gst_percent", "");
                        data.put("gst_amount", "Sub Total");
                    } else {
                        data.put("sgst_percent", "");
                        data.put("sgst_amount", "");
                        data.put("cgst_percent", "");
                        data.put("cgst_amount", "Sub Total");
                    }

                    datasource.add(data);
                }
            }

//			dealReq.getDealInvoiceProducts().forEach(prod -> {
//				Map<String, String> data = new HashMap<>();
//
//				data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
//				data.put("quantity", prod.getQuantityAsHTMLText());
//				data.put("price", Util.decimalFormatter(prod.getRateAmount()));
//				data.put("rate", Util.decimalFormatter(prod.getPrice()));
//				data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
//				if (dealReq.getDealInvoice().getGstType().equals("IGST")) {
//					data.put("gst_percent", prod.getGstPercentage() + "%");
//					data.put("gst_amount", Util.decimalFormatter(prod.getGSTAmount()));
//				} else {
//					data.put("sgst_percent", Util.getSGSTCGSTPercentage(prod.getGstPercentage()) + "%");
//					data.put("sgst_amount", Util.decimalFormatter(prod.getGSTAmount() / 2));
//					data.put("cgst_percent", Util.getSGSTCGSTPercentage(prod.getGstPercentage()) + "%");
//					data.put("cgst_amount", Util.decimalFormatter(prod.getGSTAmount() / 2));
//				}
//
//				datasource.add(data);
//			});

            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

            final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

            File directory = new File(contentPath + "/Invoices/" + dealReq.getDealInvoice().getId());
            System.out.println(directory.getAbsolutePath());
            if (!directory.exists()) {
                System.out.println("Directory created ::" + directory.getAbsolutePath());
                directory.mkdirs();
            }

            if (dealReq.getExportType() != null && dealReq.getExportType().equalsIgnoreCase("rtf")) {

                dealReq.getDealInvoice()
                        .setFilename(dealReq.getDealInvoice().getInvoiceNo().replaceAll("/", "-") + ".rtf");
                final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealInvoice().getFilename();
                System.out.println(filePath);

//				 Export the report to a RTF file.
                JRRtfExporter exporter = new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
                exporter.exportReport();

            } else {

                dealReq.getDealInvoice()
                        .setFilename(dealReq.getDealInvoice().getInvoiceNo().replaceAll("/", "-") + ".pdf");
                final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealInvoice().getFilename();
                System.out.println(filePath);

                // Export the report to a PDF file.
                JasperExportManager.exportReportToPdfFile(print, filePath);

            }

            invRepo.save(dealReq.getDealInvoice());
            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("DealInvoice", dealReq.getDealInvoice());
        return resp;
    }

    @Override
    public Map<String, Object> UploadGeneratedInvoicePDF(int dealInvoiceId, MultipartFile file) {
        Map<String, Object> resp = new HashMap<>();

        DealInvoice di = invRepo.findById(dealInvoiceId);

        File directory = new File(contentPath + "/Invoices/" + dealInvoiceId);
        System.out.println(directory.getAbsolutePath());
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File convertFile = new File(directory.getAbsoluteFile() + "/" + file.getOriginalFilename());

        try {
            convertFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(convertFile);
            fout.write(file.getBytes());
            fout.close();

            di.setFilename(file.getOriginalFilename());
            di = invRepo.save(di);

            resp.putAll(Util.SuccessResponse());
        } catch (IOException e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("DealInvoice", di);
        return resp;
    }

    @Override
    public Map<String, Object> UploadInvoiceSatisfactoyPDF(int dealInvoiceId, MultipartFile file) {
        Map<String, Object> resp = new HashMap<>();

        DealInvoice di = invRepo.findById(dealInvoiceId);

        File directory = new File(contentPath + "/Invoices/" + dealInvoiceId);
        System.out.println(directory.getAbsolutePath());
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File convertFile = new File(directory.getAbsoluteFile() + "/SC_" + file.getOriginalFilename());

        try {
            convertFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(convertFile);
            fout.write(file.getBytes());
            fout.close();

            di.setSatisfactoryCertificate("SC_" + file.getOriginalFilename());
            di = invRepo.save(di);

            resp.putAll(Util.SuccessResponse());
        } catch (IOException e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("DealInvoice", di);
        return resp;
    }

    @Override
    public Map<String, Object> UploadInvoiceWorkCompletionPDF(int dealInvoiceId, MultipartFile file) {
        Map<String, Object> resp = new HashMap<>();

        DealInvoice di = invRepo.findById(dealInvoiceId);

        File directory = new File(contentPath + "/Invoices/" + dealInvoiceId);
        System.out.println(directory.getAbsolutePath());
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File convertFile = new File(directory.getAbsoluteFile() + "/WC_" + file.getOriginalFilename());

        try {
            convertFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(convertFile);
            fout.write(file.getBytes());
            fout.close();

            di.setWorkCompletionCertificate("WC_" + file.getOriginalFilename());
            di = invRepo.save(di);

            resp.putAll(Util.SuccessResponse());
        } catch (IOException e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("DealInvoice", di);
        return resp;
    }

    @Override
    public Map<String, Object> generateDeliveryChallanPDF1(DealInvoiceRequest dealReq) {

        Map<String, Object> resp = new HashMap<>();

        try {

            dealReq.setDeliveryChallan(dcRepo.findById(dealReq.getDeliveryChallan().getId()));

            dealReq.setDealInvoice(invRepo.findById(dealReq.getDeliveryChallan().getInvoiceId()));

            dealReq.setDeal(dealRepo.findById(dealReq.getDeliveryChallan().getDealId()));

            InputStream stream = this.getClass().getResourceAsStream("/reports/Delivery_Challan/DC_Template_1.jrxml");

            final Map<String, Object> parameters = new HashMap<>();

            InfoDetails info = infoDetailRepo.findById(1);

            parameters.put("cmp_name", info.getCmpName());
            parameters.put("cmp_address", info.getCompanyAddressHTML1());
            parameters.put("cmp_logo_url", info.getLogoAsFile());
            parameters.put("dealtype_label", "Delivery Challan");

            String deal_date_label = "", deal_date_text = "";

            deal_date_label = deal_date_label + "Invoice No : <br>";
            deal_date_text = deal_date_text + String.valueOf(dealReq.getDeliveryChallan().getInvoiceNo()) + "<br>";

            if (dealReq.getDealInvoice() != null) {

                deal_date_label = deal_date_label + "Invoice Date : <br>";
                deal_date_text = deal_date_text
                        + String.valueOf(Util.sdfFormatter(dealReq.getDealInvoice().getInvoiceDate())) + "<br>";

                deal_date_label = deal_date_label + "Due Date : <br>";
                deal_date_text = deal_date_text
                        + String.valueOf(Util.sdfFormatter(dealReq.getDealInvoice().getDueDate())) + "<br>";

                deal_date_label = deal_date_label + "Sales Order No : <br>";
                deal_date_text = deal_date_text + String.valueOf(dealReq.getDealInvoice().getSalesOrderNo()) + "<br>";

            } else {

                deal_date_label = deal_date_label + "Invoice Date : <br>";
                deal_date_text = deal_date_text + "<br>";

                deal_date_label = deal_date_label + "Due Date : <br>";
                deal_date_text = deal_date_text + "<br>";

                deal_date_label = deal_date_label + "Sales Order No : <br>";
                deal_date_text = deal_date_text + "<br>";

            }

            deal_date_label = deal_date_label + "PO No : <br>";
            deal_date_text = deal_date_text + String.valueOf(dealReq.getDeal().getPurchaseOrderNo()) + "<br>";

            parameters.put("deal_date_label", deal_date_label);
            parameters.put("deal_date_text", deal_date_text);

            parameters.put("billing_to", dealReq.getBillingToAddress());
            parameters.put("shipping_to", dealReq.getShippingToAddress());

            System.out.println(parameters.toString());

            List<Map<String, String>> datasource = new ArrayList<>();

            dealReq.getDeliveryChallan().getProducts().forEach(prod -> {
                Map<String, String> data = new HashMap<>();

                data.put("name_description", prod.getNameDescHTMLText(dealReq.getAddRawMaterials()).replaceAll("\n", "<br>"));
                data.put("quantity", prod.getQuantityAsHTMLText());

                datasource.add(data);
            });

//			dealReq.getDealInvoiceProducts().forEach(prod -> {
//				Map<String, String> data = new HashMap<>();
//
//				data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
//				data.put("quantity", prod.getQuantityAsHTMLText());
//
//				datasource.add(data);
//			});

            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

            final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

            File directory = new File(contentPath + "/Delivery_Challans/");
            System.out.println(directory.getAbsolutePath());
            if (!directory.exists()) {
                System.out.println("Directory created ::" + directory.getAbsolutePath());
                directory.mkdirs();
            }
            String filename = "DC_" + dealReq.getDeal().getId() + "_" + dealReq.getDeliveryChallan().getId() + ".pdf";
            dealReq.getDeliveryChallan().setFilename(filename);
            final String filePath = directory.getAbsolutePath() + "/" + filename;
            System.out.println(filePath);

            // Export the report to a PDF file.
            JasperExportManager.exportReportToPdfFile(print, filePath);
            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("DeliveryChallan", dealReq.getDeliveryChallan());
        return resp;

    }

    @Transactional
    @Override
    public Map<String, Object> saveDealPayments(DealPayments payment) {
        Map<String, Object> resp = new HashMap<>();
        List<DealPayments> payments = new ArrayList<>();
        try {

            payment = paymentRepo.save(payment);

            payments = paymentRepo.findByInvoiceId(payment.getInvoiceId());

            double sum = payments.stream().mapToDouble(DealPayments::getTotalAmount).sum();

            Query query = em
                    .createQuery("UPDATE DealInvoice di SET di.paidAmount = :paidAmount WHERE di.id = :invoiceId");
            query.setParameter("paidAmount", sum);
            query.setParameter("invoiceId", payment.getInvoiceId());
            query.executeUpdate();

            DealInvoice dealInvoice = invRepo.findById(payment.getInvoiceId());
            System.out.println(dealInvoice);
            if (dealInvoice.getDealType().equalsIgnoreCase("amc")) {

                List<DealInvoiceProducts> dealProducts = invProdRepo.findByInvoiceId(payment.getInvoiceId());
                System.out.println(dealProducts);

                List<AMCDetails> amcdetails = new ArrayList<>();
                List<InstituteProducts> instituteProducts = new ArrayList<>();

                for (DealInvoiceProducts dp : dealProducts) {
                    System.out.println("Name:::" + dp.getName());
                    AMCDetails amcdetail = instAmcRepo.findByAmcIdAndProduct(dealInvoice.getInvoiceNo(),
                            dp.getProductId());
                    if (amcdetail == null) {
                        amcdetail = new AMCDetails();
                    }

                    amcdetail.setInstitute(dealInvoice.getInstitute());
                    amcdetail.setProduct(dp.getProductId());
                    amcdetail.setAmcId(dealInvoice.getInvoiceNo());
                    amcdetail.setInvDate(dealInvoice.getInvoiceDate());
                    amcdetail.setTitle(dealInvoice.getSubject());
                    amcdetail.setAmcAmount(dp.getPrice());
                    amcdetail.setGst(dp.getGSTAmount());
                    amcdetail.setTotalAmount(dp.getTotalAmount());
                    amcdetail.setFromDate(dealInvoice.getAmcFromDate());
                    amcdetail.setToDate(dealInvoice.getAmcToDate());
                    amcdetail.setPaidDate(payment.getPaymentDate());
                    amcdetail.setPayMode(payment.getMode());
                    amcdetail.setServiceType("AMC");
                    amcdetail.setTransactionDetails(payment.getReferenceno() + " Drawn on " + payment.getDrawnon());
                    amcdetail.setDescription(payment.getDescription());

                    amcdetails.add(amcdetail);

                    InstituteProducts ip = instProductRepo.findByInstituteAndProduct(dealInvoice.getInstitute(),
                            new Product(dp.getProductId()));

                    if (ip != null) {
                        ip.setAmcAmount(dp.getPrice());
                        ip.setCurrentServiceUnder(ServiceUnder.valueOf("AMC"));
                        ip.setLastAMCPaidDate(payment.getPaymentDate());
                        ip.setAmcExpiryDate(dealInvoice.getAmcToDate());

                        instituteProducts.add(ip);
                    }

                }

                instAmcRepo.saveAll(amcdetails);
                instProductRepo.saveAll(instituteProducts);
            }

            resp.putAll(Util.SuccessResponse());

        } catch (Exception Ex) {
            Ex.printStackTrace();
            resp.putAll(Util.FailedResponse(Ex.getMessage()));
        }
        resp.put("DealPayment", payment);
        resp.put("DealPayments", payments);
        return resp;
    }

    @Override
    public Map<String, Object> getDealPayments(int invoiceId) {
        Map<String, Object> resp = new HashMap<>();
        List<DealPayments> payments = new ArrayList<>();
        DealInvoice invoice = new DealInvoice();
        try {

            invoice = invRepo.findById(invoiceId);

            payments = paymentRepo.findByInvoiceId(invoiceId);

            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("DealPayments", payments);
        resp.put("DealInvoice", invoice);

        return resp;
    }

    @Override
    public Map<String, Object> deleteDealPayments(DealPayments req) {
        Map<String, Object> resp = new HashMap<>();
        List<DealPayments> payments = new ArrayList<>();
        try {

            paymentRepo.delete(req);

            payments = paymentRepo.findByInvoiceId(req.getInvoiceId());

            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("DealPayments", payments);

        return resp;
    }

    @Override
    public Map<String, Object> createDealPaymentsReceipt1(DealRequest dealReq) {
        Map<String, Object> resp = new HashMap<>();
        DealInvoice dealInvoice = new DealInvoice();
        DealPayments dealPayments = new DealPayments();
        try {
            // DecimalFormat decformatter = new DecimalFormat("###,##0.00");
            // SimpleDateFormat Util.sdfFormatter = new SimpleDateFormat("dd/MM/yyyy");

            dealReq.setPayment(paymentRepo.findById(dealReq.getPayment().getId()));

            DealInvoice invoice = invRepo.findById(dealReq.getPayment().getInvoiceId());

            System.out.println(dealReq);

            InputStream stream = null;

            stream = this.getClass().getResourceAsStream("/reports/Receipt/Receipt_1_Template.jrxml");

            final Map<String, Object> parameters = new HashMap<>();

            InfoDetails info = infoDetailRepo.findById(1);
            Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

            parameters.put("cmp_name", info.getCmpName());
            parameters.put("cmp_address", info.getCompanyAddressHTML2());

            parameters.put("towards", "purchase on " + info.getCmpName());
            parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
            parameters.put("online_payment_url", info.getInstamojoPaymentURL());

            parameters.put("receipt_no", "R-" + dealReq.getPayment().getId());
            parameters.put("receipt_date", Util.sdfFormatter(new Date()));
//			parameters.put("clg_name", dealInvoice.getInstitute().getInstituteName());
//			parameters.put("total_amount", "Rs. " + Util.decimalFormatter(dealReq.getPayment().getTotalAmount()));
//			parameters.put("total_amount_words", "Rs. " + Util.decimalFormatter(dealReq.getPayment().getTotalAmount())
//					+ " (Rupees " + Util.EnglishNumberToWords(dealReq.getPayment().getTotalAmount()) + ")");
//
//			parameters.put("cheque_no", dealReq.getPayment().getReferenceno());
//			parameters.put("payment_date", Util.sdfFormatter(dealReq.getPayment().getPaymentDate()));
//			parameters.put("bank_name", dealReq.getPayment().getDrawnon());

            parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
            parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
            parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
            parameters.put("logo", info.getLogoAsFile());

            System.out.println("Company Logo::::::::::::::::::" + info.getLogoAsFile());
            String payment_details_description = dealReq.getReceiptContent();

//			payment_details_description = payment_details_description + " Received with thanks from   "
//					+ dealInvoice.getInstitute().getInstituteName();
//
//			payment_details_description = payment_details_description + "&nbsp;&nbsp;a sum of  Rs."
//					+ Util.decimalFormatter(dealReq.getPayment().getTotalAmount()) + " (Rupees "
//					+ Util.EnglishNumberToWords(dealReq.getPayment().getTotalAmount()) + ")";
//
//			payment_details_description = payment_details_description + "&nbsp;&nbsp;Vide Cheque/DD No.  "
//					+ dealReq.getPayment().getReferenceno() + "&nbsp;&nbsp;&nbsp;dated  "
//					+ Util.sdfFormatter(dealReq.getPayment().getPaymentDate()) + "&nbsp;&nbsp;&nbsp;drawn on&nbsp;"
//					+ dealReq.getPayment().getDrawnon();
//			payment_details_description = payment_details_description + "&nbsp;&nbsp;towards purchase on "
//					+ info.getCmpName();
//
//			payment_details_description = payment_details_description
//					+ "&nbsp;&nbsp;in payment of our bill/invoice number ";
//			payment_details_description = payment_details_description + "&nbsp;&nbsp;Rs. "
//					+ Util.decimalFormatter(dealReq.getPayment().getTotalAmount()) + " by cheque/DD/NEFT";

            parameters.put("payment_details_description", payment_details_description);

            if (invoice != null)
                parameters.put("bill_no", invoice.getInvoiceNo());
            else
                parameters.put("bill_no", "");

            System.out.println(parameters.toString());

            List<Map<String, String>> datasource = new ArrayList<>();

            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

            final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

            File directory = new File(contentPath + "/Receipts/");
            System.out.println(directory.getAbsolutePath());
            if (!directory.exists()) {
                System.out.println("Directory created ::" + directory.getAbsolutePath());
                directory.mkdirs();
            }
            dealReq.getPayment().setReceiptfilename("Receipt_" + dealReq.getPayment().getId() + "_Rs_"
                    + Math.round(dealReq.getPayment().getTotalAmount()) + ".pdf");
            final String filePath = directory.getAbsolutePath() + "/" + dealReq.getPayment().getReceiptfilename();
            System.out.println(filePath);

            dealPayments = paymentRepo.save(dealReq.getPayment());

            // Export the report to a PDF file.
            JasperExportManager.exportReportToPdfFile(print, filePath);
            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("DealPayment", dealPayments);
        resp.put("DealInvoice", dealInvoice);
        return resp;

    }

    @Override
    public Map<String, Object> createDealPaymentsReceipt2(DealRequest dealReq) {

        Map<String, Object> resp = new HashMap<>();
        DealInvoice dealInvoice = new DealInvoice();
        DealPayments dealPayments = new DealPayments();
        try {
            // DecimalFormat decformatter = new DecimalFormat("###,##0.00");
            // SimpleDateFormat Util.sdfFormatter = new SimpleDateFormat("dd/MM/yyyy");

            dealReq.setPayment(paymentRepo.findById(dealReq.getPayment().getId()));

            DealInvoice invoice = invRepo.findById(dealReq.getPayment().getInvoiceId());

            System.out.println(dealReq);

            InputStream stream = null;

            stream = this.getClass().getResourceAsStream("/reports/Receipt/Receipt_2_Template.jrxml");

            final Map<String, Object> parameters = new HashMap<>();

            String billAdd = (String) invoice.getBillingAddress();
            if (billAdd.startsWith(",<br>")) {
                System.out.println(":::Inside Condition:::");
                billAdd = billAdd.substring(5);
            }
            InfoDetails info = infoDetailRepo.findById(1);
            Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());
            parameters.put("cmp_name", info.getCmpName());
            parameters.put("cmp_address", info.getCompanyAddressHTML2());
            parameters.put("billing_to", billAdd.replace("<br>,", ""));
//			parameters.put("towards", "purchase on " + info.getCmpName());
//			parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
//			parameters.put("online_payment_url", info.getInstamojoPaymentURL());
//			parameters.put("receipt_date", Util.sdfFormatter(new Date()));

            parameters.put("receipt_no", "R-" + dealReq.getPayment().getId());
            parameters.put("reference_no", dealReq.getPayment().getReferenceno());
            parameters.put("invoiceno", invoice.getInvoiceNo());
            parameters.put("paymode", dealReq.getPayment().getMode());
            parameters.put("payment_date", dealReq.getPaymentDate());
            parameters.put("grand_total", "Rs." + Util.decimalFormatter(dealReq.getPayment().getTotalAmount()));
            parameters.put("grand_total", "Rs." + Util.decimalFormatter(dealReq.getPayment().getTotalAmount()));
            parameters.put("amount_in_words", Util.EnglishNumberToWords(dealReq.getPayment().getTotalAmount()));
            parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
            parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
            parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
            parameters.put("cmp_logo_url", info.getLogoAsFile());
            parameters.put("terms", "");
            parameters.put("designation", dealReq.getDesignation());

            System.out.println("Company Logo::::::::::::::::::" + info.getLogoAsFile());
            System.out.println(":::::Billing To:::" + dealReq.getBillingAddress().replace(",", ",<br>"));

            parameters.put("bill_no", invoice.getInvoiceNo());

            System.out.println(parameters.toString());

            List<Map<String, String>> datasource = new ArrayList<>();

            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

            final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

            File directory = new File(contentPath + "/Receipts/");
            System.out.println(directory.getAbsolutePath());
            if (!directory.exists()) {
                System.out.println("Directory created ::" + directory.getAbsolutePath());
                directory.mkdirs();
            }
            dealReq.getPayment().setReceiptfilename("Receipt_" + dealReq.getPayment().getId() + "_Rs_"
                    + Math.round(dealReq.getPayment().getTotalAmount()) + ".pdf");
            final String filePath = directory.getAbsolutePath() + "/" + dealReq.getPayment().getReceiptfilename();
            System.out.println(filePath);

            dealPayments = paymentRepo.save(dealReq.getPayment());

            // Export the report to a PDF file.
            JasperExportManager.exportReportToPdfFile(print, filePath);
            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("DealPayment", dealPayments);
        resp.put("DealInvoice", dealInvoice);
        return resp;

    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getDealPaymentsReport(DealInvoiceSearchRequest req) {

        Map<String, Object> resp = new HashMap<>();
        List<DealInvoiceResponse> payments = new ArrayList<>();
        try {

            String dealFilter = "";

            /* Deals Filter starts */

            if (req.getInstitutes() != null && req.getInstitutes().size() > 0) {
                String instituteIds = "'0'";
                for (Institute inst : req.getInstitutes()) {
                    instituteIds = instituteIds + ",'" + inst.getInstituteId() + "'";
                }
                dealFilter = dealFilter + " and di.institute in (" + instituteIds + ") ";
            }
            if (req.getAgents() != null && req.getAgents().size() > 0) {
                String agents = "'0'";
                for (Agent agnt : req.getAgents()) {
                    agents = agents + ",'" + agnt.getEmailId() + "'";
                }
                dealFilter = dealFilter + " and di.createdBy in (" + agents + ") ";
            }

            if (req.getDealCreatedDateFrom() != null && req.getDealCreatedDateTo() != null) {
                dealFilter = dealFilter + " and di.createddatetime between '"
                        + Util.sdfFormatter(req.getDealCreatedDateFrom()) + "' and '"
                        + Util.sdfFormatter(req.getDealCreatedDateTo()) + " 23:59:59'";
            }

            if (req.getDealModifiedDateFrom() != null && req.getDealModifiedDateTo() != null) {
                dealFilter = dealFilter + " and di.lastupdatedatetime between '"
                        + Util.sdfFormatter(req.getDealModifiedDateFrom()) + "' and '"
                        + Util.sdfFormatter(req.getDealModifiedDateTo()) + " 23:59:59'";
            }

            if (req.getDealInvoiceProducts() != null && req.getDealInvoiceProducts().size() > 0) {
                String productIds = "0";
                for (DealInvoiceProducts dp : req.getDealInvoiceProducts()) {
                    productIds = productIds + "," + dp.getId();
                }
                dealFilter = dealFilter
                        + " and di.id in ( select dp.invoiceId from DealInvoiceProducts dp where dp.productId in ("
                        + productIds + "))";
            }

            if (req.getDealType() != null && req.getDealType() != "") {
                dealFilter = dealFilter + " and di.dealType = '" + req.getDealType() + "'";
            }

            /* Deals Filter ends and Deal Payments starts */

            if (req.getPaymentSubject() != null && req.getPaymentSubject() != "") {
                dealFilter = dealFilter + " and dp.subject like '%" + req.getPaymentSubject() + "%'";
            }
            if (req.getPaymentReferenceNo() != null && req.getPaymentReferenceNo() != "") {
                dealFilter = dealFilter + " and dp.referenceno like '%" + req.getPaymentReferenceNo() + "%'";
            }
            if (req.getPaymentReceiptNo() != null && req.getPaymentReceiptNo() != "") {
                dealFilter = dealFilter + " and dp.receiptno like '%" + req.getPaymentReceiptNo() + "%'";
            }
            if (req.getPaymentDrawnOn() != null && req.getPaymentDrawnOn() != "") {
                dealFilter = dealFilter + " and dp.drawnon like '%" + req.getPaymentDrawnOn() + "%'";
            }
            if (req.getPaymentMode() != null && req.getPaymentMode() != "") {
                dealFilter = dealFilter + " and dp.mode like '%" + req.getPaymentMode() + "%'";
            }
            if (req.getPaymentDateFrom() != null && req.getPaymentDateTo() != null) {
                dealFilter = dealFilter + " and dp.paymentDate between '" + Util.sdfFormatter(req.getPaymentDateFrom())
                        + "' and '" + Util.sdfFormatter(req.getPaymentDateTo()) + "'";
            }
            if (req.getPaymentAmount() != null && req.getPaymentAmount() != "") {
                try {
                    if (req.getPaymentAmount().contains("-")) {
                        String[] str = req.getPaymentAmount().split("-");
                        dealFilter = dealFilter + " and dp.amount between " + str[0] + " and " + str[1] + "";
                    } else {
                        dealFilter = dealFilter + " and dp.amount = '" + req.getPaymentAmount() + "'";
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }

            }

            /* Deal Invoice ends */

            Query query = em.createQuery(
                    "select new com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceResponse(di , dp) from DealPayments dp "
                            + "left join fetch DealInvoice di on (dp.invoiceId = di.id) where 2 > 1 " + dealFilter,
                    DealInvoiceResponse.class);

            payments = query.getResultList();

            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("DealPayments", payments);
        return resp;

    }

    @Override
    public Map<String, Object> getNextInvoiceNo() {
        // TODO Auto-generated method stub
        Map<String, Object> resp = new HashMap<>();
        String autoGeneratedNo = "";
        String LastTerms = "";

        try {

            DealInvoice dealInvoiceTemp = invRepo.findLastDealInvoice();

            if (dealInvoiceTemp != null) {
                autoGeneratedNo = Util.autoIncrementNo(dealInvoiceTemp.getInvoiceNo());
                LastTerms = dealInvoiceTemp.getTerms();
            }

            resp.putAll(Util.SuccessResponse());
        } catch (Exception Ex) {
            Ex.printStackTrace();
            resp.putAll(Util.FailedResponse(Ex.getMessage()));
        }
        resp.put("AutoGeneratedInvoiceNo", autoGeneratedNo);
        resp.put("LastTerms", LastTerms);
        return resp;
    }

    @Override
    public Map<String, Object> generateDeliveryChallanPDF2(DealInvoiceRequest dealReq) {

        Map<String, Object> resp = new HashMap<>();

        try {

            dealReq.setDeliveryChallan(dcRepo.findById(dealReq.getDeliveryChallan().getId()));

            dealReq.setDealInvoice(invRepo.findById(dealReq.getDeliveryChallan().getInvoiceId()));

            dealReq.setDeal(dealRepo.findById(dealReq.getDeliveryChallan().getDealId()));

            InputStream stream = this.getClass().getResourceAsStream("/reports/Delivery_Challan/DC_Template_2.jrxml");

            final Map<String, Object> parameters = new HashMap<>();

            InfoDetails info = infoDetailRepo.findById(1);

            parameters.put("cmp_name", info.getCmpName());
            parameters.put("cmp_address", info.getCompanyAddressHTML1());
            parameters.put("cmp_logo_url", info.getLogoAsFile());
            parameters.put("dealtype_label", "Delivery Challan");

            String deal_date_label = "", deal_date_text = "";

            deal_date_label = deal_date_label + "Invoice No : <br>";
            deal_date_text = deal_date_text + String.valueOf(dealReq.getDeliveryChallan().getInvoiceNo()) + "<br>";

            if (dealReq.getDealInvoice() != null) {

                deal_date_label = deal_date_label + "Invoice Date : <br>";
                deal_date_text = deal_date_text
                        + String.valueOf(Util.sdfFormatter(dealReq.getDealInvoice().getInvoiceDate())) + "<br>";

                deal_date_label = deal_date_label + "Due Date : <br>";
                deal_date_text = deal_date_text
                        + String.valueOf(Util.sdfFormatter(dealReq.getDealInvoice().getDueDate())) + "<br>";

                deal_date_label = deal_date_label + "Sales Order No : <br>";
                deal_date_text = deal_date_text + String.valueOf(dealReq.getDealInvoice().getSalesOrderNo()) + "<br>";

            } else {

                deal_date_label = deal_date_label + "Invoice Date : <br>";
                deal_date_text = deal_date_text + "<br>";

                deal_date_label = deal_date_label + "Due Date : <br>";
                deal_date_text = deal_date_text + "<br>";

                deal_date_label = deal_date_label + "Sales Order No : <br>";
                deal_date_text = deal_date_text + "<br>";

            }

            deal_date_label = deal_date_label + "PO No : <br>";
            deal_date_text = deal_date_text + String.valueOf(dealReq.getDeal().getPurchaseOrderNo()) + "<br>";

            parameters.put("deal_date_label", deal_date_label);
            parameters.put("deal_date_text", deal_date_text);

            parameters.put("billing_to", dealReq.getBillingToAddress());
            parameters.put("shipping_to", dealReq.getShippingToAddress());

            System.out.println(parameters.toString());

            List<Map<String, String>> datasource = new ArrayList<>();

            dealReq.getDeliveryChallan().getProducts().forEach(prod -> {
                Map<String, String> data = new HashMap<>();

                data.put("name_description", prod.getNameDescHTMLText(dealReq.getAddRawMaterials()).replaceAll("\n", "<br>"));
                data.put("quantity", prod.getQuantityAsHTMLText());

                datasource.add(data);
            });

//			dealReq.getDealInvoiceProducts().forEach(prod -> {
//				Map<String, String> data = new HashMap<>();
//
//				data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
//				data.put("quantity", prod.getQuantityAsHTMLText());
//
//				datasource.add(data);
//			});

            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

            final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

            File directory = new File(contentPath + "/Delivery_Challans/");
            System.out.println(directory.getAbsolutePath());
            if (!directory.exists()) {
                System.out.println("Directory created ::" + directory.getAbsolutePath());
                directory.mkdirs();
            }
            String filename = "DC_" + dealReq.getDeal().getId() + "_" + dealReq.getDeliveryChallan().getId() + ".pdf";
            dealReq.getDeliveryChallan().setFilename(filename);
            final String filePath = directory.getAbsolutePath() + "/" + filename;
            System.out.println(filePath);

            // Export the report to a PDF file.
            JasperExportManager.exportReportToPdfFile(print, filePath);
            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("DeliveryChallan", dealReq.getDeliveryChallan());
        return resp;

    }

    @Override
    public Map<String, Object> generateDeliveryChallanPDF3(DealInvoiceRequest dealReq) {

        Map<String, Object> resp = new HashMap<>();

        try {

            dealReq.setDeliveryChallan(dcRepo.findById(dealReq.getDeliveryChallan().getId()));

            dealReq.setDealInvoice(invRepo.findById(dealReq.getDeliveryChallan().getInvoiceId()));

            dealReq.setDeal(dealRepo.findById(dealReq.getDeliveryChallan().getDealId()));

            InputStream stream = this.getClass().getResourceAsStream("/reports/Delivery_Challan/DC_Template_3.jrxml");

            final Map<String, Object> parameters = new HashMap<>();

            InfoDetails info = infoDetailRepo.findById(1);

            parameters.put("cmp_name", info.getCmpName());
            parameters.put("cmp_address", info.getCompanyAddressHTML1());
            parameters.put("cmp_logo_url", info.getLogoAsFile());
            parameters.put("dealtype_label", "Delivery Challan");

            String deal_date_label = "", deal_date_text = "";

            deal_date_label = deal_date_label + "Invoice No : <br>";
            deal_date_text = deal_date_text + String.valueOf(dealReq.getDeliveryChallan().getInvoiceNo()) + "<br>";

            if (dealReq.getDealInvoice() != null) {

                deal_date_label = deal_date_label + "Invoice Date : <br>";
                deal_date_text = deal_date_text
                        + String.valueOf(Util.sdfFormatter(dealReq.getDealInvoice().getInvoiceDate())) + "<br>";

                deal_date_label = deal_date_label + "Due Date : <br>";
                deal_date_text = deal_date_text
                        + String.valueOf(Util.sdfFormatter(dealReq.getDealInvoice().getDueDate())) + "<br>";

                deal_date_label = deal_date_label + "Sales Order No : <br>";
                deal_date_text = deal_date_text + String.valueOf(dealReq.getDealInvoice().getSalesOrderNo()) + "<br>";

            } else {

                deal_date_label = deal_date_label + "Invoice Date : <br>";
                deal_date_text = deal_date_text + "<br>";

                deal_date_label = deal_date_label + "Due Date : <br>";
                deal_date_text = deal_date_text + "<br>";

                deal_date_label = deal_date_label + "Sales Order No : <br>";
                deal_date_text = deal_date_text + "<br>";

            }

            deal_date_label = deal_date_label + "PO No : <br>";
            deal_date_text = deal_date_text + String.valueOf(dealReq.getDeal().getPurchaseOrderNo()) + "<br>";

            parameters.put("deal_date_label", deal_date_label);
            parameters.put("deal_date_text", deal_date_text);

            parameters.put("billing_to", dealReq.getBillingToAddress());
            parameters.put("shipping_to", dealReq.getShippingToAddress());

            System.out.println(parameters.toString());

            List<Map<String, String>> datasource = new ArrayList<>();

            dealReq.getDeliveryChallan().getProducts().forEach(prod -> {
                Map<String, String> data = new HashMap<>();

                data.put("name_description", prod.getNameDescHTMLText(dealReq.getAddRawMaterials()).replaceAll("\n", "<br>"));
                data.put("quantity", prod.getQuantityAsHTMLText());

                datasource.add(data);
            });

            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

            final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

            File directory = new File(contentPath + "/Delivery_Challans/");
            System.out.println(directory.getAbsolutePath());
            if (!directory.exists()) {
                System.out.println("Directory created ::" + directory.getAbsolutePath());
                directory.mkdirs();
            }
            String filename = "DC_" + dealReq.getDeal().getId() + "_" + dealReq.getDeliveryChallan().getId() + ".pdf";
            dealReq.getDeliveryChallan().setFilename(filename);
            final String filePath = directory.getAbsolutePath() + "/" + filename;
            System.out.println(filePath);

            // Export the report to a PDF file.
            JasperExportManager.exportReportToPdfFile(print, filePath);
            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("DeliveryChallan", dealReq.getDeliveryChallan());
        return resp;

    }

    @Override
    public Map<String, Object> getPendingInvoiceQuantityProducts(DealInvoiceSearchRequest req) {

        // TODO Auto-generated method stub
        Map<String, Object> resp = new HashMap<>();
        Deal deal = null;
        List<DealInvoice> dealInvoices = new ArrayList<>();
        List<DealProducts> reminingDealProducts = new ArrayList<>();

        try {

            if (req.getDealId() > 0) {

                List<DealProducts> __dealProducts = dealProdRepo.findByDealId(req.getDealId());

                System.out.println("__dealProducts::" + __dealProducts.size());

                dealInvoices = invRepo.findByDealId(req.getDealId());
                List<DealInvoiceProducts> dealInvProducts = new ArrayList<>();

                System.out.println("dealInvoices::" + dealInvoices.size());

                if (dealInvoices.size() > 0) {
                    List<Integer> ids = dealInvoices.stream().map(DealInvoice::getId).collect(Collectors.toList());

                    dealInvProducts = invProdRepo.findByInvoiceIdIn(ids);

                    System.out.println("dealInvoiceProducts::" + dealInvProducts.size());
                }

                for (DealProducts _dp : __dealProducts) {
                    DealProducts dp = _dp.clone();
                    for (DealInvoiceProducts dip : dealInvProducts) {
                        if (dp.getProductId() == dip.getProductId())
                            dp.setQuantity(dp.getQuantity() - dip.getQuantity());
                    }
                    if (dp.getQuantity() > 0)
                        reminingDealProducts.add(dp);
                }
            }

            resp.putAll(Util.SuccessResponse());

        } catch (Exception Ex) {
            Ex.printStackTrace();
            resp.putAll(Util.FailedResponse(Ex.getMessage()));
        }
        resp.put("DealProducts", reminingDealProducts);
        resp.put("DealInvoices", dealInvoices);
        return resp;

    }

    @Override
    public Map<String, Object> saveInvoiceEmail(InvoiceEmail email) {
        Map<String, Object> resp = new HashMap<>();
        try {

            email = invEmailRepo.save(email);
            resp.putAll(Util.SuccessResponse());
        } catch (Exception Ex) {
            Ex.printStackTrace();
            resp.putAll(Util.FailedResponse(Ex.getMessage()));
        }
        resp.put("InvoiceEmail", email);
        return resp;
    }

    @Override
    public Map<String, Object> addInvoiceEmailAttachment(int invoiceEmailId, MultipartFile file) {

        Map<String, Object> resp = new HashMap<>();

        InvoiceEmail invEmail = invEmailRepo.findById(invoiceEmailId);
        InvoiceEmailAttachments attach = new InvoiceEmailAttachments();

        File directory = new File(contentPath + "/Invoices/" + invEmail.getInvoiceId() + "/Emails/" + invEmail.getId());

        System.out.println(directory.getAbsolutePath());
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File convertFile = new File(directory.getAbsoluteFile() + "/" + file.getOriginalFilename());

        try {
            convertFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(convertFile);
            fout.write(file.getBytes());
            fout.close();

            attach.setFilename(file.getOriginalFilename());

            attach.setFiletype(file.getContentType());
            attach.setSize(file.getSize());
            attach.setEmailId(invoiceEmailId);
            attach.setInvoiceId(invEmail.getInvoiceId());
            attach = invEmailAttRepo.save(attach);

            resp.putAll(Util.SuccessResponse());
        } catch (IOException e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("InvoiceEmailAttachment", attach);
        return resp;

    }

    @Transactional
    @Override
    public Map<String, Object> sendInvoiceEmail(InvoiceEmail req) {
        System.out.println(req);
        Map<String, Object> resp = new HashMap<>();
        try {

            req = invEmailRepo.findById(req.getId());

            Set<String> emailds = new HashSet<>();
            emailds.add(req.getMailIds());

            String[] emailUpdates = req.getMailIdCC().split(";");
            System.out.println(emailUpdates.toString() + "  " + emailUpdates.length);

            System.out.println(emailds.size());
            EmailModel emailModel = new EmailModel("Sales");

            emailModel.setMailTo(req.getMailIds());
            emailModel.setMailList(emailUpdates);
            emailModel.setOtp(String.valueOf(Util.generateOTP()));
            emailModel.setMailSub(req.getSubject());

            emailModel.setMailText(req.getMessage());

            File directory = new File(contentPath + "/Invoices/" + req.getInvoiceId() + "/" + req.getFilename());
            System.out.println(directory.getAbsolutePath());

            emailModel.setContent_path(directory.getAbsolutePath());

            final List<InvoiceEmailAttachments> attachments = invEmailAttRepo.findByEmailId(req.getId());

            List<String> attachs = new ArrayList<>();
            for (InvoiceEmailAttachments attach : attachments) {
                directory = new File(contentPath + "/Invoices/" + req.getInvoiceId() + "/Emails/" + req.getId() + "/"
                        + attach.getFilename());

                attachs.add(directory.getAbsolutePath());
            }

            emailModel.setContentPaths(attachs);

            emailModel.setSenderConf("Sales");
            emailSender.sendmail(emailModel);

            int i = emailSender.sendmail(emailModel);

            if (i == 1) {
                Query query = em.createQuery("Update InvoiceEmail set sent = 1 where id = :id");
                query.setParameter("id", req.getId());

                query.executeUpdate();

                resp.putAll(Util.SuccessResponse());
            } else if (i == 2) {
                resp.putAll(Util.invalidMessage("Email Setting not Found, Setup Email Setting First!"));
            } else {
                resp.putAll(Util.invalidMessage("Something went wrong"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.putAll(Util.FailedResponse(e.getMessage()));
        }
        resp.put("DealEmail", req);
        return resp;
    }

    @Override
    public Map<String, Object> loadInvoiceEmailReminderSettings() {
        Map<String, Object> resp = new HashMap<>();
        InvoiceEmailReminderSettings setting = null;
        try {

            setting = reminderSettingRepo.findById(1);

            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("InvoiceEmailReminderSettings", setting);
        return resp;
    }

    @Override
    public Map<String, Object> saveInvoiceEmailReminderSettings(InvoiceEmailReminderSettings settings) {
        Map<String, Object> resp = new HashMap<>();
        InvoiceEmailReminderSettings setting = null;
        try {

            setting = reminderSettingRepo.save(settings);

            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("InvoiceEmailReminderSettings", setting);
        return resp;
    }

    @Override
    public Map<String, Object> saveInvoiceReminders(DealInvoiceReminderRequest request) {
        Map<String, Object> resp = new HashMap<>();
        try {

            request.setDealInvoiceReminders(invReminderRepo.saveAll(request.getDealInvoiceReminders()));

            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    public Map<String, Object> getInvoiceReminders(int dealId) {
        Map<String, Object> resp = new HashMap<>();
        List<DealInvoiceReminder> reminders = new ArrayList<>();
        try {

            reminders = invReminderRepo.findByDealId(dealId);

            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("DealInvoiceReminders", reminders);
        return resp;
    }

    @Override
    public Map<String, Object> deleteInvoiceReminders(DealInvoiceReminderRequest request) {
        Map<String, Object> resp = new HashMap<>();
        try {

            invReminderRepo.deleteAll(request.getDealInvoiceReminders());

            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    public Map<String, Object> searchInvoiceReminders(DealInvoiceReminderRequest request) {
        System.out.println("searchInvoiceReminders===");
        Map<String, Object> resp = new HashMap<>();
        List<DealInvoiceReminderResponse> reminders = new ArrayList<>();

        try {
            String filterQuery = "";
            if (request.getFromDate() != null && request.getToDate() != null) {
                filterQuery = filterQuery + " and rem.reminderDate between '" + Util.sdfFormatter(request.getFromDate())
                        + "' and '" + Util.sdfFormatter(request.getToDate()) + "' ";
            }

            if (request.getCreatedFromDate() != null && request.getCreatedToDate() != null) {
                filterQuery = filterQuery + " and rem.reminderDate between '"
                        + Util.sdfFormatter(request.getCreatedFromDate()) + "' and '"
                        + Util.sdfFormatter(request.getCreatedToDate()) + "' ";
            }

            if (request.getInstitutes() != null && request.getInstitutes().size() > 0) {

                String instituteIds = "'0'";
                for (Institute inst : request.getInstitutes()) {
                    instituteIds = instituteIds + ",'" + inst.getInstituteId() + "'";
                }
                filterQuery = filterQuery + " and d.institute in (" + instituteIds + ") ";
            }

            if (request.getDescription() != null && request.getDescription().isEmpty()) {
                filterQuery = filterQuery + " and rem.description like '%" + request.getDescription() + "%' ";
            }

            Query query = em.createQuery(
                    "select new com.autolib.helpdesk.Sales.model.Invoice.DealInvoiceReminderResponse(rem , d) "
                            + "from DealInvoiceReminder rem left join fetch Deal d on (rem.dealId = d.id) where 2 > 1 "
                            + filterQuery,
                    DealInvoiceReminderResponse.class);

            reminders = query.getResultList();

            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }

        resp.put("DealInvoiceReminders", reminders);
        return resp;
    }

}
