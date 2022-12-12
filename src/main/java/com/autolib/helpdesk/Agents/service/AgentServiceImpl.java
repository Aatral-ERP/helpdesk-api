package com.autolib.helpdesk.Agents.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Agents.dao.AgentDAO;
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
import com.autolib.helpdesk.common.Util;

@Service
public class AgentServiceImpl implements AgentService {

	@Autowired
	AgentDAO agentDAO;

	@Override
	public Map<String, Object> getHomePageDetails(Agent agent) {
		return agentDAO.getHomePageDetails(agent);
	}

	@Override
	public Map<String, Object> getAllAgentTickets(Agent agent) {
		return agentDAO.getAllAgentTickets(agent);
	}

	@Override
	public Map<String, Object> addAgent(Agent agent) {

		return agentDAO.addAgent(agent);
	}

	@Override
	public Map<String, Object> getAllAgents(Agent agent) {
		return agentDAO.getAllAgents(agent);
	}

	@Override
	public Map<String, Object> getAgentDetails(String agentId) {
		return agentDAO.getAgentDetails(agentId);
	}

	public Map<String, Object> deleteAgent(Agent agent) {
		return agentDAO.deleteAgent(agent);
	}

	@Override
	public Map<String, Object> changeLeaveMaster(Map<String, Object> req) {
		return agentDAO.changeLeaveMaster(req);
	}

//	@Override
//	public Map<String, Object> instituteReport(Map<String, Object> req) {
//
//		return agentDAO.instituteReport(req);
//	}

	@Override
	public Map<String, Object> loadInstitute(Map<String, Object> req) {
		return agentDAO.loadInstitute(req);
	}

	@Override
	public Map<String, Object> saveProduct(Product product) {
		return agentDAO.saveProduct(product);
	}

	@Override
	public Map<String, Object> deleteProduct(Product product) {
		return agentDAO.deleteProduct(product);
	}

	@Override
	public Map<String, Object> getAllProducts() {
		return agentDAO.getAllProducts();
	}

	@Override
	public Map<String, Object> getProductDetails(int productId) {
		return agentDAO.getProductDetails(productId);
	}

	@Override
	public Map<String, Object> forgetPassword(Map<String, Object> req) {

		Map<String, Object> respMap = new HashMap<String, Object>();
		int count = agentDAO.sendOTP(req);
		System.out.println("======Count=======" + count);
		if (count == 1) {
			respMap.putAll(Util.SuccessResponse());
		} else if (count == 2) {
			respMap.putAll(Util.invalidUser());
		} else if (count == 3) {
			respMap.putAll(Util.exitUser());
		} else if (count == 4) {
			respMap.putAll(Util.invalidEmailAndMobile());
		} else {
			respMap.putAll(Util.FailedResponse());
		}
		return respMap;
	}

	@Override
	public Map<String, Object> resetPassword(Map<String, Object> reqMap) {

		Map<String, Object> respMap = new HashMap<String, Object>();
		int count = agentDAO.resetPassword(reqMap);
		if (count > 0) {
			respMap.putAll(Util.SuccessResponse());
		} else {
			respMap.putAll(Util.FailedResponse());
		}
		return respMap;

	}

	@Override
	public Map<String, Object> checkOTP(Map<String, Object> req) {

		int count = agentDAO.checkOTP(req);
		if (count == 0) {
			req.putAll(Util.InvalidOTP());
		} else if (count == 1) {
			req.putAll(Util.SuccessResponse());
		} else if (count == 2) {
			req.putAll(Util.expiredOtpResponse());
		} else {
			req.putAll(Util.FailedResponse());
		}

		return req;
	}

	@Override
	public Map<String, Object> needed(String designation) {
		// TODO Auto-generated method stub
		return agentDAO.needed();
	}

	@Override
	public Map<String, Object> saveInfoDetails(InfoDetails info) {
		return agentDAO.saveInfoDetails(info);
	}

	@Override
	public Map<String, Object> fileUpload(MultipartFile file) {
		return agentDAO.fileUpload(file);
	}

	@Override
	public Map<String, Object> roundSealUpload(MultipartFile file) {
		return agentDAO.roundSealUpload(file);
	}

	@Override
	public Map<String, Object> fullSealUpload(MultipartFile file) {
		return agentDAO.fullSealUpload(file);
	}

	@Override
	public Map<String, Object> getInfoDetails(InfoDetails info) {
		return agentDAO.getInfoDetails(info);
	}

	@Override
	public Map<String, Object> saveProfilePhoto(MultipartFile photo, String employeeId) {
		return agentDAO.saveProfilePhoto(photo, employeeId);
	}

	@Override
	public Map<String, Object> signatureUpload(MultipartFile signature, String employeeId) {
		return agentDAO.signatureUpload(signature, employeeId);
	}

	@Override
	public Map<String, Object> saveVendor(Vendor vendor) {

		return agentDAO.saveVendor(vendor);
	}

	@Override
	public Map<String, Object> deleteVendor(Vendor vendor) {

		return agentDAO.deleteVendor(vendor);
	}

	@Override
	public Map<String, Object> getVendor(Vendor vendor) {
		return agentDAO.getVendor(vendor);
	}

	@Override
	public Map<String, Object> getAllVendors() {
		return agentDAO.getAllVendors();
	}

	@Override
	public Map<String, Object> addStockEntry(StockEntry stock) {
		return agentDAO.addStockEntry(stock);
	}

	@Override
	public Map<String, Object> getAllStockEntry(Product product) {
		return agentDAO.getAllStockEntry(product);
	}

	@Override
	public Map<String, Object> updateBulkProducts(List<Product> products) {
		return agentDAO.updateBulkProducts(products);
	}

	@Override
	public Map<String, Object> saveRoleMaster(RoleMaster role) {
		return agentDAO.saveRoleMaster(role);
	}

	@Override
	public Map<String, Object> deleteRoleMaster(RoleMaster role) {
		return agentDAO.deleteRoleMaster(role);
	}

	@Override
	public Map<String, Object> getRoleMasters(RoleMaster role) {
		return agentDAO.getRoleMasters(role);
	}

	@Override
	public Map<String, Object> getRoleMaster(int roleId) {
		return agentDAO.getRoleMasters(roleId);
	}

	@Override
	public Map<String, Object> getAgentReport(AgentReportRequest agentReport) {
		return agentDAO.getAgentReport(agentReport);
	}

	@Override
	public Map<String, Object> saveProductsMapped(ProductRequest productReq) {
		return agentDAO.saveProductsMapped(productReq);
	}

	@Override
	public Map<String, Object> getProductsMapped(ProductRequest productReq) {
		return agentDAO.getProductsMapped(productReq);
	}

	@Override
	public Map<String, Object> saveProductsRawMaterialRequest(ProductRequest productReq) {
		return agentDAO.saveProductsRawMaterialRequest(productReq);
	}

	@Override
	public Map<String, Object> getProductsRawMaterialRequests(ProductRequest productReq) {
		return agentDAO.getProductsRawMaterialRequests(productReq);
	}

	@Override
	public Map<String, Object> deleteProductsRawMaterialRequests(ProductRequest productReq) {
		return agentDAO.deleteProductsRawMaterialRequests(productReq);
	}

	@Override
	public Map<String, Object> rawMaterialRequestsReport(RawMaterialReportRequests request) {
		return agentDAO.rawMaterialRequestsReport(request);
	}

	@Override
	public Map<String, Object> loadStockDetails(StockEntryReq req) {
		return agentDAO.loadStockDetails(req);
	}

}
