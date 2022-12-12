package com.autolib.helpdesk.Agents.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.AgentReportRequest;
import com.autolib.helpdesk.Agents.entity.InfoDetails;
import com.autolib.helpdesk.Agents.entity.Product;
import com.autolib.helpdesk.Agents.entity.ProductRequest;
import com.autolib.helpdesk.Agents.entity.RawMaterialReportRequests;
import com.autolib.helpdesk.Agents.entity.RoleMaster;
import com.autolib.helpdesk.Agents.entity.StockEntry;
import com.autolib.helpdesk.Agents.entity.StockEntryReq;
import com.autolib.helpdesk.Agents.entity.Vendor;

public interface AgentService {

	Map<String, Object> getHomePageDetails(Agent agent);

	Map<String, Object> getAllAgentTickets(Agent agent);

	Map<String, Object> addAgent(Agent agent);

	Map<String, Object> getAllAgents(Agent agent);

	Map<String, Object> getAgentDetails(String agentId);

	Map<String, Object> deleteAgent(Agent agent);

	Map<String, Object> changeLeaveMaster(Map<String, Object> req);

//	Map<String, Object> instituteReport(Map<String, Object> req);

	Map<String, Object> loadInstitute(Map<String, Object> req);

	Map<String, Object> saveProduct(Product product);

	Map<String, Object> deleteProduct(Product product);

	Map<String, Object> getAllProducts();

	Map<String, Object> getProductDetails(int productId);

	Map<String, Object> forgetPassword(Map<String, Object> req);

	Map<String, Object> resetPassword(Map<String, Object> req);

	Map<String, Object> checkOTP(Map<String, Object> req);

	Map<String, Object> needed(String designation);

	Map<String, Object> saveInfoDetails(InfoDetails info);

	Map<String, Object> fileUpload(MultipartFile file);

	Map<String, Object> roundSealUpload(MultipartFile file);

	Map<String, Object> fullSealUpload(MultipartFile file);

	Map<String, Object> getInfoDetails(InfoDetails info);

	Map<String, Object> saveProfilePhoto(MultipartFile photo, String employeeId);

	Map<String, Object> signatureUpload(MultipartFile signature, String employeeId);

	Map<String, Object> saveVendor(Vendor vendor);

	Map<String, Object> deleteVendor(Vendor vendor);

	Map<String, Object> getVendor(Vendor vendor);

	Map<String, Object> getAllVendors();

	/**
	 * @param stock
	 * @return
	 */
	public Map<String, Object> addStockEntry(StockEntry stock);

	/**
	 * @param product
	 * @return
	 */
	Map<String, Object> getAllStockEntry(Product product);

	Map<String, Object> updateBulkProducts(List<Product> products);

	Map<String, Object> saveRoleMaster(RoleMaster role);

	Map<String, Object> deleteRoleMaster(RoleMaster role);

	Map<String, Object> getRoleMasters(RoleMaster role);

	Map<String, Object> getRoleMaster(int roleId);

	Map<String, Object> getAgentReport(AgentReportRequest agentReport);

	Map<String, Object> saveProductsMapped(ProductRequest productReq);

	Map<String, Object> getProductsMapped(ProductRequest productReq);

	Map<String, Object> saveProductsRawMaterialRequest(ProductRequest productReq);

	Map<String, Object> getProductsRawMaterialRequests(ProductRequest productReq);

	Map<String, Object> deleteProductsRawMaterialRequests(ProductRequest productReq);

	Map<String, Object> rawMaterialRequestsReport(RawMaterialReportRequests request);

	Map<String, Object> loadStockDetails(StockEntryReq req);
}
