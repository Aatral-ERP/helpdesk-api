package com.autolib.helpdesk.Agents.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.AgentReportRequest;
import com.autolib.helpdesk.Agents.entity.AgentToken;
import com.autolib.helpdesk.Agents.entity.InfoDetails;
import com.autolib.helpdesk.Agents.entity.Product;
import com.autolib.helpdesk.Agents.entity.ProductRequest;
import com.autolib.helpdesk.Agents.entity.RawMaterialReportRequests;
import com.autolib.helpdesk.Agents.entity.RoleMaster;
import com.autolib.helpdesk.Agents.entity.StockEntry;
import com.autolib.helpdesk.Agents.entity.StockEntryReq;
import com.autolib.helpdesk.Agents.entity.Vendor;
import com.autolib.helpdesk.Agents.repository.AgentTokenRepository;
import com.autolib.helpdesk.Agents.service.AgentService;
import com.autolib.helpdesk.common.GlobalAccessUtil;
import com.autolib.helpdesk.jwt.JwtTokenUtil;

@RestController
@RequestMapping("agent")
@CrossOrigin
public class AgentController {

	private final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	AgentService agentService;
	@Autowired
	AgentTokenRepository agentTokenRepo;

	@Autowired
	JwtTokenUtil jwtUtil;

	@PostMapping("get-home-page-details")
	public ResponseEntity<?> getHomePageDetails(@RequestHeader(value = "Authorization") String token,
			@RequestBody Agent agent) throws Exception {
		logger.info("getHomePageDetails starts:::" + agent);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = agentService.getHomePageDetails(agent);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getHomePageDetails ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-all-agent-tickets")
	public ResponseEntity<?> getAllAgentTickets(@RequestHeader(value = "Authorization") String token,
			@RequestBody Agent agent) throws Exception {
		logger.info("getAllAgentTickets starts:::" + agent);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = agentService.getAllAgentTickets(agent);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getAllAgentTickets ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-agent")
	public ResponseEntity<?> addAgent(@RequestHeader(value = "Authorization") String token, @RequestBody Agent agent) {
		logger.info("addAgent req starts::" + agent);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = agentService.addAgent(agent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-agents")
	public ResponseEntity<?> getAllAgents(@RequestHeader(value = "Authorization") String token,
			@RequestBody Agent agent) {
		System.out.println("getAllAgents req starts::" + agent);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = agentService.getAllAgents(agent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/get-Agent-Details/{AgentId}")
	public ResponseEntity<?> getAgentDetails(@RequestHeader(value = "Authorization") String token,
			@PathVariable("AgentId") String AgentId) {
		logger.info("getAgentDetails starts:::" + AgentId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = agentService.getAgentDetails(AgentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getAgentDetails ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);

	}

	@PostMapping("delete-agent")
	public ResponseEntity<?> deleteAgent(@RequestHeader(value = "Authorization") String token,
			@RequestBody Agent agent) {
		System.out.println("deleteAgent req starts::" + agent);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = agentService.deleteAgent(agent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("change-agent-leave-master")
	public ResponseEntity<?> changeLeaveMaster(@RequestHeader(value = "Authorization") String token,
			@RequestBody Map<String, Object> req) {
		System.out.println("changeLeaveMaster req starts::" + req);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = agentService.changeLeaveMaster(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("load-institute")
	public ResponseEntity<?> loadInstitute(@RequestHeader(value = "Authorization") String token,
			@RequestBody Map<String, Object> req) throws Exception {

		logger.info("loadInstitute report::" + req);
		Map<String, Object> resp = new HashMap<String, Object>();

		try {
			resp = agentService.loadInstitute(req);
		} catch (Exception Ex) {
			Ex.printStackTrace();
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-product")
	public ResponseEntity<?> saveProduct(@RequestHeader(value = "Authorization") String token,
			@RequestBody Product product) throws Exception {
		logger.info("saveProduct Starts::" + product);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = agentService.saveProduct(product);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-product")
	public ResponseEntity<?> deleteProduct(@RequestHeader(value = "Authorization") String token,
			@RequestBody Product product) throws Exception {
		logger.info("deleteProduct Starts::" + product);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = agentService.deleteProduct(product);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-products")
	public ResponseEntity<?> getAllProducts(@RequestHeader(value = "Authorization") String token) throws Exception {
		logger.info("getAllProducts Starts::");
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = agentService.getAllProducts();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		logger.info("getAllProducts Starts::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/get-Product-Details/{ProductId}")
	public ResponseEntity<?> getProductDetails(@RequestHeader(value = "Authorization") String token,
			@PathVariable("ProductId") int ProductId) {
		logger.info("getProductDetails starts:::" + ProductId);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = agentService.getProductDetails(ProductId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("getProductDetails ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);

	}

	@PostMapping("forgetPassword")
	public ResponseEntity<?> forgetPassword(@RequestBody Map<String, Object> req) throws Exception {
		logger.info("forgetPassword starts:::" + req);
		System.out.println("forgetPassword req starts::" + req);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = agentService.forgetPassword(req);
		} catch (Exception Ex) {

			Ex.printStackTrace();
		}
		logger.info("forgetPassword ends:::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("resetPassword")
	public ResponseEntity<?> resetPassword(@RequestBody Map<String, Object> req) throws Exception {
		logger.info("resetPassword starts:::" + req);
		System.out.println("resetPassword req starts::" + req);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = agentService.resetPassword(req);
		} catch (Exception Ex) {

			Ex.printStackTrace();
		}
		logger.info("resetPassword req ends::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("checkOTP")
	public ResponseEntity<?> checkOTP(@RequestBody Map<String, Object> req) throws Exception {
		logger.info("checkOTP req starts::" + req);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = agentService.checkOTP(req);

		} catch (Exception Ex) {
			System.out.println("Error checkUserExist:::::::" + Ex);
			Ex.printStackTrace();
		}
		logger.info("checkOTP req ends::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("needed")
	public ResponseEntity<?> needed(@RequestBody String designation) throws Exception {
		logger.info("autocompleteDesignation req starts::" + designation);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = agentService.needed(designation);

		} catch (Exception Ex) {
			System.out.println("Error autocompleteDesignation:::::::" + Ex);
			Ex.printStackTrace();
		}
		logger.info("autocompleteDesignation req ends::" + resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-info-details")

	public ResponseEntity<?> getInfoDetails(@RequestHeader(value = "Authorization") String token,
			@RequestBody InfoDetails info) {
		logger.info("getInfoDetails req Starts::::::::");
		Map<String, Object> resp = new HashMap<>();
		try {
			jwtUtil.isValidToken(token);
			resp = agentService.getInfoDetails(info);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-info-details")

	public ResponseEntity<?> saveInfoDetails(@RequestHeader(value = "Authorization") String token,
			@RequestBody InfoDetails info) {
		logger.info("infoDetails req Starts::::::::" + info);
		Map<String, Object> resp = new HashMap<>();
		try {
			jwtUtil.isValidToken(token);
			resp = agentService.saveInfoDetails(info);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("/upload")
	ResponseEntity<?> fileUpload(@RequestHeader(value = "Authorization") String token,
			@RequestParam("file") MultipartFile file) {
		logger.info("fileUpload req Starts::::::::");
		Map<String, Object> resp = new HashMap<>();
		try {
			jwtUtil.isValidToken(token);
			resp = agentService.fileUpload(file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("round-seal-upload")
	ResponseEntity<?> roundSealUpload(@RequestHeader(value = "Authorization") String token,
			@RequestParam("file") MultipartFile file) {
		logger.info("roundSealUpload req Starts::::::::");
		Map<String, Object> resp = new HashMap<>();
		try {
			jwtUtil.isValidToken(token);
			resp = agentService.roundSealUpload(file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("/full-seal-upload")
	ResponseEntity<?> fullSealUpload(@RequestHeader(value = "Authorization") String token,
			@RequestParam("file") MultipartFile file) {
		logger.info("fullSealUpload req Starts::::::::");
		Map<String, Object> resp = new HashMap<>();
		try {
			jwtUtil.isValidToken(token);
			resp = agentService.fullSealUpload(file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("/photoUpload")
	public ResponseEntity<?> saveProfilePhoto(@RequestHeader(value = "Authorization") String token,
			@RequestParam("photo") MultipartFile photo, @RequestParam("employeeId") String employeeId) {
		logger.info("saveProfilePhoto req Starts::::::::" + employeeId);
		Map<String, Object> resp = new HashMap<>();

		try {
			jwtUtil.isValidToken(token);
			resp = agentService.saveProfilePhoto(photo, employeeId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("saveProfilePhoto req Starts::::::::" + employeeId);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("/signatureUpload")
	public ResponseEntity<?> signatureUpload(@RequestHeader(value = "Authorization") String token,
			@RequestParam("signature") MultipartFile signature, @RequestParam("employeeId") String employeeId) {
		logger.info("signatureUpload req Starts::::::::" + employeeId);
		Map<String, Object> resp = new HashMap<>();

		try {
			jwtUtil.isValidToken(token);
			resp = agentService.signatureUpload(signature, employeeId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("signatureUpload req ends::::::::" + employeeId);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-vendor")
	public ResponseEntity<?> saveVendor(@RequestHeader(value = "Authorization") String token,
			@RequestBody Vendor vendor) {
		logger.info("saveVendor req Starts::::::::" + vendor);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = agentService.saveVendor(vendor);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-vendor")
	public ResponseEntity<?> deleteVendor(@RequestHeader(value = "Authorization") String token,
			@RequestBody Vendor vendor) {
		logger.info("deleteVendor req Starts::::::::" + vendor);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = agentService.deleteVendor(vendor);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	/*
	 * @GetMapping("get-vendor/{id}") public ResponseEntity<?>
	 * getVendor(@RequestHeader("Authorization") String token,@PathVariable("id")int
	 * id) { logger.info("deleteVendor req Starts::::::::"+id); Map<String,Object>
	 * resp = new HashMap<>(); try { resp = agentService.getVendor(id);
	 * }catch(Exception ex) { ex.printStackTrace(); } return new
	 * ResponseEntity<>(resp,HttpStatus.OK);
	 * 
	 * }
	 */
	@PostMapping("get-vendor")
	public ResponseEntity<?> getVendor(@RequestHeader("Authorization") String token, @RequestBody Vendor vendor) {
		logger.info("getVendor req Starts::::::::" + vendor);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = agentService.getVendor(vendor);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);

	}

	@PostMapping("get-vendors-details")
	public ResponseEntity<?> getAllVendors(@RequestHeader(value = "Authorization") String token) {
		logger.info("getAllVendors req Starts:::::");
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = agentService.getAllVendors();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("add-stock-entry")
	public ResponseEntity<?> addStockEntry(@RequestHeader(value = "Authorization") String token,
			@RequestBody StockEntry stock) {
		logger.info("addStockEntry req Starts:::::");
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = agentService.addStockEntry(stock);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("addStockEntry req ends:::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-all-stock-entry")
	public ResponseEntity<?> getAllStockEntry(@RequestHeader(value = "Authorization") String token,
			@RequestBody Product product) {
		logger.info("addStockEntry req Starts:::::");
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = agentService.getAllStockEntry(product);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getAllStockEntry req ends:::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("update-bulk-products")
	public ResponseEntity<?> updateBulkProducts(@RequestHeader(value = "Authorization") String token,
			@RequestBody List<Product> products) {
		logger.info("updateBulkProducts req Starts:::::");
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = agentService.updateBulkProducts(products);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("updateBulkProducts req ends:::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-role-master")
	public ResponseEntity<?> saveRoleMaster(@RequestHeader(value = "Authorization") String token,
			@RequestBody RoleMaster roles) {
		logger.info("saveRoleMaster req Starts:::::");
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = agentService.saveRoleMaster(roles);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("saveRoleMaster req ends:::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-role-master")
	public ResponseEntity<?> deleteRoleMaster(@RequestHeader(value = "Authorization") String token,
			@RequestBody RoleMaster roles) {
		logger.info("deleteRoleMaster req Starts:::::");
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = agentService.deleteRoleMaster(roles);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("deleteRoleMaster req ends:::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-role-masters")
	public ResponseEntity<?> getRoleMasters(@RequestHeader(value = "Authorization") String token,
			@RequestBody RoleMaster roles) {
		logger.info("getRoleMasters req Starts:::::");
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = agentService.getRoleMasters(roles);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getRoleMasters req ends:::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("get-role-master/{roleId}")
	public ResponseEntity<?> getRoleMaster(@RequestHeader(value = "Authorization") String token,
			@PathVariable("roleId") int roleId) {
		logger.info("getRoleMaster req Starts:::::" + roleId);
		Map<String, Object> resp = new HashMap<>();
		try {
			resp = agentService.getRoleMaster(roleId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getRoleMaster req ends:::::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-agent-report")
	public ResponseEntity<?> getAgentReport(@RequestHeader(value = "Authorization") String token,
			@RequestBody AgentReportRequest agentReport) throws Exception {
		logger.info("get-agent-report starts:::" + agentReport);
		jwtUtil.isValidToken(token);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = agentService.getAgentReport(agentReport);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getAgentReport ends:::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-products-raw-materials")
	public ResponseEntity<?> saveProductsMapped(@RequestHeader(value = "Authorization") String token,
			@RequestBody ProductRequest productReq) throws Exception {
		logger.info("saveProductsMapped Starts::" + productReq);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = agentService.saveProductsMapped(productReq);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("saveProductsMapped Ends::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-products-raw-materials")
	public ResponseEntity<?> getProductsMapped(@RequestHeader(value = "Authorization") String token,
			@RequestBody ProductRequest productReq) throws Exception {
		logger.info("getProductsMapped Starts::" + productReq);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = agentService.getProductsMapped(productReq);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getProductsMapped Ends::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("save-products-raw-materials-request")
	public ResponseEntity<?> saveProductsRawMaterialRequest(@RequestHeader(value = "Authorization") String token,
			@RequestBody ProductRequest productReq) throws Exception {
		logger.info("saveProductsRawMaterialRequest Starts::" + productReq);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = agentService.saveProductsRawMaterialRequest(productReq);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("saveProductsRawMaterialRequest Ends::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("get-products-raw-materials-requests")
	public ResponseEntity<?> getProductsRawMaterialRequests(@RequestHeader(value = "Authorization") String token,
			@RequestBody ProductRequest productReq) throws Exception {
		logger.info("getProductsRawMaterialRequests Starts::" + productReq);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = agentService.getProductsRawMaterialRequests(productReq);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("getProductsRawMaterialRequests Ends::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("delete-products-raw-materials-requests")
	public ResponseEntity<?> deleteProductsRawMaterialRequests(@RequestHeader(value = "Authorization") String token,
			@RequestBody ProductRequest productReq) throws Exception {
		logger.info("deleteProductsRawMaterialRequests Starts::" + productReq);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = agentService.deleteProductsRawMaterialRequests(productReq);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("deleteProductsRawMaterialRequests Ends::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("raw-materials-request-report")
	public ResponseEntity<?> rawMaterialRequestsReport(@RequestHeader(value = "Authorization") String token,
			@RequestBody RawMaterialReportRequests request) throws Exception {
		logger.info("rawMaterialRequestsReport Starts::" + request);
		Map<String, Object> resp = new HashMap<>();

		try {
			resp = agentService.rawMaterialRequestsReport(request);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("rawMaterialRequestsReport Ends::");
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping("load-stock-details")
	public ResponseEntity<?> loadStockDetails(@RequestHeader(value = "Authorization") String token,
			@RequestBody StockEntryReq req) throws Exception {
		logger.error("Get AMC_Details:::::::" + req);
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = agentService.loadStockDetails(req);

		} catch (Exception Ex) {
			logger.error("Error AMC_Details:::::::" + Ex);
			Ex.printStackTrace();
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);

	}

	@PostMapping("notificationToken")
	public ResponseEntity<String> notificationToken(@RequestBody AgentToken token) throws Exception {
		logger.info("notificationToken starts:::::::" + token.toString());
		try {

			AgentToken oldToken = agentTokenRepo.findByEmployeeEmailId(token.getEmployeeEmailId());

			if (oldToken == null) {
				token = agentTokenRepo.save(token);
			} else {
				oldToken.setToken(token.getToken());
				token = agentTokenRepo.save(oldToken);
			}

		} catch (Exception Ex) {
			logger.error("Error notificationToken:::::::" + Ex);
			Ex.printStackTrace();
			return new ResponseEntity<String>(GlobalAccessUtil.toJson(token), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug("notificationToken req ends::");
		return new ResponseEntity<String>(GlobalAccessUtil.toJson(token), HttpStatus.OK);
	}

}
