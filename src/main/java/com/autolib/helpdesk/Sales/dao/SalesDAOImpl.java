package com.autolib.helpdesk.Sales.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.InfoDetails;
import com.autolib.helpdesk.Agents.entity.Product;
import com.autolib.helpdesk.Agents.entity.Vendor;
import com.autolib.helpdesk.Agents.repository.AgentRepository;
import com.autolib.helpdesk.Agents.repository.InfoDetailsRepository;
import com.autolib.helpdesk.Agents.repository.ProductsRepository;
import com.autolib.helpdesk.Agents.repository.VendorRepository;
import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.Institutes.repository.InstituteRepository;
import com.autolib.helpdesk.Sales.model.TermsAndConditions;
import com.autolib.helpdesk.Sales.repository.DealsRepository;
import com.autolib.helpdesk.Sales.repository.TermsAndConditionsRepository;
import com.autolib.helpdesk.common.Util;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
@Transactional
public class SalesDAOImpl implements SalesDAO {

	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	AgentRepository agentRepo;
	@Autowired
	ProductsRepository prodRepo;
	@Autowired
	InstituteRepository instRepo;
	@Autowired
	VendorRepository vendorRepo;
	@Autowired
	InfoDetailsRepository infoRepo;
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TermsAndConditionsRepository termsRepo;

	@Autowired
	DealsRepository dealRepo;

	@Value("${al.ticket.content-path}")
	private String contentPath;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getSalesNeededData(Map<String, Object> needed) {
		Map<String, Object> resp = new HashMap<>();
		List<String> neededList = (List<String>) needed.get("needed");

		List<Institute> institutes = new ArrayList<>();
		List<String> institutesTypes = new ArrayList<>();

		List<Product> products = new ArrayList<>();
		List<Agent> agents = new ArrayList<>();
		List<Vendor> vendors = new ArrayList<>();
		InfoDetails info = new InfoDetails();
		try {
			if (neededList.contains("institutes"))
				institutes = instRepo.getInstituteMiniAddressDetails();
			if (neededList.contains("institutes_types"))
				institutesTypes = instRepo.getDistinctInstituteTypes();
			if (neededList.contains("products"))
				products = prodRepo.findAll();
			if (neededList.contains("agents"))
				agents = agentRepo.findAllMinDetails();
			if (neededList.contains("vendors"))
				vendors = vendorRepo.findAll();
			if (neededList.contains("info_min"))
				info = infoRepo.getInfoMinDetails();
			if (neededList.contains("state_tin")) {
//				resp.put("StateTinDetails", objectMapper.readTree(new ClassPathResource("./state_tin.json").getFile()));
				resp.put("StateTinDetails", Util.getStateTins());
			}

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			e.printStackTrace();
			resp.putAll(Util.FailedResponse(e.getMessage()));
			logger.info("Error getSalesNeededData :: " + e.getMessage());
		}
		resp.put("Institutes", institutes);
		resp.put("InstitutesTypes", institutesTypes);

		resp.put("Products", products);
		resp.put("Agents", agents);
		resp.put("Vendors", vendors);
		resp.put("InfoDetails", info);

		return resp;
	}

	@Override
	public Map<String, Object> getSalesDashboardData(Map<String, Object> req) {
		Map<String, Object> resp = new HashMap<>();

		try {
			String fromDate = Util.sdfFormatter(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSX", Locale.getDefault())
					.parse(String.valueOf(req.get("fromDate"))));
			String toDate = Util.sdfFormatter(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSX", Locale.getDefault())
					.parse(String.valueOf(req.get("toDate"))));

			List<Map<String, Object>> SalesDashboardPaymentsData = dealRepo.getSalesDashboardPaymentsData(fromDate,
					toDate);

			List<Map<String, Object>> productwiseSalesReport = dealRepo.getProductwiseSalesReport(fromDate, toDate);
			List<Map<String, Object>> last10MonthsSalesData = new ArrayList<>();

			resp.put("Stats", dealRepo.getSalesDashboardStatsData(fromDate, toDate));
			resp.put("Payments", SalesDashboardPaymentsData);

			List<String> types = dealRepo.getDistinctDealTypes();

			System.out.println(types.toString());

			types.parallelStream().forEach(type -> {
				Map<String, Object> map = new HashMap<>();

				map.put("name", String.valueOf(type));
				map.put("series", dealRepo.getMonthlySalesAmountsStatsByDealType(type));

				last10MonthsSalesData.add(map);

			});

			resp.put("productwiseSalesReport", productwiseSalesReport);
			resp.put("last10MonthsSalesData", last10MonthsSalesData);
			resp.putAll(Util.SuccessResponse());

		} catch (Exception e) {
			e.printStackTrace();
			resp.putAll(Util.FailedResponse(e.getMessage()));
			logger.info("Error getSalesNeededData :: " + e.getMessage());
		}

		return resp;
	}

	@Override
	public Map<String, Object> uploadPreambleDocuments(MultipartFile file) {
		Map<String, Object> resp = new HashMap<>();

		File directory = new File(contentPath + "/_preamble_documents/");
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

			resp.putAll(Util.SuccessResponse());
		} catch (IOException e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public Map<String, Object> getPreambleDocumentsList() {
		Map<String, Object> resp = new HashMap<>();
		List<String> names = new ArrayList<>();
		try {
//			Path configFilePath = FileSystems.getDefault().getPath(contentPath + "/_preamble_documents/");
//
//			List<Path> fileWithName = Files.walk(configFilePath).filter(s -> s.toString().endsWith(".pdf"))
//					.map(Path::getFileName).sorted().collect(Collectors.toList());

			names = Files.list(Paths.get(contentPath + "/_preamble_documents/")).filter(Files::isRegularFile)
					.map(p -> p.getFileName().toString()).collect(Collectors.toList());

//			for (Path name : fileWithName) {
//				// printing the name of file in every sub folder
//				System.out.println(name);
//				names.add(name.toString());
//			}

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.put("Files", names);
		return resp;
	}

	@Override
	public Map<String, Object> deletePreambleDocumentsList(String filename) {
		Map<String, Object> resp = new HashMap<>();

		try {
			File file = new File(contentPath + "/_preamble_documents/" + filename);
			Files.deleteIfExists(file.toPath());

			resp.putAll(Util.SuccessResponse());
		} catch (IOException e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public Map<String, Object> saveTermsAndConditions(TermsAndConditions terms) {
		Map<String, Object> resp = new HashMap<>();

		try {

			terms = termsRepo.save(terms);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("Terms", terms);
		return resp;
	}

	@Override
	public Map<String, Object> deleteTermsAndConditions(TermsAndConditions terms) {
		Map<String, Object> resp = new HashMap<>();

		try {

			termsRepo.delete(terms);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public Map<String, Object> getTermsAndConditions(String type) {
		Map<String, Object> resp = new HashMap<>();
		List<TermsAndConditions> terms = new ArrayList<>();
		try {

			terms = termsRepo.findByType(type);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("Terms", terms);
		return resp;
	}

}
