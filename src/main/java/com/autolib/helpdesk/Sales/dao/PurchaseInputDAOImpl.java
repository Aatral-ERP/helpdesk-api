package com.autolib.helpdesk.Sales.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.InfoDetails;
import com.autolib.helpdesk.Agents.entity.Vendor;
import com.autolib.helpdesk.Agents.repository.AgentRepository;
import com.autolib.helpdesk.Agents.repository.InfoDetailsRepository;
import com.autolib.helpdesk.Sales.model.Inputs.Bill;
import com.autolib.helpdesk.Sales.model.Inputs.BillAttachments;
import com.autolib.helpdesk.Sales.model.Inputs.BillPaymentResponse;
import com.autolib.helpdesk.Sales.model.Inputs.BillPaymentSearchRequest;
import com.autolib.helpdesk.Sales.model.Inputs.BillPayments;
import com.autolib.helpdesk.Sales.model.Inputs.BillProducts;
import com.autolib.helpdesk.Sales.model.Inputs.BillRequest;
import com.autolib.helpdesk.Sales.model.Inputs.BillSearchRequest;
import com.autolib.helpdesk.Sales.model.Inputs.PurchaseInputOrderProduct;
import com.autolib.helpdesk.Sales.model.Inputs.PurchaseInputOrderRequest;
import com.autolib.helpdesk.Sales.model.Inputs.PurchaseInputOrders;
import com.autolib.helpdesk.Sales.repository.Inputs.BillAttachmentsRepository;
import com.autolib.helpdesk.Sales.repository.Inputs.BillPaymentsRepository;
import com.autolib.helpdesk.Sales.repository.Inputs.BillProductsRepository;
import com.autolib.helpdesk.Sales.repository.Inputs.BillRepository;
import com.autolib.helpdesk.Sales.repository.Inputs.PurchaseInputOrderProductsRepository;
import com.autolib.helpdesk.Sales.repository.Inputs.PurchaseInputOrdersRepository;
import com.autolib.helpdesk.common.Util;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Repository
@Transactional
public class PurchaseInputDAOImpl implements PurchaseInputDAO {

	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	BillRepository billRepo;
	@Autowired
	BillAttachmentsRepository billAttachRepo;
	@Autowired
	BillProductsRepository billProductsRepo;
	@Autowired
	InfoDetailsRepository infoDetailRepo;
	@Autowired
	AgentRepository agentRepo;

	@Autowired
	PurchaseInputOrdersRepository orderRepo;
	@Autowired
	PurchaseInputOrderProductsRepository orderProductsRepo;
	@Autowired
	BillPaymentsRepository billPayRepo;

	@Autowired
	EntityManager em;

	@Value("${al.ticket.content-path}")
	private String contentPath;

	@Override
	public Map<String, Object> saveBill(BillRequest bill) {

		Map<String, Object> resp = new HashMap<>();
		List<BillPayments> billPayments = new ArrayList<>();

		try {

			bill.setBill(billRepo.save(bill.getBill()));

			if (bill.getBill().getId() != 0) {

				billProductsRepo.deleteByBillId(bill.getBill().getId());
				billAttachRepo.deleteByBillId(bill.getBill().getId());

				billPayments = billPayRepo.findByBillId(bill.getBill().getId());
			}

			bill.getBillProducts().forEach(prod -> {
				prod.setBillNo(bill.getBill().getBillNo());
				prod.setBillId(bill.getBill().getId());
			});

			bill.getBillAttachments().forEach(attach -> {
				attach.setBillId(bill.getBill().getId());
			});

			bill.setBillProducts(billProductsRepo.saveAll(bill.getBillProducts()));

			bill.setBillAttachments(billAttachRepo.saveAll(bill.getBillAttachments()));

			File directory = new File(contentPath + "/Bills/" + bill.getBill().getId());

			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				directory.mkdirs();
			}

			for (BillAttachments billAtt : bill.getBillAttachments()) {

				if (billAtt.getFile() != null && billAtt.getFile().getBytes().length > 0) {
					try {

						File convertFile = new File(directory.getAbsoluteFile() + "/" + billAtt.getFilename());

						convertFile.createNewFile();
						FileOutputStream fout = new FileOutputStream(convertFile);
						fout.write(com.google.api.client.util.Base64.decodeBase64(billAtt.getFile().getBytes()));
						fout.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		resp.put("Bill", bill.getBill());
		resp.put("BillPayments", billPayments);

		return resp;
	}

	@Override
	public Map<String, Object> getBill(int billId) {
		Map<String, Object> resp = new HashMap<>();
		Bill bill = null;
		List<BillProducts> billProducts = new ArrayList<>();
		List<BillAttachments> billAttachments = new ArrayList<>();

		try {
			bill = billRepo.findById(billId);

			if (bill != null) {
				billProducts = billProductsRepo.findByBillId(bill.getId());

				billAttachments = billAttachRepo.findByBillId(bill.getId());

			}
			resp.putAll(Util.SuccessResponse());

		} catch (Exception e) {
			e.printStackTrace();
			resp.putAll(Util.FailedResponse(e.getMessage()));
		}

		resp.put("Bill", bill);
		resp.put("BillProducts", billProducts);
		resp.put("BillAttachments", billAttachments);

		return resp;
	}

	@Override
	public Map<String, Object> deleteBill(Bill bill) {
		Map<String, Object> resp = new HashMap<>();
		try {

			billRepo.delete(bill);

			billProductsRepo.deleteByBillId(bill.getId());

			billAttachRepo.deleteByBillId(bill.getId());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> searchBills(BillSearchRequest req) {
		Map<String, Object> resp = new HashMap<>();
		List<Bill> bills = new ArrayList<>();

		try {

			String filterQuery = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			if (!req.getBillNo().isEmpty() && req.getBillNo() != null) {
				filterQuery = filterQuery + " and b.billNo like '%" + req.getBillNo() + "%'";
			}

			if (!req.getOrderNo().isEmpty() && req.getOrderNo() != null) {
				filterQuery = filterQuery + " and b.orderNo like '%" + req.getOrderNo() + "%'";
			}

			if (req.getVendors() != null && req.getVendors().size() > 0) {
				String vendors = "'0'";
				for (Vendor vendor : req.getVendors()) {
					vendors = vendors + ",'" + vendor.getId() + "'";
				}
				filterQuery = filterQuery + " and b.vendor in (" + vendors + ") ";
			}
			if (req.getAgents() != null && req.getAgents().size() > 0) {
				String agents = "'0'";
				for (Agent agnt : req.getAgents()) {
					agents = agents + ",'" + agnt.getEmailId() + "'";
				}
				filterQuery = filterQuery + " and b.createdBy in (" + agents + ") ";
			}
			if (req.getBillProducts() != null && req.getBillProducts().size() > 0) {
				String productIds = "0";
				for (BillProducts dp : req.getBillProducts()) {
					productIds = productIds + "," + dp.getId();
				}
				filterQuery = filterQuery
						+ " and b.id in ( select bp.billId from BillProducts bp where bp.productId in (" + productIds
						+ "))";
			}

			if (req.getBillModifiedDateFrom() != null && req.getBillModifiedDateTo() != null) {
				filterQuery = filterQuery + " and b.lastupdatedatetime between '"
						+ sdf.format(req.getBillModifiedDateFrom()) + "' and '"
						+ sdf.format(req.getBillModifiedDateTo()) + " 23:59:59'";
			}

			if (req.getBillDateFrom() != null && req.getBillDateTo() != null) {
				filterQuery = filterQuery + " and b.billDate between '" + sdf.format(req.getBillDateFrom()) + "' and '"
						+ sdf.format(req.getBillDateTo()) + "'";
			}

			if (req.getDueDateFrom() != null && req.getDueDateTo() != null) {
				filterQuery = filterQuery + " and b.dueDate between '" + sdf.format(req.getDueDateFrom()) + "' and '"
						+ sdf.format(req.getDueDateTo()) + "'";
			}

			Query query = em.createQuery("select b from Bill b where 2 > 1 " + filterQuery, Bill.class);

			bills = query.getResultList();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			e.printStackTrace();
			resp.putAll(Util.FailedResponse(e.getMessage()));
		}
		resp.put("Bills", bills);
		return resp;
	}

	@Override
	public Map<String, Object> getBillAttachment(BillAttachments attach) {
		Map<String, Object> resp = new HashMap<>();
		try {
			String path = "";
			File file = null;
			try {

				path = contentPath + "/Bills/" + attach.getBillId() + "/" + attach.getFilename() + "";

				System.out.println(path);
				file = new File(path);

				byte[] fileContent = FileUtils.readFileToByteArray(file);
				String encodedString = Base64.getEncoder().encodeToString(fileContent);

				System.out.println(encodedString);

				attach.setFile(encodedString);

				resp.putAll(Util.SuccessResponse());
			} catch (FileNotFoundException e) {
				resp.putAll(Util.FailedResponse(e.getMessage()));
				e.printStackTrace();
				logger.error("\r\nFile Not FOUND Exception::: " + path);
			} catch (Exception e) {
				resp.putAll(Util.FailedResponse(e.getMessage()));
				e.printStackTrace();
				logger.error("\r\nFile Download Exception::: " + path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.put("BillAttachment", attach);
		return resp;
	}

	@Override
	public Map<String, Object> savePurchaseInputOrder(PurchaseInputOrderRequest order) {

		Map<String, Object> resp = new HashMap<>();

		try {

			order.setPurchaseInputOrder(orderRepo.save(order.getPurchaseInputOrder()));

			if (order.getPurchaseInputOrder().getId() != 0) {

				orderProductsRepo.deleteByOrderId(order.getPurchaseInputOrder().getId());
			}

			order.getPurchaseInputOrderProducts().forEach(prod -> {
				prod.setOrderNo(order.getPurchaseInputOrder().getOrderNo());
				prod.setOrderId(order.getPurchaseInputOrder().getId());
			});
			order.setPurchaseInputOrderProducts(orderProductsRepo.saveAll(order.getPurchaseInputOrderProducts()));

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		resp.put("PurchaseInputOrder", order.getPurchaseInputOrder());
		resp.put("PurchaseInputOrderProducts", order.getPurchaseInputOrderProducts());
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> searchPurchaseInputOrders(PurchaseInputOrderRequest req) {
		Map<String, Object> resp = new HashMap<>();
		List<PurchaseInputOrders> orders = new ArrayList<>();

		try {

			String filterQuery = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			if (!req.getOrderNo().isEmpty() && req.getOrderNo() != null) {
				filterQuery = filterQuery + " and o.orderNo like '%" + req.getOrderNo() + "%'";
			}
			if (!req.getReferenceNo().isEmpty() && req.getReferenceNo() != null) {
				filterQuery = filterQuery + " and o.referenceNo like '%" + req.getReferenceNo() + "%'";
			}
			if (req.getVendors() != null && req.getVendors().size() > 0) {
				String vendors = "'0'";
				for (Vendor vendor : req.getVendors()) {
					vendors = vendors + ",'" + vendor.getId() + "'";
				}
				filterQuery = filterQuery + " and o.vendor in (" + vendors + ") ";
			}
			if (req.getAgents() != null && req.getAgents().size() > 0) {
				String agents = "'0'";
				for (Agent agnt : req.getAgents()) {
					agents = agents + ",'" + agnt.getEmailId() + "'";
				}
				filterQuery = filterQuery + " and o.createdBy in (" + agents + ") ";
			}
			if (req.getPurchaseInputOrderProducts() != null && req.getPurchaseInputOrderProducts().size() > 0) {
				String productIds = "0";
				for (PurchaseInputOrderProduct dp : req.getPurchaseInputOrderProducts()) {
					productIds = productIds + "," + dp.getId();
				}
				filterQuery = filterQuery
						+ " and o.id in ( select pip.orderId from PurchaseInputOrderProduct pip where pip.productId in ("
						+ productIds + "))";
			}

			if (req.getOrderModifiedDateFrom() != null && req.getOrderModifiedDateTo() != null) {
				filterQuery = filterQuery + " and o.lastupdatedatetime between '"
						+ sdf.format(req.getOrderModifiedDateFrom()) + "' and '"
						+ sdf.format(req.getOrderModifiedDateTo()) + " 23:59:59'";
			}

			if (req.getOrderDateFrom() != null && req.getOrderDateTo() != null) {
				filterQuery = filterQuery + " and o.orderDate between '" + sdf.format(req.getOrderDateFrom())
						+ "' and '" + sdf.format(req.getOrderDateTo()) + "'";
			}

			if (req.getExpectedDeliveryDateFrom() != null && req.getExpectedDeliveryDateTo() != null) {
				filterQuery = filterQuery + " and o.expectedDeliveryDate between '"
						+ sdf.format(req.getExpectedDeliveryDateFrom()) + "' and '"
						+ sdf.format(req.getExpectedDeliveryDateTo()) + "'";
			}

			Query query = em.createQuery("select o from PurchaseInputOrders o where 2 > 1 " + filterQuery,
					PurchaseInputOrders.class);

			orders = query.getResultList();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			e.printStackTrace();
			resp.putAll(Util.FailedResponse(e.getMessage()));
		}
		resp.put("PurchaseInputOrders", orders);
		return resp;
	}

	@Override
	public Map<String, Object> getPurchaseInputOrder(int orderId) {
		Map<String, Object> resp = new HashMap<>();
		PurchaseInputOrders order = null;
		List<PurchaseInputOrderProduct> orderProducts = new ArrayList<>();

		String autoGeneratedNo = "";
		String LastTerms = "";

		try {
			order = orderRepo.findById(orderId);

			if (order != null) {
				orderProducts = orderProductsRepo.findByOrderId(order.getId());

			} else {

				PurchaseInputOrders orderTemp = orderRepo.findLastOrderById(orderId);

				if (orderTemp != null) {

					System.out.println(orderTemp.toString());

					autoGeneratedNo = autoIncrementNo(orderTemp.getOrderNo());
					LastTerms = orderTemp.getTerms();
				}

			}
			resp.putAll(Util.SuccessResponse());

		} catch (Exception e) {
			e.printStackTrace();
			resp.putAll(Util.FailedResponse(e.getMessage()));
		}

		resp.put("PurchaseInputOrder", order);
		resp.put("PurchaseInputOrderProducts", orderProducts);

		resp.put("AutoGeneratedNo", autoGeneratedNo);
		resp.put("LastTerms", LastTerms);

		return resp;
	}

	String autoIncrementNo(String inNo) {

		String outNo = "";

		try {
			System.out.println(inNo);
			String[] bits = inNo.split("/");

			if (inNo.contains("/"))
				bits = inNo.split("/");
			else if (inNo.contains("-"))
				bits = inNo.split("-");

			String original = bits[bits.length - 1];
			String incremented = String.format("%0" + original.length() + "d", Integer.parseInt(original) + 1);

			inNo = inNo.replaceAll(original, incremented);
			outNo = inNo;

			System.out.println(inNo);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			outNo = inNo + "/01";
		}

		return outNo;
	}

	@Override
	public Map<String, Object> deletePurchaseInputOrder(PurchaseInputOrders order) {
		Map<String, Object> resp = new HashMap<>();
		try {

			orderRepo.delete(order);

			orderProductsRepo.deleteByOrderId(order.getId());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public Map<String, Object> generatePurchaseInputOrdersPDF(PurchaseInputOrderRequest req) {
		Map<String, Object> resp = new HashMap<>();

		try {

			req.setPurchaseInputOrder(orderRepo.findById(req.getPurchaseInputOrder().getId()));

			req.setPurchaseInputOrderProducts(orderProductsRepo.findByOrderId(req.getPurchaseInputOrder().getId()));

			InputStream stream = null;

			if (req.getPurchaseInputOrder().getGstType().equals("IGST")) {
				stream = this.getClass()
						.getResourceAsStream("/reports/PurchaseInputOrder/Purchase_Input_Order_Template_1_IGST.jrxml");
			} else {
				stream = this.getClass().getResourceAsStream(
						"/reports/PurchaseInputOrder/Purchase_Input_Order_Template_1_CGST_SGST.jrxml");
			}

			final Map<String, Object> parameters = new HashMap<>();
			InfoDetails info = infoDetailRepo.findById(1);

			Agent agent = agentRepo.findByEmailId(req.getSignatureBy());

			System.out.println(info.toString());

			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddressHTML1());
			parameters.put("cmp_logo_url", info.getLogoAsFile());

			parameters.put("roundseal", req.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", req.getAddRoundSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", req.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", req.getDesignation());
			parameters.put("bankdetails", info.getBankDetailsAsHTML());

			parameters.put("terms", req.getPurchaseInputOrder().getTermsAsHTML());
			parameters.put("order_date", Util.sdfFormatter(req.getPurchaseInputOrder().getOrderDate()));
			parameters.put("order_no", String.valueOf(req.getPurchaseInputOrder().getOrderNo()));
			parameters.put("reference_no",
					req.getPurchaseInputOrder().getReferenceNo() != null
							? String.valueOf(req.getPurchaseInputOrder().getReferenceNo())
							: "");
			parameters.put("due_date", Util.sdfFormatter(req.getPurchaseInputOrder().getExpectedDeliveryDate()));
			parameters.put("grand_total", "Rs." + Util.decimalFormatter(req.getPurchaseInputOrder().getGrandTotal()));
			parameters.put("amount_in_words", Util.EnglishNumberToWords(req.getPurchaseInputOrder().getGrandTotal()));

			parameters.put("billing_to", req.getPurchaseInputOrder().getBillingToAsHTML());
			parameters.put("shipping_to", req.getPurchaseInputOrder().getShippingToAsHTML());

			String price_summary_label = "", price_summary_text = "";

			price_summary_label = price_summary_label + "Original Rate : <br>";
			price_summary_text = price_summary_text + "Rs."
					+ Util.decimalFormatter(req.getPurchaseInputOrder().getSubTotal()) + "<br>";

			if (req.getPurchaseInputOrder().getDiscount() > 0) {
				price_summary_label = price_summary_label + "Discount : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter(req.getPurchaseInputOrder().getDiscount()) + "<br>";
			}

			if (req.getPurchaseInputOrder().getGstType().equalsIgnoreCase("IGST")) {
				price_summary_label = price_summary_label + "IGST (18%) : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter(req.getPurchaseInputOrder().getTax()) + "<br>";
			} else {
				price_summary_label = price_summary_label + "CGST (9%) : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter(req.getPurchaseInputOrder().getTax() / 2) + "<br>";

				price_summary_label = price_summary_label + "SGST (9%) : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter((req.getPurchaseInputOrder().getTax() / 2)) + "<br>";
			}

			price_summary_label = price_summary_label + "Adjustment : <br>";
			price_summary_text = price_summary_text + "Rs."
					+ String.format("%.2f", req.getPurchaseInputOrder().getAdjustment()) + "<br>";

			parameters.put("price_summary_label", price_summary_label);
			parameters.put("price_summary_text", price_summary_text);

			System.out.println(parameters.toString());

			List<Map<String, String>> datasource = new ArrayList<>();

			req.getPurchaseInputOrderProducts().forEach(prod -> {
				Map<String, String> data = new HashMap<>();

				data.put("name_description", prod.getNameDescHTMLText());
				data.put("quantity", String.valueOf(prod.getQuantity()));
				data.put("price", Util.decimalFormatter(prod.getRateAmount()));
				data.put("rate", Util.decimalFormatter(prod.getPrice()));
				data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
				if (req.getPurchaseInputOrder().getGstType().equals("IGST")) {
					data.put("gst_percent", prod.getGstPercentage() + "%");
					data.put("gst_amount", Util.decimalFormatter(prod.getGSTAmount()));
				} else {
					data.put("sgst_percent", prod.getGstPercentage() / 2 + "%");
					data.put("sgst_amount", Util.decimalFormatter(prod.getGSTAmount() / 2));
					data.put("cgst_percent", prod.getGstPercentage() / 2 + "%");
					data.put("cgst_amount", Util.decimalFormatter(prod.getGSTAmount() / 2));
				}

				datasource.add(data);
			});

			final JasperReport report = JasperCompileManager.compileReport(stream);
			final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

			final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

			File directory = new File(contentPath + "/Purchase_Input_Orders/");
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}
			req.getPurchaseInputOrder()
					.setFilename(req.getPurchaseInputOrder().getOrderNo().replaceAll("/", "-") + ".pdf");
			final String filePath = directory.getAbsolutePath() + "/" + req.getPurchaseInputOrder().getFilename();
			System.out.println(filePath);

			orderRepo.save(req.getPurchaseInputOrder());

			// Export the report to a PDF file.
			JasperExportManager.exportReportToPdfFile(print, filePath);
			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("PurchaseInputOrder", req.getPurchaseInputOrder());
		return resp;
	}

	@Override
	public Map<String, Object> UploadGeneratedPurchaseInputOrdersPDF(int orderId, MultipartFile file) {
		Map<String, Object> resp = new HashMap<>();

		PurchaseInputOrders order = orderRepo.findById(orderId);

		File directory = new File(contentPath + "/Purchase_Input_Orders/");
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

			order.setFilename(file.getOriginalFilename());
			order = orderRepo.save(order);

			resp.putAll(Util.SuccessResponse());
		} catch (IOException e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("PurchaseInputOrder", order);
		return resp;
	}

	@Override
	public Map<String, Object> savePurchaseInputsPayments(BillPayments billPayment) {
		Map<String, Object> resp = new HashMap<>();
		List<BillPayments> billPayments = new ArrayList<>();
		try {

			billPayment = billPayRepo.save(billPayment);

			billPayments = billPayRepo.findByBillId(billPayment.getBillId());

			resp.putAll(Util.SuccessResponse());

		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		resp.put("BillPayments", billPayments);
		return resp;
	}

	@Override
	public Map<String, Object> getPurchaseInputsBillPayments(int billId) {
		Map<String, Object> resp = new HashMap<>();
		List<BillPayments> billPayments = new ArrayList<>();
		try {
			billPayments = billPayRepo.findByBillId(billId);

			resp.putAll(Util.SuccessResponse());

		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		resp.put("BillPayments", billPayments);
		return resp;
	}

	@Override
	public Map<String, Object> deletePurchaseInputsPayments(BillPayments billPayment) {
		Map<String, Object> resp = new HashMap<>();
		List<BillPayments> billPayments = new ArrayList<>();
		try {

			billPayRepo.delete(billPayment);

			billPayments = billPayRepo.findByBillId(billPayment.getBillId());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}

		resp.put("BillPayments", billPayments);
		return resp;
	}

	@Override
	public Map<String, Object> getPurchaseInputsPaymentsReport(BillSearchRequest req) {
		Map<String, Object> resp = new HashMap<>();
		List<BillPayments> billPayments = new ArrayList<>();
		try {

//			String filterQuery = "";
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//			if (!req.getBillNo().isEmpty() && req.getBillNo() != null) {
//				filterQuery = filterQuery + " and b.billNo like '%" + req.getBillNo() + "%'";
//			}
//
//			if (!req.getOrderNo().isEmpty() && req.getOrderNo() != null) {
//				filterQuery = filterQuery + " and b.orderNo like '%" + req.getOrderNo() + "%'";
//			}
//
//			if (req.getVendors() != null && req.getVendors().size() > 0) {
//				String vendors = "'0'";
//				for (Vendor vendor : req.getVendors()) {
//					vendors = vendors + ",'" + vendor.getId() + "'";
//				}
//				filterQuery = filterQuery + " and b.vendor in (" + vendors + ") ";
//			}
//			if (req.getAgents() != null && req.getAgents().size() > 0) {
//				String agents = "'0'";
//				for (Agent agnt : req.getAgents()) {
//					agents = agents + ",'" + agnt.getEmailId() + "'";
//				}
//				filterQuery = filterQuery + " and b.createdBy in (" + agents + ") ";
//			}
//			if (req.getBillProducts() != null && req.getBillProducts().size() > 0) {
//				String productIds = "0";
//				for (BillProducts dp : req.getBillProducts()) {
//					productIds = productIds + "," + dp.getId();
//				}
//				filterQuery = filterQuery
//						+ " and b.id in ( select bp.billId from BillProducts bp where bp.productId in (" + productIds
//						+ "))";
//			}
//
//			if (req.getBillModifiedDateFrom() != null && req.getBillModifiedDateTo() != null) {
//				filterQuery = filterQuery + " and b.lastupdatedatetime between '"
//						+ sdf.format(req.getBillModifiedDateFrom()) + "' and '"
//						+ sdf.format(req.getBillModifiedDateTo()) + " 23:59:59'";
//			}
//
//			if (req.getBillDateFrom() != null && req.getBillDateTo() != null) {
//				filterQuery = filterQuery + " and b.billDate between '" + sdf.format(req.getBillDateFrom()) + "' and '"
//						+ sdf.format(req.getBillDateTo()) + "'";
//			}
//
//			if (req.getDueDateFrom() != null && req.getDueDateTo() != null) {
//				filterQuery = filterQuery + " and b.dueDate between '" + sdf.format(req.getDueDateFrom()) + "' and '"
//						+ sdf.format(req.getDueDateTo()) + "'";
//			}
//
//			Query query = em.createQuery("select b from Bill b where 2 > 1 " + filterQuery, Bill.class);
//
//			bills = query.getResultList();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}

		resp.put("BillPayments", billPayments);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> searchBillsPayment(BillPaymentSearchRequest req) {
		Map<String, Object> resp = new HashMap<>();
		List<Bill> bills = new ArrayList<>();

		try {

			String filterQuery = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			if (req.getBillId() != null && !req.getBillId().isEmpty()) {
				filterQuery = filterQuery + " and pb.billId like '%" + req.getBillId() + "%'";
			}

			if (req.getReferenceno() != null && !req.getReferenceno().isEmpty()) {
				filterQuery = filterQuery + " and pb.referenceno like '%" + req.getReferenceno() + "%'";
			}

			if (req.getMode() != null && !req.getMode().isEmpty()) {
				filterQuery = filterQuery + " and pb.mode like '%" + req.getMode() + "%'";
			}

			if (req.getVendors() != null && req.getVendors().size() > 0) {
				String vendors = "'0'";
				for (Vendor vendor : req.getVendors()) {
					vendors = vendors + ",'" + vendor.getId() + "'";
				}
				filterQuery = filterQuery + " and b.vendor in (" + vendors + ") ";
			}

			if (req.getAgents() != null && req.getAgents().size() > 0) {
				String agents = "'0'";
				for (Agent agnt : req.getAgents()) {
					agents = agents + ",'" + agnt.getEmailId() + "'";
				}
				filterQuery = filterQuery + " and pb.createdBy in (" + agents + ") ";
			}

			if (req.getPaymentModifiedDateFrom() != null && req.getPaymentModifiedDateTo() != null) {
				filterQuery = filterQuery + " and pb.lastupdatedatetime between '"
						+ sdf.format(req.getPaymentModifiedDateFrom()) + "' and '"
						+ sdf.format(req.getPaymentModifiedDateTo()) + " 23:59:59'";
			}

			if (req.getPaymentDateFrom() != null && req.getPaymentDateTo() != null) {
				filterQuery = filterQuery + " and pb.paymentDate between '" + sdf.format(req.getPaymentDateFrom())
						+ "' and '" + sdf.format(req.getPaymentDateTo()) + "'";
			}

			System.out.println(":::FilterQuery::" + filterQuery);
			Query query = em.createQuery(
					"SELECT new com.autolib.helpdesk.Sales.model.Inputs.BillPaymentResponse(pb,b) FROM BillPayments pb JOIN Bill b ON (pb.billId = b.id) where 2 > 1 "
							+ filterQuery,
					BillPaymentResponse.class);

			bills = query.getResultList();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			e.printStackTrace();
			resp.putAll(Util.FailedResponse(e.getMessage()));
		}
		resp.put("Bills", bills);
		return resp;
	}

}
