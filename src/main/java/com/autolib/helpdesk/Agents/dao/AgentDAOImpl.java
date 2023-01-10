package com.autolib.helpdesk.Agents.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.autolib.helpdesk.Agents.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Agents.repository.AgentRepository;
import com.autolib.helpdesk.Agents.repository.InfoDetailsRepository;
import com.autolib.helpdesk.Agents.repository.ProductsRawMaterialsRepository;
import com.autolib.helpdesk.Agents.repository.ProductsRepository;
import com.autolib.helpdesk.Agents.repository.RawMaterialRequestProductsRepository;
import com.autolib.helpdesk.Agents.repository.RawMaterialRequestRepository;
import com.autolib.helpdesk.Agents.repository.RoleMasterRepository;
import com.autolib.helpdesk.Agents.repository.StockDetailResp;
import com.autolib.helpdesk.Agents.repository.StockEntryRepository;
import com.autolib.helpdesk.Agents.repository.VendorRepository;
import com.autolib.helpdesk.Agents.service.AgentService;
import com.autolib.helpdesk.Attendance.repository.AttendanceRepository;
import com.autolib.helpdesk.HR.entity.LeaveBalance;
import com.autolib.helpdesk.HR.entity.LeaveMaster;
import com.autolib.helpdesk.HR.repository.LeaveBalanceRepository;
import com.autolib.helpdesk.HR.repository.LeaveMasterRepository;
import com.autolib.helpdesk.Tickets.repository.TicketRepository;
import com.autolib.helpdesk.common.DBQueryUtil;
import com.autolib.helpdesk.common.EmailModel;
import com.autolib.helpdesk.common.EmailSender;
import com.autolib.helpdesk.common.EnumUtils.TicketStatus;
import com.autolib.helpdesk.common.Util;

@Repository
public class AgentDAOImpl implements AgentDAO {

    @Autowired
    TicketRepository ticketRepo;

    @Autowired

    private EmailSender emailSender;

    @Autowired
    AgentRepository agentRepo;

    @Autowired
    AgentPhotoUtil agentPhotoUtil;

    @Autowired
    private JdbcTemplate jdbcTemp;

    @Autowired
    InfoDetailsRepository infoDetailRepo;

    @Autowired
    private ProductsRepository productRepo;

    @Autowired
    private ProductsRawMaterialsRepository productRawRepo;

    @Autowired
    AttendanceRepository attRepo;

    @Autowired
    private VendorRepository vendorRepo;

    @Autowired
    private StockEntryRepository stockEntryRepo;

    @Autowired
    private RoleMasterRepository roleRepo;

    @Autowired
    private LeaveMasterRepository leaveMasterRepo;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepo;

    @Autowired
    RawMaterialRequestRepository rawMaterialRequestRepo;

    @Autowired
    RawMaterialRequestProductsRepository rawMaterialRequestProductsRepo;

    @Autowired
    AgentService agentService;

    @Autowired
    EntityManager em;

    @Value("${al.agnet.raw_material_request.url}")
    String viewRawMaterialRequestURI;

    @Value("${al.ticket.content-path}")
    private String contentPath;

    @Override
    public Map<String, Object> getHomePageDetails(Agent agent) {
        Map<String, Object> resp = new HashMap<>();
        try {

            resp.put("Stats", ticketRepo.findHomeStats(agent.getEmailId()));

            resp.put("AssignedToMeTickets", ticketRepo.findByAssignedToAndStatusNotIn(agent.getEmailId(),
                    Arrays.asList(TicketStatus.Closed, TicketStatus.Rejected)));

            resp.put("AssignedByMeTickets", ticketRepo.findByAssignedByAndStatusNotIn(agent.getEmailId(),
                    Arrays.asList(TicketStatus.Closed, TicketStatus.Rejected)));

            resp.put("AssignedToMeClosedTickets", ticketRepo.getAssignedToMeClosedTickets(agent.getEmailId()));

            resp.put("AssignedByMeClosedTickets", ticketRepo.getAssignedByMeClosedTickets(agent.getEmailId()));

            resp.put("UnAssignedTickets", ticketRepo.findByAssignedToAndStatus(null, TicketStatus.Raised));

            resp.put("Attendance", attRepo.findByEmployeeIdAndWorkingDate(agent.getEmployeeId(), new Date()));


            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }

        return resp;
    }

    @Override
    public Map<String, Object> getAllAgentTickets(Agent agent) {
        Map<String, Object> resp = new HashMap<>();
        try {

            resp.put("AssignedToMeTickets", ticketRepo.findByAssignedTo(agent.getEmailId()));

            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        return resp;
    }

    @Override
    public Map<String, Object> addAgent(Agent agent) {
        Map<String, Object> resp = new HashMap<>();
        EmailModel emailModel = new EmailModel("Common");
        try {

            Agent _agent = agentRepo.findByEmailId(agent.getEmailId());

            if (agent.getEmployeeId() == null || agent.getEmployeeId().isEmpty()) {
                resp.putAll(Util.invalidMessage("Employee Id Cannot be empty"));
            } else if (_agent != null && !_agent.getEmployeeId().equalsIgnoreCase(agent.getEmployeeId())) {
                resp.putAll(Util.invalidMessage(
                        "Email Id Already Exist with '" + _agent.getEmployeeId() + "'. Try with Another Email ID"));
            } else {

                if (agent.getPassword() == null || agent.getPassword().isEmpty()) {

                    char[] _r_password = Util.generateRandomPassword();

                    String hsdpwd = "";

                    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
                    hsdpwd = bcrypt.encode(String.valueOf(_r_password));

                    agent.setPassword(hsdpwd);

                    emailModel.setMailFrom(agent.getEmailId());
                    emailModel.setMailTo(agent.getEmailId());
                    emailModel.setMailSub("Welcome " + agent.getFirstName() + " - Registered Successfully");
                    StringBuffer sb = new StringBuffer();
                    sb.append("Welcome " + agent.getFirstName());
                    sb.append("<br>");
                    emailModel.setMailText("Successfully Registered <br>" + "Your User Email : " + agent.getEmailId()
                            + "<br>Your Password  : " + String.valueOf(_r_password));

                    emailSender.sendmail(emailModel);

                    prepareDefaultLeaveBalance(agent);

                }

                agent.setPhotoFileName(agentPhotoUtil.createDefaultProfilePicture(agent.getFirstName(),
                        agent.getLastName(), agent.getEmployeeId()));

                agent = agentRepo.save(agent);

                resp.putAll(Util.SuccessResponse());
            }

        } catch (Exception Ex) {
            Ex.printStackTrace();
            resp.putAll(Util.FailedResponse(Ex.getMessage()));
        }
        resp.put("Agent", agent);
        return resp;
    }

    @Override
    public Map<String, Object> getAllAgents(Agent agent) {
        Map<String, Object> resp = new HashMap<>();
        Iterable<Agent> agents = new ArrayList<>();
        try {
            agents = agentRepo.findAll();

            resp.putAll(Util.SuccessResponse());

        } catch (Exception Ex) {
            Ex.printStackTrace();
            resp.putAll(Util.FailedResponse(Ex.getMessage()));
        }
        resp.put("Agents", agents);
        return resp;
    }

    @Override
    public Map<String, Object> getAgentDetails(String agentId) {
        Map<String, Object> resp = new HashMap<>();
        Agent agent = new Agent();
        try {

            agent = agentRepo.findByEmployeeId(agentId);
            agent.setPhoto(agentPhotoUtil.getProfilePhoto(agent.getPhotoFileName()));
            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("Agent", agent);
        return resp;
    }

    @Override
    public Map<String, Object> deleteAgent(Agent agent) {
        Map<String, Object> resp = new HashMap<>();

        try {
            agent = agentRepo.findByEmployeeId(agent.getEmployeeId());
            if (agent == null) {
                resp.putAll(Util.invalidMessage("Agent Not Found"));
            } else {
                agentRepo.delete(agent);
                resp.putAll(Util.SuccessResponse());
            }
        } catch (Exception Ex) {
            Ex.printStackTrace();
            resp.putAll(Util.FailedResponse(Ex.getMessage()));
        }

        return resp;
    }

    @Override
    public Map<String, Object> changeLeaveMaster(Map<String, Object> req) {
        Map<String, Object> resp = new HashMap<>();
        Agent agent = null;
        try {
            agent = agentRepo.findByEmailId(String.valueOf(req.get("emailId")));
            int newLeaveMasterId = Integer.parseInt(String.valueOf(req.get("newLeaveMasterId")));
            boolean deductTakenLeavesIfany = Boolean.parseBoolean(String.valueOf(req.get("deductTakenLeavesIfany")));
            agent.setLeaveMasterId(newLeaveMasterId);

            prepareDefaultLeaveBalance(agent);

            if (deductTakenLeavesIfany) {
                LeaveBalance balance = leaveBalanceRepo.findByEmailId(agent.getEmailId());

                List<Map<String, Object>> result = leaveBalanceRepo.getAgentLeaveCounts(agent.getEmailId());

                long annualLeave = result.stream()
                        .filter(_res -> String.valueOf(_res.get("leave_type")).equalsIgnoreCase("Annual Leave"))
                        .count();

                long casualLeave = result.stream()
                        .filter(_res -> String.valueOf(_res.get("leave_type")).equalsIgnoreCase("Casual Leave"))
                        .count();

                long sickLeave = result.stream()
                        .filter(_res -> String.valueOf(_res.get("leave_type")).equalsIgnoreCase("Sick Leave")).count();

                long otherLeave = result.stream()
                        .filter(_res -> String.valueOf(_res.get("leave_type")).equalsIgnoreCase("Other Leave")).count();

                long halfDay = result.stream()
                        .filter(_res -> String.valueOf(_res.get("leave_type")).equalsIgnoreCase("Half Day")).count();

                long permissions = result.stream()
                        .filter(_res -> String.valueOf(_res.get("leave_type")).equalsIgnoreCase("Permissions")).count();

                balance.setAnnualLeave(balance.getAnnualLeave() - annualLeave - (halfDay / 2));
                balance.setCasualLeave(balance.getCasualLeave() - casualLeave);
                balance.setSickLeave(balance.getSickLeave() - sickLeave);
                balance.setOtherLeave(balance.getOtherLeave() - otherLeave);
                balance.setPermissions(balance.getPermissions() - permissions);

                leaveBalanceRepo.save(balance);
            }

            agentRepo.save(agent);

            resp.putAll(Util.SuccessResponse());
        } catch (Exception Ex) {
            Ex.printStackTrace();
            resp.putAll(Util.FailedResponse(Ex.getMessage()));
        }

        resp.put("Agent", agent);
        return resp;
    }

    void prepareDefaultLeaveBalance(Agent agent) {
        try {

            LeaveMaster master = leaveMasterRepo.findById(agent.getLeaveMasterId());

            LeaveBalance balance = new LeaveBalance();
            balance.setEmailId(agent.getEmailId());

            if (master != null) {
                balance.setAnnualLeave(Double.valueOf(master.getAnnualLeave()));
                balance.setCasualLeave(Double.valueOf(master.getCasualLeave()));
                balance.setSickLeave(Double.valueOf(master.getSickLeave()));
                balance.setOtherLeave(Double.valueOf(master.getOtherLeave()));
                balance.setPermissions(Double.valueOf(master.getPermissions()));
            }

            System.out.println(balance.toString());

            leaveBalanceRepo.save(balance);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * @Override public Map<String, Object> instituteReport(Map<String, Object> req)
     * { System.out.println("instituteReport DAOImpl method starts::"); Map<String,
     * String> valueReqMap = new HashMap<String, String>(); int set_from = 0, set_to
     * = 0, setCount = 0; Map<String, Object> respListMap = new HashMap<>(); String
     * sqlQuery = ""; String namedQuery = ""; List institutionList = new
     * ArrayList<>(); try { valueReqMap = (Map<String, String>)
     * req.get("SearchInstitutionReq"); set_from =
     * Integer.parseInt(String.valueOf(valueReqMap.get("set_from"))); set_to =
     * Integer.parseInt(String.valueOf(valueReqMap.get("set_to")));
     *
     * String institute_id = valueReqMap.get("instituteId").toString(); String
     * institute_name = valueReqMap.get("instituteName").toString(); String
     * institute_type = valueReqMap.get("instituteType").toString(); String
     * product_type = valueReqMap.get("productType").toString(); String
     * service_under = valueReqMap.get("serviceUnder").toString();
     *
     * if (institute_id != null && !institute_id.isEmpty()) { namedQuery =
     * namedQuery + "and institute_id = '" + institute_id + "'"; } if
     * (institute_name != null && !institute_name.isEmpty()) { namedQuery =
     * namedQuery + " and institute_name like '%" + institute_name + "%'"; } if
     * (institute_type != null && !institute_type.isEmpty()) { namedQuery =
     * namedQuery + "and institute_type = '" + institute_type + "'"; } if
     * (product_type != null && !product_type.isEmpty()) { namedQuery = namedQuery +
     * "and product_type = '" + product_type + "'"; } if (service_under != null &&
     * !service_under.isEmpty()) { namedQuery = namedQuery + "and service_under = '"
     * + service_under + "'"; }
     *
     * String limitQuery = " limit " + set_from + " , " + set_to; String Query =
     * DBQueryUtil.instituteSearch + " " + namedQuery + limitQuery;
     * System.out.println(Query); institutionList = jdbcTemp.queryForList(Query);
     *
     * if (valueReqMap.containsKey("setCount")) { if
     * (Boolean.parseBoolean(String.valueOf(valueReqMap.get("setCount")))) {
     * setCount = jdbcTemp.queryForObject(DBQueryUtil.instituteSearch_Count + " " +
     * namedQuery, Integer.class); } }
     *
     * } catch (Exception e) { e.printStackTrace(); }
     * respListMap.put("institutionList", institutionList);
     * respListMap.put("setCount", setCount);
     * System.out.println("instituteReport DAOImpl method ends::"); return
     * respListMap; }
     */

    @SuppressWarnings({"unchecked", "unused", "rawtypes"})
    @Override
    public Map<String, Object> loadInstitute(Map<String, Object> req) {
        Map<String, Object> resMap = new HashMap<>();

        List instituteAllList = new ArrayList();
        List serviceTypeList = new ArrayList();
        List instituteTypeList = new ArrayList();
        Map<String, String> valueReqMap = new HashMap<String, String>();

        try {
            valueReqMap = (Map<String, String>) req.get("loadInstitutionReq");
            instituteAllList = jdbcTemp
                    .queryForList("SELECT DISTINCT institute_name FROM institutes ORDER BY institute_name ASC");
            instituteTypeList = jdbcTemp
                    .queryForList("SELECT DISTINCT institute_type FROM institutes ORDER BY institute_type ASC");
            serviceTypeList = jdbcTemp
                    .queryForList("SELECT DISTINCT service_under FROM institutes ORDER BY service_under ASC");

        } catch (Exception ex) {
            ex.printStackTrace();
            resMap.putAll(Util.FailedResponse());
        }

        resMap.put("instituteAllList", instituteAllList);
        resMap.put("serviceTypeList", serviceTypeList);
        resMap.put("instituteTypeList", instituteTypeList);
        resMap.putAll(Util.SuccessResponse());

        return resMap;
    }

    @Override
    public Map<String, Object> saveProduct(Product product) {

        Map<String, Object> respMap = new HashMap<>();

        try {
            if (product.getName() == null) {
                respMap.putAll(Util.invalidMessage("Product Name Cannot be empty"));
            } else {
                product = productRepo.save(product);
                respMap.putAll(Util.SuccessResponse());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.putAll(Util.FailedResponse(ex.getMessage()));
        }
        respMap.put("Product", product);
        return respMap;
    }

    @Override
    public Map<String, Object> deleteProduct(Product product) {

        Map<String, Object> respMap = new HashMap<>();

        try {

            product = productRepo.findById(product.getId());

            if (product == null)
                respMap.putAll(Util.invalidMessage("Product Not Found"));

            else
                productRepo.delete(product);
            respMap.putAll(Util.SuccessResponse());

        } catch (Exception Ex) {

            Ex.printStackTrace();
            respMap.putAll(Util.FailedResponse(Ex.getMessage()));
        }

        return respMap;
    }

    @Override
    public Map<String, Object> getProductDetails(int productId) {
        Map<String, Object> resp = new HashMap<>();
        Product product = new Product();
        try {

            product = productRepo.findById(productId);
            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("Product", product);
        return resp;
    }

    @Override
    public Map<String, Object> getAllProducts() {

        Map<String, Object> respMap = new HashMap<>();

        List<Product> productList = new ArrayList<>();

        try {

            productList = productRepo.findAll();

        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.putAll(Util.FailedResponse(ex.getMessage()));
        }
        respMap.put("productList", productList);
        respMap.putAll(Util.SuccessResponse());
        return respMap;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int sendOTP(Map<String, Object> req) {

        int saveUpdateCount = 0;
        Map<String, String> reqStrMap = (Map<String, String>) req.get("SendOTPReq");

        String email_id = reqStrMap.get("username");

        try {

            int memberCheck = jdbcTemp.queryForObject(
                    "SELECT COUNT(*) as count FROM agents WHERE email_id = '" + email_id + "'", Integer.class);

            System.out.println("memberCheck::::::" + memberCheck);

            if (memberCheck == 0) {
                saveUpdateCount = 2;
            } else {
                EmailModel emailModel = new EmailModel("Common");
                emailModel.setFromName("AutoLib Software Systems");
                emailModel.setMailTo(email_id);
                emailModel.setOtp(String.valueOf(Util.generateOTP()));
                emailModel.setMailText("Your OTP For Reset Password : " + emailModel.getOtp());
                emailModel.setMailSub("Your OTP For Reset Password : " + emailModel.getOtp());

                Object[] otpInserParam = {email_id, emailModel.getOtp()};
                saveUpdateCount = jdbcTemp.update(DBQueryUtil.Insert_Signup_OTP, otpInserParam);

                emailSender.sendmail(emailModel);
                System.out.println("emailSender::::");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return saveUpdateCount;
    }

    @SuppressWarnings("unchecked")
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
                    "select count(*) as count from agents where email_id = '" + email_id + "'", Integer.class);
            if (count > 0) {

                saveUpdateCount = jdbcTemp
                        .update("UPDATE agents SET password = '" + password + "' WHERE email_id ='" + email_id + "'");

                EmailModel emailModel = new EmailModel("Common");
                emailModel.setFromName("AutoLib Software Systems");
                emailModel.setMailTo(email_id);
                emailModel.setMailSub("Password Changed");

                StringBuffer sb = new StringBuffer();
                sb.append("Hi,<br>");
                // sb.append("Dear " + ic.getFirstName() + ", <br><br>");
                // sb.append("Your Password has been Successfully Changed.<br><br><br>"+"This is
                // a confirmation that your password was change to\");
                sb.append(
                        "Your Password has been Successfully Changed,The given below is the changed Password.<br><br>");
                sb.append("<br>Password : " + requestMap.get("new_password"));

                emailModel.setMailText(sb.toString());

                emailSender.sendmail(emailModel);

            }

        } catch (Exception ex) {
            System.out.println("resetPassword DAO ends::");
            ex.printStackTrace();
        }
        System.out.println("resetPassword DAO ends ::");
        return saveUpdateCount;

    }

    @SuppressWarnings("unchecked")
    @Override
    public int checkOTP(Map<String, Object> req) {

        int saveUpdateCount = 0;
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
                                + memberEmail + "' and otp = '" + otp + "' order by generated_time desc limit 1",
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
    public Map<String, Object> needed() {
        System.out.println("designationAll DAOImpl method starts::");
        Map<String, Object> respMap = new HashMap<>();
        List<String> designation = new ArrayList<>();
        List<String> category = new ArrayList<>();
        List<RoleMaster> roles = new ArrayList<>();
        List<LeaveMaster> leaveMaster = new ArrayList<>();
        try {
            designation = jdbcTemp.queryForList("select distinct designation from agents order by designation asc",
                    String.class);

            category = jdbcTemp.queryForList("select distinct category from agents order by category asc",
                    String.class);

            roles = roleRepo.findAll();

            leaveMaster = leaveMasterRepo.findAll();

        } catch (Exception Ex) {
            System.out.println(Ex);
            Ex.printStackTrace();
        }
        respMap.put("Roles", roles);
        respMap.put("LeaveMasters", leaveMaster);
        respMap.put("designationList", designation);
        respMap.put("categoryList", category);
        System.out.println("designationAll DAOImpl method ends::");
        return respMap;
    }

    @Override
    public Map<String, Object> saveInfoDetails(InfoDetails info) {
        System.out.println("inside infoDetails Dao Impl::::::::" + info);
        Map<String, Object> respMap = new HashMap<>();

        try {
            if (info.getCmpName() == null || info.getCmpName().isEmpty())
                respMap.putAll(Util.invalidMessage("Name Cannot be empty..!!"));
//			else if (info.getCmpAddress() == null || info.getCmpAddress().isEmpty())
//				respMap.putAll(Util.invalidMessage("Address Cannot be empty..!!"));
//			else if (info.getCmpPhone() == null || info.getCmpPhone().isEmpty())
//				respMap.putAll(Util.invalidMessage("Phone Cannot be empty..!!"));
//			else if (info.getCmpEmail() == null || info.getCmpEmail().isEmpty())
//				respMap.putAll(Util.invalidMessage("Email Cannot be empty..!!"));
//			else if (info.getGstNo() == null || info.getGstNo().isEmpty())
//				respMap.putAll(Util.invalidMessage("Gst Number Cannot be empty..!!"));
//			else if (info.getCmpWebsiteUrl() == null || info.getCmpWebsiteUrl().isEmpty())
//				respMap.putAll(Util.invalidMessage("Website Cannot be empty..!!"));
            else {
                info = infoDetailRepo.save(info);
                respMap.put("infoDetails", info);
                respMap.putAll(Util.SuccessResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respMap;
    }

    @Modifying
    @Transactional
    @Override
    public Map<String, Object> fileUpload(MultipartFile file) {
        System.out.println("inside fileUpload Dao Impl::::::::");
        Map<String, Object> respMap = new HashMap<>();
        InfoDetails infoDetail = new InfoDetails();
        try {

            infoDetail = infoDetailRepo.findById(infoDetail.getId());
            if (infoDetail != null) {
                infoDetail.setCmpLogo(file.getBytes());

                Query query = em.createQuery("Update InfoDetails info set info.cmpLogo = :cmpLogo");
                query.setParameter("cmpLogo", file.getBytes());
                query.executeUpdate();

                respMap.putAll(Util.SuccessResponse());
            } else
                respMap.putAll(Util.FailedResponse("Not a valid Company Details Found"));

        } catch (Exception e) {
            e.printStackTrace();
            respMap.putAll(Util.FailedResponse("File Too Large..!!"));
        }

        respMap.put("infoDetail", infoDetail);
        return respMap;
    }

    @Modifying
    @Transactional
    @Override
    public Map<String, Object> roundSealUpload(MultipartFile file) {
        System.out.println("inside roundSealUpload Dao Impl::::::::");
        Map<String, Object> respMap = new HashMap<>();
        InfoDetails infoDetail = new InfoDetails();
        try {

            infoDetail = infoDetailRepo.findById(infoDetail.getId());
            if (infoDetail != null) {
                infoDetail.setRoundSeal(file.getBytes());

                Query query = em.createQuery("Update InfoDetails info set info.roundSeal = :roundSeal");
                query.setParameter("roundSeal", file.getBytes());
                query.executeUpdate();

                respMap.putAll(Util.SuccessResponse());
            } else
                respMap.putAll(Util.FailedResponse("Not a valid Company Details Found"));

        } catch (Exception e) {
            e.printStackTrace();
            respMap.putAll(Util.FailedResponse("File Too Large..!!"));
        }
        respMap.put("infoDetail", infoDetail);

        return respMap;
    }

    @Modifying
    @Transactional
    @Override
    public Map<String, Object> fullSealUpload(MultipartFile file) {
        System.out.println("inside fullSealUpload Dao Impl::::::::");
        Map<String, Object> respMap = new HashMap<>();
        InfoDetails infoDetail = new InfoDetails();
        try {

            infoDetail = infoDetailRepo.findById(infoDetail.getId());
            if (infoDetail != null) {
                infoDetail.setFullSeal(file.getBytes());

                Query query = em.createQuery("Update InfoDetails info set info.fullSeal = :fullSeal");
                query.setParameter("fullSeal", file.getBytes());
                query.executeUpdate();

                respMap.putAll(Util.SuccessResponse());
            } else
                respMap.putAll(Util.FailedResponse("Not a valid Company Details Found"));

        } catch (Exception e) {
            e.printStackTrace();
            respMap.putAll(Util.FailedResponse("File Too Large..!!"));
        }
        respMap.put("infoDetail", infoDetail);

        return respMap;
    }

    @Override
    public Map<String, Object> getInfoDetails(InfoDetails info) {
        System.out.println("inside getInfoDetails Dao Impl::::::::");
        Map<String, Object> respMap = new HashMap<>();

        try {

            info = infoDetailRepo.findById(info.getId());

            respMap.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.putAll(Util.FailedResponse());
        }
        respMap.put("infoDetails", info);
        return respMap;
    }

    @Modifying
    @Transactional
    @Override
    public Map<String, Object> saveProfilePhoto(MultipartFile photo, String employeeId) {

        System.out.println("inside saveProfilePhoto Dao Impl::::::::");
        Map<String, Object> respMap = new HashMap<>();
        Agent agent = new Agent();
        try {

            agent = agentRepo.findByEmployeeId(employeeId);
            String filename = employeeId + "_" + photo.getOriginalFilename();

            if (agent != null) {

                File directory = new File(contentPath + "/_profile_photos" + "/");
                System.out.println(directory.getAbsolutePath());
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                File convertFile = new File(directory.getAbsoluteFile() + "/" + filename);
                convertFile.createNewFile();
                FileOutputStream fout = new FileOutputStream(convertFile);
                fout.write(photo.getBytes());
                fout.close();

                agent.setPhoto(photo.getBytes());

                Query query = em.createQuery(
                        "Update Agent a set a.photoFileName = :photofilename where a.employeeId = :employeeId");
                query.setParameter("photofilename", filename);
                query.setParameter("employeeId", employeeId);

                query.executeUpdate();

                agent.setPhotoFileName(filename);

                respMap.putAll(Util.SuccessResponse());
            } else
                respMap.putAll(Util.FailedResponse("File Too Large...!!!"));

        } catch (Exception e) {
            e.printStackTrace();
            respMap.putAll(Util.FailedResponse("File Too Large...!!!"));
        }
        respMap.put("agent", agent);
        return respMap;
    }

    @Modifying
    @Transactional
    @Override
    public Map<String, Object> signatureUpload(MultipartFile signature, String employeeId) {

        System.out.println("inside signatureUpload Dao Impl::::::::");
        Map<String, Object> respMap = new HashMap<>();
        Agent agent = new Agent();
        try {

            agent = agentRepo.findByEmployeeId(employeeId);
            String filename = employeeId + "_" + signature.getOriginalFilename();

            if (agent != null) {
                File directory = new File(contentPath + "/_profile_signatures" + "/");
                System.out.println(directory.getAbsolutePath());
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                File convertFile = new File(directory.getAbsoluteFile() + "/" + filename);
                convertFile.createNewFile();
                FileOutputStream fout = new FileOutputStream(convertFile);
                fout.write(signature.getBytes());
                fout.close();

                agent.setSignature(signature.getBytes());

                Query query = em.createQuery(
                        "Update Agent a set a.signature = :signature , a.signatureFileName = :signatureFileName where a.employeeId = :employeeId");
                query.setParameter("signature", signature.getBytes());
                query.setParameter("signatureFileName", filename);
                query.setParameter("employeeId", employeeId);
                query.executeUpdate();

                agent.setSignatureFileName(filename);

                respMap.putAll(Util.SuccessResponse());
            } else
                respMap.putAll(Util.FailedResponse("File Too Large...!!!"));

        } catch (Exception e) {
            e.printStackTrace();
            respMap.putAll(Util.FailedResponse(e.getMessage()));
        }
        respMap.put("agent", agent);
        return respMap;
    }

    @Override
    public Map<String, Object> saveVendor(Vendor vendor) {
        System.out.println("inside saveVendor Dao Impl::::::::" + vendor);
        Map<String, Object> respMap = new HashMap<>();

        try {

            if (vendor.getVendorName() == null || vendor.getVendorName().isEmpty()) {
                respMap.putAll(Util.invalidMessage("Vendor Name Cannot be Empty"));
            } else if (vendor.getVendorPhone() == null || vendor.getVendorPhone().isEmpty()) {
                respMap.putAll(Util.invalidMessage("Phone Cannot be Empty"));
            } else if (vendor.getVendorEmail() == null || vendor.getVendorEmail().isEmpty()) {
                respMap.putAll(Util.invalidMessage("EMail Cannot be Empty"));
            } else if (vendor.getGstNo() == null || vendor.getGstNo().isEmpty()) {
                respMap.putAll(Util.invalidMessage("Gst number Cannot be Empty"));
            } else {
                vendor = vendorRepo.save(vendor);
                respMap.put("vendor", vendor);
                respMap.putAll(Util.SuccessResponse());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.putAll(Util.FailedResponse());
        }

        return respMap;
    }

    @Override
    public Map<String, Object> deleteVendor(Vendor vendor) {
        System.out.println("inside deleteVendor DaoImpl::::");
        Map<String, Object> respMap = new HashMap<>();
        try {

            vendor = vendorRepo.findById(vendor.getId());
            if (vendor == null) {
                respMap.putAll(Util.invalidMessage("Agent Not Found"));
            } else {
                vendorRepo.delete(vendor);
                respMap.putAll(Util.SuccessResponse());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.putAll(Util.invalidMessage("You cannot able to delete this vendor.!its associated with others.!"));

        }
        return respMap;
    }

    @Override
    public Map<String, Object> getVendor(Vendor vendor) {
        System.out.println("Inside getVendor DaoImpl::::::");
        Map<String, Object> respMap = new HashMap<>();

        try {
            vendor = vendorRepo.findById(vendor.getId());
            if (vendor == null) {
                respMap.putAll(Util.invalidMessage("Vendor Not Found..!!"));
            } else
                respMap.put("vendor", vendor);
            respMap.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return respMap;
    }

    @Override
    public Map<String, Object> getAllVendors() {
        System.out.println("Inside getAllVendors DaoImpl::::::");
        Map<String, Object> respMap = new HashMap<>();
        Iterable<Vendor> vendor = new ArrayList<>();
        try {
            vendor = vendorRepo.findAll();
            if (vendor == null) {
                respMap.putAll(Util.invalidMessage("Vendor Not Found..!!"));
            } else
                respMap.put("vendors", vendor);
            respMap.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return respMap;
    }

    @Transactional
    @Override
    public Map<String, Object> addStockEntry(StockEntry stock) {
        System.out.println("Inside getAllVendors DaoImpl::::::");
        Map<String, Object> respMap = new HashMap<>();
        Product product = new Product();
        try {

            stock = stockEntryRepo.save(stock);

            if (stock.getType().equalsIgnoreCase("credit")) {
                Query query = em.createQuery("Update Product p set p.stock = p.stock + :stock where p.id = :id");
                query.setParameter("stock", stock.getQuantity());
                query.setParameter("id", stock.getProductId());

                query.executeUpdate();
            } else if (stock.getType().equalsIgnoreCase("deduct")) {
                Query query = em.createQuery("Update Product p set p.stock = p.stock - :stock where p.id = :id");
                query.setParameter("stock", stock.getQuantity());
                query.setParameter("id", stock.getProductId());

                query.executeUpdate();
            }

            product = productRepo.findById(stock.getProductId());

            respMap.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.putAll(Util.FailedResponse(ex.getMessage()));
        }
        respMap.put("StockEntry", stock);
        respMap.put("Product", product);
        return respMap;
    }

    @Override
    public Map<String, Object> getAllStockEntry(Product product) {
        System.out.println("Inside getAllVendors DaoImpl::::::");
        Map<String, Object> respMap = new HashMap<>();
        List<StockEntry> stockEntries = new ArrayList<>();
        try {

            stockEntries = stockEntryRepo.findByProductId(product.getId());

            respMap.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.putAll(Util.FailedResponse(ex.getMessage()));
        }
        respMap.put("StockEntries", stockEntries);
        return respMap;
    }

    @Override
    public Map<String, Object> updateBulkProducts(List<Product> products) {
        Map<String, Object> respMap = new HashMap<>();
        try {

            products = productRepo.saveAll(products);

            respMap.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.putAll(Util.FailedResponse(ex.getMessage()));
        }
        respMap.put("Products", products);
        return respMap;
    }

    @Override
    public Map<String, Object> saveRoleMaster(RoleMaster role) {
        Map<String, Object> respMap = new HashMap<>();
        try {

            role = roleRepo.save(role);

            respMap.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.putAll(Util.FailedResponse(ex.getMessage()));
        }
        respMap.put("RoleMaster", role);
        return respMap;
    }

    @Override
    public Map<String, Object> deleteRoleMaster(RoleMaster role) {
        Map<String, Object> respMap = new HashMap<>();
        try {

            role = roleRepo.findById(role.getId());

            List<Agent> agents = agentRepo.findByAgentType(role.getId());

            if (agents.isEmpty()) {
                roleRepo.delete(role);
                respMap.putAll(Util.SuccessResponse());
            } else {
                respMap.put("Agents", agents);

                respMap.putAll(Util.invalidMessage(agents.size() + " agents associated with this Role."));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.putAll(Util.FailedResponse(ex.getMessage()));
        }
        return respMap;
    }

    @Override
    public Map<String, Object> getRoleMasters(RoleMaster role) {
        Map<String, Object> respMap = new HashMap<>();
        List<RoleMaster> roles = new ArrayList<>();
        try {

            roles = roleRepo.findAll();

            respMap.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.putAll(Util.FailedResponse(ex.getMessage()));
        }
        respMap.put("RoleMasters", roles);
        return respMap;
    }

    @Override
    public Map<String, Object> getRoleMasters(int roleId) {
        Map<String, Object> respMap = new HashMap<>();
        RoleMaster role = new RoleMaster();
        try {

            role = roleRepo.findById(roleId);

            respMap.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.putAll(Util.FailedResponse(ex.getMessage()));
        }
        respMap.put("RoleMaster", role);
        return respMap;
    }

    @Override
    public Map<String, Object> getAgentReport(AgentReportRequest agentReport) {
        Map<String, Object> resp = new HashMap<>();
        Agent agent = null;
        SimpleDateFormat sdf = null;
        SimpleDateFormat sdf1 = null;
        try {

            System.out.println("Inside getAgentReport DaoImpl::::::");

            if (agentReport.getAgents().size() > 0) {

                agent = agentRepo.findByEmailId(agentReport.getAgents().get(0).getEmailId());

                if (agentReport.getFromDate() != null || agentReport.getToDate() != null) {
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    sdf1 = new SimpleDateFormat("yyyy-MM-dd");

                    // sdf.format(agentReport.getFromDate())
                    // sdf.format(agentReport.getToDate());
                    resp.put("agent", agent);
                    resp.put("ticketCount",
                            agentRepo.findTicketCountStats(agentReport.getAgents().get(0).getEmailId(),
                                    sdf.format(agentReport.getFromDate()).toString(),
                                    sdf.format(agentReport.getToDate()).toString()));
                    resp.put("ticketStatCount",
                            agentRepo.findAgentTicketStatus(agentReport.getAgents().get(0).getEmailId(),
                                    sdf.format(agentReport.getFromDate()).toString(),
                                    sdf.format(agentReport.getToDate()).toString()));
                    resp.put("averageRating",
                            agentRepo.findAgentAverageRating(agentReport.getAgents().get(0).getEmailId(),
                                    sdf.format(agentReport.getFromDate()).toString(),
                                    sdf.format(agentReport.getToDate()).toString()));
                    resp.put("ratings",
                            agentRepo.findAgentRatings(agentReport.getAgents().get(0).getEmailId(),
                                    sdf.format(agentReport.getFromDate()).toString(),
                                    sdf.format(agentReport.getToDate()).toString()));
                    resp.put("workingDays",
                            agentRepo.findAttendance(agent.getEmployeeId(),
                                    sdf1.format(agentReport.getFromDate()).toString(),
                                    sdf1.format(agentReport.getToDate()).toString()));
                    resp.put("priorityStatus",
                            agentRepo.findAgentTicketStatus(agentReport.getAgents().get(0).getEmailId(),
                                    sdf.format(agentReport.getFromDate()).toString(),
                                    sdf.format(agentReport.getToDate()).toString()));
                    resp.put("siteAttendance",
                            agentRepo.findAgentSiteAttendance(agent.getEmployeeId(),
                                    sdf.format(agentReport.getFromDate()).toString(),
                                    sdf.format(agentReport.getToDate()).toString()));
                    resp.put("priorityStatus",
                            agentRepo.findAgentPriority(agentReport.getAgents().get(0).getEmailId(),
                                    sdf.format(agentReport.getFromDate()).toString(),
                                    sdf.format(agentReport.getToDate()).toString()));
                    resp.putAll(Util.SuccessResponse());
                } else {
                    resp.put("agent", agent);
                    resp.put("ratings", agentRepo.findAgentRatings(agentReport.getAgents().get(0).getEmailId()));
                    resp.put("averageRating",
                            agentRepo.findAgentAverageRating(agentReport.getAgents().get(0).getEmailId()));
                    resp.put("ticketCount",
                            agentRepo.findTicketCountStats(agentReport.getAgents().get(0).getEmailId()));
                    resp.put("ticketStatCount",
                            agentRepo.findAgentTicketStatus(agentReport.getAgents().get(0).getEmailId()));
                    resp.put("workingDays", agentRepo.findAttendance(agent.getEmployeeId()));
                    resp.put("priorityStatus",
                            agentRepo.findAgentTicketStatus(agentReport.getAgents().get(0).getEmailId()));
                    resp.put("siteAttendance", agentRepo.findAgentSiteAttendance(agent.getEmployeeId()));
                    resp.put("priorityStatus",
                            agentRepo.findAgentPriority(agentReport.getAgents().get(0).getEmailId()));

                    resp.putAll(Util.SuccessResponse());
                }
            }
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        return resp;
    }

    @Transactional
    @Override
    public Map<String, Object> saveProductsMapped(ProductRequest productReq) {

        System.out.println("saveProduct DAOImpl method ends::::::");

        Map<String, Object> respMap = new HashMap<>();

        try {

            productRawRepo.deleteByProductId(productReq.getProduct().getId());

            productReq.setProductsRawMaterials(productRawRepo.saveAll(productReq.getProductsRawMaterials()));
            respMap.putAll(Util.SuccessResponse());

        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.putAll(Util.FailedResponse(ex.getMessage()));
        }
        respMap.put("ProductsRawMaterials", productReq.getProductsRawMaterials());
        return respMap;
    }

    @Override
    public Map<String, Object> getProductsMapped(ProductRequest productReq) {

        System.out.println("saveProduct DAOImpl method ends::::::");

        Map<String, Object> respMap = new HashMap<>();

        try {

            productReq.setProductsRawMaterials(productRawRepo.findByProductId(productReq.getProduct().getId()));

            respMap.putAll(Util.SuccessResponse());

        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.putAll(Util.FailedResponse(ex.getMessage()));
        }
        respMap.put("ProductsRawMaterials", productReq.getProductsRawMaterials());
        return respMap;
    }

    @Transactional
    @Override
    public Map<String, Object> saveProductsRawMaterialRequest(ProductRequest productReq) {

        System.out.println("saveProduct DAOImpl method ends::::::");

        Map<String, Object> respMap = new HashMap<>();

        try {

            boolean isExist = productReq.getRawMaterialRequest().getId() > 0 ? true : false;

            productReq.setRawMaterialRequest(rawMaterialRequestRepo.save(productReq.getRawMaterialRequest()));

            if (productReq.getCurrentAction().equalsIgnoreCase("Save")) {
                sendProductsRawMaterialRequestMail(isExist, productReq);

                //	PushNotification.sendRawMaterialRequestNotification(productReq.getRawMaterialRequest());

                productReq.getRawMaterialRequestProducts().stream()
                        .forEach(rmrp -> rmrp.setRequestId(productReq.getRawMaterialRequest().getId()));

                rawMaterialRequestProductsRepo.deleteByRequestId(productReq.getRawMaterialRequest().getId());

                productReq.setRawMaterialRequestProducts(
                        rawMaterialRequestProductsRepo.saveAll(productReq.getRawMaterialRequestProducts()));
            } else if (productReq.getCurrentAction().equalsIgnoreCase("Approved")) {

                for (RawMaterialRequestProducts rp : productReq.getRawMaterialRequestProducts()) {
                    StockEntry se = new StockEntry();
                    se.setProductId(rp.getMappedProductId());
                    se.setEntryDate(new Date());
                    se.setEntryBy("--system-generated--");
                    se.setQuantity(rp.getQuantity());
                    se.setType("Deduct");
                    se.setRemarks("#" + productReq.getRawMaterialRequest().getId() + " - (Fabrication) "
                            + productReq.getRawMaterialRequest().getProductName());

                    agentService.addStockEntry(se);
                }

                sendProductsRawMaterialRequestMailApproved(isExist, productReq);

                //		PushNotification.sendProductsRawMaterialRequestNotify(productReq);

            } else if (productReq.getCurrentAction().equalsIgnoreCase("Rejected"))
                sendProductsRawMaterialRequestMailRejected(isExist, productReq);

            //	PushNotification.sendProductsRawMaterialRequestNotify(productReq);

            respMap.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.putAll(Util.FailedResponse(ex.getMessage()));
        }
        respMap.put("RawMaterialRequest", productReq.getRawMaterialRequest());
        respMap.put("RawMaterialRequestProducts", productReq.getRawMaterialRequestProducts());
        return respMap;
    }

    void sendProductsRawMaterialRequestMail(boolean isExist, ProductRequest productReq) {
        try {
            EmailModel emailModel = new EmailModel("Common");

            emailModel.setMailFrom(productReq.getRawMaterialRequest().getRequestBy());
            emailModel.setMailTo(productReq.getRawMaterialRequest().getRequestTo());

            if (isExist)
                emailModel.setMailSub("#" + productReq.getRawMaterialRequest().getId() + " (Updated) - "
                        + productReq.getRawMaterialRequest().getRequestBy() + " requested for raw materials");
            else
                emailModel.setMailSub("#" + productReq.getRawMaterialRequest().getId() + " - "
                        + productReq.getRawMaterialRequest().getRequestBy() + " requested for raw materials");

            StringBuffer sb = new StringBuffer();
            sb.append("Hi, ");
            sb.append("<br><br>");
            sb.append(productReq.getRawMaterialRequest().getRequestBy()
                    + " requested for raw materials for manufacturing '"
                    + productReq.getRawMaterialRequest().getProductName() + "'");
            sb.append("<br><br>");
            sb.append("<h4>Requested Materials:</h4>");
            sb.append("<br>");

            for (RawMaterialRequestProducts raw : productReq.getRawMaterialRequestProducts()) {
                sb.append(raw.getMappedProductName() + " - (Qty:" + raw.getQuantity() + ")");
                if (raw.getDescription() != null) {
                    sb.append("<br>");
                    sb.append("		" + raw.getDescription());
                }
                sb.append("<hr>");
            }

            if (productReq.getRawMaterialRequest().getRemarks() != null) {

                sb.append("<h5>Remarks:</h5>");

                sb.append(productReq.getRawMaterialRequest().getRemarks().replaceAll("\n", "<br>").replaceAll("\r",
                        "<br>"));

            }

            sb.append("<br><br>");

            sb.append("View : " + viewRawMaterialRequestURI + "?expand=" + productReq.getRawMaterialRequest().getId());

            sb.append("<br>");
            sb.append("<br>");
            emailModel.setMailText(sb.toString());

            emailSender.sendmail(emailModel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void sendProductsRawMaterialRequestMailApproved(boolean isExist, ProductRequest productReq) {
        try {
            EmailModel emailModel = new EmailModel("Common");

            emailModel.setMailTo(productReq.getRawMaterialRequest().getRequestBy());

            emailModel.setMailSub("#" + productReq.getRawMaterialRequest().getId() + " (Approved) - "
                    + productReq.getRawMaterialRequest().getRequestBy() + " - "
                    + productReq.getRawMaterialRequest().getProductName());

            StringBuffer sb = new StringBuffer();
            sb.append("Hi, ");
            sb.append("<br><br>");
            sb.append("Your request for manufacturing '" + productReq.getRawMaterialRequest().getProductName()
                    + "' is Approved");

            sb.append("<br><br>");
            sb.append("<h4>Requested Materials:</h4>");
            sb.append("<br>");

            for (RawMaterialRequestProducts raw : productReq.getRawMaterialRequestProducts()) {
                sb.append(raw.getMappedProductName() + " - (Qty:" + raw.getQuantity() + ")");
                if (raw.getDescription() != null) {
                    sb.append("<br>");
                    sb.append("		" + raw.getDescription());
                }
                sb.append("<hr>");
            }

            if (productReq.getRawMaterialRequest().getRemarks() != null) {

                sb.append("<h5>Remarks:</h5>");

                sb.append(productReq.getRawMaterialRequest().getRemarks().replaceAll("\n", "<br>").replaceAll("\r",
                        "<br>"));

            }

            sb.append("<br><br>");

            sb.append("View : " + viewRawMaterialRequestURI + "?expand=" + productReq.getRawMaterialRequest().getId());

            sb.append("<br>");
            sb.append("<br>");
            emailModel.setMailText(sb.toString());

            emailSender.sendmail(emailModel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void sendProductsRawMaterialRequestMailRejected(boolean isExist, ProductRequest productReq) {
        try {
            EmailModel emailModel = new EmailModel("Common");

            emailModel.setMailTo(productReq.getRawMaterialRequest().getRequestBy());

            emailModel.setMailSub("#" + productReq.getRawMaterialRequest().getId() + " (Rejected) - "
                    + productReq.getRawMaterialRequest().getRequestBy() + " - "
                    + productReq.getRawMaterialRequest().getProductName());

            StringBuffer sb = new StringBuffer();
            sb.append("Hi, ");
            sb.append("<br><br>");
            sb.append("Your request for manufacturing '" + productReq.getRawMaterialRequest().getProductName()
                    + "' is Rejected");
            sb.append("<br><br>");
            sb.append("<h4>Requested Materials:</h4>");
            sb.append("<br>");

            for (RawMaterialRequestProducts raw : productReq.getRawMaterialRequestProducts()) {
                sb.append(raw.getMappedProductName() + " - (Qty:" + raw.getQuantity() + ")");
                if (raw.getDescription() != null) {
                    sb.append("<br>");
                    sb.append("		" + raw.getDescription());
                }
                sb.append("<hr>");
            }

            if (productReq.getRawMaterialRequest().getRemarks() != null) {

                sb.append("<h5>Remarks:</h5>");

                sb.append(productReq.getRawMaterialRequest().getRemarks().replaceAll("\n", "<br>").replaceAll("\r",
                        "<br>"));

            }

            sb.append("<br><br>");

            sb.append("View : " + viewRawMaterialRequestURI + "?expand=" + productReq.getRawMaterialRequest().getId());

            sb.append("<br>");
            sb.append("<br>");
            emailModel.setMailText(sb.toString());

            emailSender.sendmail(emailModel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> getProductsRawMaterialRequests(ProductRequest productReq) {

        Map<String, Object> respMap = new HashMap<>();

        List<RawMaterialRequestResponse> _response_rawMaterialRequestsToMe = new ArrayList<>();
        List<RawMaterialRequestResponse> _response_rawMaterialRequestsByMe = new ArrayList<>();

        try {

            List<RawMaterialRequest> rawMaterialRequestsToMe = new ArrayList<>();
            List<RawMaterialRequest> rawMaterialRequestsByMe = new ArrayList<>();

            rawMaterialRequestsToMe = rawMaterialRequestRepo.getRawMaterialRequestsToMe(productReq.getEmailId());

            rawMaterialRequestsByMe = rawMaterialRequestRepo.getRawMaterialRequestsByMe(productReq.getEmailId());

            List<Integer> toids = rawMaterialRequestsToMe.stream().map(RawMaterialRequest::getId)
                    .collect(Collectors.toList());

            List<Integer> byids = rawMaterialRequestsByMe.stream().map(RawMaterialRequest::getId)
                    .collect(Collectors.toList());

            List<Integer> ids = Stream.concat(toids.stream(), byids.stream()).collect(Collectors.toList());

            List<RawMaterialRequestProducts> rawMaterialRequestsProducts = rawMaterialRequestProductsRepo
                    .findByRequestIdIn(ids);

            System.out.println(Stream.concat(toids.stream(), byids.stream()).collect(Collectors.toList()));

            rawMaterialRequestsToMe.parallelStream().forEach(req -> {

                RawMaterialRequestResponse resp = new RawMaterialRequestResponse();

                resp.setRawMaterialRequest(req);
                resp.setRawMaterialRequestProducts(rawMaterialRequestsProducts.parallelStream()
                        .filter(prod -> prod.getRequestId() == req.getId()).collect(Collectors.toList()));

                _response_rawMaterialRequestsToMe.add(resp);

            });

            rawMaterialRequestsByMe.parallelStream().forEach(req -> {

                RawMaterialRequestResponse resp = new RawMaterialRequestResponse();

                resp.setRawMaterialRequest(req);
                resp.setRawMaterialRequestProducts(rawMaterialRequestsProducts.parallelStream()
                        .filter(prod -> prod.getRequestId() == req.getId()).collect(Collectors.toList()));

                _response_rawMaterialRequestsByMe.add(resp);

            });
            respMap.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.putAll(Util.FailedResponse(ex.getMessage()));
        }

        respMap.put("RawMaterialRequestsToMe", _response_rawMaterialRequestsToMe);
        respMap.put("RawMaterialRequestsByMe", _response_rawMaterialRequestsByMe);
        return respMap;
    }

    @Transactional
    @Override
    public Map<String, Object> deleteProductsRawMaterialRequests(ProductRequest productReq) {

        Map<String, Object> respMap = new HashMap<>();

        try {

            rawMaterialRequestRepo.delete(productReq.getRawMaterialRequest());

            rawMaterialRequestProductsRepo.deleteByRequestId(productReq.getRawMaterialRequest().getId());

            respMap.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.putAll(Util.FailedResponse(ex.getMessage()));
        }
        return respMap;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> rawMaterialRequestsReport(RawMaterialReportRequests request) {

        Map<String, Object> respMap = new HashMap<>();
        List<RawMaterialRequest> requests = new ArrayList<>();
        try {

            String filter = "";

            if (request.getProducts() != null && request.getProducts().size() > 0) {
                String productIds = "0";
                for (Product dp : request.getProducts()) {
                    productIds = productIds + "," + dp.getId();
                }
                filter = filter + " and rq.productId in  (" + productIds + ")";
            }

            if (request.getRequestBy() != null && request.getRequestBy().size() > 0) {
                String agents = "'0'";
                for (Agent agnt : request.getRequestBy()) {
                    agents = agents + ",'" + agnt.getEmailId() + "'";
                }
                filter = filter + " and rq.requestBy in (" + agents + ") ";
            }

            if (request.getRequestTo() != null && request.getRequestTo().size() > 0) {
                String agents = "'0'";
                for (Agent agnt : request.getRequestTo()) {
                    agents = agents + ",'" + agnt.getEmailId() + "'";
                }
                filter = filter + " and rq.requestTo in (" + agents + ") ";
            }

            if (request.getRequestDateFrom() != null && request.getRequestDateTo() != null) {
                filter = filter + " and rq.requestDate between '" + Util.sdfFormatter(request.getRequestDateFrom())
                        + "' and '" + Util.sdfFormatter(request.getRequestDateTo()) + " 23:59:59'";
            }

            if (request.getApprovedDateFrom() != null && request.getApprovedDateTo() != null) {
                filter = filter + " and rq.approvedDate between '" + Util.sdfFormatter(request.getApprovedDateFrom())
                        + "' and '" + Util.sdfFormatter(request.getApprovedDateTo()) + " 23:59:59'";
            }

            if (request.getStatus() != null && !request.getStatus().isEmpty()) {
                filter = filter + " and rq.status = '" + request.getStatus() + "'";
            }

            if (request.getSubject() != null && !request.getSubject().isEmpty()) {
                filter = filter + " and rq.subject like '%" + request.getSubject() + "%'";
            }

            /* RawMaterialReportRequests Filter ends */

            Query query = em.createQuery("select rq from RawMaterialRequest rq where 2 > 1 " + filter,
                    RawMaterialRequest.class);

            requests = query.getResultList();

            respMap.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.putAll(Util.FailedResponse(ex.getMessage()));
        }
        respMap.put("RawMaterialRequests", requests);

        return respMap;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> loadStockDetails(StockEntryReq req) {
        Map<String, Object> resp = new HashMap<>();
        // List amcDetails = new ArrayList();
        String filterQuery = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            if (req.getRemarks() != null && req.getRemarks() != "") {
                filterQuery = filterQuery + " and a.remarks like '%" + req.getRemarks() + "%'";
            }
            if (req.getType() != null && req.getType() != "") {
                filterQuery = filterQuery + " and a.type like '%" + req.getType() + "%'";
            }
            if (req.getCategory() != null && req.getCategory() != "") {
                filterQuery = filterQuery + " and p.category like '%" + req.getCategory() + "%'";
            }
            if (req.getEntryDateFrom() != null && req.getEntryDateTo() != null) {
                filterQuery = filterQuery + " and a.entryDate between '" + sdf.format(req.getEntryDateFrom())
                        + "' and '" + sdf.format(req.getEntryDateTo()) + "'";
            }

            if (req.getProducts() != null && req.getProducts().size() > 0) {
                String productIds = "0";
                for (Product p : req.getProducts()) {
                    productIds = productIds + "," + p.getId();
                }
                filterQuery = filterQuery + " and a.productId in (" + productIds + ") ";
            }

            System.out.println(":::Filtersss::::" + filterQuery);
            Query query = em.createQuery(
                    "SELECT new com.autolib.helpdesk.Agents.repository.StockDetailResp(a,p) FROM StockEntry a JOIN Product p ON (a.productId = p.id)"
                            + filterQuery,
                    StockDetailResp.class);

            List<StockDetailResp> stockDetails = query.getResultList();

            resp.put("stockDetails", stockDetails);
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        return resp;
    }

}
