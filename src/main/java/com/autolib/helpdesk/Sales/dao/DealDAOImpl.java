package com.autolib.helpdesk.Sales.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
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

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.InfoDetails;
import com.autolib.helpdesk.Agents.entity.StockEntry;
import com.autolib.helpdesk.Agents.repository.AgentRepository;
import com.autolib.helpdesk.Agents.repository.InfoDetailsRepository;
import com.autolib.helpdesk.Agents.repository.ProductsRepository;
import com.autolib.helpdesk.Agents.service.AgentService;
import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.Institutes.model.InstituteContact;
import com.autolib.helpdesk.Institutes.repository.InstituteAmcRepository;
import com.autolib.helpdesk.Institutes.repository.InstituteContactRepository;
import com.autolib.helpdesk.Institutes.repository.InstituteProductRepository;
import com.autolib.helpdesk.Institutes.repository.InstituteRepository;
import com.autolib.helpdesk.Sales.model.Deal;
import com.autolib.helpdesk.Sales.model.DealContacts;
import com.autolib.helpdesk.Sales.model.DealDCProducts;
import com.autolib.helpdesk.Sales.model.DealDeliveryChallan;
import com.autolib.helpdesk.Sales.model.DealEmail;
import com.autolib.helpdesk.Sales.model.DealEmailAttachments;
import com.autolib.helpdesk.Sales.model.DealPayments;
import com.autolib.helpdesk.Sales.model.DealProducts;
import com.autolib.helpdesk.Sales.model.DealProformaInvoice;
import com.autolib.helpdesk.Sales.model.DealProjectImplementation;
import com.autolib.helpdesk.Sales.model.DealProjectImplementationComments;
import com.autolib.helpdesk.Sales.model.DealPurchaseOrder;
import com.autolib.helpdesk.Sales.model.DealQuotation;
import com.autolib.helpdesk.Sales.model.DealRequest;
import com.autolib.helpdesk.Sales.model.DealResponse;
import com.autolib.helpdesk.Sales.model.DealSalesOrder;
import com.autolib.helpdesk.Sales.model.DealSearchRequest;
import com.autolib.helpdesk.Sales.model.NoteAttachments;
import com.autolib.helpdesk.Sales.model.NoteRequest;
import com.autolib.helpdesk.Sales.model.Notes;
import com.autolib.helpdesk.Sales.model.ProjectImplemantationRequest;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoice;
import com.autolib.helpdesk.Sales.repository.DealContactsRepository;
import com.autolib.helpdesk.Sales.repository.DealDCProductsRepository;
import com.autolib.helpdesk.Sales.repository.DealDeliveryChallanRepository;
import com.autolib.helpdesk.Sales.repository.DealEmailAttachmentRepository;
import com.autolib.helpdesk.Sales.repository.DealEmailRepository;
import com.autolib.helpdesk.Sales.repository.DealPaymentsRepository;
import com.autolib.helpdesk.Sales.repository.DealProductsRepository;
import com.autolib.helpdesk.Sales.repository.DealProformaInvoiceRepository;
import com.autolib.helpdesk.Sales.repository.DealProjectImplementationCommentsAttachmentsRepository;
import com.autolib.helpdesk.Sales.repository.DealProjectImplementationCommentsRepository;
import com.autolib.helpdesk.Sales.repository.DealProjectImplementationRepository;
import com.autolib.helpdesk.Sales.repository.DealPurchaseOrderRepository;
import com.autolib.helpdesk.Sales.repository.DealQuotationRepository;
import com.autolib.helpdesk.Sales.repository.DealSalesOrderRepository;
import com.autolib.helpdesk.Sales.repository.DealsRepository;
import com.autolib.helpdesk.Sales.repository.NoteAttachmentsRepository;
import com.autolib.helpdesk.Sales.repository.NotesRepository;
import com.autolib.helpdesk.Sales.repository.Invoice.DealInvoiceProductsRepository;
import com.autolib.helpdesk.Sales.repository.Invoice.DealInvoiceRepository;
import com.autolib.helpdesk.common.EmailModel;
import com.autolib.helpdesk.common.EmailSender;
import com.autolib.helpdesk.common.Util;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRRtfExporter;

@Repository
@Transactional
public class DealDAOImpl implements DealDAO {

	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	DealsRepository dealRepo;
	@Autowired
	AgentRepository agentRepo;
	@Autowired
	DealProductsRepository dealProductsRepo;
	@Autowired
	InstituteProductRepository instProdRepo;
	@Autowired
	DealContactsRepository dealContactsRepo;
	@Autowired
	NotesRepository notesRepo;
	@Autowired
	NoteAttachmentsRepository noteAttachRepo;
	@Autowired
	InstituteRepository instRepo;
	@Autowired
	InstituteContactRepository instContactRepo;
	@Autowired
	DealQuotationRepository quoteRepo;
	@Autowired
	DealProformaInvoiceRepository proInvRepo;
	@Autowired
	DealInvoiceRepository invRepo;
	@Autowired
	DealInvoiceProductsRepository invProdRepo;
	@Autowired
	DealPurchaseOrderRepository poRepo;
	@Autowired
	DealSalesOrderRepository soRepo;
	@Autowired
	DealPaymentsRepository paymentRepo;
	@Autowired
	DealDeliveryChallanRepository dcRepo;
	@Autowired
	DealDCProductsRepository dcProdRepo;
	@Autowired
	InfoDetailsRepository infoDetailRepo;
	@Autowired
	DealEmailRepository dealEmailRepo;
	@Autowired
	DealEmailAttachmentRepository dealEmailAttRepo;
	@Autowired
	DealProjectImplementationRepository dpimRepo;
	@Autowired
	DealProjectImplementationCommentsRepository dpimCommentRepo;
	@Autowired
	DealProjectImplementationCommentsAttachmentsRepository dpimCARepo;
	@Autowired
	AgentService agentService;
	@Autowired
	ProductsRepository prodRepo;
	@Autowired
	InstituteAmcRepository instAmcRepo;
	@Autowired
	InstituteProductRepository instProductRepo;

	@Autowired
	EmailSender emailSender;

	@Autowired
	EntityManager em;

	@Value("${al.ticket.content-path}")
	private String contentPath;
	@Value("${al.ticket.web.url}")
	private String webURL;
	@Value("${al.agent.web.url}")
	private String agentWebURL;

	@Override
	public Map<String, Object> saveDeal(DealRequest dealReq) {
		Map<String, Object> resp = new HashMap<>();
		try {

			if (dealReq.getDeal().getId() != 0) {

				dealProductsRepo.deleteByDealId(dealReq.getDeal().getId());
				dealContactsRepo.deleteByDealId(dealReq.getDeal().getId());
			}

			dealReq.setDeal(dealRepo.save(dealReq.getDeal()));

			dealReq.getDealProducts().forEach(dp -> dp.setDealId(dealReq.getDeal().getId()));

			dealProductsRepo.saveAll(dealReq.getDealProducts());

			for (InstituteContact ic : dealReq.getInstituteContacts()) {
				dealContactsRepo.save(new DealContacts(dealReq.getDeal().getId(), ic));
			}

			Institute instTemp = instRepo.findByInstituteId(dealReq.getDeal().getInstitute().getInstituteId());

			instTemp.setStreet1(dealReq.getDeal().getBillingStreet1());
			instTemp.setStreet2(dealReq.getDeal().getBillingStreet2());
			instTemp.setCity(dealReq.getDeal().getBillingCity());
			instTemp.setState(dealReq.getDeal().getBillingState());
			instTemp.setCountry(dealReq.getDeal().getBillingCountry());
			instTemp.setZipcode(dealReq.getDeal().getBillingZIPCode());

			instRepo.save(instTemp);

			resp.putAll(Util.SuccessResponse());

		} catch (Exception Ex) {
			logger.error(Ex.getMessage());
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		resp.put("Deal", dealReq.getDeal());
		resp.put("getDealProducts", dealReq.getDealProducts());
		resp.put("InstituteContacts", dealReq.getInstituteContacts());
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDeals(DealSearchRequest dealSearchReq) {
		Map<String, Object> resp = new HashMap<>();
		List<Deal> deals = new ArrayList<>();
		try {
			String filterQuery = "", dealFilter = "", quoteFilter = "", poFilter = "", soFilter = "",
					proInvoiceFilter = "", invoiceFilter = "";
//			

			/* Deals Filter starts */

			if (dealSearchReq.getInstitutes() != null && dealSearchReq.getInstitutes().size() > 0) {
				String instituteIds = "'0'";
				for (Institute inst : dealSearchReq.getInstitutes()) {
					instituteIds = instituteIds + ",'" + inst.getInstituteId() + "'";
				}
				dealFilter = dealFilter + " and d.institute in (" + instituteIds + ") ";
			}
			if (dealSearchReq.getAgents() != null && dealSearchReq.getAgents().size() > 0) {
				String agents = "'0'";
				for (Agent agnt : dealSearchReq.getAgents()) {
					agents = agents + ",'" + agnt.getEmailId() + "'";
				}
				dealFilter = dealFilter + " and d.createdBy in (" + agents + ") ";
			}

			if (dealSearchReq.getDealCreatedDateFrom() != null && dealSearchReq.getDealCreatedDateTo() != null) {
				dealFilter = dealFilter + " and d.createddatetime between '"
						+ Util.sdfFormatter(dealSearchReq.getDealCreatedDateFrom()) + "' and '"
						+ Util.sdfFormatter(dealSearchReq.getDealCreatedDateTo()) + " 23:59:59'";
			}

			if (dealSearchReq.getDealModifiedDateFrom() != null && dealSearchReq.getDealModifiedDateTo() != null) {
				dealFilter = dealFilter + " and d.lastupdatedatetime between '"
						+ Util.sdfFormatter(dealSearchReq.getDealModifiedDateFrom()) + "' and '"
						+ Util.sdfFormatter(dealSearchReq.getDealModifiedDateTo()) + " 23:59:59'";
			}

			if (dealSearchReq.getDealProducts() != null && dealSearchReq.getDealProducts().size() > 0) {
				String productIds = "0";
				for (DealProducts dp : dealSearchReq.getDealProducts()) {
					productIds = productIds + "," + dp.getId();
				}
				dealFilter = dealFilter + " and d.id in ( select d.dealId from DealProducts dp where dp.productId in ("
						+ productIds + "))";
			}
			if (dealSearchReq.getDealType() != null && dealSearchReq.getDealType() != "") {
				dealFilter = dealFilter + " and d.dealType = '" + dealSearchReq.getDealType() + "'";
			}

			/* Deals Filter ends and Deal Quotation Filter starts */

			if (dealSearchReq.getQuoteNo() != null && dealSearchReq.getQuoteNo() != "") {
				quoteFilter = quoteFilter + " and dq.quoteNo like '%" + dealSearchReq.getQuoteNo() + "%'";
			}
			if (dealSearchReq.getQuoteSubject() != null && dealSearchReq.getQuoteSubject() != "") {
				quoteFilter = quoteFilter + " and dq.subject like '%" + dealSearchReq.getQuoteSubject() + "%'";
			}
			if (dealSearchReq.getQuoteStage() != null && dealSearchReq.getQuoteStage() != "") {
				quoteFilter = quoteFilter + " and dq.quoteStage = '" + dealSearchReq.getQuoteStage() + "'";
			}
			if (dealSearchReq.getQuoteDateFrom() != null && dealSearchReq.getQuoteDateTo() != null) {
				quoteFilter = quoteFilter + " and dq.quoteDate between '"
						+ Util.sdfFormatter(dealSearchReq.getQuoteDateFrom()) + "' and '"
						+ Util.sdfFormatter(dealSearchReq.getQuoteDateTo()) + "'";
			}
			if (dealSearchReq.getQuoteValidDateFrom() != null && dealSearchReq.getQuoteValidDateTo() != null) {
				quoteFilter = quoteFilter + " and dq.validUntil between '"
						+ Util.sdfFormatter(dealSearchReq.getQuoteValidDateFrom()) + "' and '"
						+ Util.sdfFormatter(dealSearchReq.getQuoteValidDateTo()) + "'";
			}

			/* Deals Quotation Filter ends and Deal Purchase Order Filter starts */

			if (dealSearchReq.getPoNo() != null && dealSearchReq.getPoNo() != "") {
				poFilter = poFilter + " and dpo.purchaseOrderNo like '%" + dealSearchReq.getPoNo() + "%'";
			}
			if (dealSearchReq.getPoSubject() != null && dealSearchReq.getPoSubject() != "") {
				poFilter = poFilter + " and dpo.subject like '%" + dealSearchReq.getPoSubject() + "%'";
			}
			if (dealSearchReq.getPoStatus() != null && dealSearchReq.getPoStatus() != "") {
				poFilter = poFilter + " and dpo.status = '" + dealSearchReq.getPoStatus() + "'";
			}
			if (dealSearchReq.getPoRequisitionNo() != null && dealSearchReq.getPoRequisitionNo() != "") {
				poFilter = poFilter + " and dpo.requisitionNo like '%" + dealSearchReq.getPoRequisitionNo() + "%'";
			}
			if (dealSearchReq.getPoTrackingNo() != null && dealSearchReq.getPoTrackingNo() != "") {
				poFilter = poFilter + " and dpo.trackingNo like '%" + dealSearchReq.getPoTrackingNo() + "%'";
			}
			if (dealSearchReq.getPoDateFrom() != null && dealSearchReq.getPoDateTo() != null) {
				poFilter = poFilter + " and dpo.purchaseOrderDate between '"
						+ Util.sdfFormatter(dealSearchReq.getPoDateFrom()) + "' and '"
						+ Util.sdfFormatter(dealSearchReq.getPoDateTo()) + "'";
			}
			if (dealSearchReq.getPoDueDateFrom() != null && dealSearchReq.getPoDueDateTo() != null) {
				poFilter = poFilter + " and dpo.dueDate between '" + Util.sdfFormatter(dealSearchReq.getPoDueDateFrom())
						+ "' and '" + Util.sdfFormatter(dealSearchReq.getPoDueDateTo()) + "'";
			}

			/* Deals Purchase Order Filter ends and Deal Sales Order starts */

			if (dealSearchReq.getSoNo() != null && dealSearchReq.getSoNo() != "") {
				soFilter = soFilter + " and dso.salesOrderNo like '%" + dealSearchReq.getSoNo() + "%'";
			}
			if (dealSearchReq.getSoSubject() != null && dealSearchReq.getSoSubject() != "") {
				soFilter = soFilter + " and dso.subject like '%" + dealSearchReq.getSoSubject() + "%'";
			}
			if (dealSearchReq.getSoDueDateFrom() != null && dealSearchReq.getSoDueDateTo() != null) {
				soFilter = soFilter + " and dso.dueDate between '" + Util.sdfFormatter(dealSearchReq.getSoDueDateFrom())
						+ "' and '" + Util.sdfFormatter(dealSearchReq.getPoDueDateTo()) + "'";
			}
			if (dealSearchReq.getSoStatus() != null && dealSearchReq.getSoStatus() != "") {
				soFilter = soFilter + " and dso.status = '" + dealSearchReq.getSoStatus() + "'";
			}

			/* Deals Sales Order Filter ends and Deal Proforma Invoice starts */

			if (dealSearchReq.getProInvoiceNo() != null && dealSearchReq.getProInvoiceNo() != "") {
				proInvoiceFilter = proInvoiceFilter + " and dpi.proformaInvoiceNo like '%"
						+ dealSearchReq.getProInvoiceNo() + "%'";
			}
			if (dealSearchReq.getProInvoiceSubject() != null && dealSearchReq.getProInvoiceSubject() != "") {
				proInvoiceFilter = proInvoiceFilter + " and dpi.subject like '%" + dealSearchReq.getProInvoiceSubject()
						+ "%'";
			}
			if (dealSearchReq.getProInvoiceDateFrom() != null && dealSearchReq.getProInvoiceDateTo() != null) {
				proInvoiceFilter = proInvoiceFilter + " and dpi.invoiceDate between '"
						+ Util.sdfFormatter(dealSearchReq.getProInvoiceDateFrom()) + "' and '"
						+ Util.sdfFormatter(dealSearchReq.getProInvoiceDateTo()) + "'";
			}
			if (dealSearchReq.getProInvoiceDueDateFrom() != null && dealSearchReq.getProInvoiceDueDateTo() != null) {
				proInvoiceFilter = proInvoiceFilter + " and dpi.dueDate between '"
						+ Util.sdfFormatter(dealSearchReq.getProInvoiceDueDateFrom()) + "' and '"
						+ Util.sdfFormatter(dealSearchReq.getProInvoiceDueDateTo()) + "'";
			}

			/* Deals Proforma Invoice Filter ends and Deal Invoice starts */

			if (dealSearchReq.getInvoiceNo() != null && dealSearchReq.getInvoiceNo() != "") {
				invoiceFilter = invoiceFilter + " and di.invoiceNo like '%" + dealSearchReq.getInvoiceNo() + "%'";
			}
			if (dealSearchReq.getInvoiceSubject() != null && dealSearchReq.getInvoiceSubject() != "") {
				invoiceFilter = invoiceFilter + " and di.subject like '%" + dealSearchReq.getInvoiceSubject() + "%'";
			}
			if (dealSearchReq.getInvoiceDateFrom() != null && dealSearchReq.getInvoiceDateTo() != null) {
				invoiceFilter = invoiceFilter + " and di.invoiceDate between '"
						+ Util.sdfFormatter(dealSearchReq.getInvoiceDateFrom()) + "' and '"
						+ Util.sdfFormatter(dealSearchReq.getInvoiceDateTo()) + "'";
			}
			if (dealSearchReq.getInvoiceDueDateFrom() != null && dealSearchReq.getInvoiceDueDateTo() != null) {
				invoiceFilter = invoiceFilter + " and di.dueDate between '"
						+ Util.sdfFormatter(dealSearchReq.getInvoiceDueDateFrom()) + "' and '"
						+ Util.sdfFormatter(dealSearchReq.getInvoiceDueDateTo()) + "'";
			}
			if (dealSearchReq.getInvoiceStatus() != null && dealSearchReq.getInvoiceStatus() != "") {
				invoiceFilter = invoiceFilter + " and di.invoiceStatus = '" + dealSearchReq.getInvoiceStatus() + "'";
			}
			if (dealSearchReq.getGstMonth() != null && dealSearchReq.getGstMonth() != "") {
				invoiceFilter = invoiceFilter + " and di.gstMonth = '" + dealSearchReq.getGstMonth() + "'";
			}
			if (dealSearchReq.getGstYear() != null && dealSearchReq.getGstYear() != "") {
				invoiceFilter = invoiceFilter + " and di.gstYear = '" + dealSearchReq.getGstYear() + "'";
			}

			/* Deal Invoice ends */

			if (!dealFilter.isEmpty()) {
				filterQuery = filterQuery + dealFilter;
			}

			if (!quoteFilter.isEmpty()) {
				filterQuery = filterQuery + " and d.id in (select dealId from DealQuotation dq where 2>1 " + quoteFilter
						+ ")";
			}

			if (!poFilter.isEmpty()) {
				filterQuery = filterQuery + " and d.id in (select dealId from DealPurchaseOrder dpo where 2>1 "
						+ poFilter + ")";
			}

			if (!soFilter.isEmpty()) {
				filterQuery = filterQuery + " and d.id in (select dealId from DealSalesOrder dso where 2>1 " + soFilter
						+ ")";
			}

			if (!proInvoiceFilter.isEmpty()) {
				filterQuery = filterQuery + " and d.id in (select dealId from DealProformaInvoice dpi where 2>1 "
						+ proInvoiceFilter + ")";
			}

			if (!invoiceFilter.isEmpty()) {
				filterQuery = filterQuery + " and d.id in (select dealId from DealInvoice di where 2>1 " + invoiceFilter
						+ ")";
			}

			System.out.println(filterQuery);

			Query query = em.createQuery("select d from Deal d where 2 > 1 " + filterQuery, Deal.class);

			deals = query.getResultList();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			logger.error(e.getMessage());
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("Deals", deals);

		return resp;
	}

	@Override
	public Map<String, Object> getDeal(int dealId) {
		Map<String, Object> resp = new HashMap<>();
		Deal deal = new Deal();
		List<DealProducts> dealProducts = new ArrayList<>();
		List<DealContacts> dealContacts = new ArrayList<>();
		try {

			deal = dealRepo.findById(dealId);

			dealProducts = dealProductsRepo.findByDealId(dealId);

			dealContacts = dealContactsRepo.findByDealId(dealId);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("Deal", deal);
		resp.put("DealProducts", dealProducts);
		resp.put("DealContacts", dealContacts);

		return resp;
	}

	@Override
	public Map<String, Object> deleteDeal(int dealId) {
		Map<String, Object> resp = new HashMap<>();
		try {

			if (dealRepo.findById(dealId) != null) {

				dealRepo.deleteById(dealId);

				quoteRepo.deleteByDealId(dealId);

				poRepo.deleteByDealId(dealId);

				soRepo.deleteByDealId(dealId);

				dpimRepo.deleteByDealId(dealId);

				dealProductsRepo.deleteByDealId(dealId);

				dealContactsRepo.deleteByDealId(dealId);

				notesRepo.deleteByDealId(dealId);

				noteAttachRepo.deleteByDealId(dealId);

				paymentRepo.deleteByDealId(dealId);

				List<DealInvoice> invs = invRepo.findByDealId(dealId);

				if (invs.size() > 0) {
					invRepo.deleteAll(invs);

					List<Integer> invoiceIds = invs.stream().map(DealInvoice::getId).distinct()
							.collect(Collectors.toList());

					invProdRepo.deleteByInvoiceIdsIn(invoiceIds);

				}

				resp.putAll(Util.SuccessResponse());
			} else {
				resp.putAll(Util.invalidMessage("No Such Deal Found"));
			}

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}

		return resp;
	}

	@Override
	public Map<String, Object> saveNotes(NoteRequest note) {
		Map<String, Object> resp = new HashMap<>();

		try {
			note.setNote(notesRepo.save(note.getNote()));

			if (note.getNoteAttachments().size() > 0) {

				File directory = new File(
						contentPath + "/Deals/" + note.getNote().getDealId() + "/Notes/" + note.getNote().getId());

				System.out.println(directory.getAbsolutePath());
				if (!directory.exists()) {
					directory.mkdirs();
				}

				note.getNoteAttachments().forEach(noteAtt -> {
					noteAtt.setNoteId(note.getNote().getId());
					File convertFile = new File(directory.getAbsoluteFile() + "/" + noteAtt.getFilename());

					try {
						convertFile.createNewFile();
						FileOutputStream fout = new FileOutputStream(convertFile);
						fout.write(com.google.api.client.util.Base64.decodeBase64(noteAtt.getFile().getBytes()));
						fout.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

				});

				note.setNoteAttachments(noteAttachRepo.saveAll(note.getNoteAttachments()));
			}

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("Note", note.getNote());
		resp.put("NoteAttachments", note.getNoteAttachments());
		return resp;
	}

	@Override
	public Map<String, Object> deleteNotes(Notes note) {
		Map<String, Object> resp = new HashMap<>();

		try {
			notesRepo.delete(note);
			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public Map<String, Object> getAllNotes(Notes note) {
		Map<String, Object> resp = new HashMap<>();
		List<Notes> notes = new ArrayList<>();
		List<NoteAttachments> noteAttachments = new ArrayList<>();
		try {
			notes = notesRepo.findByDealId(note.getDealId());

			if (notes.size() > 0)
				noteAttachments = noteAttachRepo.findByDealId(note.getDealId());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("Notes", notes);
		resp.put("NoteAttachments", noteAttachments);

		return resp;
	}

	@Override
	public Map<String, Object> getNoteAttachment(NoteAttachments noteAtt) {
		Map<String, Object> resp = new HashMap<>();
		try {
			String path = "";
			File file = null;
			try {

				path = contentPath + "/Deals/" + noteAtt.getDealId() + "/Notes/" + noteAtt.getNoteId() + "/"
						+ noteAtt.getFilename() + "";

				System.out.println(path);
				file = new File(path);

				byte[] fileContent = FileUtils.readFileToByteArray(file);
				String encodedString = Base64.getEncoder().encodeToString(fileContent);

				System.out.println(encodedString);

				noteAtt.setFile(encodedString);

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
		resp.put("NoteAttachment", noteAtt);
		return resp;
	}

	@Override
	public Map<String, Object> saveDealQuotation(DealQuotation quote) {
		Map<String, Object> resp = new HashMap<>();
		try {
			DealQuotation dqtemp = quoteRepo.findByQuoteNo(quote.getQuoteNo());

			if (quote.getId() == 0 && dqtemp != null) {
				resp.putAll(Util.invalidMessage("Quote No Already Exist"));
			} else if (dqtemp != null && quote.getId() != dqtemp.getId()) {
				resp.putAll(Util.invalidMessage("Quote No Already Exist with another Quote"));
			} else {

				quote = quoteRepo.save(quote);

				Query query = em.createQuery("UPDATE Deal d SET d.quoteNo = :quoteNo WHERE d.id = :id");
				query.setParameter("quoteNo", quote.getQuoteNo());
				query.setParameter("id", quote.getDealId());
				query.executeUpdate();

				resp.putAll(Util.SuccessResponse());
			}

		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		resp.put("DealQuotation", quote);
		return resp;
	}

	@Override
	public Map<String, Object> getDealQuotation(int dealId) {
		Map<String, Object> resp = new HashMap<>();
		DealQuotation dealQuote = new DealQuotation();
		String autoGeneratedQuoteNo = "Q/001";
		String lastTerms = "";
		try {

			dealQuote = quoteRepo.findByDealId(dealId);

			if (dealQuote == null) { // if null AutoIncrement the Quotation No. from last Quotation.

				DealQuotation dealQuoteTemp = quoteRepo.findLastQuotationByType(dealId);

				if (dealQuoteTemp != null) {
					autoGeneratedQuoteNo = Util.autoIncrementNo(dealQuoteTemp.getQuoteNo());

					lastTerms = dealQuoteTemp.getTerms();

				}

			}

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealQuotation", dealQuote);
		resp.put("AutoGeneratedQuoteNo", autoGeneratedQuoteNo);
		resp.put("LastTerms", lastTerms);

		return resp;
	}

//	String autoIncrementNo(String inNo) {
//
//		String outNo = "";
//
//		try {
//			System.out.println(inNo);
//			String[] bits = inNo.split("/");
//
//			if (inNo.contains("/"))
//				bits = inNo.split("/");
//			else if (inNo.contains("-"))
//				bits = inNo.split("-");
//
//			String original = bits[bits.length - 1];
//			String incremented = String.format("%0" + original.length() + "d", Integer.parseInt(original) + 1);
//
//			inNo = inNo.replaceAll(original, incremented);
//			outNo = inNo;
//
//			System.out.println(inNo);
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//			outNo = inNo + "/01";
//		}
//
//		return outNo;
//
//	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> generateQuotationPDFTemplate1Lite(DealRequest dealReq) {
		Map<String, Object> resp = new HashMap<>();

		try {

			dealReq.setDealQuotation(quoteRepo.findById(dealReq.getDealQuotation().getId()));

			dealReq.setDeal(dealRepo.findById(dealReq.getDealQuotation().getDealId()));

			dealReq.setDealProducts(dealProductsRepo.findByDealId(dealReq.getDealQuotation().getDealId()));

			System.out.println(dealReq.getDealQuotation().getQuoteNo());
			System.out.println(dealReq.getDealQuotation());

			InputStream stream = this.getClass()
					.getResourceAsStream("/reports/FinalTemplates/Template_1/Template_1_Lite.jrxml");

			final Map<String, Object> parameters = new HashMap<>();
			InfoDetails info = infoDetailRepo.findById(1);

			Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

			System.out.println(info.toString());

			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddressHTML1());
			parameters.put("cmp_logo_url", info.getLogoAsFile());

			parameters.put("is_discount_applied", dealReq.isDiscountApplied());
			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", dealReq.getDesignation());
			parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
			parameters.put("online_payment_url", info.getInstamojoPaymentURL());

			parameters.put("terms", dealReq.getDealQuotation().getTerms().replaceAll("\n", "<br>"));
			parameters.put("grand_total_text", "Grand Total : ");
			parameters.put("grand_total", "Rs." + Util.decimalFormatter(dealReq.getDeal().getGrandTotal()));
			parameters.put("amount_in_words", Util.EnglishNumberToWords(dealReq.getDeal().getGrandTotal()));

			String deal_date_label = "", deal_date_text = "";

			deal_date_label = deal_date_label + "Quote Number : <br>";
			deal_date_text = deal_date_text + String.valueOf(dealReq.getDealQuotation().getQuoteNo()) + "<br>";
			deal_date_label = deal_date_label + "Quote Date : <br>";
			deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealQuotation().getQuoteDate()) + "<br>";
			deal_date_label = deal_date_label + "Valid Until : <br>";
			deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealQuotation().getValidUntil()) + "<br>";

			parameters.put("deal_date_label", deal_date_label);
			parameters.put("deal_date_text", deal_date_text);

			if (dealReq.getDeal().getDealType().equalsIgnoreCase("AMC") && dealReq.getDeal().getAmcFromDate() != null
					&& dealReq.getDeal().getAmcToDate() != null) {
				String subject = "Sub: " + dealReq.getDealQuotation().getSubject()
						+ " <br>AMC Charges for the following items for the period of "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcFromDate(), "dd/MM/yyyy") + " to "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcToDate(), "dd/MM/yyyy");
				parameters.put("subject", subject);
			} else {
				parameters.put("subject", "Sub: " + dealReq.getDealQuotation().getSubject());
			}
			parameters.put("dealtype_label", dealReq.getDeal().getDealType().toUpperCase() + " QUOTATION");

			parameters.put("billing_to", dealReq.getBillingToAddress());
			parameters.put("shipping_to", dealReq.getShippingToAddress());

			String price_summary_label = "", price_summary_text = "";

			price_summary_label = price_summary_label + "Original Rate : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getSubTotal())
					+ "<br>";

			if (dealReq.getDeal().getDiscount() > 0) {
				price_summary_label = price_summary_label + "Discount : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getDiscount())
						+ "<br>";
			}

			price_summary_label = price_summary_label + "Taxable Amount : <br>";
			price_summary_text = price_summary_text + "Rs."
					+ Util.decimalFormatter(dealReq.getDeal().getSubTotal() - dealReq.getDeal().getDiscount()) + "<br>";

			if (dealReq.getDeal().getGstType().equalsIgnoreCase("IGST")) {
				price_summary_label = price_summary_label + "IGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax())
						+ "<br>";
			} else {
				price_summary_label = price_summary_label + "CGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax() / 2)
						+ "<br>";

				price_summary_label = price_summary_label + "SGST : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter((dealReq.getDeal().getTax() / 2)) + "<br>";
			}

			if (dealReq.getDeal().getAdjustment() != 0.00) {
				price_summary_label = price_summary_label + "Adjustment : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ String.format("%.2f", dealReq.getDeal().getAdjustment()) + "<br>";
			}

			parameters.put("price_summary_label", price_summary_label);
			parameters.put("price_summary_text", price_summary_text);

			System.out.println(parameters.toString());

			List<Map<String, String>> datasource = new ArrayList<>();

			List<Integer> partIds = dealReq.getDealProducts().stream().mapToInt(DealProducts::getPartId).distinct()
					.boxed().sorted().collect(Collectors.toList());

			int sno = 1;
			for (int partId : partIds) {
				Map<String, String> data = new HashMap<>();

				List<DealProducts> dp = dealReq.getDealProducts().stream().filter(_dp -> _dp.getPartId() == partId)
						.collect(Collectors.toList());

				data.put("sno", sno + ".");
				data.put("total",
						Util.decimalFormatter(dp.stream().map(prod -> (double) prod.getPrice() * prod.getQuantity())
								.mapToDouble(amount -> amount).sum()));

				int _part_sno = 1;
				String name_description = "";
				for (DealProducts prod : dp) {
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

			File directory = new File(contentPath + "/Deals/" + dealReq.getDeal().getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}

			if (dealReq.getExportType() != null && dealReq.getExportType().equalsIgnoreCase("rtf")) {

				dealReq.getDealQuotation()
						.setFilename(dealReq.getDealQuotation().getQuoteNo().replaceAll("/", "-") + ".rtf");
				final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename();
				System.out.println(filePath);

//				 Export the report to a RTF file.
				JRRtfExporter exporter = new JRRtfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
				exporter.exportReport();

			} else {
				dealReq.getDealQuotation()
						.setFilename(dealReq.getDealQuotation().getQuoteNo().replaceAll("/", "-") + ".pdf");
				final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename();
				System.out.println(filePath);

//		 Export the report to a PDF file.
				JasperExportManager.exportReportToPdfFile(print, filePath);

				// Merge Preamble if any
				System.out.println(dealReq.getPreamble());
				if (dealReq.getPreamble() != null && !dealReq.getPreamble().isEmpty()) {
					List<String> filePaths = new ArrayList<>();
					if (dealReq.getPreamblePosition() != null) {
						if (dealReq.getPreamblePosition().equalsIgnoreCase("Quotation First")) {
							filePaths.add(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
							filePaths.add(contentPath + "/_preamble_documents/" + dealReq.getPreamble());
						} else {
							filePaths.add(contentPath + "/_preamble_documents/" + dealReq.getPreamble());
							filePaths.add(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
						}
					} else {
						filePaths.add(contentPath + "/_preamble_documents/" + dealReq.getPreamble());
						filePaths.add(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
					}
					System.out.println(filePaths);
					System.out.println(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
					Util.mergePDF(filePaths,
							directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
				}
			}

			quoteRepo.save(dealReq.getDealQuotation());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealQuotation", dealReq.getDealQuotation());
		resp.put("Deal", dealReq.getDeal());
		return resp;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> generateQuotationPDFTemplate2Lite(DealRequest dealReq) {
		Map<String, Object> resp = new HashMap<>();

		try {

			dealReq.setDealQuotation(quoteRepo.findById(dealReq.getDealQuotation().getId()));

			dealReq.setDeal(dealRepo.findById(dealReq.getDealQuotation().getDealId()));

			dealReq.setDealProducts(dealProductsRepo.findByDealId(dealReq.getDealQuotation().getDealId()));

			System.out.println(dealReq.toString());

			InputStream stream = null;

			if (dealReq.getDeal().getGstType().equals("IGST")) {
				stream = this.getClass().getResourceAsStream("/reports/Quotation/Quote_Template_2_IGST.jrxml");
			} else {
				stream = this.getClass().getResourceAsStream("/reports/Quotation/Quote_Template_2_CGST_SGST.jrxml");
			}

			final Map<String, Object> parameters = new HashMap<>();

			InfoDetails info = infoDetailRepo.findById(1);
			Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddressHTML2());
			parameters.put("cmp_logo_url", info.getLogoAsFile());

			parameters.put("is_discount_applied", dealReq.isDiscountApplied());
			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", dealReq.getDesignation());
			parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
			parameters.put("online_payment_url", info.getInstamojoPaymentURL());

			parameters.put("terms", dealReq.getDealQuotation().getTerms().replaceAll("\n", "<br>"));
			parameters.put("vailid_until", Util.sdfFormatter(dealReq.getDealQuotation().getValidUntil()));
			parameters.put("quote_date", Util.sdfFormatter(dealReq.getDealQuotation().getQuoteDate()));
			parameters.put("quote_no", String.valueOf(dealReq.getDealQuotation().getQuoteNo()));
			parameters.put("grand_total", "Rs." + Util.decimalFormatter(dealReq.getDeal().getGrandTotal()));
			parameters.put("amount_in_words", Util.EnglishNumberToWords(dealReq.getDeal().getGrandTotal()));

			if (dealReq.getDeal().getDealType().equalsIgnoreCase("AMC") && dealReq.getDeal().getAmcFromDate() != null
					&& dealReq.getDeal().getAmcToDate() != null) {
				String subject = "Sub: " + dealReq.getDealQuotation().getSubject()
						+ " <br>AMC Charges for the following items for the period of "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcFromDate(), "dd/MM/yyyy") + " to "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcToDate(), "dd/MM/yyyy");
				parameters.put("subject", subject);
			} else {
				parameters.put("subject", "Sub: " + dealReq.getDealQuotation().getSubject());
			}

			parameters.put("billing_to", dealReq.getBillingToAddress());
			parameters.put("shipping_to", dealReq.getShippingToAddress());

			String price_summary_label = "", price_summary_text = "";

			price_summary_label = price_summary_label + "Original Rate : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getSubTotal())
					+ "<br>";

			if (dealReq.getDeal().getDiscount() > 0) {
				price_summary_label = price_summary_label + "Discount : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getDiscount())
						+ "<br>";
			}

			price_summary_label = price_summary_label + "Taxable Amount : <br>";
			price_summary_text = price_summary_text + "Rs."
					+ Util.decimalFormatter(dealReq.getDeal().getSubTotal() - dealReq.getDeal().getDiscount()) + "<br>";

			if (dealReq.getDeal().getGstType().equalsIgnoreCase("IGST")) {
				price_summary_label = price_summary_label + "IGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax())
						+ "<br>";
			} else {
				price_summary_label = price_summary_label + "CGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax() / 2)
						+ "<br>";

				price_summary_label = price_summary_label + "SGST : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter((dealReq.getDeal().getTax() / 2)) + "<br>";
			}

			if (dealReq.getDeal().getAdjustment() > 0) {
				price_summary_label = price_summary_label + "Adjustment : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ String.format("%.2f", dealReq.getDeal().getAdjustment()) + "<br>";
			}

			parameters.put("price_summary_label", price_summary_label);
			parameters.put("price_summary_text", price_summary_text);

			System.out.println(parameters.toString());
			System.out.println("::::no:::::" + dealReq.getDealQuotation().getQuoteNo());

			List<Map<String, String>> datasource = new ArrayList<>();

			dealReq.getDealProducts().forEach(prod -> {
				Map<String, String> data = new HashMap<>();

				data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
				data.put("quantity", prod.getQuantityAsHTMLText());
				data.put("price", Util.decimalFormatter(prod.getRateAmount()));
				data.put("rate", Util.decimalFormatter(prod.getPrice()));
				data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
				if (dealReq.getDeal().getGstType().equals("IGST")) {
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

			File directory = new File(contentPath + "/Deals/" + dealReq.getDeal().getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}

			if (dealReq.getExportType() != null && dealReq.getExportType().equalsIgnoreCase("rtf")) {

				dealReq.getDealQuotation()
						.setFilename(dealReq.getDealQuotation().getQuoteNo().replaceAll("/", "-") + ".rtf");
				final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename();
				System.out.println(filePath);

//				 Export the report to a RTF file.
				JRRtfExporter exporter = new JRRtfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
				exporter.exportReport();

			} else {
				dealReq.getDealQuotation()
						.setFilename(dealReq.getDealQuotation().getQuoteNo().replaceAll("/", "-") + ".pdf");
				final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename();
				System.out.println(filePath);

//		 Export the report to a PDF file.
				JasperExportManager.exportReportToPdfFile(print, filePath);

				// Merge Preamble if any
				System.out.println(dealReq.getPreamble());
				if (dealReq.getPreamble() != null && !dealReq.getPreamble().isEmpty()) {
					List<String> filePaths = new ArrayList<>();
					if (dealReq.getPreamblePosition() != null) {
						if (dealReq.getPreamblePosition().equalsIgnoreCase("Quotation First")) {
							filePaths.add(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
							filePaths.add(contentPath + "/_preamble_documents/" + dealReq.getPreamble());
						} else {
							filePaths.add(contentPath + "/_preamble_documents/" + dealReq.getPreamble());
							filePaths.add(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
						}
					} else {
						filePaths.add(contentPath + "/_preamble_documents/" + dealReq.getPreamble());
						filePaths.add(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
					}
					System.out.println(filePaths);
					System.out.println(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
					Util.mergePDF(filePaths,
							directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
				}
			}

			quoteRepo.save(dealReq.getDealQuotation());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealQuotation", dealReq.getDealQuotation());
		resp.put("Deal", dealReq.getDeal());
		return resp;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> generateQuotationPDFTemplate3Lite(DealRequest dealReq) {

		Map<String, Object> resp = new HashMap<>();

		try {

			dealReq.setDealQuotation(quoteRepo.findById(dealReq.getDealQuotation().getId()));

			dealReq.setDeal(dealRepo.findById(dealReq.getDealQuotation().getDealId()));

			dealReq.setDealProducts(dealProductsRepo.findByDealId(dealReq.getDealQuotation().getDealId()));

			System.out.println(dealReq.getDealQuotation().getQuoteNo());
			System.out.println(dealReq.getDealQuotation());

			InputStream stream = this.getClass()
					.getResourceAsStream("/reports/FinalTemplates/Template_3/Template_3_Lite.jrxml");

			final Map<String, Object> parameters = new HashMap<>();
			InfoDetails info = infoDetailRepo.findById(1);

			Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

			System.out.println(info.toString());

			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddressHTML1());
			parameters.put("cmp_logo_url", info.getLogoAsFile());

			parameters.put("is_discount_applied", dealReq.isDiscountApplied());
			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", dealReq.getDesignation());
			parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
			parameters.put("online_payment_url", info.getInstamojoPaymentURL());

			parameters.put("terms", dealReq.getDealQuotation().getTerms().replaceAll("\n", "<br>"));

			if (dealReq.getDeal().getDealType().equalsIgnoreCase("AMC") && dealReq.getDeal().getAmcFromDate() != null
					&& dealReq.getDeal().getAmcToDate() != null) {
				String subject = "Sub: " + dealReq.getDealQuotation().getSubject()
						+ " <br> Charges for the following items for the period of "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcFromDate(), "dd/MM/yyyy") + " to "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcToDate(), "dd/MM/yyyy");
				parameters.put("subject", subject);
			} else {
				parameters.put("subject", "Sub: " + dealReq.getDealQuotation().getSubject());
			}
			parameters.put("dealtype_label", dealReq.getDeal().getDealType().toUpperCase() + " QUOTATION");

			parameters.put("billing_to", dealReq.getBillingToAddress());
			parameters.put("shipping_to", dealReq.getShippingToAddress());

			String price_summary_label = "", price_summary_text = "";
			String deal_date_label = "", deal_date_text = "";

			deal_date_label = deal_date_label + "Quote Number : <br>";
			deal_date_text = deal_date_text + String.valueOf(dealReq.getDealQuotation().getQuoteNo()) + "<br>";
			deal_date_label = deal_date_label + "Quote Date : <br>";
			deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealQuotation().getQuoteDate()) + "<br>";
			deal_date_label = deal_date_label + "Valid Until : <br>";
			deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealQuotation().getValidUntil()) + "<br>";

			parameters.put("deal_date_label", deal_date_label);
			parameters.put("deal_date_text", deal_date_text);

			price_summary_label = price_summary_label + "Original Rate : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getSubTotal())
					+ "<br>";

			if (dealReq.getDeal().getDiscount() > 0) {
				price_summary_label = price_summary_label + "Discount : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getDiscount())
						+ "<br>";
			}

			price_summary_label = price_summary_label + "Taxable Amount : <br>";
			price_summary_text = price_summary_text + "Rs."
					+ Util.decimalFormatter(dealReq.getDeal().getSubTotal() - dealReq.getDeal().getDiscount()) + "<br>";

			if (dealReq.getDeal().getGstType().equalsIgnoreCase("IGST")) {
				price_summary_label = price_summary_label + "IGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax())

						+ "<br>";
				parameters.put("Igst_amount", Util.decimalFormatter(dealReq.getDeal().getTax()));
			} else {
				price_summary_label = price_summary_label + "CGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax() / 2)
						+ "<br>";

				price_summary_label = price_summary_label + "SGST : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter((dealReq.getDeal().getTax() / 2)) + "<br>";
				parameters.put("cgst_amount", Util.decimalFormatter(dealReq.getDeal().getTax() / 2));
				parameters.put("sgst_amount", Util.decimalFormatter(dealReq.getDeal().getTax() / 2));
			}

			if (dealReq.getDeal().getAdjustment() != 0.00) {
				price_summary_label = price_summary_label + "Adjustment : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ String.format("%.2f", dealReq.getDeal().getAdjustment()) + "<br>";
			}

			parameters.put("adjust", Util.decimalFormatter(dealReq.getDeal().getTax() / 2));

			parameters.put("price_summary_label", price_summary_label);
			parameters.put("price_summary_text", price_summary_text);
			parameters.put("deal_amount_label", "Grand Total :");
			parameters.put("deal_amount_text", "Rs." + Util.decimalFormatter(dealReq.getDeal().getGrandTotal()));
			parameters.put("amount_in_words", Util.EnglishNumberToWords(dealReq.getDeal().getGrandTotal()));

			parameters.put("taxable_amount",
					Util.decimalFormatter(dealReq.getDeal().getSubTotal() - dealReq.getDeal().getDiscount()));

			System.out.println(parameters.toString());

			List<Map<String, String>> datasource = new ArrayList<>();

			List<Integer> partIds = dealReq.getDealProducts().stream().mapToInt(DealProducts::getPartId).distinct()
					.boxed().sorted().collect(Collectors.toList());

			int sno = 1;
			for (int partId : partIds) {
				Map<String, String> data = new HashMap<>();

				List<DealProducts> dp = dealReq.getDealProducts().stream().filter(_dp -> _dp.getPartId() == partId)
						.collect(Collectors.toList());

				data.put("sno", sno + ".");
				data.put("total",
						Util.decimalFormatter(dp.stream().map(prod -> (double) prod.getPrice() * prod.getQuantity())
								.mapToDouble(amount -> amount).sum()));

				int _part_sno = 1;
				String name_description = "";
				for (DealProducts prod : dp) {
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

			File directory = new File(contentPath + "/Deals/" + dealReq.getDeal().getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}

			if (dealReq.getExportType() != null && dealReq.getExportType().equalsIgnoreCase("rtf")) {

				dealReq.getDealQuotation()
						.setFilename(dealReq.getDealQuotation().getQuoteNo().replaceAll("/", "-") + ".rtf");
				final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename();
				System.out.println(filePath);

//				 Export the report to a RTF file.
				JRRtfExporter exporter = new JRRtfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
				exporter.exportReport();

			} else {
				dealReq.getDealQuotation()
						.setFilename(dealReq.getDealQuotation().getQuoteNo().replaceAll("/", "-") + ".pdf");
				final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename();
				System.out.println(filePath);

//		 Export the report to a PDF file.
				JasperExportManager.exportReportToPdfFile(print, filePath);

				// Merge Preamble if any
				System.out.println(dealReq.getPreamble());
				if (dealReq.getPreamble() != null && !dealReq.getPreamble().isEmpty()) {
					List<String> filePaths = new ArrayList<>();
					if (dealReq.getPreamblePosition() != null) {
						if (dealReq.getPreamblePosition().equalsIgnoreCase("Quotation First")) {
							filePaths.add(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
							filePaths.add(contentPath + "/_preamble_documents/" + dealReq.getPreamble());
						} else {
							filePaths.add(contentPath + "/_preamble_documents/" + dealReq.getPreamble());
							filePaths.add(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
						}
					} else {
						filePaths.add(contentPath + "/_preamble_documents/" + dealReq.getPreamble());
						filePaths.add(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
					}
					System.out.println(filePaths);
					System.out.println(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
					Util.mergePDF(filePaths,
							directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
				}
			}

			quoteRepo.save(dealReq.getDealQuotation());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealQuotation", dealReq.getDealQuotation());
		resp.put("Deal", dealReq.getDeal());
		return resp;

	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> generateQuotationPDFTemplate1(DealRequest dealReq) {
		Map<String, Object> resp = new HashMap<>();

		try {

			dealReq.setDealQuotation(quoteRepo.findById(dealReq.getDealQuotation().getId()));

			dealReq.setDeal(dealRepo.findById(dealReq.getDealQuotation().getDealId()));

			dealReq.setDealProducts(dealProductsRepo.findByDealId(dealReq.getDealQuotation().getDealId()));

			System.out.println(dealReq.getDealQuotation().getQuoteNo());
			System.out.println(dealReq.getDealQuotation());

			InputStream stream = null;

			if (dealReq.getDeal().getGstType().equals("IGST")) {
				stream = this.getClass()
						.getResourceAsStream("/reports/FinalTemplates/Template_1/Template_1_IGST.jrxml");
			} else {
				stream = this.getClass()
						.getResourceAsStream("/reports/FinalTemplates/Template_1/Template_1_CGST_SGST.jrxml");
			}

			final Map<String, Object> parameters = new HashMap<>();
			InfoDetails info = infoDetailRepo.findById(1);

			Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

			System.out.println(info.toString());

			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddressHTML1());
			parameters.put("cmp_logo_url", info.getLogoAsFile());

			parameters.put("is_discount_applied", dealReq.isDiscountApplied());
			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", dealReq.getDesignation());
			parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
			parameters.put("online_payment_url", info.getInstamojoPaymentURL());

//			parameters.put("vailid_until", Util.sdfFormatter(dealReq.getDealQuotation().getValidUntil()));
//			parameters.put("quote_date", Util.sdfFormatter(dealReq.getDealQuotation().getQuoteDate()));
//			parameters.put("quote_no", String.valueOf(dealReq.getDealQuotation().getQuoteNo()));

			parameters.put("terms", dealReq.getDealQuotation().getTerms().replaceAll("\n", "<br>"));
			parameters.put("grand_total_text", "Grand Total : ");
			parameters.put("grand_total", "Rs." + Util.decimalFormatter(dealReq.getDeal().getGrandTotal()));
			parameters.put("amount_in_words", Util.EnglishNumberToWords(dealReq.getDeal().getGrandTotal()));

			String deal_date_label = "", deal_date_text = "";

			deal_date_label = deal_date_label + "Quote Number : <br>";
			deal_date_text = deal_date_text + String.valueOf(dealReq.getDealQuotation().getQuoteNo()) + "<br>";
			deal_date_label = deal_date_label + "Quote Date : <br>";
			deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealQuotation().getQuoteDate()) + "<br>";
			deal_date_label = deal_date_label + "Valid Until : <br>";
			deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealQuotation().getValidUntil()) + "<br>";
			deal_date_label = deal_date_label + "Reference Number : <br>";
			deal_date_text = deal_date_text + String.valueOf(dealReq.getDealQuotation().getReferenceNo()) + "<br>";
			deal_date_label = deal_date_label + "Reference Date : <br>";
			deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealQuotation().getReferenceDate()) + "<br>";
			parameters.put("deal_date_label", deal_date_label);
			parameters.put("deal_date_text", deal_date_text);

			if (dealReq.getDeal().getDealType().equalsIgnoreCase("AMC") && dealReq.getDeal().getAmcFromDate() != null
					&& dealReq.getDeal().getAmcToDate() != null) {
				String subject = "Sub: " + dealReq.getDealQuotation().getSubject()
						+ " <br>AMC Charges for the following items for the period of "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcFromDate(), "dd/MM/yyyy") + " to "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcToDate(), "dd/MM/yyyy");
				parameters.put("subject", subject);
			} else {
				parameters.put("subject", "Sub: " + dealReq.getDealQuotation().getSubject());
			}
			parameters.put("dealtype_label", dealReq.getDeal().getDealType().toUpperCase() + " QUOTATION");

			parameters.put("billing_to", dealReq.getBillingToAddress());
			parameters.put("shipping_to", dealReq.getShippingToAddress());

			String price_summary_label = "", price_summary_text = "";

			price_summary_label = price_summary_label + "Original Rate : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getSubTotal())
					+ "<br>";

			if (dealReq.getDeal().getDiscount() > 0) {
				price_summary_label = price_summary_label + "Discount : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getDiscount())
						+ "<br>";
			}

			price_summary_label = price_summary_label + "Taxable Amount : <br>";
			price_summary_text = price_summary_text + "Rs."
					+ Util.decimalFormatter(dealReq.getDeal().getSubTotal() - dealReq.getDeal().getDiscount()) + "<br>";

			if (dealReq.getDeal().getGstType().equalsIgnoreCase("IGST")) {
				price_summary_label = price_summary_label + "IGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax())
						+ "<br>";
			} else {
				price_summary_label = price_summary_label + "CGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax() / 2)
						+ "<br>";

				price_summary_label = price_summary_label + "SGST : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter((dealReq.getDeal().getTax() / 2)) + "<br>";
			}

			if (dealReq.getDeal().getAdjustment() != 0.00) {
				price_summary_label = price_summary_label + "Adjustment : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ String.format("%.2f", dealReq.getDeal().getAdjustment()) + "<br>";
			}

			parameters.put("price_summary_label", price_summary_label);
			parameters.put("price_summary_text", price_summary_text);

			System.out.println(parameters.toString());

			List<Map<String, String>> datasource = new ArrayList<>();

			List<Integer> partIds = dealReq.getDealProducts().stream().mapToInt(DealProducts::getPartId).distinct()
					.boxed().sorted().collect(Collectors.toList());

			int sno = 1;
			for (int partId : partIds) {

				for (DealProducts prod : dealReq.getDealProducts()) {
					if (prod.getPartId() == partId) {

						Map<String, String> data = new HashMap<>();

						data.put("sno", sno + ".");
						data.put("type", "product");
						data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
						data.put("quantity", prod.getQuantityAsHTMLText());
						data.put("price", Util.decimalFormatter(prod.getRateAmount()));
						data.put("rate", Util.decimalFormatter(prod.getPrice()));
						data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
						if (dealReq.getDeal().getGstType().equals("IGST")) {
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

					Double totalAmount = dealReq.getDealProducts().stream().filter(prod -> prod.getPartId() == partId)
							.mapToDouble(DealProducts::getTotalAmount).sum();

					data.put("sno", "");
					data.put("type", "subtotal");
					data.put("name_description", "");
					data.put("quantity", "");
					data.put("price", "");
					data.put("rate", "");
					data.put("total", Util.decimalFormatter(totalAmount));
					if (dealReq.getDeal().getGstType().equals("IGST")) {
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

			final JasperReport report = JasperCompileManager.compileReport(stream);
			final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

			final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

			File directory = new File(contentPath + "/Deals/" + dealReq.getDeal().getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}

			if (dealReq.getExportType() != null && dealReq.getExportType().equalsIgnoreCase("rtf")) {

				dealReq.getDealQuotation()
						.setFilename(dealReq.getDealQuotation().getQuoteNo().replaceAll("/", "-") + ".rtf");
				final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename();
				System.out.println(filePath);

//				 Export the report to a RTF file.
				JRRtfExporter exporter = new JRRtfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
				exporter.exportReport();

			} else {
				dealReq.getDealQuotation()
						.setFilename(dealReq.getDealQuotation().getQuoteNo().replaceAll("/", "-") + ".pdf");
				final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename();
				System.out.println(filePath);

//		 Export the report to a PDF file.
				JasperExportManager.exportReportToPdfFile(print, filePath);

				// Merge Preamble if any
				System.out.println(dealReq.getPreamble());
				if (dealReq.getPreamble() != null && !dealReq.getPreamble().isEmpty()) {
					List<String> filePaths = new ArrayList<>();
					if (dealReq.getPreamblePosition() != null) {
						if (dealReq.getPreamblePosition().equalsIgnoreCase("Quotation First")) {
							filePaths.add(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
							filePaths.add(contentPath + "/_preamble_documents/" + dealReq.getPreamble());
						} else {
							filePaths.add(contentPath + "/_preamble_documents/" + dealReq.getPreamble());
							filePaths.add(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
						}
					} else {
						filePaths.add(contentPath + "/_preamble_documents/" + dealReq.getPreamble());
						filePaths.add(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
					}
					System.out.println(filePaths);
					System.out.println(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
					Util.mergePDF(filePaths,
							directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
				}
			}

			quoteRepo.save(dealReq.getDealQuotation());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealQuotation", dealReq.getDealQuotation());
		resp.put("Deal", dealReq.getDeal());
		return resp;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> generateQuotationPDFTemplate2(DealRequest dealReq) {
		Map<String, Object> resp = new HashMap<>();

		try {

			dealReq.setDealQuotation(quoteRepo.findById(dealReq.getDealQuotation().getId()));

			dealReq.setDeal(dealRepo.findById(dealReq.getDealQuotation().getDealId()));

			dealReq.setDealProducts(dealProductsRepo.findByDealId(dealReq.getDealQuotation().getDealId()));

			System.out.println(dealReq.toString());

			InputStream stream = null;

			if (dealReq.getDeal().getGstType().equals("IGST")) {
				stream = this.getClass().getResourceAsStream("/reports/Quotation/Quote_Template_2_IGST.jrxml");
			} else {
				stream = this.getClass().getResourceAsStream("/reports/Quotation/Quote_Template_2_CGST_SGST.jrxml");
			}

			final Map<String, Object> parameters = new HashMap<>();

			InfoDetails info = infoDetailRepo.findById(1);
			Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddressHTML2());
			parameters.put("cmp_logo_url", info.getLogoAsFile());

			parameters.put("is_discount_applied", dealReq.isDiscountApplied());
			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", dealReq.getDesignation());
			parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
			parameters.put("online_payment_url", info.getInstamojoPaymentURL());

			parameters.put("terms", dealReq.getDealQuotation().getTerms().replaceAll("\n", "<br>"));
			parameters.put("vailid_until", Util.sdfFormatter(dealReq.getDealQuotation().getValidUntil()));
			parameters.put("quote_date", Util.sdfFormatter(dealReq.getDealQuotation().getQuoteDate()));
			parameters.put("quote_no", String.valueOf(dealReq.getDealQuotation().getQuoteNo()));
			parameters.put("grand_total", "Rs." + Util.decimalFormatter(dealReq.getDeal().getGrandTotal()));
			parameters.put("amount_in_words", Util.EnglishNumberToWords(dealReq.getDeal().getGrandTotal()));

			if (dealReq.getDeal().getDealType().equalsIgnoreCase("AMC") && dealReq.getDeal().getAmcFromDate() != null
					&& dealReq.getDeal().getAmcToDate() != null) {
				String subject = "Sub: " + dealReq.getDealQuotation().getSubject()
						+ " <br>AMC Charges for the following items for the period of "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcFromDate(), "dd/MM/yyyy") + " to "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcToDate(), "dd/MM/yyyy");
				parameters.put("subject", subject);
			} else {
				parameters.put("subject", "Sub: " + dealReq.getDealQuotation().getSubject());
			}

			parameters.put("billing_to", dealReq.getBillingToAddress());
			parameters.put("shipping_to", dealReq.getShippingToAddress());

			String price_summary_label = "", price_summary_text = "";

			price_summary_label = price_summary_label + "Original Rate : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getSubTotal())
					+ "<br>";

			if (dealReq.getDeal().getDiscount() > 0) {
				price_summary_label = price_summary_label + "Discount : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getDiscount())
						+ "<br>";
			}

			price_summary_label = price_summary_label + "Taxable Amount : <br>";
			price_summary_text = price_summary_text + "Rs."
					+ Util.decimalFormatter(dealReq.getDeal().getSubTotal() - dealReq.getDeal().getDiscount()) + "<br>";

			if (dealReq.getDeal().getGstType().equalsIgnoreCase("IGST")) {
				price_summary_label = price_summary_label + "IGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax())
						+ "<br>";
			} else {
				price_summary_label = price_summary_label + "CGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax() / 2)
						+ "<br>";

				price_summary_label = price_summary_label + "SGST : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter((dealReq.getDeal().getTax() / 2)) + "<br>";
			}

			if (dealReq.getDeal().getAdjustment() != 0.00) {
				price_summary_label = price_summary_label + "Adjustment : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ String.format("%.2f", dealReq.getDeal().getAdjustment()) + "<br>";
			}

			parameters.put("price_summary_label", price_summary_label);
			parameters.put("price_summary_text", price_summary_text);

			System.out.println(parameters.toString());
			System.out.println("::::no:::::" + dealReq.getDealQuotation().getQuoteNo());

			List<Map<String, String>> datasource = new ArrayList<>();

			dealReq.getDealProducts().forEach(prod -> {
				Map<String, String> data = new HashMap<>();

				data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
				data.put("quantity", prod.getQuantityAsHTMLText());
				data.put("price", Util.decimalFormatter(prod.getRateAmount()));
				data.put("rate", Util.decimalFormatter(prod.getPrice()));
				data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
				if (dealReq.getDeal().getGstType().equals("IGST")) {
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

			File directory = new File(contentPath + "/Deals/" + dealReq.getDeal().getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}

			if (dealReq.getExportType() != null && dealReq.getExportType().equalsIgnoreCase("rtf")) {

				dealReq.getDealQuotation()
						.setFilename(dealReq.getDealQuotation().getQuoteNo().replaceAll("/", "-") + ".rtf");
				final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename();
				System.out.println(filePath);

//				 Export the report to a RTF file.
				JRRtfExporter exporter = new JRRtfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
				exporter.exportReport();

			} else {
				dealReq.getDealQuotation()
						.setFilename(dealReq.getDealQuotation().getQuoteNo().replaceAll("/", "-") + ".pdf");
				final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename();
				System.out.println(filePath);

//		 Export the report to a PDF file.
				JasperExportManager.exportReportToPdfFile(print, filePath);

				// Merge Preamble if any
				System.out.println(dealReq.getPreamble());
				if (dealReq.getPreamble() != null && !dealReq.getPreamble().isEmpty()) {
					List<String> filePaths = new ArrayList<>();
					if (dealReq.getPreamblePosition() != null) {
						if (dealReq.getPreamblePosition().equalsIgnoreCase("Quotation First")) {
							filePaths.add(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
							filePaths.add(contentPath + "/_preamble_documents/" + dealReq.getPreamble());
						} else {
							filePaths.add(contentPath + "/_preamble_documents/" + dealReq.getPreamble());
							filePaths.add(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
						}
					} else {
						filePaths.add(contentPath + "/_preamble_documents/" + dealReq.getPreamble());
						filePaths.add(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
					}
					System.out.println(filePaths);
					System.out.println(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
					Util.mergePDF(filePaths,
							directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
				}
			}

			quoteRepo.save(dealReq.getDealQuotation());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealQuotation", dealReq.getDealQuotation());
		resp.put("Deal", dealReq.getDeal());
		return resp;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> generateQuotationPDFTemplate3(DealRequest dealReq) {

		Map<String, Object> resp = new HashMap<>();

		try {

			dealReq.setDealQuotation(quoteRepo.findById(dealReq.getDealQuotation().getId()));

			dealReq.setDeal(dealRepo.findById(dealReq.getDealQuotation().getDealId()));

			dealReq.setDealProducts(dealProductsRepo.findByDealId(dealReq.getDealQuotation().getDealId()));

			System.out.println(dealReq.getDealQuotation().getQuoteNo());
			System.out.println(dealReq.getDealQuotation());

			InputStream stream = null;

			if (dealReq.getDeal().getGstType().equals("IGST")) {
				stream = this.getClass()
						.getResourceAsStream("/reports/FinalTemplates/Template_3/Template_3_IGST.jrxml");
			} else {
				stream = this.getClass()
						.getResourceAsStream("/reports/FinalTemplates/Template_3/Template_3_CGST_SGST.jrxml");
			}

			final Map<String, Object> parameters = new HashMap<>();
			InfoDetails info = infoDetailRepo.findById(1);

			Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

			System.out.println(info.toString());

			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddressHTML1());
			parameters.put("cmp_logo_url", info.getLogoAsFile());

			parameters.put("is_discount_applied", dealReq.isDiscountApplied());
			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", dealReq.getDesignation());
			parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
			parameters.put("online_payment_url", info.getInstamojoPaymentURL());

			parameters.put("terms", dealReq.getDealQuotation().getTerms().replaceAll("\n", "<br>"));
//			parameters.put("vailid_until", Util.sdfFormatter(dealReq.getDealQuotation().getValidUntil()));
//			parameters.put("quote_date", Util.sdfFormatter(dealReq.getDealQuotation().getQuoteDate()));
//			parameters.put("quote_no", String.valueOf(dealReq.getDealQuotation().getQuoteNo()));

			if (dealReq.getDeal().getDealType().equalsIgnoreCase("AMC") && dealReq.getDeal().getAmcFromDate() != null
					&& dealReq.getDeal().getAmcToDate() != null) {
				String subject = "Sub: " + dealReq.getDealQuotation().getSubject()
						+ " <br> Charges for the following items for the period of "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcFromDate(), "dd/MM/yyyy") + " to "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcToDate(), "dd/MM/yyyy");
				parameters.put("subject", subject);
			} else {
				parameters.put("subject", "Sub: " + dealReq.getDealQuotation().getSubject());
			}
			parameters.put("dealtype_label", dealReq.getDeal().getDealType().toUpperCase() + " QUOTATION");

			parameters.put("billing_to", dealReq.getBillingToAddress());
			parameters.put("shipping_to", dealReq.getShippingToAddress());

			String price_summary_label = "", price_summary_text = "";
			String deal_date_label = "", deal_date_text = "";

			deal_date_label = deal_date_label + "Quote Number : <br>";
			deal_date_text = deal_date_text + String.valueOf(dealReq.getDealQuotation().getQuoteNo()) + "<br>";
			deal_date_label = deal_date_label + "Quote Date : <br>";
			deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealQuotation().getQuoteDate()) + "<br>";
			deal_date_label = deal_date_label + "Valid Until : <br>";
			deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealQuotation().getValidUntil()) + "<br>";

			parameters.put("deal_date_label", deal_date_label);
			parameters.put("deal_date_text", deal_date_text);

			price_summary_label = price_summary_label + "Original Rate : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getSubTotal())
					+ "<br>";

			if (dealReq.getDeal().getDiscount() > 0) {
				price_summary_label = price_summary_label + "Discount : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getDiscount())
						+ "<br>";
			}

			price_summary_label = price_summary_label + "Taxable Amount : <br>";
			price_summary_text = price_summary_text + "Rs."
					+ Util.decimalFormatter(dealReq.getDeal().getSubTotal() - dealReq.getDeal().getDiscount()) + "<br>";

			if (dealReq.getDeal().getGstType().equalsIgnoreCase("IGST")) {
				price_summary_label = price_summary_label + "IGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax())

						+ "<br>";
				parameters.put("Igst_amount", Util.decimalFormatter(dealReq.getDeal().getTax()));
			} else {
				price_summary_label = price_summary_label + "CGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax() / 2)
						+ "<br>";

				price_summary_label = price_summary_label + "SGST : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter((dealReq.getDeal().getTax() / 2)) + "<br>";
				parameters.put("cgst_amount", Util.decimalFormatter(dealReq.getDeal().getTax() / 2));
				parameters.put("sgst_amount", Util.decimalFormatter(dealReq.getDeal().getTax() / 2));
			}

			if (dealReq.getDeal().getAdjustment() != 0.00) {
				price_summary_label = price_summary_label + "Adjustment : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ String.format("%.2f", dealReq.getDeal().getAdjustment()) + "<br>";
			}

			parameters.put("adjust", Util.decimalFormatter(dealReq.getDeal().getTax() / 2));

			parameters.put("price_summary_label", price_summary_label);
			parameters.put("price_summary_text", price_summary_text);
			parameters.put("deal_amount_label", "Grand Total :");
			parameters.put("deal_amount_text", "Rs." + Util.decimalFormatter(dealReq.getDeal().getGrandTotal()));
			parameters.put("amount_in_words", Util.EnglishNumberToWords(dealReq.getDeal().getGrandTotal()));

			parameters.put("taxable_amount",
					Util.decimalFormatter(dealReq.getDeal().getSubTotal() - dealReq.getDeal().getDiscount()));

			System.out.println(parameters.toString());

			List<Map<String, String>> datasource = new ArrayList<>();

			List<Integer> partIds = dealReq.getDealProducts().stream().mapToInt(DealProducts::getPartId).distinct()
					.boxed().sorted().collect(Collectors.toList());

			int sno = 1;
			for (int partId : partIds) {

				for (DealProducts prod : dealReq.getDealProducts()) {
					if (prod.getPartId() == partId) {

						Map<String, String> data = new HashMap<>();

						data.put("sno", sno + ".");
						data.put("type", "product");
						data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
						data.put("quantity", prod.getQuantityAsHTMLText());
						data.put("price", Util.decimalFormatter(prod.getRateAmount()));
						data.put("rate", Util.decimalFormatter(prod.getPrice()));
						data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
						if (dealReq.getDeal().getGstType().equals("IGST")) {
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

					Double totalAmount = dealReq.getDealProducts().stream().filter(prod -> prod.getPartId() == partId)
							.mapToDouble(DealProducts::getTotalAmount).sum();

					data.put("sno", "");
					data.put("type", "subtotal");
					data.put("name_description", "");
					data.put("quantity", "");
					data.put("price", "");
					data.put("rate", "");
					data.put("total", Util.decimalFormatter(totalAmount));
					if (dealReq.getDeal().getGstType().equals("IGST")) {
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

//			dealReq.getDealProducts().forEach(prod -> {
//				Map<String, String> data = new HashMap<>();
//
//				data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
//				data.put("quantity", prod.getQuantityAsHTMLText());
//				data.put("price", Util.decimalFormatter(prod.getRateAmount()));
//				data.put("rate", Util.decimalFormatter(prod.getPrice()));
//				data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
//				if (dealReq.getDeal().getGstType().equals("IGST")) {
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

			File directory = new File(contentPath + "/Deals/" + dealReq.getDeal().getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}

			if (dealReq.getExportType() != null && dealReq.getExportType().equalsIgnoreCase("rtf")) {

				dealReq.getDealQuotation()
						.setFilename(dealReq.getDealQuotation().getQuoteNo().replaceAll("/", "-") + ".rtf");
				final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename();
				System.out.println(filePath);

//				 Export the report to a RTF file.
				JRRtfExporter exporter = new JRRtfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
				exporter.exportReport();

			} else {
				dealReq.getDealQuotation()
						.setFilename(dealReq.getDealQuotation().getQuoteNo().replaceAll("/", "-") + ".pdf");
				final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename();
				System.out.println(filePath);

//		 Export the report to a PDF file.
				JasperExportManager.exportReportToPdfFile(print, filePath);

				// Merge Preamble if any
				System.out.println(dealReq.getPreamble());
				if (dealReq.getPreamble() != null && !dealReq.getPreamble().isEmpty()) {
					List<String> filePaths = new ArrayList<>();
					if (dealReq.getPreamblePosition() != null) {
						if (dealReq.getPreamblePosition().equalsIgnoreCase("Quotation First")) {
							filePaths.add(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
							filePaths.add(contentPath + "/_preamble_documents/" + dealReq.getPreamble());
						} else {
							filePaths.add(contentPath + "/_preamble_documents/" + dealReq.getPreamble());
							filePaths.add(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
						}
					} else {
						filePaths.add(contentPath + "/_preamble_documents/" + dealReq.getPreamble());
						filePaths.add(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
					}
					System.out.println(filePaths);
					System.out.println(directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
					Util.mergePDF(filePaths,
							directory.getAbsolutePath() + "/" + dealReq.getDealQuotation().getFilename());
				}
			}

			quoteRepo.save(dealReq.getDealQuotation());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealQuotation", dealReq.getDealQuotation());
		resp.put("Deal", dealReq.getDeal());
		return resp;

	}

	@Override
	public Map<String, Object> UploadGeneratedQuotationPDF(int dealQuoteId, MultipartFile file) {
		Map<String, Object> resp = new HashMap<>();

		DealQuotation dealQuote = quoteRepo.findById(dealQuoteId);

		File directory = new File(contentPath + "/Deals/" + dealQuote.getDealId());
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

			dealQuote.setFilename(file.getOriginalFilename());
			dealQuote = quoteRepo.save(dealQuote);

			resp.putAll(Util.SuccessResponse());
		} catch (IOException e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealQuotation", dealQuote);
		return resp;
	}

	@Override
	public Map<String, Object> saveDealProformaInvoice(DealProformaInvoice dealProInvReq) {
		Map<String, Object> resp = new HashMap<>();
		try {
			DealProformaInvoice dpitemp = proInvRepo.findByProformaInvoiceNo(dealProInvReq.getProformaInvoiceNo());
			if (dealProInvReq.getId() == 0 && dpitemp != null) {
				resp.putAll(Util.invalidMessage("Proforma Invoice No Already Exist"));
			} else if (dpitemp != null && dealProInvReq.getId() > 0 && dpitemp != null
					&& dealProInvReq.getId() != dpitemp.getId()) {
				resp.putAll(Util.invalidMessage("Proforma Invoice No Already Exist with another Deal"));
			} else {

				dealProInvReq = proInvRepo.save(dealProInvReq);

				Query query = em
						.createQuery("UPDATE Deal d SET d.proformaInvoiceNo = :proformaInvoiceNo WHERE d.id = :id");
				query.setParameter("proformaInvoiceNo", dealProInvReq.getProformaInvoiceNo());
				query.setParameter("id", dealProInvReq.getDealId());
				query.executeUpdate();

				resp.putAll(Util.SuccessResponse());
			}

		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		resp.put("DealProformaInvoice", dealProInvReq);
		return resp;
	}

	@Override
	public Map<String, Object> getDealProformaInvoice(int dealId) {
		Map<String, Object> resp = new HashMap<>();
		DealProformaInvoice proInv = new DealProformaInvoice();
		Deal deal = new Deal();
		String autoGeneratedNo = "PI/001";
		String lastQuoteTerms = "";
		try {
			proInv = proInvRepo.findByDealId(dealId);

			deal = dealRepo.findById(dealId);

			if (proInv == null) {

				// if null AutoIncrement the ProformaInvoiceNo from last Quotation.

				DealProformaInvoice dealProformaInvoiceTemp = proInvRepo.findLastProformaInvoiceByType(dealId);

				if (dealProformaInvoiceTemp != null) {
					autoGeneratedNo = Util.autoIncrementNo(dealProformaInvoiceTemp.getProformaInvoiceNo());
					lastQuoteTerms = dealProformaInvoiceTemp.getTerms();

				}
			}

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealProformaInvoice", proInv);
		resp.put("Deal", deal);
		resp.put("AutoGeneratedInvoiceNo", autoGeneratedNo);
		resp.put("LastTerms", lastQuoteTerms);

		return resp;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> generateProformaInvoicePDFTemplate1Lite(DealRequest dealReq) {
		Map<String, Object> resp = new HashMap<>();

		try {

			dealReq.setDealProformaInvoice(proInvRepo.findById(dealReq.getDealProformaInvoice().getId()));

			dealReq.setDeal(dealRepo.findById(dealReq.getDealProformaInvoice().getDealId()));

			dealReq.setDealProducts(dealProductsRepo.findByDealId(dealReq.getDealProformaInvoice().getDealId()));

			System.out.println(dealReq.toString());

			InputStream stream = this.getClass()
					.getResourceAsStream("/reports/FinalTemplates/Template_1/Template_1_Lite.jrxml");

			final Map<String, Object> parameters = new HashMap<>();

			InfoDetails info = infoDetailRepo.findById(1);
			Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddressHTML1());
			parameters.put("cmp_logo_url", info.getLogoAsFile());
//			parameters.put("invoice_label", "PROFORMA INVOICE");

			parameters.put("dealtype_label", "PROFORMA INVOICE");

			parameters.put("is_discount_applied", dealReq.isDiscountApplied());
			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", dealReq.getDesignation());
			parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
			parameters.put("online_payment_url", info.getInstamojoPaymentURL());

			parameters.put("terms", dealReq.getDealProformaInvoice().getTerms().replaceAll("\n", "<br>"));

			String deal_date_label = "", deal_date_text = "";

			deal_date_label = deal_date_label + "PI Number : <br>";
			deal_date_text = deal_date_text + String.valueOf(dealReq.getDealProformaInvoice().getProformaInvoiceNo())
					+ "<br>";
			deal_date_label = deal_date_label + "PI Date : <br>";
			deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealProformaInvoice().getInvoiceDate())
					+ "<br>";
			deal_date_label = deal_date_label + "Due Date : <br>";
			deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealProformaInvoice().getDueDate()) + "<br>";

			if (dealReq.getDealProformaInvoice().getPurchaseOrderNo() != null
					&& dealReq.getDealProformaInvoice().getPurchaseOrderNo() != "") {
				deal_date_label = deal_date_label + "PO Number : <br>";
				deal_date_text = deal_date_text + String.valueOf(dealReq.getDealProformaInvoice().getPurchaseOrderNo())
						+ "<br>";
			}
			if (dealReq.getDealProformaInvoice().getSalesOrderNo() != null
					&& dealReq.getDealProformaInvoice().getSalesOrderNo() != "") {
				deal_date_label = deal_date_label + "Sales Order No : <br>";
				deal_date_text = deal_date_text + String.valueOf(dealReq.getDealProformaInvoice().getSalesOrderNo())
						+ "<br>";
			}

			parameters.put("deal_date_label", deal_date_label);
			parameters.put("deal_date_text", deal_date_text);

			parameters.put("grand_total_text", "Grand Total : ");
			parameters.put("grand_total",
					"Rs." + Util.decimalFormatter(
							(dealReq.getDeal().getGrandTotal() - dealReq.getDealProformaInvoice().getPaidAmount())
									+ dealReq.getDealProformaInvoice().getShippingCost()));
			parameters.put("amount_in_words",
					Util.EnglishNumberToWords(
							(dealReq.getDeal().getGrandTotal() - dealReq.getDealProformaInvoice().getPaidAmount())
									+ dealReq.getDealProformaInvoice().getShippingCost()));

			if (dealReq.getDeal().getDealType().equalsIgnoreCase("AMC") && dealReq.getDeal().getAmcFromDate() != null
					&& dealReq.getDeal().getAmcToDate() != null) {
				String subject = "Sub: " + dealReq.getDealProformaInvoice().getSubject()
						+ " <br>AMC Charges for the following items for the period of "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcFromDate(), "dd/MM/yyyy") + " to "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcToDate(), "dd/MM/yyyy");
				parameters.put("subject", subject);
			} else {
				parameters.put("subject", "Sub: " + dealReq.getDealProformaInvoice().getSubject());
			}

			parameters.put("billing_to", dealReq.getBillingToAddress());
			parameters.put("shipping_to", dealReq.getShippingToAddress());

			String price_summary_label = "", price_summary_text = "";

			price_summary_label = price_summary_label + "Original Rate : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getSubTotal())
					+ "<br>";

			if (dealReq.getDeal().getDiscount() > 0) {
				price_summary_label = price_summary_label + "Discount : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getDiscount())
						+ "<br>";
			}

			price_summary_label = price_summary_label + "Taxable Amount : <br>";
			price_summary_text = price_summary_text + "Rs."
					+ Util.decimalFormatter(dealReq.getDeal().getSubTotal() - dealReq.getDeal().getDiscount()) + "<br>";

			if (dealReq.getDeal().getGstType().equalsIgnoreCase("IGST")) {
				price_summary_label = price_summary_label + "IGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax())
						+ "<br>";
			} else {
				price_summary_label = price_summary_label + "CGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax() / 2)
						+ "<br>";

				price_summary_label = price_summary_label + "SGST : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter((dealReq.getDeal().getTax() / 2)) + "<br>";
			}

			if (dealReq.getDeal().getAdjustment() != 0.00) {
				price_summary_label = price_summary_label + "Adjustment : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ String.format("%.2f", dealReq.getDeal().getAdjustment()) + "<br>";
			}

			price_summary_label = price_summary_label + "Grand Total : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getGrandTotal())
					+ "<br>";

			if (dealReq.getDealProformaInvoice().getPaidAmount() > 0) {
				price_summary_label = price_summary_label + "Paid Amount : <br>";
				price_summary_text = price_summary_text + ""
						+ Util.decimalFormatter(dealReq.getDealProformaInvoice().getPaidAmount()) + "<br>";
			}

			if (dealReq.getDealProformaInvoice().getShippingCost() > 0) {
				price_summary_label = price_summary_label + "Shipment Amount : <br>";
				price_summary_text = price_summary_text + ""
						+ Util.decimalFormatter(dealReq.getDealProformaInvoice().getShippingCost()) + "<br>";
			}

			parameters.put("price_summary_label", price_summary_label);
			parameters.put("price_summary_text", price_summary_text);

			System.out.println(parameters.toString());

			List<Map<String, String>> datasource = new ArrayList<>();

			List<Integer> partIds = dealReq.getDealProducts().stream().mapToInt(DealProducts::getPartId).distinct()
					.boxed().sorted().collect(Collectors.toList());

			int sno = 1;
			for (int partId : partIds) {
				Map<String, String> data = new HashMap<>();

				List<DealProducts> dp = dealReq.getDealProducts().stream().filter(_dp -> _dp.getPartId() == partId)
						.collect(Collectors.toList());

				data.put("sno", sno + ".");
				data.put("total",
						Util.decimalFormatter(dp.stream().map(prod -> (double) prod.getPrice() * prod.getQuantity())
								.mapToDouble(amount -> amount).sum()));

				int _part_sno = 1;
				String name_description = "";
				for (DealProducts prod : dp) {
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

			File directory = new File(contentPath + "/Deals/" + dealReq.getDeal().getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}

			if (dealReq.getExportType() != null && dealReq.getExportType().equalsIgnoreCase("rtf")) {

				dealReq.getDealProformaInvoice().setFilename(
						dealReq.getDealProformaInvoice().getProformaInvoiceNo().replaceAll("/", "-") + ".rtf");
				final String filePath = directory.getAbsolutePath() + "/"
						+ dealReq.getDealProformaInvoice().getFilename();
				System.out.println(filePath);

//				 Export the report to a RTF file.
				JRRtfExporter exporter = new JRRtfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
				exporter.exportReport();

			} else {

				dealReq.getDealProformaInvoice().setFilename(
						dealReq.getDealProformaInvoice().getProformaInvoiceNo().replaceAll("/", "-") + ".pdf");
				final String filePath = directory.getAbsolutePath() + "/"
						+ dealReq.getDealProformaInvoice().getFilename();
				System.out.println(filePath);

				// Export the report to a PDF file.
				JasperExportManager.exportReportToPdfFile(print, filePath);

			}

			proInvRepo.save(dealReq.getDealProformaInvoice());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealProformaInvoice", dealReq.getDealProformaInvoice());
		return resp;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> generateProformaInvoicePDFTemplate2Lite(DealRequest dealReq) {
		Map<String, Object> resp = new HashMap<>();

		try {

			dealReq.setDealProformaInvoice(proInvRepo.findById(dealReq.getDealProformaInvoice().getId()));

			dealReq.setDeal(dealRepo.findById(dealReq.getDealProformaInvoice().getDealId()));

			dealReq.setDealProducts(dealProductsRepo.findByDealId(dealReq.getDealProformaInvoice().getDealId()));

			System.out.println(dealReq.toString());

			InputStream stream = null;

			if (dealReq.getDeal().getGstType().equals("IGST")) {
				stream = this.getClass()
						.getResourceAsStream("/reports/ProformaInvoice/Proforma_Invoice_Template_2_IGST.jrxml");
			} else {
				stream = this.getClass()
						.getResourceAsStream("/reports/ProformaInvoice/Proforma_Invoice_Template_2_CGST_SGST.jrxml");
			}

			final Map<String, Object> parameters = new HashMap<>();
			InfoDetails info = infoDetailRepo.findById(1);
			Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddressHTML2());
			parameters.put("cmp_logo_url", info.getLogoAsFile());
			parameters.put("invoice_label", "PROFORMA INVOICE");

			parameters.put("is_discount_applied", dealReq.isDiscountApplied());
			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", dealReq.getDesignation());
			parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
			parameters.put("online_payment_url", info.getInstamojoPaymentURL());

			parameters.put("terms", dealReq.getDealProformaInvoice().getTerms().replaceAll("\n", "<br>"));
			parameters.put("invoice_date", Util.sdfFormatter(dealReq.getDealProformaInvoice().getInvoiceDate()));
			parameters.put("due_date", Util.sdfFormatter(dealReq.getDealProformaInvoice().getDueDate()));
			parameters.put("invoice_no", String.valueOf(dealReq.getDealProformaInvoice().getProformaInvoiceNo()));
			parameters.put("sales_order_no", String.valueOf(dealReq.getDealProformaInvoice().getSalesOrderNo()));
			parameters.put("purchase_order_no", String.valueOf(dealReq.getDealProformaInvoice().getPurchaseOrderNo()));

			parameters.put("balance_amount",
					"Rs." + Util.decimalFormatter(
							(dealReq.getDeal().getGrandTotal() - dealReq.getDealProformaInvoice().getPaidAmount())
									+ dealReq.getDealProformaInvoice().getShippingCost()));
			parameters.put("amount_in_words",
					Util.EnglishNumberToWords(
							(dealReq.getDeal().getGrandTotal() - dealReq.getDealProformaInvoice().getPaidAmount())
									+ dealReq.getDealProformaInvoice().getShippingCost()));

			if (dealReq.getDeal().getDealType().equalsIgnoreCase("AMC") && dealReq.getDeal().getAmcFromDate() != null
					&& dealReq.getDeal().getAmcToDate() != null) {
				String subject = "Sub: " + dealReq.getDealProformaInvoice().getSubject()
						+ " <br>AMC Charges for the following items for the period of "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcFromDate(), "dd/MM/yyyy") + " to "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcToDate(), "dd/MM/yyyy");
				parameters.put("subject", subject);
			} else {
				parameters.put("subject", "Sub: " + dealReq.getDealProformaInvoice().getSubject());
			}

			parameters.put("billing_to", dealReq.getBillingToAddress());
			parameters.put("shipping_to", dealReq.getShippingToAddress());

			String price_summary_label = "", price_summary_text = "";

			price_summary_label = price_summary_label + "Original Rate : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getSubTotal())
					+ "<br>";

			if (dealReq.getDeal().getDiscount() > 0) {
				price_summary_label = price_summary_label + "Discount : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getDiscount())
						+ "<br>";
			}

			price_summary_label = price_summary_label + "Taxable Amount : <br>";
			price_summary_text = price_summary_text + "Rs."
					+ Util.decimalFormatter(dealReq.getDeal().getSubTotal() - dealReq.getDeal().getDiscount()) + "<br>";

			if (dealReq.getDeal().getGstType().equalsIgnoreCase("IGST")) {
				price_summary_label = price_summary_label + "IGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax())
						+ "<br>";
			} else {
				price_summary_label = price_summary_label + "CGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax() / 2)
						+ "<br>";

				price_summary_label = price_summary_label + "SGST : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter((dealReq.getDeal().getTax() / 2)) + "<br>";
			}

			if (dealReq.getDeal().getAdjustment() != 0.00) {
				price_summary_label = price_summary_label + "Adjustment : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ String.format("%.2f", dealReq.getDeal().getAdjustment()) + "<br>";
			}

			price_summary_label = price_summary_label + "Grand Total : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getGrandTotal())
					+ "<br>";

			price_summary_label = price_summary_label + "Paid Amount : <br>";
			price_summary_text = price_summary_text + ""
					+ Util.decimalFormatter(dealReq.getDealProformaInvoice().getPaidAmount()) + "<br>";

			if (dealReq.getDealProformaInvoice().getShippingCost() > 0) {
				price_summary_label = price_summary_label + "Shipment Amount : <br>";
				price_summary_text = price_summary_text + ""
						+ Util.decimalFormatter(dealReq.getDealProformaInvoice().getShippingCost()) + "<br>";
			}

			parameters.put("price_summary_label", price_summary_label);
			parameters.put("price_summary_text", price_summary_text);

			System.out.println(parameters.toString());

			List<Map<String, String>> datasource = new ArrayList<>();

			dealReq.getDealProducts().forEach(prod -> {
				Map<String, String> data = new HashMap<>();

				data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
				data.put("quantity", prod.getQuantityAsHTMLText());
				data.put("price", Util.decimalFormatter(prod.getRateAmount()));
				data.put("rate", Util.decimalFormatter(prod.getPrice()));
				data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
				if (dealReq.getDeal().getGstType().equals("IGST")) {
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

			File directory = new File(contentPath + "/Deals/" + dealReq.getDeal().getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}

			if (dealReq.getExportType() != null && dealReq.getExportType().equalsIgnoreCase("rtf")) {

				dealReq.getDealProformaInvoice().setFilename(
						dealReq.getDealProformaInvoice().getProformaInvoiceNo().replaceAll("/", "-") + ".rtf");
				final String filePath = directory.getAbsolutePath() + "/"
						+ dealReq.getDealProformaInvoice().getFilename();
				System.out.println(filePath);

//				 Export the report to a RTF file.
				JRRtfExporter exporter = new JRRtfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
				exporter.exportReport();

			} else {

				dealReq.getDealProformaInvoice().setFilename(
						dealReq.getDealProformaInvoice().getProformaInvoiceNo().replaceAll("/", "-") + ".pdf");
				final String filePath = directory.getAbsolutePath() + "/"
						+ dealReq.getDealProformaInvoice().getFilename();
				System.out.println(filePath);

				// Export the report to a PDF file.
				JasperExportManager.exportReportToPdfFile(print, filePath);

			}

			proInvRepo.save(dealReq.getDealProformaInvoice());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealProformaInvoice", dealReq.getDealProformaInvoice());
		return resp;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> generateProformaInvoicePDFTemplate3Lite(DealRequest dealReq) {

		Map<String, Object> resp = new HashMap<>();

		try {

			dealReq.setDealProformaInvoice(proInvRepo.findById(dealReq.getDealProformaInvoice().getId()));

			dealReq.setDeal(dealRepo.findById(dealReq.getDealProformaInvoice().getDealId()));

			dealReq.setDealProducts(dealProductsRepo.findByDealId(dealReq.getDealProformaInvoice().getDealId()));

			System.out.println(dealReq.toString());

			InputStream stream = this.getClass()
					.getResourceAsStream("/reports/FinalTemplates/Template_3/Template_3_Lite.jrxml");

			final Map<String, Object> parameters = new HashMap<>();

			InfoDetails info = infoDetailRepo.findById(1);
			Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddressHTML1());
			parameters.put("cmp_logo_url", info.getLogoAsFile());
			parameters.put("dealtype_label", "PROFORMA INVOICE");

			parameters.put("is_discount_applied", dealReq.isDiscountApplied());
			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", dealReq.getDesignation());
			parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
			parameters.put("online_payment_url", info.getInstamojoPaymentURL());

			parameters.put("terms", dealReq.getDealProformaInvoice().getTerms().replaceAll("\n", "<br>"));
			parameters.put("deal_amount_label", "Balance Amount :");
			parameters.put("deal_amount_text",
					"Rs." + Util.decimalFormatter(
							(dealReq.getDeal().getGrandTotal() - dealReq.getDealProformaInvoice().getPaidAmount())
									+ dealReq.getDealProformaInvoice().getShippingCost()));
			parameters.put("amount_in_words",
					Util.EnglishNumberToWords(
							(dealReq.getDeal().getGrandTotal() - dealReq.getDealProformaInvoice().getPaidAmount())
									+ dealReq.getDealProformaInvoice().getShippingCost()));

			if (dealReq.getDeal().getDealType().equalsIgnoreCase("AMC") && dealReq.getDeal().getAmcFromDate() != null
					&& dealReq.getDeal().getAmcToDate() != null) {
				String subject = "Sub: " + dealReq.getDealProformaInvoice().getSubject()
						+ " <br>AMC Charges for the following items for the period of "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcFromDate(), "dd/MM/yyyy") + " to "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcToDate(), "dd/MM/yyyy");
				parameters.put("subject", subject);
			} else {
				parameters.put("subject", "Sub: " + dealReq.getDealProformaInvoice().getSubject());
			}

			parameters.put("billing_to", dealReq.getBillingToAddress());
			parameters.put("shipping_to", dealReq.getShippingToAddress());

			String price_summary_label = "", price_summary_text = "";
			String deal_date_label = "", deal_date_text = "";

			deal_date_label = deal_date_label + "PI Number : <br>";
			deal_date_text = deal_date_text + String.valueOf(dealReq.getDealProformaInvoice().getProformaInvoiceNo())
					+ "<br>";
			deal_date_label = deal_date_label + "PI Date : <br>";
			deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealProformaInvoice().getInvoiceDate())
					+ "<br>";
			deal_date_label = deal_date_label + "Due Date : <br>";
			deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealProformaInvoice().getDueDate()) + "<br>";

			if (dealReq.getDealProformaInvoice().getPurchaseOrderNo() != null
					&& dealReq.getDealProformaInvoice().getPurchaseOrderNo() != "") {
				deal_date_label = deal_date_label + "PO Number : <br>";
				deal_date_text = deal_date_text + String.valueOf(dealReq.getDealProformaInvoice().getPurchaseOrderNo())
						+ "<br>";
			}
			if (dealReq.getDealProformaInvoice().getSalesOrderNo() != null
					&& dealReq.getDealProformaInvoice().getSalesOrderNo() != "") {
				deal_date_label = deal_date_label + "Sales Order No : <br>";
				deal_date_text = deal_date_text + String.valueOf(dealReq.getDealProformaInvoice().getSalesOrderNo())
						+ "<br>";
			}

			price_summary_label = price_summary_label + "Original Rate : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getSubTotal())
					+ "<br>";

			if (dealReq.getDeal().getDiscount() > 0) {
				price_summary_label = price_summary_label + "Discount : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getDiscount())
						+ "<br>";
			}

			price_summary_label = price_summary_label + "Taxable Amount : <br>";
			price_summary_text = price_summary_text + "Rs."
					+ Util.decimalFormatter(dealReq.getDeal().getSubTotal() - dealReq.getDeal().getDiscount()) + "<br>";

			if (dealReq.getDeal().getGstType().equalsIgnoreCase("IGST")) {
				price_summary_label = price_summary_label + "IGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax())
						+ "<br>";
			} else {
				price_summary_label = price_summary_label + "CGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax() / 2)
						+ "<br>";

				price_summary_label = price_summary_label + "SGST : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter((dealReq.getDeal().getTax() / 2)) + "<br>";
			}

			if (dealReq.getDeal().getAdjustment() != 0.00) {
				price_summary_label = price_summary_label + "Adjustment : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ String.format("%.2f", dealReq.getDeal().getAdjustment()) + "<br>";
			}

			price_summary_label = price_summary_label + "Grand Total : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getGrandTotal())
					+ "<br>";

			if (dealReq.getDealProformaInvoice().getPaidAmount() > 0) {
				price_summary_label = price_summary_label + "Paid Amount : <br>";
				price_summary_text = price_summary_text + ""
						+ Util.decimalFormatter(dealReq.getDealProformaInvoice().getPaidAmount()) + "<br>";
			}

			if (dealReq.getDealProformaInvoice().getShippingCost() > 0) {
				price_summary_label = price_summary_label + "Shipment Amount : <br>";
				price_summary_text = price_summary_text + ""
						+ Util.decimalFormatter(dealReq.getDealProformaInvoice().getShippingCost()) + "<br>";
			}

			parameters.put("price_summary_label", price_summary_label);
			parameters.put("price_summary_text", price_summary_text);

			parameters.put("deal_date_label", deal_date_label);
			parameters.put("deal_date_text", deal_date_text);

			System.out.println(parameters.toString());

			List<Map<String, String>> datasource = new ArrayList<>();

			List<Integer> partIds = dealReq.getDealProducts().stream().mapToInt(DealProducts::getPartId).distinct()
					.boxed().sorted().collect(Collectors.toList());

			int sno = 1;
			for (int partId : partIds) {
				Map<String, String> data = new HashMap<>();

				List<DealProducts> dp = dealReq.getDealProducts().stream().filter(_dp -> _dp.getPartId() == partId)
						.collect(Collectors.toList());

				data.put("sno", sno + ".");
				data.put("total",
						Util.decimalFormatter(dp.stream().map(prod -> (double) prod.getPrice() * prod.getQuantity())
								.mapToDouble(amount -> amount).sum()));

				int _part_sno = 1;
				String name_description = "";
				for (DealProducts prod : dp) {
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

			File directory = new File(contentPath + "/Deals/" + dealReq.getDeal().getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}
			if (dealReq.getExportType() != null && dealReq.getExportType().equalsIgnoreCase("rtf")) {

				dealReq.getDealProformaInvoice().setFilename(
						dealReq.getDealProformaInvoice().getProformaInvoiceNo().replaceAll("/", "-") + ".rtf");
				final String filePath = directory.getAbsolutePath() + "/"
						+ dealReq.getDealProformaInvoice().getFilename();
				System.out.println(filePath);

//				 Export the report to a RTF file.
				JRRtfExporter exporter = new JRRtfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
				exporter.exportReport();

			} else {

				dealReq.getDealProformaInvoice().setFilename(
						dealReq.getDealProformaInvoice().getProformaInvoiceNo().replaceAll("/", "-") + ".pdf");
				final String filePath = directory.getAbsolutePath() + "/"
						+ dealReq.getDealProformaInvoice().getFilename();
				System.out.println(filePath);

				// Export the report to a PDF file.
				JasperExportManager.exportReportToPdfFile(print, filePath);

			}

			proInvRepo.save(dealReq.getDealProformaInvoice());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealProformaInvoice", dealReq.getDealProformaInvoice());
		return resp;

	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> generateProformaInvoicePDFTemplate1(DealRequest dealReq) {
		Map<String, Object> resp = new HashMap<>();

		try {

			dealReq.setDealProformaInvoice(proInvRepo.findById(dealReq.getDealProformaInvoice().getId()));

			dealReq.setDeal(dealRepo.findById(dealReq.getDealProformaInvoice().getDealId()));

			dealReq.setDealProducts(dealProductsRepo.findByDealId(dealReq.getDealProformaInvoice().getDealId()));

			System.out.println(dealReq.toString());

			InputStream stream = null;

			if (dealReq.getDeal().getGstType().equals("IGST")) {
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
//			parameters.put("invoice_label", "PROFORMA INVOICE");

			parameters.put("dealtype_label", "PROFORMA INVOICE");

			parameters.put("is_discount_applied", dealReq.isDiscountApplied());
			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", dealReq.getDesignation());
			parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
			parameters.put("online_payment_url", info.getInstamojoPaymentURL());

			parameters.put("terms", dealReq.getDealProformaInvoice().getTerms().replaceAll("\n", "<br>"));

//			parameters.put("invoice_date", Util.sdfFormatter(dealReq.getDealProformaInvoice().getInvoiceDate()));
//			parameters.put("due_date", Util.sdfFormatter(dealReq.getDealProformaInvoice().getDueDate()));
//			parameters.put("invoice_no", String.valueOf(dealReq.getDealProformaInvoice().getProformaInvoiceNo()));
//			parameters.put("sales_order_no", String.valueOf(dealReq.getDealProformaInvoice().getSalesOrderNo()));
//			parameters.put("purchase_order_no", String.valueOf(dealReq.getDealProformaInvoice().getPurchaseOrderNo()));

			String deal_date_label = "", deal_date_text = "";

			deal_date_label = deal_date_label + "PI Number : <br>";
			deal_date_text = deal_date_text + String.valueOf(dealReq.getDealProformaInvoice().getProformaInvoiceNo())
					+ "<br>";
			deal_date_label = deal_date_label + "PI Date : <br>";
			deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealProformaInvoice().getInvoiceDate())
					+ "<br>";
			deal_date_label = deal_date_label + "Due Date : <br>";
			deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealProformaInvoice().getDueDate()) + "<br>";

			if (dealReq.getDealProformaInvoice().getPurchaseOrderNo() != null
					&& dealReq.getDealProformaInvoice().getPurchaseOrderNo() != "") {
				deal_date_label = deal_date_label + "PO Number : <br>";
				deal_date_text = deal_date_text + String.valueOf(dealReq.getDealProformaInvoice().getPurchaseOrderNo())
						+ "<br>";
			}
			if (dealReq.getDealProformaInvoice().getSalesOrderNo() != null
					&& dealReq.getDealProformaInvoice().getSalesOrderNo() != "") {
				deal_date_label = deal_date_label + "Sales Order No : <br>";
				deal_date_text = deal_date_text + String.valueOf(dealReq.getDealProformaInvoice().getSalesOrderNo())
						+ "<br>";
			}

			parameters.put("deal_date_label", deal_date_label);
			parameters.put("deal_date_text", deal_date_text);

			parameters.put("grand_total_text", "Grand Total : ");
			parameters.put("grand_total",
					"Rs." + Util.decimalFormatter(
							(dealReq.getDeal().getGrandTotal() - dealReq.getDealProformaInvoice().getPaidAmount())
									+ dealReq.getDealProformaInvoice().getShippingCost()));
			parameters.put("amount_in_words",
					Util.EnglishNumberToWords(
							(dealReq.getDeal().getGrandTotal() - dealReq.getDealProformaInvoice().getPaidAmount())
									+ dealReq.getDealProformaInvoice().getShippingCost()));

			if (dealReq.getDeal().getDealType().equalsIgnoreCase("AMC") && dealReq.getDeal().getAmcFromDate() != null
					&& dealReq.getDeal().getAmcToDate() != null) {
				String subject = "Sub: " + dealReq.getDealProformaInvoice().getSubject()
						+ " <br>AMC Charges for the following items for the period of "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcFromDate(), "dd/MM/yyyy") + " to "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcToDate(), "dd/MM/yyyy");
				parameters.put("subject", subject);
			} else {
				parameters.put("subject", "Sub: " + dealReq.getDealProformaInvoice().getSubject());
			}

			parameters.put("billing_to", dealReq.getBillingToAddress());
			parameters.put("shipping_to", dealReq.getShippingToAddress());

			String price_summary_label = "", price_summary_text = "";

			price_summary_label = price_summary_label + "Original Rate : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getSubTotal())
					+ "<br>";

			if (dealReq.getDeal().getDiscount() > 0) {
				price_summary_label = price_summary_label + "Discount : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getDiscount())
						+ "<br>";
			}

			price_summary_label = price_summary_label + "Taxable Amount : <br>";
			price_summary_text = price_summary_text + "Rs."
					+ Util.decimalFormatter(dealReq.getDeal().getSubTotal() - dealReq.getDeal().getDiscount()) + "<br>";

			if (dealReq.getDeal().getGstType().equalsIgnoreCase("IGST")) {
				price_summary_label = price_summary_label + "IGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax())
						+ "<br>";
			} else {
				price_summary_label = price_summary_label + "CGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax() / 2)
						+ "<br>";

				price_summary_label = price_summary_label + "SGST : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter((dealReq.getDeal().getTax() / 2)) + "<br>";
			}

			if (dealReq.getDeal().getAdjustment() != 0.00) {
				price_summary_label = price_summary_label + "Adjustment : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ String.format("%.2f", dealReq.getDeal().getAdjustment()) + "<br>";
			}

			price_summary_label = price_summary_label + "Grand Total : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getGrandTotal())
					+ "<br>";

			if (dealReq.getDealProformaInvoice().getPaidAmount() > 0) {
				price_summary_label = price_summary_label + "Paid Amount : <br>";
				price_summary_text = price_summary_text + ""
						+ Util.decimalFormatter(dealReq.getDealProformaInvoice().getPaidAmount()) + "<br>";
			}

			if (dealReq.getDealProformaInvoice().getShippingCost() > 0) {
				price_summary_label = price_summary_label + "Shipment Amount : <br>";
				price_summary_text = price_summary_text + ""
						+ Util.decimalFormatter(dealReq.getDealProformaInvoice().getShippingCost()) + "<br>";
			}

			parameters.put("price_summary_label", price_summary_label);
			parameters.put("price_summary_text", price_summary_text);

			System.out.println(parameters.toString());

			List<Map<String, String>> datasource = new ArrayList<>();

			List<Integer> partIds = dealReq.getDealProducts().stream().mapToInt(DealProducts::getPartId).distinct()
					.boxed().sorted().collect(Collectors.toList());

			int sno = 1;
			for (int partId : partIds) {

				for (DealProducts prod : dealReq.getDealProducts()) {
					if (prod.getPartId() == partId) {

						Map<String, String> data = new HashMap<>();

						data.put("sno", sno + ".");
						data.put("type", "product");
						data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
						data.put("quantity", prod.getQuantityAsHTMLText());
						data.put("price", Util.decimalFormatter(prod.getRateAmount()));
						data.put("rate", Util.decimalFormatter(prod.getPrice()));
						data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
						if (dealReq.getDeal().getGstType().equals("IGST")) {
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

					Double totalAmount = dealReq.getDealProducts().stream().filter(prod -> prod.getPartId() == partId)
							.mapToDouble(DealProducts::getTotalAmount).sum();

					data.put("sno", "");
					data.put("type", "subtotal");
					data.put("name_description", "");
					data.put("quantity", "");
					data.put("price", "");
					data.put("rate", "");
					data.put("total", Util.decimalFormatter(totalAmount));
					if (dealReq.getDeal().getGstType().equals("IGST")) {
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

//			dealReq.getDealProducts().forEach(prod -> {
//				Map<String, String> data = new HashMap<>();
//
//				data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
//				data.put("quantity", prod.getQuantityAsHTMLText());
//				data.put("price", Util.decimalFormatter(prod.getRateAmount()));
//				data.put("rate", Util.decimalFormatter(prod.getPrice()));
//				data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
//				if (dealReq.getDeal().getGstType().equals("IGST")) {
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

			File directory = new File(contentPath + "/Deals/" + dealReq.getDeal().getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}

			if (dealReq.getExportType() != null && dealReq.getExportType().equalsIgnoreCase("rtf")) {

				dealReq.getDealProformaInvoice().setFilename(
						dealReq.getDealProformaInvoice().getProformaInvoiceNo().replaceAll("/", "-") + ".rtf");
				final String filePath = directory.getAbsolutePath() + "/"
						+ dealReq.getDealProformaInvoice().getFilename();
				System.out.println(filePath);

//				 Export the report to a RTF file.
				JRRtfExporter exporter = new JRRtfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
				exporter.exportReport();

			} else {

				dealReq.getDealProformaInvoice().setFilename(
						dealReq.getDealProformaInvoice().getProformaInvoiceNo().replaceAll("/", "-") + ".pdf");
				final String filePath = directory.getAbsolutePath() + "/"
						+ dealReq.getDealProformaInvoice().getFilename();
				System.out.println(filePath);

				// Export the report to a PDF file.
				JasperExportManager.exportReportToPdfFile(print, filePath);

			}

			proInvRepo.save(dealReq.getDealProformaInvoice());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealProformaInvoice", dealReq.getDealProformaInvoice());
		return resp;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> generateProformaInvoicePDFTemplate2(DealRequest dealReq) {
		Map<String, Object> resp = new HashMap<>();

		try {
			// DecimalFormat decformatter = new DecimalFormat("###,##0.00");
			// SimpleDateFormat Util.sdfFormatter = new SimpleDateFormat("dd/MM/yyyy");

			dealReq.setDealProformaInvoice(proInvRepo.findById(dealReq.getDealProformaInvoice().getId()));

			dealReq.setDeal(dealRepo.findById(dealReq.getDealProformaInvoice().getDealId()));

			dealReq.setDealProducts(dealProductsRepo.findByDealId(dealReq.getDealProformaInvoice().getDealId()));

			System.out.println(dealReq.toString());

			InputStream stream = null;

			if (dealReq.getDeal().getGstType().equals("IGST")) {
				stream = this.getClass()
						.getResourceAsStream("/reports/ProformaInvoice/Proforma_Invoice_Template_2_IGST.jrxml");
			} else {
				stream = this.getClass()
						.getResourceAsStream("/reports/ProformaInvoice/Proforma_Invoice_Template_2_CGST_SGST.jrxml");
			}

			final Map<String, Object> parameters = new HashMap<>();
			InfoDetails info = infoDetailRepo.findById(1);
			Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddressHTML2());
			parameters.put("cmp_logo_url", info.getLogoAsFile());
			parameters.put("invoice_label", "PROFORMA INVOICE");

			parameters.put("is_discount_applied", dealReq.isDiscountApplied());
			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", dealReq.getDesignation());
			parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
			parameters.put("online_payment_url", info.getInstamojoPaymentURL());

			parameters.put("terms", dealReq.getDealProformaInvoice().getTerms().replaceAll("\n", "<br>"));
			parameters.put("invoice_date", Util.sdfFormatter(dealReq.getDealProformaInvoice().getInvoiceDate()));
			parameters.put("due_date", Util.sdfFormatter(dealReq.getDealProformaInvoice().getDueDate()));
			parameters.put("invoice_no", String.valueOf(dealReq.getDealProformaInvoice().getProformaInvoiceNo()));
			parameters.put("sales_order_no", String.valueOf(dealReq.getDealProformaInvoice().getSalesOrderNo()));
			parameters.put("purchase_order_no", String.valueOf(dealReq.getDealProformaInvoice().getPurchaseOrderNo()));

			parameters.put("balance_amount",
					"Rs." + Util.decimalFormatter(
							(dealReq.getDeal().getGrandTotal() - dealReq.getDealProformaInvoice().getPaidAmount())
									+ dealReq.getDealProformaInvoice().getShippingCost()));
			parameters.put("amount_in_words",
					Util.EnglishNumberToWords(
							(dealReq.getDeal().getGrandTotal() - dealReq.getDealProformaInvoice().getPaidAmount())
									+ dealReq.getDealProformaInvoice().getShippingCost()));

			if (dealReq.getDeal().getDealType().equalsIgnoreCase("AMC") && dealReq.getDeal().getAmcFromDate() != null
					&& dealReq.getDeal().getAmcToDate() != null) {
				String subject = "Sub: " + dealReq.getDealProformaInvoice().getSubject()
						+ " <br>AMC Charges for the following items for the period of "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcFromDate(), "dd/MM/yyyy") + " to "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcToDate(), "dd/MM/yyyy");
				parameters.put("subject", subject);
			} else {
				parameters.put("subject", "Sub: " + dealReq.getDealProformaInvoice().getSubject());
			}

			parameters.put("billing_to", dealReq.getBillingToAddress());
			parameters.put("shipping_to", dealReq.getShippingToAddress());

			String price_summary_label = "", price_summary_text = "";

			price_summary_label = price_summary_label + "Original Rate : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getSubTotal())
					+ "<br>";

			if (dealReq.getDeal().getDiscount() > 0) {
				price_summary_label = price_summary_label + "Discount : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getDiscount())
						+ "<br>";
			}

			price_summary_label = price_summary_label + "Taxable Amount : <br>";
			price_summary_text = price_summary_text + "Rs."
					+ Util.decimalFormatter(dealReq.getDeal().getSubTotal() - dealReq.getDeal().getDiscount()) + "<br>";

			if (dealReq.getDeal().getGstType().equalsIgnoreCase("IGST")) {
				price_summary_label = price_summary_label + "IGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax())
						+ "<br>";
			} else {
				price_summary_label = price_summary_label + "CGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax() / 2)
						+ "<br>";

				price_summary_label = price_summary_label + "SGST : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter((dealReq.getDeal().getTax() / 2)) + "<br>";
			}

			if (dealReq.getDeal().getAdjustment() != 0.00) {
				price_summary_label = price_summary_label + "Adjustment : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ String.format("%.2f", dealReq.getDeal().getAdjustment()) + "<br>";
			}

			price_summary_label = price_summary_label + "Grand Total : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getGrandTotal())
					+ "<br>";

			price_summary_label = price_summary_label + "Paid Amount : <br>";
			price_summary_text = price_summary_text + ""
					+ Util.decimalFormatter(dealReq.getDealProformaInvoice().getPaidAmount()) + "<br>";

			if (dealReq.getDealProformaInvoice().getShippingCost() > 0) {
				price_summary_label = price_summary_label + "Shipment Amount : <br>";
				price_summary_text = price_summary_text + ""
						+ Util.decimalFormatter(dealReq.getDealProformaInvoice().getShippingCost()) + "<br>";
			}

			parameters.put("price_summary_label", price_summary_label);
			parameters.put("price_summary_text", price_summary_text);

			System.out.println(parameters.toString());

			List<Map<String, String>> datasource = new ArrayList<>();

			dealReq.getDealProducts().forEach(prod -> {
				Map<String, String> data = new HashMap<>();

				data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
				data.put("quantity", prod.getQuantityAsHTMLText());
				data.put("price", Util.decimalFormatter(prod.getRateAmount()));
				data.put("rate", Util.decimalFormatter(prod.getPrice()));
				data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
				if (dealReq.getDeal().getGstType().equals("IGST")) {
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

			File directory = new File(contentPath + "/Deals/" + dealReq.getDeal().getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}

			if (dealReq.getExportType() != null && dealReq.getExportType().equalsIgnoreCase("rtf")) {

				dealReq.getDealProformaInvoice().setFilename(
						dealReq.getDealProformaInvoice().getProformaInvoiceNo().replaceAll("/", "-") + ".rtf");
				final String filePath = directory.getAbsolutePath() + "/"
						+ dealReq.getDealProformaInvoice().getFilename();
				System.out.println(filePath);

//				 Export the report to a RTF file.
				JRRtfExporter exporter = new JRRtfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
				exporter.exportReport();

			} else {

				dealReq.getDealProformaInvoice().setFilename(
						dealReq.getDealProformaInvoice().getProformaInvoiceNo().replaceAll("/", "-") + ".pdf");
				final String filePath = directory.getAbsolutePath() + "/"
						+ dealReq.getDealProformaInvoice().getFilename();
				System.out.println(filePath);

				// Export the report to a PDF file.
				JasperExportManager.exportReportToPdfFile(print, filePath);

			}

			proInvRepo.save(dealReq.getDealProformaInvoice());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealProformaInvoice", dealReq.getDealProformaInvoice());
		return resp;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> generateProformaInvoicePDFTemplate3(DealRequest dealReq) {

		Map<String, Object> resp = new HashMap<>();

		try {

			dealReq.setDealProformaInvoice(proInvRepo.findById(dealReq.getDealProformaInvoice().getId()));

			dealReq.setDeal(dealRepo.findById(dealReq.getDealProformaInvoice().getDealId()));

			dealReq.setDealProducts(dealProductsRepo.findByDealId(dealReq.getDealProformaInvoice().getDealId()));

			System.out.println(dealReq.toString());

			InputStream stream = null;

			if (dealReq.getDeal().getGstType().equals("IGST")) {
				stream = this.getClass()
						.getResourceAsStream("/reports/FinalTemplates/Template_3/Template_3_IGST.jrxml");
			} else {
				stream = this.getClass()
						.getResourceAsStream("/reports/FinalTemplates/Template_3/Template_3_CGST_SGST.jrxml");
			}

			final Map<String, Object> parameters = new HashMap<>();

			InfoDetails info = infoDetailRepo.findById(1);
			Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddressHTML1());
			parameters.put("cmp_logo_url", info.getLogoAsFile());
			parameters.put("dealtype_label", "PROFORMA INVOICE");

			parameters.put("is_discount_applied", dealReq.isDiscountApplied());
			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", dealReq.getDesignation());
			parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
			parameters.put("online_payment_url", info.getInstamojoPaymentURL());

			parameters.put("terms", dealReq.getDealProformaInvoice().getTerms().replaceAll("\n", "<br>"));
			parameters.put("deal_amount_label", "Balance Amount :");
			parameters.put("deal_amount_text",
					"Rs." + Util.decimalFormatter(
							(dealReq.getDeal().getGrandTotal() - dealReq.getDealProformaInvoice().getPaidAmount())
									+ dealReq.getDealProformaInvoice().getShippingCost()));
			parameters.put("amount_in_words",
					Util.EnglishNumberToWords(
							(dealReq.getDeal().getGrandTotal() - dealReq.getDealProformaInvoice().getPaidAmount())
									+ dealReq.getDealProformaInvoice().getShippingCost()));

			if (dealReq.getDeal().getDealType().equalsIgnoreCase("AMC") && dealReq.getDeal().getAmcFromDate() != null
					&& dealReq.getDeal().getAmcToDate() != null) {
				String subject = "Sub: " + dealReq.getDealProformaInvoice().getSubject()
						+ " <br>AMC Charges for the following items for the period of "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcFromDate(), "dd/MM/yyyy") + " to "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcToDate(), "dd/MM/yyyy");
				parameters.put("subject", subject);
			} else {
				parameters.put("subject", "Sub: " + dealReq.getDealProformaInvoice().getSubject());
			}

			parameters.put("billing_to", dealReq.getBillingToAddress());
			parameters.put("shipping_to", dealReq.getShippingToAddress());

			String price_summary_label = "", price_summary_text = "";
			String deal_date_label = "", deal_date_text = "";

			deal_date_label = deal_date_label + "PI Number : <br>";
			deal_date_text = deal_date_text + String.valueOf(dealReq.getDealProformaInvoice().getProformaInvoiceNo())
					+ "<br>";
			deal_date_label = deal_date_label + "PI Date : <br>";
			deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealProformaInvoice().getInvoiceDate())
					+ "<br>";
			deal_date_label = deal_date_label + "Due Date : <br>";
			deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getDealProformaInvoice().getDueDate()) + "<br>";

			if (dealReq.getDealProformaInvoice().getPurchaseOrderNo() != null
					&& dealReq.getDealProformaInvoice().getPurchaseOrderNo() != "") {
				deal_date_label = deal_date_label + "PO Number : <br>";
				deal_date_text = deal_date_text + String.valueOf(dealReq.getDealProformaInvoice().getPurchaseOrderNo())
						+ "<br>";
			}
			if (dealReq.getDealProformaInvoice().getSalesOrderNo() != null
					&& dealReq.getDealProformaInvoice().getSalesOrderNo() != "") {
				deal_date_label = deal_date_label + "Sales Order No : <br>";
				deal_date_text = deal_date_text + String.valueOf(dealReq.getDealProformaInvoice().getSalesOrderNo())
						+ "<br>";
			}

			price_summary_label = price_summary_label + "Original Rate : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getSubTotal())
					+ "<br>";

			if (dealReq.getDeal().getDiscount() > 0) {
				price_summary_label = price_summary_label + "Discount : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getDiscount())
						+ "<br>";
			}

			price_summary_label = price_summary_label + "Taxable Amount : <br>";
			price_summary_text = price_summary_text + "Rs."
					+ Util.decimalFormatter(dealReq.getDeal().getSubTotal() - dealReq.getDeal().getDiscount()) + "<br>";

			if (dealReq.getDeal().getGstType().equalsIgnoreCase("IGST")) {
				price_summary_label = price_summary_label + "IGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax())
						+ "<br>";
			} else {
				price_summary_label = price_summary_label + "CGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax() / 2)
						+ "<br>";

				price_summary_label = price_summary_label + "SGST : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter((dealReq.getDeal().getTax() / 2)) + "<br>";
			}

			if (dealReq.getDeal().getAdjustment() != 0.00) {
				price_summary_label = price_summary_label + "Adjustment : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ String.format("%.2f", dealReq.getDeal().getAdjustment()) + "<br>";
			}

			price_summary_label = price_summary_label + "Grand Total : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getGrandTotal())
					+ "<br>";

			if (dealReq.getDealProformaInvoice().getPaidAmount() > 0) {
				price_summary_label = price_summary_label + "Paid Amount : <br>";
				price_summary_text = price_summary_text + ""
						+ Util.decimalFormatter(dealReq.getDealProformaInvoice().getPaidAmount()) + "<br>";
			}

			if (dealReq.getDealProformaInvoice().getShippingCost() > 0) {
				price_summary_label = price_summary_label + "Shipment Amount : <br>";
				price_summary_text = price_summary_text + ""
						+ Util.decimalFormatter(dealReq.getDealProformaInvoice().getShippingCost()) + "<br>";
			}

			parameters.put("price_summary_label", price_summary_label);
			parameters.put("price_summary_text", price_summary_text);

			parameters.put("deal_date_label", deal_date_label);
			parameters.put("deal_date_text", deal_date_text);

			System.out.println(parameters.toString());

			List<Map<String, String>> datasource = new ArrayList<>();

			List<Integer> partIds = dealReq.getDealProducts().stream().mapToInt(DealProducts::getPartId).distinct()
					.boxed().sorted().collect(Collectors.toList());

			int sno = 1;
			for (int partId : partIds) {

				for (DealProducts prod : dealReq.getDealProducts()) {
					if (prod.getPartId() == partId) {

						Map<String, String> data = new HashMap<>();

						data.put("sno", sno + ".");
						data.put("type", "product");
						data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
						data.put("quantity", prod.getQuantityAsHTMLText());
						data.put("price", Util.decimalFormatter(prod.getRateAmount()));
						data.put("rate", Util.decimalFormatter(prod.getPrice()));
						data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
						if (dealReq.getDeal().getGstType().equals("IGST")) {
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

					Double totalAmount = dealReq.getDealProducts().stream().filter(prod -> prod.getPartId() == partId)
							.mapToDouble(DealProducts::getTotalAmount).sum();

					data.put("sno", "");
					data.put("type", "subtotal");
					data.put("name_description", "");
					data.put("quantity", "");
					data.put("price", "");
					data.put("rate", "");
					data.put("total", Util.decimalFormatter(totalAmount));
					if (dealReq.getDeal().getGstType().equals("IGST")) {
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

//			dealReq.getDealProducts().forEach(prod -> {
//				Map<String, String> data = new HashMap<>();
//
//				data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
//				data.put("quantity", prod.getQuantityAsHTMLText());
//				data.put("price", Util.decimalFormatter(prod.getRateAmount()));
//				data.put("rate", Util.decimalFormatter(prod.getPrice()));
//				data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
//				if (dealReq.getDeal().getGstType().equals("IGST")) {
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

			File directory = new File(contentPath + "/Deals/" + dealReq.getDeal().getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}
			if (dealReq.getExportType() != null && dealReq.getExportType().equalsIgnoreCase("rtf")) {

				dealReq.getDealProformaInvoice().setFilename(
						dealReq.getDealProformaInvoice().getProformaInvoiceNo().replaceAll("/", "-") + ".rtf");
				final String filePath = directory.getAbsolutePath() + "/"
						+ dealReq.getDealProformaInvoice().getFilename();
				System.out.println(filePath);

//				 Export the report to a RTF file.
				JRRtfExporter exporter = new JRRtfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
				exporter.exportReport();

			} else {

				dealReq.getDealProformaInvoice().setFilename(
						dealReq.getDealProformaInvoice().getProformaInvoiceNo().replaceAll("/", "-") + ".pdf");
				final String filePath = directory.getAbsolutePath() + "/"
						+ dealReq.getDealProformaInvoice().getFilename();
				System.out.println(filePath);

				// Export the report to a PDF file.
				JasperExportManager.exportReportToPdfFile(print, filePath);

			}

			proInvRepo.save(dealReq.getDealProformaInvoice());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealProformaInvoice", dealReq.getDealProformaInvoice());
		return resp;

	}

	@Override
	public Map<String, Object> UploadGeneratedProformaInvoicePDF(int dealProformaInvoiceId, MultipartFile file) {
		Map<String, Object> resp = new HashMap<>();

		DealProformaInvoice di = proInvRepo.findById(dealProformaInvoiceId);

		File directory = new File(contentPath + "/Deals/" + di.getDealId());
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
			di = proInvRepo.save(di);

			resp.putAll(Util.SuccessResponse());
		} catch (IOException e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealProformaInvoice", di);
		return resp;
	}

	@Override
	public Map<String, Object> getDealInvoicesPreview(int dealId) {
		Map<String, Object> resp = new HashMap<>();
		List<DealInvoice> dealInvoices = new ArrayList<>();
		try {

			dealInvoices = invRepo.findByDealId(dealId);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealInvoices", dealInvoices);

		return resp;
	}

	@Override
	public Map<String, Object> saveDealPurchaseOrder(DealPurchaseOrder po) {
		Map<String, Object> resp = new HashMap<>();
		try {

			DealPurchaseOrder potemp = poRepo.findByPurchaseOrderNo(po.getPurchaseOrderNo());

			if (po.getId() == 0 && potemp != null) {
				resp.putAll(Util.invalidMessage("Purchase Order No Already Exist"));
			} else if (potemp != null && po.getId() != potemp.getId()) {
				resp.putAll(Util.invalidMessage("Purchase Order No Already Exist with another Deal"));
			} else {

				po = poRepo.save(po);

				Query query = em.createQuery("UPDATE Deal d SET d.purchaseOrderNo = :purchaseOrderNo WHERE d.id = :id");
				query.setParameter("purchaseOrderNo", po.getPurchaseOrderNo());
				query.setParameter("id", po.getDealId());
				query.executeUpdate();

				resp.putAll(Util.SuccessResponse());
			}

		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		resp.put("DealPurchaseOrder", po);
		return resp;
	}

	@Override
	public Map<String, Object> getDealPurchaseOrder(int dealId) {
		Map<String, Object> resp = new HashMap<>();
		DealPurchaseOrder po = new DealPurchaseOrder();
		Deal deal = new Deal();

		try {
			po = poRepo.findByDealId(dealId);

			deal = dealRepo.findById(dealId);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealPurchaseOrder", po);
		resp.put("Deal", deal);

		return resp;
	}

	@Override
	public Map<String, Object> UploadPurchaseOrderFile(int dealPurchaseOrderId, MultipartFile file) {
		Map<String, Object> resp = new HashMap<>();

		DealPurchaseOrder po = poRepo.findById(dealPurchaseOrderId);

		File directory = new File(contentPath + "/Deals/" + po.getDealId());
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

			po.setFilename(file.getOriginalFilename());
			po = poRepo.save(po);

			resp.putAll(Util.SuccessResponse());
		} catch (IOException e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealPurchaseOrder", po);
		return resp;
	}

	@Override
	public Map<String, Object> saveDealSalesOrder(DealSalesOrder so) {
		Map<String, Object> resp = new HashMap<>();
		try {

			DealSalesOrder sotemp = soRepo.findBySalesOrderNo(so.getSalesOrderNo());

			if (so.getId() == 0 && sotemp != null) {
				resp.putAll(Util.invalidMessage("Sales Order No Already Exist"));
			} else if (sotemp != null && so.getId() != sotemp.getId()) {
				resp.putAll(Util.invalidMessage("Sales Order No Already Exist with another Deal"));
			} else {

				so = soRepo.save(so);

				Query query = em.createQuery("UPDATE Deal d SET d.salesOrderNo = :salesOrderNo WHERE d.id = :id");
				query.setParameter("salesOrderNo", so.getSalesOrderNo());
				query.setParameter("id", so.getDealId());
				query.executeUpdate();

				resp.putAll(Util.SuccessResponse());
			}

		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		resp.put("DealSalesOrder", so);
		return resp;
	}

	@Override
	public Map<String, Object> getDealSalesOrder(int dealId) {
		Map<String, Object> resp = new HashMap<>();
		DealSalesOrder so = new DealSalesOrder();
		Deal deal = new Deal();
		String autoGeneratedNo = "SO/001", lastTerms = "";
		try {
			so = soRepo.findByDealId(dealId);

			deal = dealRepo.findById(dealId);

			if (so == null) { // if null AutoIncrement the Quotation No. from last Quotation.

				DealSalesOrder soTemp = soRepo.findLastSalesOrderByType(dealId);

				if (soTemp != null) {
					autoGeneratedNo = Util.autoIncrementNo(soTemp.getSalesOrderNo());
					lastTerms = soTemp.getTerms();

				}
			}

			resp.putAll(Util.SuccessResponse());
		} catch (

		Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealSalesOrder", so);
		resp.put("Deal", deal);
		resp.put("AutoGeneratedSONo", autoGeneratedNo);
		resp.put("lastTerms", lastTerms);

		return resp;
	}

	@Override
	public Map<String, Object> UploadSalesOrderFile(int dealSalesOrderId, MultipartFile file) {
		Map<String, Object> resp = new HashMap<>();

		DealSalesOrder so = soRepo.findById(dealSalesOrderId);

		File directory = new File(contentPath + "/Deals/" + so.getDealId());
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

			so.setFilename(file.getOriginalFilename());
			so = soRepo.save(so);

			resp.putAll(Util.SuccessResponse());
		} catch (IOException e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealSalesOrder", so);
		return resp;
	}

	@Override
	public Map<String, Object> generateSalesOrderPDF1(DealRequest dealReq) {
		Map<String, Object> resp = new HashMap<>();

		try {

			System.out.println("Inside generateSalesOrderPDF DaoImpl:::::::::" + dealReq.getSalesOrder().getId());

			dealReq.setSalesOrder(soRepo.findById(dealReq.getSalesOrder().getId()));

			dealReq.setDealQuotation(quoteRepo.findByDealId(dealReq.getSalesOrder().getDealId()));

			dealReq.setDeal(dealRepo.findById(dealReq.getSalesOrder().getDealId()));

			dealReq.setDealProducts(dealProductsRepo.findByDealId(dealReq.getSalesOrder().getDealId()));

			InputStream stream = null;

//			if (dealReq.getDeal().getGstType().equals("IGST")) {
//				stream = this.getClass().getResourceAsStream("/reports/SalesOrder/Sales_Template_1_IGST.jrxml");
//			} else {
//				stream = this.getClass().getResourceAsStream("/reports/SalesOrder/Sales_Template_1_CGST_SGST.jrxml");
//			}
//			
			if (dealReq.getDeal().getGstType().equals("IGST")) {
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
			parameters.put("dealtype_label", "SALES ORDER");

			parameters.put("is_discount_applied", dealReq.isDiscountApplied());
			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", dealReq.getDesignation());
			parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
			parameters.put("online_payment_url", info.getInstamojoPaymentURL());

			parameters.put("terms", dealReq.getSalesOrder().getTerms().replaceAll("\n", "<br>"));
//			parameters.put("vailid_until", Util.sdfFormatter(dealReq.getSalesOrder().getDueDate()));
//			parameters.put("quote_no", String.valueOf(dealReq.getDealQuotation().getQuoteNo()));

			parameters.put("grand_total_text", "Grand Total : ");
			parameters.put("grand_total", "Rs." + Util.decimalFormatter(dealReq.getDeal().getGrandTotal()));
			parameters.put("amount_in_words", Util.EnglishNumberToWords(dealReq.getDeal().getGrandTotal()));

//			parameters.put("sales_order_no", dealReq.getSalesOrder().getSalesOrderNo());
//			parameters.put("purchase_order_no", dealReq.getSalesOrder().getPurchaseOrderNo());

			String deal_date_label = "", deal_date_text = "";

			deal_date_label = deal_date_label + "Sales Order No : <br>";
			deal_date_text = deal_date_text + dealReq.getSalesOrder().getSalesOrderNo() + "<br>";

			if (dealReq.getSalesOrder().getPurchaseOrderNo() != null
					&& dealReq.getSalesOrder().getPurchaseOrderNo() != "") {
				deal_date_label = deal_date_label + "PO Number : <br>";
				deal_date_text = deal_date_text + String.valueOf(dealReq.getSalesOrder().getPurchaseOrderNo()) + "<br>";
			}
			if (dealReq.getDealQuotation().getQuoteNo() != null && dealReq.getDealQuotation().getQuoteNo() != "") {
				deal_date_label = deal_date_label + "Quote No : <br>";
				deal_date_text = deal_date_text + String.valueOf(dealReq.getDealQuotation().getQuoteNo()) + "<br>";
			}

			deal_date_label = deal_date_label + "Due Date : <br>";
			deal_date_text = deal_date_text + Util.sdfFormatter(dealReq.getSalesOrder().getDueDate()) + "<br>";

			parameters.put("deal_date_label", deal_date_label);
			parameters.put("deal_date_text", deal_date_text);

			parameters.put("billing_to", dealReq.getBillingToAddress());
			parameters.put("shipping_to", dealReq.getShippingToAddress());

			String price_summary_label = "", price_summary_text = "";

			price_summary_label = price_summary_label + "Original Rate : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getSubTotal())
					+ "<br>";

			if (dealReq.getDeal().getDiscount() > 0) {
				price_summary_label = price_summary_label + "Discount : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getDiscount())
						+ "<br>";
			}

			price_summary_label = price_summary_label + "Taxable Amount : <br>";
			price_summary_text = price_summary_text + "Rs."
					+ Util.decimalFormatter(dealReq.getDeal().getSubTotal() - dealReq.getDeal().getDiscount()) + "<br>";

			if (dealReq.getDeal().getGstType().equalsIgnoreCase("IGST")) {
				price_summary_label = price_summary_label + "IGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax())
						+ "<br>";
			} else {
				price_summary_label = price_summary_label + "CGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax() / 2)
						+ "<br>";

				price_summary_label = price_summary_label + "SGST : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter((dealReq.getDeal().getTax() / 2)) + "<br>";
			}

			if (dealReq.getDeal().getAdjustment() != 0.00) {
				price_summary_label = price_summary_label + "Adjustment : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ String.format("%.2f", dealReq.getDeal().getAdjustment()) + "<br>";
			}

			parameters.put("price_summary_label", price_summary_label);
			parameters.put("price_summary_text", price_summary_text);

			List<Map<String, String>> datasource = new ArrayList<>();

			List<Integer> partIds = dealReq.getDealProducts().stream().mapToInt(DealProducts::getPartId).distinct()
					.boxed().sorted().collect(Collectors.toList());

			int sno = 1;
			for (int partId : partIds) {

				for (DealProducts prod : dealReq.getDealProducts()) {
					if (prod.getPartId() == partId) {

						Map<String, String> data = new HashMap<>();

						data.put("sno", sno + ".");
						data.put("type", "product");
						data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
						data.put("quantity", prod.getQuantityAsHTMLText());
						data.put("price", Util.decimalFormatter(prod.getRateAmount()));
						data.put("rate", Util.decimalFormatter(prod.getPrice()));
						data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
						if (dealReq.getDeal().getGstType().equals("IGST")) {
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

					Double totalAmount = dealReq.getDealProducts().stream().filter(prod -> prod.getPartId() == partId)
							.mapToDouble(DealProducts::getTotalAmount).sum();

					data.put("sno", "");
					data.put("type", "subtotal");
					data.put("name_description", "");
					data.put("quantity", "");
					data.put("price", "");
					data.put("rate", "");
					data.put("total", Util.decimalFormatter(totalAmount));
					if (dealReq.getDeal().getGstType().equals("IGST")) {
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

//			dealReq.getDealProducts().forEach(prod -> {
//				Map<String, String> data = new HashMap<>();
//
//				data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
//				data.put("quantity", prod.getQuantityAsHTMLText());
//				data.put("price", Util.decimalFormatter(prod.getRateAmount()));
//				data.put("rate", Util.decimalFormatter(prod.getPrice()));
//				data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
//				if (dealReq.getDeal().getGstType().equals("IGST")) {
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

			File directory = new File(contentPath + "/Deals/" + dealReq.getDeal().getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}

			dealReq.getSalesOrder()
					.setFilename(dealReq.getSalesOrder().getSalesOrderNo().replaceAll("/", "-") + ".pdf");
			final String filePath = directory.getAbsolutePath() + "/" + dealReq.getSalesOrder().getFilename();
			System.out.println(filePath);
			soRepo.save(dealReq.getSalesOrder());

			// Export the report to a PDF file.
			JasperExportManager.exportReportToPdfFile(print, filePath);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealSalesOrder", dealReq.getSalesOrder());
		return resp;
	}

	@Override
	public Map<String, Object> generateSalesOrderPDF2(DealRequest dealReq) {
		Map<String, Object> resp = new HashMap<>();

		try {

			System.out.println("Inside generateSalesOrderPDF DaoImpl:::::::::" + dealReq.getSalesOrder().getId());
			// DecimalFormat decformatter = new DecimalFormat("###,##0.00");
			// SimpleDateFormat Util.sdfFormatter = new SimpleDateFormat("dd/MM/yyyy");

			dealReq.setSalesOrder(soRepo.findById(dealReq.getSalesOrder().getId()));

			dealReq.setDealQuotation(quoteRepo.findByDealId(dealReq.getSalesOrder().getDealId()));

			dealReq.setDeal(dealRepo.findById(dealReq.getSalesOrder().getDealId()));

			dealReq.setDealProducts(dealProductsRepo.findByDealId(dealReq.getSalesOrder().getDealId()));

			InputStream stream = null;

			if (dealReq.getDeal().getGstType().equals("IGST")) {
				stream = this.getClass().getResourceAsStream("/reports/SalesOrder/Sales_Template_2_IGST.jrxml");
			} else {
				stream = this.getClass().getResourceAsStream("/reports/SalesOrder/Sales_Template_2_CGST_SGST.jrxml");
			}

			final Map<String, Object> parameters = new HashMap<>();
			InfoDetails info = infoDetailRepo.findById(1);
			Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddressHTML2());
			parameters.put("cmp_logo_url", info.getLogoAsFile());

			parameters.put("is_discount_applied", dealReq.isDiscountApplied());
			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", dealReq.getDesignation());
			parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
			parameters.put("online_payment_url", info.getInstamojoPaymentURL());

			parameters.put("terms", dealReq.getSalesOrder().getTerms().replaceAll("\n", "<br>"));
			parameters.put("vailid_until", Util.sdfFormatter(dealReq.getSalesOrder().getDueDate()));
			parameters.put("quote_no", String.valueOf(dealReq.getDealQuotation().getQuoteNo()));

			parameters.put("grand_total_text", "Grand Total : ");
			parameters.put("grand_total", "Rs." + Util.decimalFormatter(dealReq.getDeal().getGrandTotal()));
			parameters.put("amount_in_words", Util.EnglishNumberToWords(dealReq.getDeal().getGrandTotal()));

			parameters.put("sales_order_no", dealReq.getSalesOrder().getSalesOrderNo());
			parameters.put("purchase_order_no", dealReq.getSalesOrder().getPurchaseOrderNo());

			parameters.put("billing_to", dealReq.getBillingToAddress());
			parameters.put("shipping_to", dealReq.getShippingToAddress());

			String price_summary_label = "", price_summary_text = "";

			price_summary_label = price_summary_label + "Original Rate : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getSubTotal())
					+ "<br>";

			if (dealReq.getDeal().getDiscount() > 0) {
				price_summary_label = price_summary_label + "Discount : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getDiscount())
						+ "<br>";
			}

			price_summary_label = price_summary_label + "Taxable Amount : <br>";
			price_summary_text = price_summary_text + "Rs."
					+ Util.decimalFormatter(dealReq.getDeal().getSubTotal() - dealReq.getDeal().getDiscount()) + "<br>";

			if (dealReq.getDeal().getGstType().equalsIgnoreCase("IGST")) {
				price_summary_label = price_summary_label + "IGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax())
						+ "<br>";
			} else {
				price_summary_label = price_summary_label + "CGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax() / 2)
						+ "<br>";

				price_summary_label = price_summary_label + "SGST : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter((dealReq.getDeal().getTax() / 2)) + "<br>";
			}

			if (dealReq.getDeal().getAdjustment() != 0.00) {
				price_summary_label = price_summary_label + "Adjustment : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ String.format("%.2f", dealReq.getDeal().getAdjustment()) + "<br>";
			}

			parameters.put("price_summary_label", price_summary_label);
			parameters.put("price_summary_text", price_summary_text);

			List<Map<String, String>> datasource = new ArrayList<>();

			dealReq.getDealProducts().forEach(prod -> {
				Map<String, String> data = new HashMap<>();

				data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
				data.put("quantity", prod.getQuantityAsHTMLText());
				data.put("price", Util.decimalFormatter(prod.getRateAmount()));
				data.put("rate", Util.decimalFormatter(prod.getPrice()));
				data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
				if (dealReq.getDeal().getGstType().equals("IGST")) {
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

			File directory = new File(contentPath + "/Deals/" + dealReq.getDeal().getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}

			dealReq.getSalesOrder()
					.setFilename(dealReq.getSalesOrder().getSalesOrderNo().replaceAll("/", "-") + ".pdf");
			final String filePath = directory.getAbsolutePath() + "/" + dealReq.getSalesOrder().getFilename();
			System.out.println(filePath);
			soRepo.save(dealReq.getSalesOrder());

			// Export the report to a PDF file.
			JasperExportManager.exportReportToPdfFile(print, filePath);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealSalesOrder", dealReq.getSalesOrder());
		return resp;

	}

	@Override
	public Map<String, Object> saveDealPayments(DealPayments payment) {
		Map<String, Object> resp = new HashMap<>();
		List<DealPayments> payments = new ArrayList<>();
		try {

			payment = paymentRepo.save(payment);

			payments = paymentRepo.findByDealId(payment.getDealId());

			double sum = payments.stream().mapToDouble(DealPayments::getTotalAmount).sum();

			Query query = em
					.createQuery("UPDATE DealInvoice di SET di.paidAmount = :paidAmount WHERE di.dealId = :dealId");
			query.setParameter("paidAmount", sum);
			query.setParameter("dealId", payment.getDealId());
			query.executeUpdate();

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
	public Map<String, Object> getDealPayments(int dealId) {
		Map<String, Object> resp = new HashMap<>();
		List<DealPayments> payments = new ArrayList<>();
		Deal deal = new Deal();
		try {
			deal = dealRepo.findById(dealId);

			payments = paymentRepo.findByDealId(dealId);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealPayments", payments);
		resp.put("Deal", deal);

		return resp;
	}

	@Override
	public Map<String, Object> deleteDealPayments(DealPayments req) {
		Map<String, Object> resp = new HashMap<>();
		List<DealPayments> payments = new ArrayList<>();
		try {

			paymentRepo.delete(req);

			payments = paymentRepo.findByDealId(req.getDealId());

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
		DealPayments dealPayments = new DealPayments();
		try {

			dealReq.setPayment(paymentRepo.findById(dealReq.getPayment().getId()));

			Deal deal = dealRepo.findById(dealReq.getPayment().getDealId());

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
			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("logo", info.getLogoAsFile());

			System.out.println("Company Logo::::::::::::::::::" + info.getLogoAsFile());
			String payment_details_description = dealReq.getReceiptContent();

			parameters.put("payment_details_description", payment_details_description);

			if (deal.getInvoiceNo() != null)
				parameters.put("bill_no", deal.getInvoiceNo());
			else
				parameters.put("bill_no", "");

			List<Map<String, String>> datasource = new ArrayList<>();

			final JasperReport report = JasperCompileManager.compileReport(stream);
			final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

			final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

//			File directory = new File(contentPath + "/Receipts/");
			File directory = new File(contentPath + "/Deals/" + deal.getId());
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
		return resp;

	}

	@Override
	public Map<String, Object> createDealPaymentsReceipt2(DealRequest dealReq) {

		Map<String, Object> resp = new HashMap<>();
		DealPayments dealPayments = new DealPayments();
		try {

			dealReq.setPayment(paymentRepo.findById(dealReq.getPayment().getId()));

			Deal deal = dealRepo.findById(dealReq.getPayment().getDealId());

			dealReq.setDeal(deal);

			System.out.println(dealReq);

			InputStream stream = null;

			stream = this.getClass().getResourceAsStream("/reports/Receipt/Receipt_2_Template.jrxml");

			final Map<String, Object> parameters = new HashMap<>();

			String billAdd = (String) dealReq.getBillingToAddress();
			if (billAdd.startsWith(",<br>")) {
				System.out.println(":::Inside Condition:::");
				billAdd = billAdd.substring(5);
			}
			InfoDetails info = infoDetailRepo.findById(1);
			Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());
			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddressHTML2());
			parameters.put("billing_to", billAdd.replace("<br>,", ""));

			parameters.put("receipt_no", "R-" + dealReq.getPayment().getId());
			parameters.put("reference_no", dealReq.getPayment().getReferenceno());
			parameters.put("invoiceno", deal.getInvoiceNo());
			parameters.put("paymode", dealReq.getPayment().getMode());
			parameters.put("payment_date", dealReq.getPaymentDate());

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

			parameters.put("bill_no", deal.getInvoiceNo());

			System.out.println(parameters.toString());

			List<Map<String, String>> datasource = new ArrayList<>();

			final JasperReport report = JasperCompileManager.compileReport(stream);
			final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

			final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

//			File directory = new File(contentPath + "/Receipts/");
			File directory = new File(contentPath + "/Deals/" + deal.getId());
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
		return resp;

	}

//
//	@Override
//	public Map<String, Object> createDealPaymentsReceipt(DealRequest dealReq) {
//
//		Map<String, Object> resp = new HashMap<>();
//		Deal deal = new Deal();
//		DealPayments dealPayments = new DealPayments();
//		try {
//			// DecimalFormat decformatter = new DecimalFormat("###,##0.00");
//			// SimpleDateFormat Util.sdfFormatter = new SimpleDateFormat("dd/MM/yyyy");
//
//			dealReq.setPayment(paymentRepo.findById(dealReq.getPayment().getId()));
//
//			deal = dealRepo.findById(dealReq.getPayment().getDealId());
//
////			DealInvoice invoice = invRepo.findByDealId(dealReq.getPayment().getDealId());
//
//			System.out.println(dealReq);
//
//			InputStream stream = null;
//
//			stream = this.getClass().getResourceAsStream("/reports/Receipt.jrxml");
//
//			final Map<String, Object> parameters = new HashMap<>();
//
//			InfoDetails info = infoDetailRepo.findById(1);
//			Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());
//
//			parameters.put("cmp_name", info.getCmpName());
//			parameters.put("cmp_address", info.getCompanyAddressHTML2());
//
//			parameters.put("towards", "purchase on " + info.getCmpName());
//			parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
//			parameters.put("online_payment_url", info.getInstamojoPaymentURL());
//
//			parameters.put("receipt_no", "R-" + dealReq.getPayment().getId());
//			parameters.put("receipt_date", Util.sdfFormatter(new Date()));
//			parameters.put("clg_name", deal.getInstitute().getInstituteName());
//			parameters.put("total_amount", "Rs. " + Util.decimalFormatter(dealReq.getPayment().getTotalAmount()));
//			parameters.put("total_amount_words", "Rs. " + Util.decimalFormatter(dealReq.getPayment().getTotalAmount())
//					+ " (Rupees " + Util.EnglishNumberToWords(dealReq.getPayment().getTotalAmount()) + ")");
//
//			parameters.put("cheque_no", dealReq.getPayment().getReferenceno());
//			parameters.put("payment_date", Util.sdfFormatter(dealReq.getPayment().getPaymentDate()));
//			parameters.put("bank_name", dealReq.getPayment().getDrawnon());
//
//			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
//			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
//			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
//			parameters.put("logo", info.getLogoAsFile());
//
//			System.out.println("Company Logo::::::::::::::::::" + info.getLogoAsFile());
//			String payment_details_description = dealReq.getReceiptContent();
//
//			payment_details_description = payment_details_description + " Received with thanks from   "
//					+ deal.getInstitute().getInstituteName();
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
//
//			parameters.put("payment_details_description", payment_details_description);
//
//			if (deal.getInvoiceNo() != null)
//				parameters.put("bill_no", deal.getInvoiceNo());
//			else
//				parameters.put("bill_no", "");
//
//			System.out.println(parameters.toString());
//
//			List<Map<String, String>> datasource = new ArrayList<>();
//
//			final JasperReport report = JasperCompileManager.compileReport(stream);
//			final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);
//
//			final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);
//
//			File directory = new File(contentPath + "/Deals/" + deal.getId());
//			System.out.println(directory.getAbsolutePath());
//			if (!directory.exists()) {
//				System.out.println("Directory created ::" + directory.getAbsolutePath());
//				directory.mkdirs();
//			}
//			dealReq.getPayment().setReceiptfilename("Receipt_" + dealReq.getPayment().getId() + "_Rs_"
//					+ Math.round(dealReq.getPayment().getTotalAmount()) + ".pdf");
//			final String filePath = directory.getAbsolutePath() + "/" + dealReq.getPayment().getReceiptfilename();
//			System.out.println(filePath);
//
//			// dealReq = paymentRepo.save(dealReq.getPayment());
//
//			dealPayments = paymentRepo.save(dealReq.getPayment());
//
//			// Export the report to a PDF file.
//			JasperExportManager.exportReportToPdfFile(print, filePath);
//			resp.putAll(Util.SuccessResponse());
//		} catch (Exception e) {
//			resp.putAll(Util.FailedResponse(e.getMessage()));
//			e.printStackTrace();
//		}
//		resp.put("DealPayment", dealPayments);
//		resp.put("Deal", deal);
//		return resp;
//
//	}

	@Transactional
	@Override
	public Map<String, Object> saveDealDeliveryChallan(DealDeliveryChallan dc) {
		Map<String, Object> resp = new HashMap<>();
		try {

			boolean newDC = dc.getId() > 0 ? false : true;

			dc = dcRepo.save(dc);

			if (newDC) {

				for (DealDCProducts dcp : dc.getProducts()) {

					// Stock Entry
					StockEntry se = new StockEntry();
					se.setProductId(dcp.getProductId());
					se.setEntryDate(new Date());
					se.setEntryBy("--system-generated--");
					se.setQuantity(dcp.getQuantity());
					se.setType("Deduct");
					se.setRemarks("DC generated for invoice #" + dcp.getInvoiceNo() + " at "
							+ Util.sdfFormatter(dc.getCreateddatetime(), "dd/MM/yyyy"));

					agentService.addStockEntry(se);

				}
			}

			resp.putAll(Util.SuccessResponse());

		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		resp.put("DealDeliveryChallan", dc);
		return resp;
	}

	@Override
	public Map<String, Object> deleteDealDeliveryChallan(DealDeliveryChallan dc) {
		Map<String, Object> resp = new HashMap<>();

		try {

			dcRepo.delete(dc);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}

		return resp;
	}

	@Override
	public Map<String, Object> getDealDeliveryChallan(int dealId) {
		Map<String, Object> resp = new HashMap<>();
		List<DealDeliveryChallan> dc = new ArrayList<>();

		try {

			dc = dcRepo.findByDealId(dealId);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealDeliveryChallans", dc);

		return resp;
	}

	@SuppressWarnings("unused")
	@Override
	public Map<String, Object> getPendingDCQuantityProducts(DealRequest dealReq) {

		// TODO Auto-generated method stub
		Map<String, Object> resp = new HashMap<>();
		Deal deal = null;
//		List<DealInvoice> dealInvoices = new ArrayList<>();
		List<DealProducts> reminingDealProducts = new ArrayList<>();

		try {

			if (dealReq.getDeal().getId() > 0) {

				List<DealProducts> __dealProducts = dealProductsRepo.findByDealId(dealReq.getDeal().getId());

				System.out.println("__dealProducts::" + __dealProducts.size());

				deal = dealRepo.findById(dealReq.getDeal().getId());
				List<DealDCProducts> dcps = dcProdRepo.findByDealId(dealReq.getDeal().getId());

				for (DealProducts _dp : __dealProducts) {
					DealProducts dp = _dp.clone();
					for (DealDCProducts dcp : dcps) {
						if (dp.getProductId() == dcp.getProductId())
							dp.setQuantity(dp.getQuantity() - dcp.getQuantity());
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
		resp.put("ReminingDealProducts", reminingDealProducts);
		return resp;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDealQuotationsReport(DealSearchRequest req) {

		Map<String, Object> resp = new HashMap<>();
		List<DealResponse> quotes = new ArrayList<>();
		try {

			String dealFilter = "";

			/* Deals Filter starts */

			if (req.getInstitutes() != null && req.getInstitutes().size() > 0) {
				String instituteIds = "'0'";
				for (Institute inst : req.getInstitutes()) {
					instituteIds = instituteIds + ",'" + inst.getInstituteId() + "'";
				}
				dealFilter = dealFilter + " and d.institute in (" + instituteIds + ") ";
			}
			if (req.getAgents() != null && req.getAgents().size() > 0) {
				String agents = "'0'";
				for (Agent agnt : req.getAgents()) {
					agents = agents + ",'" + agnt.getEmailId() + "'";
				}
				dealFilter = dealFilter + " and d.createdBy in (" + agents + ") ";
			}

			if (req.getDealCreatedDateFrom() != null && req.getDealCreatedDateTo() != null) {
				dealFilter = dealFilter + " and d.createddatetime between '"
						+ Util.sdfFormatter(req.getDealCreatedDateFrom()) + "' and '"
						+ Util.sdfFormatter(req.getDealCreatedDateTo()) + " 23:59:59'";
			}

			if (req.getDealModifiedDateFrom() != null && req.getDealModifiedDateTo() != null) {
				dealFilter = dealFilter + " and d.lastupdatedatetime between '"
						+ Util.sdfFormatter(req.getDealModifiedDateFrom()) + "' and '"
						+ Util.sdfFormatter(req.getDealModifiedDateTo()) + " 23:59:59'";
			}

			if (req.getDealProducts() != null && req.getDealProducts().size() > 0) {
				String productIds = "0";
				for (DealProducts dp : req.getDealProducts()) {
					productIds = productIds + "," + dp.getId();
				}
				dealFilter = dealFilter + " and d.id in ( select dp.dealId from DealProducts dp where dp.productId in ("
						+ productIds + "))";
			}
			if (req.getDealType() != null && req.getDealType() != "") {
				dealFilter = dealFilter + " and d.dealType = '" + req.getDealType() + "'";
			}

			/* Deals Filter ends and Deal Quotation Filter starts */

			if (req.getQuoteNo() != null && req.getQuoteNo() != "") {
				dealFilter = dealFilter + " and dq.quoteNo like '%" + req.getQuoteNo() + "%'";
			}
			if (req.getQuoteSubject() != null && req.getQuoteSubject() != "") {
				dealFilter = dealFilter + " and dq.subject like '%" + req.getQuoteSubject() + "%'";
			}
			if (req.getQuoteStage() != null && req.getQuoteStage() != "") {
				dealFilter = dealFilter + " and dq.quoteStage = '" + req.getQuoteStage() + "'";
			}
			if (req.getQuoteDateFrom() != null && req.getQuoteDateTo() != null) {
				dealFilter = dealFilter + " and dq.quoteDate between '" + Util.sdfFormatter(req.getQuoteDateFrom())
						+ "' and '" + Util.sdfFormatter(req.getQuoteDateTo()) + "'";
			}
			if (req.getQuoteValidDateFrom() != null && req.getQuoteValidDateTo() != null) {
				dealFilter = dealFilter + " and dq.validUntil between '"
						+ Util.sdfFormatter(req.getQuoteValidDateFrom()) + "' and '"
						+ Util.sdfFormatter(req.getQuoteValidDateTo()) + "'";
			}

			/* Deals Quotation Filter ends */

			Query query = em.createQuery(
					"select new com.autolib.helpdesk.Sales.model.DealResponse(d , dq) from DealQuotation dq "
							+ "left join fetch Deal d on (dq.dealId = d.id) where 2 > 1 " + dealFilter,
					DealResponse.class);

			quotes = query.getResultList();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealQuotations", quotes);
		return resp;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDealPurchaseOrdersReport(DealSearchRequest req) {

		Map<String, Object> resp = new HashMap<>();
		List<DealResponse> purchaseOrders = new ArrayList<>();
		try {

			String dealFilter = "";

			/* Deals Filter starts */

			if (req.getInstitutes() != null && req.getInstitutes().size() > 0) {
				String instituteIds = "'0'";
				for (Institute inst : req.getInstitutes()) {
					instituteIds = instituteIds + ",'" + inst.getInstituteId() + "'";
				}
				dealFilter = dealFilter + " and d.institute in (" + instituteIds + ") ";
			}
			if (req.getAgents() != null && req.getAgents().size() > 0) {
				String agents = "'0'";
				for (Agent agnt : req.getAgents()) {
					agents = agents + ",'" + agnt.getEmailId() + "'";
				}
				dealFilter = dealFilter + " and d.createdBy in (" + agents + ") ";
			}

			if (req.getDealCreatedDateFrom() != null && req.getDealCreatedDateTo() != null) {
				dealFilter = dealFilter + " and d.createddatetime between '"
						+ Util.sdfFormatter(req.getDealCreatedDateFrom()) + "' and '"
						+ Util.sdfFormatter(req.getDealCreatedDateTo()) + " 23:59:59'";
			}

			if (req.getDealModifiedDateFrom() != null && req.getDealModifiedDateTo() != null) {
				dealFilter = dealFilter + " and d.lastupdatedatetime between '"
						+ Util.sdfFormatter(req.getDealModifiedDateFrom()) + "' and '"
						+ Util.sdfFormatter(req.getDealModifiedDateTo()) + " 23:59:59'";
			}

			if (req.getDealProducts() != null && req.getDealProducts().size() > 0) {
				String productIds = "0";
				for (DealProducts dp : req.getDealProducts()) {
					productIds = productIds + "," + dp.getId();
				}
				dealFilter = dealFilter + " and d.id in ( select dp.dealId from DealProducts dp where dp.productId in ("
						+ productIds + "))";
			}
			if (req.getDealType() != null && req.getDealType() != "") {
				dealFilter = dealFilter + " and d.dealType = '" + req.getDealType() + "'";
			}

			/* Deals Filter ends and Deal Quotation Filter starts */

			if (req.getPoNo() != null && req.getPoNo() != "") {
				dealFilter = dealFilter + " and dpo.purchaseOrderNo like '%" + req.getPoNo() + "%'";
			}
			if (req.getPoSubject() != null && req.getPoSubject() != "") {
				dealFilter = dealFilter + " and dpo.subject like '%" + req.getPoSubject() + "%'";
			}
			if (req.getPoStatus() != null && req.getPoStatus() != "") {
				dealFilter = dealFilter + " and dpo.status = '" + req.getPoStatus() + "'";
			}
			if (req.getPoRequisitionNo() != null && req.getPoRequisitionNo() != "") {
				dealFilter = dealFilter + " and dpo.requisitionNo like '%" + req.getPoRequisitionNo() + "%'";
			}
			if (req.getPoTrackingNo() != null && req.getPoTrackingNo() != "") {
				dealFilter = dealFilter + " and dpo.trackingNo like '%" + req.getPoTrackingNo() + "%'";
			}
			if (req.getPoDateFrom() != null && req.getPoDateTo() != null) {
				dealFilter = dealFilter + " and dpo.purchaseOrderDate between '"
						+ Util.sdfFormatter(req.getPoDateFrom()) + "' and '" + Util.sdfFormatter(req.getPoDateTo())
						+ "'";
			}
			if (req.getPoDueDateFrom() != null && req.getPoDueDateTo() != null) {
				dealFilter = dealFilter + " and dpo.dueDate between '" + Util.sdfFormatter(req.getPoDueDateFrom())
						+ "' and '" + Util.sdfFormatter(req.getPoDueDateTo()) + "'";
			}

			/* Deals Purchase Order Filter ends */

			Query query = em.createQuery(
					"select new com.autolib.helpdesk.Sales.model.DealResponse(d , dpo) from DealPurchaseOrder dpo "
							+ "left join fetch Deal d on (dpo.dealId = d.id) where 2 > 1 " + dealFilter,
					DealResponse.class);

			purchaseOrders = query.getResultList();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealPurchaseOrders", purchaseOrders);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDealProjectImplementationsReport(DealSearchRequest req) {

		Map<String, Object> resp = new HashMap<>();
		List<DealResponse> implementations = new ArrayList<>();
		try {

			String dealFilter = "";

			/* Deals Filter starts */

			if (req.getInstitutes() != null && req.getInstitutes().size() > 0) {
				String instituteIds = "'0'";
				for (Institute inst : req.getInstitutes()) {
					instituteIds = instituteIds + ",'" + inst.getInstituteId() + "'";
				}
				dealFilter = dealFilter + " and d.institute in (" + instituteIds + ") ";
			}
			if (req.getAgents() != null && req.getAgents().size() > 0) {
				String agents = "'0'";
				for (Agent agnt : req.getAgents()) {
					agents = agents + ",'" + agnt.getEmailId() + "'";
				}
				dealFilter = dealFilter + " and d.createdBy in (" + agents + ") ";
			}

			if (req.getDealCreatedDateFrom() != null && req.getDealCreatedDateTo() != null) {
				dealFilter = dealFilter + " and d.createddatetime between '"
						+ Util.sdfFormatter(req.getDealCreatedDateFrom()) + "' and '"
						+ Util.sdfFormatter(req.getDealCreatedDateTo()) + " 23:59:59'";
			}

			if (req.getDealModifiedDateFrom() != null && req.getDealModifiedDateTo() != null) {
				dealFilter = dealFilter + " and d.lastupdatedatetime between '"
						+ Util.sdfFormatter(req.getDealModifiedDateFrom()) + "' and '"
						+ Util.sdfFormatter(req.getDealModifiedDateTo()) + " 23:59:59'";
			}

			if (req.getDealProducts() != null && req.getDealProducts().size() > 0) {
				String productIds = "0";
				for (DealProducts dp : req.getDealProducts()) {
					productIds = productIds + "," + dp.getId();
				}
				dealFilter = dealFilter + " and d.id in ( select dp.dealId from DealProducts dp where dp.productId in ("
						+ productIds + "))";
			}
			if (req.getDealType() != null && req.getDealType() != "") {
				dealFilter = dealFilter + " and d.dealType = '" + req.getDealType() + "'";
			}

			/* Deals Filter ends and Deal Project Implementations Filter starts */

			if (req.getManufacturingAgents() != null && req.getManufacturingAgents().size() > 0) {
				String agents = "'0'";
				for (Agent agnt : req.getManufacturingAgents()) {
					agents = agents + ",'" + agnt.getEmailId() + "'";
				}
				dealFilter = dealFilter + " and dpim.manufacturingAgent in (" + agents + ") ";
			}

			if (req.getDeliveryAgents() != null && req.getDeliveryAgents().size() > 0) {
				String agents = "'0'";
				for (Agent agnt : req.getDeliveryAgents()) {
					agents = agents + ",'" + agnt.getEmailId() + "'";
				}
				dealFilter = dealFilter + " and dpim.deliveryAgent in (" + agents + ") ";
			}

			if (req.getInstallationAgents() != null && req.getInstallationAgents().size() > 0) {
				String agents = "'0'";
				for (Agent agnt : req.getInstallationAgents()) {
					agents = agents + ",'" + agnt.getEmailId() + "'";
				}
				dealFilter = dealFilter + " and dpim.installedAgent in (" + agents + ") ";
			}

			if (req.getProjectImplementationStatus() != null && req.getProjectImplementationStatus() != "") {
				dealFilter = dealFilter + " and dpim.status = '" + req.getProjectImplementationStatus() + "'";
			}

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();

			LocalDate date = LocalDate.parse(dtf.format(now));

			if (req.getDate() != null || req.getDate() != "" || req.getDate() != "0" || !req.getDate().isEmpty()) {
				dealFilter = dealFilter + "AND dpim.createddatetime BETWEEN '"
						+ date.minusMonths(Integer.parseInt(req.getDate())) + "' AND '" + date + "' ";

			}

			if (req.getFromDate() != null || req.getToDate() != null) {

				SimpleDateFormat sdf1 = null;

				sdf1 = new SimpleDateFormat("yyyy-MM-dd");

				dealFilter = dealFilter + "AND dpim.createddatetime BETWEEN '"
						+ sdf1.format(req.getFromDate()).toString() + "' AND '"
						+ sdf1.format(req.getToDate()).toString() + "' ";
			}

			/* Deals Project Implementations Filter ends */

			Query query = em.createQuery(
					"select new com.autolib.helpdesk.Sales.model.DealResponse(d , dpim) from DealProjectImplementation dpim "
							+ "left join fetch Deal d on (dpim.dealId = d.id) where 2 > 1 " + dealFilter,
					DealResponse.class);

			implementations = query.getResultList();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealProjectImplementations", implementations);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDealSalesOrdersReport(DealSearchRequest req) {

		Map<String, Object> resp = new HashMap<>();
		List<DealResponse> salesOrders = new ArrayList<>();
		try {

			String dealFilter = "";

			/* Deals Filter starts */

			if (req.getInstitutes() != null && req.getInstitutes().size() > 0) {
				String instituteIds = "'0'";
				for (Institute inst : req.getInstitutes()) {
					instituteIds = instituteIds + ",'" + inst.getInstituteId() + "'";
				}
				dealFilter = dealFilter + " and d.institute in (" + instituteIds + ") ";
			}
			if (req.getAgents() != null && req.getAgents().size() > 0) {
				String agents = "'0'";
				for (Agent agnt : req.getAgents()) {
					agents = agents + ",'" + agnt.getEmailId() + "'";
				}
				dealFilter = dealFilter + " and d.createdBy in (" + agents + ") ";
			}

			if (req.getDealCreatedDateFrom() != null && req.getDealCreatedDateTo() != null) {
				dealFilter = dealFilter + " and d.createddatetime between '"
						+ Util.sdfFormatter(req.getDealCreatedDateFrom()) + "' and '"
						+ Util.sdfFormatter(req.getDealCreatedDateTo()) + " 23:59:59'";
			}

			if (req.getDealModifiedDateFrom() != null && req.getDealModifiedDateTo() != null) {
				dealFilter = dealFilter + " and d.lastupdatedatetime between '"
						+ Util.sdfFormatter(req.getDealModifiedDateFrom()) + "' and '"
						+ Util.sdfFormatter(req.getDealModifiedDateTo()) + " 23:59:59'";
			}

			if (req.getDealProducts() != null && req.getDealProducts().size() > 0) {
				String productIds = "0";
				for (DealProducts dp : req.getDealProducts()) {
					productIds = productIds + "," + dp.getId();
				}
				dealFilter = dealFilter + " and d.id in ( select dp.dealId from DealProducts dp where dp.productId in ("
						+ productIds + "))";
			}
			if (req.getDealType() != null && req.getDealType() != "") {
				dealFilter = dealFilter + " and d.dealType = '" + req.getDealType() + "'";
			}

			/* Deals Filter ends and Deal Sales Order starts */

			if (req.getSoNo() != null && req.getSoNo() != "") {
				dealFilter = dealFilter + " and dso.salesOrderNo like '%" + req.getSoNo() + "%'";
			}
			if (req.getSoSubject() != null && req.getSoSubject() != "") {
				dealFilter = dealFilter + " and dso.subject like '%" + req.getSoSubject() + "%'";
			}
			if (req.getSoDueDateFrom() != null && req.getSoDueDateTo() != null) {
				dealFilter = dealFilter + " and dso.dueDate between '" + Util.sdfFormatter(req.getSoDueDateFrom())
						+ "' and '" + Util.sdfFormatter(req.getSoDueDateTo()) + "'";
			}
			if (req.getSoStatus() != null && req.getSoStatus() != "") {
				dealFilter = dealFilter + " and dso.status = '" + req.getSoStatus() + "'";
			}

			/* Deals Sales Order Filter ends */

			Query query = em.createQuery(
					"select new com.autolib.helpdesk.Sales.model.DealResponse(d , dso) from DealSalesOrder dso "
							+ "left join fetch Deal d on (dso.dealId = d.id) where 2 > 1 " + dealFilter,
					DealResponse.class);

			salesOrders = query.getResultList();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealSalesOrders", salesOrders);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDealProformaInvoicesReport(DealSearchRequest req) {

		Map<String, Object> resp = new HashMap<>();
		List<DealResponse> proformaInvs = new ArrayList<>();
		try {

			String dealFilter = "";

			/* Deals Filter starts */

			if (req.getInstitutes() != null && req.getInstitutes().size() > 0) {
				String instituteIds = "'0'";
				for (Institute inst : req.getInstitutes()) {
					instituteIds = instituteIds + ",'" + inst.getInstituteId() + "'";
				}
				dealFilter = dealFilter + " and d.institute in (" + instituteIds + ") ";
			}
			if (req.getAgents() != null && req.getAgents().size() > 0) {
				String agents = "'0'";
				for (Agent agnt : req.getAgents()) {
					agents = agents + ",'" + agnt.getEmailId() + "'";
				}
				dealFilter = dealFilter + " and d.createdBy in (" + agents + ") ";
			}

			if (req.getDealCreatedDateFrom() != null && req.getDealCreatedDateTo() != null) {
				dealFilter = dealFilter + " and d.createddatetime between '"
						+ Util.sdfFormatter(req.getDealCreatedDateFrom()) + "' and '"
						+ Util.sdfFormatter(req.getDealCreatedDateTo()) + " 23:59:59'";
			}

			if (req.getDealModifiedDateFrom() != null && req.getDealModifiedDateTo() != null) {
				dealFilter = dealFilter + " and d.lastupdatedatetime between '"
						+ Util.sdfFormatter(req.getDealModifiedDateFrom()) + "' and '"
						+ Util.sdfFormatter(req.getDealModifiedDateTo()) + " 23:59:59'";
			}

			if (req.getDealProducts() != null && req.getDealProducts().size() > 0) {
				String productIds = "0";
				for (DealProducts dp : req.getDealProducts()) {
					productIds = productIds + "," + dp.getId();
				}
				dealFilter = dealFilter + " and d.id in ( select dp.dealId from DealProducts dp where dp.productId in ("
						+ productIds + "))";
			}
			if (req.getDealType() != null && req.getDealType() != "") {
				dealFilter = dealFilter + " and d.dealType = '" + req.getDealType() + "'";
			}

			/* Deals Filter ends and Deal Proforma Invoice starts */

			if (req.getProInvoiceNo() != null && req.getProInvoiceNo() != "") {
				dealFilter = dealFilter + " and dpi.proformaInvoiceNo like '%" + req.getProInvoiceNo() + "%'";
			}
			if (req.getProInvoiceSubject() != null && req.getProInvoiceSubject() != "") {
				dealFilter = dealFilter + " and dpi.subject like '%" + req.getProInvoiceSubject() + "%'";
			}
			if (req.getProInvoiceDateFrom() != null && req.getProInvoiceDateTo() != null) {
				dealFilter = dealFilter + " and dpi.invoiceDate between '"
						+ Util.sdfFormatter(req.getProInvoiceDateFrom()) + "' and '"
						+ Util.sdfFormatter(req.getProInvoiceDateTo()) + "'";
			}
			if (req.getProInvoiceDueDateFrom() != null && req.getProInvoiceDueDateTo() != null) {
				dealFilter = dealFilter + " and dpi.dueDate between '"
						+ Util.sdfFormatter(req.getProInvoiceDueDateFrom()) + "' and '"
						+ Util.sdfFormatter(req.getProInvoiceDueDateTo()) + "'";
			}

			/* Deals Proforma Invoice Filter ends */

			Query query = em.createQuery(
					"select new com.autolib.helpdesk.Sales.model.DealResponse(d , dpi) from DealProformaInvoice dpi "
							+ "left join fetch Deal d on (dpi.dealId = d.id) where 2 > 1 " + dealFilter,
					DealResponse.class);

			proformaInvs = query.getResultList();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealProformaInvoices", proformaInvs);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDealInvoicesReport(DealSearchRequest req) {

		Map<String, Object> resp = new HashMap<>();
		List<DealResponse> invoices = new ArrayList<>();
		try {

			String dealFilter = "";

			/* Deals Filter starts */

			if (req.getInstitutes() != null && req.getInstitutes().size() > 0) {
				String instituteIds = "'0'";
				for (Institute inst : req.getInstitutes()) {
					instituteIds = instituteIds + ",'" + inst.getInstituteId() + "'";
				}
				dealFilter = dealFilter + " and d.institute in (" + instituteIds + ") ";
			}
			if (req.getAgents() != null && req.getAgents().size() > 0) {
				String agents = "'0'";
				for (Agent agnt : req.getAgents()) {
					agents = agents + ",'" + agnt.getEmailId() + "'";
				}
				dealFilter = dealFilter + " and d.createdBy in (" + agents + ") ";
			}

			if (req.getDealCreatedDateFrom() != null && req.getDealCreatedDateTo() != null) {
				dealFilter = dealFilter + " and d.createddatetime between '"
						+ Util.sdfFormatter(req.getDealCreatedDateFrom()) + "' and '"
						+ Util.sdfFormatter(req.getDealCreatedDateTo()) + " 23:59:59'";
			}

			if (req.getDealModifiedDateFrom() != null && req.getDealModifiedDateTo() != null) {
				dealFilter = dealFilter + " and d.lastupdatedatetime between '"
						+ Util.sdfFormatter(req.getDealModifiedDateFrom()) + "' and '"
						+ Util.sdfFormatter(req.getDealModifiedDateTo()) + " 23:59:59'";
			}

			if (req.getDealProducts() != null && req.getDealProducts().size() > 0) {
				String productIds = "0";
				for (DealProducts dp : req.getDealProducts()) {
					productIds = productIds + "," + dp.getId();
				}
				dealFilter = dealFilter + " and d.id in ( select dp.dealId from DealProducts dp where dp.productId in ("
						+ productIds + "))";
			}
			if (req.getDealType() != null && req.getDealType() != "") {
				dealFilter = dealFilter + " and d.dealType = '" + req.getDealType() + "'";
			}

			/* Deals Filter ends and Deal Invoice starts */

			if (req.getInvoiceNo() != null && req.getInvoiceNo() != "") {
				dealFilter = dealFilter + " and di.invoiceNo like '%" + req.getInvoiceNo() + "%'";
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

			/* Deal Invoice ends */

			Query query = em.createQuery(
					"select new com.autolib.helpdesk.Sales.model.DealResponse(d , di) from DealInvoice di left join fetch Deal d on (di.dealId = d.id) where 2 > 1 "
							+ dealFilter,
					DealResponse.class);

			invoices = query.getResultList();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealInvoices", invoices);
		return resp;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDealDeliveryChallansReport(DealSearchRequest req) {

		Map<String, Object> resp = new HashMap<>();
		List<DealResponse> dcs = new ArrayList<>();
		try {

			String dealFilter = "";

			/* Deals Filter starts */

			if (req.getInstitutes() != null && req.getInstitutes().size() > 0) {
				String instituteIds = "'0'";
				for (Institute inst : req.getInstitutes()) {
					instituteIds = instituteIds + ",'" + inst.getInstituteId() + "'";
				}
				dealFilter = dealFilter + " and d.institute in (" + instituteIds + ") ";
			}
			if (req.getAgents() != null && req.getAgents().size() > 0) {
				String agents = "'0'";
				for (Agent agnt : req.getAgents()) {
					agents = agents + ",'" + agnt.getEmailId() + "'";
				}
				dealFilter = dealFilter + " and d.createdBy in (" + agents + ") ";
			}

			if (req.getDealCreatedDateFrom() != null && req.getDealCreatedDateTo() != null) {
				dealFilter = dealFilter + " and d.createddatetime between '"
						+ Util.sdfFormatter(req.getDealCreatedDateFrom()) + "' and '"
						+ Util.sdfFormatter(req.getDealCreatedDateTo()) + " 23:59:59'";
			}

			if (req.getDealModifiedDateFrom() != null && req.getDealModifiedDateTo() != null) {
				dealFilter = dealFilter + " and d.lastupdatedatetime between '"
						+ Util.sdfFormatter(req.getDealModifiedDateFrom()) + "' and '"
						+ Util.sdfFormatter(req.getDealModifiedDateTo()) + " 23:59:59'";
			}

			if (req.getDealProducts() != null && req.getDealProducts().size() > 0) {
				String productIds = "0";
				for (DealProducts dp : req.getDealProducts()) {
					productIds = productIds + "," + dp.getId();
				}
				dealFilter = dealFilter
						+ " and dc.id in ( select dc.dealId from DealDCProducts dcp where dcp.productId in ("
						+ productIds + "))";
			}
			if (req.getDealType() != null && req.getDealType() != "") {
				dealFilter = dealFilter + " and d.dealType = '" + req.getDealType() + "'";
			}

			Query query = em.createQuery(
					"select new com.autolib.helpdesk.Sales.model.DealResponse(d , dc) from DealDeliveryChallan dc "
							+ "left join fetch Deal d on (dc.dealId = d.id) where 2 > 1 " + dealFilter,
					DealResponse.class);

			dcs = query.getResultList();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealDeliveryChallans", dcs);
		return resp;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDealPaymentsReport(DealSearchRequest req) {

		Map<String, Object> resp = new HashMap<>();
		List<DealResponse> payments = new ArrayList<>();
		try {

			String dealFilter = "";

			/* Deals Filter starts */

			if (req.getInstitutes() != null && req.getInstitutes().size() > 0) {
				String instituteIds = "'0'";
				for (Institute inst : req.getInstitutes()) {
					instituteIds = instituteIds + ",'" + inst.getInstituteId() + "'";
				}
				dealFilter = dealFilter + " and d.institute in (" + instituteIds + ") ";
			}
			if (req.getAgents() != null && req.getAgents().size() > 0) {
				String agents = "'0'";
				for (Agent agnt : req.getAgents()) {
					agents = agents + ",'" + agnt.getEmailId() + "'";
				}
				dealFilter = dealFilter + " and d.createdBy in (" + agents + ") ";
			}

			if (req.getDealCreatedDateFrom() != null && req.getDealCreatedDateTo() != null) {
				dealFilter = dealFilter + " and d.createddatetime between '"
						+ Util.sdfFormatter(req.getDealCreatedDateFrom()) + "' and '"
						+ Util.sdfFormatter(req.getDealCreatedDateTo()) + " 23:59:59'";
			}

			if (req.getDealModifiedDateFrom() != null && req.getDealModifiedDateTo() != null) {
				dealFilter = dealFilter + " and d.lastupdatedatetime between '"
						+ Util.sdfFormatter(req.getDealModifiedDateFrom()) + "' and '"
						+ Util.sdfFormatter(req.getDealModifiedDateTo()) + " 23:59:59'";
			}

			if (req.getDealProducts() != null && req.getDealProducts().size() > 0) {
				String productIds = "0";
				for (DealProducts dp : req.getDealProducts()) {
					productIds = productIds + "," + dp.getId();
				}
				dealFilter = dealFilter + " and d.id in ( select dp.dealId from DealProducts dp where dp.productId in ("
						+ productIds + "))";
			}
			if (req.getDealType() != null && req.getDealType() != "") {
				dealFilter = dealFilter + " and d.dealType = '" + req.getDealType() + "'";
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
					"select new com.autolib.helpdesk.Sales.model.DealResponse(d , dp) from DealPayments dp "
							+ "left join fetch Deal d on (dp.dealId = d.id) where 2 > 1 " + dealFilter,
					DealResponse.class);

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
	public Map<String, Object> saveDealEmail(DealEmail email) {
		Map<String, Object> resp = new HashMap<>();
		try {

			email = dealEmailRepo.save(email);
			resp.putAll(Util.SuccessResponse());
		} catch (Exception Ex) {
			logger.error(Ex.getMessage());
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		resp.put("DealEmail", email);
		return resp;
	}

	@Override
	public Map<String, Object> addDealEmailAttachment(int dealEmailId, MultipartFile file) {

		Map<String, Object> resp = new HashMap<>();

		DealEmail dealEmail = dealEmailRepo.findById(dealEmailId);
		DealEmailAttachments attach = new DealEmailAttachments();

		File directory = new File(contentPath + "/Deals/" + dealEmail.getDealId() + "/Emails/" + dealEmail.getId());

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
			attach.setEmailId(dealEmailId);
			attach.setDealId(dealEmail.getDealId());
			attach = dealEmailAttRepo.save(attach);

			resp.putAll(Util.SuccessResponse());
		} catch (IOException e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealEmailAttachment", dealEmail);
		return resp;

	}

	@Override
	public Map<String, Object> sendDealEmail(DealEmail req) {
		System.out.println(req);
		Map<String, Object> resp = new HashMap<>();
		try {

			req = dealEmailRepo.findById(req.getId());

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
			File directory = null;

			System.out.println("req.getTab():::" + req.getTab());

			if (req.getTab().equals("LETTERPAD")) {
				directory = new File(contentPath + "/Letterpads/" + req.getFilename().replaceAll(".pdf", "") + "/"
						+ req.getFilename());
			} else {
				directory = new File(contentPath + "/Deals/" + req.getDealId() + "/" + req.getFilename());
			}
			System.out.println(directory.getAbsolutePath());

			emailModel.setContent_path(directory.getAbsolutePath());

			final List<DealEmailAttachments> attachments = dealEmailAttRepo.findByEmailId(req.getId());

			List<String> attachs = new ArrayList<>();
			for (DealEmailAttachments attach : attachments) {
				directory = new File(contentPath + "/Deals/" + req.getDealId() + "/Emails/" + req.getId() + "/"
						+ attach.getFilename());

				attachs.add(directory.getAbsolutePath());
			}

			emailModel.setContentPaths(attachs);

			emailModel.setSenderConf("Sales");
			int i = emailSender.sendmail(emailModel);

			if (i == 1) {
				Query query = em.createQuery("Update DealEmail set sent = 1 where id = :id");
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
	public Map<String, Object> getDealEmail(DealEmail req) {
		Map<String, Object> resp = new HashMap<>();
		List<DealEmail> dealEmails = new ArrayList<>();
		List<DealEmailAttachments> dealEmailAttachments = new ArrayList<>();

		try {
			dealEmails = dealEmailRepo.findByDealId(req.getDealId());

			dealEmailAttachments = dealEmailAttRepo.findByDealId(req.getDealId());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception Ex) {
			logger.error(Ex.getMessage());
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		resp.put("DealEmails", dealEmails);
		resp.put("DealEmailAttachments", dealEmailAttachments);
		return resp;
	}

	public Map<String, Object> getAmcPayment(int id) {
		Map<String, Object> resp = new HashMap<>();
		DealPayments payments = new DealPayments();
		Deal deal = new Deal();
		List<DealProducts> dealProducts = new ArrayList<>();
		try {
			payments = paymentRepo.findById(id);

			deal = dealRepo.findById(payments.getDealId());

			dealProducts = dealProductsRepo.findByDealId(payments.getDealId());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealPayment", payments);
		resp.put("Deal", deal);
		resp.put("DealProducts", dealProducts);

		return resp;
	}

	@Override
	public Map<String, Object> saveDealProjectImplementation(DealProjectImplementation dpim) {
		Map<String, Object> resp = new HashMap<>();
		try {
			String mailAction = dpim.getMailAction();

			dpim = dpimRepo.save(dpim);

			if (mailAction != null && !mailAction.isEmpty()) {
				dpim.setMailAction(mailAction);
				sendDealProjectImplementationMailAction(dpim);
			}
			resp.putAll(Util.SuccessResponse());

		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		resp.put("DealProjectImplementation", dpim);
		return resp;
	}

	@Override
	public Map<String, Object> getDealProjectImplementation(int dealId) {
		Map<String, Object> resp = new HashMap<>();
		DealProjectImplementation dpim = new DealProjectImplementation();
		try {

			dpim = dpimRepo.findByDealId(dealId);

			resp.putAll(Util.SuccessResponse());

		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		resp.put("DealProjectImplementation", dpim);
		return resp;
	}

	@Override
	public Map<String, Object> getDealProjectImplementations(ProjectImplemantationRequest projectReq) {
		Map<String, Object> resp = new HashMap<>();
		List<DealProjectImplementation> dpims = new ArrayList<>();

		List<DealResponse> dealResponses = new ArrayList<>();

		try {

			dpims = dpimRepo.findMyAssignedProjectImplementations(projectReq.getMailId(), projectReq.getFromDate(),
					projectReq.getToDate());

			List<Integer> dealIds = dpims.stream().map(DealProjectImplementation::getDealId)
					.collect(Collectors.toList());

			final List<Deal> deals = dealRepo.findAllById(dealIds);

			List<DealProducts> dealProducts = dealProductsRepo.findAllByDealIdIn(dealIds);

			dpims.parallelStream().forEach(dpim -> {

				Optional<Deal> matchingObject = deals.stream().filter(p -> p.getId() == dpim.getDealId()).findFirst();

				List<DealProducts> dps = dealProducts.stream().filter(dp -> dp.getDealId() == dpim.getDealId())
						.collect(Collectors.toList());

				dealResponses.add(new DealResponse(matchingObject.orElse(null), dpim, dps));

			});

			resp.putAll(Util.SuccessResponse());

		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		resp.put("DealProjectImplementations", dealResponses);
		return resp;
	}

	@Override
	public Map<String, Object> saveDealProjectImplementationComments(DealProjectImplementationComments comment) {
		Map<String, Object> resp = new HashMap<>();

		try {
			comment = dpimCommentRepo.save(comment);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("Comment", comment);
//		resp.put("CommentAttachments", request.getAttachments());
		return resp;
	}

	@Override
	public Map<String, Object> deleteDealProjectImplementationComments(DealProjectImplementationComments comment) {
		Map<String, Object> resp = new HashMap<>();

		try {

			dpimCommentRepo.delete(comment);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public Map<String, Object> getAllDealProjectImplementationComments(DealProjectImplementationComments comment) {
		Map<String, Object> resp = new HashMap<>();
		List<DealProjectImplementationComments> comments = new ArrayList<>();
		try {

			comments = dpimCommentRepo.findByDealId(comment.getDealId());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealProjectImplementationComments", comments);
		return resp;
	}

	void sendDealProjectImplementationMailAction(DealProjectImplementation dpim) {
		System.out.println("sendDealProjectImplementationMailAction ::" + dpim.toString());
		try {
			Deal deal = dealRepo.findById(dpim.getDealId());
			List<DealProducts> dps = dealProductsRepo.findByDealId(dpim.getDealId());

			System.out.println(deal);
			System.out.println(deal);
			System.out.println(dps);
			System.out.println(dpim.getMailAction());

			String agenturl = agentWebURL + "project-implementations?expand=" + dpim.getId();
			String adminurl = agentWebURL + "sales/deals/overview/" + dpim.getDealId() + "/projectimplementation";

			EmailModel emailModel = new EmailModel("Sales");
			if (dpim.getMailAction().equalsIgnoreCase("assignManufacturingToAgent")) {

				// Agent Mail
				emailModel.setMailTo(dpim.getManufacturingAgent());
				emailModel.setMailFrom(dpim.getManufacturingAssignedBy());

				emailModel.setMailSub("Manufacturing Assigned to You - " + deal.getInstitute().getInstituteName());

				StringBuilder sb = new StringBuilder();
				sb.append("Hi,<br><br>");
				sb.append("Following Products/Materials are Assigned to you for Manufacturing.<br>");
				sb.append("<h3>" + deal.getInstitute().getInstituteName() + "</h3>");
				sb.append("<small>" + deal.getInstitute().getFullAddress() + "</small>");
				sb.append("<h4><u>Products</u></h4>");

				dps.forEach(dp -> {
					sb.append("<p>" + dp.getName() + " (Qty : " + dp.getQuantity() + ")</p>");
				});

				sb.append("<p>Assigned To : " + dpim.getManufacturingAgent() + "</p>");
				sb.append("<p>Assigned By : " + dpim.getManufacturingAssignedBy() + "</p>");

				sb.append("<br><a href='" + agenturl + "'>" + agenturl + "</small>");

				emailModel.setMailText(sb.toString());

			} else if (dpim.getMailAction().equalsIgnoreCase("assignDeliveryToAgent")) {

				emailModel.setMailTo(dpim.getDeliveryAgent());
				emailModel.setMailFrom(dpim.getDeliveryAssignedBy());
				emailModel.setMailSub("Delivery Assigned to You - " + deal.getInstitute().getInstituteName());

				StringBuilder sb = new StringBuilder();
				sb.append("Hi,<br><br>");
				sb.append("Following Products/Materials are Assigned to you for Delivery.<br>");
				sb.append("<h3>" + deal.getInstitute().getInstituteName() + "</h3>");
				sb.append("<small>" + deal.getInstitute().getFullAddress() + "</small>");
				sb.append("<h4><u>Products</u></h4>");

				dps.forEach(dp -> {
					sb.append("<p>" + dp.getName() + " (Qty : " + dp.getQuantity() + ")</p>");
				});

				sb.append("<p>Assigned To : " + dpim.getDeliveryAgent() + "</p>");
				sb.append("<p>Assigned By : " + dpim.getDeliveryAssignedBy() + "</p>");

				sb.append("<br><a href='" + agenturl + "'>" + agenturl + "</small>");

				emailModel.setMailText(sb.toString());
			} else if (dpim.getMailAction().equalsIgnoreCase("assignInstallationToAgent")) {

				emailModel.setMailTo(dpim.getInstalledAgent());
				emailModel.setMailFrom(dpim.getInstalledAssignedBy());
				emailModel.setMailSub("Installation Assigned to You - " + deal.getInstitute().getInstituteName());

				StringBuilder sb = new StringBuilder();
				sb.append("Hi,<br><br>");
				sb.append("Following Products/Materials are Assigned to you for Installation.<br>");
				sb.append("<h3>" + deal.getInstitute().getInstituteName() + "</h3>");
				sb.append("<small>" + deal.getInstitute().getFullAddress() + "</small>");
				sb.append("<h4><u>Products</u></h4>");

				dps.forEach(dp -> {
					sb.append("<p>" + dp.getName() + " (Qty : " + dp.getQuantity() + ")</p>");
				});

				sb.append("<p>Assigned To : " + dpim.getInstalledAgent() + "</p>");
				sb.append("<p>Assigned By : " + dpim.getInstalledAssignedBy() + "</p>");

				sb.append("<br><a href='" + agenturl + "'>" + agenturl + "</small>");

				emailModel.setMailText(sb.toString());
			} else if (dpim.getMailAction().equalsIgnoreCase("markasManufacturingCompleted")) {

				emailModel.setMailTo(dpim.getManufacturingAssignedBy());
				emailModel.setMailFrom(dpim.getManufacturingFinishedBy());
				emailModel.setMailSub("Manufacturing Marked As Completed , Needs Approval - "
						+ deal.getInstitute().getInstituteName());

				StringBuilder sb = new StringBuilder();
				sb.append("Hi,<br><br>");
				sb.append("Following Products/Materials Manufacturing marked as Completed , Needs Approval.<br>");
				sb.append("<h3>" + deal.getInstitute().getInstituteName() + "</h3>");
				sb.append("<small>" + deal.getInstitute().getFullAddress() + "</small>");
				sb.append("<h4><u>Products</u></h4>");

				dps.forEach(dp -> {
					sb.append("<p>" + dp.getName() + " (Qty : " + dp.getQuantity() + ")</p>");
				});

				sb.append("<p>Assigned To : " + dpim.getManufacturingAgent() + "</p>");
				sb.append("<p>Assigned By : " + dpim.getManufacturingAssignedBy() + "</p>");

				sb.append("<br><a href='" + adminurl + "'>" + adminurl + "</small>");

				emailModel.setMailText(sb.toString());
			} else if (dpim.getMailAction().equalsIgnoreCase("markasDeliveryCompleted")) {

				emailModel.setMailTo(dpim.getDeliveryAssignedBy());
				emailModel.setMailFrom(dpim.getDeliveryFinishedBy());
				emailModel.setMailSub(
						"Delivery Marked As Completed, Needs Approval - " + deal.getInstitute().getInstituteName());

				StringBuilder sb = new StringBuilder();
				sb.append("Hi,<br><br>");
				sb.append("Following Products/Materials Delivery marked as Completed , Needs Approval.<br>");
				sb.append("<h3>" + deal.getInstitute().getInstituteName() + "</h3>");
				sb.append("<small>" + deal.getInstitute().getFullAddress() + "</small>");
				sb.append("<h4><u>Products</u></h4>");

				dps.forEach(dp -> {
					sb.append("<p>" + dp.getName() + " (Qty : " + dp.getQuantity() + ")</p>");
				});

				sb.append("<p>Assigned To : " + dpim.getDeliveryAgent() + "</p>");
				sb.append("<p>Assigned By : " + dpim.getDeliveryAssignedBy() + "</p>");

				sb.append("<br><a href='" + adminurl + "'>" + adminurl + "</small>");

				emailModel.setMailText(sb.toString());
			} else if (dpim.getMailAction().equalsIgnoreCase("markasInstallationCompleted")) {

				emailModel.setMailTo(dpim.getInstalledAgent());
				emailModel.setMailFrom(dpim.getInstalledFinishedBy());
				emailModel.setMailSub("Installation Marked As Completed , Needs Approval - "
						+ deal.getInstitute().getInstituteName());

				StringBuilder sb = new StringBuilder();
				sb.append("Hi,<br><br>");
				sb.append("Following Products/Materials Installation marked as Completed , Needs Approval.<br>");
				sb.append("<h3>" + deal.getInstitute().getInstituteName() + "</h3>");
				sb.append("<small>" + deal.getInstitute().getFullAddress() + "</small>");
				sb.append("<h4><u>Products</u></h4>");

				dps.forEach(dp -> {
					sb.append("<p>" + dp.getName() + " (Qty : " + dp.getQuantity() + ")</p>");
				});

				sb.append("<p>Assigned To : " + dpim.getInstalledAgent() + "</p>");
				sb.append("<p>Assigned By : " + dpim.getInstalledAssignedBy() + "</p>");

				sb.append("<br><a href='" + adminurl + "'>" + adminurl + "</small>");

				emailModel.setMailText(sb.toString());
			} else if (dpim.getMailAction().equalsIgnoreCase("approveManufacturing")) {

				emailModel.setMailTo(dpim.getManufacturingAgent());
				emailModel.setMailFrom(dpim.getManufacturingAssignedBy());
				emailModel.setMailSub("Manufacturing Approved - " + deal.getInstitute().getInstituteName());

				StringBuilder sb = new StringBuilder();
				sb.append("Hi,<br><br>");
				sb.append("Following Products/Materials Manufacturing Approved .<br>");
				sb.append("<h3>" + deal.getInstitute().getInstituteName() + "</h3>");
				sb.append("<small>" + deal.getInstitute().getFullAddress() + "</small>");
				sb.append("<h4><u>Products</u></h4>");

				dps.forEach(dp -> {
					sb.append("<p>" + dp.getName() + " (Qty : " + dp.getQuantity() + ")</p>");
				});

				sb.append("<p>Assigned To : " + dpim.getManufacturingAgent() + "</p>");
				sb.append("<p>Assigned By : " + dpim.getManufacturingAssignedBy() + "</p>");

				sb.append("<br><a href='" + agenturl + "'>" + agenturl + "</small>");

				emailModel.setMailText(sb.toString());
			} else if (dpim.getMailAction().equalsIgnoreCase("approveDelivery")) {

				emailModel.setMailTo(dpim.getDeliveryAgent());
				emailModel.setMailFrom(dpim.getDeliveryAssignedBy());
				emailModel.setMailSub("Delivery Approved - " + deal.getInstitute().getInstituteName());

				StringBuilder sb = new StringBuilder();
				sb.append("Hi,<br><br>");
				sb.append("Following Products/Materials Delivery Approved .<br>");
				sb.append("<h3>" + deal.getInstitute().getInstituteName() + "</h3>");
				sb.append("<small>" + deal.getInstitute().getFullAddress() + "</small>");
				sb.append("<h4><u>Products</u></h4>");

				dps.forEach(dp -> {
					sb.append("<p>" + dp.getName() + " (Qty : " + dp.getQuantity() + ")</p>");
				});

				sb.append("<p>Assigned To : " + dpim.getDeliveryAgent() + "</p>");
				sb.append("<p>Assigned By : " + dpim.getDeliveryAssignedBy() + "</p>");

				sb.append("<br><a href='" + agenturl + "'>" + agenturl + "</small>");

				emailModel.setMailText(sb.toString());
			} else if (dpim.getMailAction().equalsIgnoreCase("approveInstalled")) {

				emailModel.setMailTo(dpim.getInstalledAgent());
				emailModel.setMailFrom(dpim.getInstalledAssignedBy());
				emailModel.setMailSub("Installation Approved - " + deal.getInstitute().getInstituteName());

				StringBuilder sb = new StringBuilder();
				sb.append("Hi,<br><br>");
				sb.append("Following Products/Materials Installation Approved .<br>");
				sb.append("<h3>" + deal.getInstitute().getInstituteName() + "</h3>");
				sb.append("<small>" + deal.getInstitute().getFullAddress() + "</small>");
				sb.append("<h4><u>Products</u></h4>");

				dps.forEach(dp -> {
					sb.append("<p>" + dp.getName() + " (Qty : " + dp.getQuantity() + ")</p>");
				});

				sb.append("<p>Assigned To : " + dpim.getInstalledAgent() + "</p>");
				sb.append("<p>Assigned By : " + dpim.getInstalledAssignedBy() + "</p>");

				sb.append("<br><a href='" + agenturl + "'>" + agenturl + "</small>");

				emailModel.setMailText(sb.toString());
			}
			emailModel.setSenderConf("Sales");
			emailSender.sendmail(emailModel);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> generateDealInstamojoPaymentURL(Deal deal) {
		Map<String, Object> resp = new HashMap<>();
		List<DealProjectImplementationComments> comments = new ArrayList<>();
//		Map<String, Object> payload = new HashMap<>();
		try {
			{

//				restTemp.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

//				if (channelName.equalsIgnoreCase("WEB"))
//					payload.put("redirect_url", GlobalAccessUtil.InstamojoRedirectWEBURL);
//				else
//					payload.put("redirect_url", GlobalAccessUtil.InstamojoRedirectMOBILEURL);

//				MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
//				headers.add("X-Api-Key", GlobalAccessUtil.InstamojoApiKey);
//				headers.add("X-Auth-Token", GlobalAccessUtil.InstamojoAuthToken);
//				headers.add("Content-Type", "application/json");
//				
//				HttpEntity<Object> request = new HttpEntity<Object>(payload, GlobalAccessUtil.InstamojoHeader());
//				System.out.println(request);
//
//				resp = restTemp.postForObject("https://www.instamojo.com/api/1.1/payment-requests/", request, Map.class);
//				System.out.println(resp);
//
//				if (Boolean.parseBoolean(resp.get("success").toString()) == true) {
//					Map<String, Object> payment_request = (Map<String, Object>) resp.get("payment_request");
//					System.out.println(payment_request);
//					Object[] payParam = { payment_request.get("id"), payment_request.get("buyer_name"),
//							payment_request.get("status"), payload.get("store_id"), "" };
//					jdbcTemp.update(DBQueryUtil.Insert_Payment, payParam);
			}

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealProjectImplementationComments", comments);
		return resp;
	}

	@Override
	public Map<String, Object> generateSalesOrderPDF3(DealRequest dealReq) {
		Map<String, Object> resp = new HashMap<>();

		try {

			System.out.println("Inside generateSalesOrderPDF DaoImpl:::::::::" + dealReq.getSalesOrder().getId());
			// DecimalFormat decformatter = new DecimalFormat("###,##0.00");
			// SimpleDateFormat Util.sdfFormatter = new SimpleDateFormat("dd/MM/yyyy");

			dealReq.setSalesOrder(soRepo.findById(dealReq.getSalesOrder().getId()));

			dealReq.setDealQuotation(quoteRepo.findByDealId(dealReq.getSalesOrder().getDealId()));

			dealReq.setDeal(dealRepo.findById(dealReq.getSalesOrder().getDealId()));

			dealReq.setDealProducts(dealProductsRepo.findByDealId(dealReq.getSalesOrder().getDealId()));

			InputStream stream = null;

			if (dealReq.getDeal().getGstType().equals("IGST")) {
				stream = this.getClass().getResourceAsStream("/reports/Global/Deal_Template_IGST.jrxml");
			} else {
				stream = this.getClass().getResourceAsStream("/reports/Global/Deal_Template_CGST_SGST.jrxml");
			}

			final Map<String, Object> parameters = new HashMap<>();
			InfoDetails info = infoDetailRepo.findById(1);
			Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddressHTML2());
			parameters.put("cmp_logo_url", info.getLogoAsFile());
			parameters.put("dealtype_label", "Sales Order");

			parameters.put("is_discount_applied", dealReq.isDiscountApplied());
			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", dealReq.getDesignation());
			parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
			parameters.put("online_payment_url", info.getInstamojoPaymentURL());

			parameters.put("terms", dealReq.getSalesOrder().getTerms().replaceAll("\n", "<br>"));
			parameters.put("vailid_until", Util.sdfFormatter(dealReq.getSalesOrder().getDueDate()));
			parameters.put("quote_no", String.valueOf(dealReq.getDealQuotation().getQuoteNo()));

			parameters.put("deal_amount_label", "Grand Total :");
			parameters.put("deal_amount_text", "Rs." + Util.decimalFormatter(dealReq.getDeal().getGrandTotal()));
			parameters.put("sales_order_no", dealReq.getSalesOrder().getSalesOrderNo());
			parameters.put("purchase_order_no", dealReq.getSalesOrder().getPurchaseOrderNo());

			parameters.put("billing_to", dealReq.getBillingToAddress());
			parameters.put("shipping_to", dealReq.getShippingToAddress());

			String price_summary_label = "", price_summary_text = "";

			String deal_date_label = "", deal_date_text = "";

			deal_date_label = deal_date_label + "Sales Order No : <br>";
			deal_date_text = deal_date_text + String.valueOf(dealReq.getSalesOrder().getSalesOrderNo()) + "<br>";
			deal_date_label = deal_date_label + "Purchase Order No : <br>";
			deal_date_text = deal_date_text + String.valueOf(dealReq.getSalesOrder().getPurchaseOrderNo()) + "<br>";
			deal_date_label = deal_date_label + "Quote Number  : <br>";
			deal_date_text = deal_date_text + String.valueOf(dealReq.getDealQuotation().getQuoteNo()) + "<br>";
			deal_date_label = deal_date_label + "Due Date  : <br>";
			deal_date_text = deal_date_text + String.valueOf(Util.sdfFormatter(dealReq.getSalesOrder().getDueDate()))
					+ "<br>";

			price_summary_label = price_summary_label + "Original Rate : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getSubTotal())
					+ "<br>";

			if (dealReq.getDeal().getDiscount() > 0) {
				price_summary_label = price_summary_label + "Discount : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getDiscount())
						+ "<br>";
			}

			price_summary_label = price_summary_label + "Taxable Amount : <br>";
			price_summary_text = price_summary_text + "Rs."
					+ Util.decimalFormatter(dealReq.getDeal().getSubTotal() - dealReq.getDeal().getDiscount()) + "<br>";

			if (dealReq.getDeal().getGstType().equalsIgnoreCase("IGST")) {
				price_summary_label = price_summary_label + "IGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax())
						+ "<br>";
			} else {
				price_summary_label = price_summary_label + "CGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax() / 2)
						+ "<br>";

				price_summary_label = price_summary_label + "SGST : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter((dealReq.getDeal().getTax() / 2)) + "<br>";
			}

			if (dealReq.getDeal().getAdjustment() != 0.00) {
				price_summary_label = price_summary_label + "Adjustment : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ String.format("%.2f", dealReq.getDeal().getAdjustment()) + "<br>";
			}

			parameters.put("price_summary_label", price_summary_label);
			parameters.put("price_summary_text", price_summary_text);

			parameters.put("deal_date_label", deal_date_label);
			parameters.put("deal_date_text", deal_date_text);

			parameters.put("amount_in_words", Util.EnglishNumberToWords(dealReq.getDeal().getGrandTotal()));
			List<Map<String, String>> datasource = new ArrayList<>();

			dealReq.getDealProducts().forEach(prod -> {
				Map<String, String> data = new HashMap<>();

				data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
				data.put("quantity", prod.getQuantityAsHTMLText());
				data.put("price", Util.decimalFormatter(prod.getRateAmount()));
				data.put("rate", Util.decimalFormatter(prod.getPrice()));
				data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
				if (dealReq.getDeal().getGstType().equals("IGST")) {
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

			File directory = new File(contentPath + "/Deals/" + dealReq.getDeal().getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}

			dealReq.getSalesOrder()
					.setFilename(dealReq.getSalesOrder().getSalesOrderNo().replaceAll("/", "-") + ".pdf");
			final String filePath = directory.getAbsolutePath() + "/" + dealReq.getSalesOrder().getFilename();
			System.out.println(filePath);
			soRepo.save(dealReq.getSalesOrder());

			// Export the report to a PDF file.
			JasperExportManager.exportReportToPdfFile(print, filePath);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealSalesOrder", dealReq.getSalesOrder());
		return resp;

	}

	@Override
	public Map<String, Object> generateDeliveryChallanPDF1(DealRequest dealReq) {

		Map<String, Object> resp = new HashMap<>();

		try {
			// DecimalFormat decformatter = new DecimalFormat("###,##0.00");
			// SimpleDateFormat Util.sdfFormatter = new SimpleDateFormat("dd/MM/yyyy");

			dealReq.setDealProformaInvoice(proInvRepo.findById(dealReq.getDealProformaInvoice().getId()));

			dealReq.setDeal(dealRepo.findById(dealReq.getDealProformaInvoice().getDealId()));

			dealReq.setDealProducts(dealProductsRepo.findByDealId(dealReq.getDealProformaInvoice().getDealId()));

			System.out.println(dealReq.toString());

			InputStream stream = null;

			stream = this.getClass().getResourceAsStream("/reports/Delivery_Challan/DC_Template_1.jrxml");

			final Map<String, Object> parameters = new HashMap<>();

			InfoDetails info = infoDetailRepo.findById(1);
			Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddressHTML1());
			parameters.put("cmp_logo_url", info.getLogoAsFile());
			parameters.put("dealtype_label", "Delivery Challan");

			parameters.put("is_discount_applied", dealReq.isDiscountApplied());
			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", dealReq.getDesignation());
			parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
			parameters.put("online_payment_url", info.getInstamojoPaymentURL());

			parameters.put("terms", dealReq.getDealProformaInvoice().getTerms().replaceAll("\n", "<br>"));
//			parameters.put("invoice_date", Util.sdfFormatter(dealReq.getDealProformaInvoice().getInvoiceDate()));
//			parameters.put("due_date", Util.sdfFormatter(dealReq.getDealProformaInvoice().getDueDate()));
//			parameters.put("invoice_no", String.valueOf(dealReq.getDealProformaInvoice().getProformaInvoiceNo()));
//			parameters.put("sales_order_no", String.valueOf(dealReq.getDealProformaInvoice().getSalesOrderNo()));
//			parameters.put("purchase_order_no", String.valueOf(dealReq.getDealProformaInvoice().getPurchaseOrderNo()));
			parameters.put("deal_amount_label", "Balance Amount :");
			parameters.put("deal_amount_text",
					"Rs." + Util.decimalFormatter(
							(dealReq.getDeal().getGrandTotal() - dealReq.getDealProformaInvoice().getPaidAmount())
									+ dealReq.getDealProformaInvoice().getShippingCost()));
			parameters.put("amount_in_words",
					Util.EnglishNumberToWords(
							(dealReq.getDeal().getGrandTotal() - dealReq.getDealProformaInvoice().getPaidAmount())
									+ dealReq.getDealProformaInvoice().getShippingCost()));

			if (dealReq.getDeal().getDealType().equalsIgnoreCase("AMC") && dealReq.getDeal().getAmcFromDate() != null
					&& dealReq.getDeal().getAmcToDate() != null) {
				String subject = "Sub: " + dealReq.getDealProformaInvoice().getSubject()
						+ " <br>AMC Charges for the following items for the period of "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcFromDate(), "dd/MM/yyyy") + " to "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcToDate(), "dd/MM/yyyy");
				parameters.put("subject", subject);
			} else {
				parameters.put("subject", "Sub: " + dealReq.getDealProformaInvoice().getSubject());
			}

			parameters.put("billing_to", dealReq.getBillingToAddress());
			parameters.put("shipping_to", dealReq.getShippingToAddress());

			String price_summary_label = "", price_summary_text = "";

			String deal_date_label = "", deal_date_text = "";

			deal_date_label = deal_date_label + "Invoice No : <br>";
			deal_date_text = deal_date_text + String.valueOf(dealReq.getDealInvoice().getInvoiceNo()) + "<br>";
			deal_date_label = deal_date_label + "Invoice Date : <br>";
			deal_date_text = deal_date_text
					+ String.valueOf(Util.sdfFormatter(dealReq.getDealProformaInvoice().getInvoiceDate())) + "<br>";
			deal_date_label = deal_date_label + "PO No : <br>";
			deal_date_text = deal_date_text + String.valueOf(dealReq.getDealProformaInvoice().getPurchaseOrderNo())
					+ "<br>";

			price_summary_label = price_summary_label + "Original Rate : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getSubTotal())
					+ "<br>";

			if (dealReq.getDeal().getDiscount() > 0) {
				price_summary_label = price_summary_label + "Discount : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getDiscount())
						+ "<br>";
			}

			price_summary_label = price_summary_label + "Taxable Amount : <br>";
			price_summary_text = price_summary_text + "Rs."
					+ Util.decimalFormatter(dealReq.getDeal().getSubTotal() - dealReq.getDeal().getDiscount()) + "<br>";

			if (dealReq.getDeal().getGstType().equalsIgnoreCase("IGST")) {
				price_summary_label = price_summary_label + "IGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax())
						+ "<br>";
			} else {
				price_summary_label = price_summary_label + "CGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax() / 2)
						+ "<br>";

				price_summary_label = price_summary_label + "SGST : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter((dealReq.getDeal().getTax() / 2)) + "<br>";
			}

			if (dealReq.getDeal().getAdjustment() != 0.00) {
				price_summary_label = price_summary_label + "Adjustment : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ String.format("%.2f", dealReq.getDeal().getAdjustment()) + "<br>";
			}

			price_summary_label = price_summary_label + "Grand Total : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getGrandTotal())
					+ "<br>";

			price_summary_label = price_summary_label + "Paid Amount : <br>";
			price_summary_text = price_summary_text + ""
					+ Util.decimalFormatter(dealReq.getDealProformaInvoice().getPaidAmount()) + "<br>";

			if (dealReq.getDealProformaInvoice().getShippingCost() > 0) {
				price_summary_label = price_summary_label + "Shipment Amount : <br>";
				price_summary_text = price_summary_text + ""
						+ Util.decimalFormatter(dealReq.getDealProformaInvoice().getShippingCost()) + "<br>";
			}

			parameters.put("price_summary_label", price_summary_label);
			parameters.put("price_summary_text", price_summary_text);

			parameters.put("deal_date_label", deal_date_label);
			parameters.put("deal_date_text", deal_date_text);

			System.out.println(parameters.toString());

			List<Map<String, String>> datasource = new ArrayList<>();

			dealReq.getDealProducts().forEach(prod -> {
				Map<String, String> data = new HashMap<>();

				data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
				data.put("quantity", prod.getQuantityAsHTMLText());
				data.put("price", Util.decimalFormatter(prod.getRateAmount()));
				data.put("rate", Util.decimalFormatter(prod.getPrice()));
				data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
				if (dealReq.getDeal().getGstType().equals("IGST")) {
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

			File directory = new File(contentPath + "/Deals/" + dealReq.getDeal().getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}
			dealReq.getDealProformaInvoice()
					.setFilename(dealReq.getDealProformaInvoice().getProformaInvoiceNo().replaceAll("/", "-") + ".pdf");
			final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealProformaInvoice().getFilename();
			System.out.println(filePath);

			proInvRepo.save(dealReq.getDealProformaInvoice());

			// Export the report to a PDF file.
			JasperExportManager.exportReportToPdfFile(print, filePath);
			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealProformaInvoice", dealReq.getDealProformaInvoice());
		return resp;

	}

	@Override
	public Map<String, Object> generateDeliveryChallanPDF2(DealRequest dealReq) {

		Map<String, Object> resp = new HashMap<>();

		try {
			// DecimalFormat decformatter = new DecimalFormat("###,##0.00");
			// SimpleDateFormat Util.sdfFormatter = new SimpleDateFormat("dd/MM/yyyy");

			dealReq.setDealProformaInvoice(proInvRepo.findById(dealReq.getDealProformaInvoice().getId()));

			dealReq.setDeal(dealRepo.findById(dealReq.getDealProformaInvoice().getDealId()));

			dealReq.setDealProducts(dealProductsRepo.findByDealId(dealReq.getDealProformaInvoice().getDealId()));

			System.out.println(dealReq.toString());

			InputStream stream = null;

			stream = this.getClass().getResourceAsStream("/reports/Delivery_Challan/DC_Template_2.jrxml");

			final Map<String, Object> parameters = new HashMap<>();

			InfoDetails info = infoDetailRepo.findById(1);
			Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddressHTML1());
			parameters.put("cmp_logo_url", info.getLogoAsFile());
			parameters.put("dealtype_label", "Delivery Challan");

			parameters.put("is_discount_applied", dealReq.isDiscountApplied());
			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());

			parameters.put("amount_in_words",
					Util.EnglishNumberToWords(
							(dealReq.getDeal().getGrandTotal() - dealReq.getDealProformaInvoice().getPaidAmount())
									+ dealReq.getDealProformaInvoice().getShippingCost()));

			if (dealReq.getDeal().getDealType().equalsIgnoreCase("AMC") && dealReq.getDeal().getAmcFromDate() != null
					&& dealReq.getDeal().getAmcToDate() != null) {
				String subject = "Sub: " + dealReq.getDealProformaInvoice().getSubject()
						+ " <br>AMC Charges for the following items for the period of "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcFromDate(), "dd/MM/yyyy") + " to "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcToDate(), "dd/MM/yyyy");
				parameters.put("subject", subject);
			} else {
				parameters.put("subject", "Sub: " + dealReq.getDealProformaInvoice().getSubject());
			}

			parameters.put("billing_to", dealReq.getBillingToAddress());
			parameters.put("shipping_to", dealReq.getShippingToAddress());

			String deal_date_label = "", deal_date_text = "";

			deal_date_label = deal_date_label + "Invoice No : <br>";
			deal_date_text = deal_date_text + String.valueOf(dealReq.getDealInvoice().getInvoiceNo()) + "<br>";
			deal_date_label = deal_date_label + "Invoice Date : <br>";
			deal_date_text = deal_date_text
					+ String.valueOf(Util.sdfFormatter(dealReq.getDealProformaInvoice().getInvoiceDate())) + "<br>";
			deal_date_label = deal_date_label + "PO No : <br>";
			deal_date_text = deal_date_text + String.valueOf(dealReq.getDealProformaInvoice().getPurchaseOrderNo())
					+ "<br>";

			parameters.put("deal_date_label", deal_date_label);
			parameters.put("deal_date_text", deal_date_text);

			System.out.println(parameters.toString());

			List<Map<String, String>> datasource = new ArrayList<>();

			dealReq.getDealProducts().forEach(prod -> {
				Map<String, String> data = new HashMap<>();

				data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
				data.put("quantity", prod.getQuantityAsHTMLText());

				data.put("rate", Util.decimalFormatter(prod.getPrice()));
				data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
				datasource.add(data);
			});

			final JasperReport report = JasperCompileManager.compileReport(stream);
			final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

			final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

			File directory = new File(contentPath + "/Deals/" + dealReq.getDeal().getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}
			dealReq.getDealProformaInvoice()
					.setFilename(dealReq.getDealProformaInvoice().getProformaInvoiceNo().replaceAll("/", "-") + ".pdf");
			final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealProformaInvoice().getFilename();
			System.out.println(filePath);

			proInvRepo.save(dealReq.getDealProformaInvoice());

			// Export the report to a PDF file.
			JasperExportManager.exportReportToPdfFile(print, filePath);
			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealDeliveryChallan", dealReq.getDealProformaInvoice());
		return resp;

	}

	@Override
	public Map<String, Object> generateDeliveryChallanPDF3(DealRequest dealReq) {

		Map<String, Object> resp = new HashMap<>();

		try {
			// DecimalFormat decformatter = new DecimalFormat("###,##0.00");
			// SimpleDateFormat Util.sdfFormatter = new SimpleDateFormat("dd/MM/yyyy");

			dealReq.setDealProformaInvoice(proInvRepo.findById(dealReq.getDealProformaInvoice().getId()));

			dealReq.setDeal(dealRepo.findById(dealReq.getDealProformaInvoice().getDealId()));

			dealReq.setDealProducts(dealProductsRepo.findByDealId(dealReq.getDealProformaInvoice().getDealId()));

			System.out.println(dealReq.toString());

			InputStream stream = null;

			stream = this.getClass().getResourceAsStream("/reports/Delivery_Challan/DC_Template_3.jrxml");

			final Map<String, Object> parameters = new HashMap<>();

			InfoDetails info = infoDetailRepo.findById(1);
			Agent agent = agentRepo.findByEmailId(dealReq.getSignatureBy());

			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddressHTML1());
			parameters.put("cmp_logo_url", info.getLogoAsFile());
			parameters.put("dealtype_label", "Delivery Challan");

			parameters.put("is_discount_applied", dealReq.isDiscountApplied());
			parameters.put("roundseal", dealReq.getAddRoundSeal() ? info.getRoundSealAsFile() : null);
			parameters.put("fullseal", dealReq.getAddFullSeal() ? info.getFullSealAsFile() : null);
			parameters.put("signature", dealReq.getAddSign() ? agent.getSignatureAsFile() : null);
			parameters.put("for_label", "For " + info.getCmpName());
			parameters.put("designation", dealReq.getDesignation());
			parameters.put("bankdetails", info.getBankDetails().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
			parameters.put("online_payment_url", info.getInstamojoPaymentURL());

			parameters.put("terms", dealReq.getDealProformaInvoice().getTerms().replaceAll("\n", "<br>"));
//			parameters.put("invoice_date", Util.sdfFormatter(dealReq.getDealProformaInvoice().getInvoiceDate()));
//			parameters.put("due_date", Util.sdfFormatter(dealReq.getDealProformaInvoice().getDueDate()));
//			parameters.put("invoice_no", String.valueOf(dealReq.getDealProformaInvoice().getProformaInvoiceNo()));
//			parameters.put("sales_order_no", String.valueOf(dealReq.getDealProformaInvoice().getSalesOrderNo()));
//			parameters.put("purchase_order_no", String.valueOf(dealReq.getDealProformaInvoice().getPurchaseOrderNo()));
			parameters.put("deal_amount_label", "Balance Amount :");
			parameters.put("deal_amount_text",
					"Rs." + Util.decimalFormatter(
							(dealReq.getDeal().getGrandTotal() - dealReq.getDealProformaInvoice().getPaidAmount())
									+ dealReq.getDealProformaInvoice().getShippingCost()));
			parameters.put("amount_in_words",
					Util.EnglishNumberToWords(
							(dealReq.getDeal().getGrandTotal() - dealReq.getDealProformaInvoice().getPaidAmount())
									+ dealReq.getDealProformaInvoice().getShippingCost()));

			if (dealReq.getDeal().getDealType().equalsIgnoreCase("AMC") && dealReq.getDeal().getAmcFromDate() != null
					&& dealReq.getDeal().getAmcToDate() != null) {
				String subject = "Sub: " + dealReq.getDealProformaInvoice().getSubject()
						+ " <br>AMC Charges for the following items for the period of "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcFromDate(), "dd/MM/yyyy") + " to "
						+ Util.sdfFormatter(dealReq.getDeal().getAmcToDate(), "dd/MM/yyyy");
				parameters.put("subject", subject);
			} else {
				parameters.put("subject", "Sub: " + dealReq.getDealProformaInvoice().getSubject());
			}

			parameters.put("billing_to", dealReq.getBillingToAddress());
			parameters.put("shipping_to", dealReq.getShippingToAddress());

			String price_summary_label = "", price_summary_text = "";

			String deal_date_label = "", deal_date_text = "";

			deal_date_label = deal_date_label + "Invoice No : <br>";
			deal_date_text = deal_date_text + String.valueOf(dealReq.getDealInvoice().getInvoiceNo()) + "<br>";
			deal_date_label = deal_date_label + "Invoice Date : <br>";
			deal_date_text = deal_date_text
					+ String.valueOf(Util.sdfFormatter(dealReq.getDealProformaInvoice().getInvoiceDate())) + "<br>";
			deal_date_label = deal_date_label + "PO No : <br>";
			deal_date_text = deal_date_text + String.valueOf(dealReq.getDealProformaInvoice().getPurchaseOrderNo())
					+ "<br>";

			price_summary_label = price_summary_label + "Original Rate : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getSubTotal())
					+ "<br>";

			if (dealReq.getDeal().getDiscount() > 0) {
				price_summary_label = price_summary_label + "Discount : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getDiscount())
						+ "<br>";
			}

			price_summary_label = price_summary_label + "Taxable Amount : <br>";
			price_summary_text = price_summary_text + "Rs."
					+ Util.decimalFormatter(dealReq.getDeal().getSubTotal() - dealReq.getDeal().getDiscount()) + "<br>";

			if (dealReq.getDeal().getGstType().equalsIgnoreCase("IGST")) {
				price_summary_label = price_summary_label + "IGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax())
						+ "<br>";
			} else {
				price_summary_label = price_summary_label + "CGST : <br>";
				price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getTax() / 2)
						+ "<br>";

				price_summary_label = price_summary_label + "SGST : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ Util.decimalFormatter((dealReq.getDeal().getTax() / 2)) + "<br>";
			}

			if (dealReq.getDeal().getAdjustment() != 0.00) {
				price_summary_label = price_summary_label + "Adjustment : <br>";
				price_summary_text = price_summary_text + "Rs."
						+ String.format("%.2f", dealReq.getDeal().getAdjustment()) + "<br>";
			}

			price_summary_label = price_summary_label + "Grand Total : <br>";
			price_summary_text = price_summary_text + "Rs." + Util.decimalFormatter(dealReq.getDeal().getGrandTotal())
					+ "<br>";

			price_summary_label = price_summary_label + "Paid Amount : <br>";
			price_summary_text = price_summary_text + ""
					+ Util.decimalFormatter(dealReq.getDealProformaInvoice().getPaidAmount()) + "<br>";

			if (dealReq.getDealProformaInvoice().getShippingCost() > 0) {
				price_summary_label = price_summary_label + "Shipment Amount : <br>";
				price_summary_text = price_summary_text + ""
						+ Util.decimalFormatter(dealReq.getDealProformaInvoice().getShippingCost()) + "<br>";
			}

			parameters.put("price_summary_label", price_summary_label);
			parameters.put("price_summary_text", price_summary_text);

			parameters.put("deal_date_label", deal_date_label);
			parameters.put("deal_date_text", deal_date_text);

			System.out.println(parameters.toString());

			List<Map<String, String>> datasource = new ArrayList<>();

			dealReq.getDealProducts().forEach(prod -> {
				Map<String, String> data = new HashMap<>();

				data.put("name_description", prod.getNameDescHTMLText().replaceAll("\n", "<br>"));
				data.put("quantity", prod.getQuantityAsHTMLText());
				data.put("price", Util.decimalFormatter(prod.getRateAmount()));
				data.put("rate", Util.decimalFormatter(prod.getPrice()));
				data.put("total", Util.decimalFormatter(prod.getTotalAmount()));
				if (dealReq.getDeal().getGstType().equals("IGST")) {
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

			File directory = new File(contentPath + "/Deals/" + dealReq.getDeal().getId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}
			dealReq.getDealProformaInvoice()
					.setFilename(dealReq.getDealProformaInvoice().getProformaInvoiceNo().replaceAll("/", "-") + ".pdf");
			final String filePath = directory.getAbsolutePath() + "/" + dealReq.getDealProformaInvoice().getFilename();
			System.out.println(filePath);

			proInvRepo.save(dealReq.getDealProformaInvoice());

			// Export the report to a PDF file.
			JasperExportManager.exportReportToPdfFile(print, filePath);
			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("DealProformaInvoice", dealReq.getDealProformaInvoice());
		return resp;

	}

}
