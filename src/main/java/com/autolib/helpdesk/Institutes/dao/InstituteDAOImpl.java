package com.autolib.helpdesk.Institutes.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Agents.entity.Product;
import com.autolib.helpdesk.Agents.repository.InfoDetailsRepository;
import com.autolib.helpdesk.Agents.repository.ProductsRepository;
import com.autolib.helpdesk.Institutes.model.AMCDetailResp;
import com.autolib.helpdesk.Institutes.model.AMCDetails;
import com.autolib.helpdesk.Institutes.model.AMCReminderRequest;
import com.autolib.helpdesk.Institutes.model.AMCSearchRequest;
import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.Institutes.model.InstituteContact;
import com.autolib.helpdesk.Institutes.model.InstituteContactRequest;
import com.autolib.helpdesk.Institutes.model.InstituteContactResponse;
import com.autolib.helpdesk.Institutes.model.InstituteImportReq;
import com.autolib.helpdesk.Institutes.model.InstituteProducts;
import com.autolib.helpdesk.Institutes.model.InstituteProductsRequest;
import com.autolib.helpdesk.Institutes.model.InstituteRequest;
import com.autolib.helpdesk.Institutes.model.InvoiceRequest;
import com.autolib.helpdesk.Institutes.repository.InstituteAmcRepository;
import com.autolib.helpdesk.Institutes.repository.InstituteContactRepository;
import com.autolib.helpdesk.Institutes.repository.InstituteProductRepository;
import com.autolib.helpdesk.Institutes.repository.InstituteRepository;
import com.autolib.helpdesk.common.DBQueryUtil;
import com.autolib.helpdesk.common.EmailModel;
import com.autolib.helpdesk.common.EmailSender;
import com.autolib.helpdesk.common.EnumUtils.ServiceUnder;
import com.autolib.helpdesk.common.Util;
import com.autolib.helpdesk.schedulers.model.LogsSchedulerInvoice;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Repository
public class InstituteDAOImpl implements InstituteDAO {

	@Autowired
	InstituteRepository instRepo;
	@Autowired
	InstituteContactRepository instContRepo;
	@Autowired
	InstituteAmcRepository instAmcRepo;
	@Autowired
	InstituteProductRepository instProductRepo;
	@Autowired
	ProductsRepository productRepo;

	@Autowired
	InfoDetailsRepository infoDetailsRepo;
	@PersistenceContext
	EntityManager em;

	@Autowired
	private EmailSender emailSender;

	@Autowired
	private JdbcTemplate jdbcTemp;

	@Value("${al.ticket.content-path}")
	private String contentPath;
	@Value("${al.ticket.web.url}")
	private String webURL;

	@Override
	public Map<String, Object> saveInstitute(Institute institute) {
		Map<String, Object> resp = new HashMap<>();
		try {
			if (instRepo.findByInstituteId(institute.getInstituteId()) != null) {
				resp.putAll(Util.invalidMessage("Institute Id Already Exist"));
			} else if (institute.getInstituteName() == null || institute.getInstituteName().isEmpty()) {
				resp.putAll(Util.invalidMessage("Institute Name Cannot be empty"));
			}
//			else if (institute.getEmailId() == null || institute.getEmailId().isEmpty()) {
//			resp.putAll(Util.invalidMessage("Email Id Cannot be empty"));			} 
			else if (institute.getZipcode() == null || institute.getZipcode().isEmpty()) {
				resp.putAll(Util.invalidMessage("Zipcode Cannot be empty"));
			}
			
			else if (institute.getInstituteType() == null || institute.getInstituteType().isEmpty()) {
				resp.putAll(Util.invalidMessage("InstituteType Cannot be empty"));
			}
			else if (institute.getStreet1() == null || institute.getStreet1().isEmpty()) {
			resp.putAll(Util.invalidMessage("Address1 Cannot be empty"));
			} 
			
			else if (institute.getCity() == null || institute.getCity().isEmpty()) {
				resp.putAll(Util.invalidMessage("City Cannot be empty"));
			}
			else if (institute.getState() == null || institute.getState().isEmpty()) {
			resp.putAll(Util.invalidMessage("State Cannot be empty"));
			} 
//			else if (institute.getStreet2() == null || institute.getStreet2().isEmpty()) {
//				resp.putAll(Util.invalidMessage("Address2 Cannot be empty"));
//				} 
//			else if (institute.getEmailId() == null || institute.getEmailId().isEmpty()) {
//				resp.putAll(Util.invalidMessage("Email Id Cannot be empty"));
//			}
			
			else if (institute.getCountry() == null || institute.getCountry().isEmpty()) {
				resp.putAll(Util.invalidMessage("Country Cannot be empty"));
			}
					
			else {

				String lastId = instRepo.findLastMaxId();
				System.out.println(lastId);
				try {
					Integer.parseInt(lastId);
					lastId = String.valueOf(Integer.parseInt(lastId) + 1);
				} catch (Exception e) {
					lastId = lastId + "1";
				}

				institute.setInstituteId(lastId);

				System.out.println(lastId);
				institute = instRepo.save(institute);
				resp.putAll(Util.SuccessResponse());
			}

		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		resp.put("Institute", institute);
		return resp;
	}

	@Override
	public Map<String, Object> editInstitute(Institute institute) {
		Map<String, Object> resp = new HashMap<>();
		try {
			if (instRepo.findByInstituteId(institute.getInstituteId()) == null) {
				resp.putAll(Util.invalidMessage("Institute Id Not Exist"));
			} else if (institute.getInstituteName() == null || institute.getInstituteName().isEmpty()) {
				resp.putAll(Util.invalidMessage("Institute Name Cannot be empty"));
			}
//			else if (institute.getEmailId() == null || institute.getEmailId().isEmpty()) {
//				resp.putAll(Util.invalidMessage("Email Id Cannot be empty"));
//			} 
//			else if (institute.getZipcode() == null || institute.getZipcode().isEmpty()) {
//				resp.putAll(Util.invalidMessage("Zipcode Cannot be empty"));
//			} 
			else {
				institute = instRepo.save(institute);
				resp.putAll(Util.SuccessResponse());
			}

		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		resp.put("Institute", institute);
		return resp;
	}

	@Override
	public Map<String, Object> getInstitutePreDeleteData(Institute institute) {
		Map<String, Object> resp = new HashMap<>();
		try {
			resp.put("Counts", instRepo.getInstitutePreDeleteDate(institute.getInstituteId()));

			resp.putAll(Util.SuccessResponse());
		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		return resp;
	}

	@Transactional
	@Modifying
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> deleteInstitute(Institute inst) {
		Map<String, Object> resp = new HashMap<>();
		try {
			inst = instRepo.findByInstituteId(inst.getInstituteId());
			if (inst == null) {
				resp.putAll(Util.invalidMessage("Institute Not Found"));
			} else {
				Query query = null;
				List<String> dealIds = new ArrayList<>();
				List<String> ticketIds = new ArrayList<>();

				query = em.createQuery("select id FROM Deal WHERE institute = :inst");
				dealIds = query.setParameter("inst", inst).getResultList();

				query = em.createQuery("select ticketId FROM Ticket WHERE institute = :inst");
				ticketIds = query.setParameter("inst", inst).getResultList();

				System.out.println(dealIds.toString());
				System.out.println(ticketIds.toString());

				// Delete Deal Datas

				query = em.createQuery("delete FROM DealContacts WHERE dealId in :dealIds");
				resp.put("DealContactsCount", query.setParameter("dealIds", dealIds).executeUpdate());

				query = em.createQuery("delete FROM DealDeliveryChallan WHERE dealId in :dealIds");
				resp.put("DealDeliveryChallanCount", query.setParameter("dealIds", dealIds).executeUpdate());

				query = em.createQuery("delete FROM DealEmailAttachments WHERE dealId in :dealIds");
				resp.put("DealEmailAttachmentsCount", query.setParameter("dealIds", dealIds).executeUpdate());

				query = em.createQuery("delete FROM DealEmail WHERE dealId in :dealIds");
				resp.put("DealEmailCount", query.setParameter("dealIds", dealIds).executeUpdate());

				query = em.createQuery("delete FROM DealInvoice WHERE dealId in :dealIds");
				resp.put("DealInvoiceCount", query.setParameter("dealIds", dealIds).executeUpdate());

				query = em.createQuery("delete FROM Notes WHERE dealId in :dealIds");
				resp.put("NotesCount", query.setParameter("dealIds", dealIds).executeUpdate());

				query = em.createQuery("delete FROM NoteAttachments WHERE dealId in :dealIds");
				resp.put("NoteAttachmentsCount", query.setParameter("dealIds", dealIds).executeUpdate());

				query = em.createQuery("delete FROM DealPayments WHERE dealId in :dealIds");
				resp.put("DealPaymentsCount", query.setParameter("dealIds", dealIds).executeUpdate());

				query = em.createQuery("delete FROM DealProducts WHERE dealId in :dealIds");
				resp.put("DealProductsCount", query.setParameter("dealIds", dealIds).executeUpdate());

				query = em.createQuery("delete FROM DealProformaInvoice WHERE dealId in :dealIds");
				resp.put("DealProformaInvoiceCount", query.setParameter("dealIds", dealIds).executeUpdate());

				query = em.createQuery("delete FROM DealPurchaseOrder WHERE dealId in :dealIds");
				resp.put("DealPurchaseOrderCount", query.setParameter("dealIds", dealIds).executeUpdate());

				query = em.createQuery("delete FROM DealQuotation WHERE dealId in :dealIds");
				resp.put("DealQuotationCount", query.setParameter("dealIds", dealIds).executeUpdate());

				query = em.createQuery("delete FROM DealSalesOrder WHERE dealId in :dealIds");
				resp.put("DealSalesOrderCount", query.setParameter("dealIds", dealIds).executeUpdate());

				query = em.createQuery("delete FROM DealPurchaseOrder WHERE dealId in :dealIds");
				resp.put("DealPurchaseOrderCount", query.setParameter("dealIds", dealIds).executeUpdate());

				query = em.createQuery("delete FROM Deal WHERE id in :dealIds");
				resp.put("DealCount", query.setParameter("dealIds", dealIds).executeUpdate());

				// Delete Ticket Datas
				query = em.createNativeQuery("delete FROM ticket_attachments WHERE ticket_id in :ticketIds");
				resp.put("TicketAttachmentsCount", query.setParameter("ticketIds", ticketIds).executeUpdate());

				query = em.createQuery("delete FROM TicketRating WHERE ticketId in :ticketIds");
				resp.put("TicketRatingCount", query.setParameter("ticketIds", ticketIds).executeUpdate());

				query = em.createNativeQuery("delete FROM ticket_replies WHERE ticket_id in :ticketIds");
				resp.put("TicketRepliesCount", query.setParameter("ticketIds", ticketIds).executeUpdate());

				query = em.createNativeQuery("delete FROM ticket_service_invoices WHERE ticket_id in :ticketIds");
				resp.put("TicketRepliesCount", query.setParameter("ticketIds", ticketIds).executeUpdate());

				query = em.createQuery("delete FROM Ticket WHERE institute in :institute");
				resp.put("TicketCount", query.setParameter("institute", inst).executeUpdate());

				// Delete InstituteContact

				query = em.createQuery("delete FROM InstituteContact WHERE instituteId in :instituteId");
				resp.put("InstituteContactCount",
						query.setParameter("instituteId", inst.getInstituteId()).executeUpdate());

				// Delete InstituteProducts

				query = em.createQuery("delete FROM InstituteProducts WHERE institute in :institute");
				resp.put("InstituteProductsCount", query.setParameter("institute", inst).executeUpdate());

				// Delete AMCDetails Datas

				query = em.createQuery("delete FROM AMCDetails WHERE institute = :inst");
				resp.put("AMCDetailsCount", query.setParameter("inst", inst).executeUpdate());

				// Delete GoogleMailAsTicket Datas

				query = em.createQuery("delete FROM GoogleMailAsTicket WHERE institute = :inst");
				resp.put("GoogleMailAsTicketCount", query.setParameter("inst", inst).executeUpdate());

				// Delete SiteAttendance Datas

				query = em.createQuery("delete FROM SiteAttendance WHERE institute = :inst");
				resp.put("GoogleMailAsTicketCount", query.setParameter("inst", inst).executeUpdate());

				// Delete LogsSchedulerInvoice Datas

				query = em.createQuery("delete FROM LogsSchedulerInvoice WHERE institute = :inst");
				resp.put("LogsSchedulerInvoiceCount", query.setParameter("inst", inst).executeUpdate());

				// Delete Institute Datas

				query = em.createQuery("delete FROM Institute WHERE instituteId = :instId");
				resp.put("InstituteCount", query.setParameter("instId", inst.getInstituteId()).executeUpdate());

				resp.putAll(Util.SuccessResponse());
			}

		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		return resp;
	}

	@Override
	public Map<String, Object> getInstituteDetails(Institute institute) {
		Map<String, Object> resp = new HashMap<>();
		File filePath = null;

		try {
			institute = instRepo.findByInstituteId(institute.getInstituteId());

			if (institute == null) {
				resp.putAll(Util.invalidMessage("Institute Not Found"));

			} else {
//				String logoURL = institute.getLogourl();
//				filePath = new File(contentPath + "/InstituteLogo/" + logoURL);
//
//				institute.setLogourl(filePath.toString());
				resp.putAll(Util.SuccessResponse());
			}

		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}

		resp.put("Institute", institute);

		return resp;
	}

	@Override
	public Map<String, Object> saveInstituteContact(InstituteContact ic) {

		Map<String, Object> resp = new HashMap<>();
		try {
			if (ic.getInstituteId() == null || ic.getInstituteId().isEmpty()) {
				resp.putAll(Util.invalidMessage("Institute Id Cannot be empty"));
			} else if (ic.getEmailId() == null || ic.getEmailId().isEmpty()) {
				resp.putAll(Util.invalidMessage("Email Id Cannot be empty"));
			} else {
				boolean isRandomPwdGenerated = false;
				String pwd = String.valueOf(Util.generateRandomPassword());

				System.out.println("Password:::::::" + pwd);
				if (ic.getId() == 0) {
					String hashedpwd = "";

					BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
					hashedpwd = bcrypt.encode(pwd);

					if (ic.getPassword() == null) {
						ic.setPassword(hashedpwd);
						isRandomPwdGenerated = true;
					}
				}

				if (ic.getIsBlocked() == null)
					ic.setIsBlocked(Short.valueOf("0"));

				ic = instContRepo.save(ic);

				/*
				 * if (isRandomPwdGenerated) { EmailModel emailModel = new EmailModel();
				 * emailModel.setMailFrom(ic.getEmailId());
				 * emailModel.setFromName("AutoLib Software Systems");
				 * emailModel.setMailTo(ic.getEmailId());
				 * emailModel.setMailSub("Welcome to AutoLib - Helpdesk");
				 * 
				 * StringBuffer sb = new StringBuffer(); sb.append("Dear " + ic.getFirstName() +
				 * ", <br><br>");
				 * 
				 * sb.
				 * append("We glad to introduce our new Helpdesk Ticketing Application.<br><br>"
				 * +
				 * "In Future for any enquiries or supports regarding any our supplied Products, Use below URL to raise Tickets, and you can track the Status of the Tickets at any time.<br><br>"
				 * + "Helpdesk Ticketing System for your Support.<br><br>" +
				 * "Use below Credentials to get started with us.<br>");
				 * 
				 * sb.append("<br>URL : " + webURL);
				 * 
				 * sb.append("<br><br>Email Id : " + ic.getEmailId());
				 * sb.append("<br>Password : " + pwd);
				 * 
				 * sb.append(
				 * "<br><br>Note : The Above Password is auto generated by system, Kindly change password after successfull login by using below link."
				 * );
				 * 
				 * sb.append("<br>http://smallcart.in:9009/onboard/change-password <br>");
				 * 
				 * emailModel.setMailText(sb.toString());
				 * 
				 * emailSender.sendmail(emailModel); }
				 */
				resp.putAll(Util.SuccessResponse());
			}

		} catch (DataIntegrityViolationException e) {
			if (e.getMessage().contains("uc_instid_email"))
				resp.putAll(Util.invalidMessage("EmailId Already Associated with another contact"));
			else {
				e.printStackTrace();
				resp.putAll(Util.FailedResponse(e.getMessage()));
			}
		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		resp.put("InstituteContact", ic);
		return resp;

	}

	@Override
	public Map<String, Object> sendInstituteContact(InstituteContact ic) {

		Map<String, Object> resp = new HashMap<>();
		try {
			if (ic.getInstituteId() == null || ic.getInstituteId().isEmpty()) {
				resp.putAll(Util.invalidMessage("Institute Id Cannot be empty"));
			} else if (ic.getEmailId() == null || ic.getEmailId().isEmpty()) {
				resp.putAll(Util.invalidMessage("Email Id Cannot be empty"));
			} else {
				boolean isRandomPwdGenerated = false;
				String pwd = String.valueOf(Util.generateRandomPassword());

				System.out.println("Password:::::::" + pwd);
				if (ic.getId() != 0) {
					String hashedpwd = "";

					BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
					hashedpwd = bcrypt.encode(pwd);

					String email_id = ic.getEmailId();
					int pwdUpdateCount = 0;

					pwdUpdateCount = jdbcTemp.update("UPDATE institute_contact SET password ='" + hashedpwd
							+ "' WHERE email_id='" + email_id + "'");

					if (ic.getPassword() != null) {
						ic.setPassword(hashedpwd);
						isRandomPwdGenerated = true;
					}
				}

				if (ic.getIsBlocked() == null)
					ic.setIsBlocked(Short.valueOf("0"));

//				ic = instContRepo.save(ic);

				if (isRandomPwdGenerated) {
					EmailModel emailModel = new EmailModel("Common");
					emailModel.setMailFrom(ic.getEmailId());
					emailModel.setFromName("AutoLib Software Systems");
					emailModel.setMailTo(ic.getEmailId());
					emailModel.setMailSub("Welcome to AutoLib - Helpdesk");

					StringBuffer sb = new StringBuffer();
					sb.append("Dear " + ic.getFirstName() + ", <br><br>");

					/*
					 * sb.
					 * append("We glad to introduce our new Helpdesk Ticketing Application.<br><br>"
					 * +
					 * "In Future for any enquiries or supports regarding any our supplied Products, Use below URL to raise Tickets, and you can track the Status of the Tickets at any time.<br><br>"
					 * + "Helpdesk Ticketing System for your Support.<br><br>" +
					 * "Use below Credentials to get started with us.<br>");
					 */

					sb.append(infoDetailsRepo.getMailContent().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));
					System.out.println("Mail:::"
							+ infoDetailsRepo.getMailContent().replaceAll("\n", "<br>").replaceAll("\r", "<br>"));

					sb.append("<br><br>URL : " + webURL);

					sb.append("<br><br>Email Id : " + ic.getEmailId());
					sb.append("<br>Password : " + pwd);

					sb.append(
							"<br><br>Note : The Above Password is auto generated by system, Kindly change password after successfull login by using below link.");

					sb.append(webURL + "onboard/change-password <br>");

					emailModel.setMailText(sb.toString());

					emailSender.sendmail(emailModel);
				}

				resp.putAll(Util.SuccessResponse());
			}

		} catch (DataIntegrityViolationException e) {
			if (e.getMessage().contains("uc_instid_email"))
				resp.putAll(Util.invalidMessage("EmailId Already Associated with another contact"));
			else {
				e.printStackTrace();
				resp.putAll(Util.FailedResponse(e.getMessage()));
			}
		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		resp.put("InstituteContact", ic);
		return resp;

	}

	@Override
	public Map<String, Object> deleteInstituteContact(InstituteContact ic) {
		Map<String, Object> resp = new HashMap<>();
		try {
			if (!instContRepo.existsById(ic.getId())) {
				resp.putAll(Util.invalidMessage("Contact Not Found"));
			} else {
				instContRepo.delete(ic);
				resp.putAll(Util.SuccessResponse());
			}

		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		return resp;
	}

	@Override
	public Map<String, Object> getInstituteContacts(InstituteContact ic) {
		Map<String, Object> resp = new HashMap<>();
		List<InstituteContact> contacts = new ArrayList<>();
		try {
			contacts = instContRepo.findByInstituteId(ic.getInstituteId());
			resp.putAll(Util.SuccessResponse());
		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		resp.put("InstituteContacts", contacts);
		return resp;
	}

	@Override
	public int changePassword(Map<String, Object> req) {
		System.out.println("changePassword DAO starts::");
		int saveUpdateCount = 0;
		Map<String, Object> requestMap = (Map<String, Object>) req.get("ChangePasswordReq");
		BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();

		try {

			String email_id = requestMap.get("username").toString();
			String old_password = requestMap.get("old_password").toString();
			String new_password = pwdEncoder.encode(requestMap.get("new_password").toString());
			System.out.println("changePassword DAO starts::");

			System.out.println("email_id::::" + email_id);
			System.out.println("old_password::::" + old_password);
			System.out.println("new_password::::" + new_password);

			int count = jdbcTemp.queryForObject(
					"select count(*) as count from institute_contact where email_id='" + email_id + "'", Integer.class);
			if (count > 0) {
				String check = jdbcTemp.queryForObject(
						"select password from institute_contact where email_id='" + email_id + "'", String.class);
				System.out.println(check + ":::" + pwdEncoder.matches(old_password, check));
				if (pwdEncoder.matches(old_password, check)) {
					saveUpdateCount = jdbcTemp.update("UPDATE institute_contact SET password ='" + new_password
							+ "' WHERE email_id='" + email_id + "'");
					saveUpdateCount = 1;
				}
			}

			System.out.println("saveUpdateCount::::::::" + saveUpdateCount);
		} catch (Exception ex) {
			System.out.println("changePassword DAO ends::");
			ex.printStackTrace();
		}

		return saveUpdateCount;

	}

	@Override
	public int sendOTP(Map<String, Object> req) {

		int saveUpdateCount = 0;
		Map<String, String> reqStrMap = (Map<String, String>) req.get("SendOTPReq");

		String memberPhone = "";

		String email_id = reqStrMap.get("username");
		System.out.println("memberEmail:::::::" + email_id);

		try {

			int memberCheck = jdbcTemp.queryForObject(
					"SELECT COUNT(*) as count FROM institute_contact WHERE email_id = '" + email_id + "'",
					Integer.class);

			System.out.println("memberCheck::::::" + memberCheck);

			if (memberCheck == 0) {
				saveUpdateCount = 2;
			}

			else {
				EmailModel emailModel = new EmailModel("Common");
				emailModel.setMailFrom(email_id);
				emailModel.setFromName("AutoLib Software Systems");
				emailModel.setMailTo(email_id);
				emailModel.setPhone_number(memberPhone);
				emailModel.setOtp(String.valueOf(Util.generateOTP()));
				emailModel.setMailText("Your OTP For Reset Password : " + emailModel.getOtp());
				emailModel.setMailSub("Your OTP For Reset Password : " + emailModel.getOtp());

				Object[] otpInserParam = { email_id, emailModel.getOtp() };
				saveUpdateCount = jdbcTemp.update(DBQueryUtil.Insert_Signup_OTP, otpInserParam);

				emailSender.sendmail(emailModel);
				System.out.println("emailSender::::");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return saveUpdateCount;
	}

	@Override
	public int resetPassword(Map<String, Object> reqMap) {

		int saveUpdateCount = 0;
		BCryptPasswordEncoder pwdEncrypt = new BCryptPasswordEncoder();
		Map<String, Object> requestMap = (Map<String, Object>) reqMap.get("ResetPasswordReq");
		System.out.println("resetPassword DAO starts::");
		try {
			String email_id = requestMap.get("username").toString();
			String password = pwdEncrypt.encode(requestMap.get("new_password").toString());

			int count = jdbcTemp.queryForObject(
					"select count(*) as count from institute_contact where email_id = '" + email_id + "'",
					Integer.class);
			if (count > 0) {

				saveUpdateCount = jdbcTemp.update("UPDATE institute_contact SET password = '" + password
						+ "' WHERE email_id ='" + email_id + "'");

			}

		} catch (Exception ex) {
			System.out.println("resetPassword DAO ends::");
			ex.printStackTrace();
		}
		System.out.println("resetPassword DAO ends ::");
		return saveUpdateCount;

	}

	@Override
	public int checkOTP(Map<String, Object> req) {

		int saveUpdateCount = 0;
		String count1 = "";
		int count = 0;
		int sec = 0;
		System.out.println("checkOTP  starts::");
		Map<String, String> reqStrMap = (Map<String, String>) req.get("CheckOTPReq");
		String memberEmail = reqStrMap.get("username");
		String otp = reqStrMap.get("otp");

		System.out.println("username::::::" + memberEmail);
		System.out.println("OTP::::::" + otp);
		try {

			count = jdbcTemp.queryForObject(
					"select count(*) from signup_otp where mail_id = '" + memberEmail + "' and otp = '" + otp + "'",
					Integer.class);

			System.out.println("count::::::::" + count);
			if (count > 0) {
				sec = jdbcTemp.queryForObject(
						"select time_to_sec(timediff(current_timestamp,generated_time)) as sec from signup_otp where mail_id = '"
								+ memberEmail + "' and otp = '" + otp + "' order by  generated_time desc limit 1",
						Integer.class);
				if (sec <= 120) {
					saveUpdateCount = jdbcTemp
							.update("update signup_otp set verified_time=current_timestamp where mail_id = '"
									+ memberEmail + "' and otp = '" + otp + "'");
					saveUpdateCount = 1;
				} else {
					saveUpdateCount = 2;
				}
			}
		} catch (Exception ex) {
			saveUpdateCount = 0;
			ex.printStackTrace();
		}
		System.out.println("checkOTP ends");
		return saveUpdateCount;
	}

	@Override
	public Map<String, Object> saveAmcDetails(AMCDetails amc) {
		Map<String, Object> resp = new HashMap<>();
		try {

			if (amc.getAmcId() == null || amc.getAmcId().isEmpty()) {
				resp.putAll(Util.invalidMessage("AMC Id Not Exist"));
			} else if (amc.getFromDate() == null || amc.getToDate() == null || amc.getPaidDate() == null) {
				resp.putAll(Util.invalidMessage("Date's Cannot be empty"));
			} else {
				instAmcRepo.save(amc);

				System.out.println("::::::Service_Type:::::" + amc.getServiceType());
				System.out.println("::::::Inst_ID:::::" + amc.getInstitute().getInstituteId());

				if (amc.getServiceType().equals("AMC")) {
					System.out.println("::::::Inside IF:::::");

					InstituteProducts ip = instProductRepo.findByInstituteAndProduct(amc.getInstitute(),
							new Product(amc.getProduct()));

					ip.setAmcAmount(amc.getAmcAmount());
					ip.setCurrentServiceUnder(ServiceUnder.valueOf("AMC"));
					ip.setLastAMCPaidDate(amc.getPaidDate());
					ip.setAmcExpiryDate(amc.getToDate());

					System.out.println(":::;;;;" + ip);

					instProductRepo.save(ip);

				}
				resp.putAll(Util.SuccessResponse());
			}
		} catch (DataIntegrityViolationException ex) {
			if (ex.getMessage().contains("uc_amcid_product"))
				resp.putAll(Util.invalidMessage("Invoice No is Already Exist with Same Product"));
			else
				resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}

		return resp;
	}

	@Override
	public Map<String, Object> loadInstitute() {
		Map<String, Object> resp = new HashMap<>();
		List<Map<String, Object>> institute = new ArrayList<>();

		try {
			institute = jdbcTemp.queryForList("SELECT institute_id, CASE WHEN short_term IS NULL THEN institute_name "
					+ "	ELSE CONCAT(IFNULL(short_term,''),' (',institute_name,')') END AS institute_name"
					+ " FROM institutes");
			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("institute", institute);
		return resp;
	}

	public Map amcReport(Map<String, Object> reqMap) {
		Map<String, String> valueReqMap = new HashMap<String, String>();
		Map<String, Object> respListMap = new HashMap<>();
		String sqlQuery = "";
		String namedQuery = "";
		List amcList = new ArrayList<>();
		try {
			valueReqMap = (Map<String, String>) reqMap.get("SearchAmcReport");

			String institute = valueReqMap.get("selectedinstitute").toString();
			String paymode = valueReqMap.get("paymode").toString();
			String validFromDate = valueReqMap.get("validFromDate").toString();
			String validToDate = valueReqMap.get("validToDate").toString();
			String paidFromDate = valueReqMap.get("paidFromDate").toString();
			String paidToDate = valueReqMap.get("paidToDate").toString();
			String product = valueReqMap.get("product").toString();

			if (institute != null && !institute.isEmpty()) {
				namedQuery = "and institute_name like '%" + institute + "%'";
			}
			if (validFromDate != null && !validFromDate.isEmpty() && validToDate != null && !validToDate.isEmpty()) {
				namedQuery = namedQuery + " and to_date  between '" + validFromDate + "' and '" + validToDate + "'";
			}
			if (paidToDate != null && !paidToDate.isEmpty()) {
				namedQuery = namedQuery + " and paid_date  between '" + paidFromDate + "' and '" + paidToDate + "'";
			}
			if (product != null && !product.isEmpty()) {
				namedQuery = namedQuery + " and product like '%" + product + "%'";
			}
			if (paymode != null && !paymode.isEmpty()) {
				namedQuery = namedQuery + "and pay_mode = '" + paymode + "'";
			}

			String Query = "SELECT `amc_details`.`amc_id`,`amc_details`.`paid_date`,`amc_details`.`from_date`,`amc_details`.`to_date`,`amc_details`.`pay_mode`,`amc_details`.`amc_amount`,`amc_details`.`inv_date`,`amc_details`.`product_id`,`amc_details`.`institute_id`, `institutes`.`institute_name` AS `institute_name`,`products`.`name` AS `name` FROM (amc_details \n"
					+ "JOIN `institutes` ON (`amc_details`.`institute_id` = `institutes`.`institute_id`)"
					+ "JOIN `products` ON (`amc_details`.`product_id` = `products`.`id`)) where 2>1 " + " "
					+ namedQuery;
			// logger.debug(Query);
			amcList = jdbcTemp.queryForList(Query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// logger.error("Got Exception while communicating to DB" + e);
			e.printStackTrace();
		}
		respListMap.put("amcList", amcList);
		// logger.info("AMC_Report DAOImpl method ends::");
		return respListMap;
	}

	@Override
	public Map<String, Object> saveInstituteProducts(InstituteProducts ip) {
		Map<String, Object> resp = new HashMap<>();

		try {
			ip = instProductRepo.save(ip);
			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("InstituteProducts", ip);
		return resp;
	}

	@Override
	public Map<String, Object> removeInstituteProducts(InstituteProducts ip) {
		Map<String, Object> resp = new HashMap<>();

		try {

			instProductRepo.delete(ip);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("InstituteProducts", ip);
		return resp;
	}

	@Override
	public Map<String, Object> getInstituteProducts(InstituteProducts ip) {

		Map<String, Object> resp = new HashMap<>();

		try {
			String filterQuery = "";

			if (ip.getInstitute() != null) {
				filterQuery = filterQuery + " and ip.institute ='" + ip.getInstitute().getInstituteId() + "'";
			}
			if (ip.getProduct() != null) {
				filterQuery = filterQuery + " and ip.product ='" + ip.getProduct().getId() + "'";
			}

			Query query = em.createQuery(
					"select ip from InstituteProducts ip INNER JOIN FETCH ip.product p where 2 > 1 " + filterQuery,
					InstituteProducts.class);
			resp.put("InstituteProducts", query.getResultList());
			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		return resp;

	}

	@Override
	public Map<String, Object> getInstituteProductsAllReportData() {
		Map<String, Object> resp = new HashMap<>();
		List<Institute> institutes = new ArrayList<>();
		List<Product> products = new ArrayList<>();
		try {

			institutes = instRepo.getInstituteMimDetails();

			products = productRepo.findAll();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		resp.put("Institutes", institutes);
		resp.put("Products", products);
		resp.put("ServiceUnders", ServiceUnder.values());
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getInstituteProductsAll(InstituteProductsRequest ipr) {
		Map<String, Object> resp = new HashMap<>();
		List<InstituteProducts> ips = new ArrayList<>();
		try {
			String filterQuery = "";
			if (ipr.getInstitutes().size() > 0) {
				String instituteIds = "'0'";
				for (Institute inst : ipr.getInstitutes()) {
					instituteIds = instituteIds + ",'" + inst.getInstituteId() + "'";
				}
				filterQuery = filterQuery + " and ip.institute in (" + instituteIds + ") ";
			}
			if (ipr.getProducts().size() > 0) {
				String products = "0";
				for (Product product : ipr.getProducts()) {
					products = products + "," + product.getId() + "";
				}
				filterQuery = filterQuery + " and ip.product in (" + products + ") ";
			}
			if (ipr.getServiceUnders().size() > 0) {
				String serviceUnder = "'0'";
				for (ServiceUnder su : ipr.getServiceUnders()) {
					serviceUnder = serviceUnder + ",'" + su + "'";
				}
				filterQuery = filterQuery + " and ip.currentServiceUnder in (" + serviceUnder + ") ";
			}
			if (ipr.getFromDate() != null || ipr.getToDate() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				filterQuery = filterQuery + " and ip.amcExpiryDate between '" + sdf.format(ipr.getFromDate())
						+ "' and '" + sdf.format(ipr.getToDate()) + "'";
			}
			String qry = "select ip from InstituteProducts ip "
					+ " INNER JOIN FETCH ip.institute i INNER JOIN FETCH ip.product p where 2 > 1 " + filterQuery;

			System.out.println(qry);

			Query query = em.createQuery(qry, InstituteProducts.class);

			ips = query.getResultList();
			System.out.println("getPrepareAMCDashboardData::" + ipr.getPrepareAMCDashboardData());
			if (ipr.getPrepareAMCDashboardData()) {
				resp = getPrepareAMCDashboardData(ips);
			}

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}

		resp.put("InstituteProducts", ips);
		return resp;
	}

	Map<String, Object> getPrepareAMCDashboardData(List<InstituteProducts> ips) {

		Map<String, Object> resp = new HashMap<>();

		List<InstituteProductsTabData> _reminders_instituteProducts_0_10 = new ArrayList<>();
		List<InstituteProductsTabData> _reminders_instituteProducts_11_30 = new ArrayList<>();
		List<InstituteProductsTabData> _reminders_instituteProducts_31_60 = new ArrayList<>();
		List<InstituteProductsTabData> _reminders_instituteProducts_61_90 = new ArrayList<>();

		List<InstituteProductsTabData> _reminders_instituteProducts_expired_30 = new ArrayList<>();
		List<InstituteProductsTabData> _reminders_instituteProducts_expired = new ArrayList<>();
		List<InstituteProductsTabData> _warranty_instituteProducts = new ArrayList<>();
		List<InstituteProductsTabData> _amc_instituteProducts = new ArrayList<>();
		List<InstituteProductsTabData> _servicecall_instituteProducts = new ArrayList<>();
		List<InstituteProductsTabData> _notinany_instituteProducts = new ArrayList<>();
		List<InstituteProductsTabData> _all_instituteProducts = new ArrayList<>();

		try {

			List<String> instIds = ips.parallelStream().map(ip -> {

				ip.setDaysDiff(getDayDiff(ip.getAmcExpiryDate()));
				ip.setExpiresIn(getDayDiffText(ip.getDaysDiff()));

				return ip.getInstitute().getInstituteId();
			}).distinct().collect(Collectors.toList());

			instIds.parallelStream().forEach(id -> {

//				System.out.println("id::::::" + id);

				InstituteProductsTabData tabData = new InstituteProductsTabData();

				tabData.institute = ips.stream().filter(ip -> ip.getInstitute().getInstituteId().equalsIgnoreCase(id))
						.map(ip -> ip.getInstitute()).findFirst().orElse(null);

				if (tabData.institute != null) {
//					System.out.println(
//							"id::" + tabData.institute.getInstituteId() + "--" + tabData.institute.getInstituteName());
					tabData.products = ips.stream()
							.filter(ip -> ip.getInstitute().getInstituteId().equalsIgnoreCase(id))
							.collect(Collectors.toList());

					tabData.products.forEach(prod -> {

						// Adding Warranty, AMC, ServiceCall, NotInAnyService
						tabData.showIn.add(String.valueOf(prod.getCurrentServiceUnder()));
//						System.out.println("getCurrentServiceUnder::" + String.valueOf(prod.getCurrentServiceUnder()));

						if (prod.getDaysDiff() > -31 && prod.getDaysDiff() < 0)
							tabData.showIn.add("last 30 days");
						else if (prod.getDaysDiff() < 0)
							tabData.showIn.add("< 0 days");
						else if (prod.getDaysDiff() >= 0 && prod.getDaysDiff() <= 10)
							tabData.showIn.add("0-10 days");
						else if (prod.getDaysDiff() >= 11 && prod.getDaysDiff() <= 30)
							tabData.showIn.add("11-30 days");
						else if (prod.getDaysDiff() >= 31 && prod.getDaysDiff() <= 60)
							tabData.showIn.add("31-60 days");
						else if (prod.getDaysDiff() >= 61 && prod.getDaysDiff() <= 90)
							tabData.showIn.add("61-90 days");
					});
//					System.out.println(tabData);
					_all_instituteProducts.add(tabData);
				} else {
					System.err.println("Institute Not Found::" + id);
				}
			});

			_reminders_instituteProducts_0_10 = _all_instituteProducts.stream()
					.filter(ip -> ip.showIn.contains("0-10 days")).collect(Collectors.toList());
			_reminders_instituteProducts_11_30 = _all_instituteProducts.stream()
					.filter(ip -> ip.showIn.contains("11-30 days")).collect(Collectors.toList());
			_reminders_instituteProducts_31_60 = _all_instituteProducts.stream()
					.filter(ip -> ip.showIn.contains("31-60 days")).collect(Collectors.toList());
			_reminders_instituteProducts_61_90 = _all_instituteProducts.stream()
					.filter(ip -> ip.showIn.contains("61-90 days")).collect(Collectors.toList());

			_reminders_instituteProducts_expired_30 = _all_instituteProducts.stream()
					.filter(ip -> ip.showIn.contains("last 30 days")).collect(Collectors.toList());
			_reminders_instituteProducts_expired = _all_instituteProducts.stream()
					.filter(ip -> ip.showIn.contains("< 0 days")).collect(Collectors.toList());

			_warranty_instituteProducts = _all_instituteProducts.stream().filter(ip -> ip.showIn.contains("Warranty"))
					.collect(Collectors.toList());
			_amc_instituteProducts = _all_instituteProducts.stream().filter(ip -> ip.showIn.contains("AMC"))
					.collect(Collectors.toList());
			_servicecall_instituteProducts = _all_instituteProducts.stream()
					.filter(ip -> ip.showIn.contains("ServiceCall")).collect(Collectors.toList());
			_notinany_instituteProducts = _all_instituteProducts.stream()
					.filter(ip -> ip.showIn.contains("NotInAnyService")).collect(Collectors.toList());

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.put("_reminders_instituteProducts_0_10", _reminders_instituteProducts_0_10);
		resp.put("_reminders_instituteProducts_11_30", _reminders_instituteProducts_11_30);
		resp.put("_reminders_instituteProducts_31_60", _reminders_instituteProducts_31_60);
		resp.put("_reminders_instituteProducts_61_90", _reminders_instituteProducts_61_90);

		resp.put("_reminders_instituteProducts_expired_30", _reminders_instituteProducts_expired_30);
		resp.put("_reminders_instituteProducts_expired", _reminders_instituteProducts_expired);
		resp.put("_warranty_instituteProducts", _warranty_instituteProducts);
		resp.put("_amc_instituteProducts", _amc_instituteProducts);
		resp.put("_servicecall_instituteProducts", _servicecall_instituteProducts);
		resp.put("_notinany_instituteProducts", _notinany_instituteProducts);
		resp.put("_all_instituteProducts", _all_instituteProducts);

		Map<String, Object> tabDataMap = new HashMap<>();

		tabDataMap.put("Expires 0-10 days", _reminders_instituteProducts_0_10);
		tabDataMap.put("Expires 11-30 days", _reminders_instituteProducts_11_30);
		tabDataMap.put("Expires 31-60 days", _reminders_instituteProducts_31_60);
		tabDataMap.put("Expires 61-90 days", _reminders_instituteProducts_61_90);

		tabDataMap.put("Expired last 30 days", _reminders_instituteProducts_expired_30);
		tabDataMap.put("Expired", _reminders_instituteProducts_expired);
		tabDataMap.put("Under Warranty", _warranty_instituteProducts);
		tabDataMap.put("Under AMC", _amc_instituteProducts);
		tabDataMap.put("Service Call", _servicecall_instituteProducts);
		tabDataMap.put("Not In Any Service", _notinany_instituteProducts);
		tabDataMap.put("All AMC Records", _all_instituteProducts);

		resp.put("Tabs",
				Arrays.asList("Expired last 30 days", "Expires 0-10 days", "Expires 11-30 days", "Expires 31-60 days",
						"Expires 61-90 days", "Expired", "Under Warranty", "Under AMC", "Service Call",
						"Not In Any Service", "All AMC Records"));

		resp.put("TabData", tabDataMap);

		return resp;
	}

	long getDayDiff(Date amcExpiryDate) {
		return Days.daysBetween(LocalDate.parse(Util.sdfFormatter(new Date())),
				LocalDate.parse(Util.sdfFormatter(amcExpiryDate))).getDays();
	}

	String getDayDiffText(long diffDays) {
		if (diffDays == 0)
			return "Expires today";
		else if (diffDays > 0)
			return "Expires in " + diffDays + " day(s)";
		else
			return "Expired " + Math.abs(diffDays) + " day(s) ago";
	}

	@Override
	public Map<String, Object> saveInstituteProductsAll(InstituteProductsRequest ipr) {
		Map<String, Object> resp = new HashMap<>();
		try {

			instProductRepo.saveAll(ipr.getInstituteProducts());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}

		return resp;
	}

	@Override
	public Map<String, Object> InstituteAmc(AMCDetails amc) {
		Map<String, Object> resp = new HashMap<>();
		try {
			resp.put("AMCDetails", instAmcRepo.findByInstitute(amc.getInstitute()));

			resp.putAll(Util.SuccessResponse());
		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		return resp;
	}

	@Override
	public Map<String, Object> createInvoice(InvoiceRequest ir) {
		Map<String, Object> resp = new HashMap<>();
		try {

			final InputStream stream = this.getClass().getResourceAsStream("/reports/Amc_Quote_Multi.jrxml");

			final JasperReport report = JasperCompileManager.compileReport(stream);

//			final JasperReport report = (JasperReport) JRLoader.loadObject(stream);

			final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(ir.getInvoiceProducts());

			final Map<String, Object> parameters = new HashMap<>();
			parameters.put("amc_amount", "Rs." + ir.getInvoice().getTotalAmount());
			parameters.put("institute_name", ir.getInvoice().getInstitute().getInstituteName());
			parameters.put("address", ir.getInvoice().getInstitute().getCity() + ", "
					+ ir.getInvoice().getInstitute().getState() + " - " + ir.getInvoice().getInstitute().getZipcode());
			parameters.put("gst", "18%");

			parameters.put("total_amount", "Rs." + ir.getInvoice().getTotalAmount());
			parameters.put("gst_amount", "Rs." + ir.getInvoice().getGstAmount());
			parameters.put("invoice_no", ir.getInvoice().getInvoiceNo());

			final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

			File directory = new File(contentPath + "_institute_amc_invoices" + "/");
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdir();
			}
			final String filePath = directory.getAbsolutePath() + "/" + ir.getInvoice().getInvoiceNo() + ".pdf";
			System.out.println(filePath);
			// Export the report to a PDF file.
			JasperExportManager.exportReportToPdfFile(print, filePath);

		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		return resp;
	}

	@Override
	public Map<String, Object> loadAMCDetails(AMCSearchRequest amcSearchReq) {
		Map<String, Object> resp = new HashMap<>();
		// List amcDetails = new ArrayList();
		String filterQuery = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			if (amcSearchReq.getInstitutes() != null && amcSearchReq.getInstitutes().size() > 0) {
				String instituteIds = "0";
				for (Institute inst : amcSearchReq.getInstitutes()) {
					instituteIds = instituteIds + "," + inst.getInstituteId();
				}
				filterQuery = filterQuery + " and a.institute in (" + instituteIds + ") ";
			}
			if (amcSearchReq.getPayMode() != null && amcSearchReq.getPayMode() != "") {
				filterQuery = filterQuery + " and a.payMode like '%" + amcSearchReq.getPayMode() + "%'";
			}
			if (amcSearchReq.getAmcId() != null && amcSearchReq.getAmcId() != "") {
				filterQuery = filterQuery + " and a.amcId like '%" + amcSearchReq.getAmcId() + "%'";
			}
			if (amcSearchReq.getTransDetails() != null && amcSearchReq.getTransDetails() != "") {
				filterQuery = filterQuery + " and a.transactionDetails like '%" + amcSearchReq.getTransDetails() + "%'";
			}
			if (amcSearchReq.getServiceType() != null && amcSearchReq.getServiceType() != "") {
				filterQuery = filterQuery + " and a.serviceType like '%" + amcSearchReq.getServiceType() + "%'";
			}
			if (amcSearchReq.getAmcPaidDateFrom() != null && amcSearchReq.getAmcPaidDateTo() != null) {
				filterQuery = filterQuery + " and a.paidDate between '" + sdf.format(amcSearchReq.getAmcPaidDateFrom())
						+ "' and '" + sdf.format(amcSearchReq.getAmcPaidDateTo()) + " 23:59:59'";
			}
			if (amcSearchReq.getAmcId() != null && amcSearchReq.getAmcId() != "") {
				filterQuery = filterQuery + " and a.amcId like '%" + amcSearchReq.getAmcId() + "%'";
			}
			if (amcSearchReq.getTransDetails() != null && amcSearchReq.getTransDetails() != "") {
				filterQuery = filterQuery + " and a.transactionDetails like '%" + amcSearchReq.getTransDetails() + "%'";
			}
			if (amcSearchReq.getAmcProducts() != null && amcSearchReq.getAmcProducts().size() > 0) {
				String productIds = "0";
				for (Product p : amcSearchReq.getAmcProducts()) {
					productIds = productIds + "," + p.getId();
				}
				filterQuery = filterQuery + " and a.product in (" + productIds + ") ";
			}
			System.out.println(":::Filtersss::::" + filterQuery);
			Query query = em.createQuery(
					"SELECT new com.autolib.helpdesk.Institutes.model.AMCDetailResp(a,p) FROM AMCDetails a JOIN Product p ON (a.product = p.id)"
							+ filterQuery,
					AMCDetailResp.class);

			List amcDetails = query.getResultList();

			resp.put("amcDetails", amcDetails);

		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public Map<String, Object> getAmcDetailsEdit(int aid) {
		// logger.info("Get AMCDetails DAOImpl Starts:::::::"+aid);
		Map<String, Object> resp = new HashMap<>();
		AMCDetails ad = new AMCDetails();
		try {

			ad = instAmcRepo.findById(aid);
			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("AMCdetails", ad);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getInstituteProductsExpiryReminder() {
		Map<String, Object> resp = new HashMap<>();
		List<InstituteProducts> ips = new ArrayList<>();
		try {

			String filterQuery = "";

			Query query = em.createQuery("select ip from InstituteProducts ip "
					+ " INNER JOIN FETCH ip.institute i INNER JOIN FETCH ip.product p where 2 > 1 and "
					+ "datediff(ip.amcExpiryDate , '" + Util.getDateYMD()
					+ "') in (-15 , -7 , -1 ,0 , 1 , 7 , 15 , 30) " + filterQuery, InstituteProducts.class);
			ips = query.getResultList();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("InstituteProducts", ips);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getInstituteAMCReminderSentReport(AMCReminderRequest amcReminderReq) {
		Map<String, Object> resp = new HashMap<>();
		List<LogsSchedulerInvoice> logs = new ArrayList<>();
		try {

			String filterQuery = "";

			if (amcReminderReq.getInstitutes().size() > 0) {
				String instituteIds = "0";
				for (Institute inst : amcReminderReq.getInstitutes()) {
					instituteIds = instituteIds + "," + inst.getInstituteId();
				}
				filterQuery = filterQuery + " and log.institute in (" + instituteIds + ") ";
			}

			if (amcReminderReq.getFromDate() != null || amcReminderReq.getToDate() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				filterQuery = filterQuery + " and log.createddatetime between '"
						+ sdf.format(amcReminderReq.getFromDate()) + "' and '" + sdf.format(amcReminderReq.getToDate())
						+ "'";
			}

			Query query = em.createQuery("select log from LogsSchedulerInvoice log "
					+ " INNER JOIN FETCH log.institute i where 2 > 1 " + filterQuery, LogsSchedulerInvoice.class);
			logs = query.getResultList();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("Logs", logs);

		return resp;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getInstContact(InstituteContactRequest instContactReq) {
		Map<String, Object> resp = new HashMap<>();
		// List amcDetails = new ArrayList();
		String filterQuery = "";
		List<InstituteContactResponse> contactDetails = new ArrayList<>();

		try {
			if (instContactReq.getInstitutes() != null && instContactReq.getInstitutes().size() > 0) {
				String instituteIds = "0";
				for (Institute inst : instContactReq.getInstitutes()) {
					instituteIds = instituteIds + "," + inst.getInstituteId();
				}
				filterQuery = filterQuery + " and ic.instituteId in (" + instituteIds + ") ";
			}
			if (instContactReq.getFirstName() != null && instContactReq.getFirstName() != "") {
				filterQuery = filterQuery + " and ic.firstName like '%" + instContactReq.getFirstName() + "%'";
			}
			if (instContactReq.getEmailId() != null && instContactReq.getEmailId() != "") {
				filterQuery = filterQuery + " and ic.emailId like '%" + instContactReq.getEmailId() + "%'";
			}
			if (instContactReq.getPhone() != null && instContactReq.getPhone() != "") {
				filterQuery = filterQuery + " and ic.phone like '%" + instContactReq.getPhone() + "%'";
			}
			if (instContactReq.getCity() != null && instContactReq.getCity() != "") {
				filterQuery = filterQuery + " and ic.city like '%" + instContactReq.getCity() + "%'";
			}
			if (instContactReq.getIsBlocked() != null) {
				filterQuery = filterQuery + " and ic.isBlocked like '%" + instContactReq.getIsBlocked() + "%'";
			}

			System.out.println(":::Filtersss::::" + filterQuery);

			Query query = em.createQuery(
					"SELECT new com.autolib.helpdesk.Institutes.model.InstituteContactResponse(i , ic) FROM InstituteContact ic left join fetch Institute i on (ic.instituteId = i.instituteId) where 2>1 "
							+ filterQuery,
					InstituteContactResponse.class);
			contactDetails = query.getResultList();
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("contactDetails", contactDetails);
		return resp;
	}

	public Map<String, Object> getInstituteReport(InstituteRequest institute) {
		Map<String, Object> resp = new HashMap<>();
		String filterQuery = "";

		try {
			if (institute.getInstituteId() != null && institute.getInstituteId() != "") {
				filterQuery = filterQuery + " and i.instituteId = '" + institute.getInstituteId() + "'";
			}
			if (institute.getInstitutes() != null && institute.getInstitutes().size() > 0) {
				String instituteIds = "'0'";
				for (Institute inst : institute.getInstitutes()) {
					instituteIds = instituteIds + "," + inst.getInstituteId();
				}
				filterQuery = filterQuery + " and i.instituteId in (" + instituteIds + ") ";
			}
			if (institute.getInstituteType() != null && institute.getInstituteType() != "") {
				filterQuery = filterQuery + " and i.instituteType like '%" + institute.getInstituteType() + "%'";
			}
			if (institute.getEmailId() != null && institute.getEmailId() != "") {
				filterQuery = filterQuery + " and i.emailId like '%" + institute.getEmailId() + "%'";
			}
			if (institute.getPhone() != null && institute.getPhone() != "") {
				filterQuery = filterQuery + " and i.phone like '%" + institute.getPhone() + "%'";
			}
			if (institute.getCity() != null && institute.getCity() != "") {
				filterQuery = filterQuery + " and i.city like '%" + institute.getCity() + "%'";
			}
			if (institute.getAlternateEmailId() != null && institute.getAlternateEmailId() != "") {
				filterQuery = filterQuery + " and i.alternateEmailId like '%" + institute.getAlternateEmailId() + "%'";
			}
			if (institute.getAlternatePhone() != null && institute.getAlternatePhone() != "") {
				filterQuery = filterQuery + " and i.alternatePhone like '%" + institute.getAlternatePhone() + "%'";
			}
			if (institute.getShortTerm() != null && institute.getShortTerm() != "") {
				filterQuery = filterQuery + " and i.shortTerm like '%" + institute.getShortTerm() + "%'";
			}

			System.out.println(":::FilterQuery::::::" + filterQuery);

			Query query = em.createQuery("select i from Institute i where 2>1" + " " + filterQuery);
			List<InstituteRequest> instReport = query.getResultList();
			resp.put("instReport", instReport);

		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		return resp;
	}

	@Modifying
	@Transactional
	@Override
	public Map<String, Object> saveInstituteLogo(MultipartFile file, String instituteId) {
		Map<String, Object> resp = new HashMap<>();
		Institute ins = new Institute();
		try {

			ins = instRepo.findByInstituteId(instituteId);
			ins.setInstituteId(instituteId);
			System.out.println(ins.getInstituteName());
			String logoURL = instituteId + "_" + ins.getInstituteName() + ".jpg";
			ins.setLogourl(logoURL);

			byte[] bytes = file.getBytes();
			File directory = new File(contentPath + "/InstituteLogo/");

			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}

			OutputStream out = new FileOutputStream(contentPath + "/InstituteLogo/" + logoURL);
			out.write(bytes);
			out.close();

			if (ins.getLogourl() == null) {

				ins = instRepo.save(ins);
			}

			else {
				Query query = em
						.createQuery("Update Institute a set a.logourl = :logourl WHERE a.instituteId = :instituteId");
				query.setParameter("logourl", logoURL);
				query.setParameter("instituteId", instituteId);
				query.executeUpdate();

			}

			resp.put("Institute", ins);
			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.putAll(Util.FailedResponse(ex.getMessage()));
		}
		return resp;
	}

	@Override
	public Map<String, Object> saveInstituteImport(InstituteImportReq request) {

		System.out.println("saveAccountStatement DAOImpl method ends::::::");

		Map<String, Object> respMap = new HashMap<>();

		try {
			System.out.println("::Length::" + request.getInstitute().size());
			// row = request.getInstitute().getRow(request.getInstitute().size());

			request.getInstitute().forEach(inst -> {
				String Id = String.valueOf(Integer.parseInt(instRepo.findLastMaxId()) + 1);
				System.out.println("Old Id:::" + instRepo.findLastMaxId());
				System.out.println("New Id:::::" + Id);
				System.out.println("Name::::::" + inst.getInstituteName());

				int instCount = jdbcTemp
						.queryForObject("select count(*) as count from institutes WHERE institute_name ='"
								+ inst.getInstituteName() + "'", Integer.class);
				System.out.println(":::count:::" + instCount);
				if (instCount > 0) {
					System.out.println("::Inside If:::");
					respMap.putAll(Util.invalidMessage("'" + inst.getInstituteName() + "' is Already Exist"));
				} else {
					System.out.println("::Inside else:::");
					inst.setInstituteId(Id);
					instRepo.save(inst);
					respMap.putAll(Util.SuccessResponse());
				}

			});
			// instRepo.saveAll(request.getInstitute());

		} catch (Exception ex) {
			ex.printStackTrace();
			respMap.putAll(Util.FailedResponse(ex.getMessage()));
		}
		return respMap;
	}

	@Override
	public Map<String, Object> updateInstituteImport(InstituteImportReq request) {

		System.out.println("updateAccountStatement DAOImpl method ends::::::");

		Map<String, Object> respMap = new HashMap<>();
		String filterQuery = "";

		try {
//			request.getInstitute().removeIf(Objects::isNull);
//			System.out.println(":::Null Removal:::::"+request.getInstitute()..removeIf(Objects::isNull));
//			colors = colors.stream()
//            .map(x -> (x == null) ? "####" : x)
//            .collect(Collectors.toList());

			request.getInstitute().forEach(inst -> {
				String filter = "";

				if (!inst.getAgents().isEmpty() && inst.getAgents() != null) {
					filter = filter + " ,agents = '" + inst.getAgents() + "'";
				}
//				if(!inst.getInstituteName().isEmpty())
//				{
//					filter = filter + " ,institute_name = '" + inst.getInstituteName() +"'";
//				}
				if (!inst.getShortTerm().isEmpty() && inst.getShortTerm() != null) {
					filter = filter + " ,short_term = '" + inst.getShortTerm() + "'";
				}
				if (!inst.getInstituteType().isEmpty() && inst.getInstituteType() != null) {
					filter = filter + " ,institute_type = '" + inst.getInstituteType() + "'";
				}
				if (inst.getPhone().equals(null)) {
					System.out.println("::::Inside Null check");
					filter = filter + "";
				}
				if (!inst.getPhone().isEmpty() && inst.getPhone() != null) {
					filter = filter + " ,phone = '" + inst.getPhone() + "'";
				}
				if (!inst.getEmailId().isEmpty() && inst.getEmailId() != null) {
					filter = filter + " ,email_id = '" + inst.getEmailId() + "'";
				}
				if (!inst.getAlternatePhone().isEmpty() && inst.getAlternatePhone() != null) {
					filter = filter + " ,alternate_phone = '" + inst.getAlternatePhone() + "'";
				}
				if (!inst.getAlternateEmailId().isEmpty() && inst.getAlternateEmailId() != null) {
					filter = filter + " ,alternate_email_id = '" + inst.getAlternateEmailId() + "'";
				}
				if (!inst.getStreet1().isEmpty() && inst.getStreet1() != null) {
					filter = filter + " ,street1 = '" + inst.getStreet1() + "'";
				}
				if (!inst.getStreet2().isEmpty() && inst.getStreet2() != null) {
					filter = filter + " ,street2 = '" + inst.getStreet2() + "'";
				}
				if (!inst.getCity().isEmpty() && inst.getCity() != null) {
					filter = filter + " ,city = '" + inst.getCity() + "'";
				}
				if (!inst.getState().isEmpty() && inst.getState() != null) {
					filter = filter + " ,state = '" + inst.getState() + "'";
				}
				if (!inst.getZipcode().isEmpty() && inst.getZipcode() != null) {
					filter = filter + " ,zipcode = '" + inst.getZipcode() + "'";
				}
				if (!inst.getGstno().isEmpty() && inst.getGstno() != null) {
					filter = filter + " ,gstno = '" + inst.getGstno() + "'";
				}
				if (!inst.getKeyInfo().isEmpty() && inst.getKeyInfo() != null) {
					filter = filter + " ,key_info = '" + inst.getKeyInfo() + "'";
				}
				if (!inst.getRemarks().isEmpty() && inst.getRemarks() != null) {
					filter = filter + " ,remarks = '" + inst.getRemarks() + "'";
				}

				System.out.println("::::Filter_Fields::::" + filter);
				String filter1 = filter.substring(2);
				// System.out.println("::::Check:::"+filter1);
				jdbcTemp.update(
						"update institutes set " + filter1 + " where institute_name='" + inst.getInstituteName() + "'");

				// instRepo.save(inst);
				respMap.putAll(Util.SuccessResponse());
			});
			// instRepo.saveAll(request.getInstitute());

		} catch (Exception ex) {
			ex.printStackTrace();
			respMap.putAll(Util.FailedResponse(ex.getMessage()));
		}
		return respMap;
	}

	@Override
	public Map<String, Object> getInvoiceAMCEntries(String req) {
		Map<String, Object> resp = new HashMap<>();
		// AMCDetails amcDetails = new AMCDetails();

		try {
			// amcDetails = instAmcRepo.findByAmcId(req);
			Query query = em.createQuery("Select ad from AMCDetails ad where amcId like '%" + req + "%'");
			resp.put("InvAmcDetails", query.getResultList());
			resp.putAll(Util.SuccessResponse());

		} catch (Exception Ex) {
			Ex.printStackTrace();
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
		}
		return resp;
	}

}

class InstituteProductsTabData {
	public Institute institute;
	public List<InstituteProducts> products = new ArrayList<>();
	public List<String> showIn = new ArrayList<>();

	@Override
	public String toString() {
		return "InstituteProductsTabData [institute=" + institute + ", products=" + products + ", showIn=" + showIn
				+ "]";
	}

}